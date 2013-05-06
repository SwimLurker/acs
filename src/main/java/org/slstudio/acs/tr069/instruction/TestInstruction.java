package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ÉÏÎç3:39
 */
public class TestInstruction implements IInstruction {
    private String jobID = null;
    private String someValue = null;

    public TestInstruction(String jobID, String someValue) {
        this.jobID = jobID;
        this.someValue = someValue;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getSomeValue() {
        return someValue;
    }

    public void setSomeValue(String someValue) {
        this.someValue = someValue;
    }

    public String getInstructionID() {
        return "c1";
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionNormalErrorException, InstructionFatalErrorException {
        cmdContext.getSymbolTable().put("test", someValue+"_test");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
