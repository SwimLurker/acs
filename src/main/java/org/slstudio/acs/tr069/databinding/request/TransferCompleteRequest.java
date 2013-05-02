package org.slstudio.acs.tr069.databinding.request;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.FaultStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Iterator;

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

    public TransferCompleteRequest(SOAPEnvelope envelope) throws DataBindingException {
        super(envelope);
        OMElement element=envelope.getBody().getFirstElement();
        //commandKey
        Iterator cmdKeyIt= element.getChildrenWithName(new QName("CommandKey"));
        if(cmdKeyIt==null||!cmdKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"command key is null");
        }
        this.commandKey=((OMElement)cmdKeyIt.next()).getText();

        Iterator faultIt= element.getChildrenWithName(new QName("FaultStruct"));
        if(faultIt==null||!faultIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"fault struct is null");
        }
        this.faultStruct=new FaultStruct((OMElement)faultIt.next());
        //startTime
        Iterator startTimeIt= element.getChildrenWithName(new QName("StartTime"));
        if(startTimeIt==null||!startTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time is null");
        }
        try {
            String startTimeStr=((OMElement)startTimeIt.next()).getText();
            if(startTimeStr!=null&&(!startTimeStr.equals(""))){
                this.startTime= ConverterUtil.convertToDateTime(startTimeStr);
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time format error",e);
        }
        //completeTime
        Iterator completeTimeIt= element.getChildrenWithName(new QName("CompleteTime"));
        if(completeTimeIt==null||!completeTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time is null");
        }
        try {
            String completeTimeStr=((OMElement)completeTimeIt.next()).getText();
            if(completeTimeStr!=null&&(!completeTimeStr.equals(""))){
                this.completeTime= ConverterUtil.convertToDateTime(completeTimeStr);
            }
        } catch (Exception e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time format error",e);
        }
    }
}
