package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterInfoStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:56
 */
public class GetParameterNamesResponse extends TR069Message {
    private List<ParameterInfoStruct> parameters = new ArrayList<ParameterInfoStruct>();

    public List<ParameterInfoStruct> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterInfoStruct> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERNAMES_MESSAGERESPONSE;
    }
}

