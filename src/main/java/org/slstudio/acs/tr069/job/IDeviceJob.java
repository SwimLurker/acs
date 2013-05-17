package org.slstudio.acs.tr069.job;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.job.result.IJobResultHandler;
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

    public IJobRequest beginRun(ITR069MessageContext context) throws JobException;

    public void beginRunWithRequest(ITR069MessageContext context, TR069Message request) throws JobException;

    public IJobRequest continueRun(ITR069MessageContext context) throws JobException;

    public IJobRequest continueRunWithResponse(ITR069MessageContext context, TR069Message response) throws JobException;

    public void continueRunWithRequest(ITR069MessageContext context, TR069Message request) throws JobException;

    public void failOnException(Exception exp);

    public boolean isReady();

    public boolean isRunning();

    public boolean isFinished();

    public List<IInstruction> getInstructions();

    public Map<String, Object> getSymbolTable();
}
