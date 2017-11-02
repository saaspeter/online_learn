<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shopForm" property="jsSuffix" type="java.lang.String"/>
    <title>修改培训商店</title>

    <% boolean isadmin = false; String action = "/shop/updatesaveshop.do";%>
    <authz:authorize ifAnyGranted="ROLE_SysAdmin,ROLE_BizDataAdmin">
       <% isadmin = true; action = "/shop/adminsaveshop.do"; %>
    </authz:authorize>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    
	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<style>
	    .userinput_div{
		    width: 75%;
			float:left;
			overflow: auto;
		}
		
		.field_left{
			width:285px;
			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 302px;
		}
		
		.user_input{
            width: 300px;
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
		   width: 300px;
		   font-size: larger;
		}
		
		.right_style{
		    padding-left:10px;
			text-align:left;
			font-size:15px;
			color:#ff0000;
		}
	
	</style>
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/shopcheck.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="../../styles/script/region/region.js"></script>
    <script language="javascript">
	<!--
	   function inputOnFocus(obj){
	      obj.className='input_on';
	   }
	   
	   function inputOnBlur(obj){
	      obj.className='user_input';
	      checkElement(obj,1);
	   }
	   
	   g_check.showErrType = 1;

	//-->
	</script>
  </head>
  
  <body>
	<div>
	
	<div class="fieldsTitleDiv">设置商店基本信息</div>
	
	<html:form styleId="editForm" action="<%=action %>" method="post">	
      <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
      <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">
	  <input type="hidden" name="vo.shopid" value="<bean:write name="shopForm" property="vo.shopid"/>">
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
		  <table cellspacing="7" cellpadding="0" border="0" width="100%">
			  <tr>
			     <td class="field_left">商店名称:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:write name="shopForm" property="vo.shopname"/></span>
			     </td>
			     <td id="shopnameId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店代码:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:write name="shopForm" property="vo.shopcode"/></span>
			     </td>
			     <td id="shopnameId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">所属行业:</td>
			     <td class="login_field">
			         <html:select name="shopForm" property="vo.bizarea" styleClass="user_input">
			            <html:option value=""></html:option>
					    <html:optionsSaas consCode="common.bizArea"/>
			         </html:select>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc"><i></i><span id="shopcodeId_tip"></span></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">经营者类型:</td>
			     <td class="login_field">
			         <html:select name="shopForm" property="vo.ownertype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.OwnerType" param1="register"/>
			         </html:select>
			     </td>
			     <td id="ownertypeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店开放类型:</td>
			     <td class="login_field" colspan="2">
			         <html:select name="shopForm" property="vo.opentype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.OpenType"/>
			         </html:select>
			     </td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店人数规模:</td>
			     <td class="login_field">
			         <html:select name="shopForm" property="vo.usernumscale" styleClass="user_input">
			            <html:option value=""></html:option>
					    <html:optionsSaas consCode="platform.ShopConstant.UserNumScale"/>
			         </html:select>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  <%if(isadmin){ %>
			  <tr>
			     <td class="field_left">认证状态:</td>
			     <td class="login_field">
			         <html:select name="shopForm" property="vo.isauthen" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.IsAuthen"/>
			         </html:select>
			     </td>
			     <td id="isauthenId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">收费类型:</td>
			     <td class="login_field">
			         <html:select name="shopForm" property="vo.chargetype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.ChargeType"/>
			         </html:select>
			     </td>
			     <td id="chargetypeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  <%} %>
	      </table>
      </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button style="font-size:20px;" onclick="submitForm('editForm'); return  false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button style="font-size:20px;" onclick="goUrl('/shop/viewshopeditpage.do?queryVO.shopid=<bean:write name="shopForm" property="vo.shopid"/>');return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>

	</div>
	
  </body>
</html:html>
