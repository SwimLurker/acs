package org.slstudio.acs.tr069.engine;

import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.session.context.IMessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: обнГ3:57
 */
public class TR069AM3Engine extends TR069ProtocolEngine {
    @Override
    protected void beforeDoService(IMessageContext messageContext) throws ACSException {
        throw new ACSException("Unsupport TR069 version");
    }

}