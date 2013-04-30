package org.slstudio.acs.tr069.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.engine.AbstractProtocolEngine;
import org.slstudio.acs.tr069.dispatcher.DefaultTR069MethodDispatcher;
import org.slstudio.acs.tr069.pipeline.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:23
 */
public class TR069Engine extends AbstractProtocolEngine {
    private static final Log log = LogFactory.getLog(TR069Engine.class);

    @Override
    protected void inintPipelines() {
        pipelines.add(new InitializePipeline());
        pipelines.add(new ParseMessagePipeline());
        pipelines.add(new ValidateMessagePipeline());
        pipelines.add(new CheckSessionPipeline());
        pipelines.add(new DispatchMethodPipeline(new DefaultTR069MethodDispatcher()));
        pipelines.add(new FinalizePipeline());
    }

}
