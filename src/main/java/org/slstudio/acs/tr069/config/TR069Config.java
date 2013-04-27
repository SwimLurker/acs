package org.slstudio.acs.tr069.config;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-24
 * Time: ионГ12:14
 */
public class TR069Config {
    public static boolean isUseSessionCookie() {
        return true;
    }

    public static boolean isSupportSameIPForDifferentSession() {
        return true;
    }
}
