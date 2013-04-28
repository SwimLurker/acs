package org.slstudio.acs.tr069.engine;

import org.slstudio.acs.kernal.engine.AbstractProtocolEngine;
import org.slstudio.acs.tr069.pipeline.CheckSessionPipeline;
import org.slstudio.acs.tr069.pipeline.InitializePipeline;
import org.slstudio.acs.tr069.pipeline.ParseMessagePipeline;
import org.slstudio.acs.tr069.pipeline.ValidateMessagePipeline;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:23
 */
public class TR069Engine extends AbstractProtocolEngine {

    @Override
    protected void inintPipelines() {
        pipelines.add(new InitializePipeline());
        pipelines.add(new ParseMessagePipeline());
        pipelines.add(new ValidateMessagePipeline());
        pipelines.add(new CheckSessionPipeline());
    }
}
