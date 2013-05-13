package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:52
 */
public class FactoryResetResponse extends TR069Message {

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_FACTORYRESET_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static FactoryResetResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        FactoryResetResponse frr = new FactoryResetResponse();
        populateHeaderValues(frr, envelope.getHeader());
        return frr;
    }
}