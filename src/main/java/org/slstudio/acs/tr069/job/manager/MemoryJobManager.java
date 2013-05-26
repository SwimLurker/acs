package org.slstudio.acs.tr069.job.manager;

import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.ISystemDeviceJob;
import org.slstudio.acs.tr069.job.IUserDeviceJob;
import org.slstudio.acs.tr069.job.queue.JobQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:18
 */
public class MemoryJobManager implements IJobManager {
    private Map<String,JobQueue<IUserDeviceJob>> userJobQueueTable = new HashMap<String, JobQueue<IUserDeviceJob>>();
    private Map<String,JobQueue<ISystemDeviceJob>> systemJobQueueTable = new HashMap<String, JobQueue<ISystemDeviceJob>>();


    public synchronized void addJob(IDeviceJob job) {
        if(job instanceof ISystemDeviceJob){
            addSystemJob((ISystemDeviceJob)job);
        }else if(job instanceof IUserDeviceJob){
            addUserJob((IUserDeviceJob)job);
        }
    }

    public synchronized void removeJob(IDeviceJob job) {
        if(job instanceof ISystemDeviceJob){
            removeSystemJob((ISystemDeviceJob)job);
        }else if(job instanceof IUserDeviceJob){
            removeUserJob((IUserDeviceJob)job);
        }
    }

    public List<ISystemDeviceJob> getAllSystemJobs() {
        List<ISystemDeviceJob> result = new ArrayList<ISystemDeviceJob>();
        for(JobQueue<ISystemDeviceJob> jobQueue : systemJobQueueTable.values()){
            result.addAll(jobQueue.getAllJobs());
        }
        return result;
    }

    public List<IUserDeviceJob> getAllUserJobs() {
        List<IUserDeviceJob> result = new ArrayList<IUserDeviceJob>();
        for(JobQueue<IUserDeviceJob> jobQueue : userJobQueueTable.values()){
            result.addAll(jobQueue.getAllJobs());
        }
        return result;
    }

    public List<ISystemDeviceJob> findSystemJobs(String deviceKey) {
        JobQueue<ISystemDeviceJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue!=null){
            return jobQueue.getAllJobs();
        }
        return new ArrayList<ISystemDeviceJob>();
    }

    public List<IUserDeviceJob> findUserJobs(String deviceKey) {
        JobQueue<IUserDeviceJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue!=null){
            return jobQueue.getAllJobs();
        }
        return new ArrayList<IUserDeviceJob>();
    }

    public ISystemDeviceJob findSystemJob(String deviceKey, String jobID) {
        JobQueue<ISystemDeviceJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.find(jobID);
        }
        return null;
    }

    public IUserDeviceJob findUserJob(String deviceKey, String jobID) {
        JobQueue<IUserDeviceJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.find(jobID);
        }
        return null;
    }

    public ISystemDeviceJob fetchSystemJob(String deviceKey) {
        JobQueue<ISystemDeviceJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.fetch();
        }
        return null;
    }

    public IUserDeviceJob fetchUserJob(String deviceKey) {
        JobQueue<IUserDeviceJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.fetch();
        }
        return null;
    }

    public IDeviceJob findJob(String jobID) {
        for(JobQueue<ISystemDeviceJob> jobQueue : systemJobQueueTable.values()){
            ISystemDeviceJob job = jobQueue.find(jobID);
            if(job != null){
                return job;
            }
        }
        for(JobQueue<IUserDeviceJob> jobQueue : userJobQueueTable.values()){
            IUserDeviceJob job = jobQueue.find(jobID);
            if(job != null){
               return job;
            }
        }
        return null;
    }

    private void addSystemJob(ISystemDeviceJob job){
        JobQueue<ISystemDeviceJob> jobQueue = systemJobQueueTable.get(job.getDeviceKey());
        if(jobQueue == null){
            jobQueue = new JobQueue<ISystemDeviceJob>();
            systemJobQueueTable.put(job.getDeviceKey(),jobQueue);
        }
        jobQueue.push(job);

    }

    private void addUserJob(IUserDeviceJob job){

        JobQueue<IUserDeviceJob> jobQueue = userJobQueueTable.get(job.getDeviceKey());
        if(jobQueue == null){
            jobQueue = new JobQueue<IUserDeviceJob>();
            userJobQueueTable.put(job.getDeviceKey(),jobQueue);
        }
        jobQueue.push(job);
    }

    private void removeSystemJob(ISystemDeviceJob job) {
        String deviceKey = job.getDeviceKey();
        JobQueue<ISystemDeviceJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            jobQueue.remove(job);
        }
    }

    private void removeUserJob(IUserDeviceJob job) {
        String deviceKey = job.getDeviceKey();
        JobQueue<IUserDeviceJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            jobQueue.remove(job);
        }
    }

}
