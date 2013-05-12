package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-5
 * Time: ÉÏÎç11:02
 */
public interface IWaitResponseInstruction extends IInstruction {
    //return true if the instruction handled the response
    //return false if the instruction skip to handle the response
    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionNormalErrorException, InstructionFatalErrorException;
}
