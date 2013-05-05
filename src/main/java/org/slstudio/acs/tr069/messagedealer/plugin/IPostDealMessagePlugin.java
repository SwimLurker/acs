package org.slstudio.acs.tr069.messagedealer.plugin;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ1:12
 */
public interface IPostDealMessagePlugin {
    public void execute(ITR069MessageContext context, SOAPMessage soapMessage, TR069Message tr069Message);
}
