package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: обнГ4:06
 */
public class ScheduleInformResponse extends TR069Message {

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SCHEDULEINFORM_MESSAGERESPONSE;
    }

}