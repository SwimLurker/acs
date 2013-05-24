package org.slstudio.acs.hms.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.ACSServer;
import org.slstudio.acs.DefaultACSServerLifecycleListener;
import org.slstudio.acs.kernal.exception.LifecycleException;
import org.slstudio.acs.kernal.session.monitor.SessionMonitor;
import org.slstudio.acs.util.BeanLocator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç12:21
 */
public class SessionMonitorServerListener extends DefaultACSServerLifecycleListener {
    private static final Log log = LogFactory.getLog(SessionMonitorServerListener.class);

    @Override
    public void onAfterStartServer(ACSServer server) throws LifecycleException {
        log.info("Starting session monitor...");
        ((SessionMonitor)BeanLocator.getBean("sessionMonitor")).startMonitor();
    }

    @Override
    public void onBeforeStopServer(ACSServer server) throws LifecycleException {
        log.info("Stopping session monitor...");
        ((SessionMonitor)BeanLocator.getBean("sessionMonitor")).stopMonitor();
    }
}
