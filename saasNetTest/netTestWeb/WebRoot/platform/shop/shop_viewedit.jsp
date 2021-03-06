<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.ShopConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看商店信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>
    <script language=JavaScript src="../../styles/script/movediv.js"></script>
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
	
	</style>

  </head>
  
  <body>
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">
	<div>
	  <div class="fieldsTitleDiv">商店基本信息</div>
	  <div id="fieldDisDiv">
	     <table cellspacing="7" cellpadding="0" border="0" width="100%">
			  <tr>
			     <td class="field_left">商店名称:</td>
			     <td class="login_field"><span class="user_input"><bean:write name="shopForm" property="vo.shopname"/></span></td>
			     <td id="shopnameId_tip_td" class="login_fieldDesc"></td>
			  </tr>

              <tr>
			     <td class="field_left">商店代号:</td>
			     <td class="login_field"><span class="user_input"><bean:write name="shopForm" property="vo.shopcode"/></span></td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">所属行业:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:writeSaas name="shopForm" property="vo.bizarea" consCode="common.bizArea"/></span>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">经营者类型:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:writeSaas name="shopForm" property="vo.ownertype" consCode="platform.ShopConstant.OwnerType"/></span>
			     </td>
			     <td id="ownertypeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店开放类型:</td>
			     <td class="login_field" colspan="2">
			         <span class="user_input"><bean:writeSaas name="shopForm" property="vo.opentype" consCode="platform.ShopConstant.OpenType"/></span>
			     </td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店人数规模:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:writeSaas name="shopForm" property="vo.usernumscale" consCode="platform.ShopConstant.UserNumScale"/></span>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">创建者:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:write name="shopForm" property="vo.creatorname"/></span>
			     </td>
			     <td id="creatornameId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">认证状态:</td>
			     <td class="login_field">
			         <span class="user_input">
			         <logic:equal name="shopForm" property="vo.isauthen" value="<%=ShopConstant.IsAuthen_no.toString() %>">
			              <img src="<%=request.getContextPath() %>/styles/imgs/noVerified.gif" title="未认证学校"/>
			              <a href="javascript:void(0)" onclick="newDiv('/shop/applyshopauthen.do?shopid=<bean:write name="shopForm" property="vo.shopid"/>&backUrlEncode=<bean:write name="shopForm" property="backUrlEncode"/>',0,1,550,420);return false;">更改认证</a>
			         </logic:equal>
			         <logic:equal name="shopForm" property="vo.isauthen" value="<%=ShopConstant.IsAuthen_pass.toString() %>">
		                <img src="<%=request.getContextPath() %>/styles/imgs/verified.gif" title="已认证学校"/>
		            </logic:equal>
			         </span>
			     </td>
			     <td id="isauthenId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left" style="vertical-align: bottom;">收费类型:</td>
			     <td class="login_field">
			         <bean:writeSaas name="shopForm" property="vo.chargetype" consCode="platform.ShopConstant.ChargeType"/>
			         &nbsp;<img src="../../styles/imgs/help.png" style="cursor:pointer;" title='修改收费类型，请点击上面"更改认证"'>
			     </td>
			     <td id="chargetypeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <logic:equal name="shopForm" property="vo.chargetype" value="<%=ShopConstant.ChargeType_paid.toString() %>">
			  <tr>
			     <td class="field_left">支付信息:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:writeSaas name="shopForm" property="extvo.payinfo"/></span>
			     </td>
			     <td id="shopstatusId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  </logic:equal>
			  
			  <tr>
			     <td class="field_left">商店状态:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:writeSaas name="shopForm" property="vo.shopstatus" consCode="ShopConstant.ShopStatus"/></span>
			     </td>
			     <td id="shopstatusId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">注册时间:</td>
			     <td class="login_field">
			         <span class="user_input"><bean:write name="shopForm" property="vo.regisdate" format="yyyy-MM-dd"/></span>
			     </td>
			     <td id="shopstatusId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>

	      </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button style="font-size:20px;" onclick="goUrl('/shop/editshoppage.do?queryVO.shopid=<bean:write name="shopForm" property="vo.shopid"/>&backUrlEncode=', 'backUrlEncode');return false;">修改</button></li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
