package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.soap.SOAPEnvelope;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:46
 */
public abstract class TR069Message implements Serializable {
    private transient SOAPEnvelope envelope=null;

    public TR069Message(SOAPEnvelope envelope) {
        this.envelope = envelope;
    }
    @JsonIgnore
    public SOAPEnvelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(SOAPEnvelope envelope) {
        this.envelope = envelope;
    }

    public abstract String getMessageName();
}

