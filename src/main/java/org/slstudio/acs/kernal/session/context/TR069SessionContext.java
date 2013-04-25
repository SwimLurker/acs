package org.slstudio.acs.kernal.session.context;

import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.SessionException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:01
 */
public class TR069SessionContext extends SessionContextBase implements ITR069SessionContext {
    public String getClientIP(){
        return (String)getProperty(TR069Constants.CONTEXT_KEY_CLIENTIP);
    }

    public void setClientIP(String clientIP){
        setProperty(TR069Constants.CONTEXT_KEY_CLIENTIP, clientIP);
    }

    public int getClientPort(){
        int port = -1;
        Object portObj = getProperty(TR069Constants.CONTEXT_KEY_CLIENTPORT);
        if(portObj != null){
            try{
                port = ((Integer)portObj).intValue();
            }catch (Exception exp){
                port = -1;
            }
        }
        return port;
    }

    public void setClientPort(int port){
        setProperty(TR069Constants.CONTEXT_KEY_CLIENTPORT, new Integer(port));
    }

    @Override
    public void init() throws SessionException {
        IProtocolEndPoint endPoint = getEndPoint();
        String clientIP = endPoint.getProperty(TR069Constants.CONTEXT_KEY_CLIENTIP);
        setClientIP(clientIP);
        String clientPort = endPoint.getProperty(TR069Constants.CONTEXT_KEY_CLIENTPORT);
        int port = -1;
        try{
            port = Integer.parseInt(clientPort);
        }catch (Exception exp){
            port = -1;
        }
        setClientPort(-1);
    }
}
