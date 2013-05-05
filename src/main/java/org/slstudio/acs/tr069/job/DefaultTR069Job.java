package org.slstudio.acs.tr069.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.command.*;
import org.slstudio.acs.tr069.command.exception.CommandFatalErrorException;
import org.slstudio.acs.tr069.command.exception.CommandNormalErrorException;
import org.slstudio.acs.tr069.command.exception.TestGetPVsCommand;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ÉÏÎç1:52
 */
public class DefaultTR069Job implements ISystemJob, IUserJob{
    public static final int STATUS_READY = 0;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_FAILED = 3;

    private static final Log log = LogFactory.getLog(DefaultTR069Job.class);

    private String deviceKey = null;
    private String jobID = null;
    private int status = STATUS_READY;

    private ICommandQueue commandQueue = new DefaultCommandQueue();
    private ICommand currentCommand = null;
    private Map<String, Object> symbolTable = new HashMap<String, Object>();

    private IJobRequest cachedRequest = null;


    public DefaultTR069Job(String deviceKey, String jobID) {
        this.deviceKey = deviceKey;
        this.jobID = jobID;
        commandQueue.push(new TestCommand("abc", jobID));
        commandQueue.push(new TestGetPVsCommand(jobID));
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isReady() {
        return status == STATUS_READY;
    }

    public boolean isRunning() {
        return status == STATUS_RUNNING;
    }

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
        }

        //get new command and execute
        currentCommand = commandQueue.pop();

        return executeTillBlockingOrNoCommand(context);
    }

    public void beginRunWithRequest(ITR069MessageContext context, TR069Message message) throws JobException{
        //job can not be finished
        checkJobNotFinished();
        //job can not be running
        checkJobNotRunning();
        //first set status to running
        if(isReady()){
            setStatus(STATUS_RUNNING);
        }
        //fetch one command from queue
        currentCommand = commandQueue.pop();

        //then let job execute command until some command is blocking or all command has finished execution
        IJobRequest jobRequest = executeTillBlockingOrNoCommand(context);

        //if jobRequest not null, then means there is some command is waiting response, then cache the jobRequest and
        // return to let current command handle future response
        if(jobRequest !=null){
            cachedRequest = jobRequest;
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.toSOAPMessage());
            return;
        }
        //all commands have finished execution -- job has completed, then return just return
        if(currentCommand == null){
            log.debug("(job:" + jobID + ") begin with request:" +
                    message.getMessageName() + ", all commands has been finished execution, job has completed");
            return;
        }
        //jobRequest is null and current command is not null ,means there is some command is waiting request
        if(!isWaitingRequest(currentCommand)){
            log.error("(job:" + jobID + ",command:" + currentCommand.getCommandID() + "): current command is wrong type to handle request:" + message.getMessageName());
            throw new JobException("impossible command type for dealing request");
        }

        IWaitRequestCommand waitRequestCommand = (IWaitRequestCommand)currentCommand;
        boolean bHandled = handleCommandWithRequest(waitRequestCommand, message, context);
        if(!bHandled){
            //not handled by current command, return null to skip for future request
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") skip this request:" +
                    (message == null? "empty message":message.getMessageName()));
            return;
        }

        //get new command and let job continue execute command till some command is blocking or all command has been executed
        currentCommand = commandQueue.pop();
        jobRequest = executeTillBlockingOrNoCommand(context);
        //cache the job Request if it is not null, then return to let current command handle future request or response
        if(jobRequest !=null){
            cachedRequest = jobRequest;
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.toSOAPMessage());
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

        //current command should not be null
        checkCurrentCommandNotNull();
        //current command should not be non-blocking
        checkCurrentCommandBlocking();

        //send cached result
        IJobRequest request = getCachedRequest();
        if(request != null){
            return request;
        }

        //if current job is waiting request, just return null to let job continue in running to deal other request
        if(isWaitingRequest(currentCommand)) {
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") is waiting request," +
                    "skip this response:" + (message == null? "empty message":message.getMessageName()));
            return null;
        }

        //let current command handle response
        IWaitResponseCommand waitResponseCommand = (IWaitResponseCommand)currentCommand;
        boolean bHandled = handleCommandWithResponse(waitResponseCommand, message, context);
        if(!bHandled){
            //response not handled by current command, return null to skip for future response
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") skip this response:" +
                    (message == null? "empty message":message.getMessageName()));
            return null;
        }
        //response has been handled, pop next command and run to get some request
        currentCommand = commandQueue.pop();
        return executeTillBlockingOrNoCommand(context);
    }

    public void continueRunWithRequest(ITR069MessageContext context, TR069Message message) throws JobException {
        //job can not be finished
        checkJobNotFinished();
        //job can not be ready
        checkJobNotReady();

        //current command should not be null
        checkCurrentCommandNotNull();
        //current command should not be non-blocking
        checkCurrentCommandBlocking();

        //if current job is waiting response, just return to let job continue in running to deal other response
        if(isWaitingResponse(currentCommand)) {
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") is waiting response," +
                    "skip this request:" + (message == null? "empty message":message.getMessageName()));
            return;
        }

        //let current command handle request
        IWaitRequestCommand waitRequestCommand = (IWaitRequestCommand)currentCommand;
        boolean bHandled = handleCommandWithRequest(waitRequestCommand, message, context);
        if(!bHandled){
            //request not handled by current command, return to skip for future request
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") skip this response:" +
                    (message == null? "empty message":message.getMessageName()));
            return;
        }
        //request has been handled, pop next command and run to get some request
        currentCommand = commandQueue.pop();
        IJobRequest jobRequest = executeTillBlockingOrNoCommand(context);
        //cache the job Request if it is not null, then return to let current command handle future request or response
        if(jobRequest !=null){
            cachedRequest = jobRequest;
            log.debug("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") begin with request:" +
                    message.getMessageName() + ", it cached request message:" + jobRequest.toSOAPMessage());
        }
    }

    public void failOnError(Exception exp) {
        setStatus(STATUS_FAILED);
        log.error("job:"+ jobID + " has failed for exception", exp);
    }

    private void fail(ITR069MessageContext context) {
        setStatus(STATUS_FAILED);
        log.info("job:" + jobID + " failed");
    }

    private void complete(ITR069MessageContext context) {
        setStatus(STATUS_COMPLETE);
        log.info("job:" + jobID + " completed");
        System.out.println("Job result:"+ symbolTable.get("test"));
    }


    //return null means all command has finished execution(current command is null) or there is some command waiting request (current command is not null)
    //return not null means there is some command waiting response (current command is not null)
    private IJobRequest executeTillBlockingOrNoCommand(ITR069MessageContext context) throws JobException{
        IJobRequest request = null;
        boolean bContinue = true;
        while(bContinue){
            //no command in queue, job completed
            if(currentCommand == null){
                log.info("no command in queue, complete job:" + jobID);
                complete(context);
                break;
            }
            try{
                CommandContext cmdContext = new CommandContext(symbolTable);
                //execute command to get result
                request = currentCommand.execute(cmdContext);
                if(isBlockingCommand(currentCommand)){
                    //command is waiting request or response, then return
                    bContinue = false;
                }else{
                    //command has been executed and not waiting request/response, then get next command to execute
                    currentCommand = commandQueue.pop();
                }
            }catch(CommandNormalErrorException cneExp){
                log.warn("(job:" + jobID + ",command:" + currentCommand.getCommandID() +") command execute failed, but can go on next command", cneExp);
                currentCommand = commandQueue.pop();
            }catch(CommandFatalErrorException cfeExp){
                log.warn("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") command execute failed with fatal error", cfeExp);
                fail(context);
                throw new JobException("command execute with fatal error, makes job failed", cfeExp);
            }
        }
        return request;
    }

    private void checkJobNotFinished() throws JobException{
        if(isFinished()){
            log.error("(job:" + jobID + ",command:" + (currentCommand == null?"null":currentCommand.getCommandID()) + "): job has already finished");
            throw new JobException("job has already finished");
        }
    }

    private void checkJobNotReady() throws JobException{
        if(isReady()){
            log.error("(job:" + jobID + ",command:" + (currentCommand == null?"null":currentCommand.getCommandID()) + "): job has not begun");
            throw new JobException("job has not begun to run");
        }
    }

    private void checkJobNotRunning() throws JobException{
        if(isRunning()){
            log.error("(job:" + jobID + ",command:" + (currentCommand == null?"null":currentCommand.getCommandID()) + "): job is still running");
            throw new JobException("job is running");
        }
    }

    private void checkCurrentCommandNotNull() throws JobException{
        if(currentCommand == null){
            log.error("job:" + jobID + " should not be null");
            throw new JobException("current command is null");
        }
    }

    private void checkCurrentCommandBlocking() throws JobException{
        //check if current command is blocking
        if(!isBlockingCommand(currentCommand)) {
            log.error("(job:" + jobID + ",command:" + currentCommand.getCommandID() + ") is not blocking command to handle response");
            throw new JobException("current command is not blocking");
        }
    }

    private IJobRequest getCachedRequest(){
        if(cachedRequest != null){
            log.debug("(job:" + jobID + ",command:" + (currentCommand == null?"null":currentCommand.getCommandID()) +"): has cached request: " + cachedRequest.toSOAPMessage());
            IJobRequest result = cachedRequest;
            cachedRequest = null;
            return result;
        }
        return null;
    }

    private boolean isWaitingResponse(ICommand command) {
        if(command != null){
            if(command instanceof IWaitResponseCommand ){
                log.debug("(job:" + jobID + ",command:" + command.getCommandID() + "): command is waiting response");
                return true;
            }
        }
        return false;
    }

    private boolean isWaitingRequest(ICommand command) {
        if(command != null){
            if(command instanceof IWaitRequestCommand ){
                log.debug("(job:" + jobID + ",command:" + command.getCommandID() + "): command is waiting request");
                return true;
            }
        }
        return false;
    }

    private boolean isBlockingCommand(ICommand command) {
        if(command != null){
            if(command instanceof IWaitResponseCommand || command instanceof IWaitRequestCommand){
                log.debug("(job:" + jobID + ",command:" + command.getCommandID() + "): command is blocking");
                return true;
            }
        }
        return false;
    }

    private boolean handleCommandWithResponse(IWaitResponseCommand command, TR069Message response, ITR069MessageContext context)
            throws JobException{
        CommandContext cmdContext = new CommandContext(symbolTable);
        try{
            return command.handleResponse(cmdContext, response);
        }catch(CommandNormalErrorException cneExp){
            log.warn("(Job:" + jobID + ",Command:" + command.getCommandID() + "): current command handle response:" +
                    (response == null? "empty message":response.getMessageName()) + " failed, but can go on next command", cneExp);
            return true;
        }catch(CommandFatalErrorException cfeExp){
            log.warn("(Job:" + jobID + ",Command:" + command.getCommandID() + "): current command handle response:" +
                    (response == null? "empty message":response.getMessageName()) + " failed with fatal error, makes job failed", cfeExp);
            fail(context);
            throw new JobException("command execute with fatal error, makes job failed", cfeExp);
        }
    }

    private boolean handleCommandWithRequest(IWaitRequestCommand command, TR069Message request, ITR069MessageContext context)
            throws JobException{
        CommandContext cmdContext = new CommandContext(symbolTable);
        try{
            return command.handleRequest(cmdContext, request);
        }catch(CommandNormalErrorException cneExp){
            log.warn("(Job:" + jobID + ",Command:" + command.getCommandID() + "): current command handle request:" +
                    (request == null? "empty message":request.getMessageName()) + " failed, but can go on next command", cneExp);
            return true;
        }catch(CommandFatalErrorException cfeExp){
            log.warn("(Job:" + jobID + ",Command:" + command.getCommandID() + "): current command handle request:" +
                    (request == null? "empty message":request.getMessageName()) + " failed with fatal error, makes job failed", cfeExp);
            fail(context);
            throw new JobException("command execute with fatal error, makes job failed", cfeExp);
        }
    }

}
