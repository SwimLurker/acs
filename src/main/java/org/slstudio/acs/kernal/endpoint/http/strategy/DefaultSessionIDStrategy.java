package org.slstudio.acs.kernal.endpoint.http.strategy;

import org.slstudio.acs.kernal.TR069Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç2:14
 */
public class DefaultSessionIDStrategy implements ISessionIDStrategy {
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    public DefaultSessionIDStrategy(HttpServletRequest req, HttpServletResponse res){
        this.request = req;
        this.response =res;
    }

    public String getSessionID(){
        String sessionID = null;
        Cookie[] cookies=request.getCookies();
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

    public void setSessionID(String sessionID){
        if(sessionID!=null){
            String cookieValue = TR069Constants.COOKIE_SESSION_ID + "=" + sessionID + ";Version=1;Discard";
            response.setHeader("Set-Cookie2",cookieValue);
        }
    }
}
