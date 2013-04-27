package org.slstudio.acs.tr069.endpoint.http.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ2:06
 */
public abstract class AbstractClientIPGetStrategy implements IClientIPGetStrategy{
    private HttpServletRequest req = null;

    public AbstractClientIPGetStrategy(HttpServletRequest request){
        this.req = request;
    }

    public HttpServletRequest getHttpServletRequest(){
        return req;
    }

    public void setHttpServletRequest(HttpServletRequest req){
        this.req = req;
    }
}
