<%--
  Created by IntelliJ IDEA.
  User: chandler
  Date: 13-5-11
  Time: 下午10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="dg_msg" class="easyui-datagrid" style="height:auto;padding: 10px;"
       fit="true" border="true"
       data-options="singleSelect:true,url:'/webconsole/rest/sessions/<%=request.getParameter("sessionID")%>/messages',method:'GET'" >
    <thead>
    <tr width="100%" >
        <th field="request" width="300" data-options="formatter: function(value,row,index){return '<textarea cols=\'50\' rows=\'5\' >'+value+'</textarea>' }">Response</th>
        <th field="response" width="300" data-options="formatter: function(value,row,index){return '<textarea cols=\'50\' rows=\'5\' >'+value+'</textarea>' }">Response</th>
        <th field="lastErrorCode" width = "100" >Last Error Code</th>
    </tr>
    </thead>

</table>