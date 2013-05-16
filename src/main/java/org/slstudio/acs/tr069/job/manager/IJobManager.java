package org.slstudio.acs.tr069.job.manager;

import org.slstudio.acs.tr069.job.IDeviceJob;
import org.slstudio.acs.tr069.job.ISystemDeviceJob;
import org.slstudio.acs.tr069.job.IUserDeviceJob;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:26
 */
public interface IJobManager {
    public void removeJob(IDeviceJob job);
    public ISystemDeviceJob findSystemJob(String deviceKey, String jobID);
    public IUserDeviceJob findUserJob(String devcieKey,String jobID);
    public ISystemDeviceJob fetchSystemJob(String deviceKey);
    public IUserDeviceJob fetchUserJob(String deviceKey);
    public void addSystemJob(ISystemDeviceJob job);
    public void addUserJob(IUserDeviceJob job);

   public IDeviceJob findJob(String jobID);

    List<ISystemDeviceJob> getAllSystemJobs();

    List<IUserDeviceJob> getAllUserJobs();

    List<ISystemDeviceJob> findSystemJobs(String deviceKey);

    List<IUserDeviceJob> findUserJobs(String deviceKey);
}
