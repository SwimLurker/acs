package org.slstudio.acs.kernal.session.factory;

import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ионГ10:12
 */
public interface IMessageContextFactory {
    public IMessageContext create(ISessionContext sessionContext);
}
