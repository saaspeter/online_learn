<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="quesdifficultForm" property="jsSuffix" type="java.lang.String"/>
    <title>难度设置</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/wareques/listQuesdifficult.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="quesdifficultForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="quesdifficultForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">难度名称: 
		<input type="text" name="queryVO.difficultname" value="<bean:write name="quesdifficultForm" property="queryVO.difficultname"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/wareques/addQuesdifficult.do');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/wareques/editQuesdifficult.do?queryVO.difficultid=');return false;"><bean:message key="netTest.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/wareques/deleteQuesdifficult.do');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
			 
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>难度名称</td>
        <td>难度系数</td>
        <td>状态</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="difficultid"/>" onClick="selectInfo(this,'clickedRow')"></td>      
        <td><bean:write name="vo" property="difficultname"/></td>
        <td><bean:write name="vo" property="difficultvalue"/></td>
        <td><bean:writeSaas name="vo" property="status" consCode="common.const.commonstatus"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
