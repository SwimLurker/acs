package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ионГ2:41
 */
public interface ITR069MessageContext extends IMessageContext {
    public List<SOAPMessage> getSoapMessageList();
    public void setSoapMessageList(List<SOAPMessage> messageList);
    public String getResponseString();
    public void setResponseString(String responseString);
    public ITR069SessionContext getTR069SessionContext();
}
