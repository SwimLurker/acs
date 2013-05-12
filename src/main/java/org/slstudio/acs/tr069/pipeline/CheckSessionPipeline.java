package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.CheckSessionException;
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
                throw new CheckSessionException("Multiple envelopes when check inform");
            }else {
                //message size is ok, then check if it is inform
                if(isInform(messages.get(0))){
                    sessionContext.setStatus(ACSConstants.SESSION_STATUS_CHECKED);
                }else{
                    throw new CheckSessionException("Need inform request first");
                }
            }
        }
    }

    //check if the message is an inform request
    private boolean isInform(SOAPMessage message) throws CheckSessionException{
        SOAPEnvelope envelope = message.getEnvelope();
        if(envelope == null){
            throw new CheckSessionException("Not Inform request envelope is null");
        }
        String commandName = SOAPUtil.getCommandName(envelope);
        if(commandName == null){
            throw new CheckSessionException("Not Inform request,instruction name is null");
        }
        String cwmpID = SOAPUtil.getIDFromHeader(envelope);
        if(cwmpID == null){
            throw new CheckSessionException("Not Inform request,instruction id is null");
        }
        return TR069Constants.INFORM_MESSAGE.equals(commandName);
    }

}
