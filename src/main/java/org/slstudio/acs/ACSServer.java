package org.slstudio.acs;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ÉÏÎç12:18
 */
public class ACSServer {
    private static ACSServer _instance = null;
    private boolean bRunning = false;

    protected ACSServer(){
    }

    public static ACSServer getInstance(){
        if(_instance == null){
            _instance = new ACSServer();
        }
        return _instance;
    }

    public boolean isRunning() {
        return bRunning;
    }

    public void setAcsConfigFile(String acsConfigfile) {

    }

    public boolean start() {
        bRunning = true;
        return bRunning;
    }

    public boolean stop() {
        bRunning = false;
        return true;
    }
}
