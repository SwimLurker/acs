package org.slstudio.acs.tr069.job.runner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.instruction.*;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.resulthandler.IJobResultHandler;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÏÂÎç10:29
 */
public class JobRunner implements IJobRunner{
    private static final Log log = LogFactory.getLog(JobRunner.class);
    private List<IJobResultHandler> resultHandlerList = new ArrayList<IJobResultHandler>();

    public List<IJobResultHandler> getResultHandlerList() {
        return resultHandlerList;
    }

    public void setResultHandlerList(List<IJobResultHandler> resultHandlerList) {
        this.resultHandlerList = resultHandlerList;
    }

    public TR069Message beginRun(IDeviceJob job, ITR069MessageContext context) throws JobException {
        //job can not be finished
        checkJobNotFinished(job);
        //job can not be running
        checkJobNotRunning(job);
        //first set status to running
        if(job.isReady()){
            job.setStatus(DeviceJobConstants.STATUS_RUNNING);
            job.setBeginTime(new Date());
        }

        //let job continue execute instruction
        return executeTillBlockingOrJobFinished(job, context);
    }

    public void beginRunWithRequest(IDeviceJob job, ITR069MessageContext context, TR069Message message) throws JobException{
        //job can not be finished
        checkJobNotFinished(job);
        //job can not be running
        checkJobNotRunning(job);
        //first set status to running
        if(job.isReady()){
            job.setStatus(DeviceJobConstants.STATUS_RUNNING);
            job.setBeginTime(new Date());
        }

        //then let job execute instruction until some instruction is blocking or job completed or failed
        TR069Message jobRequest = executeTillBlockingOrJobFinished(job,context);

        String jobID = job.getJobID();

        //if jobRequest not null, then means there is some instruction is waiting response, then cache the jobRequest and
        // return to let current instruction handle future response
        if(jobRequest !=null){
            job.setCachedRequest(jobRequest);
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.toSOAPString());
            return;
        }

        IInstruction currentInstruction = job.getCurrentInstruction();
        //all instructions have finished execution -- job has completed/failed, then return just return
        if(currentInstruction == null){
            log.debug("(job:" + job.getJobID() + ") begin with request:" +
                    message.getMessageName() + ", job has finished");
            return;
        }
        //jobRequest is null and current instruction is not null ,means there is some instruction is waiting request
        if(!isWaitingRequest(currentInstruction)){
            log.error("(job:" + jobID + ")'s current instruction:" + currentInstruction.getInstructionID() +" is wrong type to handle request:" + message.getMessageName());
            throw new JobException("impossible instruction type for dealing request");
        }

        IWaitTR069RequestInstruction waitRequestInstruction = (IWaitTR069RequestInstruction) currentInstruction;
        boolean bHandled = handleInstructionWithRequest(job, waitRequestInstruction, message, context);
        if(!bHandled){
            //not handled by current instruction, return null to skip for future request
            log.debug("(job:" + jobID + ")'s instruction:" + currentInstruction.getInstructionID() + " skip this request:" +
                    (message == null? "empty message":message.getMessageName()));
            return;
        }

        // job has completed/failed, then return just return
        if(job.getCurrentInstruction() == null){
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", job has finished");
            return;
        }

        //let job continue execute instruction till some instruction is blocking or job completed or failed
        jobRequest = executeTillBlockingOrJobFinished(job, context);
        //cache the job Request if it is not null, then return to let current instruction handle future request or response
        if(jobRequest !=null){
            job.setCachedRequest(jobRequest);
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.toSOAPString());
        }
    }

    public TR069Message continueRun(IDeviceJob job, ITR069MessageContext context) throws JobException{
        return continueRunWithResponse(job, context, null);
    }

    public TR069Message continueRunWithResponse(IDeviceJob job, ITR069MessageContext context, TR069Message message) throws JobException{
        //job can not be finished
        checkJobNotFinished(job);
        //job can not be ready
        checkJobNotReady(job);

        //current instruction should not be null
        checkCurrentInstructionNotNull(job);
        //current instruction should not be non-blocking
        checkCurrentInstructionBlocking(job);

        //send cached resulthandler
        TR069Message request = fetchCachedRequest(job);
        if(request != null){
            return request;
        }
        String jobID = job.getJobID();
        IInstruction currentInstruction = job.getCurrentInstruction();
        //if current job is waiting request, just return null to let job continue in running to deal other request
        if(isWaitingRequest(currentInstruction)) {
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") is waiting request," +
                    "skip this response:" + (message == null? "empty message":message.getMessageName()));
            return null;
        }

        //let current instruction handle response
        IWaitTR069ResponseInstruction waitResponseInstruction = (IWaitTR069ResponseInstruction) currentInstruction;
        boolean bHandled = handleInstructionWithResponse(job, waitResponseInstruction, message, context);
        if(!bHandled){
            //response not handled by current instruction, return null to skip for future response
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") skip this response:" +
                    (message == null? "empty message":message.getMessageName()));
            return null;
        }
        currentInstruction = job.getCurrentInstruction();
        // job has completed/failed, then return just return
        if(currentInstruction == null){
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", job has finished");
            return null;
        }
        //response has been handled, continue execute to get some request
        return executeTillBlockingOrJobFinished(job, context);
    }

    public void continueRunWithRequest(IDeviceJob job, ITR069MessageContext context, TR069Message message) throws JobException {
        //job can not be finished
        checkJobNotFinished(job);
        //job can not be ready
        checkJobNotReady(job);

        //current instruction should not be null
        checkCurrentInstructionNotNull(job);
        //current instruction should not be non-blocking
        checkCurrentInstructionBlocking(job);

        String jobID = job.getJobID();
        IInstruction currentInstruction = job.getCurrentInstruction();
        //if current job is waiting response, just return to let job continue in running to deal other response
        if(isWaitingResponse(currentInstruction)) {
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") is waiting response," +
                    "skip this request:" + (message == null? "empty message":message.getMessageName()));
            return;
        }

        //let current instruction handle request
        IWaitTR069RequestInstruction waitRequestInstruction = (IWaitTR069RequestInstruction) currentInstruction;
        boolean bHandled = handleInstructionWithRequest(job,waitRequestInstruction, message, context);
        if(!bHandled){
            //request not handled by current instruction, return to skip for future request
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") skip this response:" +
                    (message == null? "empty message":message.getMessageName()));
            return;
        }

        currentInstruction = job.getCurrentInstruction();
        // job has completed/failed, then return just return
        if(currentInstruction == null){
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", job has finished");
            return;
        }
        //request has been handled, continue to get some request
        TR069Message jobRequest = executeTillBlockingOrJobFinished(job, context);
        //cache the job Request if it is not null, then return to let current instruction handle future request or response
        if(jobRequest !=null){
            job.setCachedRequest(jobRequest);
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.toSOAPString());
        }
    }

    public void failOnException(IDeviceJob job, Exception exp) {
        job.setStatus(DeviceJobConstants.STATUS_FAILED);
        job.setErrorCode(DeviceJobConstants.ERRORCODE_UNKNOWNERROR);
        job.setErrorMsg(exp.getMessage());
        job.setCompleteTime(new Date());
        log.error("job:"+ job.getJobID() + " has failed for exception", exp);
        for(IJobResultHandler handler: resultHandlerList){
            handler.onFailed(job);
        }
    }

    public void failOnTimeout(IDeviceJob job, boolean bWaitingTimeout) {
        job.setStatus(DeviceJobConstants.STATUS_FAILED);
        job.setErrorCode(bWaitingTimeout?DeviceJobConstants.ERRORCODE_WAITINGTIMEOUT:DeviceJobConstants.ERRORCODE_RUNNINGTIMEOUT);
        job.setErrorMsg(bWaitingTimeout?"Waiting timeout for execution":"Running timeout");
        job.setCompleteTime(new Date());
        log.error("job:"+ job.getJobID() + " has failed for timeout:" + (bWaitingTimeout?"waiting timeout":"running timeout"));
        for(IJobResultHandler handler: resultHandlerList){
            handler.onFailed(job);
        }
    }

    private void fail(IDeviceJob job, ITR069MessageContext context, int errorCode, String errorMsg) {
        job.setStatus(DeviceJobConstants.STATUS_FAILED);
        job.setCompleteTime(new Date());
        job.setCurrentInstruction(null);
        if(job.getErrorCode() == DeviceJobConstants.ERRORCODE_NOERROR){
            job.setErrorCode(errorCode);
            job.setErrorMsg(errorMsg);
        }
        log.info("job:" + job.getJobID() + " failed");
        for(IJobResultHandler handler: resultHandlerList){
            handler.onFailed(job);
        }
    }

    private void complete(IDeviceJob job, ITR069MessageContext context) {
        job.setStatus(DeviceJobConstants.STATUS_COMPLETE);
        job.setCompleteTime(new Date());
        job.setCurrentInstruction(null);
        log.info("job:" + job.getJobID() + " completed");
        for(IJobResultHandler handler: resultHandlerList){
            handler.onSucceed(job);
        }
    }


    //return null means all instruction has finished execution(current instruction is null) or there is some instruction waiting request (current instruction is not null)
    //return not null means there is some instruction waiting response (current instruction is not null)
    private TR069Message executeTillBlockingOrJobFinished(IDeviceJob job, ITR069MessageContext context){
        TR069Message request = null;
        boolean bContinue = true;
        //fetch one instruction from queue

        String jobID = job.getJobID();
        IInstruction currentInstruction = null;
        while((currentInstruction = job.getInstructionQueue().pop()) != null){
            job.setCurrentInstruction(currentInstruction);
            try{
                InstructionContext cmdContext = new InstructionContext(job.getSymbolTable());
                //execute instruction to get resulthandler
                currentInstruction.execute(cmdContext);
                if(currentInstruction instanceof ITR069Instruction){
                    //instruction is waiting request or response, get message then return
                    request = ((ITR069Instruction)currentInstruction).getTR069Message();
                    break;
                }
            }catch(InstructionFailException ifExp){
                log.warn("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() +") instruction execute failed, but can go on next instruction", ifExp);
            }catch(JobFailException jfExp){
                log.warn("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") instruction execute failed, make job fail", jfExp);
                fail(job, context, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION, jfExp.getMessage());
                //job failed, should return no request
                return null;
            }catch(JobCompleteException jcExp){
                log.info("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") instruction execute make job complete");
                complete(job, context);
                //job failed, should return no request
                return null;
            }
        }

        if(currentInstruction == null){
            job.setCurrentInstruction(null);
            //no instruction in queue, job completed
            log.info("no instruction in queue, complete job:" + jobID);
            complete(job, context);
        }
        return request;
    }

    private void checkJobNotFinished(IDeviceJob job) throws JobException{
        if(job.isFinished()){
            log.error("(job:" + job.getJobID() + ",instruction:" + (job.getCurrentInstruction() == null ? "null" : job.getCurrentInstruction().getInstructionID()) + "): job has already finished");
            throw new JobException("job has already finished");
        }
    }

    private void checkJobNotReady(IDeviceJob job) throws JobException{
        if(job.isReady()){
            log.error("(job:" + job.getJobID() + ",instruction:" + (job.getCurrentInstruction() == null ? "null" : job.getCurrentInstruction().getInstructionID()) + "): job has not begun");
            throw new JobException("job has not begun to run");
        }
    }

    private void checkJobNotRunning(IDeviceJob job) throws JobException{
        if(job.isRunning()){
            log.error("(job:" + job.getJobID() + ",instruction:" + (job.getCurrentInstruction() == null ? "null" : job.getCurrentInstruction().getInstructionID()) + "): job is still running");
            throw new JobException("job is running");
        }
    }

    private void checkCurrentInstructionNotNull(IDeviceJob job) throws JobException{
        if(job.getCurrentInstruction() == null){
            log.error("job:" + job.getJobID() + " should not be null");
            throw new JobException("current instruction is null");
        }
    }

    private void checkCurrentInstructionBlocking(IDeviceJob job) throws JobException{
        //check if current instruction is blocking
        if(!isBlockingInstruction(job.getCurrentInstruction())) {
            log.error("(job:" + job.getJobID() + ",instruction:" + job.getCurrentInstruction().getInstructionID() + ") is not blocking instruction to handle response");
            throw new JobException("current instruction is not blocking");
        }
    }

    private TR069Message fetchCachedRequest(IDeviceJob job){
        if(job.getCachedRequest() != null){
            log.debug("(job:" + job.getJobID() + ",instruction:" + (job.getCurrentInstruction() == null ? "null" : job.getCurrentInstruction().getInstructionID()) + "): has cached request: " + job.getCachedRequest().toSOAPString());
            TR069Message result = job.getCachedRequest();
            job.setCachedRequest(null);
            return result;
        }
        return null;
    }

    private boolean isWaitingResponse(IInstruction instruction) {
        if(instruction != null){
            if(instruction instanceof IWaitTR069ResponseInstruction){
                log.debug("instruction:" + instruction.getInstructionID() + " is waiting response");
                return true;
            }
        }
        return false;
    }

    private boolean isWaitingRequest(IInstruction instruction) {
        if(instruction != null){
            if(instruction instanceof IWaitTR069RequestInstruction){
                log.debug("instruction:" + instruction.getInstructionID() + " is waiting request");
                return true;
            }
        }
        return false;
    }

    private boolean isBlockingInstruction(IInstruction instruction) {
        if(instruction != null){
            if(instruction instanceof ITR069Instruction){
                log.debug("instruction:" + instruction.getInstructionID() + " is blocking");
                return true;
            }
        }
        return false;
    }

    private boolean handleInstructionWithResponse(IDeviceJob job, IWaitTR069ResponseInstruction instruction, TR069Message response, ITR069MessageContext context)
            throws JobException{
        InstructionContext instructionContext = new InstructionContext(job.getSymbolTable());
        try{
            return instruction.handleResponse(instructionContext, response);
        }catch(InstructionFailException cneExp){
            log.warn("(Job:" + job.getJobID() + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle response:" +
                    (response == null? "empty message":response.getMessageName()) + " failed, but can go on next instruction", cneExp);
            return true;
        }catch(JobFailException jfExp){
            log.warn("(Job:" + job.getJobID() + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle response:" +
                    (response == null? "empty message":response.getMessageName()) + " failed with fatal error, makes job failed", jfExp);
            fail(job, context, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION, jfExp.getMessage());
            return true;
        }
    }

    private boolean handleInstructionWithRequest(IDeviceJob job, IWaitTR069RequestInstruction instruction, TR069Message request, ITR069MessageContext context)
            throws JobException{
        InstructionContext instructionContext = new InstructionContext(job.getSymbolTable());
        try{
            return instruction.handleRequest(instructionContext, request);
        }catch(InstructionFailException cneExp){
            log.warn("(Job:" + job.getJobID() + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle request:" +
                    (request == null? "empty message":request.getMessageName()) + " failed, but can go on next instruction", cneExp);
            return true;
        }catch(JobFailException jfExp){
            log.warn("(Job:" + job.getJobID() + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle request:" +
                    (request == null? "empty message":request.getMessageName()) + " failed with fatal error, makes job failed", jfExp);
            fail(job, context, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION, jfExp.getMessage());
            return true;
        }
    }
}
