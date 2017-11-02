<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="{[#Project#]}Web.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="{[#actionName#]}Form" property="jsSuffix" type="java.lang.String"/>
    <title><bean:message key="{[#Project#]}.page.{[#folder#]}.{[#jspName#]}_list.jsp.title"/></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/{[#folder#]}/list{[#className#]}.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="{[#actionName#]}Form" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="{[#actionName#]}Form" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="{[#Project#]}.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="{[#Project#]}.page.{[#folder#]}.{[#actionName#]}.{[#searchColumn#]}"/>: 
		<input type="text" name="queryVO.{[#searchColumn#]}" value="<bean:write name="{[#actionName#]}Form" property="queryVO.{[#searchColumn#]}"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="{[#Project#]}.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="{[#Project#]}.commonpage.complexQuery"/></a>
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
			 <li><button onclick="addRecord('/{[#folder#]}/add{[#className#]}.do');"><bean:message key="{[#Project#]}.commonpage.add"/></button></li>
			 <li><button onclick="delRec('list','keys','/{[#folder#]}/delete{[#className#]}.do');"><bean:message key="{[#Project#]}.commonpage.delete"/></button></li>
			 
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
{[#listColumnNamesDefines#]}
        <td>操作</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="{[#pkName#]}"/>' value="<bean:write name="vo" property="{[#pkName#]}"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <a href="javascript:newDiv('/{[#folder#]}/view{[#className#]}.do?queryVO.{[#pkName#]}=<bean:write name="vo" property="{[#pkName#]}"/>',0,1,600,400);"><bean:write name="vo" property="{[#searchColumn#]}"/></a>
        <input id='pkId<bean:write name="vo" property="{[#pkName#]}"/>Name' type="hidden" value="<bean:write name="vo" property="{[#searchColumn#]}"/>"/>
{[#listColumnsDefines#]}
        <td><a href="#" onclick="goUrl('/{[#folder#]}/edit{[#className#]}.do?queryVO.{[#pkName#]}=<bean:write name="vo" property="{[#pkName#]}"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.modify"/></a>
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
		    changeComplexSearch("complexSearchDiv","<bean:write name="{[#actionName#]}Form" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
