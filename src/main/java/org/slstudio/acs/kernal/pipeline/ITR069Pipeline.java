package org.slstudio.acs.kernal.pipeline;

import org.slstudio.acs.kernal.context.ISessionContext;
import org.slstudio.acs.kernal.exception.TR069Exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ионГ12:52
 */
public interface ITR069Pipeline {
    void process(ISessionContext context) throws TR069Exception;
}
