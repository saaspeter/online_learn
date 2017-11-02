<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant,netTest.exam.vo.Testchecker,netTest.exam.constant.TestcheckerConstant,netTestWeb.platform.user.form.UsershopForm, netTest.exam.constant.TestinfoConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="testcheckerForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="shopid" name="testcheckerForm" property="testinfoVO.shopid" type="java.lang.Long"/>
    <bean:define id="teststatus" name="testcheckerForm" property="testinfoVO.teststatus" type="java.lang.Short"/>
    <title>考试阅卷人员管理</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext%>/styles/script/resource/mess<%=jsSuffix%>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext%>/dwr/interface/orguserLogic.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext%>/dwr/engine.js'></script>
	<script type="text/javascript">
	
	    function getOrg(useridVar, textid){
	        var textObj = document.getElementById(textid);
	        var text = textObj.innerHTML;
	        if(text==null||text==''){
		        orguserLogic.selectOrgForUser(useridVar, <%=shopid%>,
	               function CB_rtn(orguserVO){
	                  if(orguserVO!=null){
	                     var orgname = orguserVO.orgname;
	                     var textObj = document.getElementById(textid);
	     	             textObj.innerHTML = orgname;
	     	          }   
		           }
	     	    );
     	    }
	    }
	
	    function selUserCB(ids,names){
	        if(ids!=null&&ids!=""){
	           document.getElementById("userIdStrId").value=ids;
	           document.getElementById("userNameStrId").value=names;
	           var form = document.getElementById("list");
	           form.action = "saveTestchecker.do";
	           form.submit();
	        }
	    }
	
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exam/listTestchecker.do" method="post">
  <input type="hidden" name="queryVO.orgbaseid" value="<bean:write name="testcheckerForm" property="queryVO.orgbaseid"/>" />
  <input type="hidden" name="queryVO.testid" value="<bean:write name="testcheckerForm" property="queryVO.testid"/>" />
  <input type="hidden" name="queryVO.testname" value="<bean:write name="testcheckerForm" property="queryVO.testname"/>" />
  <input id="userIdStrId" type="hidden" name="userIdStr" value="">
  <input id="userNameStrId" type="hidden" name="userNameStr" value="">
  <div class="navlistBar">
     <a href="javascript:void(0)" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testcheckerForm" property="queryVO.testid"/>');" title="点击返回考试详细信息"><bean:write name="testcheckerForm" property="testinfoVO.testname"/></a> &gt; 阅卷人员列表
  </div>
  <div id="firstLineDiv">
      
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
		     <%if(!TestinfoConstant.Teststatus_openExam.equals(teststatus)) { %>
			 <li><button type="button" onclick="newDiv('/userproduct/seloneprodmaguser.do?queryVO.productbaseid=<bean:write name="testcheckerForm" property="testinfoVO.productbaseid"/>',0,1,600,400);return false;"><bean:message key="netTest.commonpage.add"/></button>&nbsp;&nbsp;</li>
			 <li><button type="button" onclick="delRec('list','keys','/exam/deleteTestchecker.do?vo.testid=<bean:write name="testcheckerForm" property="queryVO.testid"/>');"><bean:message key="netTest.commonpage.delete"/></button>&nbsp;&nbsp;</li>
			 <%} %>
			 <li><button type="button" onclick="goUrl('/exam/monitorTest.do?queryVO.testid=<bean:write name="testcheckerForm" property="queryVO.testid"/>');">返回考试详情</button>&nbsp;&nbsp;</li>
		  </ul>
	  </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
		<td>用户</td>
        <td>评阅题目个数</td>
        <td>所在单位</td>
      </tr>
      </thead>
      <logic:present name="testcheckerForm" property="datalist">
	  <logic:iterate id="vo" name="testcheckerForm" property="datalist" indexId="ind" type="netTest.exam.vo.Testchecker">
      <tr class='<%=(ind%2==0)?"oddRow":"evenRow" %>' onmouseover="getOrg('<bean:write name="vo" property="userid"/>', 'tdId<%=ind %>');"> 
        <td><%if(TestcheckerConstant.IsExamCreator_no.equals(vo.getIsexamcreator())){ %>
            <input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="userid"/>' value="<bean:write name="vo" property="userid"/>" onClick="selectInfo(this,'clickedRow')">
            <%} %>
        </td>
        <td><bean:write name="vo" property="displayname"/></td>
	    <td><bean:write name="vo" property="checkquesnum"/></td>
	    <td id="tdId<%=ind %>"></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  </html:form>
  </div>

  </body>
</html:html>
