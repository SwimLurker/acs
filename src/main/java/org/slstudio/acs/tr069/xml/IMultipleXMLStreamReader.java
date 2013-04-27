package org.slstudio.acs.tr069.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç4:59
 */
public interface IMultipleXMLStreamReader extends IMultipleXMLStreamConstants {

    public Object getProperty(java.lang.String name) throws java.lang.IllegalArgumentException;

    public int next() throws MultipleXMLStreamException;

    public void require(int type, String namespaceURI, String localName) throws MultipleXMLStreamException;


    public String getElementText() throws MultipleXMLStreamException;

    public int nextTag() throws MultipleXMLStreamException;

    public boolean hasNext() throws MultipleXMLStreamException;

    public void close() throws MultipleXMLStreamException;

    public String getNamespaceURI(String prefix);

    public boolean isStartElement();

    public boolean isEndElement();

    public boolean isCharacters();

    public boolean isWhiteSpace();

    public String getAttributeValue(String namespaceURI,
                                    String localName);

    public int getAttributeCount();

    public QName getAttributeName(int index);

    public String getAttributeNamespace(int index);

    public String getAttributeLocalName(int index);

    public String getAttributePrefix(int index);

    public String getAttributeType(int index);

    public String getAttributeValue(int index);

    public boolean isAttributeSpecified(int index);

    public int getNamespaceCount();

    public String getNamespacePrefix(int index);

    public String getNamespaceURI(int index);

    public NamespaceContext getNamespaceContext();

    public int getEventType();

    public String getText();

    public char[] getTextCharacters();

    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length)
            throws MultipleXMLStreamException;

    public int getTextStart();

    public int getTextLength();

    public String getEncoding();

    public boolean hasText();


    public ILocation getLocation();

    public QName getName();

    public String getLocalName();

    public boolean hasName();

    public String getNamespaceURI();

    public String getPrefix();

    public String getVersion();

    public boolean isStandalone();

    public boolean standaloneSet();

    public String getCharacterEncodingScheme();

    public String getPITarget();

    public String getPIData();
}
