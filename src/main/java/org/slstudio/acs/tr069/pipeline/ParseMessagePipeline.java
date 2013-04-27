package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.exception.ParseMessageException;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPEnvelopeParser;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.xml.MultipleXMLStreamException;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç3:37
 */
public class ParseMessagePipeline extends AbstractTR069Pipeline {
    private static final Log log = LogFactory.getLog(ParseMessagePipeline.class);

    @Override
    protected void process(ITR069MessageContext context) throws TR069Exception {
        InputStream is=(InputStream)context.getInputStream();
        if(is==null){
            throw new ParseMessageException("Message inputstream can't be null");
        }
        List<SOAPMessage> messages=new ArrayList<SOAPMessage>();
        try {
            List<SOAPEnvelope> envelopes=new SOAPEnvelopeParser().parseEnvelope(new InputStreamReader(is));
            if(log.isDebugEnabled()){
                log.debug("Receive SOAP Request:-------------");
            }
            for(SOAPEnvelope envelope: envelopes){
                if(log.isDebugEnabled()){
                    log.debug(envelope.toString());
                }
                messages.add(new SOAPMessage(envelope));
            }
        } catch (MultipleXMLStreamException e) {
            throw new ParseMessageException("Parse inputstream to multiple xml stream failed",e);
        } catch (XMLStreamException e) {
            throw new ParseMessageException("Parse xml stream failed",e);
        } catch (Exception e){
            throw new ParseMessageException("Parse message failed",e);
        }
        context.setSoapMessageList(messages);
    }
}
