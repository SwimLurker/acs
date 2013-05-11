package org.slstudio.acs;

import org.slstudio.acs.kernal.exception.LifecycleException;
import org.slstudio.acs.kernal.lifecycle.ILifecycleListener;
import org.slstudio.acs.kernal.lifecycle.LifecycleEvent;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÏÂÎç2:34
 */
public class DefaultACSServerLifecycleListener implements ILifecycleListener {
    public void lifecycleEvent(LifecycleEvent event)  throws LifecycleException {
        if(ACSServer.BEFORE_START_EVENT.equals(event.getType())){
            onBeforeStartServer((ACSServer) event.getLifecycle());
        }else if(ACSServer.START_EVENT.equals(event.getType())){
            onStartServer((ACSServer) event.getLifecycle());
        }else if(ACSServer.AFTER_START_EVENT.equals(event.getType())){
            onAfterStartServer((ACSServer) event.getLifecycle());
        }else if(ACSServer.BEFORE_STOP_EVENT.equals(event.getType())){
            onBeforeStopServer((ACSServer) event.getLifecycle());
        }else if(ACSServer.STOP_EVENT.equals(event.getType())){
            onStopServer((ACSServer) event.getLifecycle());
        }else if(ACSServer.AFTER_STOP_EVENT.equals(event.getType())){
            onAfterStopServer((ACSServer) event.getLifecycle());
        }
    }

    public void onBeforeStartServer(ACSServer server) throws LifecycleException{
    }
    public void onStartServer(ACSServer server) throws LifecycleException{
    }
    public void onAfterStartServer(ACSServer server) throws LifecycleException{
    }
    public void onBeforeStopServer(ACSServer server) throws LifecycleException{
    }
    public void onStopServer(ACSServer server) throws LifecycleException{
    }
    public void onAfterStopServer(ACSServer server) throws LifecycleException{
    }
}
