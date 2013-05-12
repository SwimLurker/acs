package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.dispatcher.ITR069MethodDispatcher;
import org.slstudio.acs.tr069.fault.FaultUtil;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.messagedealer.ITR069MethodDealer;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.soap.SOAPUtil;
import org.slstudio.acs.util.BeanLocator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÉÏÎç10:47
 */
public class DispatchMethodPipeline extends AbstractTR069Pipeline {
    private static final Log log = LogFactory.getLog(DispatchMethodPipeline.class);
    private ITR069MethodDispatcher dispatcher = null;

    public DispatchMethodPipeline(ITR069MethodDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    protected void process(ITR069MessageContext context) throws PipelineException {
        if(context.getSessionContext().getStatus()!= ACSConstants.SESSION_STATUS_CHECKED){
            //not checked session
            return;
        }
        List<SOAPMessage> messages = context.getSoapMessageList();
        int maxReceiveEnvelopeCount = context.getTR069SessionContext().getMaxReceiveEnvelopeCount();
        log.debug("current message can send envelope:" + context.getCanSendEnvelopeCount() );

        StringBuilder resultBuf=new StringBuilder();
        for(int i=0;i<messages.size();i++){
            SOAPMessage message = messages.get(i);
            if(!message.isDealed()){
                String requestID= SOAPUtil.getIDFromHeader(message.getEnvelope());
                if(i >= maxReceiveEnvelopeCount){
                    dealMessageWithFault(message, new TR069Fault(false,
                            TR069Constants.SERVER_FAULT_TOO_MANY_ENVELOPES,
                            FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_TOO_MANY_ENVELOPES),
                            requestID));
                }else{
                    SOAPEnvelope envelope=message.getEnvelope();
                    ITR069MethodDealer dealer=dispatcher.findMethodDealer(envelope);
                    if(dealer==null){
                        log.error("Can not find tr069 method dealer");
                        dealMessageWithFault(message, new TR069Fault(true,
                                TR069Constants.SERVER_FAULT_METHOD_NOT_SUPPORT,
                                FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_METHOD_NOT_SUPPORT),
                                requestID));
                    }else{
                        try {
                            dealer.deal(context,message);
                        }catch (TR069Fault fault){
                            log.error("deal tr069 method failed with fault",fault);
                            //for post deal message plugin ,because message has been dealed, so throw fault in plugin will not cause response fault
                            //TODO: think if it is reasonable
                            if(!message.isDealed()){
                                dealMessageWithFault(message,fault);
                            }
                        }catch (Exception exp){
                            log.error("deal tr069 method failed",exp);
                            dealMessageWithFault(message, new TR069Fault(false,
                                TR069Constants.SERVER_FAULT_INTERNAL_ERROR,
                                FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_INTERNAL_ERROR),
                                requestID));
                        }
                    }
                }
            }
            for(String responseStr: message.getResponses()){
                resultBuf.append(responseStr);
            }
            //set new can send envelopes count
            log.debug("first use can send envelope count is:" + context.getCanSendEnvelopeCount());
            int currentAvailableEnvelopes=context.getCanSendEnvelopeCount();
            if(currentAvailableEnvelopes!=-1){
                context.setCanSendEnvelopeCount(currentAvailableEnvelopes - message.getResponses().size());
            }
        }
        //check if has can send envelopes,and deal as empty envelope
        if(log.isDebugEnabled()){
            log.debug("Now can send envelope count is :"+context.getCanSendEnvelopeCount());
        }

        //while has available envelope count, then think there are empty message
        while(context.getCanSendEnvelopeCount()>0){
            SOAPMessage message=new SOAPMessage(null);
            try {
                ITR069MethodDealer dealer= (ITR069MethodDealer)BeanLocator.getBean("EmptyMessageDealer");
                dealer.deal(context,message);
            } catch (TR069Fault fault){
                log.error("deal tr069 method failed with fault",fault);
                //for post deal message plugin ,because message has been dealed, so throw fault in plugin will not cause response fault
                //TODO: think if it is reasonable
                if(message.isDealed()){
                    dealMessageWithFault(message,fault);
                }
            }catch (Exception exp){
                log.error("deal message failed",exp);
                String requestID=SOAPUtil.getIDFromHeader(message.getEnvelope());
                dealMessageWithFault(message, new TR069Fault(false,
                        TR069Constants.SERVER_FAULT_INTERNAL_ERROR,
                        FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_INTERNAL_ERROR),
                        requestID));
                break;
            }
            List<String> responseList = message.getResponses();
            if(responseList.size()==0){
                break;
            }
            for(String responseStr :responseList){
                resultBuf.append(responseStr);
            }
            //set new can send envelopes count
            context.setCanSendEnvelopeCount(context.getCanSendEnvelopeCount()-responseList.size());
        }


        String tr069Response=resultBuf.toString();
        if(!tr069Response.equals("")){
            context.setResponse(tr069Response);
        }else{
            context.setResponse(null);
        }
    }

    private void dealMessageWithFault(SOAPMessage message, TR069Fault fault) {
        SOAPEnvelope envelope=message.getEnvelope();
        if(envelope!=null){
            String commandName= SOAPUtil.getCommandName(envelope);
            if(SOAPUtil.isRequest(commandName)){
                message.addResponse(fault.toFault());
            }
        }
        message.setDealed(true);
        message.setFault(fault);
    }
}
