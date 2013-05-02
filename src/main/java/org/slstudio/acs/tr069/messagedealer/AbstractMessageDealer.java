package org.slstudio.acs.tr069.messagedealer;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.event.DefaultTR069Event;
import org.slstudio.acs.tr069.event.IRequestEventListener;
import org.slstudio.acs.tr069.event.ITR069Event;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.fault.FaultUtil;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.messagedealer.plugin.IPostDealMessagePlugin;
import org.slstudio.acs.tr069.messagedealer.plugin.IPreDealMessagePlugin;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.soap.SOAPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-2
 * Time: ÉÏÎç2:14
 */
public abstract class AbstractMessageDealer implements ITR069MethodDealer{
    private static final Log log = LogFactory.getLog(AbstractMessageDealer.class);

    private List<IPreDealMessagePlugin> prePlugins = new ArrayList<IPreDealMessagePlugin>();
    private List<IPostDealMessagePlugin> postPlugins = new ArrayList<IPostDealMessagePlugin>();
    private List<IRequestEventListener> listeners = new ArrayList<IRequestEventListener>();

    public List<IPreDealMessagePlugin> getPrePlugins() {
        return prePlugins;
    }

    public void setPrePlugins(List<IPreDealMessagePlugin> prePlugins) {
        this.prePlugins = prePlugins;
    }

    public List<IPostDealMessagePlugin> getPostPlugins() {
        return postPlugins;
    }

    public void setPostPlugins(List<IPostDealMessagePlugin> postPlugins) {
        this.postPlugins = postPlugins;
    }

    public List<IRequestEventListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<IRequestEventListener> listeners) {
        this.listeners = listeners;
    }

    public void deal(ITR069MessageContext context, SOAPMessage message){
        //call plugins before deal request
        callPreRequestPlugins(context, message);

        TR069Message tr069Message = null;
        try{
            tr069Message = convertMessage(message.getEnvelope());
        }catch(TR069Fault fault){
            log.error("convert soap message to tr069 message failed",fault);
            dealMessageWithFault(message, fault);
        }
        if(!message.isDealed()){
            try{
                String response = dealMessage(context, tr069Message);
                message.setDealed(true);
                if(response != null){
                    message.addResponse(response);
                }
            }catch(TR069Fault fault){
                log.error("convert soap message to tr069 message failed",fault);
                dealMessageWithFault(message, fault);
            }
        }

        //call plugins after deal request
        callPostRequestPlugins(context, message);

        //fire event
        fireRequestEvent(context, tr069Message);

    }

    private void dealMessageWithFault(SOAPMessage message, TR069Fault fault) {
        SOAPEnvelope envelope = message.getEnvelope();
        if(envelope == null){
            log.error("envelope is null");
            message.setDealed(true);
        }else{
            String commandName = SOAPUtil.getCommandName(message.getEnvelope());
            if(commandName == null){
                log.error("command name is null");
            }else if(SOAPUtil.isRequest(commandName)){
                message.setDealed(true);
                message.addResponse(fault.toFault());
            }else if(SOAPUtil.isResponse(commandName)){
                message.setDealed(true);
            }
            message.setDealed(true);
        }
    }

    private void callPreRequestPlugins(ITR069MessageContext context, SOAPMessage message) {
        for(IPreDealMessagePlugin plugin : prePlugins) {
            plugin.execute(context, message);
        }
    }

    private TR069Message convertMessage(SOAPEnvelope envelope) throws TR069Fault{
        String requestID = SOAPUtil.getIDFromHeader(envelope);
        TR069Message request = null;
        try{
            request =convertToTR069Message(envelope);
        }catch (TR069Exception e) {
            log.error("binding request error", e);
            throw new TR069Fault(true,
                    TR069Constants.SERVER_FAULT_INVALID_ARGUMENTS,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_INVALID_ARGUMENTS),
                    requestID);
        }
        return request;
    }

    private void callPostRequestPlugins(ITR069MessageContext context, SOAPMessage message){
        for(IPostDealMessagePlugin plugin : postPlugins) {
            plugin.execute(context, message);
        }
    }

    private void fireRequestEvent(ITR069MessageContext context, TR069Message message) {
        for(IRequestEventListener listener: listeners){
            ITR069Event event = createMessageEvent(context, message);
            listener.onRequest(event);
        }
    }

    protected ITR069Event createMessageEvent(ITR069MessageContext context, TR069Message message){
        return new DefaultTR069Event(context, message);
    }

    protected abstract TR069Message convertToTR069Message(SOAPEnvelope envelope) throws TR069Exception;

    protected abstract String dealMessage(ITR069MessageContext context,TR069Message request) throws TR069Fault ;


}

