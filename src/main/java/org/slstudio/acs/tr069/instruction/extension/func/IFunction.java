package org.slstudio.acs.tr069.instruction.extension.func;

import org.slstudio.acs.tr069.exception.InstructionException;
import org.slstudio.acs.tr069.instruction.context.InstructionContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-25
 * Time: ионГ3:26
 */
public interface IFunction {
    public void setArgs(String [] args) throws InstructionException;
    public String execute(InstructionContext context) throws InstructionException;
    public String getName();
    public IFunction newInstance();
}
