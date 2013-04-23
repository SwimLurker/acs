package org.slstudio.acs.kernal.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:53
 */
public class TR069Exception extends ACSException {
    public TR069Exception() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TR069Exception(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TR069Exception(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TR069Exception(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
