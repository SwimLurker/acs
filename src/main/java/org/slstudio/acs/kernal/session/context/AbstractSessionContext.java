package org.slstudio.acs.kernal.session.context;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.kernal.exception.ContextException;
import org.slstudio.acs.kernal.session.factory.IMessageContextFactory;
import org.slstudio.acs.kernal.session.factory.SessionIDGeneratorFactory;
import org.slstudio.acs.util.LongToDateSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç12:30
 */
public abstract class AbstractSessionContext implements ISessionContext {
    private String sessionID = null;
    private int status = ACSConstants.SESSION_STATUS_CREATED;
    private String clientID = null;
    private IProtocolEngine engine = null;
    private long timestamp = 0l;
    private Map<String, Object> properties = new HashMap<String, Object>();
    private List<IMessageContext> messageContextList = new ArrayList<IMessageContext>();
    private IMessageContextFactory messageContextFactory = null;

    protected AbstractSessionContext(IMessageContextFactory messageFactory) {
        this.messageContextFactory = messageFactory;
    }
    @JsonIgnore()
    public IMessageContextFactory getMessageContextFactory() {
        return messageContextFactory;
    }

    public void setMessageContextFactory(IMessageContextFactory messageContextFactory) {
        this.messageContextFactory = messageContextFactory;
    }

    public String getSessionID(){
        return sessionID;
    }

    public void setSessionID(String sessionID){
        this.sessionID = sessionID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getClientID(){
        return clientID;
    }

    public void setClientID(String clientID){
        this.clientID = clientID;
    }
    @JsonIgnore()
    public IProtocolEngine getEngine() {
        return engine;
    }

    public void setEngine(IProtocolEngine engine) {
        this.engine = engine;
    }

    @JsonSerialize(using = LongToDateSerializer.class)
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public Object getProperty(String key){
        return properties.get(key);
    }

    public void setProperty(String key, Object value){
        properties.put(key, value);
    }

    public IMessageContext newMessageContext(IProtocolEndPoint endPoint) throws ContextException {
        IMessageContext messageContext = messageContextFactory.create(this);
        messageContext.initMessageContext(endPoint);
        messageContextList.add(messageContext);
        return messageContext;
    }

    public IMessageContext getCurrentMessageContext(){
        return messageContextList.get(messageContextList.size()-1);
    }
    @JsonIgnore()
    public List<? extends IMessageContext> getMessageContextList(){
        return messageContextList;
    }

    public void initSessionContext(IProtocolEndPoint endPoint) throws ContextException {
        properties.clear();
        String newSessionID = SessionIDGeneratorFactory.getInstance().getSessionIDGenerator().generateSessionID();
        setSessionID(newSessionID);
        String newClientID = endPoint.getProperty(ACSConstants.SESSIONCONTEXT_KEY_CLIENTID);
        setClientID(newClientID);
        setTimestamp(System.currentTimeMillis());
        init(endPoint);
        for(IMessageContext messageContext: messageContextList){
            messageContext.initMessageContext(endPoint);
        }
    }

    protected abstract void init(IProtocolEndPoint endPoint) throws ContextException;

}
