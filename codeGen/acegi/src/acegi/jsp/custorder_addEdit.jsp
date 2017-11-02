<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="acegi.page.{[#folder#]}.custorder_addEdit.jsp.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/checkform.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/{[#folder#]}/custorder.do?method=save" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="custorderForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="custorderForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.orderid" value="<bean:write name="custorderForm" property="vo.orderid"/>">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="acegi.commonpage.save"/></button></li>
				 <li><button onclick="return false;"><bean:message key="acegi.commonpage.reset"/></button></li>
				 <li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="acegi.commonpage.back"/></button></li>
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="acegi.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
		  <div id="toUrl">
		      <bean:message key="acegi.commonpage.toUrl"/>:
		        <select name="select">
				  <option></option>
		          <option selected="selected"><bean:message key="acegi.commonpage.listPage"/></option>
	            </select>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="acegi.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.userid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.userid" value="<bean:write name="custorderForm" property="vo.userid"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.ordercode"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.ordercode" value="<bean:write name="custorderForm" property="vo.ordercode"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.ordername"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.ordername" value="<bean:write name="custorderForm" property="vo.ordername"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.ordertype"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.ordertype" value="<bean:write name="custorderForm" property="vo.ordertype"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.ordertime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.ordertime" value="<bean:write name="custorderForm" property="vo.ordertime"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.baseaccountid"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.baseaccountid" value="<bean:write name="custorderForm" property="vo.baseaccountid"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.reqendtime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.reqendtime" value="<bean:write name="custorderForm" property="vo.reqendtime"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.fullcost"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.fullcost" value="<bean:write name="custorderForm" property="vo.fullcost"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.prepaycost"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.prepaycost" value="<bean:write name="custorderForm" property="vo.prepaycost"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.discount"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.discount" value="<bean:write name="custorderForm" property="vo.discount"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.prepaytime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.prepaytime" value="<bean:write name="custorderForm" property="vo.prepaytime"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.fullpaytime"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.fullpaytime" value="<bean:write name="custorderForm" property="vo.fullpaytime"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.payway"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.payway" value="<bean:write name="custorderForm" property="vo.payway"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.paystatus"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.paystatus" value="<bean:write name="custorderForm" property="vo.paystatus"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel"><bean:message key="acegi.page.{[#folder#]}.custorder.orderstatus"/>:</div>
			   <div class="field"> 
			     <input type="text" name="vo.orderstatus" value="<bean:write name="custorderForm" property="vo.orderstatus"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


			  <div id="fieldDisPlus"></div>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="if(submitForm('editForm')){newDiv('../pubs/saveDiv.jsp',1,0);} return  false;"><bean:message key="acegi.commonpage.save"/></button></li>
			<li><button onclick="return false;"><bean:message key="acegi.commonpage.reset"/></button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;"><bean:message key="acegi.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
