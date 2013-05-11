package org.slstudio.acs;

import org.slstudio.acs.hms.lifecycle.MessagingServiceServerListener;
import org.slstudio.acs.kernal.lifecycle.ILifecycle;
import org.slstudio.acs.kernal.lifecycle.ILifecycleListener;
import org.slstudio.acs.kernal.lifecycle.LifecycleSupport;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:18
 */
public class ACSServer implements ILifecycle{
    public static final String BEFORE_START_EVENT = "Event_BeforeStart";
    public static final String START_EVENT = "Event_Start";
    public static final String AFTER_START_EVENT = "Event_AfterStart";
    public static final String BEFORE_STOP_EVENT = "Event_BeforeStop";
    public static final String STOP_EVENT = "Event_Stop";
    public static final String AFTER_STOP_EVENT = "Event_AfterStop";

    private static ACSServer _instance = null;
    private boolean bRunning = false;
    private LifecycleSupport lifecycle = null;

    protected ACSServer(){
        lifecycle =new LifecycleSupport(this);
        addLifecycleListener(new MessagingServiceServerListener());
    }

    public static ACSServer getInstance(){
        if(_instance == null){
            _instance = new ACSServer();
        }
        return _instance;
    }

    public boolean isRunning() {
        return bRunning;
    }

    public void setAcsConfigFile(String acsConfigfile) {

    }

    public void addLifecycleListener(ILifecycleListener listener) {
        lifecycle.addLifecycleListener(listener);
    }

    public void removeLifecycleListener(ILifecycleListener listener) {
        lifecycle.removeLifecycleListener(listener);
    }

    public List<ILifecycleListener> getLifecycleListeners() {
        return lifecycle.getLifecycleListeners();
    }

    public void start() {
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        bRunning = true;
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);

    }

    public void stop() {
        lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        bRunning = false;
        lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
    }
}
