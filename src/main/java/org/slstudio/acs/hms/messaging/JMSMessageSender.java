package org.slstudio.acs.hms.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.tr069.databinding.DeviceIdStruct;
import org.slstudio.acs.util.BeanLocator;
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
public class JMSMessageSender implements IMessageSender{
    private static final Log log = LogFactory.getLog(JMSMessageSender.class);

    private JmsTemplate jmsTemplate = null;
    private Destination destination = null;
    private IObjectMapper objectMapper = null;

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

    public IObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(IObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Object message) throws MessagingException {
        jmsTemplate.send(destination, new MyMessageCreator(objectMapper,message));
    }

    class MyMessageCreator implements MessageCreator {
        private IObjectMapper objectMapper = null;

        private Object message = null;

        public MyMessageCreator(IObjectMapper mapper, Object message){
            this.objectMapper = mapper;
            this.message = message;
        }

        public Message createMessage(Session session) throws JMSException {
            String str = null;
            try {
                str = objectMapper.fromObject(message);
            } catch (MessagingException e) {
                log.debug("convert form object type:" + message.getClass().getName() + " to string error", e);
                throw new JMSException("convert object to string error");
            }
            return session.createTextMessage(str);
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