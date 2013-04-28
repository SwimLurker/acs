package org.slstudio.acs.tr069.datamodel;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ÏÂÎç12:01
 */
public abstract class TR069DataModel implements Serializable {

    private static final long serialVersionUID = -6652327507840104101L;

    private String name;
    private String fullName;
    private String alias;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    protected void copyTRDataModelAttribute(TR069DataModel dm){
        this.name = dm.name;
        this.fullName = dm.fullName;
        this.alias = dm.alias;
    }

    abstract public void accept(IDataModelVisitor visitor);
}