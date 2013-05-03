package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.camel.converter.jaxp.StringSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.exception.ValidateMessageException;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;
import org.slstudio.acs.tr069.util.SchemaLocator;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-3
 * Time: ÉÏÎç12:43
 */
public class ValidateMessageSchemaPipeline extends AbstractTR069Pipeline {
    private static final Log log = LogFactory.getLog(ValidateMessageSchemaPipeline.class);

    private SchemaLocator schemaLocator = null;

    public ValidateMessageSchemaPipeline(SchemaLocator schemaLocator) {
        this.schemaLocator = schemaLocator;
    }

    public SchemaLocator getSchemaLocator() {
        return schemaLocator;
    }

    public void setSchemaLocator(SchemaLocator schemaLocator) {
        this.schemaLocator = schemaLocator;
    }

    @Override
    protected void process(ITR069MessageContext context) throws PipelineException {
        String schemaName = context.getTR069SessionContext().getTR069Engine().getTr069Spec().getSchemaName();
        List<SOAPMessage> messages = context.getSoapMessageList();
        if(messages==null){
            throw new ValidateMessageException("Message can't be null");
        }
        for(int i=0;i<messages.size();i++){
            SOAPMessage message=(SOAPMessage)messages.get(i);
            validate(message.getEnvelope(),schemaName);
        }
    }

    private void validate(SOAPEnvelope envelope, String schemaName) throws ValidateMessageException{
        try {
            Schema schema = schemaLocator.findSchema(schemaName);
            Validator validator = schema.newValidator();
            validator.validate(new StringSource(envelope.toString()));
        } catch (Exception ex) {
            throw new ValidateMessageException("validate soap message with schema:" + schemaName + " failed", ex);
        }

    }





}
//implements this to avoid the facet-valid error of tr069 schema file for "mustUnderstand" attribute

