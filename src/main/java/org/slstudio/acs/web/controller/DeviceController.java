package org.slstudio.acs.web.controller;

import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
import org.slstudio.acs.web.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-8
 * Time: ÉÏÎç3:02
 */
@Controller
@RequestMapping("/devices")
public class DeviceController {
    @Resource(name = "deviceManager")
    private IDeviceManager deviceManager= null;

    @RequestMapping(value="/{deviceKey}", method = RequestMethod.GET)
    public @ResponseBody DeviceInfo getDeviceInfo(@PathVariable String deviceKey) {

        DeviceInfo device = deviceManager.findDevice(deviceKey);
        return device;

    }

    @RequestMapping(value="/{deviceKey}", method = RequestMethod.DELETE)
    public @ResponseBody Result removeDevice(@PathVariable String deviceKey) {

        deviceManager.removeDevice(deviceKey);
        return new Result(true, null);

    }

    @RequestMapping(value="/{deviceKey}", method = RequestMethod.POST)
    public @ResponseBody Result updateDevice(@PathVariable String deviceKey, @RequestParam("deviceID") String deviceID,
                                       @RequestParam("authUsername") String authUsername, @RequestParam("authPassword") String authPassword) {
        DeviceInfo deviceInfo = deviceManager.findDevice(deviceKey);
        deviceInfo.setDeviceID(deviceID);
        deviceInfo.setAuthUsername(authUsername);
        deviceInfo.setAuthPassword(authPassword);
        return new Result(true, null);

    }

    @RequestMapping(value="/{deviceKey}", method = RequestMethod.PUT)
    public @ResponseBody Result notifyDevice(@PathVariable String deviceKey) {
        DeviceInfo deviceInfo = deviceManager.findDevice(deviceKey);
        System.out.println("notify device with url:"+ deviceInfo.getCrURL());
        return new Result(true, null);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getDeviceInfo(@RequestParam("rows") int rows, @RequestParam("page") int page,
                                      @RequestParam("sort") String sortName, @RequestParam("order") String sortOrder,
                                      @RequestParam(value = "deviceKey", required = false) String deviceKey) {
        List<DeviceInfo> devices = null;
        if(deviceKey==null || deviceKey.equals("")){
            devices = deviceManager.getAllDeviceList();
        }else{
            devices = new ArrayList<DeviceInfo>();
            DeviceInfo d = deviceManager.findDevice(deviceKey);
            if(d!=null){
                devices.add(d);
            }
        }
        List<DeviceInfo> sortedDevices = sortDevice(devices, sortName, sortOrder);
        Map<String, Object> result = new HashMap<String, Object>();
        List<DeviceInfo> resultDeviceList = new ArrayList<DeviceInfo>();

        int start = (page - 1) * rows;
        int end = page * rows -1;
        for(int i=0; i<sortedDevices.size(); i++){

            if((i<=end)&&(i>=start)){
                resultDeviceList.add(sortedDevices.get(i));
            }
        }
        result.put("total",sortedDevices.size());
        result.put("rows", resultDeviceList);
        return result;
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public @ResponseBody
    Result addDevice(@RequestParam("deviceID") String deviceID, @RequestParam("deviceKey") String deviceKey,
                                      @RequestParam("authUsername") String authUsername, @RequestParam("authPassword") String authPassword) {
        DeviceInfo newDevice = new DeviceInfo();
        newDevice.setDeviceID(deviceID);
        newDevice.setDeviceKey(deviceKey);
        newDevice.setAuthUsername(authUsername);
        newDevice.setAuthPassword(authPassword);
        deviceManager.addDevice(deviceKey, newDevice);
        return new Result(true, null);
    }


    private List<DeviceInfo> sortDevice(List<DeviceInfo> allDevices, String sortName, String sortOrder) {
        if(sortOrder.equals("desc")){
            if(sortName.equals("deviceID")){
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o2.getDeviceID().compareTo(o1.getDeviceID());
                    }
                });
            }else if(sortName.equals("deviceKey")){
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o2.getDeviceKey().compareTo(o1.getDeviceKey());
                    }
                });
            }else if(sortName.equals("lastInformTime")){
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o2.getLastInformTime().compareTo(o1.getLastInformTime());
                    }
                });
            }else{
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o2.getDeviceID().compareTo(o1.getDeviceID());
                    }
                });
            }
        } else{
            if(sortName.equals("deviceID")){
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {

                        return o1.getDeviceID().compareTo(o2.getDeviceID());
                    }
                });
            }else if(sortName.equals("deviceKey")){
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o1.getDeviceKey().compareTo(o2.getDeviceKey());
                    }
                });
            }else if(sortName.equals("lastInformTime")){
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o1.getLastInformTime().compareTo(o2.getLastInformTime());
                    }
                });
            }else{
                Collections.sort(allDevices, new Comparator<DeviceInfo>() {
                    public int compare(DeviceInfo o1, DeviceInfo o2) {
                        return o1.getDeviceID().compareTo(o2.getDeviceID());
                    }
                });
            }
        }


        return allDevices;
    }
}
