package org.slstudio.acs.tr069.event;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:29
 */
public class DefaultTR069Event implements ITR069Event {
    private ITR069MessageContext messageContext = null;
    private TR069Message message = null;

    public DefaultTR069Event(ITR069MessageContext messageContext, TR069Message message) {
        this.messageContext = messageContext;
        this.message = message;
    }

    public ITR069MessageContext getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(ITR069MessageContext messageContext) {
        this.messageContext = messageContext;
    }

    public TR069Message getMessage() {
        return message;
    }

    public void setMessage(TR069Message message) {
        this.message = message;
    }
}
