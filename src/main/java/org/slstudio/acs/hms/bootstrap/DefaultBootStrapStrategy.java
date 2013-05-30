package org.slstudio.acs.hms.bootstrap;

import org.slstudio.acs.hms.bootstrap.bean.BootstrapBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-31
 * Time: ионГ1:39
 */
public class DefaultBootstrapStrategy implements IBootstrapStrategy {
    public BootstrapBean getBootstrapConfig(String deviceKey){
        BootstrapBean bsb = new BootstrapBean();
        bsb.setServingACSURL("http://localhost/acs/tr069am2");
        bsb.setServingACSUsername("testing");
        bsb.setServingACSPassword("testing");
        return bsb;
    }
}
