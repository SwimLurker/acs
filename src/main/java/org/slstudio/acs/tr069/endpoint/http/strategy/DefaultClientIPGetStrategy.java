package org.slstudio.acs.tr069.endpoint.http.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-29
 * Time: ионГ12:01
 */
public class DefaultClientIPGetStrategy extends AbstractClientIPGetStrategy{

    public DefaultClientIPGetStrategy(HttpServletRequest request) {
        super(request);
    }

    public String getClientIP() {
        return getHttpServletRequest().getRemoteAddr();
    }
}
