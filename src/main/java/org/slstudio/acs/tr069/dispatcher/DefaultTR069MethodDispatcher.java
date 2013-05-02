package org.slstudio.acs.tr069.dispatcher;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.messagedealer.EmptyMessageDealer;
import org.slstudio.acs.tr069.messagedealer.ITR069MethodDealer;
import org.slstudio.acs.tr069.messagedealer.request.GetRPCMethodsRequestDealer;
import org.slstudio.acs.tr069.messagedealer.request.InformRequestDealer;
import org.slstudio.acs.tr069.messagedealer.request.TransferCompleteRequestDealer;
import org.slstudio.acs.tr069.messagedealer.response.*;
import org.slstudio.acs.tr069.soap.SOAPUtil;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÏÂÎç12:56
 */
public class DefaultTR069MethodDispatcher implements ITR069MethodDispatcher {
    public ITR069MethodDealer findMethodDealer(SOAPEnvelope envelope) {
        if(envelope==null){
            return new EmptyMessageDealer();
        }
        SOAPFault fault=envelope.getBody().getFault();
        if(fault==null){
            String commandName= SOAPUtil.getCommandName(envelope);
            if(commandName==null){
                return null;
            }else if(TR069Constants.CLIENT_GETPARAMETERVALUES_MESSAGERESPONSE.equals(commandName)){
                return new GetParameterValuesResponseDealer();
            }else if(TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGERESPONSE.equals(commandName)){
                return new SetParameterValuesResponseDealer();
            }else if(TR069Constants.CLIENT_DOWNLOAD_MESSAGERESPONSE.equals(commandName)){
                return new DownloadResponseDealer();
            }else if(TR069Constants.CLIENT_ADDOBJECT_MESSAGERESPONSE.equals(commandName)){
                return new AddObjectResponseDealer();
            }else if(TR069Constants.CLIENT_DELETEOBJECT_MESSAGERESPONSE.equals(commandName)){
                return new DeleteObjectResponseDealer();
            }else if(TR069Constants.CLIENT_UPLOAD_MESSAGERESPONSE.equals(commandName)){
                return new UploadResponseDealer();
            } else if (TR069Constants.CLIENT_REBOOT_MESSAGERESPONSE.equals(commandName)) {
                return new RebootResponseDealer();
            } else if (TR069Constants.CLIENT_SCHEDULEINFORM_MESSAGERESPONSE.equals(commandName)) {
                return new ScheduleInformResponseDealer();
            } else if (TR069Constants.CLIENT_FACTORYRESET_MESSAGERESPONSE.equals(commandName)) {
                return new FactoryResetResponseDealer();
            } else if (TR069Constants.CLIENT_GETPARAMETERNAMES_MESSAGERESPONSE.equals(commandName)) {
                return new GetParameterNamesResponseDealer();
            } else if (TR069Constants.CLIENT_GETRPCMETHODS_MESSAGERESPONSE.equals(commandName)) {
                return new GetRPCMethodsResponseDealer();
            }else if(TR069Constants.INFORM_MESSAGE.equals(commandName)){
                return new InformRequestDealer();
            }else if(TR069Constants.TRANSFERCOMPLETE_MESSAGE.equals(commandName)){
                return new TransferCompleteRequestDealer();
            }else if(TR069Constants.GETRPCMETHODS_MESSAGE.equals(commandName)){
                return new GetRPCMethodsRequestDealer();
            }else if (TR069Constants.CLIENT_GETPARAMETERATTRIBUTES_MESSAGERESPONSE.equals(commandName)) {
                return new GetParameterAttributesResponseDealer();
            }else if (TR069Constants.CLIENT_SETPARAMETERATTRIBUTES_MESSAGERESPONSE.equals(commandName)) {
                return new SetParameterAttributesResponseDealer();
            }else{
                return null;
            }
        }else{
            //fault
            OMElement detailElement=fault.getDetail().getFirstElement();
            Iterator it=detailElement.getChildrenWithName(new QName(TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGEFAULT));
            if(it==null||!it.hasNext()){
                return new FaultDealer();
            }else{
                return new SetParameterValuesFaultDealer();
            }
        }
    }
}
