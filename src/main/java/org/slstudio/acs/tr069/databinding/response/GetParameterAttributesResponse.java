package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
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
    private List<ParameterAttributeStruct> attributes = new ArrayList<ParameterAttributeStruct>();

    public List<ParameterAttributeStruct> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ParameterAttributeStruct> attributes) {
        this.attributes = attributes;
    }

    public GetParameterAttributesResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
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
                this.attributes.add(new ParameterAttributeStruct(paramOE));
            }
        }
    }
}

