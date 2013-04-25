package org.slstudio.acs.kernal.endpoint.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.TR069Constants;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.EndPointException;
import org.slstudio.acs.kernal.session.context.ISessionContext;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-25
 * Time: ÉÏÎç2:18
 */
public class FileEndPoint implements IProtocolEndPoint {
    public static final String KEY_REQUESTFILELIST = "RequestFileList";
    public static final String KEY_CURRENTREQUESTFILEINDEX = "CurrentRequestFileIndex";
    public static final String DEFAUTL_REQUEST_FILE = "Inform.txt";

    private static final Log log = LogFactory.getLog(FileEndPoint.class);

    private String rootPath = null;
    private String propertiesFileName = null;
    private Properties properties = new Properties();

    public FileEndPoint(String rootPath, String propertiesFileName){
        this.rootPath = rootPath;
        this.propertiesFileName = propertiesFileName;
        init();
    }


    public String getProperty(String name){
        return properties.getProperty(name);
    }

    public InputStream getInputStream() throws IOException {
        int currentIndex = 0;
        try{
            currentIndex = Integer.parseInt((String)properties.get(KEY_CURRENTREQUESTFILEINDEX));
        }catch(Exception exp){
            log.error("Get current request file index error",exp);
        }
        String requestFileName = DEFAUTL_REQUEST_FILE;

        String fileString = (String)properties.get(KEY_REQUESTFILELIST);
        if(fileString != null && (!fileString.equals(""))){
            String[] requestFiles = fileString.split(",");
            if(requestFiles.length>0){
                if(requestFiles.length>=currentIndex){
                    requestFileName = requestFiles[currentIndex];
                }else{
                    requestFileName = requestFiles[0];
                }
            }
        }
        String requestFileFullName = rootPath;
        if(!requestFileFullName.endsWith(File.separator)){
            requestFileFullName += File.separator;
        }
        requestFileFullName += requestFileName;

        return new FileInputStream(requestFileFullName);
    }

    public void writeResponse(ISessionContext context) throws EndPointException {
        String fileName = rootPath;
        if(!fileName.endsWith(File.separator)){
            fileName += File.separator;
        }
        fileName += context.getSessionID();
        if(context.getLastErrorCode() != TR069Constants.ERROR_CODE_SUCCESS){
            fileName += "_error_";
            fileName += Integer.toString(context.getLastErrorCode());
        }
        fileName += ".out";
        try {
            writeFile(fileName, context.getResponse());
        } catch (IOException e) {
            throw new EndPointException("write file content error",e);
        }
        cleanup();
    }

    private void loadProperties(){
        String fileName = rootPath;
        if(!fileName.endsWith(File.separator)){
            fileName += File.separator;
        }
        fileName += propertiesFileName;
        try{
            InputStream in = new BufferedInputStream(new FileInputStream(fileName));
            properties.load(in);
        }catch(Exception exp){
            exp.printStackTrace();
        }
    }

    private void storeProperties(){
        int currentIndex = 0;
        try{
            currentIndex = Integer.parseInt((String)properties.get(KEY_CURRENTREQUESTFILEINDEX));
        }catch(Exception exp){
            log.error("Get current request file index error",exp);
        }
        currentIndex = currentIndex + 1;
        properties.setProperty(KEY_CURRENTREQUESTFILEINDEX,Integer.toString(currentIndex));

        String fileName = rootPath;
        if(!fileName.endsWith(File.separator)){
            fileName += File.separator;
        }
        fileName += propertiesFileName;
        try {
            properties.store(new FileWriter(fileName),null);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void writeFile(String fileName, String fileContent) throws IOException{
        FileWriter fw = new FileWriter(fileName);
        if(fileContent!=null){
            fw.write(fileContent);
        }
        fw.flush();
        fw.close();
    }

    private void init(){
        loadProperties();
    }

    private void cleanup(){
        storeProperties();
    }

}

