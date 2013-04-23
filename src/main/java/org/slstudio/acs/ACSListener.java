package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
        log.info("Start ACS Server...");
        //start acs server
        ACSServer server= ACSServer.getInstance();
        server.setAcsConfigFile(ACS_CONFIGFILE);

        if(!server.start()){
            log.error("Start ACS Server failed!");
        }else{
            log.info("ACS Server Started!");
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Stop ACS Server...");
        if(!ACSServer.getInstance().stop()){
            log.error("Stop ACS Server failed!");
        }else{
            log.info("ACS Server Stopped!");
        }
    }
}
