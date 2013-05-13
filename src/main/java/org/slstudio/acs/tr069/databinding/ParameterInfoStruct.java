package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÏÂÎç1:42
 */
public class ParameterInfoStruct implements Serializable {
    private String name ;
    private boolean writable;

    public ParameterInfoStruct() {
    }

    public ParameterInfoStruct(String name, boolean writable) {
        this.name = name;
        this.writable = writable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWritable() {
        return writable;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public boolean equals(Object obj) {
        if (obj == null || this.name == null)
            return false;
        else if (obj instanceof ParameterInfoStruct) {
            return this.name.equals(((ParameterInfoStruct) obj).getName());
        }
        else
            return false;
    }

    public static ParameterInfoStruct fromOMElement(OMElement element) throws DataBindingException {
        ParameterInfoStruct pis = new ParameterInfoStruct();

        Iterator nIt = element.getChildrenWithName(new QName("Name"));
        if(nIt == null || !nIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING, "Name is null");

        pis.setName(((OMElement) nIt.next()).getText());

        Iterator wIt = element.getChildrenWithName(new QName("Writable"));
        if(wIt == null || !wIt.hasNext())
            throw new DataBindingException(
                    TR069Constants.ERROR_DATA_BINDING,"Value is null");
        pis.setWritable(
                ConverterUtil.convertToBoolean(
                        ((OMElement) wIt.next()).getText()));
        return pis;
    }

}
