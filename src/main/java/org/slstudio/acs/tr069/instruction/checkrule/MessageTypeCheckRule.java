package org.slstudio.acs.tr069.instruction.checkrule;

import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç1:52
 */
public class MessageTypeCheckRule implements ITR069MessageCheckRule{

    private String targetMessageType = null;

    public String getTargetMessageType() {
        return targetMessageType;
    }

    public void setTargetMessageType(String targetMessageType) {
        this.targetMessageType = targetMessageType;
    }

    public MessageTypeCheckRule(String targetMessageType) {
        this.targetMessageType = targetMessageType;
    }

    public boolean check(TR069Message request) {
        if(request == null){
            return targetMessageType == null;
        }
        return request.getMessageName().equalsIgnoreCase(targetMessageType);
    }

    public String toString(){
        return "Check message type rule( targetMessgeType = " + targetMessageType +")";
    }
}
