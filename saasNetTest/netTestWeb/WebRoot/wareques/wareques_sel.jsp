<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.QuestionConstant,commonTool.util.StringUtil" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="productId" name="warequesForm" property="queryVO.productbaseid" type="java.lang.Long"/>
<bean:define id="productName" name="warequesForm" property="queryVO.productname" type="java.lang.String"/>
<% 
   String selectType = request.getParameter("selectType"); // 1单选，2多选
   if(selectType==null||selectType.trim().length()<1){
      selectType = "1";
   }
   // 已经选择的题目id串
   String selquesidStr = request.getParameter("selquesidStr");
   if(selquesidStr==null){ selquesidStr = ""; }
   String disableStr = "";
   
   %>
<!-- query by operator -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="warequesForm" property="jsSuffix" type="java.lang.String"/>
    <title>题库题目列表</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
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
          var prodidStr = document.getElementById("prodidId").value;
          if(prodidStr==null||prodidStr==""){
             alert('请选择课程!');
             return;
          }
	      newDiv("/wareques/listWareSelect.do?selectType=2&canselprod=2&productbaseid="+prodidStr,1,1,670,300);
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
  <div class="listPage" style="height:90%">
  <html:form styleId="list" action="/wareques/selWareques.do" method="post">
  <input id="prodidId" type="hidden" name="queryVO.productbaseid" value="<%=(productId==null)?"":productId.toString() %>">
  <input type="hidden" id="wareidStrID" name="queryVO.wareidStr" value="<bean:write name="warequesForm" property="queryVO.wareidStr"/>"/>
  <input type="hidden" name="queryVO.questype" value="<bean:write name="warequesForm" property="queryVO.questype"/>" />
  <input type="hidden" name="selectType" value="<%=selectType %>" />
  <input type="hidden" name="selquesidStr" value="<%=selquesidStr %>" />
  <input type="hidden" name="queryVO.productname" value="<%=productName %>"/>
  
  <div id="firstLineDiv">
      
      <div id="searchDiv">
         <table>
              <tr>
                  <td>题目:</td>
                  <td>  
		              <input type="text" name="queryVO.question" value="<bean:write name="warequesForm" property="queryVO.question"/>" style="width:200px"/>
                  </td>
                  <td>题库:</td>
                  <td>  
		             <input type="text" id="warenameStrID" name="queryVO.warenameStr" class="readonly" value="<bean:write name="warequesForm" property="queryVO.warenameStr"/>" readonly="readonly" />
		             <img src="../styles/imgs/ico/search.gif" alt="选择题库" onclick="selectWare();return false;">
                     <img src="../styles/imgs/ico/reset.gif" alt="清除题库" onclick="clearWare()">
                  </td>
                  <td></td>
              </tr>
              <tr>
                  <td>课程:</td>
                  <td>
                     <%=productName %>
                  </td>
                  <td>题目类型:</td>
                  <td>
                      <bean:writeSaas name="warequesForm" property="queryVO.questype" consCode="Question.QuesType"/>
                  </td>
                  <td>
                      <input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" />
                  </td>
              </tr>
         </table>
      </div>
  </div>

  <div id="functionLineDiv">
	  <div id="functionButtonDiv">
         <ul>
		    <li><button type="button" onclick="doSelect();return false;">确定</button></li>
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
        <td width="45%">题目</td>
        <td>题目类型</td>
        <td>所在题库</td>
        <td>题目难度</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.wareques.vo.Question">
      <tr>
        <td>
            <%
              if(StringUtil.includeStr(selquesidStr,vo.getQuesid().toString(),null)){
                 disableStr = "disabled='disabled'";
              }else{
                 disableStr = "";
              }
              
              if("1".equals(selectType)){ %>
	           <input type="radio" id='pkId[<%=vo.getQuesid() %>]' name="keys" value="<%=vo.getQuesid() %>" onClick="selectInfo(this,'clickedRow')" <%=disableStr %> />
	        <%}else { %>
	           <input type="checkbox" id='pkId[<%=vo.getQuesid() %>]' name="keys" value="<%=vo.getQuesid() %>" onClick="selectInfo(this,'clickedRow')" <%=disableStr %> />&nbsp; 
	        <%} %>
        </td>

        <td class="noUnderline">
           <a href="javascript:newDiv('/wareques/viewWareques.do?queryVO.quesid=<bean:write name="vo" property="quesid"/>',0,1,700,500);" >
              <bean:writeTK name="vo" property="question" cutStr="true" questypeProp="questype" transfer="true" showIndentWay="1"/>
           </a>
        </td>

        <td><bean:writeSaas name="vo" property="questype" consCode="Question.QuesType"/></td>

        <td><bean:write name="vo" property="warename"/></td>

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
  <br>
  <script language=JavaScript>
	 <!--
       window.onload=function(){
	        setListTableStyle();
	   }
     //-->
  </script>
  </body>
</html:html>
