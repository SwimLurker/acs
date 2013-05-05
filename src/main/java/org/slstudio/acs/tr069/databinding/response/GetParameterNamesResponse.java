package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
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
    private List<ParameterInfoStruct> parameters = new ArrayList<ParameterInfoStruct>();

    public List<ParameterInfoStruct> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterInfoStruct> parameters) {
        this.parameters = parameters;
    }

    public GetParameterNamesResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
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
                this.parameters.add(new ParameterInfoStruct(paramOE));
            }
        }
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERNAMES_MESSAGERESPONSE;
    }
}

