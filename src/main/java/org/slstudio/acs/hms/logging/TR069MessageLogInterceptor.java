package org.slstudio.acs.hms.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.tr069.pipeline.FinalizePipeline;
import org.slstudio.acs.tr069.pipeline.InitializePipeline;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-24
 * Time: ÉÏÎç12:57
 */

@Aspect
@Component
public class TR069MessageLogInterceptor {
    private static final Log log = LogFactory.getLog(TR069MessageLogInterceptor.class);

    @Before(value = "execution(* org.slstudio.acs.tr069.pipeline..*(org.slstudio.acs.kernal.session.context.IMessageContext))&&args(context)",argNames = "jp,context")
    public void logTR069Message(JoinPoint jp, IMessageContext context){
        Object obj = jp.getTarget();
        if(obj instanceof InitializePipeline){
            String message = replaceInputStream(context);
            StringBuilder msg = new StringBuilder();
            msg.append("receive tr069 message from client(").append(context.getSessionContext().getClientID()).append("):").append(message==null?"empty message":message);
            log.info(msg.toString());
        }else if(obj instanceof FinalizePipeline){
            String message = context.getResponse();
            StringBuilder msg = new StringBuilder();
            msg.append("send tr069 message to client(").append(context.getSessionContext().getClientID()).append("):").append(message==null?"empty message":message);
            log.info(msg.toString());
        }
    }

    @AfterThrowing(value = "execution(* org.slstudio.acs.tr069.pipeline..*(org.slstudio.acs.kernal.session.context.IMessageContext))&&args(context)",argNames = "jp,context,e", throwing = "e")
    public void logErrorMessage(JoinPoint jp, IMessageContext context, Throwable e){
        StringBuilder msg = new StringBuilder();
        msg.append("send error message to client(").append(context.getSessionContext().getClientID()).append("):error code--400, error msg--").append(e.getMessage());
        log.info(msg.toString());
    }

    private String replaceInputStream(IMessageContext context) {
        InputStream is = null;
        try{
            is = context.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int readNum = -1;
            while((readNum =is.read(buf,0,1024))!=-1){
                baos.write(buf, 0 ,readNum);
            }
            String result = baos.toString();
            context.setInputStream(new ByteArrayInputStream(baos.toByteArray()));
            baos.close();
            return result;

        }catch(Exception exp){
            exp.printStackTrace();
        }
        return null;
    }
}
