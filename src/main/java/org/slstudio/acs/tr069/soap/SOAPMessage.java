package org.slstudio.acs.tr069.soap;

import org.apache.axiom.soap.SOAPEnvelope;

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
    private List<String> responses=null;
    private boolean isDealed=false;


    public SOAPMessage(SOAPEnvelope envelope) {
        this.envelope = envelope;
        this.responses = new ArrayList<String>();
        this.isDealed = false;
    }

    public SOAPEnvelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(SOAPEnvelope envelope) {
        this.envelope = envelope;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void addResponse(String responseString) {
        this.responses.add(responseString);
    }
    public void setResponses(List<String> responses){
        this.responses=responses;
    }

    public boolean isDealed() {
        return isDealed;
    }

    public void setDealed(boolean dealed) {
        isDealed = dealed;
    }
}

