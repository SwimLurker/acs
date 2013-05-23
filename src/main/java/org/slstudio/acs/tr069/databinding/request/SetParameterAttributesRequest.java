package org.slstudio.acs.tr069.databinding.request;

import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.SetParameterAttributesStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç12:01
 */
public class SetParameterAttributesRequest extends TR069Message {
    private List<SetParameterAttributesStruct> parameterList = new ArrayList<SetParameterAttributesStruct>();

    public List<SetParameterAttributesStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<SetParameterAttributesStruct> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERATTRIBUTES_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":SetParameterAttributesStruct[").append(parameterList.size()).append("]\">");
        for(SetParameterAttributesStruct spaStruct: parameterList) {
            result.append("<SetParameterAttributesStruct>");
            result.append("<Name>").append(spaStruct.getName()==null?"":spaStruct.getName()).append("</Name>");
            result.append("<NotificationChange>").append(ConverterUtil.convertToString(spaStruct.getNotificationChange())).append("</NotificationChange>");
            result.append("<Notification>").append(ConverterUtil.convertToString(spaStruct.getNotification())).append("</Notification>");
            result.append("<AccessListChange>").append(ConverterUtil.convertToString(spaStruct.getAccessListChange())).append("</AccessListChange>");
            result.append("<AccessList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"xsd:string[").append(spaStruct.getAccessList().size()).append("]\">");
            for(String access: spaStruct.getAccessList()) {
                result.append("<string>").append(access==null?"":access).append("</string>");
            }
            result.append("</AccessList>");
            result.append("</SetParameterAttributesStruct>");
        }
        result.append("</ParameterList>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static void main(String[] args) {
        SetParameterAttributesRequest spar = new SetParameterAttributesRequest();
        List<SetParameterAttributesStruct> l = new ArrayList<SetParameterAttributesStruct>();
        SetParameterAttributesStruct pas = new SetParameterAttributesStruct();
        pas.setName("InternetGatewayDevice.ManagementServer.PeriodicInformInterval");
        pas.setNotification(0);
        pas.setAccessListChange(false);
        pas.setNotificationChange(false);
        l.add(pas);

        SetParameterAttributesStruct pas1 = new SetParameterAttributesStruct();
        pas1.setName("InternetGatewayDevice.LANDevice.1.Hosts.Host.1.IPAddress");
        pas.setNotification(1);
        pas.setAccessListChange(false);
        pas.setNotificationChange(false);
        l.add(pas1);
        spar.setParameterList(l);
        System.out.println(JSONUtil.toJsonString(spar));
        System.out.println(spar.toSOAPString());

    }

}