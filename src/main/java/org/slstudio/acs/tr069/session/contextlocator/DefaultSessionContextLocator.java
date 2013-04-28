package org.slstudio.acs.tr069.session.contextlocator;

import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.contextlocator.ISessionContextLocator;
import org.slstudio.acs.kernal.session.factory.ISessionContextFactory;
import org.slstudio.acs.kernal.session.idmanager.ISessionIDManager;
import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÏÂÎç11:44
 */
public class DefaultSessionContextLocator implements ISessionContextLocator {
    private ISessionContextFactory sessionContextFactory = null;
    private ISessionManager sessionManager = null;
    private ISessionIDManager sessionIDManager = null;

    public ISessionContextFactory getSessionContextFactory() {
        return sessionContextFactory;
    }

    public void setSessionContextFactory(ISessionContextFactory sessionContextFactory) {
        this.sessionContextFactory = sessionContextFactory;
    }

    public ISessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public ISessionIDManager getSessionIDManager() {
        return sessionIDManager;
    }

    public void setSessionIDManager(ISessionIDManager sessionIDManager) {
        this.sessionIDManager = sessionIDManager;
    }

    public ISessionContext retrieve(IProtocolEndPoint endPoint) throws ContextException {
        ISessionContext context = null;
        String clientID = endPoint.getProperty(ACSConstants.SESSIONCONTEXT_KEY_CLIENTID);
        if(clientID != null){
            String sessionID = sessionIDManager.getSessionID(clientID);
            if(sessionID != null){
                context = sessionManager.getSessionContext(sessionID);
            }
        }
        if(context == null){
            context = sessionContextFactory.create();
            context.initSessionContext(endPoint);
            addSessionRelation(context);
        }
        return context;
    }

    private synchronized void addSessionRelation(ISessionContext context){
        sessionIDManager.addSessionID(context.getClientID(), context.getSessionID());
        sessionManager.addSessionContext(context);
    }
}
