package org.slstudio.acs.tr069.job.result;

import org.slstudio.acs.tr069.job.IDeviceJob;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ионГ3:07
 */
public interface IJobResultHandler {
    public void onFailed(IDeviceJob job);
    public void onSucceed(IDeviceJob job);
}
