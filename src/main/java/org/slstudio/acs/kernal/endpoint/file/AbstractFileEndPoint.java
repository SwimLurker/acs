package org.slstudio.acs.kernal.endpoint.file;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.EndPointException;
import org.slstudio.acs.kernal.session.context.IMessageContext;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç10:34
 */
public abstract class AbstractFileEndPoint implements IProtocolEndPoint {
    private static final Log log = LogFactory.getLog(AbstractFileEndPoint.class);

    private File inputDir = null;
    private File outputDir = null;
    private File propertiesFile = null;
    private Properties properties = new Properties();

    public AbstractFileEndPoint(File inputDir, File outputDir, File propertiesFile){
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.propertiesFile = propertiesFile;
        initEndPoint();
    }

    public File getInputDir() {
        return inputDir;
    }

    public void setInputDir(File inputDir) {
        this.inputDir = inputDir;
    }

    public File getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public File getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public String getProperty(String name){
        return properties.getProperty(name);
    }

    public void saveProperty(String name, String value){
        properties.setProperty(name,value);
    }

    public final InputStream getInputStream() throws IOException {
        String requestFileName = getRequestFileName();
        return new FileInputStream(new File(inputDir,requestFileName));
    }

    public final void writeResponse(IMessageContext context) throws EndPointException {
        beforeWriteResponse(context);
        String fileName = getResponseFileName(context);
        try {
            writeFile(fileName, context.getResponse());
        } catch (IOException e) {
            throw new EndPointException("write file content error",e);
        }
        afterWriteResponse(context);
        finishEndPoint();
    }

    protected void initEndPoint(){
        loadProperties();
    }

    protected void beforeWriteResponse(IMessageContext context){
    }

    protected void afterWriteResponse(IMessageContext context){
    }

    protected void finishEndPoint(){
        storeProperties();
    }

    protected abstract String getRequestFileName() ;

    protected abstract String getResponseFileName(IMessageContext context) ;

    private void loadProperties(){
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream(propertiesFile));
            properties.load(in);
        }catch(Exception exp){
            exp.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void storeProperties(){
        FileWriter fw = null;
        try {
            fw = new FileWriter(propertiesFile);
            properties.store(fw,null);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void writeFile(String fileName, String fileContent) throws IOException{
        FileWriter fw = new FileWriter(new File(outputDir,fileName));
        if(fileContent!=null){
            fw.write(fileContent);
        }
        fw.flush();
        fw.close();
    }

}

