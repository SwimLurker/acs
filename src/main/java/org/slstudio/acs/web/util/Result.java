package org.slstudio.acs.web.util;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-9
 * Time: обнГ7:10
 */

public class Result {
    private boolean success = true;
    private String msg = null;

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
