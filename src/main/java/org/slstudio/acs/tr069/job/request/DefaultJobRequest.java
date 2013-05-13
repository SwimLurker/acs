package org.slstudio.acs.tr069.job.request;

import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-6
 * Time: ионГ1:15
 */
public class DefaultJobRequest implements IJobRequest {
    private TR069Message tr069Request = null;

    public DefaultJobRequest(TR069Message tr069Request) {
        this.tr069Request = tr069Request;
    }

    public TR069Message getTr069Request() {
        return tr069Request;
    }

    public void setTr069Request(TR069Message tr069Request) {
        this.tr069Request = tr069Request;
    }
}
