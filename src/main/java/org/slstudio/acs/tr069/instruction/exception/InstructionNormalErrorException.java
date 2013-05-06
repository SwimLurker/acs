package org.slstudio.acs.tr069.instruction.exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:16
 */
//
public class InstructionNormalErrorException extends Exception {
    public InstructionNormalErrorException() {
    }

    public InstructionNormalErrorException(String message) {
        super(message);
    }

    public InstructionNormalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstructionNormalErrorException(Throwable cause) {
        super(cause);
    }
}
