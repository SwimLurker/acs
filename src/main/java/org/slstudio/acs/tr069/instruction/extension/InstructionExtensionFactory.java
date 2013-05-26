package org.slstudio.acs.tr069.instruction.extension;

import edu.emory.mathcs.backport.java.util.Collections;
import org.slstudio.acs.tr069.instruction.extension.func.FunctionInstructionExtension;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç12:19
 */
public class InstructionExtensionFactory {
    private static Map<String, IInstructionExtension> extensionMap = Collections.synchronizedMap(new HashMap<String, IInstructionExtension>());

    static {
        extensionMap.put("FUNC", new FunctionInstructionExtension());
    }

    public static IInstructionExtension getExtension(String type) {
        return extensionMap.get(type.toUpperCase());
    }
}
