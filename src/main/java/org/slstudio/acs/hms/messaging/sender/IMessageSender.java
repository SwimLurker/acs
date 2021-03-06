package org.slstudio.acs.hms.messaging.sender;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ����1:43
 */
public interface IMessageSender {
    public String getTargetName();
    public void sendMessage(Object message) throws MessagingException;
}
