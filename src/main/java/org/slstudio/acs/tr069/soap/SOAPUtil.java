package org.slstudio.acs.tr069.soap;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeader;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.datamodel.TRParameter;
import sun.misc.BASE64Encoder;

import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

    public static void fillSOAPHeader(String requestID, StringBuffer result) {
        result.append("<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"");
        result.append(TR069Constants.SOAP_NAMESPACE);
        result.append("\" SOAP-ENV:encodingStyle=\"");
        result.append(TR069Constants.SOAP_ENCODING);
        result.append("\" xmlns:cwmp=\"");
        result.append(TR069Constants.TR069_NAMESPACE);
        result.append("\" xmlns:xsd=\"");
        result.append(TR069Constants.SOAP_XSD_NAMESPACE);
        result.append("\" xmlns:xsi=\"");
        result.append(TR069Constants.SOAP_XSI_NAMESPACE);
        result.append("\">");
        result.append("<SOAP-ENV:Header>");
        result.append("<cwmp:ID SOAP-ENV:mustUnderstand=\"1\">");
        result.append(requestID);
        result.append("</cwmp:ID>");
        result.append("</SOAP-ENV:Header>");
        result.append("<SOAP-ENV:Body>");
    }

    public static void fillSOAPTailer(StringBuffer result) {
        result.append("</SOAP-ENV:Body>");
        result.append("</SOAP-ENV:Envelope>");
    }

    public static void fillGetParameterValuesSOAPStruct(StringBuffer result, List<TRParameter> dealingParameters) {
        result.append("<cwmp:GetParameterValues>");
        result.append("<ParameterNames SOAP-ENV:arrayType=\"xsd:string[");
        result.append(dealingParameters.size());
        result.append("]\">");
        for (TRParameter paramName : dealingParameters) {
            result.append("<string>");
            result.append(paramName.getFullName().trim());
            result.append("</string>");
        }
        result.append("</ParameterNames>");
        result.append("</cwmp:GetParameterValues>");
    }

    public static void fillSetParameterValuesSOAPStruct(StringBuffer result, List<TRParameter> dealingParameters, boolean useIntAsBoolean) {
        result.append("<cwmp:SetParameterValues>");
        result.append("<ParameterList SOAP-ENV:arrayType=\"cwmp:ParameterValueStruct[");
        result.append(dealingParameters.size());
        result.append("]\">");

        for (TRParameter param :dealingParameters) {
            result.append("<ParameterValueStruct>");
            result.append("<Name>");
            result.append(param.getFullName().trim());
            result.append("</Name>");
            result.append("<Value");
            String type = param.getType();
            if (type != null) {
                result.append(" xsi:type=\"xsd:");
                result.append(type);
                result.append("\"");
            }
            result.append(">");
            if ("boolean".equalsIgnoreCase(type)) {
                if (useIntAsBoolean) {
                    if (param.getValue().equalsIgnoreCase("true")) {
                        result.append("1");
                    } else {
                        result.append("0");
                    }
                } else {
                    result.append(param.getValue());
                }
            } else {
                result.append(param.getValue());
            }
            result.append("</Value>");
            result.append("</ParameterValueStruct>");
        }
        result.append("</ParameterList>");
        result.append("<ParameterKey></ParameterKey>");
        result.append("</cwmp:SetParameterValues>");
    }

    public static void fillSetParameterAttributeSOAPStruct(StringBuffer result, List<TRParameter> dealingParameters, boolean useIntAsBoolean) {
        result.append("<cwmp:SetParameterAttributes>");
        result.append("<ParameterList SOAP-ENV:arrayType=\"cwmp:SetParameterAttributesStruct[");
        result.append(dealingParameters.size());
        result.append("]\">");
        for (TRParameter param : dealingParameters) {
            result.append("<SetParameterAttributesStruct>");
            result.append("<Name>");
            result.append(param.getFullName().trim());
            result.append("</Name>");
            result.append("<NotificationChange>");
            result.append(useIntAsBoolean ? "1" : "true");
            result.append("</NotificationChange>");
            result.append("<Notification>");
            result.append(param.getAttributeValue());
            result.append("</Notification>");
            result.append("<AccessListChange>");
            result.append("0");
            result.append("</AccessListChange>");
            result.append("<AccessList soapenc:arrayType=\"xsd:string[0]\">");
            result.append("</AccessList>");
            result.append("</SetParameterAttributesStruct>");
        }
        result.append("</ParameterList>");
        result.append("</cwmp:SetParameterAttributes>");
    }
}