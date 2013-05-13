package org.slstudio.acs.tr069.databinding.response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:10
 */
public class UploadResponse extends TR069Message {
    public static final int STATUS_UPLOAD_COMPLETE=0;
    public static final int STATUS_UPLOAD_NOT_COMPLETE=1;

    private int status=STATUS_UPLOAD_COMPLETE;
    private Calendar startTime=null;
    private Calendar completeTime=null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return TR069Constants.CLIENT_UPLOAD_MESSAGERESPONSE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<Status>").append(ConverterUtil.convertToString(status)).append("</Status>");
        result.append("<StartTime>").append(startTime==null?"":ConverterUtil.convertToString(startTime)).append("</StartTime>");
        result.append("<CompleteTime>").append(completeTime==null?"":ConverterUtil.convertToString(completeTime)).append("</CompleteTime>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static UploadResponse fromEnvelope(SOAPEnvelope envelope) throws DataBindingException {
        UploadResponse ur = new UploadResponse();
        populateHeaderValues(ur, envelope.getHeader());

        OMElement element=envelope.getBody().getFirstElement();
        //status
        Iterator statusKeyIt= element.getChildrenWithName(new QName("Status"));
        if(statusKeyIt==null||!statusKeyIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"status is null");
        }
        ur.setStatus(ConverterUtil.convertToInt(((OMElement) statusKeyIt.next()).getText()));

        //startTime
        Iterator startTimeIt= element.getChildrenWithName(new QName("StartTime"));
        if(startTimeIt==null||!startTimeIt.hasNext()){
            ur.setStartTime(null);
        }else{
            try {
                String startTimeStr=((OMElement)startTimeIt.next()).getText();
                if(startTimeStr!=null&&(!startTimeStr.equals(""))){
                    ur.setStartTime(ConverterUtil.convertToDateTime(startTimeStr));
                }
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"start time format error",e);
            }
        }
        //completeTime
        Iterator completeTimeIt= element.getChildrenWithName(new QName("CompleteTime"));
        if(completeTimeIt==null||!completeTimeIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time is null");
        }else{
            try {
                String completeTimeStr=((OMElement)completeTimeIt.next()).getText();
                if(completeTimeStr!=null&&(!completeTimeStr.equals(""))){
                    ur.setCompleteTime(ConverterUtil.convertToDateTime(completeTimeStr));
                }
            } catch (Exception e) {
                throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"complete time format error",e);
            }
        }
        return ur;
    }


}
