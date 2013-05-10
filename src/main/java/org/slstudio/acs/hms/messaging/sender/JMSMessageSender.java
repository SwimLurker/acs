package org.slstudio.acs.hms.messaging.sender;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.util.BeanLocator;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ÉÏÎç1:47
 */
public class JMSMessageSender extends AbstractStringMessageSender {
    private static final Log log = LogFactory.getLog(JMSMessageSender.class);

    private JmsTemplate jmsTemplate = null;
    private Destination destination = null;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void sendStringMessage(String messageStr) throws MessagingException {
        log.debug("send message:" + messageStr);
        try{
            jmsTemplate.send(destination, new MyMessageCreator(messageStr));
        }catch(JmsException exp){
            log.error("send message failed",exp);
        }
    }

    public String getTargetName(){
        return destination.toString();
    }

    class MyMessageCreator implements MessageCreator {
        private String messageStr = null;

        public MyMessageCreator(String messageStr){
            this.messageStr = messageStr;
        }

        public Message createMessage(Session session) throws JMSException {
            return session.createTextMessage(messageStr);
        }
    }

    public static void main(String[] args) throws MessagingException {
        DefaultMessageListenerContainer container = (DefaultMessageListenerContainer)BeanLocator.getBean("commandListenerContainer");
        container.start();
        IMessageSender sender = (IMessageSender)BeanLocator.getBean("commandSender");
        DeviceIdStruct id = new DeviceIdStruct();
        id.setOUI("OUI");
        id.setManufacturer("Test");
        id.setProductClass("productClass");
        id.setSerialNumber("123");
        sender.sendMessage(id);
    }

}