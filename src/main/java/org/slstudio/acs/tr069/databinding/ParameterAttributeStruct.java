package org.slstudio.acs.tr069.databinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:32
 */
public class ParameterAttributeStruct implements Serializable {
    //	Static fields
    public static final int TR069_PARAMETERATTRIBUTE_NOTIFICATIONOFF = 0;
    public static final int TR069_PARAMETERATTRIBUTE_NOTIFICATIONPASSIVE = 1;
    public static final int TR069_PARAMETERATTRIBUTE_NOTIFICATIONACTIVE = 2;

    //	Fields
    private String parameterName = null;
    private int notification = 0;
    private List<String> accessList = new ArrayList<String>();

    public ParameterAttributeStruct() {}

    //	Properties
    public List<String> getAccessList() {
        return accessList;
    }
    public void setAccessList(List<String> accessList) {
        this.accessList = accessList;
    }
    public int getNotification() {
        return notification;
    }
    public void setNotification(int notification) {
        this.notification = notification;
    }
    public String getParameterName() {
        return parameterName;
    }
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public boolean isNotificationOff() {
        return notification == TR069_PARAMETERATTRIBUTE_NOTIFICATIONOFF;
    }

    public boolean isNotificationPassive() {
        return notification == TR069_PARAMETERATTRIBUTE_NOTIFICATIONPASSIVE;
    }

    public boolean isNotificationActive() {
        return notification == TR069_PARAMETERATTRIBUTE_NOTIFICATIONACTIVE;
    }
}
