package org.slstudio.acs.tr069.command.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:16
 */
//
public class CommandNormalErrorException extends Exception {
    public CommandNormalErrorException() {
    }

    public CommandNormalErrorException(String message) {
        super(message);
    }

    public CommandNormalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNormalErrorException(Throwable cause) {
        super(cause);
    }
}
