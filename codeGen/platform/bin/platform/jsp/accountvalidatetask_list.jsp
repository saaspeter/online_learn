<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="accountvalidatetaskForm" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="platform.page.user.accountvalidatetask_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/user/listAccountvalidatetask.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="accountvalidatetaskForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="accountvalidatetaskForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="platform.page.user.accountvalidatetask.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="accountvalidatetaskForm" property="queryVO.{[#searchColumn#]}"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="platform.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="platform.commonpage.complexQuery"/></a>
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
			 <li><button onclick="addRecord('/user/addAccountvalidatetask.do');"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button onclick="delRec('list','keys','/user/deleteAccountvalidatetask.do');"><bean:message key="platform.commonpage.delete"/></button></li>
			 
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
        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.userid"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.email"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.validatetype"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.validatecode"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.status"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.createdate"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.aliveenddate"/></td>

        <td><bean:message key="platform.page.{[#folder#]}.accountvalidatetask.activedate"/></td>


        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="id"/>' value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/user/viewAccountvalidatetask.do?queryVO.id=<bean:write name="vo" property="id"/>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <input id='pkId<bean:write name="vo" property="id"/>Name' type="hidden" value="<bean:write name="vo" property="{[#searchColumn#]}"/>"/>
        <td><bean:write name="vo" property="userid"/></td>

        <td><bean:write name="vo" property="email"/></td>

        <td><bean:write name="vo" property="validatetype"/></td>

        <td><bean:write name="vo" property="validatecode"/></td>

        <td><bean:write name="vo" property="status"/></td>

        <td><bean:write name="vo" property="createdate"/></td>

        <td><bean:write name="vo" property="aliveenddate"/></td>

        <td><bean:write name="vo" property="activedate"/></td>


        <td><a href="#" onclick="goUrl('/user/editAccountvalidatetask.do?queryVO.id=<bean:write name="vo" property="id"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="accountvalidatetaskForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
