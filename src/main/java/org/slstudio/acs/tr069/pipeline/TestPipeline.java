package org.slstudio.acs.tr069.pipeline;

import org.slstudio.acs.tr069.exception.TR069Exception;
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
    protected void process(ITR069MessageContext context) throws TR069Exception {
        System.out.println(context.getSessionContext());
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
