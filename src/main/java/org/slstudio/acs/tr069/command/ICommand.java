package org.slstudio.acs.tr069.command;

import org.slstudio.acs.tr069.command.exception.CommandFatalErrorException;
import org.slstudio.acs.tr069.command.exception.CommandNormalErrorException;
import org.slstudio.acs.tr069.job.request.IJobRequest;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-4
 * Time: ионГ1:57
 */
public interface ICommand {
    public String getCommandID();
    public IJobRequest execute(CommandContext cmdContext) throws CommandNormalErrorException, CommandFatalErrorException;
}
