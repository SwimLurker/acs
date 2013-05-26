package org.slstudio.acs.tr069.instruction.extension.func;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÏÂÎç3:56
 */
public class FunctionFactory {
    private Map<String, IFunction> functionMap = Collections.synchronizedMap(new HashMap<String, IFunction>());

    public Map<String, IFunction> getFunctionMap() {
        return functionMap;
    }

    public void setFunctionMap(Map<String, IFunction> functionMap) {
        this.functionMap = functionMap;
    }

    public IFunction createFunction(String type){
        IFunction function = functionMap.get(type);
        return function.newInstance();
    }
}
