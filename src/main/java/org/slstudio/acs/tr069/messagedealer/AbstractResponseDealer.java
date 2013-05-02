package org.slstudio.acs.tr069.messagedealer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.job.IJobManager;
import org.slstudio.acs.tr069.job.ISystemJob;
import org.slstudio.acs.tr069.job.IUserJob;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:11
 */
public abstract class AbstractResponseDealer extends AbstractMessageDealer {
    private static final Log log = LogFactory.getLog(AbstractResponseDealer.class);
    private IJobManager jobManager = null;

    protected String dealMessage(ITR069MessageContext context,TR069Message message) throws TR069Fault {
        //first get responseID
        String responseID = SOAPUtil.getIDFromHeader(message.getEnvelope());
        String deviceKey = getDeviceKey(context.getTR069SessionContext().getInformRequest());
        //then check if it is system job which stored in session/db
        IJobRequest request = null;
        ISystemJob systemJob = jobManager.findSystemJob(deviceKey, responseID);
        if(systemJob != null){
            request = systemJob.handleResponse(context, message);
            if(request == null && systemJob.finished()){
                jobManager.removeJob(systemJob);
            }
        }else{
            IUserJob userJob = jobManager.findUserJob(deviceKey, responseID);
            if(userJob != null){
                request = userJob.handleResponse(context, message);
                if(request == null && userJob.finished()){
                    jobManager.removeJob(userJob);
                }
            }
        }
        return (request == null)?null:request.toString();
    }

    private String getDeviceKey(InformRequest informRequest) {
        return null;
    }

}
