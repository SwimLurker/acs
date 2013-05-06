package org.slstudio.acs.hms.messaging;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ1:46
 */
public interface IMessageReceiver {
    public void receive(Object message) throws MessagingException;
}
