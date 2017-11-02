<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.PayTypeConstant,netTestWeb.base.WebConstant,netTest.product.vo.Productbase"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <bean:define id="paytypeVar" name="productForm" property="vo.paytype" type="java.lang.Short"/>
    <bean:define id="productbaseidPage" name="productForm" property="vo.productbaseid" type="java.lang.Long"/>
    <bean:define id="logoimageVar" name="productForm" property="vo.logoimage" type="java.lang.String"></bean:define>
    <%
	    String productlogo = WebConstant.getDefaultLogoImg(logoimageVar, Productbase.ObjectType);
	%>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/>--<bean:write name="productForm" property="vo.productname"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
 <body>

	  <div>
	      <table style="border: 0px; width: 98%;">
	          <tr>
	              <td style="margin: 5px;width: 240px;">
	                  <img id="logoimgId" <%if(productlogo!=null&&!"".equals(productlogo)) { %> src="<%=productlogo %>" <%} %> alt="课程封面图片" style="width: 200px; height:100px;border: #eeeeee 2px solid;">
	              </td>
	              <td>
	                  <table style="border: 0px; width: 100%;">
	                      <tr>
	                          <td style="text-align: left;font-size: x-large;"><bean:write name="productForm" property="vo.productname"/></td>
	                      </tr>
	                      <tr><td><hr></td></tr>
	                      <tr>
	                         <td>
	                                                                                   价格:<font style="color: green;font-weight: bold;font-size: larger;"><bean:write name="productForm" property="vo.productprice"/></font>&nbsp;
	                                                                                   付款方式:&nbsp;<font style="font-weight: bold;"><bean:writeSaas name="productForm" property="vo.paytype" consCode="Common.PayTypeConstant.PayType"/></font>&nbsp;
	                              <%if(PayTypeConstant.PayType_useday_somedays.equals(paytypeVar)){ %>
					                   学习天数:<font style="font-weight: bold;"><bean:write name="productForm" property="vo.uselimitnum"/></font>
					              <%}else if(PayTypeConstant.PayType_usetimes_sometimes.equals(paytypeVar)){ %>
					                   学习次数:<font style="font-weight: bold;"><bean:write name="productForm" property="vo.uselimitnum"/></font>
					              <%} %>								                                          
	                         </td>
	                      </tr>
	                      <tr>
	                          <td style="padding-top: 10px;">
	                             	 共有<font style="font-weight: bold;"><bean:write name="productForm" property="vo.currentlearnusernum"/></font>人在学习该课程 &nbsp; &nbsp;
	           						课程评价: &nbsp;<bean:write name="productForm" property="commentavggrade"/>  
	                				(<img src="../styles/imgs/person.gif" height="17px"> 共<font style="font-weight: bold;"><bean:write name="productForm" property="commentusernumber"/></font>人评价)
	                          </td>
	                      </tr>
	                  </table>
	                  
	              </td>
	          </tr>
	      </table>
	  </div>
	  
	  <div id="errorDisplayDiv"></div>

	  <div style="clear: both;text-align: center;padding-top: 25px;padding-bottom:25px;">
	      <button type="button" onclick="window.open('${pageContext.request.contextPath}/shopping/addProdToCart.do?orderprodid=<bean:write name="productForm" property="vo.productbaseid"/>','_shoppingcart');return false;" style="height: 40px;font-size: large;">购买课程</button>
	  </div>

	
	<div style="height:auto; width:100%; clear:both;">
	  <ul class="tabnav">
	    <li id="tab1" class='selectTab'><a href="javascript:void(0)" onclick="showTab(1,4);return false;">课程介绍</a></li>
		<li id="tab2"><a href="javascript:void(0)" onclick="showTab(2,4);return false;">课程目录</a></li>
		<li id="tab3"><a href="javascript:void(0)" onclick="showTab(3,4);return false;">留言信息</a></li>
		<li id="tab4"><a href="javascript:void(0)" onclick="showTab(4,4);return false;">课程评价</a></li>
	  </ul>
	</div>

    <div id="content1" style="width:98%;padding-left:8px;overflow: auto;display:block;">
         <iframe id="iframecontent1" name="iframecontent1" width="100%" height="100%" src="<%=WebConstant.WebContext %>/product/viewproductdesc.do?vo.productbaseid=<%=productbaseidPage %>" scrolling="auto" frameborder="0"></iframe>
	</div>
	
	<div id="content2" style="width:99%;padding-left:8px;overflow: auto;display:none">
	     <iframe id="iframecontent2" name="iframecontent2" width="100%" height="100%" src="<%=WebConstant.WebContext %>/prodcont/onlyviewprodcate.do?productbaseid=<%=productbaseidPage %>" scrolling="auto" frameborder="0"></iframe>
	</div>
	
	<div id="content3" style="width:99%;padding-left:8px;overflow: auto;display:none">
	     <iframe id="iframecontent3" name="iframecontent3" width="100%" height="100%" src="<%=WebConstant.WebContext %>/social/listleavemess.do?queryVO.objecttype=<%=Productbase.ObjectType %>&queryVO.objectid=<%=productbaseidPage %>" scrolling="auto" frameborder="0"></iframe>
	</div>
	
	<div id="content4" style="width:99%;padding-left:8px;overflow: auto;display:none">
	     <iframe id="iframecontent4" name="iframecontent4" width="100%" height="100%" src="<%=WebConstant.WebContext %>/social/listusecomment.do?vo.objecttype=<%=Productbase.ObjectType %>&vo.objectid=<%=productbaseidPage %>" scrolling="auto" frameborder="0"></iframe>
	</div>

  </body>
  
  <script type="text/javascript" language="javascript">
	  
	  window.onload = function(){
		 loadDivByAjax("content1", '<%=WebConstant.WebContext %>/product/viewproductdesc.do','vo.productbaseid=<%=productbaseidPage %>',false);
	     if(typeof(parent.adjustFrameHeight)!='undefined'){
	    	 parent.adjustFrameHeight();
	     }
	  };
   </script>
  
</html:html>
