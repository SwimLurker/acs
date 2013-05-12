package org.slstudio.acs.tr069.databinding;

import org.apache.axis2.databinding.types.UnsignedInt;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-12
 * Time: ÏÂÎç3:10
 */
public abstract class TR069Message {
    private String messageID = null;
    private boolean holdRequests = false;
    private boolean noMoreRequests = false;
    private UnsignedInt sessionTimeout = null;
    private Map<String, Object> headers = null;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public boolean isHoldRequests() {
        return holdRequests;
    }

    public void setHoldRequests(boolean holdRequests) {
        this.holdRequests = holdRequests;
    }

    public boolean isNoMoreRequests() {
        return noMoreRequests;
    }

    public void setNoMoreRequests(boolean noMoreRequests) {
        this.noMoreRequests = noMoreRequests;
    }

    public UnsignedInt getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(UnsignedInt sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public abstract String getMessageName();
}
