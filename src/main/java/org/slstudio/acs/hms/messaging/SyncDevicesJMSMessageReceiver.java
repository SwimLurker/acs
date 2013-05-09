package org.slstudio.acs.hms.messaging;

import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
import org.slstudio.acs.hms.exception.MessagingException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-9
 * Time: ÏÂÎç10:14
 */
public class SyncDevicesJMSMessageReceiver extends AbstractJMSMessageReceiver {
    IDeviceManager deviceManager = null;
    public void receive(Object message) throws MessagingException {
        List<DeviceInfo> deviceList = (List<DeviceInfo>)message;
        for(DeviceInfo di: deviceList){
            deviceManager.addDevice(di.getDeviceKey(), di);
        }
    }
}
