package org.slstudio.acs.hms.device;

import edu.emory.mathcs.backport.java.util.Collections;

import java.text.NumberFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-8
 * Time: ÉÏÎç2:40
 */
public class MemoryDeviceManager implements IDeviceManager {
    private Map<String, DeviceInfo> deviceTable = null;

    public MemoryDeviceManager() {
        deviceTable = Collections.synchronizedMap(new HashMap<String,DeviceInfo>());
        //addTestData();
    }

    private void addTestData() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);

        for(int i=0;i<100;i++){
            DeviceInfo device = new DeviceInfo();

            device.setDeviceID("0000000"+ nf.format(i));
            device.setDeviceKey("FF00000" + nf.format(i));
            device.setAuthUsername("admin");
            device.setAuthPassword("admin");
            device.setDeviceIP("192.168.0.100");
            device.setManufacturer("FishCore");
            device.setDeviceOUI("00A00D");
            device.setProductClass("FishCore IGD Device");
            device.setSerialNumber("FF00000" + nf.format(i));
            device.setCrURL("http://192.168.0.100:9892/acscall");
            device.setCrUsername("test");
            device.setCrPassword("test");
            Date date = new Date();
            int rndValue = (int)Math.round(Math.random() * (1000000 - 1) + 1);
            date.setTime(System.currentTimeMillis() + rndValue);
            device.setLastInformTime(date);

            deviceTable.put(device.getDeviceKey(), device);
        }
    }

    public void addDevice(String deviceKey, DeviceInfo device) {
        deviceTable.put(deviceKey, device);
    }

    public DeviceInfo findDevice(String deviceKey) {
        return deviceTable.get(deviceKey);
    }

    public void removeDevice(String deviceKey) {
        deviceTable.remove(deviceKey);
    }

    public List<DeviceInfo> getAllDeviceList() {
        return new ArrayList<DeviceInfo>(deviceTable.values());
    }

    public void clearDevices() {
        deviceTable.clear();
    }
}
