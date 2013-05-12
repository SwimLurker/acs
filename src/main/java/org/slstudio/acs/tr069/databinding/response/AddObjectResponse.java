package org.slstudio.acs.tr069.databinding.response;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:24
 */
public class AddObjectResponse extends TR069Message {
    public static final int STATUS_OBJECT_ADDED=0;
    public static final int STATUS_NEED_APPLY=1;

    private UnsignedInt instanceNumber=null;
    private int status=STATUS_OBJECT_ADDED;

    public UnsignedInt getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(UnsignedInt instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_ADDOBJECT_MESSAGERESPONSE;
    }

}