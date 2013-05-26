package org.slstudio.acs.tr069.script;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.exception.ParseScriptException;
import org.slstudio.acs.tr069.instruction.*;
import org.slstudio.acs.tr069.instruction.checkrule.ITR069MessageCheckRule;
import org.slstudio.acs.tr069.instruction.checkrule.TR069MessageCheckRuleFactory;
import org.slstudio.acs.tr069.instruction.extension.IInstructionExtension;
import org.slstudio.acs.tr069.instruction.extension.InstructionExtensionFactory;
import org.slstudio.acs.util.BeanLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ÉÏÎç3:31
 */
public class SimpleScriptParser implements IScriptParser {

    private int currentInstructionID = 0;
    private String jobID = null;

    public IScriptParser newInstance() {
        return new SimpleScriptParser();
    }

    public int getCurrentInstructionID() {
        return currentInstructionID;
    }

    public void setCurrentInstructionID(int currentInstructionID) {
        this.currentInstructionID = currentInstructionID;
    }

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public List<IInstruction> parse(String scriptText, Map<String, Object> context) throws ParseScriptException{
        setJobID((String)context.get("jobID"));
        setCurrentInstructionID(1);

        List<IInstruction> result = new ArrayList<IInstruction>();
        String[] instructionTextList = parseLines(scriptText);
        for(String instructionText: instructionTextList){
            List<IInstruction> instructions = parseInstructions(instructionText);
            result.addAll(instructions);
        }
        return result;
    }



    private String[] parseLines(String text){
        return text.split("\r\n", 0);
    }

    private List<IInstruction> parseInstructions(String instructionText) throws ParseScriptException{

        //first trim begin and end blank
        instructionText = instructionText.trim();
        //get first word for instruction type
        String r[] = instructionText.split(" ", 2);
        String type = r[0];
        String args = null;
        if(r.length>=2){
            args = r[1].trim();
        }
        if(InstructionConstants.INSTRUCTION_TYPE_RETURN.equalsIgnoreCase(type) || InstructionConstants.INSTRUCTION_TYPE_ASSIGN.equalsIgnoreCase(type)){
            return constructComplexInstructions(type, args);
        }else{
            List<IInstruction> result = new ArrayList<IInstruction>();
            result.add(constructSingleInstruction(type, args));
            return result;
        }
    }

    private List<IInstruction> constructComplexInstructions(String type, String args) throws ParseScriptException{
        if(InstructionConstants.INSTRUCTION_TYPE_RETURN.equalsIgnoreCase(type)){
            return constructReturnInstructions(args);
        }else if(InstructionConstants.INSTRUCTION_TYPE_ASSIGN.equalsIgnoreCase(type)){
            return constructAssignInstructions(args);
        }else{
            throw new ParseScriptException("Unknown Instruction");
        }
    }

    private IInstruction constructSingleInstruction(String type, String args) throws ParseScriptException{
        if(InstructionConstants.INSTRUCTION_TYPE_TR069.equalsIgnoreCase(type)){
            return constructTR069Instruction(args);
        }else if(InstructionConstants.INSTRUCTION_TYPE_EXEC.equalsIgnoreCase(type)){
            return constructExecuteInstruction(args);
        }else{
            throw new ParseScriptException("Unknown Instruction");
        }
    }

    private List<IInstruction> constructReturnInstructions(String valueText) throws ParseScriptException{
        List<IInstruction> result = new ArrayList<IInstruction>();
        // retrive return value(can be constant--json format or variable, or another instruction which enclosed by "()": such as ["some text"] or $B or (TR069 setpv ...)
        if(valueText == null){
            result.add(new ReturnInstruction(Integer.toString(currentInstructionID), valueText));
            currentInstructionID++;
        }else if(valueText.startsWith(InstructionConstants.SYMBOLNAME_SUBINSTRUCTION_PREFIX) && valueText.endsWith(InstructionConstants.SYMBOLNAME_SUBINSTRUCTION_POSTFIX)){
            String r[] = valueText.split(" ", 2);
            String type = r[0];
            String args = r[1].trim();
            result.add(constructSingleInstruction(type, args));
            result.add(new ReturnInstruction(Integer.toString(currentInstructionID), constructInstructionResultReference(currentInstructionID-1)));
            currentInstructionID++;
        }else{
            result.add(new ReturnInstruction(Integer.toString(currentInstructionID), valueText));
            currentInstructionID++;
        }
        return result;
    }

    private String constructInstructionResultReference(int instructionID) {
        return InstructionConstants.SYMBOLNAME_VARIABLEREF_PREFIX +
                InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX +
                Integer.toString(instructionID) +
                InstructionConstants.SYMBOLNAME_VARIABLEREF_POSTFIX;
    }

    private List<IInstruction> constructAssignInstructions(String instructionText) throws ParseScriptException{
        // retrive assign left value(variable, start from $) and right value(can be constant--json format or variable, or another instruction which enclosed by "()": such as $A = ["some text"] or $A = $B or $A = (TR069 setpv ...)
        List<IInstruction> result = new ArrayList<IInstruction>();
        String[] s = instructionText.split("=", 0);
        String leftText = s[0].trim();
        String rightText = s[1].trim();
        if(!leftText.startsWith(InstructionConstants.SYMBOLNAME_VARIABLE_PREFIX)){
            throw new ParseScriptException("assign instruction left value should be variable which begin with '$'");
        }
        if(rightText.startsWith(InstructionConstants.SYMBOLNAME_SUBINSTRUCTION_PREFIX) && rightText.endsWith(InstructionConstants.SYMBOLNAME_SUBINSTRUCTION_POSTFIX)){
            String subInstructionText = rightText.substring(1,rightText.length()-1);
            String r[] = subInstructionText.split(" ", 2);
            String type = r[0];
            String args = r[1].trim();
            result.add(constructSingleInstruction(type, args));
            result.add(new AssignInstruction(Integer.toString(currentInstructionID),leftText, InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + Integer.toString(currentInstructionID-1)));
            currentInstructionID++;
        }else{
            result.add(new AssignInstruction(Integer.toString(currentInstructionID), leftText, rightText));
            currentInstructionID++;
        }
        return result;
    }

    private IInstruction constructTR069Instruction(String instructionText) throws ParseScriptException{
        String r[] = instructionText.split(" ", 2);
        String type = r[0];
        String args = r[1].trim();
        if(InstructionConstants.INSTRUCTION_SUBTYPE_TR069_CMD.equalsIgnoreCase(type)){
            return constructTR069CommandInstruction(args);
        }else if(InstructionConstants.INSTRUCTION_SUBTYPE_TR069_CHECK.equalsIgnoreCase(type)){
            return constructTR069CheckInstruction(args);
        }else{
            throw new ParseScriptException("Unknown tr069 Instruction");
        }
    }

    private IInstruction constructTR069CheckInstruction(String instructionText) throws ParseScriptException{
        TR069CheckInstruction checkInstruction = new TR069CheckInstruction(Integer.toString(currentInstructionID));
        currentInstructionID++;
        List<ITR069MessageCheckRule> checkRules = TR069MessageCheckRuleFactory.getCheckRuleList(instructionText);
        checkInstruction.setCheckRuleList(checkRules);
        checkInstruction.setSkipCheckCount(0);
        return checkInstruction;
    }

    private IInstruction constructTR069CommandInstruction(String instructionText) throws ParseScriptException{
        String messageID = jobID+"_"+Integer.toString(currentInstructionID);
        TR069CommandInstruction  commandInstruction = new TR069CommandInstruction(Integer.toString(currentInstructionID), messageID, instructionText);
        currentInstructionID++;
        return commandInstruction;
    }

    private IInstruction constructExecuteInstruction(String instructionText) throws ParseScriptException{
        String r[] = instructionText.split(" ", 2);
        String type = r[0];
        String args = r[1].trim();
        IInstructionExtension extension = ((InstructionExtensionFactory)BeanLocator.getBean("instructionExtensionFactory")).getExtension(type);
        if(extension == null){
            throw new ParseScriptException("Unknown exection Instruction");
        }
        IInstruction result = null;
        try {
            result = extension.createInstruction(args, currentInstructionID);
        } catch (InstructionException e) {
            e.printStackTrace();
            throw new ParseScriptException("uncorrect instruction format", e);
        }
        currentInstructionID++;
        return result;
    }

    public static void main(String[] args) throws ParseScriptException {
        StringBuilder text = new StringBuilder();
        text.append("SET $a = \"bbb\"").append("\r\n").append("SET $c = {$a}").append("\r\n").
                append("tr069 cmd getpv:{\"messageID\":\"1_1235\",\"parameterNames\":[\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\"]}").append("\r\n").append("RET");
        SimpleScriptParser parser  = new SimpleScriptParser();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jobID", "1111");
        List<IInstruction> ints = parser.parse(text.toString(), map);
        for(IInstruction in : ints){
            System.out.println(in.getInstructionID());
        }
    }

}
