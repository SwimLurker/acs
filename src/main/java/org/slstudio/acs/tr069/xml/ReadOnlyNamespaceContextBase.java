package org.slstudio.acs.tr069.xml;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç5:23
 */

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.HashSet;
import java.util.Iterator;

/**
 * <p> This class provides a ReadOnlyNamespace context that
 * takes a snapshot of the current namespaces in scope </p>
 */

public class ReadOnlyNamespaceContextBase
        implements NamespaceContext {
    private String[] prefixes;
    private String[] uris;

    public ReadOnlyNamespaceContextBase(String[] prefixArray,
                                        String[] uriArray,
                                        int size)
    {
        prefixes = new String[size];
        uris = new String[size];
        System.arraycopy(prefixArray, 0, prefixes, 0, prefixes.length);
        System.arraycopy(uriArray, 0, uris, 0, uris.length);
    }

    public String getNamespaceURI(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("Prefix may not be null.");
        if(!"".equals(prefix)) {
            for( int i = uris.length -1; i >= 0; i--) {
                if( prefix.equals( prefixes[ i ] ) ) {
                    return uris[ i ];
                }
            }
            if("xml".equals( prefix )) {
                return XMLConstants.XML_NS_URI;
            } else if("xmlns".equals( prefix )) {
                return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
            }
        } else {
            for( int i = uris.length -1; i >= 0; i--) {
                if( prefixes[ i ]  == null ) {
                    return uris[ i ];
                }
            }
        }
        return null;
    }
    public String getPrefix(String uri) {
        if (uri == null)
            throw new IllegalArgumentException("uri may not be null");
        if ("".equals(uri))
            throw new IllegalArgumentException("uri may not be empty string");

        if(uri != null) {
            for( int i = uris.length -1; i >= 0; i--) {
                if( uri.equals( uris[ i ] ) ) {
                    return checkNull(prefixes[ i ]);
                }
            }
        }
        return null;
    }

    public String getDefaultNameSpace() {
        for( int i = uris.length -1; i >= 0; i--) {
            if( prefixes[ i ]  == null ) {
                return uris[ i ];
            }
        }
        return null;
    }

    private String checkNull(String s) {
        if (s == null) return "";
        return s;
    }

    public Iterator getPrefixes(String uri) {
        if (uri == null)
            throw new IllegalArgumentException("uri may not be null");
        if ("".equals(uri))
            throw new IllegalArgumentException("uri may not be empty string");
        HashSet s = new HashSet();
        for( int i = uris.length -1; i >= 0; i--) {
            String prefix = checkNull(prefixes[i]);
            if( uri.equals( uris[ i ] ) && !s.contains(prefix)) {
                s.add(prefix);
            }
        }
        return s.iterator();
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        for (int i=0; i < uris.length; i++) {
            b.append("["+checkNull(prefixes[i])+"<->"+uris[i]+"]");
        }
        return b.toString();
    }

}
