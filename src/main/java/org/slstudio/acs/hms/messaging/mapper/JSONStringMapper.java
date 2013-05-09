package org.slstudio.acs.hms.messaging.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.slstudio.acs.hms.exception.MessagingException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ÉÏÎç2:36
 */
public class JSONStringMapper implements IObjectMapper {
    private static final Log log = LogFactory.getLog(JSONStringMapper.class);

    private ObjectMapper mapper = null;

    public JSONStringMapper() {
        mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
    }

    public String toObject(String str) throws MessagingException {
        String obj = null;
        try {
            obj = mapper.readValue(str, new TypeReference<String>(){});
        } catch ( Exception exp){
            log.error("convert string:" + str + " to object type:String error", exp);
            throw new MessagingException("convert string to object error", exp);
        }
        return obj;
    }

    public String fromObject(Object obj) throws MessagingException{
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(obj);
            log.debug("convert json object type:" + obj.getClass().getName() + " to string:" + jsonString);
        } catch (IOException e) {
            throw new MessagingException("convert json object to string error");
        }
        return jsonString;
    }
}
