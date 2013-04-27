package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.session.context.IMessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ионГ2:41
 */
public interface ITR069MessageContext extends IMessageContext {
    public ITR069SessionContext getTR069SessionContext();
}
