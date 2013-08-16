package org.slstudio.acs.tr069.databinding;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.DataBindingException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ����2:56
 */
public class DeviceIdStruct implements Serializable {
    private String manufacturer ;
    private String OUI ;
    private String productClass ;
    private String serialNumber ;
    private String softwareVersion;

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
    public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
    public static DeviceIdStruct fromOMElement(OMElement element) throws DataBindingException {
        DeviceIdStruct idStruct = new DeviceIdStruct();

        Iterator mIt=element.getChildrenWithName(new QName("Manufacturer"));
        if(mIt==null||!mIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"Manufacturer is null");
        }
        idStruct.setManufacturer(((OMElement)mIt.next()).getText());

        Iterator oIt=element.getChildrenWithName(new QName("OUI"));
        if(oIt==null||!oIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"OUI is null");
        }
        idStruct.setOUI(((OMElement)oIt.next()).getText());

        Iterator pIt=element.getChildrenWithName(new QName("ProductClass"));
        if(pIt==null||!pIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"ProductClass is null");
        }
        idStruct.setProductClass(((OMElement)pIt.next()).getText());

        Iterator sIt=element.getChildrenWithName(new QName("SerialNumber"));
        if(sIt==null||!sIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"SerialNumber is null");
        }
        idStruct.setSerialNumber(((OMElement)sIt.next()).getText());
        
        Iterator svIt=element.getChildrenWithName(new QName("SoftwareVersion"));
        if(svIt==null||!svIt.hasNext()){
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,"SoftwareVersion is null");
        }
        idStruct.setSoftwareVersion(((OMElement)svIt.next()).getText());
        


        return idStruct;
    }



	public static DeviceIdStruct fromXMLStreamReader(XMLStreamReader reader) throws DataBindingException{
        DeviceIdStruct object = new DeviceIdStruct();
        try {
            int event = reader.getEventType();
            int count = 0;
            int argumentCount = 4 ;
            boolean done =false;
            //event better be a START_ELEMENT. if not we should go up to the start element here
            while (!reader.isStartElement()){
                event = reader.next();
            }
            while(!done){
                if (javax.xml.stream.XMLStreamConstants.START_ELEMENT==event){
                    if ("Manufacturer".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setManufacturer(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("OUI".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setOUI(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("ProductClass".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setProductClass(ConverterUtil.convertToString(content));
                        count++;
                    }
                    if ("SerialNumber".equals(reader.getLocalName())){
                        String content = reader.getElementText();
                        object.setSerialNumber(ConverterUtil.convertToString(content));
                        count++;
                    }
                }

                if (argumentCount==count){
                    done=true;
                }

                if (!done){
                    event = reader.next();
                }

            }

        } catch (javax.xml.stream.XMLStreamException e) {
            throw new DataBindingException(TR069Constants.ERROR_DATA_BINDING,e);
        }

        return object;
    }

}

