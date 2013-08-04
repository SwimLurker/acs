package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.engine.IEngineSelector;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.tr069.endpoint.file.FileEndPoint;
import org.slstudio.acs.tr069.engine.DefaultEngineSelector;
import org.slstudio.acs.util.BeanLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç11:30
 */
public class FileACSServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(FileACSServlet.class);
    private List<IProtocolEngine> engineList = new ArrayList<IProtocolEngine>();
    private static final String ROOT_PATH="C:\\Users\\apple\\git\\acs\\src\\test\\resources\\file_endpoint\\";
    @Override
    public void init() throws ServletException {
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069AM1Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069AM2Engine"));
        engineList.add((IProtocolEngine) BeanLocator.getBean("tr069AM3Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069AM4Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069TestEngine"));
        for(IProtocolEngine engine: engineList){
            engine.init();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestID = getRequestID(req);
        IProtocolEngine selectedEngine = engineList.get(0);
        IEngineSelector selector = new DefaultEngineSelector();
        for(IProtocolEngine engine: engineList){
            if(selector.selectEngine(requestID, engine)){
                selectedEngine = engine;
                break;
            }
        }
        try{
            File inputDir = new File(ROOT_PATH+"input\\");
            if((!inputDir.exists())||(!inputDir.isDirectory())){
                throw new ACSException("File store input dir not found");
            }
            File propertiesFile = new File(ROOT_PATH+"test.properties");
            if((!propertiesFile.exists())||(propertiesFile.isDirectory())){
                throw new ACSException("File store properties file not found");
            }
            File outputDir = new File(ROOT_PATH+"output\\");
            if((!outputDir.exists())||(!outputDir.isDirectory())){
                outputDir.mkdirs();
            }
            IProtocolEndPoint endPoint= new FileEndPoint(inputDir,outputDir,propertiesFile);
            selectedEngine.service(endPoint);
        }catch(ACSException exp){
            exp.printStackTrace();
            throw new IOException(exp.getMessage(), exp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
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
