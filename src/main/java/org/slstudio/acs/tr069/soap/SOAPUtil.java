package org.slstudio.acs.tr069.soap;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.util.Tuple;
import org.slstudio.acs.util.Tuple.Tuple2;
import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÉÏÎç11:52
 */
public class SOAPUtil {
    public static String getIDFromHeader(SOAPEnvelope envelope) {
        SOAPHeader header = envelope.getHeader();
        if (header == null) {
            return null;
        }
        Iterator it = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, "ID"));
        while (it.hasNext()) {
            OMElement id = (OMElement) it.next();
            String responseID = id.getText();
            return responseID;
        }
        return null;
    }

    public static String getCommandName(SOAPEnvelope envelope) {
        SOAPBody body = envelope.getBody();
        if (body == null) {
            return null;
        }
        OMElement fE = body.getFirstElement();
        if (fE == null) {
            return null;
        }
        return fE.getLocalName();
    }

    public static boolean isResponse(String commandName) {
        if (commandName == null) {
            return false;
        }
        return commandName.endsWith(TR069Constants.TR069_SOAP_RESPONSE);
    }

    public static boolean isRequest(String commandName) {
        if (commandName == null) {
            return false;
        }
        return (!commandName.endsWith(TR069Constants.TR069_SOAP_RESPONSE));
    }

    public static String toISO8601DateTime(Calendar calendar) {
        return toISO8601DateTime(calendar.getTime());
    }

    public static String toISO8601DateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(date);
    }


    public static String encodeBASE64(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static void fillSOAPMessageHeader(String requestID, Boolean holdRequests, Boolean noMoreRequests, UnsignedInt sessionTimeout, Map<String, Object> headers, StringBuilder result) {
        result.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"");
        result.append(TR069Constants.SOAP_NAMESPACE);
        result.append("\" xmlns:SOAP_ENC=\"");
        result.append(TR069Constants.SOAP_ENCODING);
        result.append("\" xmlns:xsd=\"");
        result.append(TR069Constants.SOAP_XSD_NAMESPACE);
        result.append("\" xmlns:xsi=\"");
        result.append(TR069Constants.SOAP_XSI_NAMESPACE);
        result.append("\" xmlns:").append(TR069Constants.NAMESPACE_CWMP).append("=\"");
        result.append(TR069Constants.TR069_NAMESPACE);
        result.append("\">");
        result.append("<SOAP-ENV:Header>");
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":ID SOAP-ENV:mustUnderstand=\"1\">");
        result.append(requestID);
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":ID>");
        if(holdRequests != null){
            result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":HoldRequests SOAP-ENV:mustUnderstand=\"0\">");
            result.append(ConverterUtil.convertToString(holdRequests));
            result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":HoldRequests>");
        }
        if(noMoreRequests != null){
            result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":NoMoreRequests SOAP-ENV:mustUnderstand=\"0\">");
            result.append(ConverterUtil.convertToString(noMoreRequests));
            result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":NoMoreRequests>");
        }
        if(sessionTimeout != null){
            result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":SessionTimeout SOAP-ENV:mustUnderstand=\"0\">");
            result.append(ConverterUtil.convertToString(sessionTimeout));
            result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":SessionTimeout>");
        }
        for (String name : headers.keySet()) {
            result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(name).append(" SOAP-ENV:mustUnderstand=\"0\">");
            result.append(ConverterUtil.convertToString(headers.get(name)));
            result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(name).append(">");
        }
        result.append("</SOAP-ENV:Header>");
        result.append("<SOAP-ENV:Body>");
    }

    public static void fillSOAPMessageTailer(StringBuilder result) {
        result.append("</SOAP-ENV:Body>");
        result.append("</SOAP-ENV:Envelope>");
    }

    public static Tuple2<String,String> getObjectTypeAndValue(Object value) {
        if(value == null){
            return Tuple.tuple("string","");
        }else if(value instanceof String){
            return Tuple.tuple("string", (String)value);
        }else if(value instanceof Integer){
            return Tuple.tuple("int", ((Integer)value).toString());
        }else if(value instanceof UnsignedInt){
            return Tuple.tuple("unsignedInt", ((UnsignedInt)value).toString());
        }else if (value instanceof Boolean){
            return Tuple.tuple("boolean", ((Boolean)value)?"1":"0");
        }else if(value instanceof Calendar){
            return Tuple.tuple("dataTime", ConverterUtil.convertToString((Calendar) value));
        }else if(value instanceof DataHandler){
            return Tuple.tuple("base64", ConverterUtil.convertToString((DataHandler) value));
        }else if(value instanceof OMElement){
            return Tuple.tuple("object", ConverterUtil.convertToString((OMElement) value));
        }else{
            return Tuple.tuple("object", ConverterUtil.convertToString(value));
        }
    }
}