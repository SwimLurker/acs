package org.slstudio.acs.tr069.job.request;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-6
 * Time: ионГ1:15
 */
public class DefaultJobRequest implements IJobRequest {
    private String requestStr = null;

    public DefaultJobRequest(String requestStr) {
        this.requestStr = requestStr;
    }

    public String getRequestStr() {
        return requestStr;
    }

    public void setRequestStr(String requestStr) {
        this.requestStr = requestStr;
    }

    public String toSOAPMessage() {
        return requestStr;
    }
}
