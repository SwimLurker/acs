package org.slstudio.acs.tr069.databinding.request;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.tr069.databinding.EventStruct;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:48
 */
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


    public InformRequest(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
        OMElement element=envelope.getBody().getFirstElement();
        //deviceID
        Iterator deviceIDIt= element.getChildrenWithName(new QName("DeviceId"));
        if(deviceIDIt==null||!deviceIDIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"device Id is null");
        }
        this.deviceId=new DeviceIdStruct((OMElement)deviceIDIt.next());

        //eventList
        Iterator eventListIt= element.getChildrenWithName(new QName("Event"));

        if(eventListIt==null||!eventListIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"event list is null");
        }
        OMElement eventListOE=(OMElement)eventListIt.next();

        Iterator eventIt= eventListOE.getChildrenWithName(new QName("EventStruct"));
        if(eventIt==null||!eventIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"event list is null");
        }

        while(eventIt.hasNext()){
            OMElement eventOE=(OMElement)eventIt.next();
            this.eventList.add(new EventStruct(eventOE));
        }


        //maxEnvelope
        Iterator maxEnIt= element.getChildrenWithName(new QName("MaxEnvelopes"));
        if(maxEnIt==null||!maxEnIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"max envelope is null");
        }
        this.maxEnvelopes= ConverterUtil.convertToUnsignedInt(((OMElement) maxEnIt.next()).getText());

        //currentTime
        Iterator curTimeIt= element.getChildrenWithName(new QName("CurrentTime"));
        if(curTimeIt==null||!curTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"current time is null");
        }
        try {
            String currentTimeStr=((OMElement)curTimeIt.next()).getText();
            if(currentTimeStr!=null&&(!currentTimeStr.equals(""))){
                this.currentTime= ConverterUtil.convertToDateTime(currentTimeStr);
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"current time format error",e);
        }

        //retryCount
        Iterator reCountIt= element.getChildrenWithName(new QName("RetryCount"));
        if(reCountIt==null||!reCountIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"retry count is null");
        }
        this.retryCount=ConverterUtil.convertToUnsignedInt(((OMElement) reCountIt.next()).getText());

        //parameter value list
        Iterator paramListIt= element.getChildrenWithName(new QName("ParameterList"));
        if(paramListIt==null||!paramListIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter list is null");
        }
        OMElement paramListOE=(OMElement)paramListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("ParameterValueStruct"));
        if(paramIt==null||!paramIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter value list is null");
        }

        while(paramIt.hasNext()){
            OMElement paramOE=(OMElement)paramIt.next();
            this.parameterList.add(new ParameterValueStruct(paramOE));
        }
    }


}
