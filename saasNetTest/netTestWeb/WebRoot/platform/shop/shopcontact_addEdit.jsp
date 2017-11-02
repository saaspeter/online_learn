<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.shop.vo.Shopcontactinfo" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="shopcontactinfoForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="shopcontactinfoForm" property="editType" type="java.lang.Integer"/>
<bean:define id="isdefaultVar" name="shopcontactinfoForm" property="vo.isdefault" type="java.lang.Short"/>

<% boolean loadregion = false;
   if(editType==WebConstant.editType_add
		||Shopcontactinfo.Isdefault_default.equals(isdefaultVar)){
	   loadregion = true;
   }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑商店简介</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<style type="text/css">
	
	    .login_fieldDesc{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#999;
		}
		
		.login_fieldDesc_right{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#00ff00;
		}
		
		.login_fieldDesc_right i{
		    width:16px;
		    display:inline-block;
			background-image:url(../../styles/imgs/icon_success.gif);
			background-repeat: no-repeat;
		}
		
		.login_fieldDesc_wrong{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
		
		.login_fieldDesc_wrong i{
		    width:16px;
		    display:inline-block;
			background-image:url(../../styles/imgs/icon_error.gif);
			background-repeat: no-repeat;
		}

		.input_on{
		   border:2px solid #FF99CC;
		   font-size: larger;
		}
		
		.user_input{
			font-size: larger;
		}
		
		.font_input{
			font-size: larger;
		}
	
	</style>
	<script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type='text/javascript' src="../../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
	<script type='text/javascript' src="../../styles/script/region/region.js"></script>
	<script type="text/javascript">
    
       g_check.showErrType = 1;
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }

	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/saveshopcontact.do" method="post">
     <input type="hidden" name="vo.contactinfoid" value="<bean:write name="shopcontactinfoForm" property="vo.contactinfoid"/>">
     <input type="hidden" name="vo.shopid" value="<bean:write name="shopcontactinfoForm" property="vo.shopid"/>">
     <input type="hidden" name="vo.localeid" value="<bean:write name="shopcontactinfoForm" property="shopvo.localeid"/>">
	  <div class="fieldsTitleDiv"><bean:write name="shopcontactinfoForm" property="shopvo.shopname"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="margin-top:25px;">
		 <table class="formTable2">
		     <tr>
		        <td align="right">电话:</td>
		        <td><input id="telephoneId" name="vo.telephone" style="width:300px;" value="<bean:write name="shopcontactinfoForm" property="vo.telephone"/>" usage="notempty" exp="^\S{0,120}$" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
		            <span id="telephoneId_tip_td" class="login_fieldDesc"><i></i><span id="telephoneId_tip"></span></span>
		        </td>
		        <td align="right">电子邮件:</td>
		        <td><input id="emailId" name="vo.email" style="width:200px;" value="<bean:write name="shopcontactinfoForm" property="vo.email"/>" usage="notempty,email" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
		            <span id="emailId_tip_td" class="login_fieldDesc"><i></i><span id="emailId_tip"></span></span></td>
		     </tr>
		     <tr>
		        <td align="right">所在地区:</td>
		        <td colspan="3">
		            <%if(loadregion){ %>
			        <table style="border:0px;">
				        <tr>
				           <td>
				              <span>
				              <select id="provinceId" name="privCode" onchange="selectRegion()" usage="notempty" fie="所在地区" class="font_input"></select>(省)
	                          </span>
	                       </td>
	                       <td id="cityId_container_id" style="text-align: left;">
	                          <select id="cityId" name="cityCode" class="font_input" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"></select>
	                       </td>
				           <td id="provinceId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="provinceId_tip"></span></td>
				        </tr>
			        </table>
			        <%}else { %>
			        <bean:writeSaas name="shopcontactinfoForm" property="vo.regioncode" consCode="CountryregionConstant.RegionCode"/>
			        <%} %>
		        </td>
		     </tr>
		     <tr>
		        <td align="right">具体地址:</td>
		        <td colspan="3">
		            (可以填写多个地址)
		        </td>
		     </tr>
		     <tr>
		        <td></td>
		        <td colspan="3">
		            <textarea id="addressId" rows="3" name="vo.address" cols="70" class="user_input" usage="notempty,max-length:900" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"><bean:write name="shopcontactinfoForm" property="vo.address"/></textarea>
		            <div id="addressId_tip_td" class="login_fieldDesc"><i></i><span id="addressId_tip">不超过900个字符或300个汉字</span></div>
		        </td>
		     </tr>
		 </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" style="font-size: larger;" onclick="submitForm('editForm'); return false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" style="font-size: larger;" onclick="goUrl('/shop/listshopcontact.do');return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	
	<script type="text/JavaScript">
    <!-- 
        window.onload=function(){
           <%if(loadregion){ %>
           var localeidVar = '<bean:write name="shopcontactinfoForm" property="shopvo.localeid"/>';
           initRegion(localeidVar, '<bean:write name="shopcontactinfoForm" property="privCode"/>','<bean:write name="shopcontactinfoForm" property="cityCode"/>');
           <%} %>
        }
    //-->
    </script>
	
  </body>
</html:html>
