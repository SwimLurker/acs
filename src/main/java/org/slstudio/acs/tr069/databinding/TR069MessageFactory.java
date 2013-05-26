package org.slstudio.acs.tr069.databinding;

import edu.emory.mathcs.backport.java.util.Collections;
import org.codehaus.jackson.type.TypeReference;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.*;
import org.slstudio.acs.tr069.databinding.response.*;
import org.slstudio.acs.util.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-24
 * Time: ÉÏÎç12:27
 */
public class TR069MessageFactory {
    public static final String MESSAGE_TYPE_INFORM = "inform";
    public static final String MESSAGE_TYPE_INFORM_RESPONSE = "inform_r";
    public static final String MESSAGE_TYPE_GETRPCMETHOD = "getmethod";
    public static final String MESSAGE_TYPE_GETRPCMETHOD_RESPONSE = "getmethod_r";
    public static final String MESSAGE_TYPE_GETPARAMETERNAMES = "getpn";
    public static final String MESSAGE_TYPE_GETPARAMETERNAMES_RESPONSE = "getpn_r";
    public static final String MESSAGE_TYPE_GETPARAMETERVALUES = "getpv";
    public static final String MESSAGE_TYPE_GETPARAMETERVALUES_RESPONSE = "getpv_r";
    public static final String MESSAGE_TYPE_SETPARAMETERVALUES = "setpv";
    public static final String MESSAGE_TYPE_SETPARAMETERVALUES_RESPONSE = "setpv_r";
    public static final String MESSAGE_TYPE_GETPARAMETERATTRIBUTES = "getpa";
    public static final String MESSAGE_TYPE_GETPARAMETERATTRIBUTES_RESPONSE = "getpa_r";
    public static final String MESSAGE_TYPE_SETPARAMETERATTRIBUTES = "setpa";
    public static final String MESSAGE_TYPE_SETPARAMETERATTRIBUTES_RESPONSE = "setpa_r";
    public static final String MESSAGE_TYPE_ADDOBJECT = "addobj";
    public static final String MESSAGE_TYPE_ADDOBJECT_RESPONSE = "addobj_r";
    public static final String MESSAGE_TYPE_DELETEOBJECT = "delobj";
    public static final String MESSAGE_TYPE_DELETEOBJECT_RESPONSE = "delobj_r";
    public static final String MESSAGE_TYPE_DOWNLOAD = "download";
    public static final String MESSAGE_TYPE_DOWNLOAD_RESPONSE = "download_r";
    public static final String MESSAGE_TYPE_UPLOAD = "upload";
    public static final String MESSAGE_TYPE_UPLOAD_RESPONSE = "upload_r";
    public static final String MESSAGE_TYPE_REBOOT = "reboot";
    public static final String MESSAGE_TYPE_REBOOT_RESPONSE = "reboot_r";
    public static final String MESSAGE_TYPE_FACTORYRESET = "factoryreset";
    public static final String MESSAGE_TYPE_FACTORYRESET_RESPONSE = "factoryreset_r";
    public static final String MESSAGE_TYPE_SCHEDULEINFORM = "scheduleinform";
    public static final String MESSAGE_TYPE_SCHEDULEINFORM_RESPONSE = "scheduleinform_r";
    public static final String MESSAGE_TYPE_FAULT = "fault";
    public static final String MESSAGE_TYPE_SETPARAMETERVALUES_FAULT = "setpv_fault";


    private static Map<String, TypeReference> typeTable = Collections.synchronizedMap(new HashMap());
    static {
        typeTable.put(MESSAGE_TYPE_INFORM, new TypeReference<InformRequest>(){});
        typeTable.put(MESSAGE_TYPE_INFORM_RESPONSE, new TypeReference<InformResponse>(){});
        typeTable.put(MESSAGE_TYPE_GETRPCMETHOD, new TypeReference<GetRPCMethodsRequest>(){});
        typeTable.put(MESSAGE_TYPE_GETRPCMETHOD_RESPONSE, new TypeReference<GetRPCMethodsResponse>(){});
        typeTable.put(MESSAGE_TYPE_GETPARAMETERNAMES, new TypeReference<GetParameterNamesRequest>(){});
        typeTable.put(MESSAGE_TYPE_GETPARAMETERNAMES_RESPONSE, new TypeReference<GetParameterNamesResponse>(){});
        typeTable.put(MESSAGE_TYPE_GETPARAMETERVALUES, new TypeReference<GetParameterValuesRequest>(){});
        typeTable.put(MESSAGE_TYPE_GETPARAMETERVALUES_RESPONSE, new TypeReference<GetParameterValuesResponse>(){});
        typeTable.put(MESSAGE_TYPE_SETPARAMETERVALUES, new TypeReference<SetParameterValuesRequest>(){});
        typeTable.put(MESSAGE_TYPE_SETPARAMETERVALUES_RESPONSE, new TypeReference<SetParameterValuesResponse>(){});
        typeTable.put(MESSAGE_TYPE_GETPARAMETERATTRIBUTES, new TypeReference<GetParameterAttributesRequest>(){});
        typeTable.put(MESSAGE_TYPE_GETPARAMETERATTRIBUTES_RESPONSE, new TypeReference<GetParameterAttributesResponse>(){});
        typeTable.put(MESSAGE_TYPE_SETPARAMETERATTRIBUTES, new TypeReference<SetParameterAttributesRequest>(){});
        typeTable.put(MESSAGE_TYPE_SETPARAMETERATTRIBUTES_RESPONSE, new TypeReference<SetParameterAttributesResponse>(){});
        typeTable.put(MESSAGE_TYPE_ADDOBJECT, new TypeReference<AddObjectRequest>(){});
        typeTable.put(MESSAGE_TYPE_ADDOBJECT_RESPONSE, new TypeReference<AddObjectResponse>(){});
        typeTable.put(MESSAGE_TYPE_DELETEOBJECT, new TypeReference<DeleteObjectRequest>(){});
        typeTable.put(MESSAGE_TYPE_DELETEOBJECT_RESPONSE, new TypeReference<DeleteObjectResponse>(){});
        typeTable.put(MESSAGE_TYPE_DOWNLOAD, new TypeReference<DownloadRequest>(){});
        typeTable.put(MESSAGE_TYPE_DOWNLOAD_RESPONSE, new TypeReference<DownloadResponse>(){});
        typeTable.put(MESSAGE_TYPE_UPLOAD, new TypeReference<UploadRequest>(){});
        typeTable.put(MESSAGE_TYPE_UPLOAD_RESPONSE, new TypeReference<UploadResponse>(){});
        typeTable.put(MESSAGE_TYPE_REBOOT, new TypeReference<RebootRequest>(){});
        typeTable.put(MESSAGE_TYPE_REBOOT_RESPONSE, new TypeReference<RebootResponse>(){});
        typeTable.put(MESSAGE_TYPE_FACTORYRESET, new TypeReference<FactoryResetRequest>(){});
        typeTable.put(MESSAGE_TYPE_FACTORYRESET_RESPONSE, new TypeReference<FactoryResetResponse>(){});
        typeTable.put(MESSAGE_TYPE_SCHEDULEINFORM, new TypeReference<ScheduleInformRequest>(){});
        typeTable.put(MESSAGE_TYPE_SCHEDULEINFORM_RESPONSE, new TypeReference<ScheduleInformResponse>(){});
        typeTable.put(MESSAGE_TYPE_FAULT, new TypeReference<FaultResponse>(){});
        typeTable.put(MESSAGE_TYPE_SETPARAMETERVALUES_FAULT, new TypeReference<SetParameterValuesFaultResponse>(){});

    }

    public static TR069Message fromJSONString(String jsonString, String type){
        if(typeTable.containsKey(type)){
            return (TR069Message) JSONUtil.fromJsonString(jsonString, typeTable.get(type));
        }else{
            return null;
        }
    }
}
