package org.slstudio.acs.hms.messagedealer.plugin;

import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.fault.FaultUtil;
import org.slstudio.acs.tr069.fault.TR069Fault;
import org.slstudio.acs.tr069.messagedealer.plugin.IPreDealMessagePlugin;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.session.context.ITR069SessionContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.util.JSONUtil;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-12
 * Time: ÉÏÎç9:54
 */
public class CheckDevicePlugin implements IPreDealMessagePlugin {
    private IDeviceManager deviceManager = null;

    public IDeviceManager getDeviceManager() {
        return deviceManager;
    }

    public void setDeviceManager(IDeviceManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void execute(ITR069MessageContext context, SOAPMessage soapMessage, TR069Message tr069Message)  throws TR069Fault{
        if(tr069Message == null ||!(tr069Message instanceof InformRequest)){
            return;
        }
        System.out.println("---------------------------");
        System.out.println(JSONUtil.toJsonString(tr069Message));
        System.out.println(tr069Message.toSOAPString());
        System.out.println("---------------------------");

        ITR069SessionContext sessionContext = context.getTR069SessionContext();

        String requestID = tr069Message.getMessageID();

        String deviceKey =sessionContext.getDeviceKey();
        if(deviceKey == null){
            throw new TR069Fault(true,
                    TR069Constants.SERVER_FAULT_REQUEST_DENIED,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_REQUEST_DENIED),
                    requestID);
        }
        DeviceInfo device = deviceManager.findDevice(deviceKey);
        if(device == null){
            throw new TR069Fault(true,
                    TR069Constants.SERVER_FAULT_REQUEST_DENIED,
                    FaultUtil.findServerFaultMessage(TR069Constants.SERVER_FAULT_REQUEST_DENIED),
                    requestID);
        }
        updateDeviceInfo( (InformRequest)tr069Message,device);
    }

    private void updateDeviceInfo(InformRequest inform, DeviceInfo device) {
        device.setManufacturer(inform.getDeviceId().getManufacturer());
        device.setProductClass(inform.getDeviceId().getProductClass());
        device.setDeviceOUI(inform.getDeviceId().getOUI());
        device.setSerialNumber(inform.getDeviceId().getSerialNumber());
        List<ParameterValueStruct> parameters = inform.getParameterList();
        for(ParameterValueStruct pvs: parameters){
            if(TR069Constants.TR069_PARAM_CONNECTIONREQUESTURL.equals(pvs.getName())){
                device.setCrURL(pvs.getValue().toString());
            }else if(checkIP(pvs.getName())){
                device.setDeviceIP(pvs.getValue().toString());
            }
        }
        device.setLastInformTime(new Date());
    }

    private boolean checkIP(String parameterName){
        if(parameterName.startsWith("InternetGatewayDevice.WANDevice.") &&
                parameterName.endsWith(".ExternalIPAddress")){
            return true;
        }else{
            return false;
        }
    }

}
