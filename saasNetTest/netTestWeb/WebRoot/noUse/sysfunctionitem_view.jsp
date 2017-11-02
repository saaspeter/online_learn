<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>功能详情</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel">功能编码:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.functioncode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">功能名称:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.functionname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">所属系统:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.systemname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">支付方式:</div>
			   <div class="field"> 
			      <html:select name="sysfunctionitemForm" property="vo.paytype" disabled="true">
					 <html:optionsSaas consCode="Common.PayTypeConstant.PayType"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">价格:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.cost"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">功能类型:</div>
			   <div class="field"> 
			      <logic:equal name="sysfunctionitemForm" property="vo.ismust" value="<%=String.valueOf(CommonConstant.yes) %>">必须功能</logic:equal>
			      <logic:equal name="sysfunctionitemForm" property="vo.ismust" value="<%=String.valueOf(CommonConstant.no) %>">可选功能</logic:equal>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">生效日期:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.startdate" format="yyyy-MM-dd"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">有效性:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">描述:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.funcdesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
