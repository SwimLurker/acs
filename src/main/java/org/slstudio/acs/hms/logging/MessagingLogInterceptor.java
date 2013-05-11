package org.slstudio.acs.hms.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slstudio.acs.hms.messaging.receiver.IMessageReceiver;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÉÏÎç1:38
 */

@Aspect
@Component
public class MessagingLogInterceptor {
    private static final Log log = LogFactory.getLog(MessagingLogInterceptor.class);
    private String targetName = null;

    @Before(value = "execution(* org.slstudio.acs.hms.messaging.sender.IMessageSender.sendMessage(java.lang.Object)) && args(message)",argNames = "jp, message")
    public void beforeSendMessage(JoinPoint jp, Object message){
        IMessageSender sender = (IMessageSender)jp.getTarget();
        StringBuilder msg = new StringBuilder();
        msg.append("send to target[").append(sender.getTargetName()).append("] with object[").
                append(message.getClass().getCanonicalName()).append("] content:").append(message.toString());

        log.info(msg.toString());
    }

    @Before(value = "execution(* org.slstudio.acs.hms.messaging.receiver.IMessageReceiver.receiveMessage(java.lang.Object)) && args(message)",argNames = "jp, message")
    public void beforeReceiveMessage(JoinPoint jp, Object message){
        IMessageReceiver receiver = (IMessageReceiver)jp.getTarget();
        StringBuilder msg = new StringBuilder();
        msg.append("receive from source[").append(receiver.getSourceName()).append("] with object[").
                append(message.getClass().getCanonicalName()).append("] content:").append(message.toString());

        log.info(msg.toString());
    }
}
