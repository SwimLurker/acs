package org.slstudio.acs.tr069.job;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.queue.IInstructionQueue;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:14
 */
public interface IDeviceJob {
    public String getDeviceKey();

    public void setDeviceKey(String deviceKey);

    public String getJobID();

    public void setJobID(String jobID);

    public String getJobName();

    public void setJobName(String jobName);

    public Date getCreateTime();

    public void setCreateTime(Date createTime);

    public Date getBeginTime();

    public void setBeginTime(Date beginTime);

    public Date getCompleteTime();

    public void setCompleteTime(Date completeTime);

    public long getWaitingTimeout();

    public void setWaitingTimeout(long waitingTimeout);

    public long getRunningTimeout();

    public void setRunningTimeout(long runningTimeout);

    public int getStatus();

    public void setStatus(int status);

    public Object getResult();

    public void setResult(Object result);

    public int getErrorCode();

    public void setErrorCode(int errorCode);

    public String getErrorMsg();

    public void setErrorMsg(String errorMsg);

    public IInstruction getCurrentInstruction();

    public void setCurrentInstruction(IInstruction instruction);

    public TR069Message getCachedRequest();

    public void setCachedRequest(TR069Message request);

    public List<IInstruction> getInstructions();

    public IInstructionQueue getInstructionQueue();

    public void setInstructionQueue(IInstructionQueue queue);

    public Map<String, Object> getSymbolTable();

    public void setSymbolTable(Map<String, Object> symbolTable);

    public boolean isReady();

    public boolean isRunning();

    public boolean isFinished();

}
