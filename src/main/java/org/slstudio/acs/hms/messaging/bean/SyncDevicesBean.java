package org.slstudio.acs.hms.messaging.bean;

import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.util.JSONUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-10
 * Time: ÉÏÎç1:11
 */
public class SyncDevicesBean {
    public static final int COMMAND_SYNC = 0;
    public static final int COMMAND_ADD = 1;
    public static final int COMMAND_REMOVE = 2;
    public static final int COMMAND_CLEAR = 3;

    private int command = COMMAND_SYNC;
    private List<DeviceInfo>  deviceList = null;

    public SyncDevicesBean() {
    }

    public SyncDevicesBean(int command, List<DeviceInfo> deviceList) {
        this.command = command;
        this.deviceList = deviceList;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public List<DeviceInfo> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceInfo> deviceList) {
        this.deviceList = deviceList;
    }

    public String toString(){
        return JSONUtil.toJsonString(this);
    }
}
