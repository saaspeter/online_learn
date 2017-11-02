<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.ProductConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>培训科目产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type="text/javascript">
	    function editContCate(id, name){
           var url = "#/<%=CommonConstant.WebContext_Education %>/prodcont/contentcate_main.jsp?productbaseid="+id;
           newDiv(url,1,1,600,400);
	    }

	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listproduct.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productForm" property="backUrlEncode"/>">
  <input id="queryVO.categoryid" type="hidden" name="queryVO.categoryid" value="<bean:write name="productForm" property="queryVO.categoryid"/>">
  <input type="hidden" name="queryVO.shopid" value="<bean:write name="productForm" property="queryVO.shopid"/>">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">产品名称: 
		<input type="text" name="queryVO.productname" value="<bean:write name="productForm" property="queryVO.productname"/>"/>
		&nbsp;&nbsp;
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/product/addproduct.do?vo.shopid=<bean:write name="productForm" property="queryVO.shopid"/>&vo.categoryid=<bean:write name="productForm" property="queryVO.categoryid"/>');return false;"><bean:message key="netTest.commonpage.add"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/product/delproduct.do','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
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
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        <td>产品名称</td>
        <td>产品价格</td>   
        <td>状态</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" id="pkId<%=idx %>" name="keys" value="<bean:write name="vo" property="productbaseid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td><a href="javascript:newDiv('/product/viewproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>',0,1,600,400);"><bean:write name="vo" property="productname"/></a>
            &nbsp;<img src="../styles/imgs/detail.png" title="课程章节设置" style="cursor:pointer;" onclick="editContCate('<bean:write name="vo" property="productbaseid"/>');return false;"/>&nbsp;
            <input id="pkId<%=idx %>Name" type="hidden" value="<bean:write name="vo" property="productname"/>"/>
        </td>

        <td><bean:write name="vo" property="productprice"/>&nbsp;(<bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/>)</td>

        <td><bean:write name="vo" property="status"/></td>
		<td>
		    <img src="../styles/imgs/edit.png" alt="编辑" style="cursor:pointer;" onclick="goUrl('/product/editproduct.do?queryVO.productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
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
