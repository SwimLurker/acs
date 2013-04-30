package org.slstudio.acs.tr069.messagedealer.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.messagedealer.ITR069MethodDealer;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.soap.SOAPUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÏÂÎç12:46
 */
public abstract class AbstractRequestDealer implements ITR069MethodDealer{
    private static final Log log = LogFactory.getLog(AbstractRequestDealer.class);

    public void deal(ITR069MessageContext context, SOAPMessage message) {
        SOAPEnvelope envelope=message.getEnvelope();
        try{
            String response=dealRequest(envelope,context);
            message.setDealed(true);
            message.addResponse(response);
        }catch(TR069Fault fault){
            log.error("Deal request meassage failed",fault);
            dealMessageFault(message,fault);
        }

    }

    private void dealMessageFault(SOAPMessage message,TR069Fault fault) {
        SOAPEnvelope envelope=message.getEnvelope();
        if(envelope==null){
            message.setDealed(true);
        }else{
            String commandName= SOAPUtil.getCommandName(envelope);
            if(SOAPUtil.isRequest(commandName)){
                message.setDealed(true);
                message.addResponse(fault.toFault());
            }else{
                message.setDealed(true);
            }
        }
    }
    //TODO:we should return a soap envelope instead of String
    protected abstract String dealRequest(SOAPEnvelope envelope,ITR069MessageContext context) throws TR069Fault ;


}
