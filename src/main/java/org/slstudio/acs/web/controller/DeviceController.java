package org.slstudio.acs.web.controller;

import org.slstudio.acs.hms.device.DeviceInfo;
import org.slstudio.acs.hms.device.IDeviceManager;
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
@RequestMapping("/device")
public class DeviceController {
    @Resource(name = "deviceManager")
    private IDeviceManager deviceManager= null;

    @RequestMapping(value="{deviceKey}", method = RequestMethod.GET)
    public @ResponseBody DeviceInfo getDeviceInfo(@PathVariable String deviceKey) {

        DeviceInfo device = deviceManager.findDevice(deviceKey);
        return device;

    }
    @RequestMapping(value="/")
    public @ResponseBody
    Map<String, Object> getDeviceInfo(@RequestParam("rows") int rows, @RequestParam("page") int page,
                                      @RequestParam("sort") String sortName, @RequestParam("order") String sortOrder) {
        System.out.println("rows:"+ rows);
        System.out.println("page:"+ page);
        System.out.println("sortName:"+ sortName);
        System.out.println("sortOrder:"+ sortOrder);

        List<DeviceInfo> allDevices = deviceManager.getAllDeviceList();
        List<DeviceInfo> sortedDevices = sortDevice(allDevices, sortName, sortOrder);
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
