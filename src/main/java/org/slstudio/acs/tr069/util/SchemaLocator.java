package org.slstudio.acs.tr069.util;

import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-3
 * Time: ÏÂÎç12:16
 */
public class SchemaLocator {
    private static final Log log = LogFactory.getLog(SchemaLocator.class);

    private Map<String, Schema> schemaMap = null;

    public SchemaLocator(){
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        factory.setErrorHandler(new MySchemaErrorHandler());
        schemaMap = Collections.synchronizedMap(new HashMap<String, Schema>());
        Schema am1Schema = null;
        try {
            am1Schema = factory.newSchema(this.getClass().getResource("/schema/"+ TR069Constants.SCHEMA_TR069_AM1+".xsd"));
            schemaMap.put(TR069Constants.SCHEMA_TR069_AM1, am1Schema);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Schema am2Schema = null;
        try {
            am2Schema = factory.newSchema(this.getClass().getResource("/schema/"+ TR069Constants.SCHEMA_TR069_AM2+".xsd"));
            schemaMap.put(TR069Constants.SCHEMA_TR069_AM2, am2Schema);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Schema am3Schema = null;
        try {
            am3Schema = factory.newSchema(this.getClass().getResource("/schema/"+ TR069Constants.SCHEMA_TR069_AM3+".xsd"));
            schemaMap.put(TR069Constants.SCHEMA_TR069_AM3, am3Schema);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Schema am4Schema = null;
        try {
            am4Schema = factory.newSchema(this.getClass().getResource("/schema/"+ TR069Constants.SCHEMA_TR069_AM4+".xsd"));
            schemaMap.put(TR069Constants.SCHEMA_TR069_AM4, am4Schema);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Schema findSchema(String schemaName){
        return schemaMap.get(schemaName);
    }

    public void test() throws Exception{
        Schema schema = schemaMap.get(TR069Constants.SCHEMA_TR069_AM2);
        Validator validator = schema.newValidator();
        validator.setErrorHandler(new MySchemaErrorHandler());
        validator.validate(new StreamSource(new FileInputStream(new File("D:\\workspace\\acs\\src\\test\\resources\\file_endpoint\\input\\TransferComplete.xml"))));
    }

    //implements error handler to avoid facet-valid error for soap "mustUnderStand" attribute
    class MySchemaErrorHandler implements ErrorHandler {
        public void warning(SAXParseException exception) throws SAXException {
            log.warn(exception);
            System.out.println(exception.getMessage());
        }

        public void error(SAXParseException exception) throws SAXException {
            log.error(exception);
            System.out.println(exception.getMessage());
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }
    }


    public static void main(String[] args) throws Exception {
        new SchemaLocator().test();
    }
}
