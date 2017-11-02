<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userprodstatuslogForm" property="jsSuffix" type="java.lang.String"/>
    <title>学员产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
      
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  
  <div id="firstLineDiv">
      <div id="searchDiv">
          
      </div>
  </div>
  
  
  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td>发生时间</td>
        <td>状态变化</td>
        <td>操作人员</td>
        <td>说明</td>
      </tr>
      </thead>
	  <logic:present name="userprodstatuslogForm" property="datalist">
	  <logic:iterate id="vo" name="userprodstatuslogForm" property="datalist" indexId="ind" type="netTest.product.vo.Userprodstatuslog">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td>
            <bean:write name="vo" property="happendate" format="yyyy-MM-dd HH:mm"/>
        </td>
        <td><logic:notEqual name="vo" property="bfstatus" value="-1">
                <bean:writeSaas name="vo" property="bfstatus" consCode="UserProduct.status"/>
            </logic:notEqual>
            ---&gt;
            <%if(UserproductConstant.StatusRank_Suspend_serviceEnd.equals(vo.getAfstatusrank())){ %>
            <bean:writeSaas name="vo" property="afstatusrank" consCode="netTest.UserProduct.statusrank"/>
            <%}else { %>
            <bean:writeSaas name="vo" property="afstatus" consCode="UserProduct.status"/>
            <%} %>
        </td>
        <td><bean:write name="vo" property="opertorDisplayname"/></td>
        <td><bean:write name="vo" property="statusdisc"/></td>
        
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  </div>
  </body>
</html:html>
