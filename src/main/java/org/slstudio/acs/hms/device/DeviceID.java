package org.slstudio.acs.hms.device;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç10:05
 */
public class DeviceID implements Serializable {
    private String deviceKey = null;

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String toString() {
        return "DeviceKey:" + deviceKey;
    }

    public DeviceID() {

    }

    public DeviceID(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DeviceID)) {
            return false;
        }

        DeviceID device = (DeviceID) o;
        return !(device.getDeviceKey() == null || this.getDeviceKey() == null) && this.getDeviceKey().toLowerCase().equals(device.getDeviceKey().toLowerCase());
    }

    public int hashCode() {
        int hash = 1;
        if (deviceKey != null) {
            hash = hash * 31 + deviceKey.toLowerCase().hashCode();
        }
        return hash;
    }
}

