package org.slstudio.acs.tr069.instruction.extension.sql;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.IInstruction;
import org.slstudio.acs.tr069.instruction.extension.IInstructionExtension;
import org.slstudio.acs.util.Tuple;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-28
 * Time: ÉÏÎç1:47
 */
public class SQLInstructionExtension implements IInstructionExtension {
    private JdbcTemplate template =  null;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public IInstruction createInstruction(String instructionString, int instructionID) throws InstructionException {
        Tuple.Tuple2<String,String[]> sqlInfo = parseSqlStructure(instructionString);
        if(sqlInfo == null){
            throw new InstructionException("unknown sql expression");
        }
        String sql = sqlInfo._1();
        String[] args = sqlInfo._2();

        SQLInstruction sqlInstruction = new SQLInstruction(Integer.toString(instructionID));
        sqlInstruction.setSql(sql);
        sqlInstruction.setSqlArgs(args);
        sqlInstruction.setTemplate(template);
        return sqlInstruction;
    }

    private Tuple.Tuple2<String, String[]> parseSqlStructure(String sqlExpression) throws InstructionException {
        StringTokenizer st = new StringTokenizer(sqlExpression, ";");
        int count = st.countTokens();
        if(count<1){
            throw new InstructionException("invalid sql expression");
        }
        String sql = st.nextToken();
        String[] args = new String[count-1];
        for(int i=0; i<count-1; i++){
            args[i] = st.nextToken();
        }
        return Tuple.tuple(sql, args);
    }

}
