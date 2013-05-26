package org.slstudio.acs.tr069.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.queue.DefaultInstructionQueue;
import org.slstudio.acs.tr069.instruction.queue.IInstructionQueue;
import org.slstudio.acs.util.CustomDateDeserializer;
import org.slstudio.acs.util.CustomDateSerializer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ÉÏÎç1:52
 */
public class DefaultDeviceJob implements IDeviceJob {

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

    private long waitingTimeout = -1;
    private long runningTimeout = -1;
    private int status = DeviceJobConstants.STATUS_READY;

    private IInstructionQueue instructionQueue = new DefaultInstructionQueue();
    private IInstruction currentInstruction = null;
    private Map<String, Object> symbolTable = new HashMap<String, Object>();
    private TR069Message cachedRequest = null;


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

    public long getWaitingTimeout() {
        return waitingTimeout;
    }

    public void setWaitingTimeout(long waitingTimeout) {
        this.waitingTimeout = waitingTimeout;
    }

    public long getRunningTimeout() {
        return runningTimeout;
    }

    public void setRunningTimeout(long runningTimeout) {
        this.runningTimeout = runningTimeout;
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
        return symbolTable.get(InstructionConstants.SYMBOLNAME_RETURNVALUE);
    }

    public void setResult(Object result){
        symbolTable.put(InstructionConstants.SYMBOLNAME_RETURNVALUE, result);
    }

    public int getErrorCode(){
        Object result = symbolTable.get(InstructionConstants.SYMBOLNAME_ERRORCODE);
        if(result == null){
            return DeviceJobConstants.ERRORCODE_NOERROR;
        }else{
            return (Integer)result;
        }
    }

    public void setErrorCode(int errorCode){
        symbolTable.put(InstructionConstants.SYMBOLNAME_ERRORCODE, errorCode);
    }

    public String getErrorMsg(){
        return (String)symbolTable.get(InstructionConstants.SYMBOLNAME_ERRORMSG);
    }

    public void setErrorMsg(String errorMsg){
        symbolTable.put(InstructionConstants.SYMBOLNAME_ERRORMSG, errorMsg);
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

    public TR069Message getCachedRequest(){
        return cachedRequest;
    }

    public void setCachedRequest(TR069Message cachedRequest) {
        this.cachedRequest = cachedRequest;
    }

    @JsonIgnore
    public boolean isReady() {
        return status == DeviceJobConstants.STATUS_READY;
    }

    @JsonIgnore
    public boolean isRunning() {
        return status == DeviceJobConstants.STATUS_RUNNING;
    }

    @JsonIgnore
    public boolean isFinished() {
        return status == DeviceJobConstants.STATUS_COMPLETE || status == DeviceJobConstants.STATUS_FAILED;
    }
}
