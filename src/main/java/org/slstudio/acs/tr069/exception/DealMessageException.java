package org.slstudio.acs.tr069.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: обнГ4:53
 */
public class DealMessageException extends TR069Exception {

    public DealMessageException(int errorCode) {
        super(errorCode);
    }

    public DealMessageException(int errorCode, String message) {
        super(errorCode, message);
    }

    public DealMessageException(int errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public DealMessageException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
