package org.slstudio.acs.kernal.pipeline;

import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.kernal.session.context.IMessageContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: обнГ1:17
 */
public interface IProtocolPipeline {
    void processMessage(IMessageContext context) throws PipelineException;
}
