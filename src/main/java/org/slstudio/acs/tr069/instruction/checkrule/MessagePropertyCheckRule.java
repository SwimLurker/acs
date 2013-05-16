package org.slstudio.acs.tr069.instruction.checkrule;

import org.slstudio.acs.tr069.databinding.EventStruct;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.databinding.request.TransferCompleteRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç1:59
 */
public class MessagePropertyCheckRule implements ITR069MessageCheckRule {
    private String targetPropertyName = null;
    private String targetPropertyValue = null;

    public MessagePropertyCheckRule(String targetPropetyName, String targetPropertyValue) {
        this.targetPropertyName = targetPropetyName;
        this.targetPropertyValue = targetPropertyValue;
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public void setTargetPropertyName(String targetPropertyName) {
        this.targetPropertyName = targetPropertyName;
    }

    public String getTargetPropertyValue() {
        return targetPropertyValue;
    }

    public void setTargetPropertyValue(String targetPropertyValue) {
        this.targetPropertyValue = targetPropertyValue;
    }

    public boolean check(TR069Message request) {
        if(targetPropertyName.equalsIgnoreCase("ID")){
            return targetPropertyValue.equalsIgnoreCase(request.getMessageID());
        }
        if(request instanceof InformRequest){
            InformRequest ir = (InformRequest)request;
            if(targetPropertyName.equalsIgnoreCase("deviceID.manufacturer")){
                return targetPropertyValue.equalsIgnoreCase(ir.getDeviceId().getManufacturer());
            }else if(targetPropertyName.equalsIgnoreCase("deviceID.OUI")){
                return targetPropertyValue.equalsIgnoreCase(ir.getDeviceId().getOUI());
            }else if(targetPropertyName.equalsIgnoreCase("deviceID.productClass")){
                return targetPropertyValue.equalsIgnoreCase(ir.getDeviceId().getProductClass());
            }else if(targetPropertyName.equalsIgnoreCase("deviceID.serialNumber")){
                return targetPropertyValue.equalsIgnoreCase(ir.getDeviceId().getSerialNumber());
            }else if(targetPropertyName.equalsIgnoreCase("eventList.eventCode")){
                List<EventStruct>  eventList =  ir.getEventList();
                for(EventStruct event: eventList){
                    if(targetPropertyValue.equalsIgnoreCase(event.getEventCode())){
                        return true;
                    }
                }
                return false;
            } else if(targetPropertyName.equalsIgnoreCase("eventList.commandKey")){
                List<EventStruct>  eventList =  ir.getEventList();
                for(EventStruct event: eventList){
                    if(targetPropertyValue.equalsIgnoreCase(event.getCommandKey())){
                        return true;
                    }
                }
                return false;
            }else if(targetPropertyName.equalsIgnoreCase("retryCount")){
                return targetPropertyValue.equalsIgnoreCase(ir.getRetryCount().toString());
            }else if(targetPropertyName.equalsIgnoreCase("maxEnvelopes")){
                return targetPropertyValue.equalsIgnoreCase(ir.getMaxEnvelopes().toString());
            }else{
                List<ParameterValueStruct>  pvsList =  ir.getParameterList();
                for(ParameterValueStruct pvs: pvsList){
                    if(targetPropertyName.equalsIgnoreCase(pvs.getName()) && targetPropertyValue.equalsIgnoreCase(pvs.getName())) {
                        return true;
                    }
                }
                return false;
            }
        }else if( request instanceof TransferCompleteRequest){
            TransferCompleteRequest tcr = (TransferCompleteRequest)request;
            if(targetPropertyName.equalsIgnoreCase("commandKey")){
                return targetPropertyValue.equalsIgnoreCase(tcr.getCommandKey());
            }else if(targetPropertyName.equalsIgnoreCase("faultCode")){
                return targetPropertyValue.equalsIgnoreCase(tcr.getFaultStruct().getFaultCode().toString());
            }else if(targetPropertyName.equalsIgnoreCase("faultString")){
                return targetPropertyValue.equalsIgnoreCase(tcr.getFaultStruct().getFaultString());
            }
        }
        return false;
    }

    public String toString(){
        return "Check property rule("+ targetPropertyName + " = " + targetPropertyValue +")";
    }
}
