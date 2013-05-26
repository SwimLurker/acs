package org.slstudio.acs.tr069.instruction.extension;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç12:19
 */
public class InstructionExtensionFactory {
    private Map<String, IInstructionExtension> extensionMap = Collections.synchronizedMap(new HashMap<String, IInstructionExtension>());

    public Map<String, IInstructionExtension> getExtensionMap() {
        return extensionMap;
    }

    public void setExtensionMap(Map<String, IInstructionExtension> extensionMap) {
        this.extensionMap = extensionMap;
    }

    public IInstructionExtension getExtension(String type) {
        return extensionMap.get(type.toUpperCase());
    }
}
