package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.session.context.AbstractSessionContext;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.session.factory.TR069MessageContextFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:01
 */
public class TR069SessionContext extends AbstractSessionContext implements ITR069SessionContext {

    public TR069SessionContext() {
        super(new TR069MessageContextFactory());
    }

    public String getClientIP(){
        return (String)getProperty(TR069Constants.SESSIONCONTEXT_KEY_CLIENTIP);
    }

    public void setClientIP(String clientIP){
        setProperty(TR069Constants.SESSIONCONTEXT_KEY_CLIENTIP, clientIP);
    }

    public int getClientPort(){
        int port = -1;
        Object portObj = getProperty(TR069Constants.SESSIONCONTEXT_KEY_CLIENTPORT);
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
        setProperty(TR069Constants.SESSIONCONTEXT_KEY_CLIENTPORT, new Integer(port));
    }

    public boolean hasCheckSession() {
        boolean hasCheck = false;
        Object checkObj = getProperty(TR069Constants.SESSIONCONTEXT_KEY_HASCHECKSESSION);
        if(checkObj != null){
            try{
                hasCheck = ((Boolean)checkObj).booleanValue();
            }catch (Exception exp){
            }
        }
        return hasCheck;
    }

    public void setHasCheckSession(boolean checked) {
        setProperty(TR069Constants.SESSIONCONTEXT_KEY_HASCHECKSESSION, new Boolean(checked));
    }

    public ITR069MessageContext getCurrentTR069MessageContext() {
        return (ITR069MessageContext)getCurrentMessageContext();
    }

    public List<ITR069MessageContext> getTR069MessageContextList() {
        return (List<ITR069MessageContext>) getMessageContextList();
    }

    @Override
    public void init(IProtocolEndPoint endPoint) throws ContextException {
        String clientIP = endPoint.getProperty(TR069Constants.SESSIONCONTEXT_KEY_CLIENTIP);
        setClientIP(clientIP);
        String clientPort = endPoint.getProperty(TR069Constants.SESSIONCONTEXT_KEY_CLIENTPORT);
        int port = -1;
        try{
            port = Integer.parseInt(clientPort);
        }catch (Exception exp){
            port = -1;
        }
        setClientPort(-1);
    }
}
