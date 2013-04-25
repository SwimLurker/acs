package org.slstudio.acs.kernal.session.idmanager;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: обнГ11:51
 */
public interface ISessionIDManager {
    public String getSessionID(String clientID);

    public boolean addSessionID(String clientID,String sessionID);

    public boolean removeSessionID(String clientID);

    public void reset();
}
