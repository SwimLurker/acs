package org.slstudio.acs.tr069.job;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.job.resulthandler.IJobResultHandler;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

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

    public void addResultHandler(IJobResultHandler resultHandler);

    public void removeResultHandler(IJobResultHandler resultHandler);

    public List<IJobResultHandler> getResultHandlerList();

    public TR069Message beginRun(ITR069MessageContext context) throws JobException;

    public void beginRunWithRequest(ITR069MessageContext context, TR069Message request) throws JobException;

    public TR069Message continueRun(ITR069MessageContext context) throws JobException;

    public TR069Message continueRunWithResponse(ITR069MessageContext context, TR069Message response) throws JobException;

    public void continueRunWithRequest(ITR069MessageContext context, TR069Message request) throws JobException;

    public void failOnException(Exception exp);

    public void failOnTimeout(boolean bWaitingTimeout);

    public boolean isReady();

    public boolean isRunning();

    public boolean isFinished();

    public List<IInstruction> getInstructions();

    public Map<String, Object> getSymbolTable();
}
