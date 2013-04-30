package org.slstudio.acs.kernal.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: ÏÂÎç11:58
 * To change this template use File | Settings | File Templates.
 */
public interface ISessionContext {
    public String getSessionID();
    public void setSessionID(String sessionID);
    public int getStatus();
    public void setStatus(int status);
    public String getClientID();
    public void setClientID(String clientID);
    public Object getProperty(String key);
    public void setProperty(String key, Object value);
    public long getTimestamp();
    public void setTimestamp(long timestamp);
    public IMessageContext newMessageContext(IProtocolEndPoint endPoint) throws ContextException;
    public IMessageContext getCurrentMessageContext();
    public List<? extends IMessageContext> getMessageContextList();
    public void initSessionContext(IProtocolEndPoint endPoint) throws ContextException;


}
