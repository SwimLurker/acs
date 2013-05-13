package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;
import org.slstudio.acs.tr069.soap.SOAPUtil;
import org.slstudio.acs.util.Tuple;

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

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERVALUES_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<ParameterList xsi:type=\"SOAP-ENC:Array\" SOAP-ENC:arrayType=\"").append(TR069Constants.NAMESPACE_CWMP).append(":ParameterValueStruct[").append(parameterList.size()).append("]\">");
        for(ParameterValueStruct pvs: parameterList) {
            result.append("<Name>").append(pvs.getName()==null?"":pvs.getName()).append("</Name>");
            Object value = pvs.getValue();
            Tuple.Tuple2<String, String> t = SOAPUtil.getObjectTypeAndValue(value);
            result.append("<Value xsi:type=\"xsd:").append(t._1()).append("\" >").append(t._2()==null?"":t._2()).append("</Value>");
        }
        result.append("</ParameterList>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static GetParameterValuesResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        GetParameterValuesResponse gpvr = new GetParameterValuesResponse();
        populateHeaderValues(gpvr, envelope.getHeader());

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
                gpvr.getParameterList().add(ParameterValueStruct.fromOMElement(paramOE));
            }
        }
        return gpvr;
    }

}

