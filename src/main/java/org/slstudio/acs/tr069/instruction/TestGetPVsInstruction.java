package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.GetParameterValuesRequest;
import org.slstudio.acs.tr069.databinding.response.GetParameterValuesResponse;
import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;
import org.slstudio.acs.tr069.job.request.DefaultJobRequest;
import org.slstudio.acs.tr069.job.request.IJobRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-6
 * Time: ÉÏÎç1:14
 */
public class TestGetPVsInstruction implements IWaitResponseInstruction {
    private String jobID = null;

    public TestGetPVsInstruction(String jobID) {
        this.jobID = jobID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionNormalErrorException, InstructionFatalErrorException {
        if(response instanceof GetParameterValuesResponse){
            GetParameterValuesResponse gvResposne = (GetParameterValuesResponse)response;
            System.out.println("Instruction execute successfully with parameter list size:" + gvResposne.getParameterList().size());
            return true;
        }else{
            throw new InstructionFatalErrorException("response type invalid");
        }

    }

    public String getInstructionID() {
        return "c2";
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionNormalErrorException, InstructionFatalErrorException {
        GetParameterValuesRequest gpvr = new GetParameterValuesRequest();
        List<String> parameterList = new ArrayList<String>();
        parameterList.add("InternetGatewayDevice.ManagementServer.PeriodicInformInterval");
        gpvr.setParameterNames(parameterList);

        return new DefaultJobRequest(gpvr);
    }
}
