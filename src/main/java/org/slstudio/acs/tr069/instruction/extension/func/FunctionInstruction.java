package org.slstudio.acs.tr069.instruction.extension.func;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.InstructionBase;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç3:39
 */
public class FunctionInstruction extends InstructionBase {
    private IFunction function = null;

    protected FunctionInstruction(String instructionID) {
        super(instructionID);
    }

    public IFunction getFunction() {
        return function;
    }

    public void setFunction(IFunction function) {
        this.function = function;
    }

    public String getInstructionName() {
        return "Function Instruction:(" + function.getName() + ")";
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException, JobCompleteException {
        try {
            String result = function.execute(cmdContext);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), result);
        } catch (InstructionException e) {
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "function execution exception:" + e.getMessage());
            throw new JobFailException("job failed when execute function:" + function.getName(), e);
        }
    }

    public String toString(){
        return function.toString();
    }
}
