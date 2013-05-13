package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterFaultStruct;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:07
 */
public class SetParameterValuesFaultResponse extends FaultResponse {
    private List<ParameterFaultStruct> parameterFaultList=new ArrayList<ParameterFaultStruct>();

    public List<ParameterFaultStruct> getParameterFaultList() {
        return parameterFaultList;
    }

    public void setParameterFaultList(List<ParameterFaultStruct> parameterFaultList) {
        this.parameterFaultList = parameterFaultList;
    }

     @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGEFAULT;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<SOAP-ENV:Fault>");
        result.append("<faultcode>").append(isFromClient()?TR069Constants.SOAP_FAULTCODE_CLIENT:TR069Constants.SOAP_FAULTCODE_SERVER).append("</faultcode>");
        result.append("<faultstring>").append(TR069Constants.TR069_SOAP_FAULTSTRING).append("</faultstring>");
        result.append("<detail>");
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":Fault>");
        result.append("<FaultCode>").append(getFaultCode()==null?"": ConverterUtil.convertToString(getFaultCode())).append("</FaultCode>");
        result.append("<FaultString>").append(getFaultString()==null?"":getFaultString()).append("</FaultString>");
        for(ParameterFaultStruct pfs: parameterFaultList){
            result.append("<").append(getMessageName()).append(">");
            result.append("<ParameterName>").append(pfs.getParameterName()==null?"":pfs.getParameterName()).append("</ParameterName>");
            result.append("<FaultCode>").append(pfs.getFaultCode()==null?"0":ConverterUtil.convertToString(pfs.getParameterName())).append("</FaultCode>");
            result.append("<FaultString>").append(pfs.getFaultString() == null ? "" : pfs.getFaultString()).append("</FaultString>");
            result.append("</").append(getMessageName()).append(">");
        }
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":Fault>");
        result.append("</detail>");
        result.append("</SOAP-ENV:Fault>");
        return result.toString();
    }

    public static SetParameterValuesFaultResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        FaultResponse fr = FaultResponse.fromEnvelope(envelope);
        SetParameterValuesFaultResponse spvfr = new SetParameterValuesFaultResponse();
        spvfr.setFaultCode(fr.getFaultCode());
        spvfr.setFaultString(fr.getFaultString());

        populateHeaderValues(spvfr, envelope.getHeader());

        SOAPFault fault=envelope.getBody().getFault();
        OMElement element=fault.getDetail().getFirstElement();
        //parameter fault list
        Iterator paramIt= element.getChildrenWithName(new QName("SetParameterValuesFault"));
        if(paramIt==null||!paramIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter fault list is null");
        }
        while(paramIt.hasNext()){
            OMElement paramOE=(OMElement)paramIt.next();
            spvfr.getParameterFaultList().add(ParameterFaultStruct.fromOMElement(paramOE));
        }
        return spvfr;
    }

}