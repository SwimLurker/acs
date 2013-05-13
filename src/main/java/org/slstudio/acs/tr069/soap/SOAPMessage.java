package org.slstudio.acs.tr069.soap;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.fault.TR069Fault;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÏÂÎç5:38
 */
public class SOAPMessage implements java.io.Serializable{
    //TODO:maybe we should refact response to a vector of SOAPEnvelope,now we use a vector of String

    private SOAPEnvelope envelope=null;
    private List<TR069Message> responses=null;
    private TR069Fault fault = null;
    private boolean isDealed=false;


    public SOAPMessage(SOAPEnvelope envelope) {
        this.envelope = envelope;
        this.responses = new ArrayList<TR069Message>();
        this.isDealed = false;
    }

    public SOAPEnvelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(SOAPEnvelope envelope) {
        this.envelope = envelope;
    }

    public List<TR069Message> getResponses() {
        return responses;
    }

    public void addResponse(TR069Message response) {
        this.responses.add(response);
    }
    public void setResponses(List<TR069Message> responses){
        this.responses=responses;
    }

    public TR069Fault getFault() {
        return fault;
    }

    public void setFault(TR069Fault fault) {
        this.fault = fault;
    }

    public boolean isDealed() {
        return isDealed;
    }

    public void setDealed(boolean dealed) {
        isDealed = dealed;
    }
}

