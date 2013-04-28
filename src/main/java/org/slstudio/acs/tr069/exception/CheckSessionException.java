package org.slstudio.acs.tr069.exception;

import org.slstudio.acs.kernal.exception.PipelineException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ионГ1:05
 */
public class CheckSessionException extends PipelineException {
    public CheckSessionException() {
    }

    public CheckSessionException(String message) {
        super(message);
    }

    public CheckSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckSessionException(Throwable cause) {
        super(cause);
    }
}
