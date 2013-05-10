package org.slstudio.acs.hms.messaging.sender;

import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.mapper.IObjectMapper;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-10
 * Time: ÏÂÎç11:54
 */
public abstract class AbstractStringMessageSender implements IMessageSender {
    private IObjectMapper objectMapper = null;

    public IObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(IObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Object message) throws MessagingException {
        if(message instanceof String){
            sendStringMessage((String)message);
        }else{
            sendObjectMessage(message);
        }
    }

    public void sendObjectMessage(Object message) throws MessagingException{
        sendStringMessage(objectMapper.toString(message));
    }

    public abstract void sendStringMessage(String message) throws MessagingException;
}
