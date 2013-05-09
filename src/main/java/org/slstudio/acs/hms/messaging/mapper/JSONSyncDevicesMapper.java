package org.slstudio.acs.hms.messaging.mapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.bean.SyncDevicesBean;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-9
 * Time: ÏÂÎç11:33
 */
public class JSONSyncDevicesMapper implements IObjectMapper<SyncDevicesBean> {
    private static final Log log = LogFactory.getLog(JSONSyncDevicesMapper.class);
    private ObjectMapper mapper = null;

    public JSONSyncDevicesMapper() {
        mapper = new ObjectMapper();
        mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
    }

    public SyncDevicesBean toObject(String str) throws MessagingException {
        SyncDevicesBean obj = null;
        try {
            obj = mapper.readValue(str, new TypeReference<SyncDevicesBean>(){});
        } catch ( Exception exp){
            log.error("convert string:" + str + " to sync devices bean error", exp);
            throw new MessagingException("convert string to sync device bean error", exp);
        }
        return obj;
    }

    public String fromObject(Object obj) throws MessagingException {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(obj);
            log.debug("convert sync device info json object to string:" + jsonString);
        } catch (IOException e) {
            throw new MessagingException("convert json object to string error");
        }
        return jsonString;
    }
}
