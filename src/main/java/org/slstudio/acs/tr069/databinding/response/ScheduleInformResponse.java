package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:06
 */
public class ScheduleInformResponse extends TR069Message {

    public ScheduleInformResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SCHEDULEINFORM_MESSAGERESPONSE;
    }

}