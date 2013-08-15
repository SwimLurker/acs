package org.slstudio.acs.hms.bootstrap;

import org.slstudio.acs.hms.bootstrap.bean.BootStrapBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-31
 * Time: ����2:33
 */
public interface IBootStrapStrategy {
    public BootStrapBean getBootstrapConfig(String deviceKey,String ip);
}
