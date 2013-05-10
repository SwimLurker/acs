package org.slstudio.acs.tr069.session.sessionmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.idmanager.ISessionIDManager;
import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÏÂÎç11:16
 */
public class MemorySessionManager implements ISessionManager {
    private static final Log log = LogFactory.getLog(MemorySessionManager.class);

    private Map contextMap = Collections.synchronizedMap(new HashMap());
    private ISessionIDManager sessionIDManager = null;

    public MemorySessionManager(){
    }

    public ISessionIDManager getSessionIDManager() {
        return sessionIDManager;
    }

    public void setSessionIDManager(ISessionIDManager sessionIDManager) {
        this.sessionIDManager = sessionIDManager;
    }

    public List<ISessionContext> getAllSessionContexts() {
        return new ArrayList<ISessionContext>(contextMap.values());
    }

    public ISessionContext getSessionContext(String sessionID) {
        ISessionContext context=null;
        if(sessionID!=null){
            context=(ISessionContext)contextMap.get(sessionID);
        }
        if(context!=null){
            context.setTimestamp(System.currentTimeMillis());
        }
        return context;
    }

    public boolean removeSessionContext(String sessionID) {
        synchronized(contextMap){
            contextMap.remove(sessionID);
            log.info("Remove session:"+sessionID);
            return true;
        }
    }

    public boolean addSessionContext(ISessionContext context) {
        synchronized(contextMap){
            contextMap.put(context.getSessionID(), context);
            context.setTimestamp(System.currentTimeMillis());
            log.info("Add session:"+context.getSessionID());
            return true;
        }
    }

    public boolean updateSessionContext(ISessionContext context) {
        if(context==null||context.getSessionID()==null){
            return false;
        }
        synchronized(contextMap){
            contextMap.put(context.getSessionID(),context);
            context.setTimestamp(System.currentTimeMillis());
            log.info("Update session:"+context.getSessionID());
            return true;
        }
    }

    public void reset(){
        synchronized(contextMap){
            contextMap.clear();
        }
        getSessionIDManager().reset();
    }

//    public ISessionIDManager getSessionIDManager() {
//        if(idManager == null){
//            idManager = new MemorySessionIDManager();
//        }
//        return idManager;
//    }
}
