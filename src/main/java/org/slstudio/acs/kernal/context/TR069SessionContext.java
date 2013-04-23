package org.slstudio.acs.kernal.context;

import org.slstudio.acs.kernal.TR069Constants;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:01
 */
public class TR069SessionContext implements ITR069SessionContext {
    private Map properties = new HashMap();

    public int getLastErrorCode(){
        Object errorCode = getProperty(TR069Constants.CONTEXT_KEY_ERRORCODE);
        if(errorCode == null) return TR069Constants.ERROR_CODE_SUCCESS;
        return ((Integer)errorCode).intValue();
    }

    public void setErrorCode(int errorCode){
        setProperty(TR069Constants.CONTEXT_KEY_ERRORCODE, new Integer(errorCode));
    }

    public InputStream getInputStream(){
        return (InputStream)getProperty(TR069Constants.CONTEXT_KEY_INPUTSTREAM);
    }

    public void setInputStream(InputStream is){
        setProperty(TR069Constants.CONTEXT_KEY_INPUTSTREAM, is);
    }

    public String getResponse(){
        return (String)getProperty(TR069Constants.CONTEXT_KEY_RESPONSE);
    }

    public void setResponse(String response){
        setProperty(TR069Constants.CONTEXT_KEY_RESPONSE, response);
    }

    public String getClientIP(){
        return (String)getProperty(TR069Constants.CONTEXT_KEY_CLIENTIP);
    }

    public void setClientIP(String clientIP){
        setProperty(TR069Constants.CONTEXT_KEY_CLIENTIP, clientIP);
    }

    public String getSessionID(){
        return (String)getProperty(TR069Constants.CONTEXT_KEY_SESSIONID);
    }

    public void setSessionID(String sessionID){
        setProperty(TR069Constants.CONTEXT_KEY_SESSIONID, sessionID);
    }

    public Object getProperty(String key){
        return properties.get(key);
    }

    public void setProperty(String key, Object value){
        properties.put(key, value);
    }
}
