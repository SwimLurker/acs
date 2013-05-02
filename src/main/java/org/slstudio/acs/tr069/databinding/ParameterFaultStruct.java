package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.Iterator;

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

    public ParameterFaultStruct(OMElement element) throws DataBindingException {
        Iterator nIt=element.getChildrenWithName(new QName("ParameterName"));
        if(nIt==null||!nIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Parameter name is null");
        }
        this.setParameterName(((OMElement)nIt.next()).getText());

        Iterator fCodeIt=element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeIt==null||!fCodeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Fault code is null");
        }
        this.setFaultCode(ConverterUtil.convertToUnsignedInt(((OMElement) fCodeIt.next()).getText()));

        Iterator fStringIt=element.getChildrenWithName(new QName("FaultString"));
        if(fStringIt!=null&&fStringIt.hasNext()){
            this.setFaultString(((OMElement)fStringIt.next()).getText());
        }
    }
}

