<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="exeruserForm" property="jsSuffix" type="java.lang.String"/>
    
    <title>学生练习列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	   function clearOrg(){
	      document.getElementById("orgbaseidId").value='';
          document.getElementById("orgnameId").value='';
	   }
	    
	   function selectOrg_CB(ids,names,param){
          if(ids!=null&&ids!=""){
                document.getElementById("orgbaseidId").value = ids;
                document.getElementById("orgnameId").value = names;
          }
          clearDiv();
       }
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/exercise/listExeruser.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="exeruserForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="exeruserForm" property="backUrlEncode"/>">
  <input type="hidden" name="queryVO.exerid" value="<bean:write name="exeruserForm" property="queryVO.exerid"/>" />
  <input type="hidden" id="orgbaseidId" name="queryVO.orgbaseid" value="<bean:write name="exeruserForm" property="queryVO.orgbaseid"/>"/>
  
  <div class="navlistBar">
      <a href="javascript:void(0)" onclick=";" title=""><bean:write name="exeruserForm" property="exerVO.exername"/></a> 
      &gt; 已完成练习人员列表
  </div>
  <div class="titleBar">已完成练习人员列表<img src="../styles/imgs/help.gif" title="所有选修该课程人员都能看到本练习，本页面列出已经做完练习或正在做联系的人员"/></div>
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">查询单位: 
		<input type="text" id="orgnameId" name="queryVO.orgname" style="width:250px" value="<bean:write name="exeruserForm" property="queryVO.orgname"/>" readonly="readonly" onclick="newDiv('/org/selectorgtree.do?&param=1',1,1,300,400);"/>
		    <img src="../styles/imgs/cancel.png" alt="清空" style="cursor:pointer;" onclick="clearOrg();return false;"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="getAndToUrl('backUrl');return false;">返回</button></li>
		  </ul>
	  </div>
	  <!-- page list -->
      <div id="pageNumLineDiv">
         <tiles:insert page="/pubs/pagetiles.jsp"></tiles:insert>
      </div>
      <!-- page list -->
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>用户</td>
        <td>练习状态</td>
        <td>完成时间</td>
        <td></td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.exercise.vo.Exeruser">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td></td>
        <td><bean:write name="vo" property="displayname"/></td>
        <td><bean:writeSaas name="vo" property="status" consCode="netTest.ExerUser.Status"/></td>
        <td><bean:write name="vo" property="endtime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  </body>
</html:html>
