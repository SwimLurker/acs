package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterValueStruct;
import org.slstudio.acs.tr069.databinding.TR069Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç3:59
 */
public class GetParameterValuesResponse  extends TR069Message {
    private List<ParameterValueStruct> parameterList=new ArrayList<ParameterValueStruct>() ;

    public List<ParameterValueStruct> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterValueStruct> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_GETPARAMETERVALUES_MESSAGERESPONSE;
    }

}

