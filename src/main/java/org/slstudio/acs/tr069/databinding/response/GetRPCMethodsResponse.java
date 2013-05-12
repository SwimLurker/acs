package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: обнГ4:01
 */
public class GetRPCMethodsResponse extends TR069Message {
    private List<String> methods = new ArrayList<String>();

    public List<String> getMethods() {
        return methods;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETRPCMETHODS_MESSAGERESPONSE;
    }
}
