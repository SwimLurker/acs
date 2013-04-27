package org.slstudio.acs.tr069.pipeline;

import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.pipeline.IProtocolPipeline;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:52
 */
public abstract class AbstractTR069Pipeline implements IProtocolPipeline {

    public final void processMessage(IMessageContext context) throws ACSException {
        if(context instanceof ITR069MessageContext){
            ITR069MessageContext trContext = (ITR069MessageContext)context;
            try{
                process(trContext);
            }catch (TR069Exception exp){
                trContext.setErrorCode(ACSConstants.ERROR_CODE_UNKNOWNERROR);
                throw exp;
            }
        }else{
            context.setErrorCode(ACSConstants.ERROR_CODE_UNSUPPORTPROTOCOL);
        }
    }

    protected abstract void  process(ITR069MessageContext context) throws TR069Exception;
}
