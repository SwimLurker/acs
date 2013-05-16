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
 * Date: 13-5-14
 * Time: ÏÂÎç12:57
 */
public class ReturnInstruction extends InstructionBase{
    private String returnValue = null;

    public ReturnInstruction(String instructionID, String returnValue) {
        super(instructionID);
        this.returnValue = returnValue;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getInstructionName() {
        return "Return Instruction";
    }

    public String toString() {
        return "return "+ (returnValue==null?"":returnValue);
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException ,JobCompleteException{
        if(returnValue == null){
            //set job return value to last instruction result;
            int lastInstructionID = Integer.parseInt(getInstructionID())-1;
            Object value = cmdContext.getSymbolTable().get(DeviceJobConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + Integer.toString(lastInstructionID));
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, value);
        }else if(returnValue.startsWith(DeviceJobConstants.SYMBOLNAME_VARIABLE_PREFIX)){
            Object value = cmdContext.getSymbolTable().get(returnValue);
            if(value != null){
                cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, value);
            }
        }else{
            //value
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, returnValue);
        }
        //throw job complete exception to let job complete
        throw new JobCompleteException("returned");
    }
}
