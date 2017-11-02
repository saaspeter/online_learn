<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="sessionproductid" name="paperForm" property="sessionProductid" type="java.lang.Long"></bean:define>
<bean:define id="sessionproductname" name="paperForm" property="sessionProductname" type="java.lang.String"></bean:define>
<%if(sessionproductid==null) { sessionproductname=""; } %>
<html>
   <head>
	  <title>导入试卷题目</title>
	  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  <link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	  <script language=JavaScript src="../styles/script/pageAction.js"></script>
	  <script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	  <script type="text/javascript" src="../styles/script/utiltool.js"></script>
	  <html:base/>
	   
	  <script language="JavaScript" type="text/JavaScript">
      
		  function importQues(){
		     var fileObj = document.getElementById("file");
			 var fileValue = fileObj.value;
			 if(fileValue.length>0){
			    if(fileType=="txt"&&checkTxtType("file")){
			       document.forms[0].submit();  
			    }else if(fileType=="xls"&&checkExcelType("file")){
			       document.forms[0].submit();
			    }
			 }else{
			    alert("请选择上传文件!");
			    return;
			 }
		  }
		  
		  function selProduct_CB(id,name){
		      clearDiv();
		      var old_prod = document.getElementById("productidId").value;
		      if(id!=null&&id!=""&&old_prod!=id){
		         document.getElementById("productidId").value=id;
		         document.getElementById("productnameId").value=name;
		         removeWare();
		      }
		  }
		  
		  function removeWare(){
		      document.getElementById("wareidId").value='';
		      document.getElementById("warenameId").value='';
		  }
		  
		  function selectWare(){
	          var prodidStr = document.getElementById("productidId").value;
              var prodname = document.getElementById("productnameId").value;
	          if(prodidStr==null||prodidStr==""){
	             alert('请选择课程!');
	             return;
	          }
		      newDiv("/wareques/listWareSelect.do?selectType=1&productbaseid="+prodidStr+"&queryVO.productname="+prodname,1,1,670,300);
		   }
		   
	       function selWareCB(ids,names,productbaseid){
	          if(ids!=null&&ids!=""){
	             document.getElementById("wareidId").value=ids;
	             document.getElementById("warenameId").value=names;
	          }
	          clearDiv();
	       }
	  </script>
   </head>

<body>
	<html:form styleId="editForm" action="/paper/importpaperfile.do" method="post" enctype="multipart/form-data">
	    
	    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="paperForm" property="backUrl"/>">
     	
	    <div class="fieldsTitleDiv">
			从txt文件导入试卷
	    </div>

		<div class="editPage">
			<table width="90%" class="formTable2">
			   <tr>
                  <td align="center"><font color="red">*</font>试卷名称:</td>
                  <td>
                      <input type="text" name="vo.papername" value="" usage="notempty,max-length:90" fie="试卷名称" style="width:350px"/> 
                  </td>
               </tr>
			   <tr>
                  <td align="center"><font color="red">*</font>选择试卷课程:</td>
                  <td>
                      <input type="hidden" id="productidId" name="vo.productbaseid" value="<%=sessionproductid %>" usage="notempty" fie="所属课程"/>
                      <input type="text" id="productnameId" name="vo.productname" value="<%=sessionproductname %>" readonly="readonly" style="width:250px" onclick="selectProd(this);"/>
                      
                  </td>
               </tr>
               <tr>
                  <td align="center">选择题库:</td>
                  <td>
                     <input type="hidden" id="wareidId" name="wareid" value="" >
		 			 <input type="text" id="warenameId" name="vo.warenamestr" class="readonly" value="" readonly="readonly" style="width:250px"/>
         			 <img src="../styles/imgs/ico/search.gif" width="18px" alt="选择题库" title="选择题库" onclick="selectWare();" />
         			 <img src="../styles/imgs/ico/reset.gif" alt="删除题库" onclick="removeWare();">&nbsp;(选择题库后，所导入的题目会同时插入到题库中)
                  </td>
               </tr>
               <tr>
                  <td align="center"><font color="red">*</font>答题时间:</td>
                  <td>
                      <input type="text" name="vo.papertime" value="" usage="notempty,num-range:1:240" fie="答题时间" style="width:250px"/> 
                  </td>
               </tr>
               <tr>
                  <td align="center"><font color="red">*</font>试卷及格分数:</td>
                  <td>
                      <input type="text" name="vo.paperquascore" value="" usage="notempty,int+" fie="试卷及格分" style="width:250px"/> 
                  </td>
               </tr>
               <tr>
                  <td align="center">试卷说明:</td>
                  <td>
                      <textarea name="vo.paperdesc" rows="2" cols="60" usage="max-length:250" fie="试卷说明"></textarea><br>(250个字)
                  </td>
               </tr>
               <tr>
                  <td align="center">选择导入文件:</td>
                  <td>
                      <input id="fileId" type="file" name="file" size="40" usage="notempty,txt"/>
                  </td>
               </tr>
               <tr id="fileFormat_excel_Id" >
                  <td align="center">txt文件格式说明:</td>
                  <td colspan="2">
                      1. 第一行文件格式列说明, 包括其中的列说明<br>
                      2. 
                  </td>
               </tr>
               <tr id="exapleFile_excel_Id" >
                  <td align="center">样例文件下载:</td>
                  <td colspan="2">
                      <a href="<%=WebConstant.WebContext %>/pubs/download.jsp?filename=paper_import_demo.txt">paper_import_demo</a>
                  </td>
               </tr>
            </table>
		</div>
		
		<div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	    </div>
		
		<div style="text-align: center;">
		    <button type="button" onclick="submitForm('editForm');return false;">导入试卷</button>&nbsp;&nbsp;&nbsp;&nbsp;
		    <button type="button" onclick="getAndToUrl('backUrl');return false;">取消</button>
		</div>
    </html:form>
</body>
</html>
