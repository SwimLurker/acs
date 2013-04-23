package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.config.ConfigurationManager;
import org.slstudio.acs.kernal.config.TR069Config;
import org.slstudio.acs.kernal.context.ISessionContext;
import org.slstudio.acs.kernal.context.TR069SessionContextFactory;
import org.slstudio.acs.kernal.endpoint.HttpEndPoint;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.kernal.engine.TR069Engine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: ÏÂÎç11:51
 */
public class ACSServlet  extends HttpServlet {
    private static final Log log = LogFactory.getLog(org.slstudio.acs.ACSServlet.class);
    private IProtocolEngine engine = null;

    @Override
    public void init() throws ServletException {
        if(!ACSServer.getInstance().isRunning()){
            log.error("ACS Server is not running");
            throw new ServletException("ACS Server is not running");
        }
        TR069Config config = ConfigurationManager.getTR069Config();

        engine = new TR069Engine();
        engine.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IProtocolEndPoint endPoint= new HttpEndPoint(req, resp);
        ISessionContext context = TR069SessionContextFactory.getInstance().create(endPoint);
        engine.service(endPoint, context);
    }
}
