package org.slstudio.acs.tr069.session.factory;

import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.factory.IMessageContextFactory;
import org.slstudio.acs.tr069.session.context.TR069MessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ионГ10:11
 */
public class TR069MessageContextFactory implements IMessageContextFactory {
    public IMessageContext create(ISessionContext sessionContext) {
        return new TR069MessageContext(sessionContext);
    }
}
