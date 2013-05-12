package org.slstudio.acs.tr069.util;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.*;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.databinding.request.TransferCompleteRequest;
import org.slstudio.acs.tr069.databinding.response.*;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-12
 * Time: ÏÂÎç3:22
 */
public class DataBindingUtil {
    public static DeviceIdStruct toDeviceIdStruct(OMElement element) throws DataBindingException{
        DeviceIdStruct idStruct = new DeviceIdStruct();

        Iterator mIt=element.getChildrenWithName(new QName("Manufacturer"));
        if(mIt==null||!mIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Manufacturer is null");
        }
        idStruct.setManufacturer(((OMElement)mIt.next()).getText());

        Iterator oIt=element.getChildrenWithName(new QName("OUI"));
        if(oIt==null||!oIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"OUI is null");
        }
        idStruct.setOUI(((OMElement)oIt.next()).getText());

        Iterator pIt=element.getChildrenWithName(new QName("ProductClass"));
        if(pIt==null||!pIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"ProductClass is null");
        }
        idStruct.setProductClass(((OMElement)pIt.next()).getText());

        Iterator sIt=element.getChildrenWithName(new QName("SerialNumber"));
        if(sIt==null||!sIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"SerialNumber is null");
        }
        idStruct.setSerialNumber(((OMElement)sIt.next()).getText());

        return idStruct;
    }

    public static DeviceIdStruct parseDeviceIdStruct(javax.xml.stream.XMLStreamReader reader) throws DataBindingException{
        DeviceIdStruct object = new DeviceIdStruct();
        try {
            int event = reader.getEventType();
            int count = 0;
            int argumentCount = 4 ;
            boolean done =false;
            //event better be a START_ELEMENT. if not we should go up to the start element here
            while (!reader.isStartElement()){
                event = reader.next();
            }
            while(!done){
                if (javax.xml.stream.XMLStreamConstants.START_ELEMENT==event){
                    if ("Manufacturer".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setManufacturer(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("OUI".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setOUI(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("ProductClass".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setProductClass(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("SerialNumber".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setSerialNumber(ConverterUtil.convertToString(content));
                        count++;
                    }
                }

                if (argumentCount==count){
                    done=true;
                }

                if (!done){
                    event = reader.next();
                }

            }

        } catch (javax.xml.stream.XMLStreamException e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,e);
        }

        return object;
    }

    public static EventStruct toEventStruct(OMElement element) throws DataBindingException {
        EventStruct event = new EventStruct();
        Iterator eIt=element.getChildrenWithName(new QName("EventCode"));
        if(eIt==null||!eIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"EventCode is null");
        }
        event.setEventCode(((OMElement) eIt.next()).getText());

        Iterator cIt=element.getChildrenWithName(new QName("CommandKey"));
        if(cIt==null||!cIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"CommandKey is null");
        }
        event.setCommandKey(((OMElement) cIt.next()).getText());
        return event;
    }

    public static EventStruct parseEventStruct(javax.xml.stream.XMLStreamReader reader) throws DataBindingException{
        EventStruct object = new EventStruct();
        try {
            int event = reader.getEventType();
            int count = 0;
            int argumentCount = 2 ;
            boolean done =false;
            //event better be a START_ELEMENT. if not we should go up to the start element here
            while (!reader.isStartElement()){
                event = reader.next();
            }
            while(!done){
                if (javax.xml.stream.XMLStreamConstants.START_ELEMENT==event){
                    if ("EventCode".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setEventCode(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("CommandKey".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setCommandKey(ConverterUtil.convertToString(content));
                        count++;
                    }
                }
                if (argumentCount==count){
                    done=true;
                }

                if (!done){
                    event = reader.next();
                }

            }

        } catch (javax.xml.stream.XMLStreamException e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING, e);
        }

        return object;
    }

    public static FaultStruct toFaultStruct(OMElement element) throws DataBindingException {
        FaultStruct fault = new FaultStruct();
        Iterator fCodeIt=element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeIt==null||!fCodeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Fault code is null");
        }
        fault.setFaultCode(ConverterUtil.convertToUnsignedInt(((OMElement) fCodeIt.next()).getText()));

        Iterator fStringIt=element.getChildrenWithName(new QName("FaultString"));
        if(fStringIt==null||!fStringIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Fault string is null");
        }
        fault.setFaultString(((OMElement)fStringIt.next()).getText());
        return fault;
    }

    public static ParameterAttributeStruct toParameterAttributeStruct(OMElement element) throws DataBindingException {
        ParameterAttributeStruct pas = new ParameterAttributeStruct();

        Iterator nIt = element.getChildrenWithName(new QName("Name"));
        if(nIt == null || !nIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING, "Name is null");

        pas.setParameterName(((OMElement) nIt.next()).getText());

        Iterator wIt = element.getChildrenWithName(new QName("Notification"));
        if(wIt == null || !wIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING,"Notification is null");
        pas.setNotification(
                Integer.parseInt(((OMElement) wIt.next()).getText()));

        Iterator aIt = element.getChildrenWithName(new QName("AccessList"));
        if(aIt == null || !aIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING,"AccessList is null");
        OMElement accessElement = (OMElement) aIt.next();
        Iterator iter = accessElement.getChildElements();
        while (iter != null && iter.hasNext()) {
            pas.getAccessList().add(((OMElement) iter.next()).getText());
        }

        return pas;
    }

    public static ParameterFaultStruct toParameterFaultStruct(OMElement element) throws DataBindingException {
        ParameterFaultStruct pfs = new ParameterFaultStruct();

        Iterator nIt=element.getChildrenWithName(new QName("ParameterName"));
        if(nIt==null||!nIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Parameter name is null");
        }
        pfs.setParameterName(((OMElement) nIt.next()).getText());

        Iterator fCodeIt=element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeIt==null||!fCodeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Fault code is null");
        }
        pfs.setFaultCode(ConverterUtil.convertToUnsignedInt(((OMElement) fCodeIt.next()).getText()));

        Iterator fStringIt=element.getChildrenWithName(new QName("FaultString"));
        if(fStringIt!=null&&fStringIt.hasNext()){
            pfs.setFaultString(((OMElement) fStringIt.next()).getText());
        }

        return pfs;
    }

    public static ParameterInfoStruct toParameterInfoStruct(OMElement element) throws DataBindingException {
        ParameterInfoStruct pis = new ParameterInfoStruct();

        Iterator nIt = element.getChildrenWithName(new QName("Name"));
        if(nIt == null || !nIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING, "Name is null");

        pis.setName(((OMElement) nIt.next()).getText());

        Iterator wIt = element.getChildrenWithName(new QName("Writable"));
        if(wIt == null || !wIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING,"Value is null");
        pis.setWritable(
                ConverterUtil.convertToBoolean(
                        ((OMElement) wIt.next()).getText()));
        return pis;
    }

    public static ParameterValueStruct toParameterValueStruct(OMElement element) throws DataBindingException {
        ParameterValueStruct pvs = new ParameterValueStruct();

        Iterator nIt=element.getChildrenWithName(new QName("Name"));
        if(nIt==null||!nIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Name is null");
        }
        pvs.setName(((OMElement) nIt.next()).getText());

        Iterator vIt=element.getChildrenWithName(new QName("Value"));
        if(vIt==null||!vIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Value is null");
        }
        OMElement valueEle=(OMElement)vIt.next();
        String type=null;

        Iterator attrIt=valueEle.getAllAttributes();
        if(attrIt!=null){
            while(attrIt.hasNext()){
                OMAttribute attr=(OMAttribute)attrIt.next();
                if(attr.getLocalName().equals("type")){
                    type=attr.getAttributeValue();
                    if(type!=null&& type.contains(":")){
                        type=type.substring(type.indexOf(":")+1);
                    }
                }
            }
        }
        String content=valueEle.getText();
        if(type==null||type.equals("")){
            //throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Value type is null");
            pvs.setValue(ConverterUtil.convertToString(content));
        }else if(type.equals("string")){
            pvs.setValue(ConverterUtil.convertToString(content));
        }else if(type.equals("int")){
            if(content.equals("")){
                pvs.setValue(null);
            }else{
                int value=0;
                try{
                    value=ConverterUtil.convertToInt(content);
                }catch(NumberFormatException exp){
                    value=(int)ConverterUtil.convertToFloat(content);
                }
                pvs.setValue(value);
            }
        }else if(type.equals("unsignedInt")){
            if(content.equals("")){
                pvs.setValue(null);
            }else{
                pvs.setValue(ConverterUtil.convertToUnsignedInt(content));
            }
        }else if(type.equals("boolean")){
            if(content.equals("")){
                pvs.setValue(null);
            }else{
                pvs.setValue(ConverterUtil.convertToBoolean(content));
            }
        }else if(type.equals("dateTime")){
            if(content.equals("")){
                pvs.setValue(null);
            }else{
                try {
                    pvs.setValue(ConverterUtil.convertToDateTime(content));
                } catch (Exception e) {
                    throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Value type error",e);
                }
            }
        }else if(type.equals("base64")){
            try {
                pvs.setValue(ConverterUtil.convertToBase64Binary(content));
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Value type error",e);
            }
        }else if(type.equals("object")){
            pvs.setValue(ConverterUtil.convertToAnyType(content));
        }else if(type.startsWith("string")){
            //Add for type like string(64),only for compatible
            //TODO: remove this
            pvs.setValue(ConverterUtil.convertToString(content));
        }else{
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Value type unknown");
        }
        return pvs;
    }

    public static ParameterValueStruct parseParameterValueStruct(javax.xml.stream.XMLStreamReader reader) throws DataBindingException{
        ParameterValueStruct object = new ParameterValueStruct();
        try {
            int event = reader.getEventType();
            int count = 0;
            int argumentCount = 2 ;
            boolean done =false;
            //event better be a START_ELEMENT. if not we should go up to the start element here
            while (!reader.isStartElement()){
                event = reader.next();
            }
            while(!done){
                if (javax.xml.stream.XMLStreamConstants.START_ELEMENT==event){
                    if ("Name".equals(reader.getLocalName())){

                        String content = reader.getElementText();
                        object.setName(
                                org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                        count++;
                   }
                    if ("Value".equals(reader.getLocalName())){
                        String type=null;
                        String content = reader.getElementText();
                        int attrCount=reader.getAttributeCount();
                        for(int i=0;i<attrCount;i++){
                            String attrName=reader.getAttributeLocalName(i);
                            if(attrName.equals("type")){
                                type=reader.getAttributeValue(i);
                                if(type.contains(":")){
                                    type=type.substring(type.indexOf(":")+1);
                                }
                            }
                        }
                        if(type==null){
                            object.setValue(ConverterUtil.convertToString(content));
                        }else if(type.equals("string")){
                            object.setValue(ConverterUtil.convertToString(content));
                        }else if(type.equals("int")){
                            if(content.equals("")){
                                object.setValue(null);
                            }else{
                                object.setValue(ConverterUtil.convertToInt(content));
                            }
                        }else if(type.equals("unsignedInt")){
                            if(content.equals("")){
                                object.setValue(null);
                            }else{
                                object.setValue(ConverterUtil.convertToUnsignedInt(content));
                            }
                        }else if(type.equals("boolean")){
                            if(content.equals("")){
                                object.setValue(null);
                            }else{
                                object.setValue(ConverterUtil.convertToBoolean(content));
                            }
                        }else if(type.equals("dateTime")){
                            if(content.equals("")){
                                object.setValue(null);
                            }else{
                                try {
                                    object.setValue(ConverterUtil.convertToDateTime(content));
                                } catch (Exception e) {
                                    throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Value type error",e);
                                }
                            }
                        }else if(type.equals("base64")){
                            object.setValue(ConverterUtil.convertToBase64Binary(content));
                        }else if(type.equals("object")){
                            object.setValue(ConverterUtil.convertToAnyType(content));
                        }else if(type.startsWith("string")){
                            //Add for type like string(64),only for compatible
                            //TODO: remove this
                            object.setValue(ConverterUtil.convertToString(content));
                        }else{
                            object.setValue(ConverterUtil.convertToString(content));
                        }
                        count++;
                    }
                }
                if (argumentCount==count){
                    done=true;
                }
                if (!done){
                    event = reader.next();
                }
            }
        } catch (javax.xml.stream.XMLStreamException e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING, e);
        }
        return object;
    }

    public static AddObjectResponse toAddObjectResponse(SOAPEnvelope envelope) throws DataBindingException {
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

    public static DeleteObjectResponse toDeleteObjectResponse(SOAPEnvelope envelope) throws DataBindingException {
        DeleteObjectResponse dor = new DeleteObjectResponse();
        populateHeaderValues(dor, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();

        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        dor.setStatus(ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText()));
        return dor;
    }

    public static DownloadResponse toDownloadResponse(SOAPEnvelope envelope) throws DataBindingException {
        DownloadResponse dr = new DownloadResponse();
        populateHeaderValues(dr, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        dr.setStatus(ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText()));

        //startTime
        Iterator startTimeIt= element.getChildrenWithName(new QName("StartTime"));
        if(startTimeIt==null||!startTimeIt.hasNext()){
            dr.setStartTime(null);
        }else{
            try {
                String startTimeStr=((OMElement)startTimeIt.next()).getText();
                if(startTimeStr!=null&&(!startTimeStr.equals(""))){
                    dr.setStartTime(ConverterUtil.convertToDateTime(startTimeStr));
                }
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time format error",e);
            }
        }
        //completeTime
        Iterator completeTimeIt= element.getChildrenWithName(new QName("CompleteTime"));
        if(completeTimeIt==null||!completeTimeIt.hasNext()){
            dr.setCompleteTime(null);
        }else{
            try {
                String completeTimeStr=((OMElement)completeTimeIt.next()).getText();
                if(completeTimeStr!=null&&(!completeTimeStr.equals(""))){
                    dr.setCompleteTime(ConverterUtil.convertToDateTime(completeTimeStr));
                }
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time format error",e);
            }
        }
        return dr;
    }

    public static FactoryResetResponse toFactoryResetResponse(SOAPEnvelope envelope) throws DataBindingException {
        FactoryResetResponse frr = new FactoryResetResponse();
        populateHeaderValues(frr, envelope.getHeader());
        return frr;
    }

    public static FaultResponse toFaultResponse(SOAPEnvelope envelope) throws DataBindingException {
        FaultResponse fr = new FaultResponse();
        populateHeaderValues(fr, envelope.getHeader());

        SOAPFault fault=envelope.getBody().getFault();
        OMElement element=fault.getDetail().getFirstElement();
        //faultCode
        Iterator fCodeKeyIt= element.getChildrenWithName(new QName("FaultCode"));
        if(fCodeKeyIt==null||!fCodeKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"fault code is null");
        }
        fr.setFaultCode(ConverterUtil.convertToUnsignedInt(((OMElement) fCodeKeyIt.next()).getText()));
        //faultString
        Iterator fStringKeyIt= element.getChildrenWithName(new QName("FaultString"));
        if(fStringKeyIt!=null&&fStringKeyIt.hasNext()){
            fr.setFaultString(((OMElement)fStringKeyIt.next()).getText());
        }
        return fr;
    }

    public static GetParameterAttributesResponse toGetParameterAttributesResponse(SOAPEnvelope envelope) throws DataBindingException {
        GetParameterAttributesResponse gpar = new GetParameterAttributesResponse();
        populateHeaderValues(gpar, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //parameter value list
        Iterator paramListIt= element.getChildrenWithName(
                new QName("ParameterList"));

        if(paramListIt == null || !paramListIt.hasNext())
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,
                    "parameter list is null");

        OMElement paramListOE = (OMElement)paramListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("ParameterAttributeStruct"));
        if(paramIt!=null){
            while(paramIt.hasNext()){
                OMElement paramOE=(OMElement)paramIt.next();
                gpar.getAttributes().add(toParameterAttributeStruct(paramOE));
            }
        }
        return gpar;
    }

    public static GetParameterNamesResponse toGetParameterNamesResponse(SOAPEnvelope envelope) throws DataBindingException {
        GetParameterNamesResponse gpnr = new GetParameterNamesResponse();
        populateHeaderValues(gpnr, envelope.getHeader());

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
                gpnr.getParameters().add(toParameterInfoStruct(paramOE));
            }
        }
        return gpnr;
    }

    public static GetParameterValuesResponse toGetParameterValuesResponse(SOAPEnvelope envelope) throws DataBindingException {
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
                gpvr.getParameterList().add(toParameterValueStruct(paramOE));
            }
        }
        return gpvr;
    }

    public static GetRPCMethodsResponse toGetRPCMethodsResponse(SOAPEnvelope envelope) throws DataBindingException {
        GetRPCMethodsResponse gmr = new GetRPCMethodsResponse();
        populateHeaderValues(gmr, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //parameter value list
        Iterator methodsListIt= element.getChildrenWithName(
                new QName("MethodList"));

        if(methodsListIt == null || !methodsListIt.hasNext())
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,
                    "methods list is null");

        OMElement paramListOE = (OMElement)methodsListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("string"));
        if(paramIt!=null){
            while(paramIt.hasNext()){
                OMElement paramOE=(OMElement)paramIt.next();
                gmr.getMethods().add(paramOE.getText());
            }
        }
        return gmr;
    }

    public static RebootResponse toRebootResponse(SOAPEnvelope envelope) throws DataBindingException {
        RebootResponse rr =  new RebootResponse();
        populateHeaderValues(rr, envelope.getHeader());
        return rr;
    }

    public static ScheduleInformResponse toScheduleInformResponse(SOAPEnvelope envelope) throws DataBindingException {
        ScheduleInformResponse sir = new ScheduleInformResponse();
        populateHeaderValues(sir, envelope.getHeader());
        return sir;
    }

    public static SetParameterAttributesResponse toSetParameterAttributesResponse(SOAPEnvelope envelope) throws DataBindingException {
        SetParameterAttributesResponse spar = new SetParameterAttributesResponse();
        populateHeaderValues(spar, envelope.getHeader());
        return spar;
    }

    public static SetParameterValuesFaultResponse toSetParameterValuesFaultResponse(SOAPEnvelope envelope) throws DataBindingException {
        FaultResponse fr = toFaultResponse(envelope);
        SetParameterValuesFaultResponse spvfr = new SetParameterValuesFaultResponse();
        spvfr.setFaultCode(fr.getFaultCode());
        spvfr.setFaultString(fr.getFaultString());

        populateHeaderValues(spvfr, envelope.getHeader());

        SOAPFault fault=envelope.getBody().getFault();
        OMElement element=fault.getDetail().getFirstElement();
        //parameter fault list
        Iterator paramIt= element.getChildrenWithName(new QName("SetParameterValuesFault"));
        if(paramIt==null||!paramIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter fault list is null");
        }
        while(paramIt.hasNext()){
            OMElement paramOE=(OMElement)paramIt.next();
            spvfr.getParameterFaultList().add(toParameterFaultStruct(paramOE));
        }
        return spvfr;
    }

    public static SetParameterValuesResponse toSetParameterValuesResponse(SOAPEnvelope envelope) throws DataBindingException {
        SetParameterValuesResponse spvr = new SetParameterValuesResponse();
        populateHeaderValues(spvr, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        spvr.setStatus(ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText()));
        return spvr;
    }

    public static UploadResponse toUploadResponse(SOAPEnvelope envelope) throws DataBindingException {
        UploadResponse ur = new UploadResponse();
        populateHeaderValues(ur, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        ur.setStatus(ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText()));

        //startTime
        Iterator startTimeIt= element.getChildrenWithName(new QName("StartTime"));
        if(startTimeIt==null||!startTimeIt.hasNext()){
            ur.setStartTime(null);
        }else{
            try {
                String startTimeStr=((OMElement)startTimeIt.next()).getText();
                if(startTimeStr!=null&&(!startTimeStr.equals(""))){
                    ur.setStartTime(ConverterUtil.convertToDateTime(startTimeStr));
                }
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time format error",e);
            }
        }
        //completeTime
        Iterator completeTimeIt= element.getChildrenWithName(new QName("CompleteTime"));
        if(completeTimeIt==null||!completeTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time is null");
        }else{
            try {
                String completeTimeStr=((OMElement)completeTimeIt.next()).getText();
                if(completeTimeStr!=null&&(!completeTimeStr.equals(""))){
                    ur.setCompleteTime(ConverterUtil.convertToDateTime(completeTimeStr));
                }
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time format error",e);
            }
        }
        return ur;
    }

    public static GetRPCMethodsResponse toGetRPCMethodsRequest(SOAPEnvelope envelope) throws DataBindingException {
        GetRPCMethodsResponse gmr = new GetRPCMethodsResponse();
        populateHeaderValues(gmr, envelope.getHeader());
        return gmr;
    }

    public static InformRequest toInformRequest(SOAPEnvelope envelope) throws DataBindingException {
        InformRequest ir = new InformRequest();
        populateHeaderValues(ir, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //deviceID
        Iterator deviceIDIt= element.getChildrenWithName(new QName("DeviceId"));
        if(deviceIDIt==null||!deviceIDIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"device Id is null");
        }
        ir.setDeviceId(toDeviceIdStruct((OMElement) deviceIDIt.next()));

        //eventList
        Iterator eventListIt= element.getChildrenWithName(new QName("Event"));

        if(eventListIt==null||!eventListIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"event list is null");
        }
        OMElement eventListOE=(OMElement)eventListIt.next();

        Iterator eventIt= eventListOE.getChildrenWithName(new QName("EventStruct"));
        if(eventIt==null||!eventIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"event list is null");
        }

        while(eventIt.hasNext()){
            OMElement eventOE=(OMElement)eventIt.next();
            ir.getEventList().add(toEventStruct(eventOE));
        }

        //maxEnvelope
        Iterator maxEnIt= element.getChildrenWithName(new QName("MaxEnvelopes"));
        if(maxEnIt==null||!maxEnIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"max envelope is null");
        }
        ir.setMaxEnvelopes(ConverterUtil.convertToUnsignedInt(((OMElement) maxEnIt.next()).getText()));

        //currentTime
        Iterator curTimeIt= element.getChildrenWithName(new QName("CurrentTime"));
        if(curTimeIt==null||!curTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"current time is null");
        }
        try {
            String currentTimeStr=((OMElement)curTimeIt.next()).getText();
            if(currentTimeStr!=null&&(!currentTimeStr.equals(""))){
                ir.setCurrentTime(ConverterUtil.convertToDateTime(currentTimeStr));
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"current time format error",e);
        }

        //retryCount
        Iterator reCountIt= element.getChildrenWithName(new QName("RetryCount"));
        if(reCountIt==null||!reCountIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"retry count is null");
        }
        ir.setRetryCount(ConverterUtil.convertToUnsignedInt(((OMElement) reCountIt.next()).getText()));

        //parameter value list
        Iterator paramListIt= element.getChildrenWithName(new QName("ParameterList"));
        if(paramListIt==null||!paramListIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter list is null");
        }
        OMElement paramListOE=(OMElement)paramListIt.next();

        Iterator paramIt= paramListOE.getChildrenWithName(new QName("ParameterValueStruct"));
        if(paramIt==null||!paramIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"parameter value list is null");
        }

        while(paramIt.hasNext()){
            OMElement paramOE=(OMElement)paramIt.next();
            ir.getParameterList().add(toParameterValueStruct(paramOE));
        }
        return ir;
    }

    public static TransferCompleteRequest toTransferCompleteRequest(SOAPEnvelope envelope) throws DataBindingException {
        TransferCompleteRequest tcr = new TransferCompleteRequest();
        populateHeaderValues(tcr, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //commandKey
        Iterator cmdKeyIt= element.getChildrenWithName(new QName("CommandKey"));
        if(cmdKeyIt==null||!cmdKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"instruction key is null");
        }
        tcr.setCommandKey(((OMElement) cmdKeyIt.next()).getText());

        Iterator faultIt= element.getChildrenWithName(new QName("FaultStruct"));
        if(faultIt==null||!faultIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"fault struct is null");
        }
        tcr.setFaultStruct(toFaultStruct((OMElement) faultIt.next()));
        //startTime
        Iterator startTimeIt= element.getChildrenWithName(new QName("StartTime"));
        if(startTimeIt==null||!startTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time is null");
        }
        try {
            String startTimeStr=((OMElement)startTimeIt.next()).getText();
            if(startTimeStr!=null&&(!startTimeStr.equals(""))){
                tcr.setStartTime(ConverterUtil.convertToDateTime(startTimeStr));
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time format error",e);
        }
        //completeTime
        Iterator completeTimeIt= element.getChildrenWithName(new QName("CompleteTime"));
        if(completeTimeIt==null||!completeTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time is null");
        }
        try {
            String completeTimeStr=((OMElement)completeTimeIt.next()).getText();
            if(completeTimeStr!=null&&(!completeTimeStr.equals(""))){
                tcr.setCompleteTime(ConverterUtil.convertToDateTime(completeTimeStr));
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time format error",e);
        }
        return tcr;
    }

    private static void populateHeaderValues(TR069Message message, SOAPHeader header){
        if (header != null) {
            Iterator idIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_ID));
            while (idIt.hasNext()) {
                message.setMessageID(((OMElement) idIt.next()).getText());
            }
            Iterator hrIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_HOLDREQUESTS));
            while (hrIt.hasNext()) {
                message.setHoldRequests(ConverterUtil.convertToBoolean(((OMElement) hrIt.next()).getText()));
            }
            Iterator nmrIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_NOMOREREQUESTS));
            while (nmrIt.hasNext()) {
                message.setHoldRequests(ConverterUtil.convertToBoolean(((OMElement) nmrIt.next()).getText()));
            }
            Iterator stIt = header.getChildrenWithName(new QName(TR069Constants.TR069_NAMESPACE, TR069Constants.TR069_SOAP_HEADER_SESSIONTIMEOUT));
            while (stIt.hasNext()) {
                message.setSessionTimeout(ConverterUtil.convertToUnsignedInt(((OMElement) stIt.next()).getText()));
            }
        }
    }
}
