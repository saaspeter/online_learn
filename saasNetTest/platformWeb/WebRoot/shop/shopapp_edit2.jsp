<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>审核申请的商店</title>

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

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/shopapp.do?method=saveApplyInfo" method="post">	
	 
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopappForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopappForm" property="backUrlEncode"/>">	
	<input type="hidden" name="shopvo.shopid" value="<bean:write name="shopappForm" property="shopvo.shopid"/>">
	<input type="hidden" name="shopvo.shopname" value="<bean:write name="shopappForm" property="shopvo.shopname"/>">
	<input type="hidden" name="shopvo.shopcode" value="<bean:write name="shopappForm" property="shopvo.shopcode"/>">
	<input type="hidden" name="vo.shopappid" value="<bean:write name="shopappForm" property="vo.shopappid"/>">
	  <div id="functionLineDiv">
	  	  <div id="functionBarTopDiv">&gt;&gt;审核商店</div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="displayMessDiv">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  	  <div id="fieldDisDiv">
			<table width="100%" align="center"  bgcolor="#efefef" border="1px" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
				<tr align="center">
			       <td colspan="4" bgcolor="#efefef"><H3><bean:write name="shopappForm" property="vo.shopname"/></H3></td>
			    </tr>
			    <tr>
			       <td width="150px" align="right">商店类型:</td>
			       <td>
			           <html:select name="shopappForm" property="shopvo.shoptype" style="width:150px;" disabled="true">
					      <html:optionsCollection name="shopappForm" property="shopTypeList"/>
			           </html:select>
			       </td>
			       <td width="150px"  align="right">商店注册地:</td>
			       <td>
			           <html:select name="shopappForm" property="shopvo.localeid" style="width:150px;" disabled="true">
					      <html:optionsCollection name="shopappForm" property="localesList"/>
			           </html:select>
			       </td>
			    </tr>
			    <tr align="center">
			       <td width="150px"  align="right">是否开放:</td>
			       <td align="left">        
				      <html:select name="shopappForm" property="shopvo.isopen" style="width:150px;">
						<html:optionsCollection name="shopappForm" property="openTypeList"/>
				      </html:select>
			       </td>
			       <td width="150px"  align="right">是否是试用:</td>
			       <td align="left">
			          <html:select name="shopappForm" property="shopvo.istry" style="width:150px;">
					      <html:optionsCollection name="shopappForm" property="tryTypeList"/>
			          </html:select>
			       </td>
			    </tr>
			    <tr align="center">
			       <td width="150px"  align="right">注册时间:</td>
			       <td align="left">
			           <bean:write name="shopappForm" property="shopvo.regisdate" format="yyyy-MM-dd hh:mm:ss"/>
			       </td>
			       <td width="150px"  align="right">到期时间:</td>
			       <td align="left">
			           <input id="enddate" type="text" name="shopvo.enddate" value="<bean:write name="shopappForm" property="shopvo.enddate" format="yyyy-MM-dd hh:mm:ss"/>" readonly />
			       </td>
			    </tr>
			    <tr align="center">
			       <td width="150px"  align="right">人员数量:</td>
			       <td align="left" colspan="3">
			           <input type="text" name="shopvo.usernum" value="<bean:write name="shopappForm" property="shopvo.usernum"/>"/>
			       </td>
			    </tr>
			</table>
	   </div>
	   
	  <div>
	     <table width="100%" align="center"  bgcolor="#efefef" border="1px" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
			<tr align="center">
			   <td colspan="4" bgcolor="#efefef"><H3>商店审核信息</H3></td>
			</tr>
			<tr align="center">
			   <td width="150px" align="right">上次审核状态:</td>
			   <td align="left">
			       <html:select name="shopappForm" property="vo.appstatus0" disabled="true">
			          <html:option value=""></html:option>
					  <html:optionsCollection name="shopappForm" property="appstatusList"/>
			       </html:select>
			   </td>
			   <td width="150px" align="right">上次操作时间:</td>
			   <td align="left">
			      <bean:write name="shopappForm" property="vo.replydate" format="yyyy-MM-dd hh:mm:ss"/>
			   </td>
			</tr>
			<tr align="center">
			   <td width="150px" align="right">当前审批状态:</td>
			   <td align="left">
			       <html:select name="shopappForm" property="vo.appstatus" disabled="true">
					  <html:optionsCollection name="shopappForm" property="appstatusList"/>
			       </html:select>
			   </td>
			   <td width="150px" align="right">所需费用:</td>
			   <td align="left">
			      <bean:write name="shopappForm" property="vo.cost" />
			   </td>
		   <tr align="center">
			   <td width="150px" align="right">审批说明:</td>
			   <td align="left" colspan="3">
			      <bean:write name="shopappForm" property="vo.notes"/>
			   </td>
		    </tr>
		 </table>
	  </div>
	   
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="platform.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="platform.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	   
	   <div>
	    <TABLE WIDTH="100%" bgcolor="#eeeeee" align="center" cellSpacing="0">
        <TBODY>
        <TR><TD height="3"></TD></TR>
        <TR>
          <TD width=1></TD>
          <TD id="tab1" bgcolor="#0099ff" width="130px"  align="center" onclick="showTab(1,3)">开设地区设置</TD>
          <TD width=1></TD>
          <TD id="tab2" bgcolor="#00ccff" width="130px"  align="center" onclick="showTab(2,3)">商店具有功能</TD>
          <TD width=1></TD>
          <TD id="tab3" bgcolor="#00eeff" width="130px"  align="center" onclick="showTab(3,3)">课程目录</TD>
          <TD></TD>
        </TR>
        </TBODY>
        </TABLE>
	   </div>
	   <div id="content1" style="width:100%;height:230px;">
	       <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>//shop/listshopvalue.do?queryVO.shopid=<bean:write name="shopappForm" property="vo.shopid"/>" scrolling="auto" frameborder="0"></iframe>
	   </div>
	   
	   <div id="content2" style="width:100%;height:230px;display:none">
	      <iframe width="100%" height="100%"  src="<%=WebConstant.WebContext %>/shop/shopfunc.do?method=list&queryVO.shopid=<bean:write name="shopappForm" property="vo.shopid"/>" scrolling="auto" frameborder="0"></iframe>
       </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	<script type="text/javascript">
	    Calendar.setup({
	        inputField     :    "enddate",      // id of the input field
	        ifFormat       :    "%Y-%m-%d %H:%M:00",       // format of the input field
	        showsTime      :    true,            // will display a time selector
			timeFormat     :    "24",
	        singleClick    :    false,           // double-click mode
	        step           :    1                // show all years in drop-down boxes (instead of every other year as default)
	    });
	</script>
	</div>
  </body>
</html:html>