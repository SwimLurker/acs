package org.slstudio.acs.tr069.messagedealer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.fault.FaultUtil;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.request.IJobRequest;
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

        IJobRequest request = null;
        //find related job, first search system job then user job
        IDeviceJob currentJob = findJob(deviceKey, jobID);
        if(currentJob == null){
            // no job is running or can not find job for some reason, then just return null
            log.debug("can not find job:" + jobID + ", for device:" + deviceKey + " when handle response:" + responseID);
            return null;
        }
        if(currentJob.isRunning()){
             request = handleRunningJob(currentJob, context, response, responseID);
        }else if(currentJob.isReady()){
            //impossible, should not happened, just return null to let further message deal
            log.error("job:" + jobID + "should not be ready status for device:" + deviceKey + " when handle response:" + responseID);
        }else if(currentJob.isFinished()){
            //impossible, should not happened, just return null to let further message deal
            log.error("job:" + jobID + "should not be finished status for device:" + deviceKey + " when handle response:" + responseID);
            getJobManager().removeJob(currentJob);
        }
        return formatRequest(context, response, request);
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

    private IDeviceJob findJob(String deviceKey, String jobID) {
        if(jobID == null || deviceKey == null){
            return null;
        }
        IDeviceJob result = null;
        result = getJobManager().findSystemJob(deviceKey, jobID);
        if(result == null){
            result = getJobManager().findUserJob(deviceKey, jobID);
        }
        return result;
    }

    private IJobRequest handleRunningJob(IDeviceJob currentJob, ITR069MessageContext context, TR069Message response, String responseID)
            throws TR069Fault{
        IJobRequest request = null;
        try{
            log.debug("begin running job:" + currentJob.getJobID() + " with response:" + responseID);
            request = currentJob.continueRunWithResponse(context, response);
            if(currentJob.isFinished()){
                log.debug("after handle response:" + responseID +", job:"+ currentJob.getJobID() + " has finished");
                getJobManager().removeJob(currentJob);
            }
            log.debug("after handle response:" + responseID +"for job:"+ currentJob.getJobID() + ", get request:" + (request == null?"null":request.getTr069Request().toSOAPString()));
        }catch (Exception exp){
            log.error("when handle response:" + responseID + ",job:" + currentJob.getJobID() + " failed for execution",exp);
            currentJob.failOnException(exp);
            getJobManager().removeJob(currentJob);
            throw new TR069Fault(true,
                    TR069Constants.SERVER_FAULT_INTERNAL_ERROR,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_INTERNAL_ERROR),
                    responseID);
        }
        return request;
    }

    //method subclass can override to format request
    protected TR069Message formatRequest(ITR069MessageContext context, TR069Message response, IJobRequest request){
        return request == null? null: request.getTr069Request();
    }

}
