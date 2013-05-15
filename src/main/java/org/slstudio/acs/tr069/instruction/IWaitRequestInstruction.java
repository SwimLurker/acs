package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-5
 * Time: обнГ9:19
 */
public interface IWaitRequestInstruction extends ITR069Instruction {
    public boolean handleRequest(InstructionContext cmdContext, TR069Message request) throws InstructionFailException, JobFailException;
}
