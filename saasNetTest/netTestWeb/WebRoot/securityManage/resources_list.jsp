<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>权限资源列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listResources.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input type="hidden" name="complexSearchDivStatus" value="">
  <input id="Id_pid" type="hidden" name="queryVO.pid" value="<bean:write name="resourcesForm" property="queryVO.pid"/>">
 
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">资源: 
		<input type="text" name="queryVO.name" value="<bean:write name="resourcesForm" property="queryVO.name"/>"/>
		资源类型:
		<html:select name="resourcesForm" property="queryVO.resType" >
		   <html:option value=""/>
           <html:optionsSaas consCode="common.resources.rescType" />
        </html:select>
          列表类型:
		<html:select name="resourcesForm" property="queryVO.searchListType" >
		   <html:option value="1">仅查询本级别的Resource</html:option>
           <html:option value="2">查询该级别下所有Resource</html:option>
        </html:select>
		<input type="submit" name="Submit" value="查询" />
		
      </div>
  </div>
  <!-- complex Search start -->

  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <authz:privilege res="/securityManage/addResourcePage.do">
			 <li><button type="button" onclick="addRecord('/securityManage/addResourcePage.do?vo.pid=','Id_pid');return false;">新增</button></li>
			 </authz:privilege>
			 <li><button type="button" onclick="modifyRecord('list','keys','/securityManage/editResourcePage.do?vo.id=','请选择一条记录修改','一次只能选择一条记录修改');return false;">修改</button></li>
             <li><button type="button" onclick="deleteRecord('list','keys','/securityManage/deleteResource.do','请选择要删除的记录','确定要删除这些记录吗?');return false;">删除</button></li>
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
        <td>资源id</td>
        <td>资源名</td>
        <td>资源类型</td>
        <td>资源字符串</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="id"/>" onClick="selectInfo(this,'clickedRow')"></td>
        <td><bean:write name="vo" property="id"/></td>
        <td>
        <a href="javascript:newDiv('/securityManage/viewResourcePage.do?vo.id=<bean:write name="vo" property="id"/>',0,1,600,400);">
           <bean:write name="vo" property="name"/>
        </a>
        </td>
        <td><bean:write name="vo" property="resType"/></td>
        <td><bean:write name="vo" property="resString"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </body>
</html:html>
