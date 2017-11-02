<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productidVar" name="learncontentForm" property="queryVO.productbaseid" type="java.lang.Long"/>
    <title>学习内容</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<style type="text/css">

		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
	
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	    
	    function contcate_CB_Hook(id, name){
	       document.getElementById("list").submit();
	    }
	</script>
	
  </head>
  
  <body>
  
  <div class="listPage">
  <html:form styleId="list" action="/learncont/listtryprodcont.do" method="post">
  <input id="prodidId" type="hidden" name="queryVO.productbaseid" value="<%=productidVar %>" >
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td>资料名</td>
        <td>学习</td>
      </tr>
      </thead>
      <logic:present name="learncontentForm" property="datalist">
	  <logic:iterate id="vo" name="learncontentForm" property="datalist" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
         <td><img src="../styles/imgs/filetype/<bean:write name="vo" property="fileicon"/>" >
             <a href="javascript:void(0)" onclick="openWin('/learncont/viewLearncontent.do?vo.contentid=<bean:write name="vo" property="contentid"/>',750,650,'yes','yes');return false;">
             <bean:write name="vo" property="caption"/>
             </a>
         </td>
         <td>
            <a href="javascript:void(0)" onclick="openWin('/learncont/viewLearncontent.do?vo.contentid=<bean:write name="vo" property="contentid"/>',750,650,'yes','yes');return false;">查看</a>          
         </td>
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
