package org.slstudio.acs.tr069.command;

import org.slstudio.acs.tr069.command.exception.CommandFatalErrorException;
import org.slstudio.acs.tr069.command.exception.CommandNormalErrorException;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-5
 * Time: ÉÏÎç11:02
 */
public interface IWaitResponseCommand extends ICommand {
    //return true if the command handled the response
    //return false if the command skip to handle the response
    public boolean handleResponse(CommandContext cmdContext, TR069Message response) throws CommandNormalErrorException, CommandFatalErrorException;
}
