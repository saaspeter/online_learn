<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="quesdifficultForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="selectType" name="quesdifficultForm" property="selectType" type="java.lang.String"/>
    <title>难度选择</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script language="javascript">
	<!--
	   function doSelect(){
	      // 2代表可以多选
	      var backObj = selectCheck("list","keys",<%=selectType %>,"Name");      
	      if(backObj){
	         if(typeof(parent.selQuesDiffCB)!="undefined"){
		        parent.selQuesDiffCB(backObj['ids'],backObj['names']);
		     }
	      }
	   }
       
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/wareques/listQuesdifficult.do" method="post">
  <input type="hidden" name="queryVO.status" value="<%=CommonConstant.Status_valide %>" />
  <input type="hidden" name="listType" value="2" />
  <input type="hidden" name="selectType" value="<%=selectType %>" />
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">难度名称: 
		<input type="text" name="queryVO.difficultname" value="<bean:write name="quesdifficultForm" property="queryVO.difficultname"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
      </div>
  </div>

  <div id="functionLineDiv">
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
        <td width="20px">
           <%if("2".equals(selectType)){ %>
              <input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')">
           <%} %>
        </td>
        <td>难度名称</td>
        <td>难度系数</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList">
      <tr>
        <td>
            <%if("1".equals(selectType)){ %>
	           <input type="radio" id='pkId[<bean:write name="vo" property="difficultid"/>]' name="keys" value="<bean:write name="vo" property="difficultid"/>" onClick="selectInfo(this,'clickedRow')">
	        <%}else { %>
	           <input type="checkbox" id='pkId[<bean:write name="vo" property="difficultid"/>]' name="keys" value="<bean:write name="vo" property="difficultid"/>" onClick="selectInfo(this,'clickedRow')">
	        <%} %>
        </td>      
        <td><bean:write name="vo" property="difficultname"/>
            <input id='pkId[<bean:write name="vo" property="difficultid"/>]Name' type="hidden" value="<bean:write name="vo" property="difficultname"/>"/>
        </td>
        <td><bean:write name="vo" property="difficultvalue"/></td>
      </tr>
      </logic:iterate>
      </logic:present>
    </table>
  </div>
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  <div class="bottomFunDiv">
	  <ul>
		 <li><button type="button" onclick="doSelect();return false;">确定</button></li>
		 <li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
	  </ul>
  </div>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
