package org.slstudio.acs.kernal.endpoint.http;

import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.config.TR069Config;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.endpoint.http.strategy.*;
import org.slstudio.acs.kernal.exception.EndPointException;
import org.slstudio.acs.kernal.session.context.ISessionContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:12
 */
public class HttpEndPoint implements IProtocolEndPoint {
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private IClientIPGetStrategy clientIPGetStrategy = null;
    private IClientPortGetStrategy clientPortGetStrategy = null;
    private ISessionIDStrategy sessionIDStrategy = null;

    public HttpEndPoint(HttpServletRequest req, HttpServletResponse res){
        this.request = req;
        this.response = res;
        clientIPGetStrategy = new NoProxyClientIPGetStrategy(this.request);
        clientPortGetStrategy = new DefaultClientPortGetStrategy(this.request);
        sessionIDStrategy = new DefaultSessionIDStrategy(this.request, this.response);
    }

    public String getProperty(String name){
        if(TR069Constants.CONTEXT_KEY_CLIENTIP.equals(name)){
            return getClientIP();
        }else if(TR069Constants.CONTEXT_KEY_CLIENTPORT.equals(name)){
            return getClientPort();
        }else if(TR069Constants.CONTEXT_KEY_CLIENTID.equals(name)){
            return getClientID();
        }else if(TR069Constants.CONTEXT_KEY_SESSIONID.equals(name)){
            return getSessionID();
        }
        String value = getHeaderValue(name);
        if(value == null || value.equals("")){
            value = getCookieValue(name);
        }
        return value;
    }

    private String getSessionID() {
        if(TR069Config.isUseSessionCookie()){
            return sessionIDStrategy.getSessionID();
        }
        return null;
    }

    private String getClientIP() {
        return clientIPGetStrategy.getClientIP();
    }

    private String getClientPort() {
        return clientPortGetStrategy.getClientPort();
    }

    private String getClientID() {
        String clientID = getClientIP();
        String clientPort = getClientPort();
        if(clientID != null && TR069Config.isSupportSameIPForDifferentSession() && clientPort != null){
            clientID+=":";
            clientID+=clientPort;
        }
        return clientID;
    }

    public InputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public void writeResponse(ISessionContext context) throws EndPointException {
        if(context.getLastErrorCode() == TR069Constants.ERROR_CODE_SUCCESS){
            if(context.getResponse() == null) {
                writeHttpResponse(HttpServletResponse.SC_NO_CONTENT, context.getSessionID(), null);
            }else{
                writeHttpResponse(HttpServletResponse.SC_OK, context.getSessionID(), context.getResponse());
            }
        }else{
            writeHttpResponse(HttpServletResponse.SC_BAD_REQUEST, context.getSessionID(), null);
        }
    }

    private void writeHttpResponse(int httpStatus, String sessionID, String responseText) throws EndPointException {
        response.setStatus(httpStatus);
        response.setContentType("text/xml");
        if(responseText == null){
            response.setContentLength(0);
        }else{
            response.setContentLength(responseText.length());
        }
        if(TR069Config.isUseSessionCookie()){
            sessionIDStrategy.setSessionID(sessionID);
        }
        PrintWriter pw= null;
        try {
            pw = response.getWriter();
            if(responseText!=null){
                pw.println(responseText);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new EndPointException("Get writer from http servlet esponse error",e);
        }
    }

    private String getHeaderValue(String headerName) {
        return request.getHeader(headerName);
    }

    private String getCookieValue(String cookieName) {
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for(int i=0;i<cookies.length;i++){
                Cookie c=cookies[i];
                if(c.getName().equals(cookieName)){
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
