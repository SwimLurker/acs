package org.slstudio.acs.tr069.command.exception;

import org.slstudio.acs.tr069.command.CommandContext;
import org.slstudio.acs.tr069.command.IWaitResponseCommand;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.response.GetParameterValuesResponse;
import org.slstudio.acs.tr069.job.request.DefaultJobRequest;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-6
 * Time: ÉÏÎç1:14
 */
public class TestGetPVsCommand implements IWaitResponseCommand {
    private String jobID = null;

    public TestGetPVsCommand(String jobID) {
        this.jobID = jobID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public boolean handleResponse(CommandContext cmdContext, TR069Message response) throws CommandNormalErrorException, CommandFatalErrorException {
        if(response instanceof GetParameterValuesResponse){
            GetParameterValuesResponse gvResposne = (GetParameterValuesResponse)response;
            System.out.println("Command execute successfully with parameter list size:" + gvResposne.getParameterList().size());
            return true;
        }else{
            throw new CommandFatalErrorException("response type invalid");
        }

    }

    public String getCommandID() {
        return "c2";
    }

    public IJobRequest execute(CommandContext cmdContext) throws CommandNormalErrorException, CommandFatalErrorException {
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
