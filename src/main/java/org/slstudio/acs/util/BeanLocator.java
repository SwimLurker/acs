package org.slstudio.acs.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ÉÏÎç2:38
 */
public class BeanLocator {
    private static final Log log = LogFactory.getLog(BeanLocator.class);
    private static ApplicationContext context = null;
    static {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    public static Object getBean(String beanName){
        try{
            return context.getBean(beanName);
        }catch(Exception exp){
            log.error("get bean failed:", exp);
            return null;
        }
    }
}
