package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMElement;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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

    //	Constructors
    public ParameterAttributeStruct(OMElement element) throws DataBindingException {
        Iterator nIt = element.getChildrenWithName(new QName("Name"));
        if(nIt == null || !nIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING, "Name is null");

        this.setParameterName(((OMElement)nIt.next()).getText());

        Iterator wIt = element.getChildrenWithName(new QName("Notification"));
        if(wIt == null || !wIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING,"Notification is null");
        this.setNotification(
                Integer.parseInt(((OMElement) wIt.next()).getText()));

        Iterator aIt = element.getChildrenWithName(new QName("AccessList"));
        if(aIt == null || !aIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING,"AccessList is null");
        OMElement accessElement = (OMElement) aIt.next();
        Iterator iter = accessElement.getChildElements();
        while (iter != null && iter.hasNext()) {
            this.addAccess(((OMElement) iter.next()).getText());
        }
    }

    public ParameterAttributeStruct() {}

    //	Methods
    public void addAccess(String access) {
        this.accessList.add(access);
    }

}
