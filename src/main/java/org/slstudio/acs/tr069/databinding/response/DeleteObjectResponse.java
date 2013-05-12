package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:47
 */
public class DeleteObjectResponse extends TR069Message {
    public static final int STATUS_OBJECT_DELETED=0;
    public static final int STATUS_NEED_APPLY=1;

    private int status=STATUS_OBJECT_DELETED;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_DELETEOBJECT_MESSAGERESPONSE;
    }

}
