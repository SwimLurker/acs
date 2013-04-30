package org.slstudio.acs.kernal.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.exception.EndPointException;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.kernal.pipeline.IProtocolPipeline;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.contextlocator.ISessionContextLocator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ����1:16
 */
public abstract class AbstractProtocolEngine implements IProtocolEngine {
    private static final Log log = LogFactory.getLog(AbstractProtocolEngine.class);

    private String engineID = null;
    protected List<IProtocolPipeline> pipelines = null;
    private ISessionContextLocator contextLocator = null;

    public String getEngineID() {
        return engineID;
    }

    public void setEngineID(String engineID) {
        this.engineID = engineID;
    }

    public ISessionContextLocator getContextLocator() {
        return contextLocator;
    }

    public void setContextLocator(ISessionContextLocator contextLocator) {
        this.contextLocator = contextLocator;
    }

    public void init() {
        pipelines = new ArrayList<IProtocolPipeline>();
        inintPipelines();
    }

    public final void service(IProtocolEndPoint endPoint) throws ACSException {
        IMessageContext messageContext = prepareMessageContext(endPoint);
        beforeDoService(messageContext);
        try{
            doService(messageContext);
        }finally {
            afterDoService(messageContext);
        }
        writeResponse(endPoint, messageContext);
    }


    private IMessageContext prepareMessageContext(IProtocolEndPoint endPoint) throws ContextException{
        ISessionContext context = contextLocator.retrieve(endPoint);
        return context.newMessageContext(endPoint);
    }

    protected void doService(IMessageContext context) {
        try{
            for(IProtocolPipeline pipeline: pipelines){
                pipeline.processMessage(context);
            }
        }catch(PipelineException exp){
            log.error(exp);
            context.setErrorCode(ACSConstants.ERROR_CODE_PIPELINEHANDLE);
            context.setResponse(exp.getMessage());
        }
    }

    protected void writeResponse(IProtocolEndPoint endPoint,IMessageContext context) throws EndPointException {
        endPoint.writeResponse(context);
    }

    protected abstract void inintPipelines();

    protected void beforeDoService(IMessageContext messageContext) throws ACSException{
    }

    protected void afterDoService(IMessageContext messageContext) throws ACSException {
        int sessionStatus =messageContext.getSessionContext().getStatus();
        if(sessionStatus == ACSConstants.SESSION_STATUS_CREATED ||
                sessionStatus == ACSConstants.SESSION_STATUS_CLOSED ||
                sessionStatus == ACSConstants.SESSION_STATUS_TIMEOUT){
            //session status is not checked, closed, timeout
            getContextLocator().release(messageContext.getEndPoint(),messageContext.getSessionContext());
        }
    }

}

