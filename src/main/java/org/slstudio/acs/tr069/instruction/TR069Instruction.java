package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;
import org.slstudio.acs.tr069.job.request.DefaultJobRequest;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÉÏÎç12:20
 */
public class TR069Instruction extends InstructionBase implements IWaitResponseInstruction{
    private TR069Message request = null;

    public TR069Instruction(String instructionID, String jobID, TR069Message request) {
        super(instructionID, jobID);
        this.request = request;
    }

    public TR069Message getRequest() {
        return request;
    }

    public void setRequest(TR069Message request) {
        this.request = request;
    }

    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionNormalErrorException, InstructionFatalErrorException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionNormalErrorException, InstructionFatalErrorException {
        return new DefaultJobRequest(request);

    }
}
