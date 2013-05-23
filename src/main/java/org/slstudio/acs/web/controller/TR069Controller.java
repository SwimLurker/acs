package org.slstudio.acs.web.controller;

import org.slstudio.acs.exception.ACSException;
import org.slstudio.acs.kernal.endpoint.IProtocolEndPoint;
import org.slstudio.acs.kernal.engine.IProtocolEngine;
import org.slstudio.acs.tr069.endpoint.stream.StreamEndPoint;
import org.slstudio.acs.util.BeanLocator;
import org.slstudio.acs.web.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-18
 * Time: ÉÏÎç12:26
 */
@Controller
@RequestMapping("/tr069")
public class TR069Controller {
    private static Map<String, IProtocolEngine> engineMap = new HashMap<String, IProtocolEngine>();
    static {
        engineMap.put("tr069", (IProtocolEngine) BeanLocator.getBean("tr069Engine"));
        engineMap.put("tr069am1", (IProtocolEngine) BeanLocator.getBean("tr069AM1Engine"));
        engineMap.put("tr069am2", (IProtocolEngine) BeanLocator.getBean("tr069AM2Engine"));
        engineMap.put("tr069am3", (IProtocolEngine) BeanLocator.getBean("tr069AM3Engine"));
        engineMap.put("tr069am4", (IProtocolEngine) BeanLocator.getBean("tr069AM4Engine"));
        engineMap.put("test", (IProtocolEngine) BeanLocator.getBean("tr069TestEngine"));
    }

    @RequestMapping(value="/sendmessage", method = RequestMethod.POST)
    public @ResponseBody
    Result sendTRMessage(@RequestParam("engine") String engineStr, @RequestParam("trmessage") String message, @RequestParam("propertystring") String propertyString) {
        System.out.println("here");
        IProtocolEngine engine = engineMap.get(engineStr);
        if(engine == null){
            return new Result(false, "unknown protocol version");
        }

        InputStream propertiesIS = new ByteArrayInputStream(propertyString.getBytes());
        InputStream requestIS = new ByteArrayInputStream(message.getBytes());
        OutputStream responseOS = new ByteArrayOutputStream();

        try{
            IProtocolEndPoint endPoint= new StreamEndPoint(propertiesIS, requestIS, responseOS);
            engine.service(endPoint);
        }catch(ACSException exp){
            exp.printStackTrace();
            return new Result(false, exp.getMessage());
        }
        return new Result(true, responseOS.toString());
    }
}
