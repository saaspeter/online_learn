<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>权限资源列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
       function confirm(){
          var backObj = selectCheck('list','keys','2','Name','请选择一条上级资源!');
          if(backObj!=null){
             if(typeof(parent.selectCallBack)!="undefined"){
		        parent.selectCallBack(backObj['ids'],backObj['names']);
		     }
          }
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/securityManage/listRescForSingle.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="resourcesForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="resourcesForm" property="backUrlEncode"/>">
  <input id="complexSearchDivStatus" type="hidden" name="complexSearchDivStatus" value="">
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="帮助"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">资源: 
		<input type="text" name="queryVO.name" value="<bean:write name="resourcesForm" property="queryVO.name"/>"/>
		<input type="submit" name="Submit" value="查询" />
		<a href="" onclick="changeComplexSearch('complexSearchDiv');return false;">高级搜索</a>
      </div>
  </div>
  <!-- complex Search start -->
  <div id="complexSearchDiv">
      <table class="complexSearchTable">
          <tr>
              <td>Property one</td>
              <td>Property two</td>
          </tr>
      </table>
  </div>
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="confirm();return false;">确定选择</button></li>
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
      <tr class="listDataTableHead">
        <td width="20px"></td>
        <td>资源名</td>

        <td>资源类型</td>

        <td>资源字符串</td>
        
        <td>系统代码</td>
        
        <td>状态</td>

        <td>资源描述</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td><input type="radio" id="upId<%=ind %>" name="keys" value="<bean:write name="vo" property="id"/>"></td>
        
        <td>
           <bean:write name="vo" property="name"/>
           <input type="hidden" id="upId<%=ind %>Name" value="<bean:write name="vo" property="name"/>">
        </td>

        <td><bean:write name="vo" property="resType"/></td>

        <td><bean:write name="vo" property="resString"/></td>
        
        <td><bean:write name="vo" property="syscode"/></td>
        
        <td><bean:write name="vo" property="status"/></td>

        <td><bean:write name="vo" property="descn"/></td>

      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
		    changeComplexSearch("complexSearchDiv","<bean:write name="resourcesForm" property="complexSearchDivStatus"/>");
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
