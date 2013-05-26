package org.slstudio.acs.tr069.instruction.extension.func;

import edu.emory.mathcs.backport.java.util.Collections;
import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.extension.IInstructionExtension;
import org.slstudio.acs.util.Tuple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ÉÏÎç3:07
 */
public class FunctionInstructionExtension implements IInstructionExtension {
    private static Map<String,IFunction> functionMap = Collections.synchronizedMap(new HashMap<String, IFunction>());
    static {
        functionMap.put("ADD", new AddFunction());
        functionMap.put("MINUS", new MinusFunction());
        functionMap.put("PRODUCT", new ProductFunction());
    }

    public IInstruction createInstruction(String instructionString, int instructionID) throws InstructionException{
        Tuple.Tuple2<String, String[]> nameAndArgs = parseFunctionStructure(instructionString);
        if(nameAndArgs == null){
            throw new InstructionException("unknown function expression");
        }
        String funcName = nameAndArgs._1();
        IFunction func = functionMap.get(funcName.toUpperCase());
        if(func == null){
            throw new InstructionException("unknown function name");
        }
        String[] args = nameAndArgs._2();
        func.setArgs(args);
        FunctionInstruction functionInstruction = new FunctionInstruction(Integer.toString(instructionID));
        functionInstruction.setFunction(func);
        return functionInstruction;
    }

    private Tuple.Tuple2<String, String[]> parseFunctionStructure(String functionExpression) {
        int pos = functionExpression.indexOf(InstructionConstants.SYMBOLNAME_FUNCARGS_PREFIX);
        if(pos != -1){
            int endPos = functionExpression.indexOf(InstructionConstants.SYMBOLNAME_FUNCARGS_POSTFIX, pos);
            if(endPos != -1){
                String funcName = functionExpression.substring(0, pos).trim();
                String argsStr = functionExpression.substring(pos+1, endPos).trim();
                String args[] = argsStr.split(",", 0);
                return Tuple.tuple(funcName, args);
            }
        }
        return null;
    }
}
