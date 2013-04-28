package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.CheckSessionException;
import org.slstudio.acs.tr069.exception.TR069Exception;
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
        if(!sessionContext.hasCheckSession()){
            if(messages.size()==0){
                //empty envelope
                dealMessages(messages,context);
                throw new CheckSessionException("Not Inform request or Inform request has no envelope");
            }else if(messages.size()!=1){
                //multiple envelopes,then deal each messages and save result to TR069_RESPONSE_KEY
                dealMessages(messages,context);
                throw new CheckSessionException("Not Inform request or Inform request has multiple envelope");
            }else {
                SOAPMessage message=(SOAPMessage)messages.get(0);
                SOAPEnvelope envelope=message.getEnvelope();
                if(envelope==null){
                    throw new CheckSessionException("Not Inform request");
                }
                String commandName= SOAPUtil.getCommandName(envelope);
                if(commandName==null){
                    throw new CheckSessionException("Not Inform request,command name is null");
                }
                if(!TR069Constants.INFORM_MESSAGE.equals(commandName)){
                    if(SOAPUtil.isRequest(commandName)){
                        message.setDealed(true);
                        String id=SOAPUtil.getIDFromHeader(envelope);
                        String fault=new TR069Fault(false,
                                TR069Constants.SERVER_FAULT_NEED_INFORM,
                                FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_NEED_INFORM),
                                id).toFault();
                        message.addResponse(fault);
                        context.setResponseString(fault);
                    }else{
                        message.setDealed(true);
                    }
                    throw new CheckSessionException("Not Inform request");
                }else{
                    //inform request
                    sessionContext.setHasCheckSession(true);
                }
            }

            }

    }
    private void dealMessages(List<SOAPMessage> messages, ITR069MessageContext context) {
        StringBuffer result=new StringBuffer();
        for(int i=0;i<messages.size();i++){
            SOAPMessage message=(SOAPMessage)messages.get(i);
            SOAPEnvelope envelope=message.getEnvelope();
            if(envelope!=null){
                String commandName= SOAPUtil.getCommandName(envelope);
                if(SOAPUtil.isRequest(commandName)){
                    message.setDealed(true);
                    String id=SOAPUtil.getIDFromHeader(envelope);
                    String fault=new TR069Fault(false,
                            TR069Constants.SERVER_FAULT_NEED_INFORM,
                            FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_NEED_INFORM),
                            id).toFault();
                    message.addResponse(fault);
                    result.append(fault);
                }else{
                    message.setDealed(true);
                }
            }else{
                message.setDealed(true);
            }
        }
        context.setResponseString(result.toString());
    }
}
