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
    private boolean fromClient = false;
    private UnsignedInt faultCode=null;
    private String faultString=null;

    public boolean isFromClient() {
        return fromClient;
    }

    public void setFromClient(boolean fromClient) {
        this.fromClient = fromClient;
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

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_MESSAGEFAULT;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<SOAP-ENV:Fault>");
        result.append("<faultcode>").append(fromClient?TR069Constants.SOAP_FAULTCODE_CLIENT:TR069Constants.SOAP_FAULTCODE_SERVER).append("</faultcode>");
        result.append("<faultstring>").append(TR069Constants.TR069_SOAP_FAULTSTRING).append("</faultstring>");
        result.append("<detail>");
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<FaultCode>").append(faultCode==null?"": ConverterUtil.convertToString(faultCode)).append("</FaultCode>");
        result.append("<FaultString>").append(faultString==null?"":faultString).append("</FaultString>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("</detail>");
        result.append("</SOAP-ENV:Fault>");
        return result.toString();
    }

    public static FaultResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        FaultResponse fr = new FaultResponse();
        populateHeaderValues(fr, envelope.getHeader());

        SOAPFault fault=envelope.getBody().getFault();
        OMElement element=fault.getDetail().getFirstElement();
        //faultCode
        Iterator fCodeKeyIt= element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeKeyIt==null||!fCodeKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"fault code is null");
        }
        fr.setFaultCode(ConverterUtil.convertToUnsignedInt(((OMElement) fCodeKeyIt.next()).getText()));
        //faultString
        Iterator fStringKeyIt= element.getChildrenWithName(new QName("FaultString"));
        if(fStringKeyIt!=null&&fStringKeyIt.hasNext()){
            fr.setFaultString(((OMElement)fStringKeyIt.next()).getText());
        }
        return fr;
    }

}

