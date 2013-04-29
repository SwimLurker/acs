package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.session.context.AbstractMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.soap.SOAPMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç2:35
 */
public class TR069MessageContext extends AbstractMessageContext implements ITR069MessageContext {

    public TR069MessageContext(ISessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init(IProtocolEndPoint endPoint) throws ContextException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<SOAPMessage> getSoapMessageList() {
        Object obj = getProperty(TR069Constants.MESSAGECONTEXT_KEY_SOAPMESSAGELIST);
        if(obj != null){
            return (List<SOAPMessage>)obj;
        }
        return null;
    }

    public void setSoapMessageList(List<SOAPMessage> messageList) {
        setProperty(TR069Constants.MESSAGECONTEXT_KEY_SOAPMESSAGELIST,messageList);
    }

    public ITR069SessionContext getTR069SessionContext() {
        return (ITR069SessionContext)getSessionContext();
    }
}
