<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="shopopenactivityForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="shopopenactivityForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.product.shopopenactivity_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveShopopenactivity.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="shopopenactivityForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="shopopenactivityForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.activityid" value="<bean:write name="shopopenactivityForm" property="vo.activityid"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.name"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.name" value="<bean:write name="shopopenactivityForm" property="vo.name"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.starttime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.starttime" value="<bean:write name="shopopenactivityForm" property="vo.starttime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.during"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.during" value="<bean:write name="shopopenactivityForm" property="vo.during"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.jointype"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.jointype" value="<bean:write name="shopopenactivityForm" property="vo.jointype"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.onlineurl"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.onlineurl" value="<bean:write name="shopopenactivityForm" property="vo.onlineurl"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.localeid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.localeid" value="<bean:write name="shopopenactivityForm" property="vo.localeid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.regioncode"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.regioncode" value="<bean:write name="shopopenactivityForm" property="vo.regioncode"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.place"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.place" value="<bean:write name="shopopenactivityForm" property="vo.place"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.contactinfo"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.contactinfo" value="<bean:write name="shopopenactivityForm" property="vo.contactinfo"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.shopid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.shopid" value="<bean:write name="shopopenactivityForm" property="vo.shopid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.productid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.productid" value="<bean:write name="shopopenactivityForm" property="vo.productid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.creatorid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.creatorid" value="<bean:write name="shopopenactivityForm" property="vo.creatorid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.status"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.status" value="<bean:write name="shopopenactivityForm" property="vo.status"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.description"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.description" value="<bean:write name="shopopenactivityForm" property="vo.description"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.createtime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.createtime" value="<bean:write name="shopopenactivityForm" property="vo.createtime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.shopopenactivity.lastupdatetime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.lastupdatetime" value="<bean:write name="shopopenactivityForm" property="vo.lastupdatetime"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
