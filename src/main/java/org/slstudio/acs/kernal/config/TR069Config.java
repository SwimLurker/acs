package org.slstudio.acs.kernal.config;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ����12:14
 */
public class TR069Config {
    public static boolean isUseSessionCookie() {
        return true;
    }

    public static boolean isSupportSameIPForDifferentSession() {
        return true;
    }
}
