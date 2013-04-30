package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.session.context.ISessionContext;

import java.util.List;

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
    public int getClientPort();
    public void setClientPort(int port);
    public ITR069MessageContext getCurrentTR069MessageContext();
    public List<ITR069MessageContext> getTR069MessageContextList();
}
