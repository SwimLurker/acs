package org.slstudio.acs.tr069.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç4:57
 */
public class MultipleXMLParser {
    private static final Log log = LogFactory.getLog(MultipleXMLParser.class);

    private MultipleXMLReader reader=null;

    public MultipleXMLParser() {
        reader=new MultipleXMLReader();
        reader.setConfigurationContext(new ConfigurationContextBase());
    }

    public List<Reader> parse(Reader source) throws MultipleXMLStreamException {

        ((MultipleXMLReader)reader).setInput(source);
        List<Reader> result=new ArrayList<Reader>();
        //StringBuffer currentXML=null;
        while(true){
            int event=reader.next();
            //DebugUtil.debug(event);
            if(event== IMultipleXMLStreamConstants.END_MULTIPLE_DOCUMENT){
                return result;
            }else if(event==IMultipleXMLStreamConstants.START_MULTIPLE_DOCUMENT){
                result=new ArrayList<Reader>();
            }else if(event==IMultipleXMLStreamConstants.START_SINGLE_DOCUMENT){
                //reader.recycle();
                //result.add(new StringReader(reader.getCurrentXML()));
            }else if(event==IMultipleXMLStreamConstants.END_SINGLE_DOCUMENT){
                result.add(new StringReader(reader.getCurrentXML()));
                //reader.recycle();
            }
        }
    }

    public static void main(String[] args) throws MultipleXMLStreamException, IOException {
        MultipleXMLParser parser=new MultipleXMLParser();
        List<Reader> result=parser.parse(new FileReader("d:\\1.xml"));
        for(Reader reader:result){
           StringReader r=(StringReader)reader;
            char[] buf=new char[1024];
            int readnum=0;
            StringBuffer sb=new StringBuffer();
            while((readnum=r.read(buf,0,1024))!=-1){
                sb.append(buf,0,readnum);
            }
            log.debug(sb.toString());

        }

//        Vector result2=parser.parse(new FileReader("d:\\project\\testAxis2\\testsoap2.xml"));
//        for(int i=0;i<result2.size();i++){
//            DebugUtil.debug("XML2 "+i+":-----------------------");
//            StringReader r=(StringReader)result2.get(i);
//            char[] buf=new char[1024];
//            int readnum=0;
//            StringBuffer sb=new StringBuffer();
//            while((readnum=r.read(buf,0,1024))!=-1){
//                sb.append(buf,0,readnum);
//            }
//            DebugUtil.debug(sb.toString());
//        }
    }
}
