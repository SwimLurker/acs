package org.slstudio.acs.tr069.databinding;

import java.io.Serializable;

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
}
