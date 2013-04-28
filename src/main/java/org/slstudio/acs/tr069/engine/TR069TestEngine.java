package org.slstudio.acs.tr069.engine;

import org.slstudio.acs.kernal.engine.AbstractProtocolEngine;
import org.slstudio.acs.tr069.pipeline.TestPipeline;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: обнГ4:00
 */
public class TR069TestEngine extends AbstractProtocolEngine {

    @Override
    protected void inintPipelines() {
        pipelines.add(new TestPipeline());
    }
}
