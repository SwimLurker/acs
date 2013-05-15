package org.slstudio.acs.tr069.script;

import org.slstudio.acs.tr069.exception.ParseScriptException;
import org.slstudio.acs.tr069.instruction.IInstruction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ионГ2:46
 */
public interface IScriptParser {
    public List<IInstruction> parse(String scriptText) throws ParseScriptException;
}
