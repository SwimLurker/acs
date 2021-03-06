package org.slstudio.acs.tr069.session.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;
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
 * Time: ����2:35
 */

@JsonAutoDetect(value = JsonMethod.GETTER)
public class TR069MessageContext extends AbstractMessageContext implements ITR069MessageContext {
    private static final Log log = LogFactory.getLog(TR069MessageContext.class);

    private int canSendEnvelopeCount = -1;

    public int getCanSendEnvelopeCount(){
        return canSendEnvelopeCount;
    }

    public void setCanSendEnvelopeCount(int canSendEnvelopeCount){
        this.canSendEnvelopeCount = canSendEnvelopeCount;
        log.debug("set can send evenlope count to " + canSendEnvelopeCount);
    }

    public TR069MessageContext(ISessionContext sessionContext) {
        super(sessionContext);
    }

    @Override
    public void init(IProtocolEndPoint endPoint) throws ContextException {
        setCanSendEnvelopeCount(getTR069SessionContext().getMaxSendEnvelopeCount());
    }

    @JsonIgnore
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

    @JsonIgnore
    public ITR069SessionContext getTR069SessionContext() {
        return (ITR069SessionContext)getSessionContext();
    }
}
