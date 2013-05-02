package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.soap.SOAPEnvelope;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ2:46
 */
public class TR069Message implements Serializable {
    private transient SOAPEnvelope envelope=null;

    public TR069Message(SOAPEnvelope envelope) {
        this.envelope = envelope;
    }

    public SOAPEnvelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(SOAPEnvelope envelope) {
        this.envelope = envelope;
    }
}

