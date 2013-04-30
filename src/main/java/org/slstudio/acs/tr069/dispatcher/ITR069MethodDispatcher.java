package org.slstudio.acs.tr069.dispatcher;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.messagedealer.ITR069MethodDealer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-30
 * Time: обнГ12:16
 */
public interface ITR069MethodDispatcher {
    public ITR069MethodDealer findMethodDealer(SOAPEnvelope envelope);
}
