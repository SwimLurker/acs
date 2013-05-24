package org.slstudio.acs.hms.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.ACSServer;
import org.slstudio.acs.DefaultACSServerLifecycleListener;
import org.slstudio.acs.kernal.exception.LifecycleException;
import org.slstudio.acs.tr069.job.monitor.JobMonitor;
import org.slstudio.acs.util.BeanLocator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç2:03
 */
public class JobMonitorServerListener extends DefaultACSServerLifecycleListener {
    private static final Log log = LogFactory.getLog(JobMonitorServerListener.class);

    @Override
    public void onAfterStartServer(ACSServer server) throws LifecycleException {
        log.info("Starting job monitor...");
        ((JobMonitor) BeanLocator.getBean("jobMonitor")).startMonitor();
    }

    @Override
    public void onBeforeStopServer(ACSServer server) throws LifecycleException {
        log.info("Stopping job monitor...");
        ((JobMonitor)BeanLocator.getBean("jobMonitor")).stopMonitor();
    }
}
