package org.slstudio.acs.web.controller;

import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.manager.IJobManager;
import org.slstudio.acs.util.JSONUtil;
import org.slstudio.acs.web.bean.InstructionBean;
import org.slstudio.acs.web.bean.PropertyGridRowBean;
import org.slstudio.acs.web.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-16
 * Time: ÉÏÎç12:19
 */
@Controller
@RequestMapping("/jobs")
public class JobController {
    @Resource(name = "jobManager")
    private IJobManager jobManager= null;

    @RequestMapping(value="/{jobID}", method = RequestMethod.GET)
    public @ResponseBody IDeviceJob getJob(@PathVariable String jobID) {
        IDeviceJob job = jobManager.findJob(jobID);
        return job;
    }

    @RequestMapping(value="/{jobID}", method = RequestMethod.DELETE)
    public @ResponseBody Result removeJob(@PathVariable String jobID) {
        IDeviceJob job = jobManager.findJob(jobID);
        if(job == null){
            return new Result(false, "Unknown job");
        }else
            jobManager.removeJob(job);
        return new Result(true, null);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getJobList(@RequestParam("rows") int rows, @RequestParam("page") int page,
                                   @RequestParam("sort") String sortName, @RequestParam("order") String sortOrder,
                                   @RequestParam(value = "jobID", required = false) String jobID,
                                   @RequestParam(value = "deviceKey", required = false) String deviceKey,
                                   @RequestParam(value = "jobType", required = false) String jobType) {
        List<IDeviceJob> jobs = new ArrayList<IDeviceJob>();
        if(jobID !=null && (!jobID.equals(""))){
            IDeviceJob job = jobManager.findJob(jobID);
            if(job != null){
                jobs.add(job);
            }
        }else{
            if(deviceKey == null || deviceKey.equals("")){
                if(jobType != null && jobType.equals("system")){
                    jobs.addAll(jobManager.getAllSystemJobs());
                }else if(jobType != null && jobType.equals("user")){
                    jobs.addAll(jobManager.getAllUserJobs());
                }else{
                    jobs.addAll(jobManager.getAllSystemJobs());
                    jobs.addAll(jobManager.getAllUserJobs());
                }
            }else{
                if(jobType != null && jobType.equals("system")){
                    jobs.addAll(jobManager.findSystemJobs(deviceKey));
                }else if(jobType != null && jobType.equals("user")){
                    jobs.addAll(jobManager.findUserJobs(deviceKey));
                }else{
                    jobs.addAll(jobManager.findSystemJobs(deviceKey));
                    jobs.addAll(jobManager.findUserJobs(deviceKey));
                }
            }
        }


        List<IDeviceJob> sortedJobs = sortJobs(jobs, sortName, sortOrder);
        Map<String, Object> result = new HashMap<String, Object>();
        List<IDeviceJob> resultJobList = new ArrayList<IDeviceJob>();

        int start = (page - 1) * rows;
        int end = page * rows -1;
        for(int i=0; i<sortedJobs.size(); i++){

            if((i<=end)&&(i>=start)){
                resultJobList.add(sortedJobs.get(i));
            }
        }
        result.put("total",sortedJobs.size());
        result.put("rows", resultJobList);
        System.out.println(JSONUtil.toJsonString(result));
        return result;
    }

    @RequestMapping(value="/{jobID}/instructions", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getJobInstructionList(@PathVariable String jobID) {
        List<InstructionBean> instructions = new ArrayList<InstructionBean>();
        if(jobID != null){
            IDeviceJob job = jobManager.findJob(jobID);
            if(job != null){
                for(IInstruction intr:job.getInstructions()){
                    InstructionBean ib = new InstructionBean();
                    ib.setInstructionID(intr.getInstructionID());
                    ib.setInstructionName(intr.getInstructionName());
                    ib.setContent(intr.toString());
                    instructions.add(ib);
                }
            }
        }


        Map<String, Object> result = new HashMap<String, Object>();

        result.put("total",instructions.size());
        result.put("rows", instructions);
        System.out.println(JSONUtil.toJsonString(result));
        return result;
    }

    @RequestMapping(value="/{jobID}/symboltable", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getJobSymbolTable(@PathVariable String jobID) {
        List<PropertyGridRowBean> rows = new ArrayList<PropertyGridRowBean>();
        if(jobID != null){
            IDeviceJob job = jobManager.findJob(jobID);
            if(job != null){
                Set<String> keys = job.getSymbolTable().keySet();
                for(String key:keys){
                    PropertyGridRowBean row = new PropertyGridRowBean();
                    row.setName(key);
                    Object value = job.getSymbolTable().get(key);
                    row.setValue(value == null?"":JSONUtil.toJsonString(value));
                    rows.add(row);
                }
            }
        }


        Map<String, Object> result = new HashMap<String, Object>();

        result.put("total",rows.size());
        result.put("rows", rows);
        System.out.println(JSONUtil.toJsonString(result));
        return result;
    }

    private List<IDeviceJob> sortJobs(List<IDeviceJob> allJobs, String sortName, String sortOrder) {
        if(sortOrder.equals("desc")){
            if(sortName.equals("jobID")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getJobID().compareTo(o1.getJobID());
                    }
                });
            }else if(sortName.equals("jobName")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getJobName().compareTo(o1.getJobName());
                    }
                });
            }else if(sortName.equals("deviceKey")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getDeviceKey().compareTo(o1.getDeviceKey());
                    }
                });
            }else if(sortName.equals("status")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return new Integer(o2.getStatus()).compareTo(o1.getStatus());
                    }
                });
            }else if(sortName.equals("createTime")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getCreateTime().compareTo(o1.getCreateTime());
                    }
                });
            }else if(sortName.equals("beginTime")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getBeginTime().compareTo(o1.getBeginTime());
                    }
                });
            }else if(sortName.equals("completeTime")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getCompleteTime().compareTo(o1.getCompleteTime());
                    }
                });
            }else{
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o2.getJobID().compareTo(o1.getJobID());
                    }
                });
            }
        } else{
            if(sortName.equals("jobID")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getJobID().compareTo(o2.getJobID());
                    }
                });
            }else if(sortName.equals("jobName")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getJobName().compareTo(o2.getJobName());
                    }
                });
            }else if(sortName.equals("deviceKey")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getDeviceKey().compareTo(o2.getDeviceKey());
                    }
                });
            }else if(sortName.equals("status")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return new Integer(o1.getStatus()).compareTo(o2.getStatus());
                    }
                });
            }else if(sortName.equals("createTime")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getCreateTime().compareTo(o2.getCreateTime());
                    }
                });
            }else if(sortName.equals("beginTime")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getBeginTime().compareTo(o2.getBeginTime());
                    }
                });
            }else if(sortName.equals("completeTime")){
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getCompleteTime().compareTo(o2.getCompleteTime());
                    }
                });
            }else{
                Collections.sort(allJobs, new Comparator<IDeviceJob>() {
                    public int compare(IDeviceJob o1, IDeviceJob o2) {
                        return o1.getJobID().compareTo(o2.getJobID());
                    }
                });
            }
        }
        return allJobs;
    }
}
