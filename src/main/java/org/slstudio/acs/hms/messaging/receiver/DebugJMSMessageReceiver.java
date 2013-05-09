package org.slstudio.acs.hms.messaging.receiver;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ2:33
 */
public class DebugJMSMessageReceiver extends AbstractJMSMessageReceiver {
    public void receive(Object message) throws MessagingException {
        System.out.println("receive message type:" + message.getClass().getName() +", value:" + message.toString());
    }
}
