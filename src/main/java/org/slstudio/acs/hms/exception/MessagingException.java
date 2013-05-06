package org.slstudio.acs.hms.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ1:45
 */
public class MessagingException extends ACSException {
    public MessagingException() {
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagingException(Throwable cause) {
        super(cause);
    }
}
