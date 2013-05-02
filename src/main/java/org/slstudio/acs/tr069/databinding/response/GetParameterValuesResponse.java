package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
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
 * Time: ÏÂÎç3:59
 */
public class GetParameterValuesResponse  extends TR069Message {
    private List<ParameterValueStruct> parameterList=new ArrayList<ParameterValueStruct>() ;

    public List<ParameterValueStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterValueStruct> parameterList) {
        this.parameterList = parameterList;
    }

    public GetParameterValuesResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
        OMElement element=envelope.getBody().getFirstElement();
        //parameter value list
        Iterator paramListIt= element.getChildrenWithName(new QName("ParameterList"));
        if(paramListIt==null||!paramListIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter list is null");
        }
        OMElement paramListOE=(OMElement)paramListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("ParameterValueStruct"));
        if(paramIt!=null){
            while(paramIt.hasNext()){
                OMElement paramOE=(OMElement)paramIt.next();
                this.parameterList.add(new ParameterValueStruct(paramOE));
            }
        }

    }

}

