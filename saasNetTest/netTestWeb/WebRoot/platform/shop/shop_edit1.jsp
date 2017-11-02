<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shopappForm" property="jsSuffix" type="java.lang.String"/>
    <title>新增培训商店</title>

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
	   
	   function checkShopname(nameId){
          var shopnameObj = document.getElementById(nameId);
	  	  var result = null;
	  	  var retArr = null;
	  	  var message = "";
	      if(shopnameObj!=null&&shopnameObj.value!=''){
	        dwr.engine.setAsync(false);
	        shopcheck.checkDepulicateShop(shopnameObj.value,null,function CB_checkShopname(rtn){
	           if(rtn=='3'){
	           	  message = shopnameObj.value+","+getMessage("shopnameDepu");
			      result = false;
			   }else if(rtn=='1'){
			      message = "ok";
			      result = true;
	           }else{
	           	  alert(getMessage("systemError"));
	           }
	           retArr = new Array(result, message);
	        });
	      }
	      return retArr;
	   }
	   
	   function checkShopcode(codeId){
          var shopcodeObj = document.getElementById(codeId);
	  	  var result = null;
	  	  var retArr = null;
	  	  var message = "";
	      if(shopcodeObj!=null&&shopcodeObj.value!=''){
	        dwr.engine.setAsync(false);
	        shopcheck.checkDepulicateShop(null,shopcodeObj.value,function CB_checkShopcode(rtn){
	           if(rtn=='2'){
	           	  message = shopcodeObj.value+","+getMessage("shopcodeDepu");
			      result = false;
			   }else if(rtn=='1'){
			      message = "ok";
			      result = true;
	           }else{
	           	  alert(getMessage("systemError"));
	           }
	           retArr = new Array(result, message);
	        });
	      }
	      return retArr;
	   }
	   
	//-->
	</script>
  </head>
  
  <body>
	<div class="centerstyle">
	
	<div class="fieldsTitleDiv">商店基本配置</div>
	
	<html:form styleId="editForm" action="/shop/saveApplyNewShop.do" method="post">	
	<bean:define id="locale" name="shopappForm" property="locale" type="java.util.Locale"></bean:define>
    <input id="prodcatesId" type="hidden" name="prodcates" value="">
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
		  <table cellspacing="7" cellpadding="0" border="0" width="100%">
			  <tr>
			     <td class="field_left">商店:</td>
			     <td class="login_field" colspan="2"><bean:write name="shopForm" property="vo.shopname"/>
			         (<bean:write name="shopForm" property="vo.shopcode"/>
			     </td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">所属行业:</td>
			     <td class="login_field">
			         <html:select name="shopappForm" property="vo.bizarea" styleClass="user_input">
			            <html:option value=""></html:option>
					    <html:optionsSaas consCode="common.bizArea"/>
			         </html:select>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc"><i></i><span id="shopcodeId_tip"></span></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店类型:</td>
			     <td class="login_field">
			         <html:select name="shopappForm" property="vo.ownertype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.OwnerType" param1="register"/>
			         </html:select>
			     </td>
			     <td id="ownertypeId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店开放类型:</td>
			     <td class="login_field" colspan="2">
			         <html:select name="shopappForm" property="vo.opentype" styleClass="user_input">
					    <html:optionsSaas consCode="platform.ShopConstant.OpenType"/>
			         </html:select>
			     </td>
			  </tr>
			  
			  <tr>
			     <td class="field_left">商店人数规模:</td>
			     <td class="login_field">
			         <html:select name="shopappForm" property="vo.usernumscale" styleClass="user_input">
			            <html:option value=""></html:option>
					    <html:optionsSaas consCode="platform.ShopConstant.UserNumScale"/>
			         </html:select>
			     </td>
			     <td id="shopcodeId_tip_td" class="login_fieldDesc">&nbsp;</td>
			  </tr>

	      </table>
		
      </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button style="font-size:25px;" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>

	</div>
	
	<script language="JavaScript" type="text/JavaScript">
    <!-- 
        var localeidVar = '<bean:write name="shopappForm" property="vo.localeid"/>';
        initRegion(localeidVar, '','');
        
    //-->
    </script>
	
  </body>
</html:html>
