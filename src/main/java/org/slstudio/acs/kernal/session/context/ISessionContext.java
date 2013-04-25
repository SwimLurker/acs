package org.slstudio.acs.kernal.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.SessionException;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: ÏÂÎç11:58
 * To change this template use File | Settings | File Templates.
 */
public interface ISessionContext {
    public int getLastErrorCode();
    public void setErrorCode(int errorCode);
    public InputStream getInputStream();
    public void setInputStream(InputStream is);
    public String getResponse();
    public void setResponse(String response);
    public String getSessionID();
    public void setSessionID(String sessionID);
    public String getClientID();
    public void setClientID(String clientID);
    public Object getProperty(String key);
    public void setProperty(String key, Object value);
    public long getTimestamp();
    public void setTimestamp(long timestamp);
    public IProtocolEndPoint getEndPoint();
    public void initSessionContext(IProtocolEndPoint endPoint) throws SessionException;
}
