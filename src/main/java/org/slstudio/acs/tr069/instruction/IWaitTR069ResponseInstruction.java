package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-5
 * Time: ÉÏÎç11:02
 */
public interface IWaitTR069ResponseInstruction extends ITR069Instruction {
    //return true if the instruction handled the response
    //return false if the instruction skip to handle the response
    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionFailException, JobFailException;
}
