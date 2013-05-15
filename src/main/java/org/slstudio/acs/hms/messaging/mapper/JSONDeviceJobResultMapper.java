package org.slstudio.acs.hms.messaging.mapper;

import org.codehaus.jackson.type.TypeReference;
import org.slstudio.acs.hms.messaging.bean.DeviceJobResultBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ионГ3:19
 */
public class JSONDeviceJobResultMapper extends JSONObjectMapperBase<DeviceJobResultBean> {
    protected TypeReference getTypeReference() {
        return new TypeReference<DeviceJobResultBean>() {};
    }
}