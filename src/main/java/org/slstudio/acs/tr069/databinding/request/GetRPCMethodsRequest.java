package org.slstudio.acs.tr069.databinding.request;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: обнГ1:27
 */
public class GetRPCMethodsRequest extends TR069Message {
    @Override
    public String getMessageName() {
        return TR069Constants.GETRPCMETHODS_MESSAGE;
    }
}