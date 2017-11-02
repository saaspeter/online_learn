<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.product.shopopenactivity_view.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveShopopenactivity.do" method="post">
		  
	  <div id="help">
		  <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.viewRecord"/></div>
	  <div id="errorDisplayDiv"></div>
	  <div id="fieldDisDiv">
	     <ul>

		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.name"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.name"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.starttime"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.starttime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.during"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.during"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.jointype"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.jointype"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.onlineurl"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.onlineurl"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.localeid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.localeid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.regioncode"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.regioncode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.place"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.place"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.contactinfo"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.contactinfo"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.shopid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.shopid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.productid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.productid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.creatorid"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.creatorid"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.status"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.status"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.description"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.description"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.createtime"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.createtime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.product.shopopenactivity.lastupdatetime"/>:</div>
			   <div class="field"> 
			      <bean:write name="shopopenactivityForm" property="vo.lastupdatetime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		 </ul>
	  </div>
	  
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
