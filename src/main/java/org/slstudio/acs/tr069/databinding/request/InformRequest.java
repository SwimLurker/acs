package org.slstudio.acs.tr069.databinding.request;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.tr069.databinding.EventStruct;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:48
 */
@JsonAutoDetect
public class InformRequest extends TR069Message {

    private DeviceIdStruct deviceId ;
    private List<EventStruct> eventList=new ArrayList<EventStruct>();
    private UnsignedInt maxEnvelopes ;
    private Calendar currentTime ;
    private UnsignedInt retryCount ;
    private List<ParameterValueStruct> parameterList=new ArrayList<ParameterValueStruct>() ;


    public DeviceIdStruct getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(DeviceIdStruct deviceId) {
        this.deviceId = deviceId;
    }

    public List<EventStruct> getEventList() {
        return eventList;
    }

    public void setEventList(List<EventStruct> eventList) {
        this.eventList = eventList;
    }

    public UnsignedInt getMaxEnvelopes() {
        return maxEnvelopes;
    }

    public void setMaxEnvelopes(UnsignedInt maxEnvelopes) {
        this.maxEnvelopes = maxEnvelopes;
    }

    public Calendar getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Calendar currentTime) {
        this.currentTime = currentTime;
    }

    public UnsignedInt getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(UnsignedInt retryCount) {
        this.retryCount = retryCount;
    }

    public List<ParameterValueStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterValueStruct> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.INFORM_MESSAGE;
    }


}
