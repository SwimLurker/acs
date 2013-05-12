package org.slstudio.acs.hms.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slstudio.acs.kernal.pipeline.IProtocolPipeline;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-12
 * Time: ÉÏÎç1:05
 */

@Aspect
@Component
public class PipelineLogInterceptor {
    private static final Log log = LogFactory.getLog(PipelineLogInterceptor.class);

    @Before(value = "execution(* org.slstudio.acs.kernal.pipeline.IProtocolPipeline.processMessage(org.slstudio.acs.kernal.session.context.IMessageContext)) && args(message)",argNames = "jp, message")
    public void beforeProcessMessage(JoinPoint jp, IMessageContext message){
        IProtocolPipeline pipeline = (IProtocolPipeline)jp.getTarget();
        StringBuilder msg = new StringBuilder();
        msg.append("before handle message[").append(message.toString()).append("] by pipeline[").
                append(pipeline.getClass().getCanonicalName()).append("] in session[").
                append(message.getSessionContext().getSessionID()).append("]");

        log.info(msg.toString());
    }

    @AfterReturning(value = "execution(* org.slstudio.acs.kernal.pipeline.IProtocolPipeline.processMessage(org.slstudio.acs.kernal.session.context.IMessageContext)) && args(message)",argNames = "jp, message")
    public void afterReturningProcessMessage(JoinPoint jp, IMessageContext message){
        IProtocolPipeline pipeline = (IProtocolPipeline)jp.getTarget();
        StringBuilder msg = new StringBuilder();
        msg.append("handle message[").append(message.toString()).append("] by pipeline[").
                append(pipeline.getClass().getCanonicalName()).append("] in session[").
                append(message.getSessionContext().getSessionID()).append("] finished");

        log.info(msg.toString());
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.kernal.pipeline.IProtocolPipeline.processMessage(org.slstudio.acs.kernal.session.context.IMessageContext)) && args(message)",argNames = "jp, message,e",throwing = "e")
    public void afterThrowingProcessMessage(JoinPoint jp, IMessageContext message, Throwable e){
        IProtocolPipeline pipeline = (IProtocolPipeline)jp.getTarget();
        StringBuilder msg = new StringBuilder();
        msg.append("handle message[").append(message.toString()).append("] by pipeline[").
                append(pipeline.getClass().getCanonicalName()).append("] in session[").
                append(message.getSessionContext().getSessionID()).append("] finished with throw exception["+e.getMessage()+"]");

        log.info(msg.toString());
    }
}
