package org.slstudio.acs.tr069.databinding.request;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç12:12
 */
public class UploadRequest extends TR069Message {
    private String commandKey = null;
    private String fileType = null;
    private String URL = null;
    private String username = null;
    private String password = null;
    private UnsignedInt delaySeconds = null;

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UnsignedInt getDelaySeconds() {
        return delaySeconds;
    }

    public void setDelaySeconds(UnsignedInt delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_UPLOAD_MESSAGE;
    }

    @Override
    protected String toTR069SOAPString() {
        StringBuilder result = new StringBuilder();
        result.append("<").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        result.append("<CommandKey>").append(commandKey==null?"":commandKey).append("</CommandKey>");
        result.append("<FileType>").append(fileType==null?"":fileType).append("</FileType>");
        result.append("<URL>").append(URL==null?"":URL).append("</URL>");
        result.append("<Username>").append(username==null?"":username).append("</Username>");
        result.append("<Password>").append(password==null?"":password).append("</Password>");
        result.append("<DelaySeconds>").append(delaySeconds==null?"0":ConverterUtil.convertToString(delaySeconds)).append("</DelaySeconds>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

}