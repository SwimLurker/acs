package org.slstudio.acs.tr069.xml;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç5:00
 */
public class MultipleXMLStreamException extends Exception {

    protected Throwable nested;
    protected ILocation location;

    /**
     * Default constructor
     */
    public MultipleXMLStreamException(){
        super();
    }

    /**
     * Construct an exception with the assocated message.
     *
     * @param msg the message to report
     */
    public MultipleXMLStreamException(String msg) {
        super(msg);
    }

    /**
     * Construct an exception with the assocated exception
     *
     * @param th a nested exception
     */
    public MultipleXMLStreamException(Throwable th) {
        nested = th;
    }

    /**
     * Construct an exception with the assocated message and exception
     *
     * @param th a nested exception
     * @param msg the message to report
     */
    public MultipleXMLStreamException(String msg, Throwable th) {
        super(msg);
        nested = th;
    }

    /**
     * Construct an exception with the assocated message, exception and location.
     *
     * @param th a nested exception
     * @param msg the message to report
     * @param location the location of the error
     */
    public MultipleXMLStreamException(String msg, ILocation location, Throwable th) {
        super("ParseError at [row,col]:["+location.getLineNumber()+","+
                location.getColumnNumber()+"]\n"+
                "Message: "+msg);
        nested = th;
        this.location = location;
    }

    /**
     * Construct an exception with the assocated message, exception and location.
     *
     * @param msg the message to report
     * @param location the location of the error
     */
    public MultipleXMLStreamException(String msg,
                                      ILocation location) {
        super("ParseError at [row,col]:["+location.getLineNumber()+","+
                location.getColumnNumber()+"]\n"+
                "Message: "+msg);
        this.location = location;
    }


    /**
     * Gets the nested exception.
     *
     * @return Nested exception
     */
    public Throwable getNestedException() {
        return nested;
    }

    /**
     * Gets the location of the exception
     *
     * @return the location of the exception, may be null if none is available
     */
    public ILocation getLocation() {
        return location;
    }

}
