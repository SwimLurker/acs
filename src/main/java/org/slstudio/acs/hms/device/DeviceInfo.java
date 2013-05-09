package org.slstudio.acs.hms.device;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slstudio.acs.util.CustomDateDeserializer;
import org.slstudio.acs.util.CustomDateSerializer;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-8
 * Time: ÉÏÎç2:03
 */

@JsonAutoDetect()
public class DeviceInfo {
    private String deviceID = null;
    private String deviceKey = null;
    private String authUsername = null;
    private String authPassword = null;
    private String deviceIP = null;
    private String manufacturer = null;
    private String deviceOUI=null;
    private String productClass = null;
    private String serialNumber = null;
    private String crURL = null;
    private String crUsername = null;
    private String crPassword = null;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date lastInformTime = null;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getAuthUsername() {
        return authUsername;
    }

    public void setAuthUsername(String authUsername) {
        this.authUsername = authUsername;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDeviceOUI() {
        return deviceOUI;
    }

    public void setDeviceOUI(String deviceOUI) {
        this.deviceOUI = deviceOUI;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCrURL() {
        return crURL;
    }

    public void setCrURL(String crURL) {
        this.crURL = crURL;
    }

    public String getCrUsername() {
        return crUsername;
    }

    public void setCrUsername(String crUsername) {
        this.crUsername = crUsername;
    }

    public String getCrPassword() {
        return crPassword;
    }

    public void setCrPassword(String crPassword) {
        this.crPassword = crPassword;
    }

    public Date getLastInformTime() {
        return lastInformTime;
    }

    public void setLastInformTime(Date lastInformTime) {
        this.lastInformTime = lastInformTime;
    }

    public static void main(String[] args) throws IOException {
        DeviceInfo device = new DeviceInfo();
        device.setDeviceID("0000000001");
        device.setDeviceKey("FA1234567890");
        device.setDeviceIP("192.168.0.100");
        device.setManufacturer("FishCore");
        device.setDeviceOUI("00A00D");
        device.setProductClass("FishCore IGD Device");
        device.setSerialNumber("FA1234567890");
        device.setCrURL("http://192.168.0.100:9892/acscall");
        device.setCrUsername("test");
        device.setCrPassword("test");
        device.setLastInformTime(new Date());

        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        String jsonStr =  mapper.writeValueAsString(device);
        System.out.println(jsonStr);

    }

}
