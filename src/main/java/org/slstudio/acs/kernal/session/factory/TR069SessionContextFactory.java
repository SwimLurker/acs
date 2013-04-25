package org.slstudio.acs.kernal.session.factory;

import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.context.ITR069SessionContext;
import org.slstudio.acs.kernal.session.context.TR069SessionContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ����12:05
 */
public class TR069SessionContextFactory implements ISessionContextFactory {
    public ISessionContext create(){
        ITR069SessionContext context = new TR069SessionContext();
        return context;
    }
}
