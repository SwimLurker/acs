package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.util.InstructionUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÏÂÎç12:57
 */
public class ReturnInstruction extends InstructionBase{
    private String returnValueText = null;

    public ReturnInstruction(String instructionID, String returnValueText) {
        super(instructionID);
        this.returnValueText = returnValueText;
    }

    public String getReturnValueText() {
        return returnValueText;
    }

    public void setReturnValueText(String returnValueText) {
        this.returnValueText = returnValueText;
    }

    public String getInstructionName() {
        return "Return Instruction";
    }

    public String toString() {
        return "return "+ (returnValueText ==null?"": returnValueText);
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException ,JobCompleteException{
        if(returnValueText == null){
            //set job return value to last instruction resulthandler;
            int lastInstructionID = Integer.parseInt(getInstructionID())-1;
            Object value = cmdContext.getSymbolTable().get(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + Integer.toString(lastInstructionID));
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, value);
        }else{
            String resultText = null;
            try {
                resultText = InstructionUtil.populateTextWithVariableValue(returnValueText, cmdContext.getSymbolTable());
            } catch (Exception e) {
                throw new JobFailException("job failed for evaluate return value:" + e.getMessage());
            }
            if(resultText != null){
                cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, resultText);
            }
        }
        //throw job complete exception to let job complete
        throw new JobCompleteException("returned");
    }
}
