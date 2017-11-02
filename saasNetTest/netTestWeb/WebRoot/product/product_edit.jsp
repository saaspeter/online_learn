<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.PayTypeConstant, platform.constant.ShopConstant, netTest.product.constant.ProductConstant"%>
<%@ include file="/pubs/taglibs.jsp" %>

<% String status_removestr = "2,4"; %>
<authz:authorize ifAnyGranted="ROLE_SysAdmin,ROLE_BizDataAdmin">
     <% status_removestr = "4"; %>
</authz:authorize>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="ablechangepaytype" name="productForm" property="ablechangepaytype" type="java.lang.Boolean"></bean:define>
    <bean:define id="ishopopen" name="productForm" property="ishopopen" type="java.lang.Boolean"></bean:define>
    <bean:define id="canpublish" name="productForm" property="canpublish" type="java.lang.Boolean"></bean:define>
    <bean:define id="statusVar" name="productForm" property="vo.status" type="java.lang.Short"></bean:define>
    <bean:define id="shopidVar" name="productForm" property="vo.shopid"></bean:define>
    <title>课程编辑</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

    <link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<style type="text/css">
	    #leftbar1{
		   float:left;
		   width: 13%;
		}
		
		#maincontent1{
		    float:left;
		    width: 85%;
		    border-left: 1px #cccccc solid;
		    margin-left: 6px;
		    min-height: 600px;
		}
	</style>
    <script type="text/JavaScript" src="../styles/script/pageAction.js"></script>
    <script type="text/JavaScript">
    <!--
       function payTypeChange(){
          var selObj = document.getElementById("payTypeId");
          if(typeof(selObj)=='undefined'||selObj==null){
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
       
    //-->
    </script>
  </head>
  
  <body>
    <div class="col-xs-12 col-md-9 center-block">
	
	<jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidVar %>'></jsp:include>

	<div style="height:15px; clear:both;"></div>
  
    <div>
	 
		<div id="leftbar1">
		    <jsp:include page="../leftbar_netTest.jsp"></jsp:include>
		</div>
  
	<div id="maincontent1">
	<html:form styleId="editForm" action="/product/saveproduct.do" method="post">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="%2Fproduct%2Fviewproduct.do%3Fproductbaseid%3D<bean:write name="productForm" property="vo.productbaseid"/>">	
	 <input type="hidden" name="vo.productbaseid" value="<bean:write name="productForm" property="vo.productbaseid"/>">
	 <input type="hidden" name="vo.shopid" value="<bean:write name="productForm" property="vo.shopid"/>"/>
	 <input type="hidden" id="saveType" name="saveType" value="1">
	
	  <div id="fieldsTitleDiv">编辑课程基本信息</div>

	  <div id="fieldDisDiv">
	     <ul>
	     
	        <li>
			   <div class="fieldLabel"><font color="red">*</font>课程名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.productname" usage="notempty,max-length:60" fie="课程名称" value="<bean:write name="productForm" property="vo.productname"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
	        <li>
			   <div class="fieldLabel">课程目录:</div>
			   <div class="field"> 
			     <bean:write name="productForm" property="vo.categoryname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<%if(ablechangepaytype) { %>
			<li>
			   <div class="fieldLabel"><img src="../styles/imgs/help.gif" title="是否开放给本商店外用户订购学习, 当商店是非公开时，课程必须为非公开"/>
			                      课程开放性:
			   </div>
			   <div class="field"> 
			      <%
			      if(!ishopopen){ %>
			      <input name="vo.isopen" type="hidden" value="<%=ProductConstant.Isopen_no %>" >
			      <%} %>
			   	  <html:select styleId="visibleId" name="productForm" property="vo.isopen" disabled="<%=!ishopopen %>" >
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
			     <input type="text" id="uselimitnumId" name="vo.uselimitnum" usage="notempty,int+" fie="使用天数/次数" value="<bean:write name="productForm" property="vo.uselimitnum"/>" />
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
			   	  <html:select name="productForm" property="vo.promotable" >
					 <html:optionsSaas consCode="common.const.yesnoType"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">课程状态:</div>
			   <div class="field"> 
			   	  <html:select name="productForm" styleId="statusId" property="vo.status" onchange="checkStatus()">
					 <html:optionsSaas consCode="netTest.ProductConstant.status" removeStr="<%=status_removestr %>"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="btn btn-success" onclick="submitForm('editForm');return false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="btn btn-default" onclick="goUrl('/product/viewproduct.do?productbaseid=<bean:write name="productForm" property="vo.productbaseid"/>&backUrlEncode=','backUrlEncode');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
	</html:form>
	</div>
	</div>
	<jsp:include flush="true" page="../foot.jsp"></jsp:include>
	</div>
	<script type="text/JavaScript">
    <!-- 
       window.onload=function(){
         payTypeChange();
       };
    
       function checkStatus(){
    	   <%if(!canpublish){ %>
    	   var newstatus = document.getElementById("statusId").value;
    	   if(newstatus=='<%=ProductConstant.Status_valid %>'){
    		   document.getElementById("statusId").value = '<%=statusVar %>';
    		   showMessagePage('<bean:message key="error.course.noEnoughLearnContent" bundle="BizKey"/>', '1');
    	   }
    	   <%} %>
       }
    //-->
    </script>
  </body>
</html:html>
