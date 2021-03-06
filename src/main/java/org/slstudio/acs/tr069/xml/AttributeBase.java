package org.slstudio.acs.tr069.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.Writer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ����5:20
 */
public class AttributeBase implements javax.xml.stream.events.Attribute, Location {
    private String value;
    private QName name;
    private QName attributeType;
    private String locationURI;
    private int eventType = -1;
    private int line = -1;
    private int column = -1;
    private int characterOffset = 0;

    public AttributeBase(String prefix,
                         String namespaceURI,
                         String localName,
                         String value,
                         String attributeType) {
        if (prefix == null) prefix = "";

        name = new QName(namespaceURI, localName,prefix);
        this.value = value;
        this.attributeType = new QName(attributeType);
    }

    public AttributeBase(String prefix,
                         String localName,
                         String value) {

        if (prefix == null) prefix = "";
        name = new QName("",localName,prefix);
        this.value = value;
    }

    public AttributeBase(QName name,
                         String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        if (name.getPrefix()!=null &&
                !name.getPrefix().equals(""))
            return "['"+name.getNamespaceURI()+"']:"+name.getPrefix()+":"+name.getLocalPart()+"='"+value+"'";
        return name.getLocalPart()+"='"+value+"'";
    }
    public int getLineNumber() { return line; }
    public void setLineNumber(int line) { this.line = line; }
    public int getColumnNumber() { return column; }
    public void setColumnNumber(int col) { this.column = col; }
    public int getCharacterOffset() { return characterOffset; }
    public void setCharacterOffset(int c) { characterOffset = c; }
    public String getLocationURI() { return locationURI; }
    public void setLocationURI(String uri) { locationURI = uri; }

    public int getEventType() { return XMLEvent.ATTRIBUTE; }
    public boolean hasName() { return name != null; }
    public QName getName() { return name; }
    public boolean isNamespaceDeclaration() { return false; }
    public String getLocalName() { return name.getLocalPart(); }
    public String getValue() { return value; }
    public String getDTDType() { return "CDATA"; }
    public String getNamespaceURI() { return name.getNamespaceURI();}
    public void setNamespaceURI(String uri) { name = new QName(uri,name.getLocalPart()); }

    /**
     * By default, all attributes are created as specified since RI doesn't
     * handle attribute defaulting.
     */
    public boolean isSpecified() { return true;}
    public boolean isStartElement() { return false; }
    public boolean isEndElement() { return false; }
    public boolean isEntityReference() { return false; }
    public boolean isProcessingInstruction() { return false; }
    public boolean isCharacters() { return false; }
    public boolean isAttribute() { return true; }
    public boolean isNamespace() { return false; }
    public boolean isStartDocument() { return false; }
    public boolean isEndDocument() { return false; }
    public boolean isEndEntity() { return false; }
    public boolean isStartEntity() { return false; }
    public String getPublicId() { return null; }
    public String getSystemId() { return null; }

    public Location getLocation() { return this; }
    public StartElement asStartElement() { throw new ClassCastException("cannnot cast AttributeBase to StartElement"); }
    public EndElement asEndElement() { throw new ClassCastException("cannnot cast AttributeBase to EndElement"); }
    public Characters asCharacters() { throw new ClassCastException("cannnot cast AttributeBase to Characters"); }
    public void recycle(){}
    public boolean isDefault() { return true; }
    public String getSourceName() { return null ; }
    public QName getSchemaType() { return null; }
    public void writeAsEncodedUnicode(Writer writer)
            throws javax.xml.stream.XMLStreamException {}

}