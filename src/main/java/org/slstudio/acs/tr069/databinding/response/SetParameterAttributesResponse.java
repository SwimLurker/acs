package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;



/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: обнГ4:06
 */
public class SetParameterAttributesResponse extends TR069Message {
    public SetParameterAttributesResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
    }
}