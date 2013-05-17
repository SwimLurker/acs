package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.response.FaultResponse;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.job.request.DefaultJobRequest;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÉÏÎç12:20
 */
public class TR069CommandInstruction extends InstructionBase implements IWaitResponseInstruction{
    private TR069Message request = null;

    public TR069CommandInstruction(String instructionID, TR069Message request) {
        super(instructionID);
        this.request = request;
    }

    public TR069Message getRequest() {
        return request;
    }

    public void setRequest(TR069Message request) {
        this.request = request;
    }

    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionFailException, JobFailException {
        cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), response);
        if(response == null){
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, null);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGECHECKFAILED);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORMSG,  "Message response is null");
            throw new JobFailException("Handle TR069 Response error, null message");
        }
        if(response instanceof FaultResponse){
            FaultResponse fault = (FaultResponse)response;
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, fault);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORCODE, fault.getFaultCode().intValue());
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORMSG, fault.getFaultString());
            throw new JobFailException("Handle TR069 Response error");
        }

        if(!response.getMessageID().equals(request.getMessageID())){
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, response);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGEIDERROR);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORMSG, "Response id is not correct");
            throw new JobFailException("Handle TR069 Response error");
        }

        if(!response.getMessageName().equals(request.getMessageName() + "Response")){
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_RETURNVALUE, response);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_MESSAGEIDERROR);
            cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_ERRORMSG, "Response type is not correct");
            throw new JobFailException("Handle TR069 Response error");
        }
        cmdContext.getSymbolTable().put(DeviceJobConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), response);


        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getInstructionName() {
        return "TR069 Command Instruction";
    }

    public String toString() {
        return request.toSOAPString();
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException {
        return new DefaultJobRequest(request);
    }
}
