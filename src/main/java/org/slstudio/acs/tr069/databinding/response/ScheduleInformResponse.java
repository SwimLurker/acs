package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:06
 */
public class ScheduleInformResponse extends TR069Message {

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SCHEDULEINFORM_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static ScheduleInformResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        ScheduleInformResponse sir = new ScheduleInformResponse();
        populateHeaderValues(sir, envelope.getHeader());
        return sir;
    }

}