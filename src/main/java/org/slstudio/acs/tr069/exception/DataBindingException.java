package org.slstudio.acs.tr069.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ2:58
 */
public class DataBindingException extends TR069Exception{
    public DataBindingException(int errorCode) {
        super(errorCode);
    }

    public DataBindingException(int errorCode, String message) {
        super(errorCode, message);
    }

    public DataBindingException(int errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public DataBindingException(int errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
