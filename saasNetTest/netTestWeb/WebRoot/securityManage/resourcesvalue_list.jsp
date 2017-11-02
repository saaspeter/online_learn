<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>资源列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listResourcesvalue.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesvalueForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesvalueForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title=""><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">资源名称: 
		<input type="text" name="queryVO.name" value='<bean:write name="resourcesvalueForm" property="queryVO.name"/>'/>
		<input type="submit" name="Submit" value='<bean:message key="platform.commonpage.query" bundle="platform.pagemess"/>' />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="platform.commonpage.complexQuery" bundle="platform.pagemess"/></a>
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
			 <li><button type="button" onclick="addRecord('/securityManage/addResourcesvalue.do?vo.id=<bean:write name="resourcesvalueForm" property="queryVO.id"/>');return false;"><bean:message key="platform.commonpage.add" bundle="platform.pagemess"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/securityManage/editResourcesvalue.do?queryVO.rescvalueid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.modify" bundle="platform.pagemess"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleteResourcesvalue.do','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete" bundle="platform.pagemess"/></button></li>
			 
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
        <td>资源名称</td>
        <td>语言地区</td>
        <td>资源描述</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="rescvalueid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td>
           <a href="javascript:newDiv('/securityManage/viewResourcesvalue.do?queryVO.rescvalueid=<bean:write name="vo" property="rescvalueid"/>',0,1,600,400);">
              <bean:write name="vo" property="name"/>
           </a>
        </td>
        <td><bean:writeSaas name="vo" property="localeid" showLocaleName="true"/></td>
        <td><bean:write name="vo" property="descn"/></td>

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
		    changeComplexSearch("complexSearchDiv","<bean:write name="resourcesvalueForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
