package org.slstudio.acs.tr069.instruction;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ1:57
 */
public interface IInstructionQueue {
    public IInstruction pop();
    public void push(IInstruction command);
}
