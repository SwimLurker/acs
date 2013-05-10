package org.slstudio.acs;

import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.bean.SyncDevicesBean;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.slstudio.acs.util.BeanLocator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:18
 */
public class ACSServer {
    private static ACSServer _instance = null;
    private boolean bRunning = false;

    protected ACSServer(){
    }

    public static ACSServer getInstance(){
        if(_instance == null){
            _instance = new ACSServer();
        }
        return _instance;
    }

    public boolean isRunning() {
        return bRunning;
    }

    public void setAcsConfigFile(String acsConfigfile) {

    }

    public boolean start() {
        DefaultMessageListenerContainer dmlc = (DefaultMessageListenerContainer)BeanLocator.getBean("syncDeviceListenerContainer");
       dmlc.start();
        bRunning = true;
        sendMockSyncDevicesMessage();
        return bRunning;
    }

    private void sendMockSyncDevicesMessage() {
        List<DeviceInfo> deviceList = new ArrayList<DeviceInfo>();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);

        for(int i=0;i<100;i++){
            DeviceInfo device = new DeviceInfo();

            device.setDeviceID("0000000"+ nf.format(i));
            device.setDeviceKey("FF00000" + nf.format(i));
            device.setAuthUsername("admin");
            device.setAuthPassword("admin");
            device.setDeviceIP("192.168.0.100");
            device.setManufacturer("FishCore");
            device.setDeviceOUI("00A00D");
            device.setProductClass("FishCore IGD Device");
            device.setSerialNumber("FF00000" + nf.format(i));
            device.setCrURL("http://192.168.0.100:9892/acscall");
            device.setCrUsername("test");
            device.setCrPassword("test");
            Date date = new Date();
            int rndValue = (int)Math.round(Math.random() * (1000000 - 1) + 1);
            date.setTime(System.currentTimeMillis() + rndValue);
            device.setLastInformTime(date);

            deviceList.add(device);
        }
        IMessageSender sender = (IMessageSender)BeanLocator.getBean("syncDevicesSender");
        try {
            sender.sendMessage(new SyncDevicesBean(SyncDevicesBean.COMMAND_SYNC, deviceList));
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public boolean stop() {
        bRunning = false;
        return true;
    }
}
