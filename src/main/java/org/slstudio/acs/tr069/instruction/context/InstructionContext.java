package org.slstudio.acs.tr069.instruction.context;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ2:09
 */
public class InstructionContext {
    private Map<String, Object> symbolTable = null;

    public InstructionContext(Map<String, Object> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public Map<String, Object> getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(Map<String, Object> symbolTable) {
        this.symbolTable = symbolTable;
    }
}
