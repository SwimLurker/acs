package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.MessageException;
import org.slstudio.acs.kernal.session.context.AbstractMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç2:35
 */
public class TR069MessageContext extends AbstractMessageContext implements ITR069MessageContext {

    public TR069MessageContext(ISessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init(IProtocolEndPoint endPoint) throws MessageException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ITR069SessionContext getTR069SessionContext() {
        return (ITR069SessionContext)getSessionContext();
    }
}
