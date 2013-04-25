package org.slstudio.acs.kernal.pipeline;

import org.slstudio.acs.kernal.exception.TR069Exception;
import org.slstudio.acs.kernal.session.context.ISessionContext;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç1:06
 */
public class TestPipeline implements ITR069Pipeline {
    public void process(ISessionContext context) throws TR069Exception {
        InputStream is = context.getInputStream();
        StringBuffer inputString=new StringBuffer();
        try{
            int ch;
            while((ch=is.read())!=-1){
                inputString.append((char)ch);
            }
        }catch (Exception exp){
            throw new TR069Exception("Read data from inputstream error", exp);
        }
        context.setResponse(inputString.toString());
    }
}
