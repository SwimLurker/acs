package org.slstudio.acs.tr069.command;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ1:57
 */
public interface ICommandQueue {
    public ICommand pop();
    public void push(ICommand command);
}
