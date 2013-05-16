package org.slstudio.acs.tr069.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.instruction.*;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.instruction.queue.DefaultInstructionQueue;
import org.slstudio.acs.tr069.instruction.queue.IInstructionQueue;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.job.result.IJobResultHandler;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.util.CustomDateDeserializer;
import org.slstudio.acs.util.CustomDateSerializer;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ÉÏÎç1:52
 */
@JsonAutoDetect
public class DefaultDeviceJob implements IDeviceJob {
    public static final int STATUS_READY = 0;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_FAILED = 3;

    private static final Log log = LogFactory.getLog(DefaultDeviceJob.class);

    private String jobID = null;
    private String jobName = null;
    private String deviceKey = null;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date createTime;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date beginTime;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date completeTime;
    private int status = STATUS_READY;

    private List<IJobResultHandler> resultHandlerList = new ArrayList<IJobResultHandler>();
    private IInstructionQueue instructionQueue = new DefaultInstructionQueue();
    private IInstruction currentInstruction = null;
    private Map<String, Object> symbolTable = new HashMap<String, Object>();
    private IJobRequest cachedRequest = null;


    public DefaultDeviceJob() {
    }

    public DefaultDeviceJob(String deviceKey, String jobID) {
        this.deviceKey = deviceKey;
        this.jobID = jobID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public void addResultHandler(IJobResultHandler resultHandler) {
        resultHandlerList.add(resultHandler);
    }

    public void removeResultHandler(IJobResultHandler resultHandler) {
        resultHandlerList.remove(resultHandler);
    }

    @JsonIgnore
    public List<IJobResultHandler> getResultHandlerList() {
        return resultHandlerList;
    }

    public IInstruction getCurrentInstruction() {
        return currentInstruction;
    }

    public void setCurrentInstruction(IInstruction currentInstruction) {
        this.currentInstruction = currentInstruction;
    }

    public List<IInstruction> getInstructions() {
        return getInstructionQueue().getAllInstructions();
    }

    public int getInstructionCount() {
        return getInstructionQueue().getAllInstructions().size();
    }

    public void addInstruction(IInstruction instruction){
        instructionQueue.push(instruction);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult(){
        return symbolTable.get(DeviceJobConstants.SYMBOLNAME_RETURNVALUE);
    }

    public void setResult(Object result){
        symbolTable.put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, result);
    }

    public int getErrorCode(){
        Object result = symbolTable.get(DeviceJobConstants.SYMBOLNAME_ERRORCODE);
        if(result == null){
            return DeviceJobConstants.ERRORCODE_NOERROR;
        }else{
            return (Integer)result;
        }
    }

    public void setErrorCode(int errorCode){
        symbolTable.put(DeviceJobConstants.SYMBOLNAME_ERRORCODE, errorCode);
    }

    public String getErrorMsg(){
        return (String)symbolTable.get(DeviceJobConstants.SYMBOLNAME_ERRORMSG);
    }

    public void setErrorMsg(String errorMsg){
        symbolTable.put(DeviceJobConstants.SYMBOLNAME_ERRORMSG, errorMsg);
    }

    @JsonIgnore
    public IInstructionQueue getInstructionQueue() {
        return instructionQueue;
    }

    public void setInstructionQueue(IInstructionQueue instructionQueue) {
        this.instructionQueue = instructionQueue;
    }

    public Map<String, Object> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(Map<String, Object> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IJobRequest getCachedRequest(){
        return cachedRequest;
    }

    @JsonIgnore
    public boolean isReady() {
        return status == STATUS_READY;
    }

    @JsonIgnore
    public boolean isRunning() {
        return status == STATUS_RUNNING;
    }

    @JsonIgnore
    public boolean isFinished() {
        return status == STATUS_COMPLETE || status == STATUS_FAILED;
    }

    public IJobRequest beginRun(ITR069MessageContext context) throws JobException{
        //job can not be finished
        checkJobNotFinished();
        //job can not be running
        checkJobNotRunning();
        //first set status to running
        if(isReady()){
            setStatus(STATUS_RUNNING);
            setBeginTime(new Date());
        }

        //let job continue execute instruction
        return executeTillBlockingOrNoInstruction(context);
    }

    public void beginRunWithRequest(ITR069MessageContext context, TR069Message message) throws JobException{
        //job can not be finished
        checkJobNotFinished();
        //job can not be running
        checkJobNotRunning();
        //first set status to running
        if(isReady()){
            setStatus(STATUS_RUNNING);
            setBeginTime(new Date());
        }

        //then let job execute instruction until some instruction is blocking or all instruction has finished execution
        IJobRequest jobRequest = executeTillBlockingOrNoInstruction(context);

        //if jobRequest not null, then means there is some instruction is waiting response, then cache the jobRequest and
        // return to let current instruction handle future response
        if(jobRequest !=null){
            cachedRequest = jobRequest;
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.getTr069Request().toSOAPString());
            return;
        }
        //all instructions have finished execution -- job has completed, then return just return
        if(currentInstruction == null){
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", all instructions has been finished execution, job has completed");
            return;
        }
        //jobRequest is null and current instruction is not null ,means there is some instruction is waiting request
        if(!isWaitingRequest(currentInstruction)){
            log.error("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + "): current instruction is wrong type to handle request:" + message.getMessageName());
            throw new JobException("impossible instruction type for dealing request");
        }

        IWaitRequestInstruction waitRequestInstruction = (IWaitRequestInstruction) currentInstruction;
        boolean bHandled = handleInstructionWithRequest(waitRequestInstruction, message, context);
        if(!bHandled){
            //not handled by current instruction, return null to skip for future request
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") skip this request:" +
                    (message == null? "empty message":message.getMessageName()));
            return;
        }

        //let job continue execute instruction till some instruction is blocking or all instruction has been executed
        jobRequest = executeTillBlockingOrNoInstruction(context);
        //cache the job Request if it is not null, then return to let current instruction handle future request or response
        if(jobRequest !=null){
            cachedRequest = jobRequest;
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.getTr069Request().toSOAPString());
        }
    }

    public IJobRequest continueRun(ITR069MessageContext context) throws JobException{
        return continueRunWithResponse(context, null);
    }

    public IJobRequest continueRunWithResponse(ITR069MessageContext context, TR069Message message) throws JobException{
        //job can not be finished
        checkJobNotFinished();
        //job can not be ready
        checkJobNotReady();

        //current instruction should not be null
        checkCurrentInstructionNotNull();
        //current instruction should not be non-blocking
        checkCurrentInstructionBlocking();

        //send cached result
        IJobRequest request = fetchCachedRequest();
        if(request != null){
            return request;
        }

        //if current job is waiting request, just return null to let job continue in running to deal other request
        if(isWaitingRequest(currentInstruction)) {
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") is waiting request," +
                    "skip this response:" + (message == null? "empty message":message.getMessageName()));
            return null;
        }

        //let current instruction handle response
        IWaitResponseInstruction waitResponseInstruction = (IWaitResponseInstruction) currentInstruction;
        boolean bHandled = handleInstructionWithResponse(waitResponseInstruction, message, context);
        if(!bHandled){
            //response not handled by current instruction, return null to skip for future response
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") skip this response:" +
                    (message == null? "empty message":message.getMessageName()));
            return null;
        }
        //response has been handled, continue execute to get some request
        return executeTillBlockingOrNoInstruction(context);
    }

    public void continueRunWithRequest(ITR069MessageContext context, TR069Message message) throws JobException {
        //job can not be finished
        checkJobNotFinished();
        //job can not be ready
        checkJobNotReady();

        //current instruction should not be null
        checkCurrentInstructionNotNull();
        //current instruction should not be non-blocking
        checkCurrentInstructionBlocking();

        //if current job is waiting response, just return to let job continue in running to deal other response
        if(isWaitingResponse(currentInstruction)) {
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") is waiting response," +
                    "skip this request:" + (message == null? "empty message":message.getMessageName()));
            return;
        }

        //let current instruction handle request
        IWaitRequestInstruction waitRequestInstruction = (IWaitRequestInstruction) currentInstruction;
        boolean bHandled = handleInstructionWithRequest(waitRequestInstruction, message, context);
        if(!bHandled){
            //request not handled by current instruction, return to skip for future request
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") skip this response:" +
                    (message == null? "empty message":message.getMessageName()));
            return;
        }
        //request has been handled, continue to get some request
        IJobRequest jobRequest = executeTillBlockingOrNoInstruction(context);
        //cache the job Request if it is not null, then return to let current instruction handle future request or response
        if(jobRequest !=null){
            cachedRequest = jobRequest;
            log.debug("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.getTr069Request().toSOAPString());
        }
    }

    public void failOnException(Exception exp) {
        setStatus(STATUS_FAILED);
        setCompleteTime(new Date());
        log.error("job:"+ jobID + " has failed for exception", exp);
        for(IJobResultHandler handler: resultHandlerList){
            handler.onFailed(this);
        }
    }

    private void fail(ITR069MessageContext context) {
        setStatus(STATUS_FAILED);
        setCompleteTime(new Date());
        log.info("job:" + jobID + " failed");
        for(IJobResultHandler handler: resultHandlerList){
            handler.onFailed(this);
        }
    }

    private void complete(ITR069MessageContext context) {
        setStatus(STATUS_COMPLETE);
        setCompleteTime(new Date());
        log.info("job:" + jobID + " completed");
        for(IJobResultHandler handler: resultHandlerList){
            handler.onSucceed(this);
        }
    }


    //return null means all instruction has finished execution(current instruction is null) or there is some instruction waiting request (current instruction is not null)
    //return not null means there is some instruction waiting response (current instruction is not null)
    private IJobRequest executeTillBlockingOrNoInstruction(ITR069MessageContext context) throws JobException{
        IJobRequest request = null;
        boolean bContinue = true;
        //fetch one instruction from queue


        while((currentInstruction = instructionQueue.pop()) != null){
            try{
                InstructionContext cmdContext = new InstructionContext(symbolTable);
                //execute instruction to get result
                request = currentInstruction.execute(cmdContext);
                if(isBlockingInstruction(currentInstruction)){
                    //instruction is waiting request or response, then return
                    break;
                }
            }catch(InstructionFailException ifExp){
                log.warn("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() +") instruction execute failed, but can go on next instruction", ifExp);
            }catch(JobFailException jfExp){
                log.warn("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") instruction execute failed, make job fail", jfExp);
                fail(context);
                throw new JobException("instruction execute failed makes job failed", jfExp);
            }catch(JobCompleteException jcExp){
                log.info("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") instruction execute make job complete", jcExp);
                //set current instruction to null
                currentInstruction = null;
                break;
            }
        }

        if(currentInstruction == null){
            //no instruction in queue, job completed
            log.info("no instruction in queue, complete job:" + jobID);
            complete(context);
        }
        return request;
    }

    private void checkJobNotFinished() throws JobException{
        if(isFinished()){
            log.error("(job:" + jobID + ",instruction:" + (currentInstruction == null ? "null" : currentInstruction.getInstructionID()) + "): job has already finished");
            throw new JobException("job has already finished");
        }
    }

    private void checkJobNotReady() throws JobException{
        if(isReady()){
            log.error("(job:" + jobID + ",instruction:" + (currentInstruction == null ? "null" : currentInstruction.getInstructionID()) + "): job has not begun");
            throw new JobException("job has not begun to run");
        }
    }

    private void checkJobNotRunning() throws JobException{
        if(isRunning()){
            log.error("(job:" + jobID + ",instruction:" + (currentInstruction == null ? "null" : currentInstruction.getInstructionID()) + "): job is still running");
            throw new JobException("job is running");
        }
    }

    private void checkCurrentInstructionNotNull() throws JobException{
        if(currentInstruction == null){
            log.error("job:" + jobID + " should not be null");
            throw new JobException("current instruction is null");
        }
    }

    private void checkCurrentInstructionBlocking() throws JobException{
        //check if current instruction is blocking
        if(!isBlockingInstruction(currentInstruction)) {
            log.error("(job:" + jobID + ",instruction:" + currentInstruction.getInstructionID() + ") is not blocking instruction to handle response");
            throw new JobException("current instruction is not blocking");
        }
    }

    private IJobRequest fetchCachedRequest(){
        if(cachedRequest != null){
            log.debug("(job:" + jobID + ",instruction:" + (currentInstruction == null ? "null" : currentInstruction.getInstructionID()) + "): has cached request: " + cachedRequest.getTr069Request().toSOAPString());
            IJobRequest result = cachedRequest;
            cachedRequest = null;
            return result;
        }
        return null;
    }

    private boolean isWaitingResponse(IInstruction instruction) {
        if(instruction != null){
            if(instruction instanceof IWaitResponseInstruction){
                log.debug("(job:" + jobID + ",instruction:" + instruction.getInstructionID() + "): instruction is waiting response");
                return true;
            }
        }
        return false;
    }

    private boolean isWaitingRequest(IInstruction instruction) {
        if(instruction != null){
            if(instruction instanceof IWaitRequestInstruction){
                log.debug("(job:" + jobID + ",instruction:" + instruction.getInstructionID() + "): instruction is waiting request");
                return true;
            }
        }
        return false;
    }

    private boolean isBlockingInstruction(IInstruction instruction) {
        if(instruction != null){
            if(instruction instanceof IWaitResponseInstruction || instruction instanceof IWaitRequestInstruction){
                log.debug("(job:" + jobID + ",instruction:" + instruction.getInstructionID() + "): instruction is blocking");
                return true;
            }
        }
        return false;
    }

    private boolean handleInstructionWithResponse(IWaitResponseInstruction instruction, TR069Message response, ITR069MessageContext context)
            throws JobException{
        InstructionContext instructionContext = new InstructionContext(symbolTable);
        try{
            return instruction.handleResponse(instructionContext, response);
        }catch(InstructionFailException cneExp){
            log.warn("(Job:" + jobID + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle response:" +
                    (response == null? "empty message":response.getMessageName()) + " failed, but can go on next instruction", cneExp);
            return true;
        }catch(JobFailException cfeExp){
            log.warn("(Job:" + jobID + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle response:" +
                    (response == null? "empty message":response.getMessageName()) + " failed with fatal error, makes job failed", cfeExp);
            fail(context);
            throw new JobException("instruction execute with fatal error, makes job failed", cfeExp);
        }
    }

    private boolean handleInstructionWithRequest(IWaitRequestInstruction instruction, TR069Message request, ITR069MessageContext context)
            throws JobException{
        InstructionContext instructionContext = new InstructionContext(symbolTable);
        try{
            return instruction.handleRequest(instructionContext, request);
        }catch(InstructionFailException cneExp){
            log.warn("(Job:" + jobID + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle request:" +
                    (request == null? "empty message":request.getMessageName()) + " failed, but can go on next instruction", cneExp);
            return true;
        }catch(JobFailException jfExp){
            log.warn("(Job:" + jobID + ",Instruction:" + instruction.getInstructionID() + "): current instruction handle request:" +
                    (request == null? "empty message":request.getMessageName()) + " failed with fatal error, makes job failed", jfExp);
            fail(context);
            throw new JobException("instruction execute with fatal error, makes job failed", jfExp);
        }
    }

}
