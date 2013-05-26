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
 * Date: 13-5-1
 * Time: ÉÏÎç2:11
 */
public abstract class AbstractResponseDealer extends AbstractMessageDealer {
    private static final Log log = LogFactory.getLog(AbstractResponseDealer.class);

    protected TR069Message dealMessage(ITR069MessageContext context,TR069Message response) throws TR069Fault {
        //get responseID
        String responseID = response.getMessageID();

        //get device key
        String deviceKey = getDeviceKey(context.getTR069SessionContext());

        //get job id from response id
        String jobID = getJobIDByResponseID(responseID);

        IDeviceJob currentJob = findRunningJob(deviceKey);
        if(currentJob == null) {
            log.debug("can not find running job for device:" + deviceKey + " when handle response:" + responseID);
            return null;
        }

        return handleJob(currentJob,context, response);
    }

    private TR069Message handleJob(IDeviceJob job, ITR069MessageContext context, TR069Message response) {
        TR069Message result = null;
        try{
            if(job.isRunning()){
                log.debug("job:" + job.getJobID() + "for device:" + job.getDeviceKey() + " continue run with response:" + response.getMessageID());
                result = getJobRunner().continueRunWithResponse(job, context, response);
            }else if(job.isReady()){
                //impossible, should not happened, just return null to let further message deal
                log.error("job:" + job.getJobID() + "should not be ready status for device:" + job.getDeviceKey() + " when handle response:" + response.getMessageID());
            }else if(job.isFinished()){
                //impossible, should not happened, just return null to let further message deal
                log.error("job:" + job + "should not be finished status for device:" + job.getDeviceKey() + " when handle response:" + response.getMessageID());
            }
            if(job.isFinished()){
                log.debug("after handle response:" + response.getMessageID() +", job:"+ job.getJobID() + " for device:" + job.getDeviceKey() + " has finished");
                getJobManager().removeJob(job);
            }
            log.debug("after handle response:" + response.getMessageID() + " for job:"+ job.getJobID() + ", get request:" + (result == null?"null":result.toSOAPString()));
        }catch (Exception exp){
            log.error("when handle response:" + response.getMessageID() + ",job:" + job.getJobID() + " failed for execution",exp);
            getJobRunner().failOnException(job, exp);
            getJobManager().removeJob(job);
        }
        return result;
    }

    //first find running job, the priority is:
    // 1. running system job
    // 2. running user job
    private IDeviceJob findRunningJob(String deviceKey) {
        if(deviceKey == null){
            return null;
        }
        IDeviceJob currentSystemJob = getJobManager().fetchSystemJob(deviceKey);
        IDeviceJob currentUserJob = getJobManager().fetchUserJob(deviceKey);
        if(currentSystemJob == null || currentSystemJob.isReady()){
            if(currentUserJob!=null && currentUserJob.isRunning()){
                //no system job or system is not running
                //current job is running
                return currentUserJob;
            }else{
                //no system job or system is not running
                // no user job or user job is not running
                return null;
            }
        }else{
            //system job is running, no matter user job status
            return currentSystemJob;
        }
    }


    private String getJobIDByResponseID(String responseID) {
        if(responseID == null){
            return null;
        }
        int pos = responseID.indexOf("_");
        if (pos == -1) {
            return null;
        }
        String jobID = responseID.substring(0, pos);
        if (jobID == null) {
            return null;
        }
        return jobID;
    }

}
