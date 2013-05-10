package org.slstudio.acs.hms.messaging.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.bean.SyncDevicesBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-9
 * Time: ÏÂÎç10:14
 */
public class SyncDevicesMessageHandler implements IMessageHandler{
    private static final Log log = LogFactory.getLog(SyncDevicesMessageHandler.class);

    IDeviceManager deviceManager = null;

    public IDeviceManager getDeviceManager() {
        return deviceManager;
    }

    public void setDeviceManager(IDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void handle(Object message) throws MessagingException {
        SyncDevicesBean sync = (SyncDevicesBean)message;
        if(sync == null){
            throw new MessagingException("sync device info error: null message");
        }
        log.debug("receive sync device info command(" + sync.getCommand() + ") with device list size:" + sync.getDeviceList().size());
        switch (sync.getCommand()){
            case SyncDevicesBean.COMMAND_ADD:
                for(DeviceInfo di: sync.getDeviceList()){
                    deviceManager.addDevice(di.getDeviceKey(), di);
                }
                break;
            case SyncDevicesBean.COMMAND_REMOVE:
                for(DeviceInfo di: sync.getDeviceList()){
                    deviceManager.removeDevice(di.getDeviceKey());
                }
                break;
            case SyncDevicesBean.COMMAND_SYNC:
                for(DeviceInfo di: sync.getDeviceList()){
                    deviceManager.addDevice(di.getDeviceKey(), di);
                }
                break;
            case SyncDevicesBean.COMMAND_CLEAR:
                deviceManager.clearDevices();
                break;
            default:
                log.error("sync device info error: unknown command");
                throw new MessagingException("sync device info error: unknown command");
        }
        log.debug("handle sync device info command successfully");
    }
}
