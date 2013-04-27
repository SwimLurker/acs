package org.slstudio.acs.kernal.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: обнГ12:48
 */
public class MessageException extends SessionException {
    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }
}
