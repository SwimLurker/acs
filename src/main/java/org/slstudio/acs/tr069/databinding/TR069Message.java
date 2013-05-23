package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.soap.SOAPUtil;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-12
 * Time: ÏÂÎç3:10
 */
public abstract class TR069Message {
    private String messageID = null;
    private Boolean holdRequests = null;
    private Boolean noMoreRequests = null;
    private UnsignedInt sessionTimeout = null;
    private Map<String, Object> headers = new HashMap<String, Object>();

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public Boolean getHoldRequests() {
        return holdRequests;
    }

    public void setHoldRequests(Boolean holdRequests) {
        this.holdRequests = holdRequests;
    }

    public Boolean getNoMoreRequests() {
        return noMoreRequests;
    }

    public void setNoMoreRequests(Boolean noMoreRequests) {
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

    public String toSOAPString() {
        StringBuilder result = new StringBuilder();
        SOAPUtil.fillSOAPMessageHeader(getMessageID(), getHoldRequests(), getNoMoreRequests(), getSessionTimeout(), getHeaders(), result);
        result.append(toTR069SOAPString());
        SOAPUtil.fillSOAPMessageTailer(result);
        return result.toString();
    }

    public static void populateHeaderValues(TR069Message message, SOAPHeader header){
        if (header != null) {
            Iterator idIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_ID));
            while (idIt.hasNext()) {
                message.setMessageID(((OMElement) idIt.next()).getText());
            }
            Iterator hrIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_HOLDREQUESTS));
            while (hrIt.hasNext()) {
                message.setHoldRequests(ConverterUtil.convertToBoolean(((OMElement) hrIt.next()).getText()));
            }
            Iterator nmrIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_NOMOREREQUESTS));
            while (nmrIt.hasNext()) {
                message.setHoldRequests(ConverterUtil.convertToBoolean(((OMElement) nmrIt.next()).getText()));
            }
            Iterator stIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_SESSIONTIMEOUT));
            while (stIt.hasNext()) {
                message.setSessionTimeout(ConverterUtil.convertToUnsignedInt(((OMElement) stIt.next()).getText()));
            }
        }
    }

    @JsonIgnore
    public abstract String getMessageName();

    protected  abstract String toTR069SOAPString();


}
