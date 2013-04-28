package org.slstudio.acs.tr069.fault;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.TR069Exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç3:14
 */
public class TR069Fault extends TR069Exception {

    private boolean bFromClient =true;
    private String faultCode=null;
    private String faultString=null;
    private String operationID=null;

    public TR069Fault(boolean bFromClient, String faultCode){
        super(FaultUtil.findServerFaultMessage(faultCode));
        this.bFromClient = bFromClient;
        this.faultCode = faultCode;
        this.faultString = FaultUtil.findServerFaultMessage(faultCode);
    }
    public TR069Fault(boolean bFromClient, String faultCode,Throwable cause){
        super(cause);
        this.bFromClient = bFromClient;
        this.faultCode = faultCode;
        this.faultString = FaultUtil.findServerFaultMessage(faultCode);
    }

    public TR069Fault(boolean bFromClient, String faultCode, String faultString) {
        super(faultString);
        this.bFromClient = bFromClient;
        this.faultCode = faultCode;
        this.faultString = faultString;
    }


    public TR069Fault(boolean bFromClient, String faultCode, String faultString,String operationID) {
        super(faultString);
        this.bFromClient = bFromClient;
        this.faultCode = faultCode;
        this.faultString = faultString;
        this.operationID = operationID;
    }

    public TR069Fault(boolean bFromClient, String faultCode, String faultString,Throwable cause) {
        super(cause);
        this.bFromClient = bFromClient;
        this.faultCode = faultCode;
        this.faultString = faultString;
    }

    public TR069Fault(boolean bFromClient, String faultCode, String faultString,String operationID,Throwable cause) {
        super(cause);
        this.bFromClient = bFromClient;
        this.faultCode = faultCode;
        this.faultString = faultString;
        this.operationID = operationID;
    }

    public boolean isbFromClient() {
        return bFromClient;
    }

    public void setbClientFault(boolean bClientFault) {
        this.bFromClient = bClientFault;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }

    public String getOperationID() {
        return operationID;
    }

    public void setOperationID(String operationID) {
        this.operationID = operationID;
    }

    public String toFault() {
        StringBuffer result=new StringBuffer();
        result.append("<soap:Envelope xmlns:soap=\"");
        result.append(TR069Constants.SOAP_NAMESPACE);
        result.append("\" soap:encodingStyle=\"");
        result.append(TR069Constants.SOAP_ENCODING);
        result.append("\" xmlns:cwmp=\"");
        result.append(TR069Constants.TR069_NAMESPACE);
        result.append("\">\n");
        result.append("<soap:Header>\n");
        if(operationID!=null){
            result.append("<cwmp:ID soap:mustUnderstand=\"1\">");
            result.append(operationID);
            result.append("</cwmp:ID>\n");
        }
        result.append("</soap:Header>\n");
        result.append("<soap:Body>\n");
        result.append("<soap:Fault>\n");
        result.append("<faultcode>");
        if(bFromClient){
            result.append(TR069Constants.SOAP_FAULTCODE_CLIENT);
        }else{
            result.append(TR069Constants.SOAP_FAULTCODE_SERVER);
        }
        result.append("</faultcode>\n");
        result.append("<faultstring>");
        result.append(TR069Constants.TR069_SOAP_FAULTSTRING);
        result.append("</faultstring>\n");
        result.append("<detail>\n");
        result.append("<cwmp:Fault>\n");
        result.append("<FaultCode>");
        result.append(faultCode);
        result.append("</FaultCode>\n");
        result.append("<FaultString>");
        result.append(faultString);
        result.append("</FaultString>\n");
        result.append("</cwmp:Fault>\n");
        result.append("</detail>\n");
        result.append("</soap:Fault>\n");
        result.append("</soap:Body>\n");
        result.append("</soap:Envelope>");
        return result.toString();
    }
}
