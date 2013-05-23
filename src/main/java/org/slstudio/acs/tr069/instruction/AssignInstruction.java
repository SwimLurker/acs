package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.util.InstructionUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç12:19
 */
public class AssignInstruction extends InstructionBase {
    private String variableName = null;
    private String variableValueText = null;

    public AssignInstruction(String instructionID, String variableName, String variableValueText) {
        super(instructionID);
        this.variableName = variableName;
        this.variableValueText = variableValueText;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValueText() {
        return variableValueText;
    }

    public void setVariableValueText(String variableValueText) {
        this.variableValueText = variableValueText;
    }

    public String getInstructionName() {
        return "Assign Instruction";
    }

    public String toString() {
        return variableName +" = " + variableValueText;
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException, JobCompleteException {
        String varialbeValue = null;
        try {
            varialbeValue = InstructionUtil.populateTextWithVariableValue(variableValueText, cmdContext.getSymbolTable());
        } catch (Exception e) {
            throw new JobFailException("job failed for evaluate variable value:" + e.getMessage());
        }

        if(varialbeValue != null){
            cmdContext.getSymbolTable().put(variableName, varialbeValue);
        }
    }

}
