<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.exam.constant.TestinfoConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="testdeptForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="createorgidLong" name="testdeptForm" property="testinfoVO.createorgid" type="java.lang.Long"></bean:define>
    <bean:define id="userorgidLong" name="testdeptForm" property="userorgid" type="java.lang.Long"></bean:define>
    <%String userorgidStr = (userorgidLong==null)?"":(","+String.valueOf(userorgidLong.longValue())+",");
      request.setAttribute("TeststatusOpenExam",TestinfoConstant.Teststatus_openExam); 
    %>
    <title>考试结果</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<!-- 
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/orgbase.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
	 -->
	<script type="text/javascript">
	
	    function selectOrg_CB(ids,names,param){
	        if(ids!=null&&ids!=""){
	           document.getElementById("deptIdStrId").value=ids;
	           document.getElementById("deptNameStrId").value=names;
	           var form = document.getElementById("list");
	           form.action = "saveTestdept.do";
	           form.submit();
	        }
	        
        }
        
        function changeStatus(testid){
            var nextname = '<bean:writeSaas name="TeststatusOpenExam" consCode="netTest.TestinfoConstant.Control.TestStatus"/>';
            if(testid!=''){
               if(confirm('确定要:'+nextname+'?')){
                  goUrl('/exam/changeStatus.do?vo.testid='+testid);
               }
            }
        }
        	
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/monitorTest.do" method="post">
  <input id="testidId" type="hidden" name="vo.testid" value="<bean:write name="testdeptForm" property="queryVO.testid"/>">
  <input type="hidden" name="queryVO.testid" value="<bean:write name="testdeptForm" property="queryVO.testid"/>">
  <input type="hidden" name="createorgid" value="<bean:write name="testdeptForm" property="testinfoVO.createorgid"/>">
  
  <div id="firstLineDiv">

  </div>
  
  <div id="functionLineDiv">
      <div style="text-align: center">	  
	  <a href="#" onclick="newDiv('/exam/viewTestinfo.do?queryVO.testid=<bean:write name="testdeptForm" property="testinfoVO.testid"/>',0,1,600,460);return false;">
	      <font style="font-weight: bold"><bean:write name="testdeptForm" property="testinfoVO.testname"/></font>
	  </a>
	  </div>
  </div>
  
  <div style="clear:left">
      <table style="width:100%;background-color: #F5FBFE">
	      <tr>
	        <td width="25%" align="right" style="border: 1px solid #eeeeee;padding: 3px;">考试有效时间：</td>
	        <td style="border: 1px solid #eeeeee;padding: 3px;"><bean:write name="testdeptForm" property="testinfoVO.teststartdate" format="yyyy-MM-dd HH:mm"/>&nbsp;&nbsp;--&nbsp;&nbsp;<bean:write name="testdeptForm" property="testinfoVO.testenddate" format="yyyy-MM-dd HH:mm"/></td>
	      </tr>
	      <tr>
	        <td width="25%" align="right" style="border: 1px solid #eeeeee;padding: 3px;">考试组织单位：</td>
	        <td style="border: 1px solid #eeeeee;padding: 3px;"><bean:write name="testdeptForm" property="testinfoVO.createorgname" /></td>
	      </tr>
	      
	      
	      <tr>
	        <td width="25%" align="right" style="border: 1px solid #eeeeee;padding: 3px;">考试状态：</td>
	        <td style="border: 1px solid #eeeeee;padding: 3px;">
	            <bean:writeSaas name="testdeptForm" property="testinfoVO.teststatus" consCode="netTest.TestinfoConstant.Teststatus"/>
	            <logic:equal name="testdeptForm" property="testinfoVO.teststatus" value="<%=TestinfoConstant.Teststatus_allChecked.toString() %>">
		            &nbsp;&nbsp;|&nbsp;&nbsp;
		            <button type="button" onclick="changeStatus('<bean:write name="testdeptForm" property="testinfoVO.testid" />');return false;">
		                <bean:writeSaas name="TeststatusOpenExam" consCode="netTest.TestinfoConstant.Control.TestStatus"/>
		            </button>
	            </logic:equal>
	        </td>
	      </tr>
	      
      </table>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div class="bottomFunDiv">
	   <div style="text-align:center;font-weight: bold;">参加考试单位列表</div>
  </div>
  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>单位名称</td>
        <td>参加考试人数</td>
        <td>实际考试人数</td>
        <td>及格人数</td>
        <td>单位平均分</td>
        <td>操作</td>
      </tr>
      </thead>
      <tr>
        <td></td>
        <td>
           <bean:write name="testdeptForm" property="vo.orgname"/>
           <input id='pkId<bean:write name="testdeptForm" property="vo.testdeptid"/>Name' type="hidden" value="<bean:write name="testdeptForm" property="vo.orgname"/>"/>
        </td>
        <td id='<bean:write name="testdeptForm" property="vo.orgbaseid"/>selfexamernum_Id'><bean:write name="testdeptForm" property="vo.examernum"/></td>
	    <td id='<bean:write name="testdeptForm" property="vo.orgbaseid"/>selfendexamnum_Id'><bean:write name="testdeptForm" property="vo.endexamnum"/></td>
        <td><bean:write name="testdeptForm" property="vo.qulifynum"/></td>
        <td><bean:write name="testdeptForm" property="vo.avescore"/></td>
        <td>
            <logic:match name="testdeptForm" property="vo.deptpath2" value="<%=userorgidStr %>" >
            <a href="#" onclick="goUrl('/exam/listtestuser.do?queryVO.testid=<bean:write name="testdeptForm" property="vo.testid"/>&queryVO.orgname=<bean:write name="testdeptForm" property="vo.orgname"/>&testdeptid=<bean:write name="testdeptForm" property="vo.testdeptid"/>&testVO.testname=<bean:write name="testdeptForm" property="testinfoVO.testname"/>&testVO.paperversion=<bean:write name="testdeptForm" property="testinfoVO.paperversion"/>');return false;">考试人员</a>
            </logic:match>
        </td>
      </tr>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>

  </body>
</html:html>
