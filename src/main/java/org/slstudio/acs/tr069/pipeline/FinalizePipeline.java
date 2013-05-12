package org.slstudio.acs.tr069.pipeline;

import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.soap.SOAPUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç4:07
 */
public class FinalizePipeline extends AbstractTR069Pipeline {
    @Override
    protected void process(ITR069MessageContext context) throws PipelineException {
        //check if has error, so need to close session
        if(context.getLastErrorCode() != ACSConstants.ERROR_CODE_SUCCESS){
            context.getSessionContext().setStatus(ACSConstants.SESSION_STATUS_CLOSED);
        }
        //check if session is closed normally(response is null)
        if(context.getResponse() == null){
            context.getSessionContext().setStatus(ACSConstants.SESSION_STATUS_CLOSED);

        }
        //check for soap fault (only fault code is 8005 when deal inform then no need to close session)
        for(SOAPMessage message: context.getSoapMessageList()){
            String commandName = SOAPUtil.getCommandName(message.getEnvelope());
            TR069Fault fault = message.getFault();
            if(fault == null){
                continue;
            }

            if(TR069Constants.INFORM_MESSAGE.equals(commandName) && TR069Constants.SERVER_FAULT_RETRY_REQUEST.equals(fault.getFaultCode())){
                continue;
            }
            context.getSessionContext().setStatus(ACSConstants.SESSION_STATUS_CLOSED);
        }
    }
}
