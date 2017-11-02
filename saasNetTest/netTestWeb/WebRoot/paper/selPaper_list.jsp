<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.paper.constant.PaperConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="paperForm" property="jsSuffix" type="java.lang.String"/>
        <bean:define id="selectType" name="paperForm" property="selectType" type="java.lang.String"/>
    <title>选择试卷</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	
	   function doSelect(){
          var backObj = selectCheck("list","keys",<%=selectType %>,"Name");
		  if(backObj){
	         if(typeof(parent.selectPaperCB)!="undefined"){
	            <%if("1".equals(selectType)){ %>
	                var papertime = document.getElementById('pkId['+backObj['ids']+']papertime').value;
	                parent.selectPaperCB(backObj['ids'],backObj['names'],papertime);
	            <%}else{ %>
		            parent.selectPaperCB(backObj['ids'],backObj['names']);
		        <%} %>
		     }
	      }
       }
	
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/paper/selectPaper.do" method="post">
  <input type="hidden" name="queryVO.status" value="<%=PaperConstant.PaperStatus_valide %>"/>
  <input type="hidden" name="queryVO.productbaseid" value="<bean:write name="paperForm" property="queryVO.productbaseid"/>"/>
  <input type="hidden" name="queryVO.productname" value="<bean:write name="paperForm" property="queryVO.productname"/>"/>
  <input type="hidden" name="queryVO.genetype" value="<bean:write name="paperForm" property="queryVO.genetype"/>"/>
  <input type="hidden" name="selectType" value="<bean:write name="paperForm" property="selectType"/>" />
  
  <div id="firstLineDiv">
      <div id="help">
	       <a href="" title="<bean:message key="netTest.commonpage.help"/>"><img src="../styles/imgs/help.jpg"></a>
      </div>
      <div id="searchDiv">
         课程:
        <input type="text" name="queryVO.productname" value="<bean:write name="paperForm" property="queryVO.productname"/>" disabled='disabled'/> 
         试卷名: 
		<input type="text" name="queryVO.papername" value="<bean:write name="paperForm" property="queryVO.papername"/>"/>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>
  <!-- complex Search start -->
  <!-- complex Search end -->
  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
		  <ul>
			 <li><button type="button" onclick="doSelect();return false;">确定选择</button></li>
			 <li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
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
        <td width="20px">
        <%if("2".equals(selectType)){ %>
           <input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')">
        <%} %>
        </td>
        <td>试卷</td>
        <td>答卷时间</td>
        <td>创建时间</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td>
            <%if("1".equals(selectType)){ %>
	           <input type="radio" id='pkId[<bean:write name="vo" property="paperid"/>]' name="keys" value="<bean:write name="vo" property="paperid"/>" onClick="selectInfo(this,'clickedRow')">
	        <%}else { %>
	           <input type="checkbox" id='pkId[<bean:write name="vo" property="paperid"/>]' name="keys" value="<bean:write name="vo" property="paperid"/>" onClick="selectInfo(this,'clickedRow')">&nbsp; 
	        <%} %>
        </td>
        <td><a href="javascript:newDiv('/paper/viewPaper.do?queryVO.paperid=<bean:write name="vo" property="paperid"/>',0,1,800,550);">
            <bean:write name="vo" property="papername"/>(编号:<bean:write name="vo" property="paperno"/>)</a>
            <input id='pkId[<bean:write name="vo" property="paperid"/>]Name' type="hidden" value="<bean:write name="vo" property="papername"/>"/>
        </td>
        <td><bean:write name="vo" property="papertime"/>
            <input id='pkId[<bean:write name="vo" property="paperid"/>]papertime' type="hidden" value="<bean:write name="vo" property="papertime"/>"/>
        </td>
        <td><bean:write name="vo" property="createtime" format="yyyy-MM-dd"/></td>
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
