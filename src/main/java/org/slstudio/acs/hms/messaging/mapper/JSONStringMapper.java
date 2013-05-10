package org.slstudio.acs.hms.messaging.mapper;

import org.codehaus.jackson.type.TypeReference;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ионГ2:36
 */
public class JSONStringMapper extends JSONObjectMapperBase<String> {
    @Override
    protected TypeReference getTypeReference() {
        return new TypeReference<String>() {};
    }
}
