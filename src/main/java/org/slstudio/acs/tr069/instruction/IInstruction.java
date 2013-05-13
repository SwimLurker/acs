package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ1:57
 */
public interface IInstruction {
    public String getInstructionID();
    public String getJobID();
    public IJobRequest execute(InstructionContext cmdContext) throws InstructionNormalErrorException, InstructionFatalErrorException;
}
