package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;
import org.slstudio.acs.util.JSONUtil;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ����1:24
 */
public class AddObjectResponse extends TR069Message {
    public static final int STATUS_OBJECT_ADDED=0;
    public static final int STATUS_NEED_APPLY=1;

    private UnsignedInt instanceNumber=null;
    private int status=STATUS_OBJECT_ADDED;

    public UnsignedInt getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(UnsignedInt instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_ADDOBJECT_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<InstanceNumber>").append(instanceNumber==null?"1": ConverterUtil.convertToString(instanceNumber)).append("</InstanceNumber>");
        result.append("<Status>").append(ConverterUtil.convertToString(status)).append("</Status>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static AddObjectResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        AddObjectResponse aor = new AddObjectResponse();
        populateHeaderValues(aor, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //instance number
        Iterator insNumKeyIt= element.getChildrenWithName(new QName("InstanceNumber"));
        if(insNumKeyIt==null||!insNumKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"instance number is null");
        }
        aor.setInstanceNumber(ConverterUtil.convertToUnsignedInt(((OMElement) insNumKeyIt.next()).getText()));

        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        aor.setStatus(ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText()));
        return aor;
    }

    public static void main(String[] args) {
        AddObjectResponse aor = new AddObjectResponse();
        aor.setInstanceNumber(new UnsignedInt(1));
        aor.setStatus(0);
        System.out.println(JSONUtil.toJsonString(aor));
    }

}