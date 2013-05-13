package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
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
 * Time: ÏÂÎç4:01
 */
public class GetRPCMethodsResponse extends TR069Message {
    private List<String> methodList = new ArrayList<String>();

    public List<String> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<String> methodList) {
        this.methodList = methodList;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETRPCMETHODS_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<MethodList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"xsd:string[").append(methodList.size()).append("]\">");
        for(String method: methodList) {
            result.append("<string>").append(method==null?"":method).append("</string>");
        }
        result.append("</MethodList>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static GetRPCMethodsResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        GetRPCMethodsResponse gmr = new GetRPCMethodsResponse();
        populateHeaderValues(gmr, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //parameter value list
        Iterator methodsListIt= element.getChildrenWithName(
                new QName("MethodList"));

        if(methodsListIt == null || !methodsListIt.hasNext())
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,
                    "methods list is null");

        OMElement paramListOE = (OMElement)methodsListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("string"));
        if(paramIt!=null){
            while(paramIt.hasNext()){
                OMElement paramOE=(OMElement)paramIt.next();
                gmr.getMethodList().add(paramOE.getText());
            }
        }
        return gmr;
    }

}
