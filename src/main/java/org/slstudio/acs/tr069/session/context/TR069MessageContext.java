package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.session.context.AbstractMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.tr069.config.TR069Config;
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

    private int maxReceivedEnvelopeCount = 1;
    private int maxSendEnvelopeCount = 1;
    private int canSendEnvelopeCount = -1;

    public int getMaxReceiveEnvelopeCount() {
        return maxReceivedEnvelopeCount;
    }

    public void setMaxReceiveEnvelopeCount(int maxReceiveEnvelopeCount) {
        this.maxReceivedEnvelopeCount = maxReceiveEnvelopeCount;
    }

    public int getMaxSendEnvelopeCount() {
        return maxSendEnvelopeCount;
    }

    public void setMaxSendEnvelopeCount(int maxSendEnvelopeCount) {
        this.maxSendEnvelopeCount = maxSendEnvelopeCount;
    }

    public int getCanSendEnvelopeCount(){
        return canSendEnvelopeCount;
    }
    public void setCanSendEnvelopeCount(int canSendEnvelopeCount){
        this.canSendEnvelopeCount = canSendEnvelopeCount;
    }

    public TR069MessageContext(ISessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init(IProtocolEndPoint endPoint) throws ContextException {
        setMaxReceiveEnvelopeCount(TR069Config.getMaxReceiveEnvelopeCount());
        setMaxSendEnvelopeCount(TR069Config.getMaxSendEnvelopeCount());
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
