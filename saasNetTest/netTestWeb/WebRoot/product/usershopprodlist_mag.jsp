<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="userproductForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="userid" name="userproductForm" property="userid" type="java.lang.Long"/>
    <bean:define id="userhasprod" name="userproductForm" property="userhasprod" type="java.lang.String"/>
    <title>学员产品列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listshopprodforuser.do" method="post">
  <input type="hidden" name="userid" value="<bean:write name="userproductForm" property="userid"/>">
  
  <div id="functionLineDiv" >
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="newDiv('/product/adduserprod.do?vo.userid=<%=userid %>',1,1,500,300);return false;">新增课程</button></li> 
		  </ul>
	  </div>

      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td>课程</td>
        <td>使用课程方式</td>
        <td>状态</td>
        <td></td>
      </tr>
      </thead>
	  <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><bean:write name="vo" property="productname"/></td>
		<td><bean:writeSaas name="vo" property="produsetype" consCode="netTest.UserproductConstant.ProdUseType"/></td>
        <td><bean:writeSaas name="vo" property="status" consCode="UserProduct.status"/></td>
        <td><img src="../styles/imgs/edit.png" alt="查看编辑" style="cursor:pointer;" onclick="newDiv('/product/edituserprod.do?vo.userproductid=<bean:write name="vo" property="userproductid"/>',1,1,600,300);return false;"/>&nbsp;&nbsp;
            <img src="../styles/imgs/delete.png" alt="删除" style="cursor:pointer;" onclick="goUrl('/product/deluserprod.do?vo.userproductid=<bean:write name="vo" property="userproductid"/>&userid=<%=userid %>');return false;"/>
        </td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
   <script type="text/javascript">
	 <!--
       window.onload=function(){
		  <%if("0".equals(userhasprod)){ %>
		       document.getElementById("displayMessDiv").innerHTML = document.getElementById("displayMessDiv").innerHTML
		         + "&nbsp&nbsp&nbsp "+"用户已经没有使用任何本店产品，是否&nbsp"
		         + "<a href=\"javascript:void(0);\" onclick=\"parent.delThisUser();return false;\">将用户从本店中删除</a>";
		  <%} %>
	   }
     //-->
  </script>
  </body>
</html:html>
