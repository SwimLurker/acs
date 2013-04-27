package org.slstudio.acs.tr069.endpoint.http.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ2:06
 */
public class NoProxyClientIPGetStrategy extends AbstractClientIPGetStrategy {
    public static final String HTTP_HEADER_REMOTEADDR = "REMOTE_ADDR";

    public NoProxyClientIPGetStrategy(HttpServletRequest request) {
        super(request);
    }

    public String getClientIP(){
        return getHttpServletRequest().getHeader(HTTP_HEADER_REMOTEADDR);
    }
}
