package org.slstudio.acs.tr069.instruction;

import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ионГ12:52
 */
public interface ITR069Instruction extends IInstruction{
    public TR069Message getTR069Message();
}
