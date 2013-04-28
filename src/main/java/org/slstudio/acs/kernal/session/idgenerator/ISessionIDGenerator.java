package org.slstudio.acs.kernal.session.idgenerator;

import org.slstudio.acs.kernal.exception.ContextException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ1:01
 */
public interface ISessionIDGenerator {
    public String generateSessionID() throws ContextException;
}
