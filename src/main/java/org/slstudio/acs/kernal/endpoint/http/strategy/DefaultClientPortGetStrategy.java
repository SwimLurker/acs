package org.slstudio.acs.kernal.endpoint.http.strategy;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ11:06
 */
public class DefaultClientPortGetStrategy implements IClientPortGetStrategy {
    public static final String HTTP_HEADER_CLIENTPORT = "TR069CPE_PORT";
    private HttpServletRequest request = null;

    public DefaultClientPortGetStrategy(HttpServletRequest req){
        this.request = req;
    }
    public String getClientPort() {
        return request.getHeader(HTTP_HEADER_CLIENTPORT);
    }
}
