package org.slstudio.acs.hms.device;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-8
 * Time: ионГ2:34
 */
public interface IDeviceManager {
    public void addDevice(String deviceKey, DeviceInfo device);
    public DeviceInfo findDevice(String deviceKey);
    public void removeDevice(String deviceKey);
    public List<DeviceInfo> getAllDeviceList();
    public void clearDevices();
}
