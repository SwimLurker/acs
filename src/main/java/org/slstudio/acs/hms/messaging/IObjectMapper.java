package org.slstudio.acs.hms.messaging;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ2:35
 */
public interface IObjectMapper {
    public Object toObject(String str, String className) throws MessagingException;
    public String fromObject(Object obj) throws MessagingException;
}
