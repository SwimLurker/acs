package org.slstudio.acs.web.controller;

import org.slstudio.acs.web.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-24
 * Time: ÉÏÎç11:16
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @RequestMapping(value="/", method = RequestMethod.GET)
    public @ResponseBody
    Result getLogs(@RequestParam("type") String logType) {
        try{
            InputStream is = new FileInputStream(logType+".log");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int readnum = -1;
            while((readnum = is.read(buf, 0, 1024))!=-1){
                baos.write(buf, 0, readnum);
            }
            is.close();
            String result = baos.toString();
            baos.close();
            return new Result(true,result);
        }catch (Exception exp){
            return new Result(false,exp.getMessage());
        }
    }
}