package org.slstudio.acs.tr069.soap;

import org.apache.axiom.om.impl.builder.StAXBuilder;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.impl.builder.StAXSOAPModelBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.xml.MultipleXMLParser;
import org.slstudio.acs.tr069.xml.MultipleXMLStreamException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç4:31
 */
public class SOAPEnvelopeParser {
    private static final Log log = LogFactory.getLog(SOAPEnvelopeParser.class);
    private MultipleXMLParser parser = null;

    public SOAPEnvelopeParser() {
        parser=new MultipleXMLParser();
    }

    public List<SOAPEnvelope> parseEnvelope(Reader reader) throws MultipleXMLStreamException, XMLStreamException {
        List<SOAPEnvelope> result=new ArrayList<SOAPEnvelope>();
        List<Reader> readers=parser.parse(reader);
        for(int i=0;i<readers.size();i++){
            Reader s=(Reader)readers.get(i);
            XMLStreamReader xmlreader= XMLInputFactory.newInstance().createXMLStreamReader(s);

            StAXBuilder builder =new StAXSOAPModelBuilder(
                    xmlreader,
                    SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
            SOAPEnvelope envelope=(SOAPEnvelope)builder.getDocumentElement();
            result.add(envelope);
        }
        return result;
    }

    public static void main(String[] args) throws MultipleXMLStreamException, XMLStreamException, FileNotFoundException {
        List<SOAPEnvelope> envelopes=new SOAPEnvelopeParser().parseEnvelope(new FileReader("d:\\project\\testAxis2\\testsoap.xml"));
        for(SOAPEnvelope envelope: envelopes){
            log.debug(envelope.getBody().toString());
        }
    }
}
