package org.slstudio.acs.kernal.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.MessageException;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç2:21
 */
public interface IMessageContext {
    public ISessionContext getSessionContext();
    public InputStream getInputStream();
    public void setInputStream(InputStream is);
    public String getResponse();
    public void setResponse(String response);
    public int getLastErrorCode();
    public void setErrorCode(int errorCode);
    public IProtocolEndPoint getEndPoint();
    public Object getProperty(String key);
    public void setProperty(String key, Object value);
    public void initMessageContext(IProtocolEndPoint endPoint) throws MessageException;
}
