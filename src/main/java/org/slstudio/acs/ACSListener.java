package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.util.BeanLocator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:38
 */
public class ACSListener implements ServletContextListener {
    public static final String ACS_CONFIGFILE="acs.xml";

    private static final Log log = LogFactory.getLog(org.slstudio.acs.ACSListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //set bean locator first
        BeanLocator.setContext(WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext()));

        log.info("Start ACS Server...");
        //start acs server
        ACSServer server= ACSServer.getInstance();
        server.setAcsConfigFile(ACS_CONFIGFILE);

        server.start();
        log.info("ACS Server Started!");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Stop ACS Server...");
        ACSServer.getInstance().stop();
        log.info("ACS Server Stopped!");
    }
}
