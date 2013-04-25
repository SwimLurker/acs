package org.slstudio.acs.kernal.session.factory;

import org.slstudio.acs.kernal.session.idgenerator.DefaultSessionIDGenerator;
import org.slstudio.acs.kernal.session.idgenerator.ISessionIDGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç1:06
 */
public class SessionIDGeneratorFactory {
    private static SessionIDGeneratorFactory _instance = null;
    private ISessionIDGenerator idGenerator = null;

    protected SessionIDGeneratorFactory(){
    }

    public static SessionIDGeneratorFactory getInstance(){
        if(_instance == null){
            _instance = new SessionIDGeneratorFactory();
        }
        return _instance;
    }

    public ISessionIDGenerator getSessionIDGenerator(){
        if(idGenerator==null){
            idGenerator  = new DefaultSessionIDGenerator();
        }
        return idGenerator;
    }
}
