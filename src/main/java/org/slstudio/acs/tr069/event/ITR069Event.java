package org.slstudio.acs.tr069.event;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ1:17
 */
public interface ITR069Event {
    public TR069Message getMessage();
    public void setMessage(TR069Message message);
    public ITR069MessageContext getMessageContext();
    public void setMessageContext(ITR069MessageContext messageContext);
}
