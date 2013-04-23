package org.slstudio.acs.kernal.context;

import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:05
 */
public class TR069SessionContextFactory implements ISessionContextFactory {
    private static TR069SessionContextFactory _instance = null;

    protected TR069SessionContextFactory(){
    }

    public static TR069SessionContextFactory getInstance(){
        if(_instance == null){
            _instance = new TR069SessionContextFactory();
        }
        return _instance;
    }

    public ISessionContext create(IProtocolEndPoint endPoint){
        ITR069SessionContext context = new TR069SessionContext();
        context.setClientIP((String)endPoint.getProperty(TR069Constants.CONTEXT_KEY_CLIENTIP));
        context.setSessionID((String)endPoint.getProperty(TR069Constants.CONTEXT_KEY_SESSIONID));
        return context;
    }
}
