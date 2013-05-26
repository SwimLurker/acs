package org.slstudio.acs.tr069.instruction;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-22
 * Time: ÏÂÎç10:00
 */
public class InstructionConstants {
    public static final String SYMBOLNAME_VARIABLEREF_PREFIX = "{";
    public static final String SYMBOLNAME_VARIABLEREF_POSTFIX = "}";
    public static final String SYMBOLNAME_ARRAYREF_PREFIX = "[";
    public static final String SYMBOLNAME_ARRAYREF_POSTFIX = "]";
    public static final String SYMBOLNAME_SUBINSTRUCTION_PREFIX = "(";
    public static final String SYMBOLNAME_SUBINSTRUCTION_POSTFIX = ")";
    public static final String SYMBOLNAME_ACCESSOPER = ".";
    public static final String SYMBOLNAME_VARIABLE_PREFIX = "$";
    public static final String SYMBOLNAME_INSTRUCTION_RESULT_PREFIX = "$$_";
    public static final String SYMBOLNAME_RETURNVALUE = "$$_RET";
    public static final String SYMBOLNAME_ERRORCODE = "$$_ERRORCODE";
    public static final String SYMBOLNAME_ERRORMSG = "$$_ERRORMSG";
    public static final String SYMBOLNAME_FUNCARGS_PREFIX = "(";
    public static final String SYMBOLNAME_FUNCARGS_POSTFIX = ")";
    public static final String SYMBOLNAME_FUNCARGS_SEPERATOR = ",";

    public static final String INSTRUCTION_TYPE_RETURN = "RET";
    public static final String INSTRUCTION_TYPE_ASSIGN = "SET";
    public static final String INSTRUCTION_TYPE_TR069 = "TR069";
    public static final String INSTRUCTION_TYPE_EXEC = "EXEC";
    public static final String INSTRUCTION_SUBTYPE_TR069_CMD = "CMD";
    public static final String INSTRUCTION_SUBTYPE_TR069_CHECK = "CHECK";
}
