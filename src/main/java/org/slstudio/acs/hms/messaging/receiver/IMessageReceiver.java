package org.slstudio.acs.hms.messaging.receiver;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ����1:46
 */
public interface IMessageReceiver {
    public void receiveMessage(Object message) throws MessagingException;
    public String getSourceName();
}
