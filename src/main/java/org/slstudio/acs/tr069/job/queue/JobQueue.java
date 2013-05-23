package org.slstudio.acs.tr069.job.queue;

import org.slstudio.acs.tr069.job.IDeviceJob;

import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-2
 * Time: ÉÏÎç3:30
 */
public class JobQueue<T extends IDeviceJob> {
    private Vector<T> queue = new Vector<T>();
    public synchronized void push(T job){
        queue.add(job);
    }
    public synchronized T pop(){
        if(queue.size()>0){
            return queue.remove(0);
        }
        return null;
    }

    public T fetch(){
        if(queue.size()>0){
            return queue.get(0);
        }
        return null;
    }
    public synchronized void remove(T job){
        queue.remove(job);
    }
    public T find(String jobID){
        for(T job: queue){
            if(job.getJobID().equals(jobID)){
                return job;
            }
        }
        return null;
    }

    public List<T> getAllJobs() {
        return queue;
    }
}
