package org.slstudio.acs.kernal.engine;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: обнГ3:51
 */
public interface IEngineSelector {
    public boolean selectEngine(String key, IProtocolEngine engine);
}
