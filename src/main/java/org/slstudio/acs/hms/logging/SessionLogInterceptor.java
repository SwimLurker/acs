package org.slstudio.acs.hms.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-24
 * Time: ÏÂÎç12:51
 */
@Aspect
@Component
public class SessionLogInterceptor {
    private static final Log log = LogFactory.getLog(SessionLogInterceptor.class);

    @Resource(name = "sessionManager")
    private ISessionManager sessionManager = null;

    @Before(value = "execution(* org.slstudio.acs.tr069.session.sessionmanager..addSessionContext(org.slstudio.acs.kernal.session.context.ISessionContext))&&args(context)",argNames = "jp,context")
    public void logAddSession(JoinPoint jp, ISessionContext context){
        StringBuilder msg = new StringBuilder();
        msg.append("create new session(sesssionID:").append(context.getSessionID()).append(",clientID:").append(context.getClientID()).append(")");
        log.info(msg.toString());
    }

    @Before(value = "execution(* org.slstudio.acs.tr069.session.sessionmanager..removeSessionContext(java.lang.String))&&args(sessionID)",argNames = "jp,sessionID")
    public void logRemoveSession(JoinPoint jp, String sessionID){
        ISessionContext context = sessionManager.getSessionContext(sessionID);
        StringBuilder msg = new StringBuilder();
        msg.append("removed session(sesssionID:").append(sessionID).append(",clientID:").append(context.getClientID()).append(")");
        log.info(msg.toString());
    }
}
