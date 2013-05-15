package org.slstudio.acs.tr069.exception;

import org.slstudio.acs.exception.ACSException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ионГ2:50
 */
public class ParseScriptException extends ACSException {
    public ParseScriptException() {
    }

    public ParseScriptException(String message) {
        super(message);
    }

    public ParseScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseScriptException(Throwable cause) {
        super(cause);
    }
}
