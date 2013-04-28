package org.slstudio.acs.tr069.session.idmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.session.idmanager.ISessionIDManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÏÂÎç11:52
 */
public class MemorySessionIDManager implements ISessionIDManager {
    private static final Log log = LogFactory.getLog(MemorySessionIDManager.class);

    private Map sessionIDMap= Collections.synchronizedMap(new HashMap());

    public String getSessionID(String clientID) {
        return (String)sessionIDMap.get(clientID);
    }

    public boolean addSessionID(String clientID, String sessionID) {
        synchronized (sessionIDMap){
            sessionIDMap.put(clientID,sessionID);
            log.info("add session ID:"+sessionID + ",clientID:" + clientID);
        }
        return true;
    }

    public boolean removeSessionID(String clientID) {
        synchronized(sessionIDMap){
            sessionIDMap.remove(clientID);
            log.info("remvoe session by client ID:" + clientID);
        }
        return true;
    }

    public void reset() {
        sessionIDMap.clear();
    }
}
