package org.slstudio.acs.hms.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.alarm.AlarmParameterDictionary;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.bean.DeviceAlarmBean;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.EventStruct;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.event.IMessageEventListener;
import org.slstudio.acs.tr069.event.ITR069Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÏÂÎç1:43
 */
public class AlarmReportListener implements IMessageEventListener {
    private static final Log log = LogFactory.getLog(AlarmReportListener.class);

    private AlarmParameterDictionary alarmDictionary = null;
    private IMessageSender alarmSender = null;

    public AlarmParameterDictionary getAlarmDictionary() {
        return alarmDictionary;
    }

    public void setAlarmDictionary(AlarmParameterDictionary alarmDictionary) {
        this.alarmDictionary = alarmDictionary;
    }

    public IMessageSender getAlarmSender() {
        return alarmSender;
    }

    public void setAlarmSender(IMessageSender alarmSender) {
        this.alarmSender = alarmSender;
    }

    public void onEvent(ITR069Event event) {
        List<ParameterValueStruct> alarmParameters = new ArrayList<ParameterValueStruct>();

        InformRequest inform = (InformRequest)event.getMessage();
        List<EventStruct> events = inform.getEventList();
        for(EventStruct e: events){
            String eventCode = e.getEventCode();
            if(TR069Constants.INFORM_EVENT_VALUE_CHANGE.equalsIgnoreCase(eventCode)){
                List<ParameterValueStruct> params = inform.getParameterList();
                for(ParameterValueStruct pvs : params){
                   if(alarmDictionary.contains(pvs.getName())){
                       alarmParameters.add(pvs);
                   }
                }
                if(alarmParameters.size() > 0){
                    DeviceAlarmBean alarmBean = new DeviceAlarmBean();
                    alarmBean.setDeviceKey(event.getMessageContext().getTR069SessionContext().getDeviceKey());
                    alarmBean.setAlarmParameters(alarmParameters);
                    try {
                        alarmSender.sendMessage(alarmBean);
                    } catch (MessagingException exp) {
                        exp.printStackTrace();
                        log.error("send alarm event failed", exp);
                    }

                }
            }
        }


    }
}
