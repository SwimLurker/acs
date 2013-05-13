package org.slstudio.acs.tr069.databinding.request;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÏÂÎç11:48
 */
public class GetParameterAttributesRequest extends TR069Message {
    private List<String> parameterNames = new ArrayList<String>();

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<String> parameterNames) {
        this.parameterNames = parameterNames;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERATTRIBUTES_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterNames xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"xsd:string[").append(parameterNames.size()).append("]\">");
        for(String name: parameterNames) {
            result.append("<string>").append(name==null?"":name).append("</string>");
        }
        result.append("</ParameterNames>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }
}


