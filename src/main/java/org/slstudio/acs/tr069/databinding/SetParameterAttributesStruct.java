package org.slstudio.acs.tr069.databinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ����3:39
 */
public class SetParameterAttributesStruct implements Serializable {
    private String name;
    private boolean notificationChange;
    private int notification;
    private boolean accessListChange;
    private List<String> accessList = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getNotificationChange() {
        return notificationChange;
    }

    public void setNotificationChange(boolean notificationChange) {
        this.notificationChange = notificationChange;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public boolean getAccessListChange() {
        return accessListChange;
    }

    public void setAccessListChange(boolean accessListChange) {
        this.accessListChange = accessListChange;
    }

    public List<String> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<String> accessList) {
        this.accessList = accessList;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SetParameterAttributesStruct that = (SetParameterAttributesStruct) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("getName ").append(this.getName()).append("   ");
        sb.append("getNotification ").append(this.getNotification()).append("   ");
        sb.append("getAccessList ").append(this.getAccessList()).append("   ");
        sb.append("isNotificationChange ").append(this.getNotificationChange()).append("   ");
        sb.append("isAccessListChange ").append(this.getAccessListChange()).append("\n");
        return sb.toString();
    }
}