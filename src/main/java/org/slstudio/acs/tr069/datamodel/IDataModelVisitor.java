package org.slstudio.acs.tr069.datamodel;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: ����12:02
 */
public interface IDataModelVisitor {
    public abstract void visit(TR069DataModel datamodel);
}
