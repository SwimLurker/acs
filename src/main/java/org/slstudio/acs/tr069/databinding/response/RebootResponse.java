package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ����4:05
 */
public class RebootResponse extends TR069Message {

    public RebootResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_REBOOT_MESSAGERESPONSE;
    }

}