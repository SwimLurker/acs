package org.slstudio.acs.kernal.engine;

import org.slstudio.acs.kernal.context.ISessionContext;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: обнГ11:55
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocolEngine {
    public void init();
    public void service(IProtocolEndPoint endPoint, ISessionContext sc);
}
