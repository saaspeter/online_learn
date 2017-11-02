<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>编辑培训商店</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    
    <link rel="stylesheet" type="text/css" media="all" href="../styles/css/calendar/calendar-blue.css" title="win2k-cold-1" />
   <script type="text/javascript" src="../styles/script/calendar/calendar.js"></script>
   <script type="text/javascript" src="../styles/script/calendar/lang/calendar-zh.js"></script>
   <script type="text/javascript" src="../styles/script/calendar/calendar-setup.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/shop/updateSaveShop.do" method="post">	
	 
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopForm" property="backUrlEncode"/>">	
	 <input type="hidden" name="vo.shopid" value="<bean:write name="shopForm" property="queryVO.shopid"/>">
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
	
	  <div id="fieldsTitleDiv"><bean:message key="platform.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="fieldDisDiv">
		<table width="100%" align="center"  bgcolor="#efefef" border="1px" cellpadding="0" cellspacing="0" bordercolor="#ffffff">
			<tr align="center">
		       <td colspan="4" bgcolor="#efefef"><H3><bean:write name="shopForm" property="vo.shopname"/></H3></td>
		    </tr>
		    <tr>
		       <td width="150px" align="right">商店代号:</td>
		       <td>
		          <input type="text" name="vo.shopcode" style="width:150px" value="<bean:write name="shopForm" property="vo.shopcode"/>"/>
		       </td>
		       <td width="150px" align="right">商店类型:</td>
		       <td>
		           <html:select name="shopForm" property="vo.shoptype" style="width:150px;" disabled="true">
				      <html:optionsCollection name="shopForm" property="shopTypeList"/>
		           </html:select>
		       </td>
		    </tr>

		    <tr>
		       <td width="150px"  align="right">商店注册地:</td>
		       <td>
		           <html:select name="shopForm" property="vo.localeid" style="width:150px;" disabled="true">
				      <html:optionsSaas localeListSet="true"/>
		           </html:select>
		       </td>
		       <td width="150px"  align="right">是否开放:</td>
		       <td align="left">        
			      <html:select name="shopForm" property="vo.isopen" style="width:150px;">
					<html:optionsSaas consCode="ShopConstant.OpenType"/>
			      </html:select>
		       </td>
		    </tr>

		    <tr align="center">
		       <td width="150px"  align="right">是否是试用:</td>
		       <td align="left">
		          <html:select name="shopForm" property="vo.istry" style="width:150px;">
				      <html:optionsSaas consCode="ShopConstant.TryType"/>
		          </html:select>
		       </td>
		       <td width="150px"  align="right">注册时间:</td>
		       <td align="left">
		           <input id="regisdate" type="text" name="vo.regisdate" value="<bean:write name="shopForm" property="vo.regisdate" format="yyyy-MM-dd hh:mm:ss"/>" readonly/>
		       </td>
		    </tr>
		    
		    <tr align="center">
		       <td width="150px"  align="right">到期时间:</td>
		       <td align="left">
		           <input id="enddate" type="text" name="vo.enddate" value="<bean:write name="shopForm" property="vo.enddate" format="yyyy-MM-dd hh:mm:ss"/>" readonly />
		       </td>
		       <td width="150px"  align="right">人员数量:</td>
		       <td align="left">
		           <input type="text" name="vo.usernum" value="<bean:write name="shopForm" property="vo.usernum"/>"/>
		       </td>
		    </tr>
		    
		    <tr align="center">
		       <td width="150px"  align="right">商店状态:</td>
		       <td align="left">
		          <html:select name="shopForm" property="vo.shopstatus" style="width:150px;" disabled="true">
				      <html:optionsSaas consCode="ShopConstant.ShopStatus"/>
		          </html:select>
		       </td>
		       <td></td>
		       <td></td>
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
	       <iframe width="100%" height="100%" src="<%=WebConstant.WebContext %>/shop/shopvalue.do?method=list&queryVO.shopid=<bean:write name="shopForm" property="vo.shopid"/>" scrolling="auto" frameborder="0"></iframe>
	   </div>
	   
	   <div id="content2" style="width:100%;height:230px;display:none">
	      <iframe width="100%" height="100%"  src="<%=WebConstant.WebContext %>/shop/shopfunc.do?method=list&queryVO.shopid=<bean:write name="shopForm" property="vo.shopid"/>" scrolling="auto" frameborder="0"></iframe>
       </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	<script type="text/javascript">
    Calendar.setup({
        inputField     :    "regisdate",      // id of the input field
        ifFormat       :    "%Y-%m-%d %H:%M:00",       // format of the input field
        showsTime      :    true,            // will display a time selector
		timeFormat     :    "24",
        singleClick    :    false,           // double-click mode
        step           :    1                // show all years in drop-down boxes (instead of every other year as default)
    });
	</script>
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