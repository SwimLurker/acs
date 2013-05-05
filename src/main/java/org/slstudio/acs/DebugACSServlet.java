package org.slstudio.acs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.engine.IEngineSelector;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.tr069.endpoint.stream.StreamEndPoint;
import org.slstudio.acs.tr069.engine.DefaultEngineSelector;
import org.slstudio.acs.util.BeanLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-29
 * Time: ÉÏÎç1:37
 */
public class DebugACSServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(DebugACSServlet.class);
    private List<IProtocolEngine> engineList = new ArrayList<IProtocolEngine>();

    @Override
    public void init() throws ServletException {
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069AM1Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069AM2Engine"));
        engineList.add((IProtocolEngine)BeanLocator.getBean("tr069AM3Engine"));
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
        String properties = req.getParameter("properties");
        String requestMsg = req.getParameter("requestMsg");

        InputStream propertiesIS = new ByteArrayInputStream(properties.getBytes());
        InputStream requestIS = new ByteArrayInputStream(requestMsg.getBytes());
        OutputStream responseOS = new ByteArrayOutputStream();

        try{
            IProtocolEndPoint endPoint= new StreamEndPoint(propertiesIS, requestIS, responseOS);
            selectedEngine.service(endPoint);
        }catch(ACSException exp){
            exp.printStackTrace();
            throw new IOException(exp.getMessage(), exp);
        }
        StringBuilder result = new StringBuilder();
        result.append("<html><body><table width=\"800\" border=\"0\">");
        result.append(getRequestForm(req));
        result.append("<tr valign=\"top\"><td>Response:</td><td><textarea name=\"result\" rows=\"20\" cols=\"80\" >");
        result.append(responseOS.toString());
        result.append("</textarea></td></tr>");
        result.append("</table></body></html>");
        writeResponse(resp, HttpServletResponse.SC_OK, "text/html", result.toString());

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder result = new StringBuilder();
        result.append("<html><body><table width=\"800\" border=\"0\">");
        result.append(getRequestForm(req));
        result.append("</table></body></html>");
        writeResponse(resp,HttpServletResponse.SC_OK,"text/html",result.toString());
    }

    private void writeResponse(HttpServletResponse resp, int statusCode, String contentType, String responseText) throws IOException {
        resp.setStatus(statusCode);
        resp.setContentType(contentType);
        resp.setContentLength(responseText.length());
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.write(responseText);
        pw.flush();
        pw.close();
    }

    private String getRequestForm(HttpServletRequest req){
        StringBuilder result = new StringBuilder();
        String postTarget = req.getRequestURI();
        result.append("<form action=\"");
        result.append(postTarget);
        result.append("\" method=\"post\">");
        result.append("<tr valign=\"top\"><td width=\"10%\">Properties:</td><td><textarea name=\"properties\" rows=\"8\" cols=\"80\" >SessionContext_Key_ClientIP=127.0.0.1\r\nSessionContext_Key_ClientID=10000</textarea></td></tr>");
        result.append("<tr valign=\"top\"><td>Request:</td><td><textarea name=\"requestMsg\" rows=\"20\" cols=\"80\" ></textarea></td></tr>");
        result.append("<tr valign=\"top\"><td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Submit\" /></td></tr>");
        result.append("</form>");
        return result.toString();
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