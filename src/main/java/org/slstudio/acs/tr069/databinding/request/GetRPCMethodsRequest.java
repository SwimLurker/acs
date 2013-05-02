package org.slstudio.acs.tr069.databinding.request;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: обнГ1:27
 */
public class GetRPCMethodsRequest extends TR069Message {
    public GetRPCMethodsRequest(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
    }
}