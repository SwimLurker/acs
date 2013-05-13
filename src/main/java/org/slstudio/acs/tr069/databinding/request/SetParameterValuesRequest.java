package org.slstudio.acs.tr069.databinding.request;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.soap.SOAPUtil;
import org.slstudio.acs.util.Tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç12:09
 */
public class SetParameterValuesRequest extends TR069Message {
    private List<ParameterValueStruct> parameterList = new ArrayList<ParameterValueStruct>();
    private String parameterKey = null;

    public List<ParameterValueStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterValueStruct> parameterList) {
        this.parameterList = parameterList;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":ParameterValueStruct[").append(parameterList.size()).append("]\">");
        for(ParameterValueStruct pvs: parameterList) {
            result.append("<Name>").append(pvs.getName()==null?"":pvs.getName()).append("</Name>");
            Object value = pvs.getValue();
            Tuple2<String, String> t = SOAPUtil.getObjectTypeAndValue(value);
            result.append("<Value xsi:type=\"xsd:").append(t._1()).append("\" >").append(t._2()==null?"":t._2()).append("</Value>");
        }
        result.append("</ParameterList>");
        result.append("<ParameterKey>").append(parameterKey==null?"":parameterKey).append("</ParameterKey>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }
}