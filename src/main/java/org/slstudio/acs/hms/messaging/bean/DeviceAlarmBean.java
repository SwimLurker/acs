package org.slstudio.acs.hms.messaging.bean;

import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.util.JSONUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÏÂÎç2:48
 */
public class DeviceAlarmBean extends EventBean{
    public static final String EVENT_TYPE_ALARM = "Alarm";
    private String deviceKey = null;
    private List<ParameterValueStruct> alarmParameters = null;

    public DeviceAlarmBean() {
        setEventType(EVENT_TYPE_ALARM);
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public List<ParameterValueStruct> getAlarmParameters() {
        return alarmParameters;
    }

    public void setAlarmParameters(List<ParameterValueStruct> alarmParameters) {
        this.alarmParameters = alarmParameters;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonString(this);
    }
}
