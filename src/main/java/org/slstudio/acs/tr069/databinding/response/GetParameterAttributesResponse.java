package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterAttributeStruct;
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
 * Time: ÏÂÎç3:54
 */
public class GetParameterAttributesResponse extends TR069Message {
    private List<ParameterAttributeStruct> parameterList = new ArrayList<ParameterAttributeStruct>();

    public List<ParameterAttributeStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterAttributeStruct> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERATTRIBUTES_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":ParameterAttributeStruct[").append(parameterList.size()).append("]\">");
        for(ParameterAttributeStruct pas: parameterList) {
            result.append("<ParameterAttributeStruct>");
            result.append("<Name>").append(pas.getName()==null?"":pas.getName()).append("</Name>");
            result.append("<Notification>").append(ConverterUtil.convertToString(pas.getNotification())).append("</Notification>");
            result.append("<AccessList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"xsd:string[").append(pas.getAccessList().size()).append("]\">");
            for(String access: pas.getAccessList()) {
                result.append("<string>").append(access==null?"":access).append("</string>");
            }
            result.append("</AccessList>");
            result.append("</ParameterAttributeStruct>");
        }
        result.append("</ParameterList>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static GetParameterAttributesResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        GetParameterAttributesResponse gpar = new GetParameterAttributesResponse();
        populateHeaderValues(gpar, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //parameter value list
        Iterator paramListIt= element.getChildrenWithName(
                new QName("ParameterList"));

        if(paramListIt == null || !paramListIt.hasNext())
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,
                    "parameter list is null");

        OMElement paramListOE = (OMElement)paramListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("ParameterAttributeStruct"));
        if(paramIt!=null){
            while(paramIt.hasNext()){
                OMElement paramOE=(OMElement)paramIt.next();
                gpar.getParameterList().add(ParameterAttributeStruct.fromOMElement(paramOE));
            }
        }
        return gpar;
    }

}

