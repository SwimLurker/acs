package org.slstudio.acs.kernal.lifecycle;

import org.slstudio.acs.kernal.exception.LifecycleException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÏÂÎç2:26
 */
public class LifecycleSupport {
    private ILifecycle lifecycle = null;
    List<ILifecycleListener> listeners = null;

    public LifecycleSupport(ILifecycle lifecycle) {
        this.lifecycle = lifecycle;
        listeners = new ArrayList<ILifecycleListener>();
    }

    public void fireLifecycleEvent(String type, Object data){
        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        List<ILifecycleListener> interested = new ArrayList<ILifecycleListener>();
        synchronized (listeners) {
            interested.addAll(listeners);
        }
        for (ILifecycleListener listener: interested){
            try {
                listener.lifecycleEvent(event);
            } catch (LifecycleException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void addLifecycleListener(ILifecycleListener listener) {
        listeners.add(listener);
    }

    public void removeLifecycleListener(ILifecycleListener listener) {
        listeners.remove(listener);
    }

    public List<ILifecycleListener> getLifecycleListeners() {
        return listeners;
    }

    public void setLifecycleListeners(List<ILifecycleListener> listeners) {
        this.listeners = listeners;
    }
}