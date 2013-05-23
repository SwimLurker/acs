package org.slstudio.acs.tr069.messagedealer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.job.IDeviceJob;
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
        IDeviceJob currentJob = fetchDeviceJob(deviceKey);

        if(currentJob != null){
            log.debug("when handle request:" + requestID + ", fetch an job:" + currentJob.getJobID());
            handleJob(currentJob, context, request);
        }else{
            // no job is running or can not find job for some reason, then will use default response
            log.debug("can not fetch job for device:" + deviceKey + " when handle request:" + requestID);
        }

        return getResponse(context, request);
    }

    private void handleJob(IDeviceJob job, ITR069MessageContext context, TR069Message request) {
        try{
            if(job.isRunning()){
                // handle running job, call job's continueRunWithRequest job method
                // The continueRunWithRequest method let job continue running (when job's cached request is not null, just sent them)
                // Sometime it will produce job request which cached in that job, which will then be send when message dealer(often the empty message dealer)
                // call that job's continueRun or continueRunWithRequest or continueRunWithResponse method.
                log.debug("job:" + job.getJobID() + "for device:" + job.getDeviceKey() + " continue run with request:" + request.getMessageID());
                job.continueRunWithRequest(context, request);
            }else if(job.isReady()){
                // handle ready job, call job's beginRunWithRequest job method
                // The beginRunWithRequest method let job begin running
                // Sometime it will produce job request which cached in that job, which will then be send when message dealer(often the empty message dealer)
                // call that job's continueRun or continueRunWithRequest or continueRunWithResponse method.
                log.debug("job:" + job.getJobID() + "for device:" + job.getDeviceKey() + " begin run with request:" + request.getMessageID());
                job.beginRunWithRequest(context, request);
            }else if(job.isFinished()){
                //impossible, should not happen
                log.error("job:" + job.getJobID() + "should not be finished status for device:" + job.getDeviceKey() + " when handle request:" + request.getMessageID());
            }

            if(job.isFinished()){
                log.debug("after handle request:" + request.getMessageID() +", job:"+ job.getJobID() + " for device:" + job.getDeviceKey() + " has finished");
                getJobManager().removeJob(job);
            }
        }catch(Exception exp){
            log.error("when handle request,job:" + job.getJobID() + " failed for execution",exp);
            job.failOnException(exp);
            getJobManager().removeJob(job);
        }
    }

    //fetch a job for give device key
    private IDeviceJob fetchDeviceJob(String deviceKey) {
        if(deviceKey == null){
            return null;
        }
        IDeviceJob result = null;
        result = getJobManager().fetchSystemJob(deviceKey);
        if(result == null){
            result = getJobManager().fetchUserJob(deviceKey);
        }
        return result;
    }


    //abstract method need subclass to format request's response with job response
    protected abstract TR069Message getResponse(ITR069MessageContext context, TR069Message request);
}
