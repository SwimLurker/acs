package org.slstudio.acs.tr069.messagedealer.plugin;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.request.InformRequest;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.session.context.ITR069SessionContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ÏÂÎç8:57
 */
public class SaveInformInfoPlugin implements IPreDealMessagePlugin {
    private static final Log log = LogFactory.getLog(SaveInformInfoPlugin.class);

    public void execute(ITR069MessageContext context, SOAPMessage soapMessage, TR069Message tr069Message) {
        if(tr069Message == null ||!(tr069Message instanceof InformRequest)){
            return;
        }
        ITR069SessionContext sessionContext = context.getTR069SessionContext();
        InformRequest inform = (InformRequest)tr069Message;
        DeviceIdStruct idStruct = inform.getDeviceId();
        if(idStruct != null){
            String deviceKey = idStruct.getSerialNumber();
            sessionContext.setDeviceKey(deviceKey);
        }
        int maxSendEnvelope = 1;
        UnsignedInt uiCount = inform.getMaxEnvelopes();
        if(uiCount != null){
            if(uiCount.intValue() == 0){
                maxSendEnvelope = Integer.MAX_VALUE;
            }else{
                maxSendEnvelope = uiCount.intValue();
            }
        }
        //set max send envelope in the session
        sessionContext.setMaxSendEnvelopeCount(maxSendEnvelope);
        log.debug("set max send envelope to :"+maxSendEnvelope);

        //also need to update the can send envelope count in current message context
        log.debug("current can send evelope count is:" + context.getCanSendEnvelopeCount());
        context.setCanSendEnvelopeCount(maxSendEnvelope);
        log.debug("set current can send evelope count to:" + maxSendEnvelope);


    }
}
