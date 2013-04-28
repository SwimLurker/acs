package org.slstudio.acs.tr069.engine;

import org.slstudio.acs.kernal.engine.IEngineSelector;
import org.slstudio.acs.kernal.engine.IProtocolEngine;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-4-28
 * Time: обнГ3:54
 */
public class DefaultEngineSelector implements IEngineSelector {

    public boolean selectEngine(String key, IProtocolEngine engine) {
        if(key != null && key.equalsIgnoreCase(engine.getEngineID())){
            return true;
        }
        return false;
    }
}
