package org.slstudio.acs.tr069.instruction.checkrule;

import org.slstudio.acs.tr069.databinding.TR069Message;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-14
 * Time: ионГ3:41
 */
public interface ITR069MessageCheckRule {
    public boolean check(TR069Message request);
}
