package org.slstudio.acs.tr069.session.context;

import org.slstudio.acs.hms.device.DeviceID;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.engine.TR069ProtocolEngine;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-23
 * Time: ÏÂÎç11:57
 * To change this template use File | Settings | File Templates.
 */
public interface ITR069SessionContext extends ISessionContext {
    public String getClientIP();
    public void setClientIP(String clientIP);
    public int getClientPort();
    public void setClientPort(int port);
    public InformRequest getInformRequest();
    public void setInformRequest(InformRequest informRequest);
    public DeviceID getDeviceID();
    public void setDeviceID(DeviceID deviceID);
    public ITR069MessageContext getCurrentTR069MessageContext();
    public List<ITR069MessageContext> getTR069MessageContextList();
    public TR069ProtocolEngine getTR069Engine();
}
