package org.slstudio.acs.tr069.databinding.response;

import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.ParameterFaultStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç4:07
 */
public class SetParameterValuesFaultResponse extends FaultResponse {
    private List<ParameterFaultStruct> parameterFaultList=new ArrayList<ParameterFaultStruct>();

    public List<ParameterFaultStruct> getParameterFaultList() {
        return parameterFaultList;
    }

    public void setParameterFaultList(List<ParameterFaultStruct> parameterFaultList) {
        this.parameterFaultList = parameterFaultList;
    }

     @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGEFAULT;
    }
}