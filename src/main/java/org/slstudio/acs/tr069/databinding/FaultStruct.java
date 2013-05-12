package org.slstudio.acs.tr069.databinding;

import org.apache.axis2.databinding.types.UnsignedInt;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:29
 */
public class FaultStruct implements Serializable {
    private UnsignedInt faultCode;
    private String faultString;

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
}

