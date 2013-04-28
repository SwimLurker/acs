package org.slstudio.acs.tr069.pipeline;

import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç1:06
 */
public class TestPipeline extends AbstractTR069Pipeline {
    @Override
    protected void process(ITR069MessageContext context) throws PipelineException {
        InputStream is = context.getInputStream();
        StringBuffer inputString=new StringBuffer();
        try{
            int ch;
            while((ch=is.read())!=-1){
                inputString.append((char)ch);
            }
        }catch (Exception exp){
            throw new PipelineException("Read data from inputstream error", exp);
        }
        String clientIP = context.getTR069SessionContext().getClientIP();
        context.setResponse(inputString.toString()+"\r\nClientIP="+clientIP);
    }
}
