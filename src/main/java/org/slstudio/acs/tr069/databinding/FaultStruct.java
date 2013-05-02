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

    public FaultStruct(OMElement element) throws DataBindingException {
        Iterator fCodeIt=element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeIt==null||!fCodeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Fault code is null");
        }
        this.faultCode= ConverterUtil.convertToUnsignedInt(((OMElement) fCodeIt.next()).getText());

        Iterator fStringIt=element.getChildrenWithName(new QName("FaultString"));
        if(fStringIt==null||!fStringIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Fault string is null");
        }
        this.faultString=((OMElement)fStringIt.next()).getText();
    }
}

