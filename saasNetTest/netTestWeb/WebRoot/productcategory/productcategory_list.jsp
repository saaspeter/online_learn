<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript">
        function freashCateTree(){
	        parent.frames[0].location.reload();
        }
     
    </script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/productcategory/listproductcategory.do" method="post">
  
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productcategoryForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productcategoryForm" property="backUrlEncode"/>">
  
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input id="queryVO.pid" type="hidden" name="queryVO.pid" value="<bean:write name="productcategoryForm" property="queryVO.pid"/>">
  <input id="queryVO.categorylevel" type="hidden" name="queryVO.categorylevel" value="<bean:write name="productcategoryForm" property="queryVO.categorylevel"/>">
  
  <div id="firstLineDiv">
      
      <div id="searchDiv"><bean:message key="platform.page.productcategory.productcategory.categoryname" bundle="platform.pagemess"/>: 
		<input type="text" name="queryVO.categoryname" value="<bean:write name="productcategoryForm" property="queryVO.categoryname"/>"/>
		包含子数据:
        <input type="checkbox" name="searchType" value="<%=String.valueOf(CommonConstant.SearchType_Below)%>" <logic:equal name="productcategoryForm" property="searchType" value="<%=String.valueOf(CommonConstant.SearchType_Below)%>">checked="checked"</logic:equal> />

		<input type="submit" name="Submit"  value='<bean:message key="platform.commonpage.query" bundle="platform.pagemess"/>' />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/productcategory/addproductcategory.do?vo.pid=<bean:write name="productcategoryForm" property="queryVO.pid"/>');return false;"><bean:message key="platform.commonpage.add" bundle="platform.pagemess"/></button></li>
			 <li><button type="button" onclick="freashCateTree();">刷新目录树</button></li>
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
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>id</td>
        <td><bean:message key="platform.page.productcategory.productcategory.categoryname" bundle="platform.pagemess"/></td>
        <td><bean:message key="platform.page.productcategory.productcategory.categorylevel" bundle="platform.pagemess"/></td>
        <td><bean:message key="platform.page.productcategory.productcategory.disorder" bundle="platform.pagemess"/></td>
        <td>状态</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="categoryid"/>" ></td>
        <td><bean:write name="vo" property="categoryid"/></td>
        <td><a href="javascript:void(0)" onclick="newDiv('/productcategory/viewproductcategory.do?queryVO.categoryid=<bean:write name="vo" property="categoryid"/>',0,1,500,350);"><bean:write name="vo" property="categoryname"/></a></td>
        <td><bean:write name="vo" property="categorylevel"/></td>
        <td><bean:write name="vo" property="disOrder"/></td>
        <td><bean:writeSaas name="vo" property="status" consCode="common.const.commonstatus"/></td>
        <td>
            <img src="../styles/imgs/edit.png" title="<bean:message key="platform.commonpage.modify" bundle="platform.pagemess"/>" style="cursor:pointer;" onclick="goUrl('/productcategory/editproductcategory.do?queryVO.categoryid=<bean:write name="vo" property="categoryid"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
	        <img src="../styles/imgs/delete.png" title="<bean:message key="platform.commonpage.delete" bundle="platform.pagemess"/>" style="cursor:pointer;" onclick="deleteSingleRec('/productcategory/delproductcategory.do?vo.categoryid=<bean:write name="vo" property="categoryid"/>');return false;"/>
        </td>
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
