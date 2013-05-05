package org.slstudio.acs.tr069.command.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:17
 */
public class CommandFatalErrorException extends Exception {
    public CommandFatalErrorException() {
    }

    public CommandFatalErrorException(String message) {
        super(message);
    }

    public CommandFatalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandFatalErrorException(Throwable cause) {
        super(cause);
    }
}
