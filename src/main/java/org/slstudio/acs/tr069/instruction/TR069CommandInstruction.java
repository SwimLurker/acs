package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.response.FaultResponse;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.util.InstructionUtil;
import org.slstudio.acs.util.JSONUtil;
import org.slstudio.acs.tr069.databinding.TR069MessageFactory;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÉÏÎç12:20
 */
public class TR069CommandInstruction extends InstructionBase implements IWaitTR069ResponseInstruction {
    private TR069Message request = null;
    private String messageID = null;
    private String messageExpression = null;


    public TR069CommandInstruction(String instructionID, String messageID, String messageExpression) {
        super(instructionID);
        this.messageID = messageID;
        this.messageExpression = messageExpression;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageExpression() {
        return messageExpression;
    }

    public void setMessageExpression(String messageExpression) {
        this.messageExpression = messageExpression;
    }

    public TR069Message getRequest() {
        return request;
    }

    public void setRequest(TR069Message request) {
        this.request = request;
    }

    public String getInstructionName() {
        return "TR069 Command Instruction";
    }

    public String toString() {
        return messageExpression;
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException {
        String messageText = null;
        try{
            messageText = InstructionUtil.populateTextWithVariableValue(messageExpression, cmdContext.getSymbolTable());
        }catch(Exception exp){
            throw new JobFailException("job failed for evaluate message text:" + exp.getMessage());
        }

        if(messageText == null){
            throw new JobFailException("job failed for evaluate message text");
        }
        try{
            request = getTR069Message(messageText);
        }catch (Exception exp){
            throw new JobFailException("job failed for get tr069 message:" + exp.getMessage());
        }

        if(request == null){
            throw new JobFailException("job failed for get tr069 message");
        }

        request.setMessageID(messageID);
    }

    public TR069Message getTR069Message() {
        return request;
    }

    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionFailException, JobFailException {
        cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), "json:" + JSONUtil.toJsonString(response));
        if(response == null){
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, null);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGECHECKFAILED);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG,  "Message response is null");
            throw new JobFailException("Handle TR069 Response error, null message");
        }
        if(response instanceof FaultResponse){
            FaultResponse fault = (FaultResponse)response;
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, fault);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, fault.getFaultCode().intValue());
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, fault.getFaultString());
            throw new JobFailException("Handle TR069 Response error");
        }

        if(!response.getMessageID().equals(messageID)){
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, response);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGEIDERROR);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "Response id is not correct");
            throw new JobFailException("Handle TR069 Response error,response id is not correct");
        }
        if(request == null){
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, null);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGECHECKFAILED);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG,  "Message request is null");
            throw new JobFailException("Handle TR069 Response error, null request");
        }

        if(!response.getMessageName().equals(request.getMessageName() + "Response")){
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_RETURNVALUE, response);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGEIDERROR);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "Response type is not correct");
            throw new JobFailException("Handle TR069 Response error");
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private TR069Message getTR069Message(String instructionText) throws IOException {
        String[] s = instructionText.split(":" , 2);
        return TR069MessageFactory.fromJSONString(s[1], s[0]);
    }
}
