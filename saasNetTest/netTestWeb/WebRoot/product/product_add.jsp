<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.PayTypeConstant,netTest.product.constant.ProductConstant,netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>

<% String bakurltype = request.getParameter("bakurltype");
   if(bakurltype==null||"".equals(bakurltype)){
	   bakurltype = "1";
   }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="ablechangepaytype" name="productForm" property="ablechangepaytype" type="java.lang.Boolean"></bean:define>
    <bean:define id="shopidVar" name="productForm" property="vo.shopid" type="java.lang.Long"></bean:define>
    <bean:define id="categoryNameVar" name="productForm" property="categoryName" type="java.lang.String"></bean:define>
    <% if(categoryNameVar==null) { categoryNameVar=""; } %>
    
    <title>新增培训课程</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

    <link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/progress_trackers.css">
    <script type="text/JavaScript" src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/shopcheck.js'></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type="text/JavaScript">
    <!--
       function payTypeChange(){
          var selObj = document.getElementById("payTypeId");
          if(selObj==null){
        	  return;
          }
          var productpriceObj = document.getElementById("productpriceId");
          var uselimitnumObj = document.getElementById("uselimitnumId");
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue!=null&&itemValue==<%=PayTypeConstant.PayType_free.toString()%>){
             productpriceObj.disabled = "disabled";
             productpriceObj.className="readonly";
          }else {
             productpriceObj.disabled = "";
             productpriceObj.className="";
          }
          if(itemValue!=null&&(itemValue==<%=PayTypeConstant.PayType_useday_somedays.toString()%>
               ||itemValue==<%=PayTypeConstant.PayType_usetimes_sometimes.toString()%>)){
             uselimitnumObj.disabled = "";
             uselimitnumObj.className="";
          }else{
             uselimitnumObj.value = "";
             uselimitnumObj.disabled = "disabled";
             uselimitnumObj.className="readonly";
          }
       }
    
       function gobackurl(){
    	   <%if("1".equals(bakurltype)){ %>
    	   goUrl('/product/product_tree_main.jsp?shopid=<%=shopidVar %>');
    	   <%}else { %>
    	   goUrl('/usercontext/listMymagProd.do?shopid=<%=shopidVar %>');
    	   <%} %>
       }
       
       function selectCate(){
   	      var url = "#"+webContext+"/productcategory/shopprodcatetreepage.do?urltype=1&shopid=<%=shopidVar %>";
   	      newDiv(url,1,1,270,300);
   	   }
       
       function selectCate_CB(id,name,param){
    	   if(id==null){
    		   return;
    	   }
    	   shopcheck.checkAddProdInCate(id, 
               function CB_check(addable){
    		      clearDiv();
                  if(!addable){
                	  showMessagePage(document.getElementById('cateSelectMsgId').value);
                	  document.getElementById("categoryNameId").value = '';
               	      document.getElementById("categoryidId").value = '';
                  }else {
                	  document.getElementById("categoryNameId").value = name;
               	      document.getElementById("categoryidId").value = id;
               	      clearMessagePage();
                  }
               }
           );
       }
              
    //-->
    </script>
  </head>
  
  <body>
  
  <div class="col-xs-12 col-md-9 center-block">

    <jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidVar %>'></jsp:include>
  
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveproduct.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value='<bean:write name="productForm" property="backUrl"/>'>
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value='<bean:write name="productForm" property="backUrlEncode"/>'>	
     <input type="hidden" name="vo.shopid" value='<bean:write name="productForm" property="vo.shopid"/>'/>
	 <input type="hidden" name="vo.tryusetype" value="<%=ProductConstant.TryUseType_dayNum%>"/>
	 <input type="hidden" id="cateSelectMsgId" value='<bean:message key="exception.Product.addInCategory" bundle="BizKey"/>'/>
	
	  <div id="fieldsTitleDiv">创建一门新课程</div>

      <div>
          <ol class="steps">
	        <li class="step1 current"><span>填写课程基本信息</span></li>
	        <li class="step2"><span><a href="javascript:void(0)" title="可以在'课程人员管理'菜单中指定课程管理员，由其继续完成课程发布和管理">设置课程管理员( 可选? )</a></span></li>
	        <li class="step3"><span>填写课程介绍</span></li>
	        <li class="step4"><span>设置课程目录</span></li>
	        <li class="step5"><span>添程视频/文档/练习</span></li>
	        <li class="step6"><span>发布课程</span></li>
	      </ol>
      </div>

	  <div id="fieldDisDiv">
	     <ul>
	     
	        <li>
			   <div class="fieldLabel"><font color="red">*</font>课程名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.productname" class="userInput" usage="notempty,max-length:60" fie="课程名称" value="<bean:write name="productForm" property="vo.productname"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
	        <li>
			   <div class="fieldLabel"><font color="red">*</font>产品目录:</div>
			   <div class="field"> 
			      <input id="categoryNameId" value="<%=categoryNameVar %>" readonly="readonly" usage="notempty" fie="课程目录" placeholder="点击选择课程目录" onclick="selectCate()"/>
			      <input id="categoryidId" type="hidden" name="vo.categoryid" usage="notempty" value="<bean:write name="productForm" property="vo.categoryid"/>">
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<%if(ablechangepaytype) { %>
			<li>
			   <div class="fieldLabel">课程开放性:</div>
			   <div class="field"> 
			   	  <html:select styleId="visibleId" name="productForm" property="vo.isopen" >
					 <html:optionsSaas consCode="netTest.ProductConstant.Isopen"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<%} %>
			<li>
			   <div class="fieldLabel">付款方式:</div>
			   <div class="field"> 
			      <%if(ablechangepaytype) { %>
			   	  <html:select styleId="payTypeId" name="productForm" property="vo.paytype" onchange="payTypeChange()" >
					 <html:optionsSaas consCode="Common.PayTypeConstant.PayType"/>
			      </html:select>
			      <%}else { %>
			      <bean:writeSaas name="productForm" property="vo.paytype" consCode="Common.PayTypeConstant.PayType"/>
			      <img src="../styles/imgs/help.gif" title='任何人都能免费学习，要想创建收费课程，需先在"管理商店基本信息中"选择认证商店'/>
			      <input type="hidden" name="vo.paytype" value="<bean:write name="productForm" property="vo.paytype"/>">
			      <%} %>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

            <%if(ablechangepaytype) { %>
		    <li>
			   <div class="fieldLabel">课程价格:</div>
			   <div class="field"> 
			     <input type="text" id="productpriceId" name="vo.productprice" usage="notempty,money" fie="课程价格" value="<bean:write name="productForm" property="vo.productprice"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">使用天数/次数:</div>
			   <div class="field"> 
			     <input type="text" id="uselimitnumId" name="vo.uselimitnum" usage="notempty,int+" fie="使用天数/次数" value="<bean:write name="productForm" property="vo.uselimitnum"/>" disabled="disabled" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">购买是否需要审批:</div>
			   <div class="field"> 
			   	  <html:select name="productForm" property="vo.isneedapprove" >
					 <html:optionsSaas consCode="netTest.ProductConstant.IsNeedApprove"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<%} %>

            <li>
			   <div class="fieldLabel">是否定为推荐课程:</div>
			   <div class="field"> 
			   	  <html:select name="productForm" property="vo.promotable" styleClass="userInput">
					 <html:optionsSaas consCode="common.const.yesnoType"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">课程状态:</div>
			   <div class="field">
			      <bean:writeSaas name="productForm" property="vo.status" consCode="netTest.ProductConstant.status"/>
			      <img src="../styles/imgs/help.gif" title='<bean:message key="tipMessage.addCourse.needAddLearnContent" bundle="BizKey"/>'/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		 </ul>
	  </div>
	  
	  <div id="displayMessDiv"></div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="btn btn-success btn-lg" onclick="submitForm('editForm');return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="btn btn-default btn-lg" onclick="gobackurl();return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
	</div>
	<script type="text/JavaScript">
	 <!--
       window.onload=function(){
		   payTypeChange();
	   }
     //-->
    </script>
  </body>
</html:html>
