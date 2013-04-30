package org.slstudio.acs.kernal.endpoint.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.EndPointException;
import org.slstudio.acs.kernal.session.context.IMessageContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç10:19
 */
public abstract class AbstractHttpEndPoint implements IProtocolEndPoint {
    private static final Log log = LogFactory.getLog(AbstractHttpEndPoint.class);

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private int httpStatus = HttpServletResponse.SC_OK;
    private String contentType = null;

    public AbstractHttpEndPoint(HttpServletRequest req, HttpServletResponse res){
        this.request = req;
        this.response = res;
        initEndPoint();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getProperty(String name){
        if(ACSConstants.SESSIONCONTEXT_KEY_CLIENTID.equals(name)){
            return getClientID();
        }else if(ACSConstants.SESSIONCONTEXT_KEY_SESSIONID.equals(name)){
            return getSessionID();
        }
        String value = getHeaderValue(name);
        if(value == null || value.equals("")){
            value = getCookieValue(name);
        }
        return value;
    }

    public void saveProperty(String name, String value){
        setHeaderValue(name, value);
    }

    public final InputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public final void writeResponse(IMessageContext context) throws EndPointException {
        beforeWriteResponse(context);
        writeHttpResponse(context.getResponse());
        afterWriteResponse(context);
        finishEndPoint();
    }

    protected void initEndPoint(){
    }

    protected void beforeWriteResponse(IMessageContext context){
    }

    protected void afterWriteResponse(IMessageContext context){
    }

    protected void finishEndPoint(){
    }

    protected abstract String getSessionID();

    protected abstract String getClientID() ;

    private void writeHttpResponse(String responseText) throws EndPointException {
        HttpServletResponse response = getResponse();
        response.setStatus(httpStatus);
        response.setContentType(contentType);
        if(responseText == null){
            response.setContentLength(0);
        }else{
            response.setContentLength(responseText.length());
        }
        log.debug("http status:"+httpStatus);
        log.debug("content type:"+contentType);
        log.debug("response:"+responseText);

        PrintWriter pw= null;
        try {
            pw = response.getWriter();
            if(responseText!=null){
                pw.println(responseText);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new EndPointException("Get writer from http servlet esponse error",e);
        }
    }

    private String getHeaderValue(String headerName) {
        return request.getHeader(headerName);
    }

    private void setHeaderValue(String headerName, String headerValue) {
        response.setHeader(headerName, headerValue);
    }

    private String getCookieValue(String cookieName) {
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for(int i=0;i<cookies.length;i++){
                Cookie c=cookies[i];
                if(c.getName().equals(cookieName)){
                    return c.getValue();
                }
            }
        }
        return null;
    }

}

