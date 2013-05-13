package org.slstudio.acs.tr069.messagedealer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.fault.FaultUtil;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.job.IJob;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÏÂÎç12:46
 */
public abstract class AbstractRequestDealer extends AbstractMessageDealer{
    private static final Log log = LogFactory.getLog(AbstractRequestDealer.class);


    @Override
    protected TR069Message dealMessage(ITR069MessageContext context,TR069Message request) throws TR069Fault{
        //first get requestID
        String requestID = request.getMessageID();

        //get device key
        String deviceKey = getDeviceKey(context.getTR069SessionContext());

        //first find a job for that device
        IJob currentJob = fetchDeviceJob(deviceKey);

        if(currentJob != null){
            log.debug("when handle request:" + requestID + ", fetch an job:" + currentJob.getJobID());
            if(currentJob.isRunning()){
                //handle running job
                handleRunningJob(currentJob, context, request, requestID);
            }else if(currentJob.isReady()){
                //handle ready job
                handleReadyJob(currentJob, context, request, requestID);
            }else if(currentJob.isFinished()){
                //impossible, should not happen
                log.error("when handle request:" + requestID + ", fetch an finished job:" + currentJob.getJobID());
                getJobManager().removeJob(currentJob);
            }
        }else{
            // no job is running or can not find job for some reason, then will use default response
            log.debug("can not fetch job for device:" + deviceKey + " when handle request:" + requestID);
        }

        return getResponse(context, request);
    }

    // handle ready job, call job's beginRunWithRequest job method
    // The beginRunWithRequest method let job begin running
    // Sometime it will produce job request which cached in that job, which will then be send when message dealer(often the empty message dealer)
    // call that job's continueRun or continueRunWithRequest or continueRunWithResponse method.
    private void handleReadyJob(IJob currentJob, ITR069MessageContext context, TR069Message request, String requestID)
            throws TR069Fault{
        try{
            log.debug("begin running job:" + currentJob.getJobID() + " with request:" + requestID);
            currentJob.beginRunWithRequest(context, request);
            if(currentJob.isFinished()){
                log.debug("after handle request:" + requestID +", job:"+ currentJob.getJobID() + " has finished");
                getJobManager().removeJob(currentJob);
            }
        }catch(Exception exp){
            log.error("when handle request:" + requestID + ",job:" + currentJob.getJobID() + " failed for execution",exp);
            currentJob.failOnError(exp);
            getJobManager().removeJob(currentJob);
            throw new TR069Fault(true,
                    TR069Constants.SERVER_FAULT_INTERNAL_ERROR,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_INTERNAL_ERROR),
                    requestID);
        }
    }

    // handle running job, call job's continueRunWithRequest job method
    // The continueRunWithRequest method let job continue running (when job's cached request is not null, just sent them)
    // Sometime it will produce job request which cached in that job, which will then be send when message dealer(often the empty message dealer)
    // call that job's continueRun or continueRunWithRequest or continueRunWithResponse method.
    private void handleRunningJob(IJob currentJob, ITR069MessageContext context, TR069Message request, String requestID)
            throws TR069Fault{
        try{
            log.debug("continue running job:" + currentJob.getJobID() + " with request:" + requestID);
            currentJob.continueRunWithRequest(context, request);
            if(currentJob.isFinished()){
                log.debug("after handle request:" + requestID +", job:"+ currentJob.getJobID() + " has finished");
                getJobManager().removeJob(currentJob);
            }
        }catch(Exception exp){
            log.error("when handle request:" + requestID + ",job:" + currentJob.getJobID() + " failed for execution",exp);
            currentJob.failOnError(exp);
            getJobManager().removeJob(currentJob);

            throw new TR069Fault(true,
                    TR069Constants.SERVER_FAULT_INTERNAL_ERROR,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_INTERNAL_ERROR),
                    requestID);
        }
    }

    //fetch a job for give device key
    private IJob fetchDeviceJob(String deviceKey) {
        if(deviceKey == null){
            return null;
        }
        IJob result = null;
        result = getJobManager().fetchSystemJob(deviceKey);
        if(result == null){
            result = getJobManager().fetchUserJob(deviceKey);
        }
        return result;
    }


    //abstract method need subclass to format request's response with job response
    protected abstract TR069Message getResponse(ITR069MessageContext context, TR069Message request);
}
