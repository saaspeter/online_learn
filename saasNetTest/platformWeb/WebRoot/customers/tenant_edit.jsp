<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.KeyInMemoryConstant,platformWeb.base.WebConstant"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="tenantForm" property="jsSuffix" type="java.lang.String"/>
    <title>公司客户信息编辑页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/customer.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/timezone/timezone<%=jsSuffix %>.js"></script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/customers/saveTenant.do" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="tenantForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="tenantForm" property="backUrlEncode"/>">	
     <input type="hidden" name="vo.userid" value="<bean:write name="tenantForm" property="vo.userid"/>">
	 <input type="hidden" name="vo.usertype" value="<bean:write name="tenantForm" property="vo.usertype"/>"/>
	 <input type="hidden" name="contractinfo.contractinfoid" value="<bean:write name="tenantForm" property="contractinfo.contractinfoid"/>"/>
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
		  <div id="toUrl">
		      <bean:message key="platform.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="platform.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv" style="background-color:#eeeeee;">公司客户信息</div>
	  
	  <div id="displayMessDiv" style="background-color:#eeeeee;color:#ff0000;">
         <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
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
			   <div class="fieldLabel">用户昵称:</div>
			   <div class="field"> 
			     <input type="text" name="custinfoex.nickname" value="<bean:write name="tenantForm" property="custinfoex.nickname"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

            <li>
			   <div class="fieldLabel">**客户名称:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="tenantForm" property="vo.name"/>"/>
			   </div>
			   <div class="fieldDesc">请输入贵公司机构的名称</div>
			</li>
			
              
		    <li>
			   <div class="fieldLabel">公司营业执照号:</div>
			   <div class="field"> 
			     <input type="text" name="vo.companycode" value="<bean:write name="tenantForm" property="vo.companycode"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">所在行业:</div>
			   <div class="field"> 
			     <input type="text" name="vo.industry" value="<bean:write name="tenantForm" property="vo.industry"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			

		    <li>
			   <div class="fieldLabel">人数:</div>
			   <div class="field"> 
			     <input type="text" name="vo.usernums" value="<bean:write name="tenantForm" property="vo.usernums"/>"/>
			   </div>
			   <div class="fieldDesc">请输入您所在公司或机构的人数</div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在国家:</div>
			   <div class="field"> 
			       <html:optionsSaas localeListSet="true"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">所在时区:</div>
			   <div class="field"> 
			      <select name="vo.timezone" id="timezoneId"></select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li><div style="text-align:center;width:100%;background-color:#eeeeee;">用户联系信息</div></li>
			
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
			     <input type="text" name="contractinfo.address" value="<bean:write name="tenantForm" property="contractinfo.address"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>		


		    <li>
			   <div class="fieldLabel">电子邮箱:</div>
			   <div class="field"> 
			     <input type="text" name="contractinfo.email" value="<bean:write name="tenantForm" property="contractinfo.email"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">电话号码:</div>
			   <div class="field"> 
			     <input type="text" name="contractinfo.telephone" value="<bean:write name="tenantForm" property="contractinfo.telephone"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">帐户金额:</div>
			   <div class="field"> 
			     <input type="text" name="vo.lcoinremain" value="<bean:write name="tenantForm" property="vo.lcoinremain"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel">简要描述:</div>
			   <div class="field"> 
			     <input type="text" name="custinfoex.notes" value="<bean:write name="tenantForm" property="custinfoex.notes"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>			

			  <div id="fieldDisPlus"></div>
		 </ul>
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
    <script language="JavaScript" type="text/JavaScript">
    <!-- 
        var defaultTimezone = '<bean:write name="tenantForm" property="vo.timezone"/>';
        loadTimezoneList('timezoneId', defaultTimezone);
    //-->
    </script>
  </body>
</html:html>
