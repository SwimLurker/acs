package org.slstudio.acs.tr069.messagedealer.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.databinding.response.InformResponse;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.messagedealer.AbstractRequestDealer;
import org.slstudio.acs.tr069.messagedealer.plugin.SaveInformInfoPlugin;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

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
        return InformRequest.fromEnvelope(envelope);
    }

    @Override
    protected TR069Message getResponse(ITR069MessageContext context, TR069Message request) {
        int maxEnvelopes = context.getTR069SessionContext().getMaxReceiveEnvelopeCount();
        String requestID = request.getMessageID();
        InformResponse response = new InformResponse();
        response.setMessageID(requestID);
        response.setMaxEnvelopes(new UnsignedInt(maxEnvelopes));
        return response;
    }

}
