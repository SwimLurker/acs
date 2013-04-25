package org.slstudio.acs.kernal.session.context;

import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.SessionException;
import org.slstudio.acs.kernal.session.factory.SessionIDGeneratorFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç12:30
 */
public abstract class SessionContextBase implements ISessionContext{
    private int lastErrorCode = TR069Constants.ERROR_CODE_SUCCESS;
    private InputStream inputStream = null;
    private String response = null;
    private String sessionID = null;
    private String clientID = null;
    private long timestamp = 0l;
    private IProtocolEndPoint endPoint = null;
    private Map properties = new HashMap();

    public int getLastErrorCode(){
        return lastErrorCode;
    }

    public void setErrorCode(int errorCode){
        this.lastErrorCode = errorCode;
    }

    public InputStream getInputStream(){
        return inputStream;
    }

    public void setInputStream(InputStream is){
        this.inputStream = is;
    }

    public String getResponse(){
        return response;
    }

    public void setResponse(String response){
        this.response = response;
    }

    public String getSessionID(){
        return sessionID;
    }

    public void setSessionID(String sessionID){
        this.sessionID = sessionID;
    }

    public String getClientID(){
        return clientID;
    }

    public void setClientID(String clientID){
        this.clientID = clientID;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public IProtocolEndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(IProtocolEndPoint endPoint) {
        this.endPoint = endPoint;
    }
    public Object getProperty(String key){
        return properties.get(key);
    }

    public void setProperty(String key, Object value){
        properties.put(key, value);
    }

    public void initSessionContext(IProtocolEndPoint endPoint) throws SessionException {
        setEndPoint(endPoint);
        properties.clear();
        try{
            InputStream is = endPoint.getInputStream();
            setInputStream(is);
        }catch(IOException exp){
            throw new SessionException("retrieve inputstream from endpoint error", exp);
        }
        setErrorCode(TR069Constants.ERROR_CODE_SUCCESS);
        setResponse(null);
        String newSessionID = SessionIDGeneratorFactory.getInstance().getSessionIDGenerator().generateSessionID();
        setSessionID(newSessionID);
        String newClientID = endPoint.getProperty(TR069Constants.CONTEXT_KEY_CLIENTID);
        setClientID(newClientID);
        setTimestamp(System.currentTimeMillis());
        init();
    }

    public abstract void init() throws SessionException;
}
