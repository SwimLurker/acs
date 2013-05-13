package org.slstudio.acs.tr069.messagedealer.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.GetRPCMethodsRequest;
import org.slstudio.acs.tr069.databinding.response.GetRPCMethodsResponse;
import org.slstudio.acs.tr069.engine.TR069ProtocolEngine;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.messagedealer.AbstractRequestDealer;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç9:26
 */
public class GetRPCMethodsRequestDealer extends AbstractRequestDealer {
    @Override
    protected TR069Message convertToTR069Message(SOAPEnvelope envelope) throws TR069Exception {
        return GetRPCMethodsRequest.fromEnvelope(envelope);
    }

    @Override
    protected TR069Message getResponse(ITR069MessageContext context, TR069Message request) {
        String requestID = request.getMessageID();
        TR069ProtocolEngine engine = context.getTR069SessionContext().getTR069Engine();
        List<String> rpcMethodList = engine.getTr069Spec().getRpcMethodList();

        GetRPCMethodsResponse response = new GetRPCMethodsResponse();
        response.setMessageID(requestID);
        response.setMethodList(rpcMethodList);
        return response;
    }

}
