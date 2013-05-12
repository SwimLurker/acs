package org.slstudio.acs.kernal;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-27
 * Time: ÉÏÎç2:56
 */
public class ACSConstants {
    public static final String MESSAGECONTEXT_KEY_INPUTSTREAM = "MessageContext_Key_InputStream" ;
    public static final String MESSAGECONTEXT_KEY_RESPONSE = "MessageContext_Key_Response" ;
    public static final String MESSAGECONTEXT_KEY_ERRORCODE = "MessageContext_Key_ErrorCode" ;
    public static final String SESSIONCONTEXT_KEY_CLIENTID = "SessionContext_Key_ClientID";
    public static final String SESSIONCONTEXT_KEY_SESSIONID = "SessionContext_Key_SessionID";
    public static final int ERROR_CODE_SUCCESS = 0;
    public static final int ERROR_CODE_UNKNOWNERROR = -1;
    public static final int ERROR_CODE_UNSUPPORTPROTOCOL = -2;
    public static final int ERROR_CODE_PIPELINEHANDLE = -3;
    public static final int ERROR_CODE_WRITERESPONSE = -4;

    public static final int SESSION_STATUS_CREATED = 0;
    public static final int SESSION_STATUS_CHECKED = 1;
    public static final int SESSION_STATUS_TIMEOUT = 2;
    public static final int SESSION_STATUS_CLOSED = 3;

}
