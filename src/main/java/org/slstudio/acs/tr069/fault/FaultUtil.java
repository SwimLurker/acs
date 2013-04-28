package org.slstudio.acs.tr069.fault;

import org.slstudio.acs.tr069.constant.TR069Constants;

import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç3:16
 */
public class FaultUtil {

    private static Hashtable clientFaultTable=new Hashtable();
    private static Hashtable serverFaultTable=new Hashtable();
    private static Hashtable systemErrorTable=new Hashtable();

    static{
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_UNKNOWN_REASON),"Unknown reason error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_TASK_WAIT_TIMEOUT),"Task wait timeout");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_TASK_DEAL_TIMEOUT),"Task deal timeout");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_DEPLOYMENT),"Deployment ACS error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_PARSE_MESSAGE_FAIL),"parse TR069 message failed");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_MESSAGE_FORMAT_INVALID),"Invalid TR069 message format");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_DATA_BINDING),"Data binding error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_SESSION_MANAGEMENT),"Session management error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_DISPATCH_TASK),"Dispatch task error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_DEAL_MESSAGE),"Deal message error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_SEND_JMS_MESSAGE),"Send JMS message error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_BUILD_REQUEST),"Build tr069 request error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_SAVE_ROMFILE),"Save romfile error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_RESTORE_ROMFILE),"restore romfile error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_UPGRADE_FIRMWARE),"upgrade fiwmware error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_CREATE_TASK),"receive server message error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_GET_DEVICE_MAC),"get device mac error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_UNKNOWN_WORKER_TASK),"set proxy worker error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_SET_PROVISIONING_CODE),"set provisioning code error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_GET_PROVISIONING_CODE),"get provisioning code error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_CLEANUP_TASK),"clean up task error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_UNKNOWN_CONFIGTYPE),"unknown device config type error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_UNSUPPORTED_TASK),"unsupported task error");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_DEVICE_NOT_DISPATCHED),"The device has not dispatch yet.");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_ACS_NOT_WORKING),"The ACS that the device dispatched to is not working currently.");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_DEVICE_NOT_EXISTS),"Device not found.");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_RELATE_TASK_FAIL),"The related task is failed");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_UNKNOWN_MESSAGE_TYPE),"Message type is unknown");
        systemErrorTable.put(Integer.toString(TR069Constants.ERROR_FIRMWAREVERSION_MISMATCH),"Fimware version check error");

        clientFaultTable.put(TR069Constants.CLIENT_FAULT_METHOD_NOT_SUPPORT,"Method not supported");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_REQUEST_DENIED,"Request denied (no reason specified)");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_INTERNAL_ERROR,"Internal error");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_INVALID_ARGUMENTS,"Invalid arguments");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_RESOURCE_EXCEEDED,"Resources exceeded");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_INVALID_PARAMETER_NAME,"Invalid parameter name");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_INVALID_PARAMETER_TYPE,"Invalid parameter type");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_INVALID_PARAMETER_VALUE,"Invalid parameter value");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_ATTEMP_TO_SET_NOTWRITE,"Attempt to set a non-writable parameter");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_NOTIFICATION_REJECT,"Notification request rejected");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_DOWNLOAD_FAILURE,"Download failure");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_UPLOAD_FAILURE,"Upload failure");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_FILE_TRANSFERAUTH_FAILURE,"File transfer server authentication failure");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_UNSUPPPORT_TRANSFER_PROTOCOL,"Unsupported protocol for file transfer");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_TIMEOUT,"CPE not responsed");
        clientFaultTable.put(TR069Constants.CLIENT_FAULT_ATOMIC_FAILURE,"The parameter set failed for atomiclly");

        serverFaultTable.put(TR069Constants.SERVER_FAULT_METHOD_NOT_SUPPORT,"Method not supported");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_REQUEST_DENIED,"Request denied");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_INTERNAL_ERROR,"Internal error");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_INVALID_ARGUMENTS,"Invalid arguments");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_RESOURCE_EXCEEDED,"Resources exceeded");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_RETRY_REQUEST,"Retry request");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_NEED_INFORM,"Need Inform request");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_TOO_MANY_ENVELOPES,"Too many envelopes");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_MESSAGE_FORMAT_INVALID,"Message format invalid");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_MESSAGE_UNKNOWN_REASON,"Unknown reason");
        serverFaultTable.put(TR069Constants.SERVER_FAULT_INVALID_DEVICEMAC,"Invalid device MAC");

    }

    public static String findClientFaultMessage(String faultCode){
        if(clientFaultTable.containsKey(faultCode)){
            return (String)clientFaultTable.get(faultCode);
        }else{
            return "Unknown Reason";
        }
    }

    public static String findServerFaultMessage(String faultCode){
        if(serverFaultTable.containsKey(faultCode)){
            return (String)serverFaultTable.get(faultCode);
        }else{
            return "Unknown Reason";
        }
    }

    public static String findErrorMessage(int errorCode){
        String errorCodeStr=Integer.toString(errorCode);

        if(systemErrorTable.containsKey(errorCodeStr)){
            return (String)systemErrorTable.get(errorCodeStr);
        }else if(clientFaultTable.containsKey(errorCodeStr)){
            return (String)clientFaultTable.get(errorCodeStr);
        }else{
            return "Unknown Reason";
        }
    }
}