package org.slstudio.acs.kernal.session.contextlocator;

import org.slstudio.acs.kernal.exception.SessionException;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: обнГ11:42
 */
public interface ISessionContextLocator {
    public ISessionContext retrieve(IProtocolEndPoint endPoint) throws SessionException;
}
