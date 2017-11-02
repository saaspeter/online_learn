<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="coursepostForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productname" name="coursepostForm" property="sessionProductname" type="java.lang.String"/>
    <bean:define id="productbaseid" name="coursepostForm" property="sessionProductid" type="java.lang.Long"/>
    <title>商店公告</title>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/product/listcoursepostmag.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="coursepostForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="coursepostForm" property="backUrlEncode"/>">
  <input id="prodnameId" type="hidden" name="productname" value="">
  <input id="prodidId" type="hidden" name="vo.productbaseid" value="<%=(productbaseid==null)?"":productbaseid.toString() %>">
  <div class="navlistBar">
       <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_operatorMag %>');" title="点击选择产品"><%=productname %></a> &gt; 课程通知
  </div>
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" class="button green fontBold" onclick="addRecord('/product/addcoursepost.do?vo.productbaseid=<%=(productbaseid==null)?"":productbaseid.toString() %>');"><bean:message key="netTest.commonpage.add"/></button></li>
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
      <%if(productbaseid==null){ %>请点击左上角选择课程<%} %>
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><%=ind+1 %>. </td>
        <td><bean:write name="vo" property="content" filter="false"/>
            &nbsp;<font style="font-size: smaller;">(<bean:writeDate name="vo" property="createtime" formatKey="Common.DateFormatType.DateTime"/>)</font>
        </td>
        <td style="">
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/product/editcoursepost.do?vo.id=<bean:write name="vo" property="id"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/product/delcoursepost.do?vo.id=<bean:write name="vo" property="id"/>&backUrl=');return false;"/>
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
