<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.PayTypeConstant,netTestWeb.base.WebConstant,netTest.product.vo.Productbase"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <bean:define id="paytypeVar" name="productForm" property="vo.paytype" type="java.lang.Short"/>
    <bean:define id="productbaseidPage" name="productForm" property="vo.productbaseid" type="java.lang.Long"/>
    <bean:define id="logoimageVar" name="productForm" property="vo.logoimage" type="java.lang.String"></bean:define>
    <bean:define id="currentlearnusernumVar" name="productForm" property="vo.currentlearnusernum" type="java.lang.Integer"></bean:define>
    
    <%
	    String productlogo = WebConstant.getDefaultLogoImg(logoimageVar, Productbase.ObjectType);
        if(currentlearnusernumVar==null){currentlearnusernumVar=0;}
	%>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/>--<bean:write name="productForm" property="vo.productname"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">
    
       function showIframeTab(no, totalNum, url, param){
		  if(no==null||no==""){
		     no = 1;
		  }
		  if(totalNum==null||totalNum==""){
		     totalNum = 10;
		  }
		  for(var i=1;i<=totalNum;i++){
		     if(i==no){
		        document.getElementById("tab"+i).className='selectTab';
		     }else{
		        document.getElementById("tab"+i).className='';
		     }
		  }
		  document.getElementById("iframecontentId").src=url;
	   }
    
    </script>

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
	                             	 共有<font style="font-weight: bold;"><%=currentlearnusernumVar %></font>人在学习该课程 &nbsp; &nbsp;
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
	    <li id="tab1" class='selectTab'><a href="javascript:void(0)" onclick="showIframeTab(1,5,'<%=WebConstant.WebContext %>/product/viewproductdesc.do?vo.productbaseid=<%=productbaseidPage %>');return false;">课程介绍</a></li>
		<li id="tab2"><a href="javascript:void(0)" onclick="showIframeTab(2,5,'<%=WebConstant.WebContext %>/prodcont/onlyviewprodcate.do?productbaseid=<%=productbaseidPage %>');return false;">课程目录</a></li>
		<li id="tab3"><a href="javascript:void(0)" onclick="showIframeTab(3,5,'<%=WebConstant.WebContext %>/product/listOpenactivity.do?showtype=5&queryVO.productid=<%=productbaseidPage %>');return false;">公开活动</a></li>
		<li id="tab4"><a href="javascript:void(0)" onclick="showIframeTab(4,5,'<%=WebConstant.WebContext %>/social/listleavemess.do?queryVO.objecttype=<%=Productbase.ObjectType %>&queryVO.objectid=<%=productbaseidPage %>');return false;">留言信息</a></li>
		<li id="tab5"><a href="javascript:void(0)" onclick="showIframeTab(5,5,'<%=WebConstant.WebContext %>/social/listusecomment.do?vo.objecttype=<%=Productbase.ObjectType %>&vo.objectid=<%=productbaseidPage %>');return false;">课程评价</a></li>
	  </ul>
	</div>

    <div id="contentId" style="width:99%;padding-left:0px;overflow: no;display:block;margin:0">
         <iframe id="iframecontentId" name="iframecontentId" width="100%" height="100%" src="<%=WebConstant.WebContext %>/product/viewproductdesc.do?vo.productbaseid=<%=productbaseidPage %>" scrolling="no" frameborder="0"></iframe>
	</div>

  </body>
  
  <script type="text/javascript" language="javascript">
	  
	  function adjustHeightInBuy(inputheight) {   
		 var ifm= document.getElementById("iframecontentId"); 
		 var heightVar = 0;
		 if(typeof(inputheight)!='undefined' && inputheight!=null){
			 heightVar = inputheight;
		 }else {
			 var subWeb = ifm.contentDocument;  
			 if(typeof(subWeb)=='undefined'){
			    subWeb = ifm.document;
			    heightVar = subWeb.body.scrollHeight;
			 }
		 }
		 if(ifm != null && heightVar != null) {
			ifm.style.height = (heightVar+65)+'px';
			document.getElementById("contentId").style.height = (heightVar+90)+'px';
	     }
		 if(typeof(parent.adjustFrameHeight)!='undefined'){
			 parent.adjustFrameHeight(heightVar+125);
		 }
	  }  
   </script>
  
</html:html>
