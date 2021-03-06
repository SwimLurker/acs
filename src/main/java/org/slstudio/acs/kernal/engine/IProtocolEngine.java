package org.slstudio.acs.kernal.engine;

import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: ����11:55
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocolEngine {
    public void init();
    public String getEngineID();
    public void service(IProtocolEndPoint endPoint) throws ACSException;
}
