package org.slstudio.acs.tr069.constant;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ����12:04
 */
public class TR069Constants {
    //session context key for TR069
    public static final String SESSIONCONTEXT_KEY_CLIENTIP = "SessionContext_Key_ClientIP";
    public static final String SESSIONCONTEXT_KEY_CLIENTPORT = "SessionContext_Key_ClientPort";
    public static final String SESSIONCONTEXT_KEY_INFORMREQUEST = "SessionContext_Key_InformRequest";
    public static final String SESSIONCONTEXT_KEY_DEVICEKEY = "SessionContext_Key_DeviceKey";

    //message context key for TR069
    public static final String MESSAGECONTEXT_KEY_SOAPMESSAGELIST = "Message_Key_SoapMessageList";

    public static final String COOKIE_SESSION_ID = "TR069_SessionID" ;

    //constants for SOAP/TR069 namespace
    public static final String SOAP_NAMESPACE="http://schemas.xmlsoap.org/soap/envelope/";
    public static final String SOAP_ENCODING="http://schemas.xmlsoap.org/soap/encoding/";
    public static final String TR069_NAMESPACE="urn:dslforum-org:cwmp-1-0";
    public static final String SOAP_XSD_NAMESPACE="http://www.w3.org/2001/XMLSchema";
    public static final String SOAP_XSI_NAMESPACE="http://www.w3.org/2001/XMLSchema-instance";
    public static final String NAMESPACE_CWMP="cwmp";

    //constants for TR069 schema
    public static final String SCHEMA_TR069 = "cwmp-1-0";
    public static final String SCHEMA_TR069_AM1 = "cwmp-1-0";
    public static final String SCHEMA_TR069_AM2 = "cwmp-1-1";
    public static final String SCHEMA_TR069_AM3 = "cwmp-1-2";
    public static final String SCHEMA_TR069_AM4 = "cwmp-1-3";

    //constants for SOAP header
    public static final String TR069_SOAP_HEADER_ID = "ID";
    public static final String TR069_SOAP_HEADER_HOLDREQUESTS = "HoldRequests";
    public static final String TR069_SOAP_HEADER_SESSIONTIMEOUT = "SessionTimeout";
    public static final String TR069_SOAP_HEADER_NOMOREREQUESTS = "NoMoreRequests";


    //constants for SOAP/TR069 message
    public static final String TR069_SOAP_FAULTSTRING="CWMP fault";
    public static final String TR069_SOAP_RESPONSE="Response";

    //constants for TR069 RPC methods
    public static final String INFORM_MESSAGE="Inform";
    public static final String TRANSFERCOMPLETE_MESSAGE="TransferComplete";
    public static final String GETRPCMETHODS_MESSAGE="GetRPCMethods";

    public static final String CLIENT_GETPARAMETERVALUES_MESSAGE="GetParameterValues";
    public static final String CLIENT_SETPARAMETERVALUES_MESSAGE="SetParameterValues";
    public static final String CLIENT_DOWNLOAD_MESSAGE="Download";
    public static final String CLIENT_ADDOBJECT_MESSAGE="AddObject";
    public static final String CLIENT_DELETEOBJECT_MESSAGE="DeleteObject";
    public static final String CLIENT_UPLOAD_MESSAGE="Upload";
    public static final String CLIENT_GETPARAMETERATTRIBUTES_MESSAGE = "GetParameterAttributes";
    public static final String CLIENT_SETPARAMETERATTRIBUTES_MESSAGE = "SetParameterAttributes";
    public static final String CLIENT_GETPARAMETERNAMES_MESSAGE = "GetParameterNames";
    public static final String CLIENT_FACTORYRESET_MESSAGE="FactoryReset";
    public static final String CLIENT_GETRPCMETHODS_MESSAGE="GetRPCMethods";
    public static final String CLIENT_REBOOT_MESSAGE="Reboot";
    public static final String CLIENT_SCHEDULEINFORM_MESSAGE="ScheduleInform";

    public static final String CLIENT_SETPROVISIONINGCODE_MESSAGE="SetProvisioningCode";
    public static final String CLIENT_GETPROVISIONINGCODE_MESSAGE="GetProvisioningCode";
    public static final String CLIENT_GETDEVICEMAC_MESSAGE="GetDeviceMAC";
    public static final String CLIETN_REBOOTFORCONFIGURATION_MESSAGE="RebootForConfiguration";
    public static final String CLIENT_GETDEVICEKEY_MESSAGE = "GetDeviceKey";
    public static final String CLIENT_IPPINGTEST_MESSAGE = "IPPingTest";


    public static final String INFORM_MESSAGERESPONSE="InformResponse";
    public static final String TRANSFERCOMPLETE_MESSAGERESPONSE="TransferCompleteResponse";
    public static final String GETRPCMETHODS_MESSAGERESPONSE="GetRPCMethodsResponse";

    public static final String CLIENT_GETPARAMETERVALUES_MESSAGERESPONSE ="GetParameterValuesResponse";
    public static final String CLIENT_SETPARAMETERVALUES_MESSAGERESPONSE ="SetParameterValuesResponse";
    public static final String CLIENT_DOWNLOAD_MESSAGERESPONSE="DownloadResponse";
    public static final String CLIENT_ADDOBJECT_MESSAGERESPONSE="AddObjectResponse";
    public static final String CLIENT_DELETEOBJECT_MESSAGERESPONSE="DeleteObjectResponse";
    public static final String CLIENT_UPLOAD_MESSAGERESPONSE="UploadResponse";
    public static final String CLIENT_REBOOT_MESSAGERESPONSE = "RebootResponse";
    public static final String CLIENT_SCHEDULEINFORM_MESSAGERESPONSE = "ScheduleInformResponse";
    public static final String CLIENT_FACTORYRESET_MESSAGERESPONSE = "FactoryResetResponse";
    public static final String CLIENT_GETPARAMETERNAMES_MESSAGERESPONSE = "GetParameterNamesResponse";
    public static final String CLIENT_GETRPCMETHODS_MESSAGERESPONSE = "GetRPCMethodsResponse";
    public static final String CLIENT_GETPARAMETERATTRIBUTES_MESSAGERESPONSE = "GetParameterAttributesResponse";
    public static final String CLIENT_SETPARAMETERATTRIBUTES_MESSAGERESPONSE = "SetParameterAttributesResponse";
    public static final String CLIENT_MESSAGEFAULT ="Fault";
    public static final String CLIENT_SETPARAMETERVALUES_MESSAGEFAULT ="SetParameterValuesFault";

    //constants for TR069 client side fault code
    public static final String CLIENT_FAULT_METHOD_NOT_SUPPORT="9000";
    public static final String CLIENT_FAULT_REQUEST_DENIED="9001";
    public static final String CLIENT_FAULT_INTERNAL_ERROR="9002";
    public static final String CLIENT_FAULT_INVALID_ARGUMENTS="9003";
    public static final String CLIENT_FAULT_RESOURCE_EXCEEDED="9004";
    public static final String CLIENT_FAULT_INVALID_PARAMETER_NAME="9005";
    public static final String CLIENT_FAULT_INVALID_PARAMETER_TYPE="9006";
    public static final String CLIENT_FAULT_INVALID_PARAMETER_VALUE="9007";
    public static final String CLIENT_FAULT_ATTEMP_TO_SET_NOTWRITE="9008";
    public static final String CLIENT_FAULT_NOTIFICATION_REJECT="9009";
    public static final String CLIENT_FAULT_DOWNLOAD_FAILURE="9010";
    public static final String CLIENT_FAULT_UPLOAD_FAILURE="9011";
    public static final String CLIENT_FAULT_FILE_TRANSFERAUTH_FAILURE="9012";
    public static final String CLIENT_FAULT_UNSUPPPORT_TRANSFER_PROTOCOL="9013";
    public static final String CLIENT_FAULT_TIMEOUT="9899";
    public static final String CLIENT_FAULT_ATOMIC_FAILURE="9898";

    //constants for TR069 server side fault code
    public static final String SERVER_FAULT_METHOD_NOT_SUPPORT="8000";
    public static final String SERVER_FAULT_REQUEST_DENIED="8001";
    public static final String SERVER_FAULT_INTERNAL_ERROR="8002";
    public static final String SERVER_FAULT_INVALID_ARGUMENTS="8003";
    public static final String SERVER_FAULT_RESOURCE_EXCEEDED="8004";
    public static final String SERVER_FAULT_RETRY_REQUEST="8005";
    public static final String SERVER_FAULT_NEED_INFORM="8800";
    public static final String SERVER_FAULT_TOO_MANY_ENVELOPES="8801";
    public static final String SERVER_FAULT_MESSAGE_FORMAT_INVALID="8802";
    public static final String SERVER_FAULT_MESSAGE_UNKNOWN_REASON="8899";
    public static final String SERVER_FAULT_INVALID_DEVICEMAC="8803";

    //constants for customized error
    public static final int SUCCESS=0;
    public static final int ERROR_UNKNOWN_REASON=-1;
    public static final int ERROR_TASK_WAIT_TIMEOUT=1;
    public static final int ERROR_TASK_DEAL_TIMEOUT=2;
    public static final int ERROR_DEPLOYMENT=3;
    public static final int ERROR_PARSE_MESSAGE_FAIL=4;
    public static final int ERROR_MESSAGE_FORMAT_INVALID=5;
    public static final int ERROR_DATA_BINDING=6;
    public static final int ERROR_SESSION_MANAGEMENT=7;
    public static final int ERROR_DISPATCH_TASK=8;
    public static final int ERROR_DEAL_MESSAGE=9;
    public static final int ERROR_SEND_JMS_MESSAGE=10;
    public static final int ERROR_BUILD_REQUEST=11;
    public static final int ERROR_SAVE_ROMFILE=12;
    public static final int ERROR_RESTORE_ROMFILE=13;
    public static final int ERROR_UPGRADE_FIRMWARE=14;
    public static final int ERROR_CREATE_TASK=15;
    public static final int ERROR_GET_DEVICE_MAC=16;
    public static final int ERROR_UNKNOWN_WORKER_TASK=17;
    public static final int ERROR_SET_PROVISIONING_CODE=18;
    public static final int ERROR_CLEANUP_TASK=19;
    public static final int ERROR_UNKNOWN_CONFIGTYPE=20;
    public static final int ERROR_GET_DEVICE_PROVISIONINGCODE=21;
    public static final int ERROR_DEVICE_OFFLINE=22;
    public static final int ERROR_DEVICE_NOT_DISPATCHED = 23;
    public static final int ERROR_ACS_NOT_WORKING = 24;
    public static final int ERROR_DEVICE_NOT_EXISTS = 25;
    public static final int ERROR_UNSUPPORTED_TASK =26;
    public static final int ERROR_GET_PROVISIONING_CODE=27;
    public static final int ERROR_RELATE_TASK_FAIL=28;
    public static final int ERROR_UNKNOWN_MESSAGE_TYPE = 29;
    public static final int ERROR_NO_TRANSFERCOMPLETE = 30;
    public static final int ERROR_FIRMWAREVERSION_MISMATCH = 31;

    //constants for TR069 fault code type
    public static final String SOAP_FAULTCODE_CLIENT="Client";
    public static final String SOAP_FAULTCODE_SERVER="Server";


    //constants for TR069 Parameters
    public static final String TR069_PARAM_SPECVERSION="InternetGatewayDevice.DeviceInfo.SpecVersion";
    public static final String TR069_PARAM_HARDWAREVERSION="InternetGatewayDevice.DeviceInfo.HardwareVersion";
    public static final String TR069_PARAM_SOFTWAREVERSION="InternetGatewayDevice.DeviceInfo.SoftwareVersion";
    public static final String TR069_PARAM_PROVISIONINGCODE="InternetGatewayDevice.DeviceInfo.ProvisioningCode";
    public static final String TR069_PARAM_MANUFACTURER="InternetGatewayDevice.DeviceInfo.Manufacturer";
    public static final String TR069_PARAM_MODEMFIRMWAREVERSION="InternetGatewayDevice.DeviceInfo.ModemFirmwareVersion";
    public static final String TR069_PARAM_ADDITIONALHARDWAREVERSION="InternetGatewayDevice.DeviceInfo.AdditionalHardwareVersion";
    public static final String TR069_PARAM_ADDITIONALSOFTWAREVERSION="InternetGatewayDevice.DeviceInfo.AdditionalSoftwareVersion";
    public static final String TR069_PARAM_CONNECTIONREQUESTURL="InternetGatewayDevice.ManagementServer.ConnectionRequestURL";
    public static final String TR069_PARAM_PARAMETERKEY="InternetGatewayDevice.ManagementServer.ParameterKey";

    public static final String TR069_PARAM_ACSURL = "InternetGatewayDevice.ManagementServer.URL";
    public static final String TR069_PARAM_ACSUSERNAME = "InternetGatewayDevice.ManagementServer.Username";
    public static final String TR069_PARAM_ACSPASSWORD = "InternetGatewayDevice.ManagementServer.Password";
    public static final String TR069_PARAM_CONNECTIONREQUEST_USERNAME = "InternetGatewayDevice.ManagementServer.ConnectionRequestUsername";
    public static final String TR069_PARAM_CONNECTIONREQUEST_PASSWORD = "InternetGatewayDevice.ManagementServer.ConnectionRequestPassword";

    
    
    
    
    public static final String TR069_PARAM_SECGW1 = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.SecGWServer1";
    public static final String TR069_PARAM_SECGW2 = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.SecGWServer2";
    public static final String TR069_PARAM_SECGW3 = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.SecGWServer3";
    public static final String TR069_PARAM_FAPGW1 = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.FAPGWServer1";
    public static final String TR069_PARAM_FAPGW2 = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.FAPGWServer2";
    public static final String TR069_PARAM_FAPGW3 = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.FAPGWServer3";
    public static final String TR069_PARAM_FAPGW_PORT = "InternetGatewayDevice.Services.FAPService.1.FAPControl.UMTS.Gateway.FAPGWPort";
    
    
    
    

    

    

    
    
    
    public static final String INFORM_EVENT_BOOTSTRAP="0 BOOTSTRAP";
    public static final String INFORM_EVENT_BOOT="1 BOOT";
    public static final String INFORM_EVENT_PERIODIC="2 PERIODIC";
    public static final String INFORM_EVENT_SCHEDULED="3 SCHEDULED";
    public static final String INFORM_EVENT_VALUE_CHANGE="4 VALUE CHANGE";
    public static final String INFORM_EVENT_KICKED="5 KICKED";
    public static final String INFORM_EVENT_CONNECTION_REQUEST="6 CONNECTION REQUEST";
    public static final String INFORM_EVENT_TRANSFER_COMPLETE="7 TRANSFER COMPLETE";
    public static final String INFORM_EVENT_DIAGNOSTICS_COMPLETE="8 DIAGNOSTICS COMPLETE";
    public static final String INFORM_EVENT_REQUEST_DOWNLOAD ="9 REQUEST DOWNLOAD";
    public static final String INFORM_EVENT_AUTONOMOUS_TRANSFER_COMPLETE ="10 AUTONOMOUS TRANSFER COMPLETE";

    public static final String INFORM_EVENT_M_REBOOT = "M Reboot";
    public static final String INFORM_EVENT_M_SCHEDULE_INFORM = "M ScheduleInform";
    public static final String INFORM_EVENT_M_DOWNLOAD = "M Download";
    public static final String INFORM_EVENT_UPLOAD = "M Upload";
}
