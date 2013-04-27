package org.slstudio.acs.tr069.engine;

import org.slstudio.acs.kernal.engine.AbstractProtocolEngine;
import org.slstudio.acs.tr069.pipeline.TestPipeline;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ионГ12:23
 */
public class TR069Engine extends AbstractProtocolEngine {

    @Override
    protected void inintPipelines() {
        pipelines.add(new TestPipeline());
    }
}
