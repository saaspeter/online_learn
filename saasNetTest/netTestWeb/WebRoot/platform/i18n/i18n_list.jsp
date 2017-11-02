<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>语言国家列表</title>
	<link rel="stylesheet" type="text/css" href="../../styles/css/list.css">
	<script type="text/javascript" src="../../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="i18n/i18n.do?method=list" method="post">
  <div id="firstLineDiv">
      <div id="searchDiv">语言: 
		<input type="text" name="queryVO.language" size="5" usage="word_num" exp="\S{0,5}" tip="语言只能是字符，且不能超过5位" value="<bean:write name="i18nForm" property="queryVO.language"/>"/>
		国家: <input type="text" name="queryVO.country" size="5" usage="word_num" exp="\S{0,5}" tip="国家只能是字符，且不能超过5位" value="<bean:write name="i18nForm" property="queryVO.country"/>"/>
		是否生效:<select name="queryVO.isset">
		                <option <logic:notPresent name="i18nForm" property="queryVO.isset"></logic:notPresent> > </option>
		                <option <logic:present name="i18nForm" property="queryVO.isset"><logic:equal name="i18nForm" property="queryVO.isset" value="<%=String.valueOf(CommonConstant.yes)%>">selected="selected"</logic:equal></logic:present> value="<%=CommonConstant.yes%>">生效</option>
		                <option <logic:present name="i18nForm" property="queryVO.isset"><logic:equal name="i18nForm" property="queryVO.isset" value="<%=String.valueOf(CommonConstant.no)%>">selected="selected"</logic:equal></logic:present>  value="<%=CommonConstant.no%>">未生效</option>
		            </select> 
		是否默认设置:<select name="queryVO.isdefaultset">
		                <option <logic:notPresent name="i18nForm" property="queryVO.isdefaultset"></logic:notPresent> > </option>
		                <option <logic:present name="i18nForm" property="queryVO.isdefaultset"><logic:equal name="i18nForm" property="queryVO.isdefaultset" value="<%=String.valueOf(CommonConstant.yes)%>">selected="selected"</logic:equal></logic:present> value="<%=CommonConstant.yes%>">默认设置</option>
		                <option <logic:present name="i18nForm" property="queryVO.isdefaultset"><logic:equal name="i18nForm" property="queryVO.isdefaultset" value="<%=String.valueOf(CommonConstant.no)%>">selected="selected"</logic:equal></logic:present>  value="<%=CommonConstant.no%>">非默认设置</option>
		            </select>
		<input type="submit" name="Submit" onclick="submitForm('list');return false;" value="查询" />
      </div>
  </div>
  
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="addRecord('/i18n/i18n.do?method=addPage');return false;">新增</button></li>
			 <li><button type="button" onclick="modifyRecord('list','keys','/i18n/i18n.do?method=editPage&editType=<%=WebConstant.editType_edit %>&queryVO.localeid=','<bean:message key="commonWeb.js.pageAction.modifyRecord.selectOneMsg" bundle="BizKey"/>','<bean:message key="commonWeb.js.pageAction.modifyRecord.oneOnlyMsg" bundle="BizKey"/>');return false;">修改</button></li>
			 
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
        <td width="20px"><input type="checkbox" name="select1_1Checkbox" value="" onClick="selectAllCheckbox('list',this,'keys')"></td>
        
        <td>语言(国家)</td>
        
        <td>语言值</td>

        <td>国家值</td>

        <td>是否生效</td>

        <td>是否是默认</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td><input type="checkbox" name="keys" value="<bean:write name="vo" property="localeid"/>" onClick="selectInfo(this,'clickedRow')"></td>
        
        <td><a href="<%=WebConstant.WebContext %>/i18n/i18n.do?method=editPage&queryVO.localeid=<bean:write name="vo" property="localeid"/>&editType=<%=WebConstant.editType_view %>"><bean:write name="vo" property="localeName"/></a></td>
        
        <td><bean:write name="vo" property="language"/></td>

        <td><bean:write name="vo" property="country"/></td>

        <td><bean:write name="vo" property="isset"/></td>

        <td><bean:writeSaas name="vo" property="isdefaultset" consCode="common.const.defaultType"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
    <script language=JavaScript>
     window.onload=function (){  
        setListTableStyle();
     } 
  </script>
  </body>
</html:html>
