package org.slstudio.acs.tr069.engine;

import org.slstudio.acs.kernal.engine.AbstractProtocolEngine;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.tr069.pipeline.TestPipeline;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç3:55
 */
public class TR069AM1Engine extends AbstractProtocolEngine {
    @Override
    protected void inintPipelines() {
        pipelines.add(new TestPipeline());
    }
    @Override
    protected void doService(IMessageContext context) throws PipelineException{
        throw new PipelineException("Unsupport TR069 version");
    }
}