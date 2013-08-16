package org.slstudio.acs.hms.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.bootstrap.IBootStrapStrategy;
import org.slstudio.acs.hms.bootstrap.bean.BootStrapBean;
import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.tr069.databinding.EventStruct;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.TR069MessageFactory;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.databinding.request.SetParameterValuesRequest;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.ReturnInstruction;
import org.slstudio.acs.tr069.instruction.TR069CommandInstruction;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.manager.IJobManager;
import org.slstudio.acs.tr069.messagedealer.plugin.IPreDealMessagePlugin;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.util.BeanLocator;
import org.slstudio.acs.util.IDGenerator;
import org.slstudio.acs.util.JSONUtil;

/**
 * Created with IntelliJ IDEA. User: chandler Date: 13-5-31 Time: ����1:12
 */
public class BootStrapPlugin implements IPreDealMessagePlugin {
	

	private static final Log log = LogFactory.getLog(BootStrapPlugin.class);

	private IJobManager jobManager = null;
	private IDeviceManager deviceManager = null;
	private IBootStrapStrategy bootstrapStrategy = null;

	public IJobManager getJobManager() {
		return jobManager;
	}

	public void setJobManager(IJobManager jobManager) {
		this.jobManager = jobManager;
	}

	public IDeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(IDeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public IBootStrapStrategy getBootstrapStrategy() {
		return bootstrapStrategy;
	}

	public void setBootstrapStrategy(IBootStrapStrategy bootstrapStrategy) {
		this.bootstrapStrategy = bootstrapStrategy;
	}

	public void execute(ITR069MessageContext context, SOAPMessage soapMessage,
			TR069Message tr069Message) throws TR069Fault {
		InformRequest inform = (InformRequest) tr069Message;
		List<EventStruct> events = inform.getEventList();
		for (EventStruct e : events) {
			String eventCode = e.getEventCode();
			if (TR069Constants.INFORM_EVENT_BOOTSTRAP.equalsIgnoreCase(eventCode)) {
				generateBootStrapJob(inform.getDeviceId(), getClientWanIP(inform));
			}
		}
	}

	private String getClientWanIP(InformRequest inform) {
		List<ParameterValueStruct> plist = inform.getParameterList();
		for (ParameterValueStruct parameterValueStruct : plist) {
			if (TR069Constants.INFORM_PARAMETER_EXTERNAL_IP_ADDRESS.equals(parameterValueStruct.getName())) {
				return String.valueOf(parameterValueStruct.getValue());
			}
		}
		return null;
	}

	private void generateBootStrapJob(DeviceIdStruct deviceId, String ip) {

		DeviceInfo device = deviceManager.findDevice(deviceId.getSerialNumber());
		if (device == null) {
			log.error("generate bootstrap job failed: unknown device");
			return;
		}

		BootStrapBean bsb = bootstrapStrategy.getBootstrapConfig(deviceId.getSerialNumber(), ip);
		if (bsb == null) {
			log
					.error("generate bootstrap job failed: can not get bootstrap settings");
			return;
		}

		String jobID = IDGenerator.getNewID();
		IDeviceJob findJob = jobManager.findJob(jobID);
		if (findJob != null) {
			log.error("generate bootstrap job failed: job existed");
			return;
		}

		IDeviceJob job = (IDeviceJob) BeanLocator.getBean("systemJob");

		job.setDeviceKey(deviceId.getSerialNumber());
		job.setJobID(jobID);
		job.setJobName("Bootstrap task: redirect service acs");
		job.setCreateTime(new Date());
		job.setWaitingTimeout(-1);
		job.setRunningTimeout(-1);

		SetParameterValuesRequest spvr = new SetParameterValuesRequest();

		List<ParameterValueStruct> pvsList = new ArrayList<ParameterValueStruct>();
		ParameterValueStruct pvs1 = new ParameterValueStruct();
		pvs1.setName(TR069Constants.TR069_PARAM_CONNECTIONREQUESTURL);
		pvs1.setValue(bsb.getAcsAddress());
		pvsList.add(pvs1);

		ParameterValueStruct pvs2 = new ParameterValueStruct();
		pvs2.setName(TR069Constants.TR069_PARAM_ACSUSERNAME);
		pvs2.setValue(bsb.getAcsAccount());
		pvsList.add(pvs2);

		ParameterValueStruct pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_ACSPASSWORD);
		pvs3.setValue(bsb.getAcsPassword());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_SECGW1);
		pvs3.setValue(bsb.getSafeGateway1());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_SECGW2);
		pvs3.setValue(bsb.getSafeGateway2());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_SECGW3);
		pvs3.setValue(bsb.getSafeGateway3());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_FAPGW1);
		pvs3.setValue(bsb.getGateway1());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_FAPGW2);
		pvs3.setValue(bsb.getGateway2());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_FAPGW3);
		pvs3.setValue(bsb.getGateway3());
		pvsList.add(pvs3);

		pvs3 = new ParameterValueStruct();
		pvs3.setName(TR069Constants.TR069_PARAM_FAPGW_PORT);
		pvs3.setValue(bsb.getGatewayPort());
		pvsList.add(pvs3);

		spvr.setParameterList(pvsList);

		String bootStrapScript = TR069MessageFactory.MESSAGE_TYPE_SETPARAMETERVALUES
				+ ":" + JSONUtil.toJsonString(spvr);

		String instructionID = "1";
		String messageID = jobID + "_" + instructionID;
		IInstruction i1 = new TR069CommandInstruction(instructionID, messageID,
				bootStrapScript);
		job.getInstructionQueue().push(i1);

		IInstruction i2 = new ReturnInstruction(
				"2",
				InstructionConstants.SYMBOLNAME_VARIABLEREF_PREFIX
						+ InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX
						+ instructionID
						+ InstructionConstants.SYMBOLNAME_VARIABLEREF_POSTFIX);
		job.getInstructionQueue().push(i2);

		jobManager.addJob(job);
	}

}
