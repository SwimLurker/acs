package org.slstudio.acs.tr069.instruction.extension.python;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.extension.IInstructionExtension;
import org.slstudio.acs.util.Tuple;

import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-30
 * Time: ÉÏÎç2:21
 */
public class PythonInstructionExtension implements IInstructionExtension {
    public IInstruction createInstruction(String instructionString, int instructionID) throws InstructionException {
        Tuple.Tuple3<String, String, String[]> pythonInfo = parsePythonStructure(instructionString);
        if(pythonInfo == null){
            throw new InstructionException("unknown python expression");
        }
        String pyFilename = pythonInfo._1();
        String funcName = pythonInfo._2();
        String[] args = pythonInfo._3();

        PythonInstruction pyInstruction = new PythonInstruction(Integer.toString(instructionID));
        pyInstruction.setFilename(pyFilename);
        pyInstruction.setFunctionName(funcName);
        pyInstruction.setArguments(args);
        return pyInstruction;
    }

    private Tuple.Tuple3<String, String, String[]> parsePythonStructure(String pythonExpression) throws InstructionException{
        StringTokenizer st = new StringTokenizer(pythonExpression, " ");
        int count = st.countTokens();
        if(count<2){
            throw new InstructionException("invalid python call expression");
        }
        String pyFilename = st.nextToken();
        String funcName = st.nextToken();
        String[] args = new String[count-2];
        for(int i=0; i<count-2; i++){
            args[i] = st.nextToken();
        }
        return Tuple.tuple(pyFilename, funcName, args);
    }
}
