package org.slstudio.acs.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:54
 */
public class ACSException extends Exception {

    public ACSException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ACSException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ACSException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ACSException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
