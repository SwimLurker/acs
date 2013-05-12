package org.slstudio.acs.tr069.messagedealer.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.engine.TR069ProtocolEngine;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.messagedealer.AbstractRequestDealer;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.util.DataBindingUtil;

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
        return DataBindingUtil.toGetRPCMethodsRequest(envelope);
    }

    @Override
    protected String getResponseString(ITR069MessageContext context, TR069Message request) {
        String requestID = request.getMessageID();
        TR069ProtocolEngine engine = context.getTR069SessionContext().getTR069Engine();
        List<String> rpcMethodList = engine.getTr069Spec().getRpcMethodList();
        return getGetRPCMethodsResponse(requestID, rpcMethodList);
    }

    private String getGetRPCMethodsResponse(String id, List<String> methodList){
        StringBuilder result=new StringBuilder();
        result.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"");
        result.append(TR069Constants.SOAP_NAMESPACE);
        result.append("\" SOAP-ENV:encodingStyle=\"");
        result.append(TR069Constants.SOAP_ENCODING);
        result.append("\" xmlns:cwmp=\"");
        result.append(TR069Constants.TR069_NAMESPACE);
        result.append("\">");
        result.append("<SOAP-ENV:Header>");
        if(id!=null){
            result.append("<cwmp:ID SOAP-ENV:mustUnderstand=\"1\">");
            result.append(id);
            result.append("</cwmp:ID>");
        }
        result.append("</SOAP-ENV:Header>");
        result.append("<SOAP-ENV:Body>");
        result.append("<cwmp:");
        result.append(TR069Constants.GETRPCMETHODS_MESSAGERESPONSE);
        result.append(">");
        result.append("<MethodList SOAP-ENV:arrayType=\"xsd:string[").append(methodList.size()).append("]\">");
        for(String methodName : methodList){
            result.append("<string>").append(methodName).append("</string>");
        }
        result.append("</MethodList>");
        result.append("</cwmp:");
        result.append(TR069Constants.GETRPCMETHODS_MESSAGERESPONSE);
        result.append(">");
        result.append("</SOAP-ENV:Body>");
        result.append("</SOAP-ENV:Envelope>");

        return result.toString();
    }

}
