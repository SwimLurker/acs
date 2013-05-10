package org.slstudio.acs.kernal.session.sessionmanager;

import org.slstudio.acs.kernal.session.context.ISessionContext;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: обнГ10:57
 */
public interface ISessionManager {

    public List<ISessionContext> getAllSessionContexts();

    public ISessionContext getSessionContext(String sessionID) ;

    public boolean removeSessionContext(String sessionID);

    public boolean addSessionContext(ISessionContext context);

    public boolean updateSessionContext(ISessionContext context);

    public void reset();
}
