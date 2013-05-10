package org.slstudio.acs.hms.messaging.mapper;

import org.slstudio.acs.hms.exception.MessagingException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ2:35
 */
public  interface IObjectMapper<T> {
    public T toObject(String str) throws MessagingException;
    public String toString(Object obj) throws MessagingException;
}
