package org.slstudio.acs.tr069.dispatcher;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFault;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.messagedealer.ITR069MethodDealer;
import org.slstudio.acs.tr069.soap.SOAPUtil;
import org.slstudio.acs.util.BeanLocator;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: ÏÂÎç12:56
 */
public class DefaultTR069MethodDispatcher implements ITR069MethodDispatcher {
    public ITR069MethodDealer findMethodDealer(SOAPEnvelope envelope) {
        if(envelope==null){
            return (ITR069MethodDealer)BeanLocator.getBean("EmptyMessageDealer");
        }
        SOAPFault fault=envelope.getBody().getFault();
        if(fault==null){
            String commandName= SOAPUtil.getCommandName(envelope);
            ITR069MethodDealer dealer = null;
            if(commandName != null){
                dealer = (ITR069MethodDealer)BeanLocator.getBean(commandName+"Dealer");
            }
            return dealer;
        }else{
            //fault
            OMElement detailElement=fault.getDetail().getFirstElement();
            Iterator it=detailElement.getChildrenWithName(new QName(TR069Constants.CLIENT_SETPARAMETERVALUES_MESSAGEFAULT));
            if(it==null||!it.hasNext()){
                return (ITR069MethodDealer)BeanLocator.getBean("FaultDealer");
            }else{
                return (ITR069MethodDealer)BeanLocator.getBean("SetParameterValuesFaultDealer");
            }
        }
    }
}
