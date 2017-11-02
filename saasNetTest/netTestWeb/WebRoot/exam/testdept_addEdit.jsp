<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="testdeptForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="testdeptForm" property="editType" type="java.lang.Integer"/>
  <%String disableStr=""; boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
      disableStr="disabled=\"disabled\""; } %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.exam.testdept_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/exam/saveTestdept.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="testdeptForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="testdeptForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.testdeptid" value="<bean:write name="testdeptForm" property="vo.testdeptid"/>">
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.shopid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.shopid" value="<bean:write name="testdeptForm" property="vo.shopid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testid" value="<bean:write name="testdeptForm" property="vo.testid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.orgbaseid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orgbaseid" value="<bean:write name="testdeptForm" property="vo.orgbaseid"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.orgname"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orgname" value="<bean:write name="testdeptForm" property="vo.orgname"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptuserset"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptuserset" value="<bean:write name="testdeptForm" property="vo.testdeptuserset"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptquaper"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptquaper" value="<bean:write name="testdeptForm" property="vo.testdeptquaper"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptavescore"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptavescore" value="<bean:write name="testdeptForm" property="vo.testdeptavescore"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptusernum"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptusernum" value="<bean:write name="testdeptForm" property="vo.testdeptusernum"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptselfusernum"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptselfusernum" value="<bean:write name="testdeptForm" property="vo.testdeptselfusernum"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdepttestingnum"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdepttestingnum" value="<bean:write name="testdeptForm" property="vo.testdepttestingnum"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptendnum"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptendnum" value="<bean:write name="testdeptForm" property="vo.testdeptendnum"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.testdeptselfendnum"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.testdeptselfendnum" value="<bean:write name="testdeptForm" property="vo.testdeptselfendnum"/>" <%=disableStr %> />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		    <li>
			   <div class="fieldLabel"><bean:message key="netTest.page.{[#folder#]}.testdept.status"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.status" value="<bean:write name="testdeptForm" property="vo.status"/>" />
			   </div>
			   <div class="fieldDesc"></div>
			</li>


		 </ul>
	  </div>
	 <div id="BarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="return false;"><bean:message key="netTest.commonpage.reset"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
