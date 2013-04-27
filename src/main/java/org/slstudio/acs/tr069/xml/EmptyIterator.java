package org.slstudio.acs.tr069.xml;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: обнГ5:13
 */
public class EmptyIterator implements java.util.Iterator {
    public static final EmptyIterator emptyIterator = new EmptyIterator();
    public boolean hasNext() { return false; }
    public Object next() { return null; }
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

