package org.slstudio.acs.tr069.command;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ1:57
 */
public class DefaultCommandQueue implements ICommandQueue {
    private Vector<ICommand> queue = new Vector<ICommand>();
    public void push(ICommand command){
        queue.add(command);
    }
    public ICommand pop(){
        if(queue.size()>0){
            return queue.remove(0);
        }
        return null;
    }
    public void remove(ICommand command){
        queue.remove(command);
    }

    public ICommand find(String commandID){
        for(ICommand cmd: queue){
            if(cmd.getCommandID().equals(commandID)){
                return cmd;
            }
        }
        return null;
    }
}
