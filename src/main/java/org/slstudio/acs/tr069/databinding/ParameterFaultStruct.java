package org.slstudio.acs.tr069.databinding;

import org.apache.axis2.databinding.types.UnsignedInt;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:36
 */
public class ParameterFaultStruct implements Serializable{
    private String parameterName=null;
    private UnsignedInt faultCode=null;
    private String faultString=null;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

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

