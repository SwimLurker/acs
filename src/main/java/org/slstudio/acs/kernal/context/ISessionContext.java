package org.slstudio.acs.kernal.context;

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
    public Object getProperty(String key);
    public void setProperty(String key, Object value);
}
