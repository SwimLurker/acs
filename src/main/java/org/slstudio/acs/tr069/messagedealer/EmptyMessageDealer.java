package org.slstudio.acs.tr069.messagedealer;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.job.IJob;
import org.slstudio.acs.tr069.job.IJobManager;
import org.slstudio.acs.tr069.job.request.IJobRequest;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.session.context.ITR069SessionContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÏÂÎç12:51
 */
public class EmptyMessageDealer extends AbstractMessageDealer {
    private static final Log log = LogFactory.getLog(EmptyMessageDealer.class);
    private IJobManager jobManager = null;

    @Override
    protected TR069Message convertToTR069Message(SOAPEnvelope envelope) throws TR069Exception {
        return null;
    }

    @Override
    protected String dealMessage(ITR069MessageContext context, TR069Message request) throws TR069Fault {
        ITR069SessionContext sessionContext = context.getTR069SessionContext();
        IJobRequest jobRequest = null;
        String deviceKey = getDeviceKey(sessionContext.getInformRequest());
        IJob job = null;
        while((job = jobManager.fetchSystemJob(deviceKey))!= null){
            jobRequest = job.execute(context);
            if(jobRequest != null){
                break;
            }else{
                if(job.finished()){
                    jobManager.removeJob(job);
                }
            }
        }
        if(jobRequest != null){
            while((job = jobManager.fetchUserJob(deviceKey))!= null){
                jobRequest = job.execute(context);
                if(jobRequest != null){
                    break;
                }else{
                    if(job.finished()){
                        jobManager.removeJob(job);
                    }
                }
            }
        }
        if(jobRequest != null){
            return jobRequest.toString();
        }
        return null;
    }

    private String getDeviceKey(InformRequest informRequest) {
        return null;
    }

}
