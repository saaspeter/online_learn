<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonToolWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="systemlogForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="commonTool.page.biz.systemlog_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/biz/listSystemlog.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="systemlogForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="systemlogForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="commonTool.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="commonTool.page.biz.systemlog.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="systemlogForm" property="queryVO.{[#searchColumn#]}"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="commonTool.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="commonTool.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>Property one</td>
              <td>Property two</td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button onclick="addRecord('/biz/addSystemlog.do');"><bean:message key="commonTool.commonpage.add"/></button></li>
			 <li><button onclick="delRec('list','keys','/biz/deleteSystemlog.do');"><bean:message key="commonTool.commonpage.delete"/></button></li>
			 
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
        <td><bean:message key="commonTool.page.{[#folder#]}.systemlog.logcode"/></td>

        <td><bean:message key="commonTool.page.{[#folder#]}.systemlog.createtime"/></td>

        <td><bean:message key="commonTool.page.{[#folder#]}.systemlog.endtime"/></td>

        <td><bean:message key="commonTool.page.{[#folder#]}.systemlog.result"/></td>

        <td><bean:message key="commonTool.page.{[#folder#]}.systemlog.content"/></td>


        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="logid"/>' value="<bean:write name="vo" property="logid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/biz/viewSystemlog.do?queryVO.logid=<bean:write name="vo" property="logid"/>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <input id='pkId<bean:write name="vo" property="logid"/>Name' type="hidden" value="<bean:write name="vo" property="{[#searchColumn#]}"/>"/>
        <td><bean:write name="vo" property="logcode"/></td>

        <td><bean:write name="vo" property="createtime"/></td>

        <td><bean:write name="vo" property="endtime"/></td>

        <td><bean:write name="vo" property="result"/></td>

        <td><bean:write name="vo" property="content"/></td>


        <td><a href="#" onclick="goUrl('/biz/editSystemlog.do?queryVO.logid=<bean:write name="vo" property="logid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
        </td>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="systemlogForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
