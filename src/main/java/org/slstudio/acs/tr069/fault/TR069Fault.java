package org.slstudio.acs.tr069.fault;

import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.tr069.databinding.response.FaultResponse;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç3:14
 */
public class TR069Fault extends ACSException {

    private FaultResponse fault = null;

    public FaultResponse getFault() {
        return fault;
    }

    public void setFault(FaultResponse fault) {
        this.fault = fault;
    }

    public TR069Fault(boolean bFromClient, String faultCode){
        super(FaultUtil.findServerFaultMessage(faultCode));
        fault = new FaultResponse();
        fault.setFromClient(bFromClient);
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(faultCode));
        fault.setFaultString(FaultUtil.findServerFaultMessage(faultCode));
    }
    public TR069Fault(boolean bFromClient, String faultCode,Throwable cause){
        super(cause);
        fault = new FaultResponse();
        fault.setFromClient(bFromClient);
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(faultCode));
        fault.setFaultString(FaultUtil.findServerFaultMessage(faultCode));
    }

    public TR069Fault(boolean bFromClient, String faultCode, String faultString) {
        super(faultString);
        fault = new FaultResponse();
        fault.setFromClient(bFromClient);
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(faultCode));
        fault.setFaultString(faultString);
    }


    public TR069Fault(boolean bFromClient, String faultCode, String faultString,String operationID) {
        super(faultString);
        fault = new FaultResponse();
        fault.setFromClient(bFromClient);
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(faultCode));
        fault.setFaultString(faultString);
        fault.setMessageID(operationID);
    }

    public TR069Fault(boolean bFromClient, String faultCode, String faultString,Throwable cause) {
        super(cause);
        fault = new FaultResponse();
        fault.setFromClient(bFromClient);
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(faultCode));
        fault.setFaultString(faultString);
    }

    public TR069Fault(boolean bFromClient, String faultCode, String faultString,String operationID,Throwable cause) {
        super(cause);
        fault = new FaultResponse();
        fault.setFromClient(bFromClient);
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(faultCode));
        fault.setFaultString(faultString);
        fault.setMessageID(operationID);
    }

    public String toFault() {
        return fault.toSOAPString();
    }
}
