package org.slstudio.acs.tr069.job.runner;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.JobException;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÉÏÎç11:23
 */
public interface IJobRunner {
    public TR069Message beginRun(IDeviceJob job, ITR069MessageContext context) throws JobException;

    public void beginRunWithRequest(IDeviceJob job, ITR069MessageContext context, TR069Message request) throws JobException;

    public TR069Message continueRun(IDeviceJob job, ITR069MessageContext context) throws JobException;

    public TR069Message continueRunWithResponse(IDeviceJob job, ITR069MessageContext context, TR069Message response) throws JobException;

    public void continueRunWithRequest(IDeviceJob job, ITR069MessageContext context, TR069Message request) throws JobException;

    public void failOnException(IDeviceJob job, Exception exp);

    public void failOnTimeout(IDeviceJob job, boolean bWaitingTimeout);

}
