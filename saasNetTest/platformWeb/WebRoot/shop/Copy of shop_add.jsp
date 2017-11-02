<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.SysfunctionitemConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增培训商店</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    
    <link rel="stylesheet" type="text/css" media="all" href="../styles/css/calendar/calendar-blue.css" title="win2k-cold-1" />
   <script type="text/javascript" src="../styles/script/calendar/calendar.js"></script>
   <script type="text/javascript" src="../styles/script/calendar/lang/calendar-zh.js"></script>
   <script type="text/javascript" src="../styles/script/calendar/calendar-setup.js"></script>
   <script language="javascript">
	<!--
	   function selectCate(){
	      newDiv("/productcategory/productcategory.do?method=list&toListType=2",1,0,670,450);
	   }
	   
       function selectCateCallBack(ids,names){
          if(ids!=null&&ids!=""){
             document.getElementById("prodcatesId").value=ids;
             document.getElementById("prodcateNamesId").value=names;
          }
          clearDiv();
       }
	//-->
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/saveApplyNewShop.do" method="post">	
	<bean:define id="locale" name="shopForm" property="locale" type="java.util.Locale"></bean:define>
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">	
	<input id="prodcatesId" type="hidden" name="prodcates" value="">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
				 <li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">开设商店</div>
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
			<br>
			<table width="90%" align="center">
				<tr>
			       <td colspan="4" bgcolor="#eeeeee">基础信息：</td>
			    </tr>
			    <tr>
			       <td>商店名称:</td>
			       <td colspan="3">
			          <input type="text" name="vo.shopname" style="width:550px"/>
			       </td>
			    </tr>
			    <tr>
			       <td>商店代号:</td>
			       <td colspan="3">
			          <input type="text" name="vo.shopcode" style="width:550px"/>
			       </td>
			    </tr>
			    <tr>
			       <td>商店类型:</td>
			       <td>
			           <html:select name="shopForm" property="vo.shoptype" style="width:150px;">
					      <html:optionsSaas consCode="platform.ShopConstant.ShopType"/>
			           </html:select>
			       </td>
			       <td>商店注册地:</td>
			       <td>
			           <html:select name="shopForm" property="vo.localeid" style="width:150px;">
					      <html:optionsSaas localeListSet="true"/>
			           </html:select>
			       </td>
			    </tr>
			    <tr>
			       <td>是否开放:</td>
			       <td>        
				      <html:select name="shopForm" property="vo.isopen" style="width:150px;">
						 <html:optionsSaas consCode="ShopConstant.OpenType"/>
				      </html:select>
			       </td>
			       <td>是否是试用:</td>
			       <td>
			          <html:select name="shopForm" property="vo.istry" style="width:150px;">
					     <html:optionsSaas consCode="ShopConstant.TryType"/>
			          </html:select>
			       </td>
			    </tr>

			    <tr>
			       <td>人员数量:</td>
			       <td>
			           <input type="text" name="vo.usernum" />
			       </td>
			       <td>商店简述:</td>
			       <td>
			           <input type="text" name="vo.shopDesc_sim" />
			       </td>
			    </tr>

			    <tr>
			       <td>商店介绍:</td>
			       <td colspan="3">
			           <textarea name="vo.shopDesc" rows="" cols="60"></textarea>
			       </td>
			    </tr>
			</table>
			<br>
			<table width="90%" align="center">
			   <tr bgcolor="#eeeeee"><td colspan="3">商店开设课程目录：</td></tr>
			   <tr>
			       <td width="80px;">&nbsp;</td>
			       <td align="left" width="450px">
			           <textarea id="prodcateNamesId" rows="" cols="60" readonly="readonly"></textarea>
			       </td>
			       <td align="left">
			          <button type="button" onclick="selectCate();return false;">选择课程目录</button>
			       </td>
			    </tr>
			</table>
			<br>
          	<table width="90%" align="center">
			   <tr bgcolor="#eeeeee"><td colspan="5">平台系统功能：</td></tr>
			       <logic:iterate id="vo" name="shopForm" property="funcPlatList">
			          <bean:define id="funcode" name="vo" property="functioncode" type="java.lang.String"></bean:define>
			          <bean:define id="systemid" name="vo" property="systemid" type="java.lang.String"/>
			          <tr>
			             <td>
			               <input type="checkbox" name="funcArr" value="<bean:write name="vo" property="functioncode"/>" >
			             </td>
			             <td><%=SysfunctionitemConstant.qryFuncName(funcode,locale)%></td>
			             <td><%=CommonConstant.qrySystemName(systemid,locale)%></td>
			             <td><bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/></td>
			             <td><bean:write name="vo" property="funcdesc"/></td>
			          </tr>
			       </logic:iterate>
			</table>
			<br>
			<table width="90%" align="center">
			   <tr bgcolor="#eeeeee"><td colspan="5">教育系统功能：</td></tr>
			   
			       <logic:iterate id="vo" name="shopForm" property="funcEduList">
			          <bean:define id="funcode" name="vo" property="functioncode" type="java.lang.String"></bean:define>
			          <bean:define id="systemid" name="vo" property="systemid" type="java.lang.String"/>
			          <bean:define id="paytype" name="vo" property="paytype" type="java.lang.Short"/>
			           <tr>
				          <td>
				              <input type="checkbox" name="funcArr" value="<bean:write name="vo" property="functioncode"/>" >
				          </td>
				          <td><%=SysfunctionitemConstant.qryFuncName(funcode,locale)%></td>
				          <td><%=CommonConstant.qrySystemName(systemid,locale)%></td>
				          <td><bean:writeSaas name="vo" property="paytype" consCode="Common.PayTypeConstant.PayType"/></td>
				          <td><bean:write name="vo" property="funcdesc"/></td>
			          </tr>
			       </logic:iterate>
			   
			</table>

			
      </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>

	</div>
  </body>
</html:html>
