package org.slstudio.acs.kernal.pipeline;

import org.slstudio.acs.kernal.context.ISessionContext;
import org.slstudio.acs.kernal.exception.TR069Exception;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ионГ1:06
 */
public class TestPipeline implements ITR069Pipeline {
    public void process(ISessionContext context) throws TR069Exception {
        context.setResponse("just for testing");
    }
}
