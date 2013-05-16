package org.slstudio.acs.hms.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.ACSServer;
import org.slstudio.acs.DefaultACSServerLifecycleListener;
import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.bean.DeviceJobBean;
import org.slstudio.acs.hms.messaging.bean.SyncDevicesBean;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.slstudio.acs.kernal.exception.LifecycleException;
import org.slstudio.acs.util.ActiveMQStarter;
import org.slstudio.acs.util.BeanLocator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÏÂÎç3:10
 */
public class MessagingServiceServerListener extends DefaultACSServerLifecycleListener {
    private static final Log log = LogFactory.getLog(MessagingServiceServerListener.class);


    @Override
    public void onBeforeStartServer(ACSServer server)  throws LifecycleException {
        startActiveMQ();
    }

    @Override
    public void onStartServer(ACSServer server)  throws LifecycleException{
        DefaultMessageListenerContainer dmlc = (DefaultMessageListenerContainer) BeanLocator.getBean("syncDeviceListenerContainer");
        dmlc.start();
        DefaultMessageListenerContainer dmlc2 = (DefaultMessageListenerContainer) BeanLocator.getBean("deviceJobListenerContainer");
        dmlc2.start();
    }

    @Override
    public void onAfterStartServer(ACSServer server)  throws LifecycleException{
        sendMockSyncDevicesStringMessage();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        sendMockDeviceJobMessage();
    }

    @Override
    public void onStopServer(ACSServer server) throws LifecycleException {
        DefaultMessageListenerContainer dmlc = (DefaultMessageListenerContainer) BeanLocator.getBean("syncDeviceListenerContainer");
        dmlc.stop();
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
        IMessageSender sender = (IMessageSender) BeanLocator.getBean("syncDevicesSender");
        try {
            sender.sendMessage(new SyncDevicesBean(SyncDevicesBean.COMMAND_SYNC, deviceList));
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void sendMockDeviceJobMessage() {
        DeviceJobBean djb  = new DeviceJobBean();
        djb.setJobID("1");
        djb.setDeviceKey("FC1234567890");
        djb.setJobName("test job");
        StringBuilder text = new StringBuilder();
        text.append("SET $a = \"bbb\"").append("\r\n").append("SET $c = $a").append("\r\n").
                append("tr069 cmd getpv:{\"parameterNames\":[\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\"]}").append("\r\n").append("RET");
        djb.setJobScript(text.toString());
        IMessageSender sender = (IMessageSender) BeanLocator.getBean("deviceJobSender");
        try {
            sender.sendMessage(djb);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DeviceJobBean djb2  = new DeviceJobBean();
        djb2.setJobID("2");
        djb2.setDeviceKey("FC1234567890");
        djb2.setJobName("test job2");
        StringBuilder text2 = new StringBuilder();
        text2.append("SET $a = \"bbb\"").append("\r\n").append("SET $c = $a").append("\r\n").
                append("tr069 cmd getpv:{\"parameterNames\":[\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\"]}").append("\r\n").append("RET");
        djb2.setJobScript(text2.toString());

        try {
            sender.sendMessage(djb2);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void sendMockSyncDevicesStringMessage() {
       String msg = "{\"command\":0,\"deviceList\":[{\"deviceID\":\"0000000000\",\"deviceKey\":\"FC1234567890\",\"authUsername\":\"admin\",\"authPassword\":\"admin\"},{\"deviceID\":\"0000000001\",\"deviceKey\":\"FF00000001\",\"authUsername\":\"admin\",\"authPassword\":\"admin\"}]}";
        IMessageSender sender = (IMessageSender) BeanLocator.getBean("syncDevicesSender");
        try {
            sender.sendMessage(msg);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void startActiveMQ() throws LifecycleException{
        try{
            ActiveMQStarter starter = (ActiveMQStarter)BeanLocator.getBean("activeMQ");
            starter.start();
        }catch(Exception exp){
            throw new LifecycleException("start active mq error", exp);
        }
    }


}
