package org.slstudio.acs.tr069.instruction.extension.python;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.InstructionBase;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.util.InstructionUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-30
 * Time: ÉÏÎç2:26
 */
public class PythonInstruction extends InstructionBase {
    private static final Log log = LogFactory.getLog(PythonInstruction.class);

    private String filename = null;
    private String functionName = null;
    private String[] arguments = null;

    protected PythonInstruction(String instructionID) {
        super(instructionID);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String getInstructionName() {
        return "PYTHON";
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException, JobCompleteException {
        try{
            Map<String, Object> symbolTable = cmdContext.getSymbolTable ();

            PythonInterpreter interpreter = new PythonInterpreter();
            InputStream is = this.getClass().getResourceAsStream(filename);
            interpreter.execfile(is);
            PyFunction func = (PyFunction)interpreter.get(functionName,PyFunction.class);
            PyObject pyobj = func.__call__(parsePyObjects(arguments, symbolTable));
            String result = null;
            if(pyobj == null){
                result = null;
            }else{
                result = pyobj.toString();
            }
            if(result != null){
                cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), result);
            }
        }catch (Exception exp){
            log.error("job failed of python function call instruction(" + getInstructionID()+"):" + exp.getMessage());
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "python function execution exception:" + exp.getMessage());
            throw new JobFailException("job failed when call python file(" + filename +")'s function(" + functionName +")", exp);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Execute Python function:").append(functionName).append(" in module:").append(filename)
                .append(" with arguments size[").append(arguments.length).append("]\r\n");
        for(int i=0; i<arguments.length ; i++){
            sb.append("arg[").append(i).append("]:").append(arguments[i]).append("\r\n");
        }
        return sb.toString();
    }

    private PyObject[] parsePyObjects(String[] arguments, Map<String, Object> symbolTable) throws InstructionException {
        int argSize = arguments.length;
        PyObject[] result = new PyObject[argSize];
        for(int i=0; i<argSize; i++){
            //first handle variable  population in args
            String arg = arguments[i];
            String newArg = InstructionUtil.populateTextWithVariableValue(arg, symbolTable);
            result[i] = parsePyObject(newArg);
        }
        return result;
    }

    private PyObject parsePyObject(String pyObjStr) {
        PyObject result = null;
        String[] s1 = pyObjStr.split("#", 2);
        if(s1.length<2){
            result = new PyString(pyObjStr);
        }else{
            String type = s1[0];
            String strValue = s1[1].trim();
            if("int".equalsIgnoreCase(type)){
                result = new PyInteger(Integer.parseInt(strValue));
            }else if("long".equalsIgnoreCase(type)){
                result = new PyLong(Long.parseLong(strValue));
            }else if("float".equalsIgnoreCase(type)){
                result = new PyFloat(Double.parseDouble(strValue));
            }else if("boolean".equalsIgnoreCase(type)){
                result = new PyBoolean(Boolean.parseBoolean(strValue));
            }else if("string".equalsIgnoreCase(type)){
                result = new PyString(strValue);
            }else if("list".equalsIgnoreCase(type)){
                String str = strValue;
                if(strValue.startsWith("[") && strValue.endsWith("]")){
                    str = str.substring(1,str.length()-1).trim();
                }
                StringTokenizer st = new StringTokenizer(str, ",");
                PyObject[] poArray = new PyObject[st.countTokens()];
                int i=0;
                while(st.hasMoreTokens()){
                    String s2 = st.nextToken();
                    poArray[i++] = parsePyObject(s2);
                }
                result = new PyList(poArray);
            }else if("set".equalsIgnoreCase(type)){
                String str = strValue;
                if(strValue.startsWith("{") && strValue.endsWith("}")){
                    str = str.substring(1,str.length()-1).trim();
                }
                StringTokenizer st = new StringTokenizer(str, ",");
                PyObject[] poArray = new PyObject[st.countTokens()];
                int i=0;
                while(st.hasMoreTokens()){
                    String s2 = st.nextToken();
                    poArray[i++] = parsePyObject(s2);
                }
                result = new PySet(new PyList(poArray));
            }else if("tuple".equalsIgnoreCase(type)){

                String str = strValue;
                if(strValue.startsWith("(") && strValue.endsWith(")")){
                    str = str.substring(1,str.length()-1).trim();
                }
                StringTokenizer st = new StringTokenizer(str, ",");
                PyObject[] poArray = new PyObject[st.countTokens()];
                int i = 0;
                while(st.hasMoreTokens()){
                    String s2 = st.nextToken();
                    poArray[i++] = parsePyObject(s2);
                }
                result = new PyTuple(poArray);

            } else if("dict".equalsIgnoreCase(type)){
                result = new PyDictionary();
                String str = strValue;
                if(strValue.startsWith("{") && strValue.endsWith("}")){
                    str = str.substring(1,str.length()-1).trim();
                }
                StringTokenizer st = new StringTokenizer(str, ",");
                while(st.hasMoreTokens()){
                    String s2 = st.nextToken();
                    String[] s3 = s2.split(":",2);
                    if(s3.length>=2){
                        result.__setitem__(parsePyObject(s3[0]),parsePyObject(s3[1]));
                    }
                }
            }else{
                result = new PyString(strValue);
            }
        }
        return result;
    }


    public static void main(String[] args) throws JobCompleteException, InstructionFailException, JobFailException {
        PythonInstruction pi = new PythonInstruction("1");
        pi.setFilename("test.py");
        pi.setFunctionName("add");
        pi.setArguments(new String[]{"dict#{string#1:long#22}","dict#{int#2:int#2}"});
        Map<String, Object> st = new HashMap<String, Object>();
        InstructionContext ic = new InstructionContext(st);
        pi.execute(ic);
        System.out.println(ic.getSymbolTable().get("$$_1"));
    }
}
