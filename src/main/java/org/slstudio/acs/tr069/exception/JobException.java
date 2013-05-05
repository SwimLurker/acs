package org.slstudio.acs.tr069.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:38
 */
public class JobException extends ACSException {
    public JobException() {
    }

    public JobException(String message) {
        super(message);
    }

    public JobException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobException(Throwable cause) {
        super(cause);
    }
}
