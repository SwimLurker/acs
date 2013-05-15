package org.slstudio.acs.hms.messaging.bean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç2:53
 */
public class DeviceJobResultBean {
    private String jobID = null;
    private String jobName = null;
    private String deviceKey = null;
    private int errorCode = 0;
    private String errorMsg = null;
    private Object jobResult = null;

    public DeviceJobResultBean() {
    }

    public DeviceJobResultBean(DeviceJobBean jobBean) {
        this.jobID = jobBean.getJobID();
        this.jobName = jobBean.getJobName();
        this.deviceKey = jobBean.getDeviceKey();
    }

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

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getJobResult() {
        return jobResult;
    }

    public void setJobResult(Object jobResult) {
        this.jobResult = jobResult;
    }
}
