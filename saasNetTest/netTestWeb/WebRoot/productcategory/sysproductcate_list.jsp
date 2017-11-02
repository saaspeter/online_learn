<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,platform.vo.Sysproductcate"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>产品目录适用系统列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
	   function selectSys(){
	      var filterStrs = "<bean:write name="sysproductcateForm" property="vo.syscodesStr"/>";
	      var urlPara = "";
	      if(filterStrs!=null&&filterStrs!=""){
	         urlPara = "?filterStrs="+filterStrs;
	      }
	      newDiv("/subsysMag/listSubsys.do"+urlPara,1,0,670,300);
	   }
	   
       function selectSysCallBack(ids,names){
          if(ids!=null&&ids!=""){
             document.getElementById("syscodesStrId").value=ids;
          }
          clearDiv();
          document.getElementById("list").action="saveSysproductcate.do";
          document.getElementById("list").submit();
       }
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/productcategory/listSysproductcate.do" method="post">
  <input type="hidden" name="queryVO.categoryid" value="<bean:write name="sysproductcateForm" property="queryVO.categoryid"/>">
  <input type="hidden" name="vo.categoryid" value="<bean:write name="sysproductcateForm" property="queryVO.categoryid"/>">
  <input id="syscodesStrId" type="hidden" name="vo.syscodesStr" value="">
  <bean:define id="locale" name="sysproductcateForm" property="locale" type="java.util.Locale"></bean:define>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="selectSys();return false;"><bean:message key="platform.commonpage.add" bundle="platform.pagemess"/></button></li>
			 <li><button type="button" onclick="deleteRecord('list','keys','/productcategory/deleteSysproductcate.do','<bean:message key="commonWeb.js.pageAction.deleteRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.deleteRecord.confirmDeleteMsg" bundle="BizKey"/>');return false;"><bean:message key="platform.commonpage.delete" bundle="platform.pagemess"/></button></li>
		  </ul>
	  </div>
      <div id="help">
	       <a href="" title="<bean:message key="platform.commonpage.help" bundle="platform.pagemess"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
  </div>
  
  <div class="dashLine"></div>
  
  <div id="displayMessDiv">
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <tr class="listDataTableHead">
         <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>
         <td>适用系统</td>
      </tr>
      <logic:present name="sysproductcateForm" property="showList">
	  <logic:iterate id="vo" name="sysproductcateForm" property="showList">
      <tr>
         <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="syscode"/>" onClick="selectInfo(this,'clickedRow')"></td>
         <td><%=CommonConstant.qrySystemName(((Sysproductcate)vo).getSyscode(),locale) %></td>
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
