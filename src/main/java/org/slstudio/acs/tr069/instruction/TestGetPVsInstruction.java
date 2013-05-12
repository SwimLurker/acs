package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.response.GetParameterValuesResponse;
import org.slstudio.acs.tr069.instruction.exception.InstructionFatalErrorException;
import org.slstudio.acs.tr069.instruction.exception.InstructionNormalErrorException;
import org.slstudio.acs.tr069.job.request.DefaultJobRequest;
import org.slstudio.acs.tr069.job.request.IJobRequest;

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
        StringBuilder sb = new StringBuilder();
        sb.append("<SOAP-ENV:Envelope xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "\t<SOAP-ENV:Header>\n" +
                "\t\t<cwmp:ID SOAP-ENV:mustUnderstand=\"1\">ID:").append(jobID).append("_1234</cwmp:ID>\n" +
                "\t\t<cwmp:NoMoreRequests>0</cwmp:NoMoreRequests>\n" +
                "\t</SOAP-ENV:Header>\n" +
                "\t<SOAP-ENV:Body>\n" +
                "\t\t<cwmp:GetParameterValues xmlns:cwmp=\"urn:dslforum-org:cwmp-1-0\">\n" +
                "\t\t\t<ParameterNames SOAP-ENC:arrayType=\"xsd:string[1]\">\n" +
                "\t\t\t\t<string>InternetGatewayDevice.ManagementServer.PeriodicInformInterval</string>\n" +
                "\t\t\t</ParameterNames>\n" +
                "\t\t</cwmp:GetParameterValues>\n" +
                "\t</SOAP-ENV:Body>\n" +
                "</SOAP-ENV:Envelope>");
        return new DefaultJobRequest(sb.toString());
    }
}
