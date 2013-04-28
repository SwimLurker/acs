package org.slstudio.acs.tr069.pipeline;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
import org.slstudio.acs.kernal.exception.PipelineException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.ValidateMessageException;
import org.slstudio.acs.tr069.session.context.ITR069MessageContext;
import org.slstudio.acs.tr069.soap.SOAPMessage;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç11:36
 */
public class ValidateMessagePipeline extends AbstractTR069Pipeline {
    @Override
    protected void process(ITR069MessageContext context) throws PipelineException {
        List<SOAPMessage> messages = context.getSoapMessageList();
        if(messages==null){
            throw new ValidateMessageException("Message can't be null");
        }
        for(int i=0;i<messages.size();i++){
            SOAPMessage message=(SOAPMessage)messages.get(i);
            validate(message.getEnvelope());
        }
    }

    private void validate(SOAPEnvelope envelope) throws ValidateMessageException{
        if(envelope==null){
            //empty message
            return;
        }
        //DebugUtil.debug("soap namespace:"+envelope.getNamespace().getName());

        if(!TR069Constants.SOAP_NAMESPACE.equals(envelope.getNamespace().getName())){
            throw new ValidateMessageException("Message format invalid:soap namespace error");
        }
        //check encoding style attribute
        String encodingStyle=envelope.getAttributeValue(new QName(TR069Constants.SOAP_NAMESPACE,"encodingStyle"));
        if(encodingStyle==null||!encodingStyle.equals(TR069Constants.SOAP_ENCODING)){
            throw new ValidateMessageException("Message format invalid:envelope attribute:encodingStyle error");
        }
        //check cwmp namespace
        SOAPBody body=envelope.getBody();
        if(body==null){
            throw new ValidateMessageException("Message format invalid:empty envelop");
        }
        SOAPFault fault=body.getFault();
        if(fault==null){
            //normal request or response
            OMElement fE=body.getFirstElement();
            if(fE==null){
                throw new ValidateMessageException("Message format invalid:empty envelop");
            }
            if(fE.getNamespace()==null||!TR069Constants.TR069_NAMESPACE.equals(fE.getNamespace().getName())){
                throw new ValidateMessageException("Message format invalid:tr069 namespace error");
            }
        }else{
            //soap fault
            //if(fault.getCode().getT)
            OMElement fE=fault.getDetail().getFirstElement();
            if(fE==null){
                throw new ValidateMessageException("Message format invalid:empty soap fault detail");
            }
            if(fE.getNamespace()==null||!TR069Constants.TR069_NAMESPACE.equals(fE.getNamespace().getName())){
                throw new ValidateMessageException("Message format invalid:tr069 namespace error");
            }
        }


    }
}
