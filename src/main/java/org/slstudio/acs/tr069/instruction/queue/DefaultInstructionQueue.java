package org.slstudio.acs.tr069.instruction.queue;

import org.slstudio.acs.tr069.instruction.IInstruction;

import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ÉÏÎç1:57
 */
public class DefaultInstructionQueue implements IInstructionQueue {
    private Vector<IInstruction> queue = new Vector<IInstruction>();
    public void push(IInstruction instruction){
        queue.add(instruction);
    }

    public List<IInstruction> getAllInstructions() {
        return queue;
    }

    public IInstruction pop(){
        if(queue.size()>0){
            return queue.remove(0);
        }
        return null;
    }
    public void remove(IInstruction instruction){
        queue.remove(instruction);
    }

    public IInstruction find(String instructionID){
        for(IInstruction instruction: queue){
            if(instruction.getInstructionID().equals(instructionID)){
                return instruction;
            }
        }
        return null;
    }
}
