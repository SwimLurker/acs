package org.slstudio.acs.tr069.job;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ2:26
 */
public interface IJobManager {
    public void removeJob(IJob job);
    public ISystemJob findSystemJob(String deviceKey, String jobID);
    public IUserJob findUserJob(String devcieKey,String jobID);
    public ISystemJob fetchSystemJob(String deviceKey);
    public IUserJob fetchUserJob(String deviceKey);
    public void addSystemJob(ISystemJob job);
    public void addUserJob(IUserJob job);
}
