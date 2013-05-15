package org.slstudio.acs.tr069.job.result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.job.IDeviceJob;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç2:56
 */
public class DebugJobResultHandler implements IJobResultHandler {
    private static final Log log = LogFactory.getLog(DebugJobResultHandler.class);

    public void onFailed(IDeviceJob job) {
        Object result = job.getResult();
        int errorCode = job.getErrorCode();
        String errorMsg = job.getErrorMsg();
        log.debug("Job(" + job.getJobID() +") for Device(" + job.getDeviceKey() + ") failed with errorCode(" + errorCode +"),errorMsg(" + errorMsg +"), result:" + result);
    }

    public void onSucceed(IDeviceJob job) {
        Object result = job.getResult();
        log.debug("Job(" + job.getJobID() +") for Device(" + job.getDeviceKey() + ") completed with result:" + result);
    }
}
