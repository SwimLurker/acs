package org.slstudio.acs.tr069.messagedealer;

import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ионГ11:14
 */
public interface ITR069MethodDealer {
    public void deal(ITR069MessageContext context,SOAPMessage message);
}
