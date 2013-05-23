package org.slstudio.acs.tr069.databinding.request;

import org.apache.axis2.databinding.types.UnsignedInt;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.databinding.TR069Message;
import org.slstudio.acs.util.JSONUtil;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-13
 * Time: ÏÂÎç11:29
 */
public class DownloadRequest extends TR069Message {
    private String commandKey = null;
    private String fileType = null;
    private String URL = null;
    private String username = null;
    private String password = null;
    private UnsignedInt fileSize = null;
    private String targetFileName = null;
    private UnsignedInt delaySeconds = null;
    private String successURL = null;
    private String failureURL = null;

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

    public UnsignedInt getFileSize() {
        return fileSize;
    }

    public void setFileSize(UnsignedInt fileSize) {
        this.fileSize = fileSize;
    }

    public String getTargetFileName() {
        return targetFileName;
    }

    public void setTargetFileName(String targetFileName) {
        this.targetFileName = targetFileName;
    }

    public UnsignedInt getDelaySeconds() {
        return delaySeconds;
    }

    public void setDelaySeconds(UnsignedInt delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    public String getSuccessURL() {
        return successURL;
    }

    public void setSuccessURL(String successURL) {
        this.successURL = successURL;
    }

    public String getFailureURL() {
        return failureURL;
    }

    public void setFailureURL(String failureURL) {
        this.failureURL = failureURL;
    }

    @Override
    public String getMessageName() {
        return TR069Constants.CLIENT_DOWNLOAD_MESSAGE;
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
        result.append("<FileSize>").append(fileSize==null?"0":ConverterUtil.convertToString(fileSize)).append("</FileSize>");
        result.append("<TargetFileName>").append(targetFileName==null?"":targetFileName).append("</TargetFileName>");
        result.append("<DelaySeconds>").append(delaySeconds==null?"0":ConverterUtil.convertToString(delaySeconds)).append("</DelaySeconds>");
        result.append("<SuccessURL>").append(successURL==null?"":successURL).append("</SuccessURL>");
        result.append("<FailureURL>").append(failureURL==null?"":failureURL).append("</FailureURL>");
        result.append("</").append(TR069Constants.NAMESPACE_CWMP).append(":").append(getMessageName()).append(">");
        return result.toString();
    }

    public static void main(String[] args) {
        DownloadRequest dr = new DownloadRequest();
        dr.setURL("http://localhost/fileupload/firmware.bin");
        dr.setUsername("testuser");
        dr.setPassword("testpass");
        dr.setFileType("1 Firmware Upgrade Image");
        dr.setDelaySeconds(new UnsignedInt(0));
        dr.setFileSize(new UnsignedInt(1024));
        dr.setTargetFileName("firmware.bin");
        System.out.println(JSONUtil.toJsonString(dr));
        System.out.println(dr.toSOAPString());
    }


}
