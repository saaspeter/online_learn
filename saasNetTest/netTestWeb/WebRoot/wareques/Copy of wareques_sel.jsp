<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="prodidVar" name="prodUseForm" property="prodidStr" type="java.lang.String"/>
<bean:define id="prodnameVar" name="prodUseForm" property="prodnameStr" type="java.lang.String"/>
<bean:define id="prodidVar2" name="warequesForm" property="queryVO.prodidStr" type="java.lang.String"/>
<bean:define id="prodnameVar2" name="warequesForm" property="queryVO.productname" type="java.lang.String"/>
<% if(prodidVar2!=null)
   {
      prodidVar = prodidVar2;
      prodnameVar = prodnameVar2;
   } 
   String selectType = request.getParameter("selectType"); // 1单选，2多选
   if(selectType==null||selectType.trim().length()<1){
      selectType = "1";
   }
   
   %>
<!-- query by operator -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="warequesForm" property="jsSuffix" type="java.lang.String"/>
    <title>题库题目列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script language="javascript">
	<!--
	   function selectProd(){
	      var prodidStr = document.getElementById("prodidStrId").value;
	      selectProd_base(prodidStr);
	   }
	   
       function selUserProdCB(ids,names){
          var oldIds = document.getElementById("prodidStrId").value;
          selUserProdCB_base(ids,names);
          if(ids!=oldIds){
             clearWare();
          }
       }
       
       function selectWare(){
          var prodidStr = document.getElementById("prodidStrId").value;
          if(prodidStr==null||prodidStr==""){
             alert('请选择课程!');
             return;
          }
	      newDiv("/wareques/listWare1.do?listType=2&selectType=2&canselprod=2&queryVO.productbaseid="+prodidStr,1,1,670,300);
	   }
	   
       function selWareCB(ids,names,productbaseid){
          if(ids!=null&&ids!=""){
             document.getElementById("wareidStrID").value=ids;
             document.getElementById("warenameStrID").value=names;
          }
          clearDiv();
       }
       
       function doSelect(){
	      // 2代表可以多选仓库 
	      var backObj = selectCheck("list","keys",<%=selectType %>);      
	      if(backObj){
	         if(typeof(parent.selWarequesCB)!="undefined"){
		        parent.selWarequesCB(backObj['ids']);
		     }
	      }
	   }
       
       function clearWare(){
          document.getElementById("wareidStrID").value = "";
          document.getElementById("warenameStrID").value = "";
       }
        
	//-->
	</script>
  </head>
  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/wareques/listWareques1.do?recurltype=1&listType=2&selectType=<%=selectType %>" method="post">
  <input type="hidden" name="queryVO.status" value="<%=QuestionConstant.Status_valide %>" />
  <input type="hidden" id="prodidStrId" name="queryVO.prodidStr" value="<%=prodidVar %>"/>
  <input type="hidden" id="wareidStrID" name="queryVO.wareidStr" value="<bean:write name="warequesForm" property="queryVO.wareidStr"/>"/>
  
  <div id="firstLineDiv">
      
      <div id="searchDiv">
         课程:
        <input type="text" id="prodnameStrId" name="queryVO.productname" value="<%=prodnameVar %>" readonly="readonly" />
         &nbsp;题库: 
		<input type="text" id="warenameStrID" name="queryVO.warenameStr" class="readonly" value="<bean:write name="warequesForm" property="queryVO.warenameStr"/>" readonly="readonly" style="width:200px"/>
         <img src="../styles/imgs/ico/search.gif" alt="选择题库" onclick="selectWare();return false;">
         <img src="../styles/imgs/ico/reset.gif" alt="清除题库" onclick="clearWare()">
         &nbsp;题目:
		<input type="text" name="queryVO.question" value="<bean:write name="warequesForm" property="queryVO.question"/>" style="width:250px"/>
		 &nbsp;题目类型:
		<html:select name="warequesForm" property="queryVO.questype" >
		     <html:option value=""></html:option>
			 <html:optionsSaas consCode="Question.QuesType"/>
	    </html:select>
		<input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
		
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">

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
        <td width="20px">
           <%if("2".equals(selectType)){ %>
           <input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')">
           <%} %>
        </td>

        <td width="45%">题目</td>

        <td>题目类型</td>
        
        <td>所在题库</td>

        <td>创建时间</td>

        <td>题目难度</td>
      </tr>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind">
      <tr>
        <td>
            <%if("1".equals(selectType)){ %>
	           <input type="radio" id='pkId[<bean:write name="vo" property="quesid"/>]' name="keys" value="<bean:write name="vo" property="quesid"/>" onClick="selectInfo(this,'clickedRow')">
	        <%}else { %>
	           <input type="checkbox" id='pkId[<bean:write name="vo" property="quesid"/>]' name="keys" value="<bean:write name="vo" property="quesid"/>" onClick="selectInfo(this,'clickedRow')">&nbsp; 
	        <%} %>
        </td>

        <td class="noUnderline">
           <a href="javascript:newDiv('/wareques/viewWareques.do?queryVO.quesid=<bean:write name="vo" property="quesid"/>',0,1,700,500);" >
              <bean:writeTK name="vo" property="question" cutStr="true" questypeProp="questype" transfer="true" showIndentWay="1"/>
           </a>
        </td>

        <td><bean:writeSaas name="vo" property="questype" consCode="Question.QuesType"/></td>

        <td><bean:write name="vo" property="warename"/></td>

        <td><bean:write name="vo" property="quescreatetime" format="yyyy-MM-dd HH:mm:ss"/></td>

        <td><bean:writeSaas name="vo" property="difficultid" consCode="Question.Difficult"/></td>
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
