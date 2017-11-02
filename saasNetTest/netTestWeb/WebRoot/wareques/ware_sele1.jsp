<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.WareConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<!-- operator ware list -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="wareForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="selectType" name="wareForm" property="selectType" type="java.lang.String"/>
    <bean:define id="productid" name="wareForm" property="queryVO.productbaseid" type="java.lang.Long"/>
    <bean:define id="productname" name="wareForm" property="queryVO.productname" type="java.lang.String"/>
    <bean:define id="selectedIds" name="wareForm" property="selectedIds" type="java.lang.String"/>
	
    <title>选择题库</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script language="javascript">
	<!--
	   function doSelect(){
	      // 2代表可以多选仓库 
	      var backObj = selectCheck("list","keys",<%=selectType %>,"Name");      
	      if(backObj){
	         if(typeof(parent.selWareCB)!="undefined"){
		        parent.selWareCB(backObj['ids'],backObj['names'],'<%=productid %>');
		     }
	      }
	   }
       
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage" style="height:87%">
  <html:form styleId="list" action="/wareques/listWareSelect.do" method="post">
  <input type="hidden" name="queryVO.status" value="<%=WareConstant.Status_valide %>"/>
  <input type="hidden" name="selectType" value="<bean:write name="wareForm" property="selectType"/>" />
  <input type="hidden" name="productbaseid" value="<%=productid %>" />
  <input type="hidden" name="queryVO.productname" value="<bean:write name="wareForm" property="queryVO.productname"/>" />
  
  <div id="firstLineDiv">
      <div style="float:left;">
          <button type="button" class="button green" onclick="doSelect();return false;">确定</button>
      </div>
      <div id="searchDiv">
                      课程:<%=productname %>
        &nbsp;
                       题库: 
		<input type="text" name="queryVO.warename" value="<bean:write name="wareForm" property="queryVO.warename"/>"/>
	   
	    <input type="submit" name="Submit" class="button green" value="<bean:message key="netTest.commonpage.query"/>" />
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
      <thead>
      <tr class="listDataTableHead">
        <td width="20px">
           <%if("2".equals(selectType)){ %>
           <input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')">
           <%} %>
        </td>
        <td>题库名</td>
        <td>创建时间</td>
        <td>所属产品</td>
        <td>题目数</td>   
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td>
	        <%if("1".equals(selectType)){ %>
	           <input type="radio" id='pkId[<bean:write name="vo" property="wareid"/>]' name="keys" value="<bean:write name="vo" property="wareid"/>" onClick="selectInfo(this,'clickedRow')">
	        <%}else { %>
	           <input type="checkbox" id='pkId[<bean:write name="vo" property="wareid"/>]' name="keys" value="<bean:write name="vo" property="wareid"/>" onClick="selectInfo(this,'clickedRow')">&nbsp; 
	        <%} %>
        </td>
        <td>
            <bean:write name="vo" property="warename"/>
            <input id='pkId[<bean:write name="vo" property="wareid"/>]Name' type="hidden" value="<bean:write name="vo" property="warename"/>"/>
        </td>
        <td><bean:write name="vo" property="warecreatetime" format="yyyy-MM-dd"/></td>
        <td><bean:write name="vo" property="productname"/></td>
        <td><bean:write name="vo" property="warequesnum"/></td>
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
		 <li><button type="button" class="button green" onclick="doSelect();return false;">确定</button></li>
		 <li><button type="button" class="button green" onclick="parent.clearDiv();return false;">取消</button></li>
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
