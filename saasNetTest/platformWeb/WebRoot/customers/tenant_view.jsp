<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.KeyInMemoryConstant,platformWeb.base.WebConstant"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>公司客户信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
  </head>
  
  <body>
	<div class="editPage">
	<input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="tenantForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="tenantForm" property="backUrlEncode"/>">	
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="platform.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv">公司客户信息</div>
	  
	  <div id="displayMessDiv">
	     <% String disMessKey = request.getAttribute(KeyInMemoryConstant.DisMessKey)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessKey));
            String disMessArg0Key = request.getAttribute(KeyInMemoryConstant.DisMessArg0Key)==null?null:((String)request.getAttribute(KeyInMemoryConstant.DisMessArg0Key));
            if(disMessKey!=null&&disMessKey.trim().length()>0){
            if(disMessArg0Key==null||disMessArg0Key.trim().length()<1){
         %>
         <bean:message key="<%=disMessKey %>" bundle="BizKey"/>
         <% }else{%>
         <bean:message key="<%=disMessKey %>" bundle="BizKey" arg0="<%=disMessArg0Key %>"/>
         <%}} %>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
            <li>
			   <div class="fieldLabel">用户帐号:</div>
			   <div class="field"> 
			     <logic:empty name="tenantForm" property="vo.loginname">
			         <input id="loginname" type="text" name="vo.loginname" value="<bean:write name="tenantForm" property="vo.loginname"/>" onchange="checkUser('loginname','该帐号已经被注册，请重新选择！','该帐号还未使用，您可以继续！');"/>
			     </logic:empty>
			     <logic:notEmpty name="tenantForm" property="vo.loginname">
			         <bean:write name="tenantForm" property="vo.loginname"/>
			     </logic:notEmpty>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">密码提示问题:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="custinfoex.tipquestion"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">问题答案:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="custinfoex.tipanswer"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">用户昵称:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="custinfoex.nickname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

            <li>
			   <div class="fieldLabel">**客户名称:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="vo.name"/>
			   </div>
			   <div class="fieldDesc">请输入贵公司机构的名称</div>
			</li>
			
              
		    <li>
			   <div class="fieldLabel">公司营业执照号:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="vo.companycode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">所在行业:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="vo.industry"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">人数:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="vo.usernums"/>
			   </div>
			   <div class="fieldDesc">请输入您所在公司或机构的人数</div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在国家:</div>
			   <div class="field"> 
			     <html:select name="tenantForm" property="vo.localeid" disabled="true">
					<html:optionsCollection name="tenantForm" property="localesList"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
						
			<li><div style="text-align:center;width:100%;background-color:#dddddd;">用户状态</div></li>
			
            <li>
			   <div class="fieldLabel">用户状态:</div>
			   <div class="field"> 
			   	   <bean:write name="tenantForm" property="vo.status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li><div style="text-align:center;width:100%;background-color:#dddddd;">用户联系信息</div></li>
			
			<li>
			   <div class="fieldLabel">所在身份(州):</div>
			   <div class="field"> 
			     <select>
			       <option>aa</option>
			     </select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在市(地区):</div>
			   <div class="field"> 
			     <select>
			       <option>aa</option>
			     </select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">所在地址:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="contractinfo.address"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>		


		    <li>
			   <div class="fieldLabel">电子邮箱:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="contractinfo.email"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">电话号码:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="contractinfo.telephone"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">帐户金额:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="vo.lcoinremain"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel">简要描述:</div>
			   <div class="field"> 
			     <bean:write name="tenantForm" property="custinfoex.desc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>			

		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="platform.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>

	</div>
  </body>
</html:html>
