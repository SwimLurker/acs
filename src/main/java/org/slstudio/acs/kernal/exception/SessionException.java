package org.slstudio.acs.kernal.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ1:31
 */
public class SessionException extends ACSException {
    public SessionException() {
    }

    public SessionException(String message) {
        super(message);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionException(Throwable cause) {
        super(cause);
    }
}
