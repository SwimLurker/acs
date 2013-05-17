package org.slstudio.acs.web.controller;

import org.slstudio.acs.hms.exception.MessagingException;
import org.slstudio.acs.hms.messaging.sender.IMessageSender;
import org.slstudio.acs.util.BeanLocator;
import org.slstudio.acs.web.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-17
 * Time: ÉÏÎç4:05
 */

@Controller
@RequestMapping("/messaging")
public class MessagingController {

    @RequestMapping(value="/sendmessage", method = RequestMethod.POST)
    public @ResponseBody
    Result sendMessage(@RequestParam("target") String target, @RequestParam("message") String message) {

        System.out.println("send to:"+ target +" message:" + message);
        IMessageSender sender= null;
        try{
            sender = (IMessageSender)BeanLocator.getBean(target +"Sender");
        }catch (Exception exp){
            exp.printStackTrace();
            return new Result(false, "unknown message sender");
        }
        if(sender==null){
            return new Result(false, "unknown message sender");
        }
        try {
            sender.sendMessage(message);
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return  new Result(false, e.getMessage());
        }
        return new Result(true, null);
    }
}
