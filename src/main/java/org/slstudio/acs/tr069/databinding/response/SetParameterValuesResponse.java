package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:09
 */
public class SetParameterValuesResponse extends TR069Message {
    public static final int STATUS_SUCCESS=0;
    public static final int STATUS_SUCCESS_NOT_APPLIED=1;

    int status=STATUS_SUCCESS;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGERESPONSE;
    }
}