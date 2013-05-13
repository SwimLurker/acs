package org.slstudio.acs.tr069.databinding.request;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÏÂÎç11:58
 */
public class ScheduleInformRequest extends TR069Message {
    private UnsignedInt delaySeconds = null;
    private String commandKey = null;

    public UnsignedInt getDelaySeconds() {
        return delaySeconds;
    }

    public void setDelaySeconds(UnsignedInt delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SCHEDULEINFORM_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<DelaySeconds>").append(delaySeconds==null?"0":ConverterUtil.convertToString(delaySeconds)).append("</DelaySeconds>");
        result.append("<CommandKey>").append(commandKey==null?"":commandKey).append("</CommandKey>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }
}



