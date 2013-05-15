package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.GetParameterValuesRequest;
import org.slstudio.acs.tr069.databinding.response.GetParameterValuesResponse;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
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


    public boolean handleResponse(InstructionContext cmdContext, TR069Message response) throws InstructionFailException, JobFailException {
        if(response instanceof GetParameterValuesResponse){
            GetParameterValuesResponse gvResposne = (GetParameterValuesResponse)response;
            System.out.println("Instruction execute successfully with parameter list size:" + gvResposne.getParameterList().size());
            return true;
        }else{
            throw new JobFailException("response type invalid");
        }

    }

    public String getInstructionID() {
        return "c2";
    }

    public IJobRequest execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException {
        GetParameterValuesRequest gpvr = new GetParameterValuesRequest();
        List<String> parameterList = new ArrayList<String>();
        parameterList.add("InternetGatewayDevice.ManagementServer.PeriodicInformInterval");
        gpvr.setParameterNames(parameterList);

        return new DefaultJobRequest(gpvr);
    }
}
