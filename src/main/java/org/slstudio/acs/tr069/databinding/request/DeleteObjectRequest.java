package org.slstudio.acs.tr069.databinding.request;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÏÂÎç11:28
 */
public class DeleteObjectRequest extends TR069Message {
    private String objectName = null;
    private String parameterKey = null;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_DELETEOBJECT_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ObjectName>").append(objectName==null?"":objectName).append("</ObjectName>");
        result.append("<ParameterKey>").append(parameterKey==null?"":parameterKey).append("</ParameterKey>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

}
