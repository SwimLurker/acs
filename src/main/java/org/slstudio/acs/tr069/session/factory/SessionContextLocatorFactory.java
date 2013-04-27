package org.slstudio.acs.tr069.session.factory;

import org.slstudio.acs.kernal.session.contextlocator.DefaultSessionContextLocator;
import org.slstudio.acs.kernal.session.contextlocator.ISessionContextLocator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç1:55
 */
public class SessionContextLocatorFactory {
    private static SessionContextLocatorFactory _instance = null;
    private ISessionContextLocator locator = null;

    protected SessionContextLocatorFactory(){
    }

    public static SessionContextLocatorFactory getInstance(){
        if(_instance == null){
            _instance = new SessionContextLocatorFactory();
        }
        return _instance;
    }

    public ISessionContextLocator getLocator(){
        if(locator == null){
            locator = new DefaultSessionContextLocator(new TR069SessionContextFactory());
        }
        return locator;
    }
}
