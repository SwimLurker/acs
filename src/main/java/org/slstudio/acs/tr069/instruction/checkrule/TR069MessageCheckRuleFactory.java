package org.slstudio.acs.tr069.instruction.checkrule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-15
 * Time: ÉÏÎç1:50
 */
public class TR069MessageCheckRuleFactory {
    public static List<ITR069MessageCheckRule> getCheckRuleList(String instructionText) {
        List<ITR069MessageCheckRule> result = new ArrayList<ITR069MessageCheckRule>();
        String[] ruleTexts = instructionText.split(",", 0);
        for(String ruleText : ruleTexts){
            String[] s = ruleText.split(":", 0);
            if(s[0].equalsIgnoreCase("type")){
                result.add(new MessageTypeCheckRule(s[1]));
            }else if(s[0].equalsIgnoreCase("property")){
                String s2[] = s[1].split("=");
                result.add(new MessagePropertyCheckRule(s2[0].trim(),s2[1].trim()));
            }
        }
        return result;
    }
}
