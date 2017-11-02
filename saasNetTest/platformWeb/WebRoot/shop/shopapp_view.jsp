<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant, platform.constant.ShopappConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>审核申请的商店</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	 <script type="text/javascript">
	 <!--
	   function delshopapp(id){
	      if(confirm('确定要删除该商店申请吗?')){
	         var url = "<%=WebConstant.WebContext%>/shop/delshopapp.do?vo.shopappid="+id;
             var param = null;
	         var rtnObj = toAjaxUrl(url, false, param);
             alert(rtnObj.info);
             parent.flushPage();
             parent.clearDiv();
          }
	   }
	   
     //-->
     </script>
  </head>
  
  <body>
	<div class="editPage">	 	
	  <div id="fieldsTitleDiv">查看审核商店记录</div>
	  
	  	  <div id="fieldDisDiv">
			<table width="96%" align="center" border="1px" cellpadding="4" cellspacing="4" bordercolor="#efefef">
			    <tr>
			       <td align="right">商店名称(代号):</td>
			       <td colspan="3">
			           <bean:write name="shopappForm" property="vo.shopname"/>&nbsp;(<bean:write name="shopappForm" property="vo.shopcode"/>)
			       </td>
			    </tr>
			    <tr>
			       <td align="right">所属行业:</td>
			       <td align="left">
			          <bean:writeSaas name="shopappForm" property="vo.bizarea" consCode="common.bizArea"/>
			       </td>
			       <td align="right">商店类型:</td>
			       <td align="left">
			           <bean:writeSaas name="shopappForm" property="vo.ownertype" consCode="platform.ShopConstant.OwnerType"/>
			       </td>
			    </tr>
			    
			    <tr align="center">
			       <td align="right">商店开放类型:</td>
			       <td align="left">
			           <bean:writeSaas name="shopappForm" property="vo.opentype" consCode="platform.ShopConstant.OpenType"/>
			       </td>
			       <td align="right">商店人数规模:</td>
			       <td align="left">
			           <bean:writeSaas name="shopappForm" property="vo.usernumscale" consCode="platform.ShopConstant.UserNumScale"/>
			       </td>
			    </tr>
			    
			    <tr align="center">
			       <td align="right">电子邮件:</td>
			       <td align="left">
			           <bean:write name="shopappForm" property="vo.email" />
			       </td>
			       <td align="right">电话/手机:</td>
			       <td align="left">
			           <bean:write name="shopappForm" property="vo.telephone" />
			       </td>
			    </tr>
			    
			    <tr align="center">
			       <td align="right">联系人:</td>
			       <td align="left">
			           <bean:write name="shopappForm" property="vo.contactname" />
			       </td>
			       <td align="right">商店所在地:</td>
			       <td align="left">
			           <bean:writeSaas name="shopappForm" property="vo.regioncode" consCode="CountryregionConstant.RegionCode"/>
			       </td>
			    </tr>
			    
			    <tr align="center">
			       <td align="right">商店地址:</td>
			       <td align="left" colspan="3">
			           <bean:write name="shopappForm" property="vo.address" />
			       </td>
			    </tr>
			    
			    <tr align="center">
			       <td align="right">审核状态:</td>
			       <td align="left" colspan="3">
			           <bean:writeSaas name="shopappForm" property="vo.appstatus" consCode="platform.ShopappConstant.AppStatus"/>
			       </td>
			    </tr>
			    
			    <tr align="center">
			       <td align="right">审核说明:</td>
			       <td align="left" colspan="3">
			           <bean:write name="shopappForm" property="vo.notes"/>
			       </td>
			    </tr>
			</table>
	    </div>
	    
	    <logic:notEqual name="shopappForm" property="vo.appstatus" value="<%=ShopappConstant.AppStatus_pass.toString() %>" >
	    <div style="width: 100%; text-align: center; margin-top: 20px;">
	        <button style="font-size: larger;" onclick="delshopapp('<bean:write name="shopappForm" property="vo.shopappid"/>');return false;">撤销商店申请</button>
	    </div>
	    </logic:notEqual>
	</div>
  </body>
</html:html>