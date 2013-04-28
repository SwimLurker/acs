package org.slstudio.acs.tr069.exception;

import org.slstudio.acs.kernal.exception.PipelineException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ионГ12:32
 */
public class ValidateMessageException extends PipelineException {
    public ValidateMessageException() {
    }

    public ValidateMessageException(String message) {
        super(message);
    }

    public ValidateMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateMessageException(Throwable cause) {
        super(cause);
    }
}
