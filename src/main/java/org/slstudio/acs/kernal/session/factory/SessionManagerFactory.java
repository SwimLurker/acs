package org.slstudio.acs.kernal.session.factory;

import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;
import org.slstudio.acs.kernal.session.sessionmanager.MemorySessionManager;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÏÂÎç10:54
 */
public class SessionManagerFactory {
    private static SessionManagerFactory _instance = null;

    private ISessionManager sessionManager = null;

    protected SessionManagerFactory(){
    }

    public static SessionManagerFactory getInstance(){
        if(_instance == null){
            _instance = new SessionManagerFactory();
        }
        return _instance;
    }

    public ISessionManager getSessionManager(){
        if(sessionManager==null){
            sessionManager  = new MemorySessionManager();
        }
        return sessionManager;
    }

}
