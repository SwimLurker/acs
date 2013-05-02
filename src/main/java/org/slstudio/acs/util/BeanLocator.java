package org.slstudio.acs.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-1
 * Time: ионГ2:38
 */
public class BeanLocator {
    private static ApplicationContext context = null;
    static {
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
}
