<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="deptinfoForm" property="jsSuffix" type="java.lang.String"/>
    <title>设置默认单位</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">
	<!--
	   function selectDefaultDept(){
	      newDiv("<%=WebConstant.WebContext %>/org/selectorgtree.do",1,0,200,450);
	   }
	   
       function selectOrg_CB(id,name){
          if(id!=null&&id!=""){
             document.getElementById("defaultOrg").value=name;
             document.getElementById("defaultOrgId").value=id;
          }
          clearDiv();
       }
	//-->
	</script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/org/setshopdefaultdeptsave.do" method="post">
	 <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="deptinfoForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="deptinfoForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.pid" value="<bean:write name="deptinfoForm" property="queryVO.pid"/>"/>
	 <input type="hidden" name="vo.orgbaseid" id="defaultOrgId" value="<bean:write name="deptinfoForm" property="vo.orgbaseid"/>"/>
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 
			  </ul>
		  </div>
		  <div id="help">
		       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
		    <li>
			   <div class="fieldLabel">单位名称:</div>
			   <div class="field"> 
			      <input type="text" id="defaultOrg" name="vo.orgname" readonly="readonly" value="<bean:write name="deptinfoForm" property="vo.orgname"/>"/>
			   </div>
			   <div class="fieldDesc">
			      <button type="button" onclick="selectDefaultDept()">选择单位</button>
			   </div>
			</li>
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');return false;">确定</button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;">取消</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
