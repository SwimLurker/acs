package org.slstudio.acs.hms.messaging.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.job.MessagingJobResultHandler;
import org.slstudio.acs.hms.messaging.bean.DeviceJobBean;
import org.slstudio.acs.hms.messaging.bean.DeviceJobResultBean;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.slstudio.acs.tr069.exception.ParseScriptException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.job.UserDeviceJob;
import org.slstudio.acs.tr069.job.manager.IJobManager;
import org.slstudio.acs.tr069.job.result.DebugJobResultHandler;
import org.slstudio.acs.tr069.script.IScriptParser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç2:45
 */
public class DeviceJobMessageHandler implements IMessageHandler{
    private static final Log log = LogFactory.getLog(DeviceJobMessageHandler.class);

    IScriptParser scriptParser = null;
    IJobManager jobManager = null;
    IDeviceManager deviceManager = null;
    IMessageSender jobResultSender = null;


    public IScriptParser getScriptParser() {
        return scriptParser;
    }

    public void setScriptParser(IScriptParser scriptParser) {
        this.scriptParser = scriptParser;
    }

    public IJobManager getJobManager() {
        return jobManager;
    }

    public void setJobManager(IJobManager jobManager) {
        this.jobManager = jobManager;
    }

    public IDeviceManager getDeviceManager() {
        return deviceManager;
    }

    public void setDeviceManager(IDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public IMessageSender getJobResultSender() {
        return jobResultSender;
    }

    public void setJobResultSender(IMessageSender jobResultSender) {
        this.jobResultSender = jobResultSender;
    }

    public void handle(Object message) throws MessagingException {
        DeviceJobBean jobBean = (DeviceJobBean)message;
        if(jobBean == null){
            throw new MessagingException("device job message error: null message");
        }
        String deviceKey =  jobBean.getDeviceKey();
        DeviceInfo device = deviceManager.findDevice(deviceKey);
        if(device == null){
            sendJobFailResult(jobBean, -1, "unknown device");
            return;
        }

        UserDeviceJob job = new UserDeviceJob(deviceKey, jobBean.getJobID());

        job.setJobID(jobBean.getJobID());
        job.setJobName(jobBean.getJobName());
        job.setCreateTime(new Date());

        List<IInstruction> instructionList = null;
        try{
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("jobID", jobBean.getJobID());
            context.put("jobName", jobBean.getJobName());
            context.put("deviceKey", jobBean.getDeviceKey());
            instructionList = scriptParser.newInstance().parse(jobBean.getJobScript(), context);
        }catch(ParseScriptException pse){
            sendJobFailResult(jobBean, -2, "can not understanding job instruction");
            pse.printStackTrace();
            return;
        }
        for(IInstruction instruction: instructionList){
            job.getInstructionQueue().push(instruction);
        }

        job.addResultHandler(new MessagingJobResultHandler(jobResultSender));
        job.addResultHandler(new DebugJobResultHandler());
        jobManager.addUserJob(job);
        log.debug("handle job message successfully");
    }

    private void sendJobFailResult(DeviceJobBean jobBean, int errorCode, String errorMsg) throws MessagingException{
        DeviceJobResultBean result = new DeviceJobResultBean(jobBean);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        jobResultSender.sendMessage(result);
    }
}