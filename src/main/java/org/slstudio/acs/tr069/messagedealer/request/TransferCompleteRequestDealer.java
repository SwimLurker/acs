package org.slstudio.acs.tr069.messagedealer.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.TransferCompleteRequest;
import org.slstudio.acs.tr069.databinding.response.TransferCompleteResponse;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.messagedealer.AbstractRequestDealer;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç9:27
 */
public class TransferCompleteRequestDealer extends AbstractRequestDealer {
    @Override
    protected TR069Message convertToTR069Message(SOAPEnvelope envelope) throws TR069Exception {
        return TransferCompleteRequest.fromEnvelope(envelope);
    }

    @Override
    protected TR069Message getResponse(ITR069MessageContext context, TR069Message request) {
        String requestID = request.getMessageID();
        TransferCompleteResponse response = new TransferCompleteResponse();
        response.setMessageID(requestID);
        return response;
    }

}
