package org.slstudio.acs.tr069.instruction.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:17
 */
public class InstructionFatalErrorException extends Exception {
    public InstructionFatalErrorException() {
    }

    public InstructionFatalErrorException(String message) {
        super(message);
    }

    public InstructionFatalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructionFatalErrorException(Throwable cause) {
        super(cause);
    }
}
