package org.slstudio.acs.hms.messaging.mapper;

import org.codehaus.jackson.type.TypeReference;
import org.slstudio.acs.hms.messaging.bean.DeviceJobBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ионГ3:10
 */
public class JSONDeviceJobMapper extends JSONObjectMapperBase<DeviceJobBean> {
    protected TypeReference getTypeReference() {
        return new TypeReference<DeviceJobBean>() {};
    }
}