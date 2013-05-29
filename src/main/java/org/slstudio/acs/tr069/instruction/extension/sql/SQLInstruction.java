package org.slstudio.acs.tr069.instruction.extension.sql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slstudio.acs.tr069.instruction.InstructionBase;
import org.slstudio.acs.tr069.instruction.InstructionConstants;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;
import org.slstudio.acs.tr069.instruction.exception.InstructionFailException;
import org.slstudio.acs.tr069.instruction.exception.JobCompleteException;
import org.slstudio.acs.tr069.instruction.exception.JobFailException;
import org.slstudio.acs.tr069.job.DeviceJobConstants;
import org.slstudio.acs.tr069.util.InstructionUtil;
import org.slstudio.acs.util.JSONUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-28
 * Time: ÉÏÎç2:10
 */
public class SQLInstruction extends InstructionBase {
    private static final Log log = LogFactory.getLog(SQLInstruction.class);

    private String sql = null;
    private String[] sqlArgs = null;
    private JdbcTemplate template = null;

    protected SQLInstruction(String instructionID) {
        super(instructionID);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String[] getSqlArgs() {
        return sqlArgs;
    }

    public void setSqlArgs(String[] sqlArgs) {
        this.sqlArgs = sqlArgs;
    }

    @JsonIgnore
    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public String getInstructionName() {
        return "SQL";
    }

    public void execute(InstructionContext cmdContext) throws InstructionFailException, JobFailException, JobCompleteException {
        //first populate variable
        try {
            String[] args = new String[sqlArgs.length];
            for(int i=0; i<sqlArgs.length; i++){
                args[i] = InstructionUtil.populateTextWithVariableValue(sqlArgs[i], cmdContext.getSymbolTable());
            }
            if((sql.toUpperCase().startsWith("SELECT"))){
                List sqlResult = executeSQLQuery(template, sql, args);
                if(sqlResult != null){
                    String result = "json:" + JSONUtil.toJsonString(sqlResult);
                    cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), result);
                }
            }else if(sql.toUpperCase().startsWith("INSERT")){
                int sqlResult = executeSQLInsert(template, sql, args);
                cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), Integer.toString(sqlResult));
            }else if(sql.toUpperCase().startsWith("UPDATE")){
                int sqlResult = executeSQLUpdate(template, sql, args);
                cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), Integer.toString(sqlResult));
            }else if(sql.toUpperCase().startsWith("DELETE")){
                int sqlResult = executeSQLDrop(template, sql, args);
                cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_INSTRUCTION_RESULT_PREFIX + getInstructionID(), Integer.toString(sqlResult));
            }else if(sql.toUpperCase().startsWith("CALL")){
                String newSQL = InstructionUtil.populateTextWithVariableValue(sql, cmdContext.getSymbolTable());
                executeSQLProcedure(template, newSQL);
            }else {
                throw new Exception("unsupport sql command");
            }

        } catch (Exception exp) {
            log.error("job failed of sql instruction(" + getInstructionID() + "):" + exp.getMessage());
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORCODE, DeviceJobConstants.ERRORCODE_INSTRUCTIONEXECUTIONEXCEPTION);
            cmdContext.getSymbolTable().put(InstructionConstants.SYMBOLNAME_ERRORMSG, "sql execution exception:" + exp.getMessage());
            throw new JobFailException("job failed when call execute sql command(" + sql +")", exp);
        }


    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Execute SQL:").append(sql).append(" with arguments size:").append(sqlArgs.length).append("\r\n");
        for(int i=0; i<sqlArgs.length ; i++){
            sb.append("arg[").append(i).append("]:").append(sqlArgs[i]).append("\r\n");
        }
        return sb.toString();
    }

    private List executeSQLQuery(JdbcTemplate template, String sql, String[] sqlArgs){
       return template.queryForList(sql, sqlArgs);
    }

    private int executeSQLInsert(JdbcTemplate template, String sql, String[] sqlArgs){
       return template.update(sql, sqlArgs);
    }

    private int executeSQLUpdate(JdbcTemplate template, String sql, String[] sqlArgs){
        return template.update(sql, sqlArgs);
    }

    private int executeSQLDrop(JdbcTemplate template, String sql, String[] sqlArgs){
        return template.update(sql, sqlArgs);
    }

    private void executeSQLProcedure(JdbcTemplate template, String sql){
        template.execute(sql);
    }
}
