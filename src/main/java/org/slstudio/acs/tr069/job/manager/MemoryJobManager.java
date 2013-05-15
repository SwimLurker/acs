package org.slstudio.acs.tr069.job.manager;

import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.ISystemDeviceJob;
import org.slstudio.acs.tr069.job.IUserDeviceJob;
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
    private Map<String,JobQueue<IUserDeviceJob>> userJobQueueTable = new HashMap<String, JobQueue<IUserDeviceJob>>();
    private Map<String,JobQueue<ISystemDeviceJob>> systemJobQueueTable = new HashMap<String, JobQueue<ISystemDeviceJob>>();


    public void removeJob(IDeviceJob job) {
        if(job instanceof ISystemDeviceJob){
            removeSystemJob((ISystemDeviceJob)job);
        }else if(job instanceof IUserDeviceJob){
            removeUserJob((IUserDeviceJob)job);
        }
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

    public void addSystemJob(ISystemDeviceJob job){
        JobQueue<ISystemDeviceJob> jobQueue = systemJobQueueTable.get(job.getDeviceKey());
        if(jobQueue == null){
            jobQueue = new JobQueue<ISystemDeviceJob>();
            systemJobQueueTable.put(job.getDeviceKey(),jobQueue);
        }
        jobQueue.push(job);

    }

    public void addUserJob(IUserDeviceJob job){
        JobQueue<IUserDeviceJob> jobQueue = userJobQueueTable.get(job.getDeviceKey());
        if(jobQueue == null){
            jobQueue = new JobQueue<IUserDeviceJob>();
            userJobQueueTable.put(job.getDeviceKey(),jobQueue);
        }
        jobQueue.push(job);
    }
}
