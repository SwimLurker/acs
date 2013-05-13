package org.slstudio.acs.tr069.databinding.request;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.tr069.databinding.EventStruct;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;
import org.slstudio.acs.tr069.soap.SOAPUtil;
import org.slstudio.acs.util.Tuple.Tuple2;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<DeviceId>");
        result.append("<Manufacturer>").append(deviceId.getManufacturer()==null?"":deviceId.getManufacturer()).append("</Manufacturer>");
        result.append("<OUI>").append(deviceId.getOUI()==null?"":deviceId.getOUI()).append("</OUI>");
        result.append("<ProductClass>").append(deviceId.getProductClass()==null?"":deviceId.getProductClass()).append("</ProductClass>");
        result.append("<SerialNumber>").append(deviceId.getSerialNumber()==null?"":deviceId.getSerialNumber()).append("</SerialNumber>");
        result.append("</DeviceId>");
        result.append("<Event xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":EventStruct[").append(eventList.size()).append("]\">");
        for(EventStruct event: eventList) {
            result.append("<EventStruct>");
            result.append("<EventCode>").append(event.getEventCode()==null?"":event.getEventCode()).append("</EventCode>");
            result.append("<CommandKey>").append(event.getCommandKey()==null?"":event.getCommandKey()).append("</CommandKey>");
            result.append("</EventStruct>");
        }
        result.append("</Event>");
        result.append("<MaxEnvelopes>").append(maxEnvelopes==null?"1":ConverterUtil.convertToString(maxEnvelopes)).append("</MaxEnvelopes>");
        result.append("<CurrentTime>").append(currentTime==null?"":ConverterUtil.convertToString(currentTime)).append("</CurrentTime>");
        result.append("<RetryCount>").append(retryCount==null?"0":ConverterUtil.convertToString(retryCount)).append("</RetryCount>");
        result.append("<ParameterList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":ParameterValueStruct[").append(parameterList.size()).append("]\">");
        for(ParameterValueStruct pvs: parameterList){
            result.append("<ParameterValueStruct>");
            result.append("<Name>").append(pvs.getName()==null?"":pvs.getName()).append("</Name>");
            Tuple2<String, String> t = SOAPUtil.getObjectTypeAndValue(pvs.getValue());
            result.append("<Value xsi:type=\"xsd:").append(t._1()).append("\">").append(t._2()==null?"":t._2()).append("</Value>");
            result.append("</ParameterValueStruct>");
        }
        result.append("</ParameterList>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static InformRequest fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        InformRequest ir = new InformRequest();
        populateHeaderValues(ir, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //deviceID
        Iterator deviceIDIt= element.getChildrenWithName(new QName("DeviceId"));
        if(deviceIDIt==null||!deviceIDIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"device Id is null");
        }
        ir.setDeviceId(DeviceIdStruct.fromOMElement((OMElement) deviceIDIt.next()));

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
            ir.getEventList().add(EventStruct.fromOMElement(eventOE));
        }

        //maxEnvelope
        Iterator maxEnIt= element.getChildrenWithName(new QName("MaxEnvelopes"));
        if(maxEnIt==null||!maxEnIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"max envelope is null");
        }
        ir.setMaxEnvelopes(ConverterUtil.convertToUnsignedInt(((OMElement) maxEnIt.next()).getText()));

        //currentTime
        Iterator curTimeIt= element.getChildrenWithName(new QName("CurrentTime"));
        if(curTimeIt==null||!curTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"current time is null");
        }
        try {
            String currentTimeStr=((OMElement)curTimeIt.next()).getText();
            if(currentTimeStr!=null&&(!currentTimeStr.equals(""))){
                ir.setCurrentTime(ConverterUtil.convertToDateTime(currentTimeStr));
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"current time format error",e);
        }

        //retryCount
        Iterator reCountIt= element.getChildrenWithName(new QName("RetryCount"));
        if(reCountIt==null||!reCountIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"retry count is null");
        }
        ir.setRetryCount(ConverterUtil.convertToUnsignedInt(((OMElement) reCountIt.next()).getText()));

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
            ir.getParameterList().add(ParameterValueStruct.fromOMElement(paramOE));
        }
        return ir;
    }

}
