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
    private List<String> methods = new ArrayList<String>();

    public List<String> getMethods() {
        return methods;
    }

    public GetRPCMethodsResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
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
                this.methods.add(paramOE.getText());
            }
        }
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETRPCMETHODS_MESSAGERESPONSE;
    }
}
