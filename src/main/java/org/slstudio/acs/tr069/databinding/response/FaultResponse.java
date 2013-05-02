package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.Iterator;

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

    public FaultResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
        SOAPFault fault=envelope.getBody().getFault();
        OMElement element=fault.getDetail().getFirstElement();
        //faultCode
        Iterator fCodeKeyIt= element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeKeyIt==null||!fCodeKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"fault code is null");
        }
        this.faultCode= ConverterUtil.convertToUnsignedInt(((OMElement) fCodeKeyIt.next()).getText());
        //faultString
        Iterator fStringKeyIt= element.getChildrenWithName(new QName("FaultString"));
        if(fStringKeyIt!=null&&fStringKeyIt.hasNext()){
            this.faultString= ((OMElement)fStringKeyIt.next()).getText();
        }

    }
}

