package org.slstudio.acs.hms.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slstudio.acs.hms.exception.MessagingException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-7
 * Time: ÉÏÎç2:36
 */
public class JSONObjectMapper implements IObjectMapper {
    private static final Log log = LogFactory.getLog(JSONObjectMapper.class);

    private ObjectMapper mapper = null;

    public JSONObjectMapper() {
        mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
    }

    public Object toObject(String str, String className) throws MessagingException {
        Object obj = null;
        try {
            obj = mapper.readValue(str, Class.forName(className));
            log.debug("convert string:" + str + " to object type:" + className);
        } catch ( Exception exp){
            log.error("convert string:" + str + " to object type:" + className + " error", exp);
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
