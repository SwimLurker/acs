package org.slstudio.acs.tr069.databinding.response;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç1:56
 */
public class InformResponse extends TR069Message {
    private UnsignedInt maxEnvelopes ;

    public UnsignedInt getMaxEnvelopes() {
        return maxEnvelopes;
    }

    public void setMaxEnvelopes(UnsignedInt maxEnvelopes) {
        this.maxEnvelopes = maxEnvelopes;
    }


    @Override
    public String getMessageName() {
        return TR069Constants.INFORM_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<MaxEnvelopes>").append(maxEnvelopes==null?"1":ConverterUtil.convertToString(maxEnvelopes)).append("</MaxEnvelopes>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

}

