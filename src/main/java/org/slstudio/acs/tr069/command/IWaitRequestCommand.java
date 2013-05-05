package org.slstudio.acs.tr069.command;

import org.slstudio.acs.tr069.command.exception.CommandFatalErrorException;
import org.slstudio.acs.tr069.command.exception.CommandNormalErrorException;
import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-5
 * Time: обнГ9:19
 */
public interface IWaitRequestCommand extends ICommand {
    public boolean handleRequest(CommandContext cmdContext, TR069Message request) throws CommandNormalErrorException, CommandFatalErrorException;
}
