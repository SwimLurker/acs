package org.slstudio.acs.tr069.databinding;

import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slstudio.acs.tr069.constant.TR069Constants;
import org.slstudio.acs.tr069.exception.DataBindingException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç3:27
 */
public class ParameterValueStruct implements Serializable {
    private static final Log log = LogFactory.getLog(ParameterValueStruct.class);

    private String name ;
    private Object value ;

    public ParameterValueStruct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}

