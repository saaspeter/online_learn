<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.exam.constant.TestinfoConstant,netTest.exam.constant.TestuserConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="testuserForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="teststatus" name="testuserForm" property="testVO.teststatus" type="java.lang.Short"/>
<bean:define id="opentype" name="testuserForm" property="testVO.opentype" type="java.lang.Short"/>
<bean:define id="viewresulttype" name="testuserForm" property="testVO.viewresulttype" type="java.lang.Short"/>
<bean:define id="testuserVO" name="testuserForm" property="vo" type="netTest.exam.vo.Testuser"/>
<bean:define id="canstarttest" name="testuserForm" property="canstarttest" type="java.lang.Boolean"/>

<% String disableStr = "disabled=\"disabled\"";
   if(canstarttest){
      disableStr = "";
   }
   String button1display = "";
   if(testuserVO==null){
      button1display = "display: none;";
   }
  
   request.setAttribute("examingStatus",TestuserConstant.Status_examing);
  %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>考试信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style type="text/css">

		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		#fieldDisDiv{
			font-size: 1.5em;
		}
		
		.tooltip-wrapper {
		    display: inline-block;
		}
	
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
	    if(!window.jQuery){
	       document.write("<script type='text/javascript' src='../styles/script/vendor/jquery-1.9.1.min.js'><\/script>");
	    }
	</script>
    <script type="text/javascript" src="../styles/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	   
          function beginTest(){
              var testidVar = document.getElementById("testidId").value;
              openWin("/exam/testExamInit.do?testid="+testidVar,750,650,'no','yes');
              document.getElementById("actionButton1Id").disabled="disabled";
              document.getElementById("statusSpanId").innerText = '<bean:writeSaas name="examingStatus" consCode="netTest.TestuserConstant.status"/>';
          }
          
          $(function() {
       	      $('.tooltip-wrapper').tooltip({position: "bottom"});
       	  });
	   
	</script>
	
  </head>
  
  <body>
    <div class="col-xs-12 col-md-9 center-block">
    <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
     
	<div class="editPage">
	
	 <input id="testidId" type="hidden" name="vo.testid" value="<bean:write name="testuserForm" property="testVO.testid"/>" />
	 <input type="hidden" name="vo.testcanstop" value="<%=TestinfoConstant.Testcanstop_no %>" />
	 <div class="navlistBar">
	     学习考试&nbsp;&gt;&gt;&nbsp;考试大厅
	  </div>
	 
	  <div id="fieldsTitleDiv">
	      <span style="font-weight: bold;"><bean:write name="testuserForm" property="testVO.testname"/></span>
	      <br>
	      <span style="font-size: 1em !important;">(课程:<bean:write name="testuserForm" property="testVO.productname"/>)</span>
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
			
			<li>
			   <div class="fieldLabel">考试时间:</div>
			   <div class="field"> 
			       <bean:writeDate name="testuserForm" property="testVO.teststartdate" format="yyyy/MM/dd HH:mm"/>
			       &nbsp;&nbsp;---&nbsp;&nbsp;
			       <bean:writeDate name="testuserForm" property="testVO.testenddate" format="yyyy/MM/dd HH:mm" showtimezone="true"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">答卷时间:</div>
			   <div class="field"> 
			       <bean:write name="testuserForm" property="testVO.papertime"/>分钟
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">本次考试状态:</div>
			   <div class="field"> 
			       <bean:writeSaas name="testuserForm" property="testVO.teststatus" consCode="netTest.TestinfoConstant.Teststatus"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">组织单位:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="testVO.createorgname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel"></div>
			   <div class="field"> 
			       <div style="height:2em;"></div>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<%if(testuserVO!=null){ %>
			<li>
			   <div class="fieldLabel">考生帐号:</div>
			   <div class="field"> 
			       <bean:write name="testuserForm" property="vo.displayname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">考生状态:</div>
			   <div class="field"> 
			       <span id="statusSpanId">
			          <bean:writeSaas name="testuserVO" property="status" consCode="netTest.TestuserConstant.status"/>
			       </span>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<%
			if((TestinfoConstant.ViewResultType_ViewScorePaperInstant.equals(viewresulttype)
			     ||TestinfoConstant.ViewResultType_ViewScoreInstant.equals(viewresulttype))
			   &&TestuserConstant.Status_endExam.equals(testuserVO.getStatus())){
			 %>
			<li>
			   <div class="fieldLabel">我的得分:</div>
			   <div class="field"> 
			       <span>
			          总分:<bean:write name="testuserVO" property="totalscore"/> &nbsp;&nbsp;&nbsp;
			          客观题得分:<bean:write name="testuserVO" property="objectscore"/>
			       </span>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			 
            <%}} %>
            <li>
			   <div class="fieldLabel">考试备注:</div>
			   <div class="field"> 
			      <bean:write name="testuserForm" property="testVO.testnotes"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		 </ul>
	  </div>
	  <div style="height:70px;">&nbsp;</div>
	  <div id="functionBarButtomDiv">
	      <button type="button" onclick="goUrl('/exam/listTestinfouser.do');" class="btn btn-primary btn-lg" style="font-size: larger;font-weight: bold;">返回我的考试</button>&nbsp;&nbsp;
	  	  <div class="tooltip-wrapper" <%if(!canstarttest){ %> data-title="不能进入考试，请检查考试状态" <%} %>>
	  	  <button id="actionButton1Id" type="button" onclick="beginTest()" class="btn btn-lg btn-success" style="font-size: larger;<%=button1display %>" <%=disableStr %> >开始考试</button>&nbsp;&nbsp;
	      </div>
	      <%if(testuserVO!=null&&TestuserConstant.Status_endExam.equals(testuserVO.getStatus())
	           &&(TestinfoConstant.Teststatus_openExam.equals(teststatus) ||
	              TestinfoConstant.ViewResultType_ViewScorePaperInstant.equals(viewresulttype))){ %>
	      <button id="actionButton3Id" type="button" class="btn btn-primary btn-lg" onclick="openWin('/exam/seeExamerResult.do?testid=<bean:write name="testuserForm" property="testVO.testid"/>&testuserid=<%=testuserVO.getTestuserid() %>&paperVO.paperid=<%=testuserVO.getPaperid() %>&paperversion=<bean:write name="testuserForm" property="testVO.paperversion"/>',750,650,'yes','yes');return false;" style="font-size: larger;font-weight: bold;width: 150px;height: 50px;" >查看答卷情况</button>
	      <%} %>
	  </div>
	  <div id="buttomDiv"></div>
	  
	</div>
	   
	   <div style="height:15px; clear:both;"></div>
	
	<jsp:include flush="true" page="/foot.jsp"></jsp:include>
	</div>
  </body>
</html:html>
