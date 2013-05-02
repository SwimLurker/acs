package org.slstudio.acs.tr069.job;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ2:14
 */
public interface IJob {
    public String getDeviceKey();
    public String getJobID();
    public IJobRequest handleResponse(ITR069MessageContext context, TR069Message response);
    public IJobRequest execute(ITR069MessageContext context);
    public boolean finished();
}
