package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.config.ConfigurationManager;
import org.slstudio.acs.kernal.config.TR069Config;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.endpoint.file.FileEndPoint;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.kernal.engine.TR069Engine;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.factory.SessionContextLocatorFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç11:30
 */
public class TestACSServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(org.slstudio.acs.TestACSServlet.class);
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
        try{
            IProtocolEndPoint endPoint= new FileEndPoint("d:\\workspace\\acs\\src\\test\\resources\\file_endpoint","test.properties");
            ISessionContext context = SessionContextLocatorFactory.getInstance().getLocator().retrieve(endPoint);
            engine.service(endPoint, context);
        }catch(ACSException exp){
            exp.printStackTrace();
            throw new IOException(exp.getMessage(), exp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
