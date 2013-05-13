package org.slstudio.acs.tr069.databinding.request;

import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÏÂÎç11:50
 */
public class GetParameterNamesRequest extends TR069Message {
    private String parameterPath = null;
    private boolean nextLevel =true;

    public String getParameterPath() {
        return parameterPath;
    }

    public void setParameterPath(String parameterPath) {
        this.parameterPath = parameterPath;
    }

    public Boolean getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Boolean nextLevel) {
        this.nextLevel = nextLevel;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERNAMES_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterPath>").append(parameterPath==null?"":parameterPath).append("</ParameterPath>");
        result.append("<NextLevel>").append(ConverterUtil.convertToString(nextLevel)).append("</NextLevel>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }
}

