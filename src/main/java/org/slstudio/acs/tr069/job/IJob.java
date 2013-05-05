package org.slstudio.acs.tr069.job;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:14
 */
public interface IJob {
    public String getDeviceKey();

    public String getJobID();

    public IJobRequest beginRun(ITR069MessageContext context) throws JobException;

    public void beginRunWithRequest(ITR069MessageContext context, TR069Message request) throws JobException;

    public IJobRequest continueRun(ITR069MessageContext context) throws JobException;

    public IJobRequest continueRunWithResponse(ITR069MessageContext context, TR069Message response) throws JobException;

    public void continueRunWithRequest(ITR069MessageContext context, TR069Message request) throws JobException;

    public void failOnError(Exception exp);

    public boolean isReady();

    public boolean isRunning();

    public boolean isFinished();


}
