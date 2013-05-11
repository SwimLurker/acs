package org.slstudio.acs.kernal.lifecycle;

import org.slstudio.acs.kernal.exception.LifecycleException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: обнГ2:24
 */
public interface ILifecycleListener {
    public void lifecycleEvent(LifecycleEvent event) throws LifecycleException;
}
