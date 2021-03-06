package org.slstudio.acs.tr069.instruction.queue;

import org.slstudio.acs.tr069.instruction.IInstruction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ����1:57
 */
public interface IInstructionQueue {
    public IInstruction pop();
    public void push(IInstruction command);
    public List<IInstruction> getAllInstructions();
}
