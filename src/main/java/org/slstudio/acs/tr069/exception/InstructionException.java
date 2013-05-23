package org.slstudio.acs.tr069.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-22
 * Time: обнГ9:36
 */
public class InstructionException extends Exception {
    public InstructionException() {
    }

    public InstructionException(String message) {
        super(message);
    }

    public InstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructionException(Throwable cause) {
        super(cause);
    }
}
