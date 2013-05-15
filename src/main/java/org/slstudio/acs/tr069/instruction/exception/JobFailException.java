package org.slstudio.acs.tr069.instruction.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:17
 */
public class JobFailException extends Exception {
    public JobFailException() {
    }

    public JobFailException(String message) {
        super(message);
    }

    public JobFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobFailException(Throwable cause) {
        super(cause);
    }
}
