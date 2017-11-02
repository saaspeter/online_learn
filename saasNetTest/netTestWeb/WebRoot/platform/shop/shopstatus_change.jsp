<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.KeyInMemoryConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>商店状态更改</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>
    <script language="JavaScript" type="text/JavaScript">
    <!--
       function submitPage(){
          var orginStatus = document.getElementById("orginStatus").value;
          var statusObj = document.getElementById("statusId");  
          var nowStatus = statusObj.options[statusObj.selectedIndex].value;
          if(orginStatus==nowStatus){
             showMessagePage("请选择改变后的状态!");
             return;
          }
          submitForm('editForm');
       }
       
    //-->
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/saveShopStatus.do" method="post">	
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">	
	<input type="hidden" name="vo.shopid" value="<bean:write name="shopForm" property="queryVO.shopid"/>">
	<input type="hidden" id="orginStatus" value="<bean:write name="shopForm" property="vo.shopstatus"/>"/>
	
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  	  <div id="fieldDisDiv">
			<table class="formTable2">

			    <tr>
			       <td width="150px" align="right">商店类型:</td>
			       <td>
			           <html:select name="shopForm" property="vo.ownertype" style="width:250px;" disabled="true">
					      <html:optionsSaas consCode="platform.ShopConstant.OwnerType"/>
			           </html:select>
			       </td>
			       <td width="150px"  align="right">商店注册地:</td>
			       <td>
			           <html:select name="shopForm" property="vo.localeid" style="width:250px;" disabled="true">
					      <html:optionsSaas localeListSet="true"/>
			           </html:select>
			       </td>
			    </tr>
			    <tr align="center">
			       <td width="150px"  align="right">是否开放:</td>
			       <td align="left">        
				      <html:select name="shopForm" property="vo.opentype" style="width:250px;" disabled="true">
						<html:optionsSaas consCode="platform.ShopConstant.OpenType"/>
				      </html:select>
			       </td>
			       <td width="150px"  align="right">注册时间:</td>
			       <td align="left">
			           <bean:write name="shopForm" property="vo.regisdate" format="yyyy-MM-dd hh:mm:ss"/>
			       </td>
			    </tr>
			    <tr align="center">
			       <td width="150px"  align="right">人员数量:</td>
			       <td align="left" colspan="3">
			           <bean:write name="shopForm" property="vo.usernum"/>
			       </td>
			    </tr>
			    <tr align="center"><td colspan="4" bgcolor="#efefef">商店状态</td></tr>
			    <tr align="center">
			       <td width="150px"  align="right">商店原状态:</td>
			       <td align="left">
			           <bean:writeSaas name="shopForm" property="vo.shopstatus" consCode="ShopConstant.ShopStatus"/>
			       </td>
			       <td width="150px"  align="right">改变后状态:</td>
			       <td align="left">
			            <html:select name="shopForm" property="vo.shopstatus" style="width:250px;" styleId="statusId">
			                <html:optionsSaas consCode="ShopConstant.ShopStatus"/>
			            </html:select>
			       </td>
			    </tr>
			    <tr align="center">
			       <td width="150px"  align="right">状态改变描述:</td>
			       <td align="left" colspan="3">
			           <input name="logVO.statusdisc" style="width:90%;">
			       </td>
			    </tr>
			</table>
	   </div>
	   
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitPage();"><bean:message key="netTest.commonpage.save"/></button></li>
		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>

	</div>
  </body>
</html:html>