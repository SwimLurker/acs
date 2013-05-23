package org.slstudio.acs.hms.job;

import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.bean.DeviceJobResultBean;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.resulthandler.IJobResultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-16
 * Time: ÉÏÎç1:49
 */
public class MessagingJobResultHandler implements IJobResultHandler {
    private IMessageSender sender = null;

    public MessagingJobResultHandler(IMessageSender sender) {
        this.sender = sender;
    }

    public IMessageSender getSender() {
        return sender;
    }

    public void setSender(IMessageSender sender) {
        this.sender = sender;
    }

    public void onFailed(IDeviceJob job) {
        DeviceJobResultBean result = new DeviceJobResultBean();
        result.setJobID(job.getJobID());
        result.setJobName(job.getJobName());
        result.setErrorCode(job.getErrorCode());
        result.setErrorMsg(job.getErrorMsg());
        try {
            sender.sendMessage(result);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void onSucceed(IDeviceJob job) {
        DeviceJobResultBean result = new DeviceJobResultBean();
        result.setJobID(job.getJobID());
        result.setJobName(job.getJobName());
        result.setJobResult(job.getResult());
        try {
            sender.sendMessage(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
