package org.slstudio.acs.hms.messaging.receiver;

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
 * Date: 13-5-11
 * Time: ÉÏÎç12:10
 */
public class JMSMessageReceiver extends AbstractStringMessageReceiver implements MessageListener {
    private static final Log log = LogFactory.getLog(JMSMessageReceiver.class);

    private String sourceName = null;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void onMessage(Message message) {
        try{
            if (message instanceof TextMessage) {
                try{
                    receiveMessage(((TextMessage) message).getText());
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
