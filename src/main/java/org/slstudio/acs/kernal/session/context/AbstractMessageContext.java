package org.slstudio.acs.kernal.session.context;

import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.MessageException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç3:16
 */
public abstract class AbstractMessageContext implements IMessageContext{
    private ISessionContext sessionContext = null;
    private InputStream inputStream = null;
    private String response = null;
    private int lastErrorCode = ACSConstants.ERROR_CODE_SUCCESS;
    private IProtocolEndPoint endPoint = null;
    private Map properties = new HashMap();

    protected AbstractMessageContext(ISessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    public ISessionContext getSessionContext(){
        return sessionContext;
    }
    public void setSessionContext(ISessionContext sessionContext){
        this.sessionContext = sessionContext;
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

    public int getLastErrorCode(){
        return lastErrorCode;
    }

    public void setErrorCode(int errorCode){
        this.lastErrorCode = errorCode;
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

    public void initMessageContext(IProtocolEndPoint endPoint) throws MessageException {
        setEndPoint(endPoint);
        properties.clear();
        try{
            InputStream is = endPoint.getInputStream();
            setInputStream(is);
        }catch(IOException exp){
            throw new MessageException("retrieve inputstream from endpoint error", exp);
        }
        setErrorCode(ACSConstants.ERROR_CODE_SUCCESS);
        setResponse(null);
        init(endPoint);
    }

    protected abstract void init(IProtocolEndPoint endPoint) throws MessageException;
}
