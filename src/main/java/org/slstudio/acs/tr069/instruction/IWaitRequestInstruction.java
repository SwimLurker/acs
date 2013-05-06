package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-5
 * Time: обнГ9:19
 */
public interface IWaitRequestInstruction extends IInstruction {
    public boolean handleRequest(InstructionContext cmdContext, TR069Message request) throws InstructionNormalErrorException, InstructionFatalErrorException;
}
