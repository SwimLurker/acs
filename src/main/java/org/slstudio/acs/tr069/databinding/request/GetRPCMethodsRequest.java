package org.slstudio.acs.tr069.databinding.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.response.GetRPCMethodsResponse;
import org.slstudio.acs.tr069.exception.DataBindingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:27
 */
public class GetRPCMethodsRequest extends TR069Message {
    @Override
    public String getMessageName() {
        return TR069Constants.GETRPCMETHODS_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static GetRPCMethodsRequest fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        GetRPCMethodsRequest gmr = new GetRPCMethodsRequest();
        populateHeaderValues(gmr, envelope.getHeader());
        return gmr;
    }

}