package org.slstudio.acs.tr069.xml;

import javax.xml.stream.events.XMLEvent;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç5:22
 */

/**
 * <p> The default implementation of the namespace class </p>
 */
public class NamespaceBase extends AttributeBase
        implements javax.xml.stream.events.Namespace {
    boolean declaresDefaultNamespace=false;
    public NamespaceBase(String prefix,
                         String namespaceURI)
    {
        super("xmlns",
                prefix,
                namespaceURI);
        declaresDefaultNamespace=false;
    }

    public NamespaceBase(String namespaceURI)
    {
        super("xmlns",
                "",
                namespaceURI);
        declaresDefaultNamespace=true;
    }

    public int getEventType() { return XMLEvent.NAMESPACE; }
    public boolean isAttribute() { return false; }
    public boolean isNamespace() { return true; }
    public String getPrefix() {
        if (declaresDefaultNamespace) return "";
        return super.getLocalName();
    }
    public String getNamespaceURI() {
        return super.getValue();
    }
    public boolean isDefaultNamespaceDeclaration() {
        return declaresDefaultNamespace;
    }
    public String toString() {
        if (declaresDefaultNamespace)
            return "xmlns='"+getNamespaceURI()+"'";
        else
            return "xmlns:"+getPrefix()+"='"+getNamespaceURI()+"'";
    }

}

