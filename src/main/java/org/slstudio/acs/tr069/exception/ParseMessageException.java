package org.slstudio.acs.tr069.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: обнГ3:43
 */
public class ParseMessageException extends TR069Exception {
    public ParseMessageException() {
    }

    public ParseMessageException(String message) {
        super(message);
    }

    public ParseMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseMessageException(Throwable cause) {
        super(cause);
    }
}
