package org.slstudio.acs.kernal.endpoint.http.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ2:10
 */
public class ProxyClientIPGetStrategy extends AbstractClientIPGetStrategy {
    public static final String HTTP_HEADER_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR ";

    public ProxyClientIPGetStrategy(HttpServletRequest request) {
        super(request);
    }

    public String getClientIP(){
        return getHttpServletRequest().getHeader(HTTP_HEADER_X_FORWARDED_FOR);
    }
}
