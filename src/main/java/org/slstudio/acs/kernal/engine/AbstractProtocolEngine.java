package org.slstudio.acs.kernal.engine;

import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.pipeline.IProtocolPipeline;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç1:16
 */
public abstract class AbstractProtocolEngine implements IProtocolEngine {
    protected List<IProtocolPipeline> pipelines = null;
    public void init() {
        pipelines = new ArrayList<IProtocolPipeline>();
        inintPipelines();
    }

    public void service(IProtocolEndPoint endPoint, ISessionContext context) throws ACSException {
        IMessageContext messageContext = context.newMessageContext(endPoint);
        System.out.println(pipelines);
        for(IProtocolPipeline pipeline: pipelines){
            pipeline.processMessage(messageContext);
        }
        endPoint.writeResponse(context.getCurrentMessageContext());
    }

    protected abstract void inintPipelines();
}

