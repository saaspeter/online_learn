<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,netTestWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="bannerimgsrc" name="shopstyleconfigForm" property="vo.bannerimg" type="java.lang.String"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>查看商店信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
	<style>
	    .field_left{
			width: 25%;
			padding-right: 7px;
			text-align:right;
			margin:2px;
			font-size: larger;
		}

		.login_field{
			width: 65%;
		}
		
		.user_input{
            width: 80%;
			font-size: larger;
		}
		
		.login_fieldDesc{
			padding-left:5px;
			text-align:left;
			font-size:15px;
			color:#999;
		}
	</style>
    <script type="text/javascript" src="../../styles/script/pageAction.js"></script>
    <script type="text/javascript">
        function changeimg(){
        	submitForm("editForm");
        	//var src = document.getElementById("ID_uploadFile").value;
        	//if(src!=null && src!=''){
        	//	document.getElementById("picPreviewId").src=src;
        	//	document.getElementById("picPreviewTrId").style.display="block";
        	//}
        }
        
    </script>
    

  </head>
  
  <body>
    
	<html:form styleId="editForm" action="/shop/saveshopstyle.do" method="post" enctype="multipart/form-data">	
	  <div class="fieldsTitleDiv">商店样式编辑</div>
	  <div id="fieldDisDiv">
	     <table cellspacing="7" cellpadding="0" border="0" width="100%">
			  <tr>
			     <td class="field_left">商店logo图片:</td>
			     <td class="login_field">
			         <%if(bannerimgsrc==null || bannerimgsrc.length()<1) { %>
				         <img id="bannerimgId" style="width:310; height:65px;" alt="" src="/<%=CommonConstant.WebContext_Education %>/styles/imgs/logo_platform.jpg">
 				         <br><font style="font-size: smaller;color: #ff0000">(没有定义shop style banner图片，因此使用系统默认图片)</font>
                     <%}else { %>
				         <img id="bannerimgId" style="width:310; height:65px;" alt="" src="<%=WebConstant.FileContext+bannerimgsrc %>" >
				     <%} %>
			     </td>
			     <td class="login_fieldDesc"></td>
			  </tr>
			  <tr>
			      <td colspan="3" style="height: 40px;">&nbsp;</td>
			  </tr>
			  <tr>
			     <td class="field_left">指定商店logo图片:</td>
			     <td class="login_field">
			        <span class="user_input">
			            <input id="ID_uploadFile" type="file" name="uploadFile" style="width:85%;font-size: 18px;" onchange="changeimg();">
			        </span>
			        <br><font style="font-size: smaller;color: #ff0000">(图片必须是300*60像素，小于2MB)</font>
			     </td>
			     <td id="shopnameId_tip_td" class="login_fieldDesc"></td>
			  </tr>
			  <tr id="picPreviewTrId" style="display:none;">
			     <td class="field_left">图片预览:</td>
			     <td class="login_field">
			        <img id="picPreviewId" src="">
			     </td>
			     <td></td>
			  </tr>
	      </table>
	  </div>

	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li>
		        
		    </li>
		 </ul>
	  </div>

	  <div id="buttomDiv"></div>

	</html:form>
  </body>
</html:html>
