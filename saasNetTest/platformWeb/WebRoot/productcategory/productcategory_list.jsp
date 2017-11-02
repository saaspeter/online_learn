<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="platform.page.productcategory.productcategory_list.jsp.title"/></title>
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
  <html:form styleId="list" action="/productcategory/productcategory.do?method=list" method="post">
  
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productcategoryForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productcategoryForm" property="backUrlEncode"/>">
  
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input id="queryVO.pid" type="hidden" name="queryVO.pid" value="<bean:write name="productcategoryForm" property="queryVO.pid"/>">
  <input id="queryVO.categorylevel" type="hidden" name="queryVO.categorylevel" value="<bean:write name="productcategoryForm" property="queryVO.categorylevel"/>">
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv"><bean:message key="platform.page.productcategory.productcategory.categoryname"/>: 
		<input type="text" name="queryVO.categoryname" value="<bean:write name="productcategoryForm" property="queryVO.categoryname"/>"/>
		<input type="submit" name="Submit"  value="<bean:message key="platform.commonpage.query"/>" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;"><bean:message key="platform.commonpage.complexQuery"/></a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>
                  目录简述:<input type="text" name="queryVO.categorydesc" exp="\S{0,127}" tip="产品目录简介必须是字符型，且不能超过127个字符;" value="<bean:write name="productcategoryForm" property="queryVO.categorydesc"/>"/>
			  </td>
              <td>
                  包含子数据:<input type="checkbox" name="searchType" value="<%=String.valueOf(CommonConstant.SearchType_Below)%>"
                        <logic:equal name="productcategoryForm" property="searchType" value="<%=String.valueOf(CommonConstant.SearchType_Below)%>">checked="checked"</logic:equal> />
              </td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/productcategory/productcategory.do?method=addPage&vo.pid=<bean:write name="productcategoryForm" property="queryVO.pid"/>');return false;"><bean:message key="platform.commonpage.add"/></button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/productcategory/productcategory.do?method=editPage&editType=<%=WebConstant.editType_edit%>&queryVO.categoryid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg"  bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.modify"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/productcategory/productcategory.do?method=delete','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete"/></button></li>
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
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>id</td>
        <td><bean:message key="platform.page.productcategory.productcategory.categoryname"/></td>
        <td><bean:message key="platform.page.productcategory.productcategory.categorylevel"/></td>
        <td><bean:message key="platform.page.productcategory.productcategory.disorder"/></td>
        <td><bean:message key="platform.page.productcategory.productcategory.categorydesc"/></td>
        <!-- <td><bean:message key="platform.page.productcategory.productcategory_list.jsp.opreation"/></td> -->
        <div id="dataTableColumnPlus"></div>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="categoryid"/>" ></td>
        <td><bean:write name="vo" property="categoryid"/></td>
        <td><a href="javascript:newDiv('/productcategory/productcategory.do?method=editPage&queryVO.categoryid=<bean:write name="vo" property="categoryid"/>&editType=<%=WebConstant.editType_view%>',0,1,500,350);"><bean:write name="vo" property="categoryname"/></a></td>
        <td><bean:write name="vo" property="categorylevel"/></td>
        <td><bean:write name="vo" property="disOrder"/></td>
        <td><bean:write name="vo" property="categorydesc"/></td>
        <!-- <td><a href="#"><bean:message key="platform.page.productcategory.productcategory_list.jsp.toup"/></a>|<a href="#"><bean:message key="platform.page.productcategory.productcategory_list.jsp.todown"/></a></td> -->
        <div id="dataTableColumnDataPlus"></div>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
    
     window.onload=function (){  
        changeComplexSearch("complexSearchDiv","<bean:write name="productcategoryForm" property="complexSearchDivStatus"/>");
        setListTableStyle();
     } 
    
  </script>
  </body>
</html:html>
