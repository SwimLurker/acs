package org.slstudio.acs.hms.messaging.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ2:33
 */
public class DebugMessageHandler implements IMessageHandler {
    private static final Log log = LogFactory.getLog(DebugMessageHandler.class);

    public void handle(Object message) throws MessagingException {
        log.debug("receive message type:" + message.getClass().getName() +", value:" + message.toString());
    }
}
