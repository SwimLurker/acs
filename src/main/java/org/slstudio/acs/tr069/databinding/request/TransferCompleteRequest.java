package org.slstudio.acs.tr069.databinding.request;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.FaultStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:28
 */
public class TransferCompleteRequest extends TR069Message {
    private String commandKey=null;
    private FaultStruct faultStruct=null;
    private Calendar startTime=null;
    private Calendar completeTime=null;

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public FaultStruct getFaultStruct() {
        return faultStruct;
    }

    public void setFaultStruct(FaultStruct faultStruct) {
        this.faultStruct = faultStruct;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Calendar completeTime) {
        this.completeTime = completeTime;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.TRANSFERCOMPLETE_MESSAGE;
    }
}
