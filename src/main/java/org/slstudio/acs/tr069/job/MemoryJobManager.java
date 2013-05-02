package org.slstudio.acs.tr069.job;

import org.slstudio.acs.tr069.job.queue.JobQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:18
 */
public class MemoryJobManager implements IJobManager {
    private Map<String,JobQueue<IUserJob>> userJobQueueTable = new HashMap<String, JobQueue<IUserJob>>();
    private Map<String,JobQueue<ISystemJob>> systemJobQueueTable = new HashMap<String, JobQueue<ISystemJob>>();


    public void removeJob(IJob job) {
        if(job instanceof ISystemJob){
            removeSystemJob((ISystemJob)job);
        }else if(job instanceof IUserJob){
            removeUserJob((IUserJob)job);
        }
    }

    private void removeSystemJob(ISystemJob job) {
        String deviceKey = job.getDeviceKey();
        JobQueue<ISystemJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            jobQueue.remove(job);
        }
    }

    private void removeUserJob(IUserJob job) {
        String deviceKey = job.getDeviceKey();
        JobQueue<IUserJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            jobQueue.remove(job);
        }
    }

    public ISystemJob findSystemJob(String deviceKey, String jobID) {
        JobQueue<ISystemJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.find(jobID);
        }
        return null;
    }

    public IUserJob findUserJob(String deviceKey, String jobID) {
        JobQueue<IUserJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.find(jobID);
        }
        return null;
    }

    public ISystemJob fetchSystemJob(String deviceKey) {
        JobQueue<ISystemJob> jobQueue = systemJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.pop();
        }
        return null;
    }

    public IUserJob fetchUserJob(String deviceKey) {
        JobQueue<IUserJob> jobQueue = userJobQueueTable.get(deviceKey);
        if(jobQueue != null){
            return jobQueue.pop();
        }
        return null;
    }

    public void addSystemJob(ISystemJob job){
        JobQueue<ISystemJob> jobQueue = systemJobQueueTable.get(job.getDeviceKey());
        if(jobQueue == null){
            jobQueue = new JobQueue<ISystemJob>();
        }
        jobQueue.push(job);
    }

    public void addUserJob(IUserJob job){
        JobQueue<IUserJob> jobQueue = userJobQueueTable.get(job.getDeviceKey());
        if(jobQueue == null){
            jobQueue = new JobQueue<IUserJob>();
        }
        jobQueue.push(job);
    }
}
