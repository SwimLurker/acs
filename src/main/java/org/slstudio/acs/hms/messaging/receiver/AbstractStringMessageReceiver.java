package org.slstudio.acs.hms.messaging.receiver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.handler.IMessageHandler;
import org.slstudio.acs.hms.messaging.mapper.IObjectMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ÉÏÎç1:56
 */
public abstract class AbstractStringMessageReceiver implements IMessageReceiver {
    private static final Log log = LogFactory.getLog(AbstractStringMessageReceiver.class);

    private IObjectMapper objectMapper = null;
    private List<IMessageHandler> handlers = null;

    public IObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(IObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<IMessageHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<IMessageHandler> handlers) {
        this.handlers = handlers;
    }

    public void consumeStringMessage(String message) throws MessagingException {
        Object obj = objectMapper.toObject(message);
        consumeObjectMessage(obj);
    }

    public void consumeObjectMessage(Object message)throws MessagingException{
        for(IMessageHandler handler: handlers){
            try{
                handler.handle(message);
            }catch(Exception exp){
                log.error("handle message:" + message + " error", exp);
            }
        }
    }


    public final void receiveMessage(Object message) throws MessagingException{
        if(message instanceof String){
            consumeStringMessage((String) message);
        }else{
            consumeObjectMessage(message);
        }
    }

}
