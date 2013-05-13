package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç3:27
 */
public class ParameterValueStruct implements Serializable {
    private static final Log log = LogFactory.getLog(ParameterValueStruct.class);

    private String name ;
    private Object value ;

    public ParameterValueStruct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public static ParameterValueStruct fromOMElement(OMElement element) throws DataBindingException {
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

    public static ParameterValueStruct fromXMLStreamReader(XMLStreamReader reader) throws DataBindingException{
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


}

