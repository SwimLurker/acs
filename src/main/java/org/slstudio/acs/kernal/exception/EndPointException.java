package org.slstudio.acs.kernal.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ионГ2:32
 */
public class EndPointException extends ACSException {
    public EndPointException() {
    }

    public EndPointException(String message) {
        super(message);
    }

    public EndPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndPointException(Throwable cause) {
        super(cause);
    }
}
