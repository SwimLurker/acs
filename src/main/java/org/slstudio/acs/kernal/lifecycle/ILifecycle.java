package org.slstudio.acs.kernal.lifecycle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: обнГ2:22
 */
public interface ILifecycle {
        public void addLifecycleListener(ILifecycleListener listener);
        public void removeLifecycleListener(ILifecycleListener listener);
        public List<ILifecycleListener> getLifecycleListeners();
        public void start();
        public void stop();
}
