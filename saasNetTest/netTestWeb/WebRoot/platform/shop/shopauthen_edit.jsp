<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, platform.constant.ShopConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shopForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="authenimageVar" name="shopForm" property="extvo.authenimage" type="java.lang.String"/>
    <bean:define id="isauthenVar" name="shopForm" property="vo.isauthen" type="java.lang.Short"/>
    <title>修改培训商店</title>

    <% boolean isadmin = false; String action = "/shop/saveapplyshopauthen.do?recurltype=2";
       boolean checkfield = false;
       if(!isadmin && (authenimageVar==null || authenimageVar.trim().length()<1)){
    	   checkfield = true;
       }
    %>
    <authz:authorize ifAnyGranted="ROLE_SysAdmin,ROLE_BizDataAdmin">
       <% isadmin = true; action = "/shop/approveshopauthen.do?recurltype=2"; %>
    </authz:authorize>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    
	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<style>
		
		.field_left{

			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 202px;
		}
		
		.user_input{
            width: 200px;
			font-size: larger;
		}
		
		.font_input{
			font-size: larger;
		}
		
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
		
		.right_style{
		    padding-left:10px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
	
	</style>
    <script language="javascript" src="../../styles/script/pageAction.js"></script>
    <script language="javascript">
	<!--
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }
	   
	   function selectfile(){
	       
	       document.getElementById("imgDivId").style.display='none';
	   }
	   

	//-->
	</script>
  </head>
  
  <body>
	<div>
	
	<div class="fieldsTitleDiv">申请更改认证</div>
	
	<html:form styleId="editForm" action="<%=action %>" method="post" enctype="multipart/form-data">
	  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
      <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">
      <input type="hidden" name="extvo.shopid" value="<bean:write name="shopForm" property="shopid"/>">
      <input type="hidden" id="telephoneId" value="<bean:write name="shopForm" property="contactvo.telephone"/>">
      <input type="hidden" id="emailId" value="<bean:write name="shopForm" property="contactvo.email"/>">
	  <div id="displayMessDiv">
	       <logic:equal name="shopForm" property="extvo.authenstatus" value="<%=ShopConstant.AuthenStatus_apply.toString() %>">
			  您的申请已提交，请等待系统管理员审核
		   </logic:equal>
	  </div>

	  <div>
		  <table cellspacing="7" cellpadding="0" border="0" width="100%">
			  <tr>
			     <td class="field_left">商店名称:</td>
			     <td class="login_field">
			         <bean:write name="shopForm" property="vo.shopname"/>(<bean:write name="shopForm" property="vo.shopcode"/>)
			     </td>
			     <td id="shopnameId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">经营者类型:</td>
			     <td class="login_field">
			         <bean:writeSaas name="shopForm" property="vo.ownertype" consCode="platform.ShopConstant.OwnerType"/>
			     </td>
			     <td id="ownertypeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  	  
			  <tr>
			     <td class="field_left">联系方式:</td>
			     <td class="login_field">
			          电话:<bean:write name="shopForm" property="contactvo.telephone"/>
			    <br> 邮件:<bean:write name="shopForm" property="contactvo.email"/>
			     </td>
			     <td class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">申请人证件类型:</td>
			     <td class="login_field" colspan="2">
			         <%if(isadmin || ShopConstant.IsAuthen_no.equals(isauthenVar)){ %>
			         <html:select name="shopForm" property="extvo.authenidtype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.AuthenIDType"/>
			         </html:select>
			         <%}else { %>
			         <bean:writeSaas name="shopForm" property="extvo.authenidtype" consCode="platform.ShopConstant.AuthenIDType"/>
			         <%} %>
			     </td>
			     <td id="authenidtypeId_tip_td" class="login_fieldDesc"><i></i><span id="authenidtypeId_tip"></span></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">申请人证件号:</td>
			     <td class="login_field">
			         <%if(isadmin || ShopConstant.IsAuthen_no.equals(isauthenVar)){ %>
			         <input id="authenidnoId" type="text" class="user_input" name="extvo.authenidno" value="<bean:write name="shopForm" property="extvo.authenidno"/>" usage="notempty" exp="^\S{0,60}$" onfocus="inputOnFocus(this);" onblur="inputOnBlur(this);"/>
			         <%}else { %>
			         <bean:write name="shopForm" property="extvo.authenidno"/>
			         <%} %>
			     </td>
			     <td id="authenidnoId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">上传证件图片:</td>
			     <td class="login_field">
			         <input id="authenimagefileId" type="file" name="extvo.authenimagefile" size="35" onchange="selectfile();return false;" <%if(checkfield){ %> usage="notempty" <%} %> fie="证件照片">
			         <logic:notEmpty name="shopForm" property="extvo.authenimage">
			         <div id="imgDivId" style="width: 250px; overflow: auto">
			             <img id="authenimageID" style="width:250px;" src="<%=WebConstant.FileContext %><bean:write name="shopForm" property="extvo.authenimage"/>">
			         </div>
			         </logic:notEmpty>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">申请商店付费类型:</td>
			     <td class="login_field" colspan="2">
			         <html:select name="shopForm" property="extvo.applychargetype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.ChargeType"/>
			         </html:select>
			     </td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">支付信息:</td>
			     <td class="login_field">
			         <textarea name="extvo.payinfo" rows="2" cols="30"><bean:write name="shopForm" property="extvo.payinfo"/></textarea>
			     </td>
			     <td class="login_fieldDesc"></td>
			  </tr>
			  
			  <%if(isadmin){ %>
			  <tr>
			     <td class="field_left">申请认证状态:</td>
			     <td class="login_field">
			         <html:select name="shopForm" property="extvo.authenstatus" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.ApplyAuthenStatus" param1="dropdown"/>
			         </html:select>
			     </td>
			     <td id="authenstatusId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  <%} %>
			  
			  <logic:present name="shopForm" property="extvo.authendate">
			  <tr>
			     <td class="field_left">日期:</td>
			     <td class="login_field">
			         <bean:writeDate name="shopForm" property="extvo.authendate" format="yyyy-MM-dd HH:mm" showtimezone="true"/>
			     </td>
			     <td class="login_fieldDesc"></td>
			  </tr>
			  </logic:present>
			  
			  <tr>
			     <td class="field_left">描述:</td>
			     <td class="login_field">
			         <textarea name="extvo.authendesc" rows="2" cols="30"><bean:write name="shopForm" property="extvo.authendesc"/></textarea>
			     </td>
			     <td id="descId_tip_td" class="login_fieldDesc"></td>
			  </tr>
	      </table>
      </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button style="font-size:20px;" type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button style="font-size:20px;" type="button" onclick="parent.clearDiv();">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>

	</div>
	
  </body>
</html:html>
