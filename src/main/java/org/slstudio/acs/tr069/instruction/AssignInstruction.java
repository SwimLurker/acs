package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç12:19
 */
public class AssignInstruction extends InstructionBase {
    private String variableName = null;
    private String variableValue = null;

    public AssignInstruction(String instructionID, String variableName, String variableValue) {
        super(instructionID);
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException, JobCompleteException {
        if(variableValue.startsWith(DeviceJobConstants.SYMBOLNAME_VARIABLE_PREFIX)){
            Object value = cmdContext.getSymbolTable().get(variableValue);
            cmdContext.getSymbolTable().put(variableName, value);
        }else{
            cmdContext.getSymbolTable().put(variableName, variableValue);
        }
        return null;
    }
}
