package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterInfoStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:56
 */
public class GetParameterNamesResponse extends TR069Message {
    private List<ParameterInfoStruct> parameterList = new ArrayList<ParameterInfoStruct>();

    public List<ParameterInfoStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterInfoStruct> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERNAMES_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":ParameterInfoStruct[").append(parameterList.size()).append("]\">");
        for(ParameterInfoStruct pis: parameterList) {
            result.append("<ParameterInfoStruct>");
            result.append("<Name>").append(pis.getName()==null?"":pis.getName()).append("</Name>");
            result.append("<Writable>").append(ConverterUtil.convertToString(pis.isWritable())).append("</Writable>");
            result.append("</ParameterInfoStruct>");
        }
        result.append("</ParameterList>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static GetParameterNamesResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        GetParameterNamesResponse gpnr = new GetParameterNamesResponse();
        populateHeaderValues(gpnr, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //parameter value list
        Iterator paramListIt= element.getChildrenWithName(
                new QName("ParameterList"));

        if(paramListIt == null || !paramListIt.hasNext())
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,
                    "parameter list is null");

        OMElement paramListOE = (OMElement)paramListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("ParameterInfoStruct"));
        if(paramIt!=null){
            while(paramIt.hasNext()){
                OMElement paramOE=(OMElement)paramIt.next();
                gpnr.getParameterList().add(ParameterInfoStruct.fromOMElement(paramOE));
            }
        }
        return gpnr;
    }

}

