package org.slstudio.acs.hms.messaging.handler;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ионГ12:07
 */
public interface IMessageHandler {
    public void handle(Object message) throws MessagingException;
}
