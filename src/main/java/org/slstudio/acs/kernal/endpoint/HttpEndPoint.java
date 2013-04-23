package org.slstudio.acs.kernal.endpoint;

import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.config.TR069Config;
import org.slstudio.acs.kernal.context.ISessionContext;

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

    public HttpEndPoint(HttpServletRequest req, HttpServletResponse res){
        this.request = req;
        this.response = res;
    }

    public Object getProperty(String name){
        if(TR069Constants.CONTEXT_KEY_CLIENTIP.equals(name)){
            return getClientIP();
        }else if(TR069Constants.CONTEXT_KEY_SESSIONID.equals(name)){
            return getSessionID();
        }
        Object value = getHeaderValue(name);
        if(value == null || value.equals("")){
            value = getCookieValue(name);
        }
        return value;
    }

    private String getSessionID() {
        return null;
    }

    private String getClientIP() {
        return null;
    }

    public InputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public void writeResponse(ISessionContext context) throws IOException {
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

    private void writeHttpResponse(int httpStatus, String sessionID, String responseText) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType("text/xml");
        if(responseText == null){
            response.setContentLength(0);
        }else{
            response.setContentLength(responseText.length());
        }
        if(TR069Config.isUseSessionCookie()){
            if(sessionID!=null){
                String cookieValue = TR069Constants.COOKIE_SESSION_ID + "=" + sessionID + ";Version=1;Discard";
                response.setHeader("Set-Cookie2",cookieValue);
            }
        }
        PrintWriter pw=response.getWriter();

        if(responseText!=null){
            pw.println(responseText);
        }
        pw.flush();
        pw.close();
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
