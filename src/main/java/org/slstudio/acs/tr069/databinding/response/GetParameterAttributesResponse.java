package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterAttributeStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:54
 */
public class GetParameterAttributesResponse extends TR069Message {
    private List<ParameterAttributeStruct> attributes = new ArrayList<ParameterAttributeStruct>();

    public List<ParameterAttributeStruct> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ParameterAttributeStruct> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERATTRIBUTES_MESSAGERESPONSE;
    }
}

