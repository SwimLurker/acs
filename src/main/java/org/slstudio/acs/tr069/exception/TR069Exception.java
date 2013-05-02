package org.slstudio.acs.tr069.exception;

import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.fault.FaultUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:53
 */
public class TR069Exception extends ACSException {
    private int errorCode= TR069Constants.SUCCESS;
    private String errorMsg=null;

    public TR069Exception(int errorCode) {
        this(errorCode, FaultUtil.findErrorMessage(errorCode));
    }

    public TR069Exception(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public TR069Exception(int errorCode,Throwable cause) {
        this(errorCode,cause.getMessage(),cause);
    }

    public TR069Exception(int errorCode,String message,Throwable cause) {
        super(message,cause);
        this.errorCode=errorCode;
        this.errorMsg=message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
