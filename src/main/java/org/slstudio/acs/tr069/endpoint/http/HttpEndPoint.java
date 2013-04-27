package org.slstudio.acs.tr069.endpoint.http;

import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.endpoint.http.AbstractHttpEndPoint;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.tr069.TR069Constants;
import org.slstudio.acs.tr069.config.TR069Config;
import org.slstudio.acs.tr069.endpoint.http.strategy.DefaultClientPortGetStrategy;
import org.slstudio.acs.tr069.endpoint.http.strategy.IClientIPGetStrategy;
import org.slstudio.acs.tr069.endpoint.http.strategy.IClientPortGetStrategy;
import org.slstudio.acs.tr069.endpoint.http.strategy.NoProxyClientIPGetStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:12
 */
public class HttpEndPoint extends AbstractHttpEndPoint implements IProtocolEndPoint {
    private IClientIPGetStrategy clientIPGetStrategy = null;
    private IClientPortGetStrategy clientPortGetStrategy = null;

    public HttpEndPoint(HttpServletRequest req, HttpServletResponse res){
        super(req,res);
        clientIPGetStrategy = new NoProxyClientIPGetStrategy(req);
        clientPortGetStrategy = new DefaultClientPortGetStrategy(req);
    }

    @Override
    public String getProperty(String name){
        if(TR069Constants.SESSIONCONTEXT_KEY_CLIENTIP.equals(name)){
            return getClientIP();
        }else if(TR069Constants.SESSIONCONTEXT_KEY_CLIENTPORT.equals(name)){
            return getClientPort();
        }
        return super.getProperty(name);
    }

    @Override
    protected String getSessionID() {
        if(TR069Config.isUseSessionCookie()){
            String sessionID = null;
            Cookie[] cookies=getRequest().getCookies();
            if(cookies!=null&&cookies.length>0){
                for(int i=0;i<cookies.length;i++){
                    Cookie c=cookies[i];
                    if(c.getName().equals(TR069Constants.COOKIE_SESSION_ID)){
                        sessionID = c.getValue();
                    }
                }
            }
            return sessionID;
        }
        return null;
    }

    @Override
    protected String getClientID() {
        String clientID = getClientIP();
        String clientPort = getClientPort();
        if(clientID != null && TR069Config.isSupportSameIPForDifferentSession() && clientPort != null){
            clientID+=":";
            clientID+=clientPort;
        }
        return clientID;
    }

    @Override
    protected void beforeWriteResponse(IMessageContext context) {
        //set http status
        if(context.getLastErrorCode() == ACSConstants.ERROR_CODE_SUCCESS){
            if(context.getResponse() == null) {
                setHttpStatus(HttpServletResponse.SC_NO_CONTENT);
            }else{
                setHttpStatus(HttpServletResponse.SC_OK);
            }
        }else{
            setHttpStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        //set content type
        String responseString = context.getResponse();
        if(responseString == null) {
            setContentType(null);
        }else{
            setContentType("text/xml");
        }
        //set session id
        String sessionID = null;
        if(context.getSessionContext()!=null){
            sessionID = context.getSessionContext().getSessionID();
        }
        if(TR069Config.isUseSessionCookie()){
            if(sessionID!=null){
                String cookieValue = TR069Constants.COOKIE_SESSION_ID + "=" + sessionID + ";Version=1;Discard";
                saveProperty("Set-Cookie2", cookieValue);
            }
        }
        super.beforeWriteResponse(context);
    }

    private String getClientIP() {
        return clientIPGetStrategy.getClientIP();
    }

    private String getClientPort() {
        return clientPortGetStrategy.getClientPort();
    }

}
