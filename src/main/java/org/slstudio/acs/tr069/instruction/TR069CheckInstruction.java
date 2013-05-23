package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.instruction.checkrule.ITR069MessageCheckRule;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç3:41
 */
public class TR069CheckInstruction extends InstructionBase implements IWaitTR069RequestInstruction {
    private List<ITR069MessageCheckRule> checkRuleList = null;
    private int skipCheckCount = 0;

    public TR069CheckInstruction(String instructionID) {
        super(instructionID);
    }

    public List<ITR069MessageCheckRule> getCheckRuleList() {
        return checkRuleList;
    }

    public void setCheckRuleList(List<ITR069MessageCheckRule> checkRuleList) {
        this.checkRuleList = checkRuleList;
    }

    public int getSkipCheckCount() {
        return skipCheckCount;
    }

    public void setSkipCheckCount(int skipCheckCount) {
        this.skipCheckCount = skipCheckCount;
    }

    public boolean handleRequest(InstructionContext cmdContext, TR069Message request) throws InstructionFailException, JobFailException {
        boolean passed = true;
        for(ITR069MessageCheckRule rule: checkRuleList){
            if(!rule.check(request)){
                passed = false;
                break;
            }
        }
        if(passed){
            //deal and instruction execute succeed
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), "true");
            return true;
        }

        if(skipCheckCount >0){
            skipCheckCount--;
            //skip the message
            return false;
        }

        //deal but instruction execute failed , throw exception to make job failed
        cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, "false");
        cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGECHECKFAILED);
        cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "TR069 Message check failed");
        throw new JobFailException("Check TR069 Message Failed");
}

    public String getInstructionName() {
        return "Check TR069 Message Instruction";
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for(ITR069MessageCheckRule rule : checkRuleList){
            result.append("Rule:").append(rule.toString()).append(",");
        }
        result.append("SkipCount:").append(skipCheckCount);
        return result.toString();
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException {
    }

    public TR069Message getTR069Message() {
        return null;
    }
}

