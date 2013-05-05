package org.slstudio.acs.tr069.messagedealer.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.messagedealer.AbstractRequestDealer;
import org.slstudio.acs.tr069.messagedealer.plugin.SaveInformInfoPlugin;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç9:25
 */
public class InformRequestDealer extends AbstractRequestDealer {
    public InformRequestDealer() {
        getPrePlugins().add(new SaveInformInfoPlugin());
    }

    @Override
    protected TR069Message convertToTR069Message(SOAPEnvelope envelope) throws TR069Exception {
        return new InformRequest(envelope);
    }

    @Override
    protected String getResponseString(ITR069MessageContext context, TR069Message request) {
        int maxEnvelopes = context.getTR069SessionContext().getMaxReceiveEnvelopeCount();
        String requestID = SOAPUtil.getIDFromHeader(request.getEnvelope());
        return getInformResponse(requestID,maxEnvelopes);
    }

    private String getInformResponse(String id, int maxEnvelopes) {
        StringBuilder result = new StringBuilder();
        result.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"");
        result.append(TR069Constants.SOAP_NAMESPACE);
        result.append("\" SOAP-ENV:encodingStyle=\"");
        result.append(TR069Constants.SOAP_ENCODING);
        result.append("\" xmlns:cwmp=\"");
        result.append(TR069Constants.TR069_NAMESPACE);
        result.append("\">");
        result.append("<SOAP-ENV:Header>");
        if (id != null) {
            result.append("<cwmp:ID SOAP-ENV:mustUnderstand=\"1\">");
            result.append(id);
            result.append("</cwmp:ID>");
        }
        result.append("</SOAP-ENV:Header>");
        result.append("<SOAP-ENV:Body>");
        result.append("<cwmp:");
        result.append(TR069Constants.INFORM_MESSAGERESPONSE);
        result.append(">");
        result.append("<MaxEnvelopes>");
        result.append(maxEnvelopes);
        result.append("</MaxEnvelopes>");
        result.append("</cwmp:");
        result.append(TR069Constants.INFORM_MESSAGERESPONSE);
        result.append(">");
        result.append("</SOAP-ENV:Body>");
        result.append("</SOAP-ENV:Envelope>");

        return result.toString();
    }

}
