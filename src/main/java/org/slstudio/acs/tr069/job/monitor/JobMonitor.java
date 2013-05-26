package org.slstudio.acs.tr069.job.monitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.manager.IJobManager;
import org.slstudio.acs.tr069.job.runner.IJobRunner;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç1:21
 */
public class JobMonitor  implements Runnable {
    private static final Log log = LogFactory.getLog(JobMonitor.class);

    private boolean bRunning = true;
    private long monitorInterval = 60000;

    private Set<String> reservedDevices = Collections.synchronizedSet(new HashSet<String>());

    private IJobManager jobManager = null;
    private IJobRunner jobRunner = null;

    public void addReservedDevice(String deviceID) {
        reservedDevices.add(deviceID);
    }

    public void removeReservedDevice(String deviceID) {
        reservedDevices.remove(deviceID);
    }

    public Set<String> getReservedDevices() {
        return reservedDevices;
    }

    public void setReservedDevices(Set<String> reservedDevices) {
        this.reservedDevices = reservedDevices;
    }

    public long getMonitorInterval() {
        return monitorInterval;
    }

    public void setMonitorInterval(long monitorInterval) {
        this.monitorInterval = monitorInterval;
    }

    public IJobManager getJobManager() {
        return jobManager;
    }

    public void setJobManager(IJobManager jobManager) {
        this.jobManager = jobManager;
    }

    public IJobRunner getJobRunner() {
        return jobRunner;
    }

    public void setJobRunner(IJobRunner jobRunner) {
        this.jobRunner = jobRunner;
    }

    public void stopMonitor() {
        this.bRunning = false;
    }

    public void startMonitor() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }


    public void run() {
        bRunning = true;
        while (bRunning) {
            log.info("Job monitor doing job...");
            try {
                checkTimeoutJobs(jobManager.getAllSystemJobs());
                checkTimeoutJobs(jobManager.getAllUserJobs());
            } catch (ConcurrentModificationException exp) {
                log.error("Job monitor exception:cocurrent modification", exp);
            } catch (Exception exp) {
                log.error("Job monitor exception", exp);
            }
            if (!bRunning) {
                break;
            }
            //wait interval
            long interval = getMonitorInterval();
            if (interval <= 0) {
                interval = 60000;
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }

    private void checkTimeoutJobs(List<? extends IDeviceJob> allJobs) {
        List<IDeviceJob> deleteJobs = new ArrayList<IDeviceJob>();

        for(IDeviceJob job : allJobs){
            if(!reservedDevices.contains(job.getDeviceKey())){
                if(job.isReady()){
                    long currentTime = System.currentTimeMillis();
                    if (job.getWaitingTimeout() != -1) {
                        if (currentTime > (job.getCreateTime().getTime() + job.getWaitingTimeout())) {
                            //wait time out,fail job and remove it from queue
                            log.info("Remove wait timeout job");
                            try {
                                jobRunner.failOnTimeout(job, true);
                            } catch (Exception exp) {
                                exp.printStackTrace();
                                log.error("fail job exception", exp);
                            }
                            deleteJobs.add(job);
                        }
                    }
                }else if (job.isRunning()) {
                    if (job.getRunningTimeout() != -1) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime > (job.getBeginTime().getTime() + job.getRunningTimeout())) {
                            //wait time out,fail job and remove it from queue
                            log.info("Remove running timeout job");
                            try {
                                jobRunner.failOnTimeout(job, false);
                            } catch (Exception exp) {
                                exp.printStackTrace();
                                log.error("fail job exception", exp);
                            }
                            deleteJobs.add(job);
                        }
                    }
                }else if (job.isFinished()) {
                    log.info("Remove finished job");
                    deleteJobs.add(job);
                }
            }
        }
        for(IDeviceJob dJob: deleteJobs){
            jobManager.removeJob(dJob);
        }
    }
}