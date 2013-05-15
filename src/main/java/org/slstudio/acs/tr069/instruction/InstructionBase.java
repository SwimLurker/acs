package org.slstudio.acs.tr069.instruction;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ионГ12:11
 */
public abstract class InstructionBase implements IInstruction {
    private String instructionID = null;

    protected InstructionBase(String instructionID) {
        this.instructionID = instructionID;
    }

    public String getInstructionID() {
        return instructionID;
    }

    public void setInstructionID(String instructionID) {
        this.instructionID = instructionID;
    }
}
