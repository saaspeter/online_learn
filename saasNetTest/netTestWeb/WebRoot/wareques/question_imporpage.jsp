<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<html>
   <head>
	  <title>导入题库题目</title>
	  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	  <script language=JavaScript src="../styles/script/pageAction.js"></script>
	  <html:base/>
	  <bean:define id="wareidStr" name="warequesForm" property="queryVO.wareidStr" type="java.lang.String"></bean:define>
	  <bean:define id="warenameStr" name="warequesForm" property="queryVO.warenameStr" type="java.lang.String"></bean:define>
	  <bean:define id="productId" name="warequesForm" property="productbaseid" type="java.lang.Long"></bean:define>
	  <bean:define id="productName" name="warequesForm" property="productname" type="java.lang.String"></bean:define>
	   
	  <script language="JavaScript" type="text/JavaScript">
	      
	      function changeFileType(type){
	          if(type=='excel'){
	             document.getElementById("importtype_Id").value="excel";
	             document.getElementById("fileId").setAttribute("usage","notempty,excel");
	             document.getElementById("fileFormat_excel_Id").style.display="";
	             document.getElementById("exapleFile_excel_Id").style.display="";
	             document.getElementById("fileFormat_txt_Id").style.display="none";
	             document.getElementById("exapleFile_txt_Id").style.display="none";
	          }else {
	             document.getElementById("importtype_Id").value="txt";
	             document.getElementById("fileId").setAttribute("usage","notempty,txt");
	             document.getElementById("fileFormat_excel_Id").style.display="none";
	             document.getElementById("exapleFile_excel_Id").style.display="none";
	             document.getElementById("fileFormat_txt_Id").style.display="";
	             document.getElementById("exapleFile_txt_Id").style.display="";
	          }
	      }
	      
		  
		  function selectWare(){
	          var prodidStr = '<%=productId %>';
	          var productName = '<%=productName %>';
	          if(prodidStr==null||prodidStr==""){
	             alert('请选择课程!');
	             return;
	          }
		      newDiv("/wareques/listWareSelect.do?selectType=1&productbaseid="+prodidStr+"&queryVO.productname="+productName,1,1,670,300);
		   }
		   
	       function selWareCB(ids,names,productbaseid){
	          if(ids!=null&&ids!=""){
	             document.getElementById("wareidStrID").value=ids;
	             document.getElementById("warenameStrID").value=names;
	          }
	          clearDiv();
	       }
	       
	  </script>
   </head>
<%
   if(wareidStr==null || wareidStr.indexOf(wareidStr)==-1){
      wareidStr = "";
      warenameStr = "";
   }
 %>
<body>
	<html:form styleId="editForm" action='<%="/wareques/importwareques.do?vo.productbaseid="+productId %>' method="post" enctype="multipart/form-data">
	    <input type="hidden" id="wareidStrID" name="vo.wareid" value="<%=wareidStr %>" />              
        <input type="hidden" id="importtype_Id" name="importtype" value="txt" />
        <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="warequesForm" property="backUrl"/>">
     	
	    <div class="fieldsTitleDiv">
			题库导入TXT / Excel CSV格式题目
	    </div>

		<div class="editPage">
			<table width="95%" class="formTable2">
               <tr>
                  <td align="center" width="25%"><font color="red">*</font>选择所在题库:</td>
                  <td>
		 			 <input type="text" id="warenameStrID" class="readonly" value="<%=warenameStr %>" readonly="readonly" usage="notempty" fie="所在题库" style="width:300px"/>
         			 <img src="../styles/imgs/ico/search.gif" width="18px" alt="选择题库" onclick="selectWare();" />
                  </td>
               </tr>
               <tr>
                  <td align="center"><font color="red">*</font>导入文件格式:</td>
                  <td>
                     <label for="radio_txt_Id">
                         <input id="radio_txt_Id" type="radio" name="importfiletype" value="txt" checked="checked" onclick="changeFileType('txt')"/>txt文件
                     </label>
                     <label for="radio_excel_Id">
                         <input id="radio_excel_Id" type="radio" name="importfiletype" value="excel" onclick="changeFileType('excel')"/>excel/csv文件
                     </label>
                  </td>
               </tr>
               <tr>
                  <td align="center"><font color="red">*</font>导入文件:</td>
                  <td>
                      <input id="fileId" type="file" name="vo.quesFile" size="40" usage="notempty,txt" fie="导入文件"/>
                  </td>
               </tr>
               <tr id="fileFormat_excel_Id" style="display: none;">
                  <td align="center" width="25%">Excel/CSV文件格式说明:</td>
                  <td>
                      1. 第一行文件格式列说明, 包括其中的列说明<br>
                      2. 目前导入仅支持选择题，判断题，问答题
                  </td>
               </tr>
               <tr id="exapleFile_excel_Id" style="display: none;">
                  <td align="center" width="25%">样例文件下载:</td>
                  <td>
                      <a href="<%=WebConstant.WebContext %>/pubs/download.jsp?filename=question_excel_demo.xls">question_excel_demo</a>
                  </td>
               </tr>
               <tr id="fileFormat_txt_Id" >
                  <td align="center" width="25%">txt文件格式说明:</td>
                  <td>
                      1. 每行有 属性行, 题目行, 题目选项行, 题目答案行<br>
                      2. 目前导入仅支持选择题，判断题，问答题
                  </td>
               </tr>
               <tr id="exapleFile_txt_Id" >
                  <td align="center" width="25%">样例文件下载:</td>
                  <td>
                      <a href="<%=WebConstant.WebContext %>/pubs/download.jsp?filename=question_txt_demo.txt">question_txt_demo</a>
                  </td>
               </tr>
            </table>
		</div>
		
		<div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	    </div>
		
		<div style="text-align: center;">
		    <button type="button" onclick="submitForm('editForm');return false;">导入题目</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    <button type="button" onclick="getAndToUrl('backUrl');return false;">取消</button>
		</div>
    </html:form>
</body>
</html>
