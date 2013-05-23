package org.slstudio.acs.hms.messaging.bean;

import org.slstudio.acs.util.JSONUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç2:42
 */
public class DeviceJobBean {
    private String jobID = null;
    private String jobName = null;
    private String deviceKey = null;
    private String jobScript = null;

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getJobScript() {
        return jobScript;
    }

    public void setJobScript(String jobScript) {
        this.jobScript = jobScript;
    }

    @Override
    public String toString(){
        return JSONUtil.toJsonString(this);
    }

    public static void main(String[] args) {
        StringBuilder text = new StringBuilder();
        text.append("SET $a = \"bbb\"").append("\r\n").append("SET $c = $a").append("\r\n").append("RET");
        DeviceJobBean djb = new DeviceJobBean();
        djb.setJobID("1");
        djb.setDeviceKey("d001");
        djb.setJobName("testjob");
        djb.setJobScript(text.toString());
        System.out.println(JSONUtil.toJsonString(djb));
    }
}

