package org.slstudio.acs.kernal.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: обнГ3:25
 */
public class LifecycleException extends ACSException {
    public LifecycleException() {
    }

    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifecycleException(Throwable cause) {
        super(cause);
    }
}
