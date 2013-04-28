package org.slstudio.acs.tr069.endpoint.stream;

import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.exception.EndPointException;
import org.slstudio.acs.kernal.session.context.IMessageContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-29
 * Time: ÉÏÎç1:27
 */
public class StreamEndPoint implements IProtocolEndPoint {
    private InputStream propertiesIS = null;
    private InputStream requestIS = null;
    private OutputStream responseOS = null;
    private Properties properties = new Properties();

    public StreamEndPoint(InputStream propertiesIS, InputStream requestIS, OutputStream responseOS) {
        this.propertiesIS = propertiesIS;
        this.requestIS = requestIS;
        this.responseOS = responseOS;
        init();
    }

    private void init() {
        try {
            properties.load(propertiesIS);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public void saveProperty(String name, String value) {
        properties.setProperty(name,value);
    }

    public InputStream getInputStream() throws IOException {
        return requestIS;
    }

    public void writeResponse(IMessageContext context) throws EndPointException {
        PrintWriter pw = new PrintWriter(responseOS);
        pw.write(Integer.toString(context.getLastErrorCode()));
        pw.write("\r\n");
        if(context.getResponse()!=null){
            pw.write(context.getResponse());
        }
        pw.flush();
        pw.close();
    }
}
