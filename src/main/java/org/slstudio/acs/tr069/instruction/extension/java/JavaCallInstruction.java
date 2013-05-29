package org.slstudio.acs.tr069.instruction.extension.java;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.instruction.InstructionBase;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.util.InstructionUtil;
import org.slstudio.acs.util.JSONUtil;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÏÂÎç5:16
 */
public class JavaCallInstruction extends InstructionBase {
    private static final Log log = LogFactory.getLog(JavaCallInstruction.class);

    private String className = null;
    private String methodName = null;
    private String[] args = null;

    public JavaCallInstruction(String instructionID) {
        super(instructionID);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getInstructionName() {
        return "JAVACALL";
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException, JobCompleteException {
        try{
            Map<String, Object> symbolTable = cmdContext.getSymbolTable ();

            int argSize = args.length;
            Class[] argClasses = new Class[argSize];
            String[] newArgs = new String[argSize];
            for(int i=0; i<argSize; i++){
                //first handle variable  population in args
                String arg = args[i];
                String newArg = InstructionUtil.populateTextWithVariableValue(arg, symbolTable);
                newArgs[i] = newArg;
                argClasses[i] = String.class;
            }
            Class cls = Class.forName(className);
            Method method =  cls.getMethod(methodName,argClasses);
            Object value = method.invoke(cls.newInstance(), newArgs);
            String result = null;
            if(value == null){
                result = null;
            }else if(value instanceof String){
                result = (String)value;
            }else{
                result = "json:" + JSONUtil.toJsonString(value);
            }
            if(result != null){
                cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), result);
            }
        }catch (Exception exp){
            log.error("job failed of java call instruction(" + getInstructionID()+"):" + exp.getMessage());
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "java method execution exception:" + exp.getMessage());
            throw new JobFailException("job failed when call class(" + className +")'s method(" + methodName +")", exp);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Call ").append(className).append("'s method[").append(methodName).append("] with args size:").append(args.length).append("\r\n");
        for(int i=0; i<args.length ; i++){
            sb.append("arg[").append(i).append("]:").append(args[0]).append("\r\n");
        }
        return sb.toString();
    }
}
