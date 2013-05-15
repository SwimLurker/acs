package org.slstudio.acs.tr069.instruction.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ионГ1:20
 */
public class JobCompleteException extends Exception {
    public JobCompleteException() {
    }

    public JobCompleteException(String message) {
        super(message);
    }

    public JobCompleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobCompleteException(Throwable cause) {
        super(cause);
    }
}
