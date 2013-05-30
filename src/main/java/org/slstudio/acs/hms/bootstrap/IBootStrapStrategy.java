package org.slstudio.acs.hms.bootstrap;

import org.slstudio.acs.hms.bootstrap.bean.BootstrapBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-31
 * Time: ионГ2:33
 */
public interface IBootstrapStrategy {
    public BootstrapBean getBootstrapConfig(String deviceKey);
}
