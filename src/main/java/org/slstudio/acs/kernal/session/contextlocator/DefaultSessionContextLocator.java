package org.slstudio.acs.kernal.session.contextlocator;

import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.SessionException;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.factory.ISessionContextFactory;
import org.slstudio.acs.kernal.session.factory.SessionManagerFactory;
import org.slstudio.acs.kernal.session.idmanager.ISessionIDManager;
import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÏÂÎç11:44
 */
public class DefaultSessionContextLocator implements ISessionContextLocator {
    private ISessionContextFactory factory = null;

    public DefaultSessionContextLocator(ISessionContextFactory factory){
        this.factory = factory;
    }

    public ISessionContextFactory getFactory() {
        return factory;
    }

    public void setFactory(ISessionContextFactory factory) {
        this.factory = factory;
    }

    public ISessionContext retrieve(IProtocolEndPoint endPoint) throws SessionException{
        ISessionContext context = null;
        ISessionManager sessionManager = SessionManagerFactory.getInstance().getSessionManager();
        ISessionIDManager idManager = sessionManager.getSessionIDManager();

        String clientID = endPoint.getProperty(ACSConstants.SESSIONCONTEXT_KEY_CLIENTID);
        if(clientID != null){
            String sessionID = idManager.getSessionID(clientID);
            if(sessionID != null){
                context = sessionManager.getSessionContext(sessionID);
            }
        }
        if(context == null){
            context = factory.create();
            context.initSessionContext(endPoint);
            addSessionRelation(context);
        }
        return context;
    }

    private synchronized void addSessionRelation(ISessionContext context){
        ISessionManager sessionManager = SessionManagerFactory.getInstance().getSessionManager();
        ISessionIDManager idManager = sessionManager.getSessionIDManager();
        idManager.addSessionID(context.getClientID(),context.getSessionID());
        sessionManager.addSessionContext(context);
    }
}
