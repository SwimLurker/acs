package org.slstudio.acs.tr069.engine.spec;

import org.slstudio.acs.tr069.constant.TR069Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-3
 * Time: ÉÏÎç10:36
 */
public class TR069Spec implements ITR069Spec {

    private List<String> rpcMethodList = null;

    public TR069Spec() {
        rpcMethodList = new ArrayList<String>();
        rpcMethodList.add("Inform");
        rpcMethodList.add("GetRPCMethod");
        rpcMethodList.add("TransferComplete");
    }

    public List<String> getRpcMethodList(){
        return rpcMethodList;
    }

    public String getSchemaName() {
        return TR069Constants.SCHEMA_TR069;
    }

}
