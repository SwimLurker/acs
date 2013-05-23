package org.slstudio.acs.util;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÏÂÎç2:13
 */
public class JSONUtil {
    private static ObjectMapper mapper = null;
    static {
        mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.GETTER, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
    }
    public static String toJsonString(Object obj){
        if(obj == null) return null;
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static Object fromJsonString(String jsonStr, TypeReference tr){
        try {
            return mapper.readValue(jsonStr, tr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
