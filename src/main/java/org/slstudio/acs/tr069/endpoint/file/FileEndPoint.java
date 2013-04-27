package org.slstudio.acs.tr069.endpoint.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.ACSConstants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.endpoint.file.AbstractFileEndPoint;
import org.slstudio.acs.kernal.session.context.IMessageContext;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç2:18
 */
public class FileEndPoint extends AbstractFileEndPoint implements IProtocolEndPoint {
    public static final String KEY_REQUESTFILELIST = "RequestFileList";
    public static final String KEY_CURRENTREQUESTFILEINDEX = "CurrentRequestFileIndex";
    public static final String DEFAUTL_REQUEST_FILE = "Inform.txt";
    public static final String DEFAUTL_RESPONSE_FILE = "Output";

    private static final Log log = LogFactory.getLog(FileEndPoint.class);

    public FileEndPoint(File inputDir, File outputDir, File propertiesFile){
        super(inputDir,outputDir,propertiesFile);
    }

    @Override
    protected String getRequestFileName() {
        int currentIndex = 0;
        try{
            currentIndex = Integer.parseInt(getProperty(KEY_CURRENTREQUESTFILEINDEX));
        }catch(Exception exp){
            log.error("Get current request file index error",exp);
        }
        String requestFileName = DEFAUTL_REQUEST_FILE;

        String fileString = getProperty(KEY_REQUESTFILELIST);
        if(fileString != null && (!fileString.equals(""))){
            String[] requestFiles = fileString.split(",");
            if(requestFiles.length>0){
                if(requestFiles.length>currentIndex){
                    requestFileName = requestFiles[currentIndex];
                }else{
                    requestFileName = requestFiles[0];
                }
            }
        }
        return requestFileName;
    }

    @Override
    protected String getResponseFileName(IMessageContext context) {
        String fileName = DEFAUTL_RESPONSE_FILE;
        if(context.getSessionContext() != null){
            fileName = context.getSessionContext().getSessionID();
        }
        if(context.getLastErrorCode() != ACSConstants.ERROR_CODE_SUCCESS){
            fileName += "_error_";
            fileName += Integer.toString(context.getLastErrorCode());
        }
        fileName += ".out";
        return fileName;
    }

    @Override
    protected void afterWriteResponse(IMessageContext context) {
        super.afterWriteResponse(context);
        int currentIndex = 0;
        try{
            currentIndex = Integer.parseInt(getProperty(KEY_CURRENTREQUESTFILEINDEX));
        }catch(Exception exp){
            log.error("Get current request file index error",exp);
        }
        currentIndex = currentIndex + 1;
        saveProperty(KEY_CURRENTREQUESTFILEINDEX, Integer.toString(currentIndex));
    }

}

