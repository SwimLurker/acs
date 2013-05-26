package org.slstudio.acs.hms.alarm;

import edu.emory.mathcs.backport.java.util.Collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-26
 * Time: ÏÂÎç2:18
 */
public class AlarmParameterDictionary {
    private Set<String> alarmParameterSet = Collections.synchronizedSet(new HashSet<String>());

    public Set<String> getAlarmParameterSet() {
        return alarmParameterSet;
    }

    public void setAlarmParameterSet(Set<String> alarmParameterSet) {
        this.alarmParameterSet = alarmParameterSet;
    }

    public boolean contains(String parameterName){
        return alarmParameterSet.contains(parameterName);
    }

    public void addAlarmParameters(Collection<String> parameters){
        alarmParameterSet.addAll(parameters);
    }
}
