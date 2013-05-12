package org.slstudio.acs.tr069.databinding.response;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:53
 */
public class FaultResponse extends TR069Message {
    private UnsignedInt faultCode=null;
    private String faultString=null;

    public UnsignedInt getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(UnsignedInt faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_MESSAGEFAULT;
    }
}

