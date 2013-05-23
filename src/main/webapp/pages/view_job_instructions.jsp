<%--
  Created by IntelliJ IDEA.
  User: chandler
  Date: 13-5-11
  Time: 下午10:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="dg_instruction-<%=request.getParameter("jobID")%>" class="easyui-datagrid" style="height:auto;padding: 10px;"
       fit="true" border="true">
</table>
<script>
    $(function(){
        $('#dg_instruction-<%=request.getParameter("jobID")%>').datagrid({
            singleSelect:true,
            url:'/webconsole/rest/jobs/<%=request.getParameter("jobID")%>/instructions',
            method:'GET',
            columns:[[
                {field:'instructionID',title:'ID',width:100},
                {field:'instructionName',title:'Name',width:200},
                {field:'content',title:'Content',width:600,formatter: function(value,row,index){return '<textarea cols=\'80\' rows=\'2\' readonly>'+value+'</textarea>' } }
            ]]
        });
    });
</script>