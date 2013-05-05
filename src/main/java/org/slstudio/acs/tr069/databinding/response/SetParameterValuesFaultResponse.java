package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
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

    public SetParameterValuesFaultResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
        SOAPFault fault=envelope.getBody().getFault();
        OMElement element=fault.getDetail().getFirstElement();
        //parameter fault list
        Iterator paramIt= element.getChildrenWithName(new QName("SetParameterValuesFault"));
        if(paramIt==null||!paramIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter fault list is null");
        }
        while(paramIt.hasNext()){
            OMElement paramOE=(OMElement)paramIt.next();
            this.parameterFaultList.add(new ParameterFaultStruct(paramOE));
        }
    }
    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGEFAULT;
    }
}