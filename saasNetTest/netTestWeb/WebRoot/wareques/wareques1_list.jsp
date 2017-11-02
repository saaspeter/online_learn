<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.constant.UserproductConstant,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="productId" name="warequesForm" property="sessionProductid" type="java.lang.Long"/>
<bean:define id="productName" name="warequesForm" property="sessionProductname" type="java.lang.String"/>

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
    <script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script language="javascript">
	<!--
	          
       function selectWare(){
          var prodidStr = '<%=productId %>';
          var productName = '<%=productName %>';
          if(prodidStr==null||prodidStr==""){
             alert('请选择课程!');
             return;
          }
	      newDiv("/wareques/listWareSelect.do?selectType=2&productbaseid="+prodidStr+"&queryVO.productname="+productName,1,1,670,300);
	   }
	   
       function selWareCB(ids,names,productbaseid){
          if(ids!=null&&ids!=""){
             document.getElementById("wareidStrID").value=ids;
             document.getElementById("warenameStrID").value=names;
          }
          clearDiv();
       }
       
       function clearWare(){
          document.getElementById("wareidStrID").value = "";
          document.getElementById("warenameStrID").value = "";
       }
       
       function prod_CB_Hook(id, name){
          clearWare();
          document.getElementById("list").submit();
       }
       
       function addQues(questype){
          if(questype==null||questype==''){
             return;
          }

          var wareidStr = document.getElementById("wareidStrID").value;
          var wareName = document.getElementById("warenameStrID").value;
          if(wareidStr.indexOf(",")!=-1){
             wareidStr = '';
             wareName = '';
          }else{
             wareidStr = '&vo.wareid='+wareidStr;
             wareName = '&vo.warename='+wareName;
             
          }
          addRecord('/wareques/addWarequesPage.do?vo.productbaseid=<%=productId %>&vo.questype='+questype+wareidStr+wareName);
       }
       
       function toAddQues(selObj){
          if(selObj==null){
             return;
          }
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue=="-1"){ return; }
          var wareidStr = document.getElementById("wareidStrID").value;
          var wareName = document.getElementById("warenameStrID").value;
          if(wareidStr.indexOf(",")!=-1){
             wareidStr = '';
             wareName = '';
          }else{
             wareidStr = '&vo.wareid='+wareidStr;
             wareName = '&vo.warename='+wareName;
          }
          goUrl('/wareques/addWarequesPage.do?vo.productbaseid=<%=productId %>&vo.questype='+itemValue+wareidStr+wareName+'&backUrlEncode=', 'backUrlEncode');
       }
       
       function toImportQues(selObj){
          document.getElementById("list").action = "importwarequespage.do";
          document.getElementById("list").submit();
       }
       
	//-->
	</script>
  </head>
    <%if(productId==null){
       productName = "所有可用产品";
    } %>  
  <body>
  <div class="listPage">
  <html:form styleId="list" action="/wareques/listWareques1.do" method="post">
  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="warequesForm" property="backUrl"/>">
  <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="warequesForm" property="backUrlEncode"/>">
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=(productId==null)?"":productId.toString() %>">
  <input id="prodnameId" type="hidden" name="productname" value="">
  <input type="hidden" id="wareidStrID" name="queryVO.wareidStr" value="<bean:write name="warequesForm" property="queryVO.wareidStr"/>"/>
  <div class="navlistBar">
      <a href="javascript:void(0)" onclick="switchProduct(this,'<%=UserproductConstant.ProdUseType_userNormal %>');" title="点击选择课程"><%=productName %></a> &gt; 题目列表
  </div>
  <div id="firstLineDiv">
      <div style="width:100%;">
         <table border="0" cellpadding="2" cellspacing="0" style="width: 70%;margin-left: 20%;">
            <tr>
               <td>题库:<input type="text" id="warenameStrID" name="queryVO.warenameStr" class="readonly" value="<bean:write name="warequesForm" property="queryVO.warenameStr"/>" readonly="readonly" style="width:200px"/>
         			<img src="../styles/imgs/ico/search.gif" alt="选择题库" onclick="selectWare()">
         			<img src="../styles/imgs/ico/reset.gif" alt="清除题库" onclick="clearWare()"></td>
               <td>题目类型:
					<html:select name="warequesForm" property="queryVO.questype" >
					     <html:option value=""></html:option>
						 <html:optionsSaas consCode="Question.QuesType"/>
				    </html:select>
               </td>
               <td></td>
            </tr>
            <tr>
               <td colspan="2">题目:<input type="text" name="queryVO.question" value="<bean:write name="warequesForm" property="queryVO.question"/>" style="width:430px"/>
               </td>
               <td><input type="submit" name="Submit" value="<bean:message key="netTest.commonpage.query"/>" /></td>
            </tr>
         </table>

      </div>
  </div>

  <div id="functionLineDiv" style="height: 38px;">
	  <div id="functionButtonDiv">
		  <ul>
		     <li>&nbsp;</li>
			 <li>    
			     <html:select name="warequesForm" property="vo.questype" onchange="toAddQues(this)" styleClass="select_second">
					  <html:option value="-1" style="color: #0000ff">&lt;新增题目&gt;</html:option>
					  <html:optionsSaas consCode="Question.QuesType"/>
			     </html:select>
			 </li>
			 <li>&nbsp;&nbsp;</li>
			 <li>
			     <button type="button" onclick="toImportQues();return false;">从文件导入题目</button>
			 </li>
			 <li>&nbsp;&nbsp;</li>
			 <li><button type="button" onclick="delRec('list','keys','/wareques/deleteWareques.do?productbaseid=<%=productId %>');return false;"><bean:message key="netTest.commonpage.delete"/></button></li>
			 
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
      <%if(productId==null){ %> 请选择课程信息，否则不能查询 <%} %>
      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="DataTableDiv">
    <table class="listDataTable" >
      <thead>
      <tr class="listDataTableHead">
        <td width="20px"><input type="checkbox" name="listCheckbox" value="checkbox" onClick="selectAllCheckbox('list',this,'keys')"></td>

        <td style="width: 50%; overflow: hidden">题目</td>

        <td>题目类型</td>
        
        <td>所在题库</td>

        <td>操作</td>
      </tr>
      </thead>
      <logic:present name="pageList">
	  <logic:iterate id="vo" name="pageList" indexId="ind" type="netTest.wareques.vo.Question">
      <tr class="<%=(ind%2==0)?"oddRow":"evenRow" %>">
        <td><input type="checkbox" name="keys" id='pkId<bean:write name="vo" property="quesid"/>' value="<bean:write name="vo" property="quesid"/>" onClick="selectInfo(this,'clickedRow')">
            <input id='pkId<bean:write name="vo" property="quesid"/>Name' type="hidden" value="第<%=ind+1 %>题"/>
            <input id='pkId<bean:write name="vo" property="quesid"/>Type' type="hidden" value="<bean:write name="vo" property="questype"/>"/>
        </td>

        <td class="noUnderline">
           <a href="javascript:void(0)" onclick="newDiv('/wareques/viewWareques.do?queryVO.quesid=<bean:write name="vo" property="quesid"/>',1,1,700,450);return false;">
              <!-- if tiankong, parameter like this: <bean:writeTK name="vo" property="question" questypeProp="questype" cutStr="false" filter="false" transfer="true" showIndentWay="2"/> ,but the has problem when cut string  -->
              <bean:writeSaas name="vo" property="question" cutStr="true" filter="false"/>
           </a>
        </td>

        <td><bean:writeSaas name="vo" property="questype" consCode="Question.QuesType"/></td>

        <td><bean:write name="vo" property="warename"/></td>

        <td>
            <img src="../styles/imgs/edit.png" title="<bean:message key="netTest.commonpage.modify"/>" style="cursor:pointer;" onclick="goUrl('/wareques/editWareques.do?queryVO.quesid=<bean:write name="vo" property="quesid"/>&editquesid=<bean:write name="vo" property="quesid"/>&backUrlEncode=','backUrlEncode');return false;"/>&nbsp;
            <img src="../styles/imgs/delete.png" title="删除" style="cursor:pointer;" onclick="delSingleRecAjax('/wareques/deleteWareques.do?productbaseid=<%=productId %>&keys=<bean:write name="vo" property="quesid"/>&backUrl=');return false;"/>
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
