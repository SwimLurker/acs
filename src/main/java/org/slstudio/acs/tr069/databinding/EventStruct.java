package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.utils.ConverterUtil;
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
 * Time: ÉÏÎç3:25
 */
public class EventStruct implements Serializable {
    private String eventCode ;
    private String commandKey ;

    public EventStruct() {
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public static EventStruct fromOMElement(OMElement element) throws DataBindingException {
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

    public static EventStruct fromXMLStreamReader(XMLStreamReader reader) throws DataBindingException{
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

}
