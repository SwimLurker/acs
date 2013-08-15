package org.slstudio.acs.hms.bootstrap;

import org.slstudio.acs.hms.bootstrap.bean.BootStrapBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-31
 * Time: ����1:39
 */
public class DefaultBootStrapStrategy implements IBootStrapStrategy {
    public BootStrapBean getBootstrapConfig(String deviceKey,String ip){
    	BootStrapBean bsb = new BootStrapBean();
//        bsb.setServingACSURL("http://localhost/acs/tr069am2");
//        bsb.setServingACSUsername("testing");
//        bsb.setServingACSPassword("testing");
        return bsb;
    }
}
