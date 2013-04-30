package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.CheckSessionException;
import org.slstudio.acs.tr069.fault.FaultUtil;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.session.context.ITR069SessionContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.soap.SOAPUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÉÏÎç1:03
 */
public class CheckSessionPipeline extends AbstractTR069Pipeline {
    private static final Log log = LogFactory.getLog(CheckSessionPipeline.class);

    @Override
    protected void process(ITR069MessageContext context) throws PipelineException {
        List<SOAPMessage> messages = context.getSoapMessageList();
        if(messages == null){
            throw new CheckSessionException("Message list can't be null");
        }
        ITR069SessionContext sessionContext = context.getTR069SessionContext();
        if(sessionContext == null){
            throw new CheckSessionException("Session context can't be null");
        }

        //if session has not been checked, then check it
        if(sessionContext.getStatus() == ACSConstants.SESSION_STATUS_CREATED){
            if(messages.size()==0){
                //empty envelope
                throw new CheckSessionException("Empty envelope when check inform");
            }else if(messages.size()!=1){
                //multiple envelopes,then deal each messages and save result to TR069_RESPONSE_KEY
                log.error("Inform request has multiple envelope");
                String responseString = dealAllMessagesWithFault(messages);
                if(responseString != null){
                    context.setResponse(responseString);
                }
            }else {
                //message size is ok, then check if it is inform
                if(isInform(messages.get(0))){
                    sessionContext.setStatus(ACSConstants.SESSION_STATUS_CHECKED);
                }else{
                    String faultResponse = dealMessageWithFault(messages.get(0));
                    context.setResponse(faultResponse);
                }
            }
        }
    }


    //handle all messages with fault
    private String dealAllMessagesWithFault(List<SOAPMessage> messages)
            throws CheckSessionException{
        boolean bHasResponse = false;
        StringBuilder result = new StringBuilder();
        for(SOAPMessage message: messages){
            //first handle messages one by one
            String faultResponse = dealMessageWithFault(message);
            //then collect response
            if(faultResponse != null){
                result.append(faultResponse);
                bHasResponse = true;
            }
        }
        return bHasResponse?result.toString():null;
    }

    //check if the message is an inform request
    private boolean isInform(SOAPMessage message) throws CheckSessionException{
        SOAPEnvelope envelope = message.getEnvelope();
        if(envelope == null){
            throw new CheckSessionException("Not Inform request envelope is null");
        }
        String commandName = SOAPUtil.getCommandName(envelope);
        if(commandName == null){
            throw new CheckSessionException("Not Inform request,command name is null");
        }
        String cwmpID = SOAPUtil.getIDFromHeader(envelope);
        if(cwmpID == null){
            throw new CheckSessionException("Not Inform request,command id is null");
        }
        return TR069Constants.INFORM_MESSAGE.equals(commandName);
    }

    // handle message with fault
    // for request, set response with fault; for response, just set it has been dealed
    // if some message is invalid ,ex: command name is null or command id is null, just throw exception
    private String dealMessageWithFault(SOAPMessage message) throws CheckSessionException{
        SOAPEnvelope envelope = message.getEnvelope();
        if(envelope == null){
            throw new CheckSessionException("Some soap envelope is null in the messages when check session");
        }
        String commandName = SOAPUtil.getCommandName(envelope);
        if(commandName == null){
            throw new CheckSessionException("Some message command name is null in the messages when check session");
        }
        String commandID = SOAPUtil.getIDFromHeader(envelope);
        if(commandID == null){
            throw new CheckSessionException("Some message command id is null in the messages when check session");
        }

        message.setDealed(true);
        if(SOAPUtil.isRequest(commandName)){
            String fault=new TR069Fault(false,
                    TR069Constants.SERVER_FAULT_NEED_INFORM,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_NEED_INFORM),
                    commandID).toFault();
            message.addResponse(fault);
            return fault;
        }else{
            return null;
        }
    }

}
