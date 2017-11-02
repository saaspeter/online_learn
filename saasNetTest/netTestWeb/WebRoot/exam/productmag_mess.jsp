<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="testtodocount" name="testinfoForm" property="testtodocount" type="java.lang.Integer"/>
<bean:define id="hasshopproduct" name="testinfoForm" property="hasshopproduct" type="java.lang.String"/>
<bean:define id="shopidVar" name="testinfoForm" property="queryVO.shopid" type="java.lang.Long"/>
<%
    String productUrl_my = request.getContextPath()+"/usercontext/listMymagProd.do?shopid="+shopidVar;
    String productUrl_all = request.getContextPath()+"/product/product_tree_main.jsp?shopid="+shopidVar;
    boolean allow_manage_allprod = false;
%>
<authz:privilege res='<%="/product/product_tree_main.jsp?shopid="+shopidVar %>'>
    <% allow_manage_allprod = true; %>
</authz:privilege>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>管理商店产品信息台</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <style>
        #menu5{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}

	</style>

  </head>
  
  <body>
      <div class="col-xs-12 col-md-9 center-block">
  	  <jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidVar %>'></jsp:include>
  
      <div id="mainContent">
      
          <div class="titleBar">产品管理信息台</div>
		  
	      <%if("no".equals(hasshopproduct)){ %>
		  <div class="navlistBar" style="margin:12px 0px 12px 0px">
		      <a href="/<%=CommonConstant.WebContext_Education %>/product/product_tree_main.jsp" class="messLink" onclick="parent.pressMenu('id_shopadmin_prodtest');">
		          <font style="color: red;font-size: larger;">商店还未设置课程,请先设置商店课程</font>
		      </a>
		  </div>
	      <%}else { %>
	      <div class="navlistBar" style="margin:12px 0px 12px 0px">
		                 学校<%if(allow_manage_allprod){ %><a href="<%=productUrl_all %>" class="messLink">共有<bean:write name="testinfoForm" property="productnum"/><%} %>门课程<%if(allow_manage_allprod){ %></a><%} %>;&nbsp;&nbsp;
		                 我<a href="<%=productUrl_my %>" class="messLink" title=''>直接管理<bean:write name="testinfoForm" property="myprodnum"/>门课程</a>;&nbsp;&nbsp;
		                 我想<a href="<%=request.getContextPath() %>/shop/myOwnShops.do?messagekey=netTest.UserServiceAction.TipMess.createCourseInHomePage" class="messLink" title='<bean:message key="netTest.UserServiceAction.TipMess.createCourseInHomePage"/>'>开设一门新课程</a> 
	      </div>
	      
	      <div class="navlistBar" style="margin:12px 0px 12px 0px">
	          <font style="color: red;font-weight: bold;">&bull;未处理的考试数目:<%=testtodocount %></font><br/>
	      </div>
	      <%if(testtodocount>0){ %>
	      (点击考试名称可查看修改详细考试信息；如果需要评阅试卷，请点击菜单:评阅试卷；如果需要查看考试，请点击菜单:考试结果)
	      <div style="font-size: larger;margin: 25px;"> 
		      <logic:present name="testinfoForm" property="testinfoList">
		         <table class="listDataTable" >
			        <thead>
			        <tr class="listDataTableHead">
			           <td width="20px"></td>
			           <td>考试名</td>
			           <td>考试时间</td>
			           <td>考试科目</td>
			           <td>考试状态</td>
			         </tr>
			         </thead>
				     <logic:iterate id="vo" name="testinfoForm" property="testinfoList" indexId="ind">
			         <tr class='<%=(ind%2==0)?"oddRow":"evenRow" %>'>
			            <td></td>
			            <td><a href="javascript:void(0)" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="vo" property="testid"/>');parent.pressMenu('id_mag_testmag');"><bean:write name="vo" property="testname"/></a>
			            </td>
			            <td><bean:write name="vo" property="teststartdate" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
				            -- <br/><bean:write name="vo" property="testenddate" format="yyyy-MM-dd HH:mm:ss"/></td>
			            <td><bean:write name="vo" property="productname"/></td>
			            <td><bean:writeSaas name="vo" property="teststatus" consCode="netTest.TestinfoConstant.Teststatus"/></td>
			         </tr>
			         </logic:iterate>
			      </table>
		       </logic:present>
		  </div>
	      <%} %>
	      <%} %>
	      
	  </div>
	  
	  <jsp:include flush="true" page="../foot.jsp"></jsp:include>
	  </div>
  </body>
</html:html>
