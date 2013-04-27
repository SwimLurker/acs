package org.slstudio.acs.tr069.xml;

import javax.xml.stream.events.XMLEvent;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç5:12
 */
public class ElementTypeNames {
    public final static String getEventTypeString(int eventType) {
        switch (eventType){
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";
            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";
            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLEvent.CHARACTERS:
                return "CHARACTERS";
            case XMLEvent.SPACE:
                return "SPACE";
            case XMLEvent.COMMENT:
                return "COMMENT";
            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLEvent.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLEvent.DTD:
                return "DTD";
            case XMLEvent.CDATA:
                return "CDATA";
            case XMLEvent.NAMESPACE:
                return "NAMESPACE";
        }
        return "UNKNOWN_EVENT_TYPE";
    }

    public static int getEventType(String val) {
        if (val.equals ("START_ELEMENT"))
            return XMLEvent.START_ELEMENT;
        if (val.equals ("SPACE"))
            return XMLEvent.SPACE;
        if (val.equals ("END_ELEMENT"))
            return XMLEvent.END_ELEMENT;
        if (val.equals ("PROCESSING_INSTRUCTION"))
            return XMLEvent.PROCESSING_INSTRUCTION;
        if (val.equals ("CHARACTERS"))
            return XMLEvent.CHARACTERS;
        if (val.equals ("COMMENT"))
            return XMLEvent.COMMENT;
        if (val.equals ("START_DOCUMENT"))
            return XMLEvent.START_DOCUMENT;
        if (val.equals ("END_DOCUMENT"))
            return XMLEvent.END_DOCUMENT;
        if (val.equals("ATTRIBUTE"))
            return XMLEvent.ATTRIBUTE;
        if (val.equals("DTD"))
            return XMLEvent.DTD;
        if (val.equals("CDATA"))
            return XMLEvent.CDATA;
        if (val.equals("NAMESPACE"))
            return XMLEvent.NAMESPACE;
        return -1;
    }
}
