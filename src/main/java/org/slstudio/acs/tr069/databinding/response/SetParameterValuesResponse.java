package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:09
 */
public class SetParameterValuesResponse extends TR069Message {
    public static final int STATUS_SUCCESS=0;
    public static final int STATUS_SUCCESS_NOT_APPLIED=1;

    int status=STATUS_SUCCESS;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SetParameterValuesResponse(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
        OMElement element=envelope.getBody().getFirstElement();
        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        this.status= ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText());
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGERESPONSE;
    }
}