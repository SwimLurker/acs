package org.slstudio.acs.tr069.instruction.extension.java;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.extension.IInstructionExtension;
import org.slstudio.acs.util.Tuple;

import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÏÂÎç5:13
 */
public class JavaCallInstructionExtension implements IInstructionExtension {

    public IInstruction createInstruction(String instructionString, int instructionID) throws InstructionException {
        Tuple.Tuple3<String,String,String[]> callInfo = parseJavaCallStructure(instructionString);
        if(callInfo == null){
            throw new InstructionException("unknown java call expression");
        }
        String className = callInfo._1();
        String methodName = callInfo._2();
        String[] args = callInfo._3();

        JavaCallInstruction callInstruction = new JavaCallInstruction(Integer.toString(instructionID));
        callInstruction.setClassName(className);
        callInstruction.setMethodName(methodName);
        callInstruction.setArgs(args);
        return callInstruction;
    }

    private Tuple.Tuple3<String, String, String[]> parseJavaCallStructure(String callExpression) throws InstructionException {
        StringTokenizer st = new StringTokenizer(callExpression, " ");
        int count = st.countTokens();
        if(count<2){
            throw new InstructionException("invalid java call expression");
        }
        String className = st.nextToken();
        String methodName = st.nextToken();
        String[] args = new String[count-2];
        for(int i=0; i<count-2; i++){
            args[i] = st.nextToken();
        }
        return Tuple.tuple(className, methodName, args);
    }

}
