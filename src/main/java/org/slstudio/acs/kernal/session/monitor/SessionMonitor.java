package org.slstudio.acs.kernal.session.monitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.idmanager.ISessionIDManager;
import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç12:04
 */
public class SessionMonitor implements Runnable{
    private static final Log log = LogFactory.getLog(SessionMonitor.class);

    private static final int SESSION_TIMEOUT=60000;

    private long sessionTimeout=SESSION_TIMEOUT;
    private boolean bRunning=true;
    private long monitorInterval=20000;

    private ISessionManager sessionManager = null;
    private ISessionIDManager sessionIDManager = null;

    public long getMonitorInterval() {
        return monitorInterval;
    }

    public void setMonitorInterval(long monitorInterval) {
        this.monitorInterval = monitorInterval;
    }

    public long getSessionTimeout(){
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout){
        this.sessionTimeout=sessionTimeout;
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

    public void stopMonitor(){
        this.bRunning=false;
    }

    public void startMonitor(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public void run(){
        bRunning=true;
        while(bRunning){
            log.info("Session monitor doing job...");
            try{
                List<ISessionContext> contexts = sessionManager.getAllSessionContexts();
                Set<String> deleteContexts=new HashSet<String>();
                Set<String> deleteClients=new HashSet<String>();

                for(ISessionContext context: contexts){
                    long currentTime=System.currentTimeMillis();
                    if(currentTime>(context.getTimestamp() + sessionTimeout)){
                        deleteContexts.add(context.getSessionID());
                        deleteClients.add(context.getClientID());
                    }
                }

                Iterator<String> dIt=deleteContexts.iterator();
                while(dIt.hasNext()){
                    sessionManager.removeSessionContext(dIt.next());
                }

                Iterator<String> dIt2=deleteClients.iterator();
                while(dIt2.hasNext()){
                    sessionIDManager.removeSessionID(dIt2.next());
                }
            }catch(ConcurrentModificationException exp){
                log.error("Session monitor exception:cocurrent modification",exp);
            }catch(Exception exp){
                log.error("Session monitor exception",exp);
            }
            long interval=getMonitorInterval();
            if(interval<=0){
                interval=20000;
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
            }
        }

    }
}