package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.Calendar;

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

}
