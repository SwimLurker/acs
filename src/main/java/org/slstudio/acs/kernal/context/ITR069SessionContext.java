package org.slstudio.acs.kernal.context;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: обнГ11:57
 * To change this template use File | Settings | File Templates.
 */
public interface ITR069SessionContext extends ISessionContext {
    public String getClientIP();
    public void setClientIP(String clientIP);
}
