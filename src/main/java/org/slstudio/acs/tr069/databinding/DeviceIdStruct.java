package org.slstudio.acs.tr069.databinding;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:56
 */
public class DeviceIdStruct implements Serializable {
    private String manufacturer ;
    private String OUI ;
    private String productClass ;
    private String serialNumber ;

    public DeviceIdStruct() {
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOUI() {
        return OUI;
    }

    public void setOUI(String OUI) {
        this.OUI = OUI;
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

}

