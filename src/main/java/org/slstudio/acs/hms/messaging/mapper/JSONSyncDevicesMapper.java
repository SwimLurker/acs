package org.slstudio.acs.hms.messaging.mapper;

import org.codehaus.jackson.type.TypeReference;
import org.slstudio.acs.hms.messaging.bean.SyncDevicesBean;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-9
 * Time: обнГ11:33
 */
public class JSONSyncDevicesMapper extends JSONObjectMapperBase<SyncDevicesBean> {
    protected TypeReference getTypeReference() {
        return new TypeReference<SyncDevicesBean>() {};
    }
}