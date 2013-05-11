package org.slstudio.acs.web.controller;

/**
 * Created with IntelliJ IDEA.
 * User: chandler
 * Date: 13-5-10
 * Time: ÉÏÎç2:30
 */

import org.slstudio.acs.kernal.session.context.IMessageContext;
import org.slstudio.acs.kernal.session.context.ISessionContext;
import org.slstudio.acs.kernal.session.sessionmanager.ISessionManager;
import org.slstudio.acs.util.JSONUtil;
import org.slstudio.acs.web.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    @Resource(name = "sessionManager")
    private ISessionManager sessionManager= null;

    @RequestMapping(value="/{sessionID}", method = RequestMethod.GET)
    public @ResponseBody
    ISessionContext getSessionContext(@PathVariable String sessionID) {

        ISessionContext session = sessionManager.getSessionContext(sessionID);
        return session;

    }

    @RequestMapping(value="/{sessionID}", method = RequestMethod.DELETE)
    public @ResponseBody
    Result removeSession(@PathVariable String sessionID) {
        sessionManager.removeSessionContext(sessionID);
        return new Result(true, null);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getSessionList(@RequestParam("rows") int rows, @RequestParam("page") int page,
                                      @RequestParam("sort") String sortName, @RequestParam("order") String sortOrder,
                                      @RequestParam(value = "sessionID", required = false) String sessionID) {
        List<ISessionContext> sessions = null;
        if(sessionID==null || sessionID.equals("")){
            sessions = sessionManager.getAllSessionContexts();
        }else{
            sessions = new ArrayList<ISessionContext>();
            ISessionContext s = sessionManager.getSessionContext(sessionID);
            if(s!=null){
                sessions.add(s);
            }
        }
        List<ISessionContext> sortedSessions = sortSessions(sessions, sortName, sortOrder);
        Map<String, Object> result = new HashMap<String, Object>();
        List<ISessionContext> resultSessionList = new ArrayList<ISessionContext>();

        int start = (page - 1) * rows;
        int end = page * rows -1;
        for(int i=0; i<sortedSessions.size(); i++){

            if((i<=end)&&(i>=start)){
                resultSessionList.add(sortedSessions.get(i));
            }
        }
        result.put("total",sortedSessions.size());
        result.put("rows", resultSessionList);
        System.out.println(JSONUtil.toJsonString(result));
        return result;
    }

    @RequestMapping(value="/{sessionID}/messages", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getSessionMessageList(@PathVariable String sessionID) {
        List<IMessageContext> messages = new ArrayList<IMessageContext>();
        if(sessionID != null){
            ISessionContext sessionContext = sessionManager.getSessionContext(sessionID);
            if(sessionContext != null){
                messages.addAll(sessionContext.getMessageContextList());
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();

        result.put("total",messages.size());
        result.put("rows", messages);
        System.out.println(JSONUtil.toJsonString(result));
        return result;
    }
    private List<ISessionContext> sortSessions(List<ISessionContext> allSessions, String sortName, String sortOrder) {
        if(sortOrder.equals("desc")){
            if(sortName.equals("sessionID")){
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return o2.getSessionID().compareTo(o1.getSessionID());
                    }
                });
            }else if(sortName.equals("deviceKey")){
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return o2.getClientID().compareTo(o1.getClientID());
                    }
                });
            }else if(sortName.equals("lastUpdateTime")){
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return new Long(o2.getTimestamp()).compareTo(new Long(o1.getTimestamp()));
                    }
                });
            }else{
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return o2.getSessionID().compareTo(o1.getSessionID());
                    }
                });
            }
        } else{
            if(sortName.equals("sessionID")){
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {

                        return o1.getSessionID().compareTo(o2.getSessionID());
                    }
                });
            }else if(sortName.equals("deviceKey")){
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return o1.getClientID().compareTo(o2.getClientID());
                    }
                });
            }else if(sortName.equals("lastUpdateTime")){
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return new Long(o1.getTimestamp()).compareTo(new Long(o2.getTimestamp()));
                    }
                });
            }else{
                Collections.sort(allSessions, new Comparator<ISessionContext>() {
                    public int compare(ISessionContext o1, ISessionContext o2) {
                        return o1.getSessionID().compareTo(o2.getSessionID());
                    }
                });
            }
        }


        return allSessions;
    }
}
