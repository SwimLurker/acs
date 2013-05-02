package org.slstudio.acs.tr069.messagedealer.response;

import org.apache.axiom.soap.SOAPEnvelope;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.tr069.databinding.response.DownloadResponse;
import org.slstudio.acs.tr069.exception.TR069Exception;
import org.slstudio.acs.tr069.messagedealer.AbstractResponseDealer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ����9:08
 */
public class DownloadResponseDealer extends AbstractResponseDealer {
    @Override
    protected TR069Message convertToTR069Message(SOAPEnvelope envelope) throws TR069Exception {
        return new DownloadResponse(envelope);
    }
}
