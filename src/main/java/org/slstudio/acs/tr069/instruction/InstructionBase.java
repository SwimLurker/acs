package org.slstudio.acs.tr069.instruction;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ионГ12:11
 */
public abstract class InstructionBase implements IInstruction {
    private String instructionID = null;
    private String jobID = null;

    protected InstructionBase(String instructionID, String jobID) {
        this.instructionID = instructionID;
        this.jobID = jobID;
    }

    public String getInstructionID() {
        return instructionID;
    }

    public String getJobID() {
        return jobID;
    }
}
