package org.slstudio.acs.tr069.instruction.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:16
 */
//
public class InstructionFailException extends Exception {
    public InstructionFailException() {
    }

    public InstructionFailException(String message) {
        super(message);
    }

    public InstructionFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructionFailException(Throwable cause) {
        super(cause);
    }
}
