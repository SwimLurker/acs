package org.slstudio.acs.tr069.job;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç12:42
 */
public class DeviceJobConstants {

    public static final int ERRORCODE_NOERROR = 0;
    public static final int ERRORCODE_UNKNOWNERROR = -1;
    public static final int ERRORCODE_MESSAGECHECKFAILED = -2;
    public static final int ERRORCODE_MESSAGEIDERROR = -3;
    public static final int ERRORCODE_WAITINGTIMEOUT = -4;
    public static final int ERRORCODE_RUNNINGTIMEOUT = -5;
    public static final int ERRORCODE_INVALIDDEVICE = -6;
    public static final int ERRORCODE_PARSEJOBSCRIPT = -7;
    public static final int ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION = -8;

    public static final int STATUS_READY = 0;
    public static final int STATUS_RUNNING = 1;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_FAILED = 3;
}
