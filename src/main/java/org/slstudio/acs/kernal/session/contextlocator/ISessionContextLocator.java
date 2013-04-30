package org.slstudio.acs.kernal.session.contextlocator;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.session.context.ISessionContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: обнГ11:42
 */
public interface ISessionContextLocator {
    public ISessionContext retrieve(IProtocolEndPoint endPoint) throws ContextException;
    public void release(IProtocolEndPoint endPoint, ISessionContext context) throws ContextException;

}
