package org.slstudio.acs.tr069.messagedealer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÏÂÎç12:46
 */
public abstract class AbstractRequestDealer extends AbstractMessageDealer{
    private static final Log log = LogFactory.getLog(AbstractRequestDealer.class);

    @Override
    protected String dealMessage(ITR069MessageContext context,TR069Message message) throws TR069Fault{
        return dealRequest(context,message);
    }

    //TODO:we should return a soap envelope instead of String
    protected abstract String dealRequest(ITR069MessageContext context,TR069Message request) throws TR069Fault ;


}
