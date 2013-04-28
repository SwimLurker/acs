package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.config.ConfigurationManager;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.engine.IEngineSelector;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.tr069.config.TR069Config;
import org.slstudio.acs.tr069.endpoint.http.HttpEndPoint;
import org.slstudio.acs.tr069.engine.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: ÏÂÎç11:51
 */
public class ACSServlet  extends HttpServlet {
    private static final Log log = LogFactory.getLog(org.slstudio.acs.ACSServlet.class);
    private List<IProtocolEngine> engineList = new ArrayList<IProtocolEngine>();

    @Override
    public void init() throws ServletException {
        if(!ACSServer.getInstance().isRunning()){
            log.error("ACS Server is not running");
            throw new ServletException("ACS Server is not running");
        }
        TR069Config config = ConfigurationManager.getTR069Config();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        engineList.add((IProtocolEngine)ctx.getBean("tr069Engine"));
        engineList.add((IProtocolEngine)ctx.getBean("tr069AM1Engine"));
        engineList.add((IProtocolEngine)ctx.getBean("tr069AM2Engine"));
        engineList.add((IProtocolEngine)ctx.getBean("tr069AM3Engine"));
        engineList.add((IProtocolEngine)ctx.getBean("tr069AM4Engine"));
        engineList.add((IProtocolEngine)ctx.getBean("tr069TestEngine"));
        for(IProtocolEngine engine: engineList){
            engine.init();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //first get module
        String requestID = getRequestID(req);
        IProtocolEngine selectedEngine = engineList.get(0);
        IEngineSelector selector = new DefaultEngineSelector();
        for(IProtocolEngine engine: engineList){
            if(selector.selectEngine(requestID, engine)){
                selectedEngine = engine;
                break;
            }
        }
        IProtocolEndPoint endPoint= new HttpEndPoint(req, resp);
        try{
            selectedEngine.service(endPoint);
        }catch(ACSException exp){
            exp.printStackTrace();
            writeErrorResponse(resp, exp.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void writeErrorResponse(HttpServletResponse resp, String message) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentLength(message.length());
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(resp.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
        }catch (IOException exp){
            if(pw != null){
                pw.close();
            }
        }
    }

    private String getRequestID(HttpServletRequest req) {
        String requestURI=req.getRequestURI();
        String prePath = req.getContextPath() + req.getServletPath();
        int startPos = prePath.length() + 1;
        if( startPos >= requestURI.length()){
            return "";
        }
        return requestURI.substring(startPos);
    }

}
