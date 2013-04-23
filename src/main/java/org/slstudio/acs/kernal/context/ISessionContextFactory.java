package org.slstudio.acs.kernal.context;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ионГ12:07
 */
public interface ISessionContextFactory {
    public ISessionContext create(IProtocolEndPoint endPoint);
}
