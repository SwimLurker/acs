package org.slstudio.acs.hms.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.exception.MessagingException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ÉÏÎç1:56
 */
public abstract class AbstractJMSMessageReceiver implements IMessageReceiver, MessageListener{
    private static final Log log = LogFactory.getLog(AbstractJMSMessageReceiver.class);

    private IObjectMapper objectMapper = null;
    private String targetClassname = null;

    public String getTargetClassname() {
        return targetClassname;
    }

    public void setTargetClassname(String targetClassname) {
        this.targetClassname = targetClassname;
    }

    public IObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(IObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void onMessage(Message  message){
        try{
            if (message instanceof TextMessage) {
                String str = ((TextMessage)message).getText();
                Object obj = null;
                try{
                    log.debug("convert string:" + str + " to object type: " + targetClassname);
                    obj = objectMapper.toObject(str, targetClassname);
                }catch(Exception exp){
                    log.error("convert from str:" + str + " to object type:" + targetClassname + " error", exp);
                    throw new JMSException("convert string to object error");
                }
                try{
                    receive(obj);
                }catch(MessagingException mexp){
                    log.error("consume message exception", mexp);
                    throw new JMSException("consume message exception");
                }
            } else {
                log.error("message type is not supported");
                throw new JMSException("Message Type Not Supported");
            }
        }catch (JMSException exp){
            log.error("handle message error", exp);
            exp.printStackTrace();
        }
    }

}
