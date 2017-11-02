<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.ProductConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="categoryidVar" name="productForm" property="queryVO.categoryid" type="java.lang.Long"></bean:define>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>培训科目产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/beautifulButton.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    function addProduct(){
	    	parent.addRecord('/product/addproduct.do?vo.categoryid=<%=categoryidVar %>&bakurltype=1');
	    }

	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listshopproductmag.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="productForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <input id="queryVO.categoryid" type="hidden" name="queryVO.categoryid" value="<%=categoryidVar %>">
  <input type="hidden" name="queryVO.shopid" value="<bean:write name="productForm" property="queryVO.shopid"/>">
  <input type="hidden" name="queryVO.isIncludeChild" value="2">
  <div id="firstLineDiv">
      <div id="searchDiv">产品名称: 
		<input type="text" name="queryVO.productname" value="<bean:write name="productForm" property="queryVO.productname"/>"/>
		&nbsp;&nbsp;
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" class="button green" onclick="addProduct();return false;" >开设新课程</button></li>
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
        <td width="20px"></td>
        <td>产品名称</td>
        <td>产品价格</td>   
        <td width="30px">状态</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="idx" type="netTest.product.vo.Productbase">
      <tr class="<%=(idx%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        
        <td><a href="javascript:void(0)" onclick="parent.goUrl('/product/viewproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&shopid=<bean:write name="vo" property="shopid"/>&menuid=id_mag_baseInfo');return false;"><bean:write name="vo" property="productname"/></a>
            <input id="pkId<%=idx %>Name" type="hidden" value="<bean:write name="vo" property="productname"/>"/>
        </td>

        <td><bean:write name="vo" property="productprice"/>&nbsp;(<bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/>)</td>

        <td width="90px" class='<%if(ProductConstant.Status_deleting.equals(vo.getStatus())){out.print("alertFont");} %>'>
            <bean:writeSaas name="vo" property="status" consCode="netTest.ProductConstant.status"/>
        </td>
		<td>
		    <img src="../styles/imgs/edit.png" title="编辑" style="cursor:pointer;" onclick="parent.goUrl('/product/viewproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&shopid=<bean:write name="vo" property="shopid"/>');return false;"/>&nbsp;
		    <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/product/delproduct.do?productbaseid=<bean:write name="vo" property="productbaseid"/>&backUrl=');return false;"/>
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
