package org.slstudio.acs.kernal.lifecycle;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-11
 * Time: ÏÂÎç2:25
 */
public class LifecycleEvent {
    private ILifecycle lifecycle = null;
    private String type = null;
    private Object data = null;

    public LifecycleEvent(ILifecycle lifecycle, String type, Object data) {
        this.lifecycle = lifecycle;
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ILifecycle getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(ILifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }
}
