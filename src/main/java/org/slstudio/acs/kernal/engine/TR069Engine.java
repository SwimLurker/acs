package org.slstudio.acs.kernal.engine;

import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.context.ISessionContext;
import org.slstudio.acs.kernal.context.ITR069SessionContext;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.TR069Exception;
import org.slstudio.acs.kernal.pipeline.ITR069Pipeline;
import org.slstudio.acs.kernal.pipeline.TestPipeline;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:23
 */
public class TR069Engine implements IProtocolEngine {
    private List<ITR069Pipeline> pipelines = null;
    public void init() {
        //for testing
        pipelines =  new ArrayList<ITR069Pipeline>();
        pipelines.add(new TestPipeline());
    }

    public void service(IProtocolEndPoint endPoint, ISessionContext context) throws IOException {
        if(context instanceof ITR069SessionContext){
            ITR069SessionContext trContext = (ITR069SessionContext)context;
            InputStream is = endPoint.getInputStream();
            trContext.setInputStream(is);
            try{
                for(ITR069Pipeline pipeline: pipelines){
                    pipeline.process(trContext);
                }
            }catch(TR069Exception exp){
                trContext.setErrorCode(TR069Constants.ERROR_CODE_UNKNOWNERROR);
            }
        }else{
            context.setErrorCode(TR069Constants.ERROR_CODE_UNSUPPORTPROTOCOL);
        }
        endPoint.writeResponse(context);
    }
}
