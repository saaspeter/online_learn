<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="warequesForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="warequesForm" property="editType" type="java.lang.Integer"/>
<bean:define id="quesType" name="warequesForm" property="vo.questype" type="java.lang.Integer"/>
<bean:define id="filetype" name="warequesForm" property="vo.filetype" type="java.lang.Short"/>
<bean:define id="quesidVar" name="warequesForm" property="vo.quesid" type="java.lang.Long"/>
<bean:define id="productbaseidVar" name="warequesForm" property="vo.productbaseid" type="java.lang.Long"/>
<bean:define id="ableuploadlocal" name="warequesForm" property="ableuploadlocal" type="java.lang.Boolean"/>

<%  boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true; } 
    String quesidVarStr = (quesidVar==null)?"":quesidVar.toString();
%>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title><bean:message key="netTest.page.common.title"/></title>
    <bean:size id="itemNoVar" name="warequesForm" property="vo.itemList"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/javascript" src="../styles/ckeditor/ckeditor.js"></script>
    <script language="javascript">
	<!--
	   <% if(itemNoVar==null) itemNoVar = 0; %>
	   var itemNo = <%=itemNoVar %>;
	   var itemNum = <%=itemNoVar %>;
	          
       function changeQuesRadio(value){
          if(value=='<%=QuestionConstant.FileType_None.toString() %>'){
             document.getElementById("quesFileId").value = '';
             document.getElementById("quesFileId").disabled = "disabled";
             document.getElementById("quesFileUploadTrId").style.display = 'none';
             document.getElementById("quesFileurlTrId").style.display = 'none';
          }else if(value=='<%=QuestionConstant.FileType_pict.toString() %>'){
             document.getElementById("quesFileId").disabled = "";
             document.getElementById("quesFileId").value = '';
             document.getElementById("quesFileUploadTrId").style.display = 'block';
             document.getElementById("quesFileurlTrId").style.display = 'none';
          } else if(value=='<%=QuestionConstant.FileType_voice.toString() %>'){
        	  document.getElementById("quesFileUploadTrId").style.display = 'none';
        	  document.getElementById("quesFileurlTrId").style.display = 'block';
          }
       }
       
       function changeItemRadio(value,itemFileId){
          if(value=='<%=QuestionConstant.FileType_None.toString() %>'){
             document.getElementById(itemFileId).value = '';
             document.getElementById(itemFileId).disabled = "disabled";
          }else {
             document.getElementById(itemFileId).disabled = "";
             document.getElementById(itemFileId).value = '';
          }
       }
       
       function changeCheck(id){
          if(id==null||id==''){ return; }
          if(document.getElementById(id).checked){
             document.getElementById(id+"Right").value="<%=QuestionConstant.IsRight_right.toString() %>";
             // if it is danxuan,then unchecked the other checkboxes,and set their values wrong
             
             <%if(QuestionConstant.QuesType_DanXuan.equals(quesType)){ %>
                var i = 0;
                for(;i<itemNo;i++){
                   if(typeof(document.getElementById("itemCheckId"+i))!="undefined"&&document.getElementById("itemCheckId"+i)!=null&&(id!="itemCheckId"+i)){
                      document.getElementById("itemCheckId"+i).checked="";
                      document.getElementById("itemCheckId"+i+"Right").value="<%=QuestionConstant.IsRight_wrong.toString() %>";
                   }
                }
             <%} %>
             
          }else{
             document.getElementById(id+"Right").value="<%=QuestionConstant.IsRight_wrong.toString() %>";
          }
       }
       
       function checkQuesFile(inputid, previewid){
          var url = document.getElementById(inputid).value;
       }
	   
	//-->
	</script>
  </head>
  
  <body> 
	<div class="editPage">
	<html:form styleId="editForm" action='<%="/wareques/saveWareques.do?recurltype=2&vo.quesid="+quesidVarStr+"&vo.productbaseid="+productbaseidVar %>' method="post" enctype="multipart/form-data">
     <input id="backUrl" type="hidden" name="backUrl" value='<bean:write name="warequesForm" property="backUrl"/>'>
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value='<bean:write name="warequesForm" property="backUrlEncode"/>'>
     <input id="delItemStrId" type="hidden" name="delItemStr" value=""/>
	 <input id="prodidId" type="hidden" value="<%=productbaseidVar %>">
	 <input name="paperid" type="hidden" value='<bean:write name="warequesForm" property="paperid"/>'>

     <input type="hidden" name="vo.questype" value="<bean:write name="warequesForm" property="vo.questype"/>"/>
	
	  <div id="fieldsTitleDiv"><%if(!isDisable){ %>新增<%}else{ %>编辑<%} %>题目</div>
          
      <div style="width:98%;float: left;padding-left: 7px;">
      
	  <div style="padding:7px;margin-top:15px;background-color:#DCEAFC"><font style="font-weight: bold;">题目属性</font></div>
	  <div>
		  <jsp:include flush="true" page="ques_include.jsp"></jsp:include>
	  </div>
	  <div style="padding:7px;margin-top:10px;background-color:#DCEAFC"><font color="red">*</font><font style="font-weight: bold;">题目题干</font></div>
	  <div>
	      <table class="formTable">
		     <tr>
		        <td colspan="2">
	                <textarea id="editor1" name="vo.question" style="width:100%;font-size: large;" rows="4" usage="notempty,max-length:1500" fie="题目题干"><bean:write name="warequesForm" property="vo.question"/></textarea>
	                <script type="text/javascript">
				         var p_editor = CKEDITOR.replace( 'editor1',
				         {
				      		toolbar : 'question',
				      		height : '80px'
				    	 });
				    </script>
	            </td>
	         </tr>
	         <tr style="background: #DCEAFC">
		        <td align="left" width="85">多媒体附件</td>
		        <td>
		            <table style="border: 0px;width: 100%;">
		               <tr>
		                  <td>
		                      <input type="hidden" name="vo.filetypeOrigin" value="<bean:write name="warequesForm" property="vo.filetype"/>" >
				              <%if(QuestionConstant.FileType_None.equals(filetype)){ isDisable=false; } 
				                else {isDisable=true;} %>
		                      <html:radio styleId="quesFileRadioId0" name="warequesForm" property="vo.filetype" value="<%=QuestionConstant.FileType_None.toString() %>" onclick='<%="changeQuesRadio(\'"+QuestionConstant.FileType_None.toString()+"\')" %>' disabled="<%=isDisable%>"></html:radio>无
		                      <html:radio styleId="quesFileRadioId1" name="warequesForm" property="vo.filetype" value="<%=QuestionConstant.FileType_pict.toString() %>" onclick='<%="changeQuesRadio(\'"+QuestionConstant.FileType_pict.toString()+"\')" %>' disabled="<%=isDisable%>"></html:radio>图片
		                      
		                      <html:radio styleId="quesFileRadioId2" name="warequesForm" property="vo.filetype" value="<%=QuestionConstant.FileType_voice.toString() %>" onclick='<%="changeQuesRadio(\'"+QuestionConstant.FileType_voice.toString()+"\')" %>' disabled="<%=isDisable%>"></html:radio>音频
		                      
		                      <%if(isDisable){%>&nbsp;<a href="javascript:void(0)" onclick="delFile('quesFileRadioId','1',this);return false;" >删除附件</a><%} %>
		                  </td>
		               </tr>
		               <tr id="quesFileUploadTrId" style="<%if(!QuestionConstant.FileType_pict.equals(filetype)){out.print("display:none");}%>">
		                  <td style="white-space: nowrap;width: 100%;">
		                      选择文件上传: <input id="quesFileId" type="file" name="vo.quesFile" style="width:70%" disabled="disabled" onchange="checkQuesFile('quesFileId','quesPreviewImgId');" usage="notempty" fie="题干文件">
		                  </td>
		               </tr>
		               <tr id="quesFileurlTrId" style="<%if(!QuestionConstant.FileType_voice.equals(filetype)){out.print("display:none");}%>">
		                  <td style="white-space: nowrap;width: 100%;">
		                      输入文件在线地址: <input id="quesFileurlId" type="text" name="vo.fileurl" style="width:70%" value='<bean:write name="warequesForm" property="vo.fileurl"/>' >
		                  </td>
		               </tr>
		            </table>
		        </td>
	         </tr>
	      </table>
	  </div>
	  <div style="padding:7px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">题目选项</font></div>
	  <div>
	      <table id="itemTableId" class="formTable2">
	         <tbody>
             <tr>
                <td width="43"><font color="red">*</font>答案</td>
                <td><font color="red">*</font>答题选项</td>
             </tr>
             <logic:iterate id="quesItemVO" name="warequesForm" property="vo.itemList" indexId="ind">
             <tr id="itemTableTrId<%=ind%>">
                <td align="center">
                   <html:checkbox styleId='<%="itemCheckId"+ind %>' name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" onclick='<%="changeCheck(\'itemCheckId"+ind+"\')" %>'></html:checkbox>
                   <input type="hidden" id='<%="itemCheckId"+ind+"Right" %>' name='<%="quesitem["+ind+"].isright" %>' value='<bean:write name="quesItemVO" property="isright"/>'>
                </td>
                <td>
                   <div>
                   <input type="hidden" name="quesitem[<%=ind%>].quesid" value="<bean:write name="quesItemVO" property="quesid"/>">
                   <input id="quesitemId<%=ind%>" type="hidden" name="quesitem[<%=ind%>].quesitemid" value="<bean:write name="quesItemVO" property="quesitemid"/>">
                   &nbsp;<input type="text" name="quesitem[<%=ind%>].quesitemcontent" value="<bean:write name="quesItemVO" property="quesitemcontent"/>" usage="notempty,max-length:300" fie="答题选项" style="width:85%">
                     &nbsp;<a href="javascript:void(0)" onclick="switchshow('divfile<%=ind%>');return false;" title="图片附件"><img style="border:0px;width: 20px;" src="<%=WebConstant.WebContext %>/styles/imgs/picture.png"/></a>
                     &nbsp;<a href="javascript:void(0)" onclick="delNewRecDiv('<%=ind%>');return false;" title="删除选项"><img style="border:0px;width: 20px;" src="<%=WebConstant.WebContext %>/styles/imgs/delete2.png"/></a>
                   </div>
                   <div id="divfile<%=ind%>" style="background: #DCEAFC;display: none"> 
                       <input type="hidden" name='<%="quesitem["+ind+"].filetypeOrigin" %>' value='<bean:write name="quesItemVO" property="filetype"/>' >
                       <logic:equal name="quesItemVO" property="filetype" value="<%=QuestionConstant.FileType_None.toString() %>"><%isDisable=false; %></logic:equal> 
                       <logic:notEqual name="quesItemVO" property="filetype" value="<%=QuestionConstant.FileType_None.toString() %>"><%isDisable=true; %></logic:notEqual>
                       &nbsp;附件:<html:radio styleId='<%="itemFileRadio"+ind+"Id0" %>' name="quesItemVO" property="filetype" tagName='<%="quesitem["+ind+"].filetype" %>' value="<%=QuestionConstant.FileType_None.toString() %>" onclick='<%="changeItemRadio(\'"+QuestionConstant.FileType_None.toString()+"\',\'itemFileId"+ind+"\')" %>' disabled="<%=isDisable%>"></html:radio>无
		               <html:radio styleId='<%="itemFileRadio"+ind+"Id1" %>' name="quesItemVO" property="filetype" tagName='<%="quesitem["+ind+"].filetype" %>' value="<%=QuestionConstant.FileType_pict.toString() %>" onclick='<%="changeItemRadio(\'"+QuestionConstant.FileType_pict.toString()+"\',\'itemFileId"+ind+"\')" %>' disabled="<%=isDisable%>"></html:radio>图片
		               <input id="itemFileId<%=ind%>" type="file" name="quesitem[<%=ind%>].itemFile" size="60" disabled="disabled" onchange="checkQuesFile('itemFileId<%=ind%>','');">
	                   <%if(isDisable){%>&nbsp;<a href="javascript:void(0)" onclick="delFile('itemFileRadio<%=ind %>Id','2',this);return false;">删除附件</a><%} %>
                    </div>
                </td>
             </tr>
             </logic:iterate>
             </tbody>
           </table>
	  </div>
	  <div style="text-align:center;margin:15px"><a href="#" class="button green medium fontBold" onclick="addItem();return false;">新增题目选项</a></div>
	  
	  <div style="padding:7px;margin-top:30px;background-color:#DCEAFC"><font style="font-weight: bold;">解题思路</font></div>
	  <div>
	      <table class="formTable">
	         <tbody>
             <tr>
                <td>
                    <textarea id="editor2" name="answerVO.solvedesc" style="width:100%" rows="4" usage="max-length:1500" fie="解题思路"><bean:write name="warequesForm" property="answerVO.solvedesc"/></textarea>
                    <script type="text/javascript">
				         var p_editor = CKEDITOR.replace( 'editor2',
				         {
				      		toolbar : 'question',
				      		height : '120px'
				    	 });
				    </script>
                </td>
             </tr>
             </tbody>
           </table>
	  </div>
	  
	  </div>
	  
	  <div style="width:30px;float:right">&nbsp;</div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button onclick="submitQues();" type="button">保存题目</button></li>
			<li><button onclick="getAndToUrl('backUrl');return false;" type="button">取消返回</button></li>
		 </ul>
	  </div>
	  <br>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	
	<script type="text/javascript">
	   var dTable = document.getElementById('itemTableId');
	   var tbody = dTable.tBodies[0]; 
	   function addItem(){
	         if(itemNum+1>10){
	            alert('选择题最多有10个选项!');
	            return;
	         }
     
             var newTr = tbody.insertRow(-1);
             newTr.id="itemTableTrId"+itemNo;      
             var newTd1 = newTr.insertCell(0); 
             newTd1.align = "center";
             newTd1.innerHTML = "<input type='checkbox' id='itemCheckId"+itemNo+"' name='isright' value='<%=QuestionConstant.IsRight_right.toString() %>' onclick=\"changeCheck('itemCheckId"+itemNo+"')\">"+
                                "<input type='hidden' id='itemCheckId"+itemNo+"Right' name='quesitem["+itemNo+"].isright' value='<%=QuestionConstant.IsRight_wrong.toString() %>'>";
  
             var newTd2 = newTr.insertCell(1);
             newTd2.innerHTML = "<div>"+
                   "<input type='hidden' name='quesitem["+itemNo+"].quesid' value=''>"+
                   "<input id='quesitemId"+itemNo+"' type='hidden' value=''>"+
                   "&nbsp;<input type='text' name='quesitem["+itemNo+"].quesitemcontent' value='' usage='notempty,max-length:300' fie='答题选项' style='width:85%'>"+
                       " &nbsp;<a href=\"javascript:void(0)\" onclick=\"switchshow('divfile"+itemNo+"');return false;\" title=\"图片附件\"><img style=\"border:0px;width: 20px;\" src=\"<%=WebConstant.WebContext %>/styles/imgs/picture.png\"/></a>"+
                       " &nbsp;<a href=\"javascript:void(0)\" onclick=\"delNewRecDiv('"+itemNo+"');return false;\" title=\"删除选项\"><img style=\"border:0px;width: 20px;\" src=\"<%=WebConstant.WebContext %>/styles/imgs/delete2.png\"/></a>"+                      
                   "</div>"+
                   "<div id='divfile"+itemNo+"' style='background: #DCEAFC;display:none;'>"+ 
                       " &nbsp;附件:<input type='radio' name='quesitem["+itemNo+"].filetype' value='<%=QuestionConstant.FileType_None.toString() %>' checked='checked' onclick=\"changeItemRadio('<%=QuestionConstant.FileType_None.toString() %>','itemFileId"+itemNo+"')\">无"+
                       " <input type='radio' name='quesitem["+itemNo+"].filetype' value='<%=QuestionConstant.FileType_pict.toString() %>' onclick=\"changeItemRadio('<%=QuestionConstant.FileType_pict.toString() %>','itemFileId"+itemNo+"')\">图片"+
                       " <input id='itemFileId"+itemNo+"' type='file' name='quesitem["+itemNo+"].itemFile' value='' disabled='disabled' onchange=\"checkQuesFile('itemFileId"+itemNo+"','');\" size='60'>"+
                    "</div>";
  	         itemNo = itemNo+1;
	         itemNum = itemNum+1;
	   }
	   
	   function switchshow(id){
	      var obj = document.getElementById(id);
	      if(obj.style.display=='block'){
	         obj.style.display='none';
	      }else if(obj.style.display=='none'){
	         obj.style.display='block';
	      }
	   }
	   
	   function delNewRecDiv(ind){
	      if(itemNum<3){
	         alert('题目选项不能少于2个!');
	         return;
	      }
	      itemNum = itemNum-1;
	      // if modify mode,then write down the delete item
	      <% if(editType!=null&&editType.intValue()==WebConstant.editType_edit){ %>
	           var idVar = "quesitemId"+ind;
	           document.getElementById("delItemStrId").value = document.getElementById("delItemStrId").value+document.getElementById(idVar).value+",";
	      <%} %>
	      
	      var rowNo = document.getElementById("itemTableTrId"+ind).sectionRowIndex;
	      dTable.deleteRow(rowNo);
	   }
	   
	   function checkItems(){
	      var ret = true;
	      var mess1 = "<li>请填写答案选项或选择选项附件!</li>";
	      var mess2 = "<li>请选择答案选项的附件文件!</li>";
	      var mess3 = "<li>请选择正确的答案选项!</li>";
	      var mess = "";
	      for(var i=0;i<itemNo;i++){
	          if(document.getElementsByName("quesitem["+i+"].quesitemcontent")!=null&&document.getElementsByName("quesitem["+i+"].quesitemcontent").length>0){
	             if(document.getElementsByName("quesitem["+i+"].quesitemcontent")[0].value==''){
	                if(getCheckedValue("quesitem["+i+"].filetype")!=null&&(getCheckedValue("quesitem["+i+"].filetype")=='<%=QuestionConstant.FileType_None.toString() %>'
	                   ||document.getElementById("itemFileId"+i).value=='')){
	                    mess += mess1;
	                    ret = false;
	                    break;
	                }
	             }else if(getCheckedValue("quesitem["+i+"].filetype")!=null&&getCheckedValue("quesitem["+i+"].filetype")!='<%=QuestionConstant.FileType_None.toString() %>'
	                  &&document.getElementById("itemFileId"+i).value==''){
	                    mess += mess2;
	                    ret = false;
	                    break;
	             }
	          }
	      }
	      var checkedItemVal = getCheckedValue("isright");
	      if(checkedItemVal==null||checkedItemVal==''){
	         ret = false;
	         mess += mess3;
	      }
	      if(!ret){
	         var messDiv = document.getElementById("displayMessDiv");
	         messDiv.innerHTML = "";
	         if(i<itemNo){
	            document.getElementsByName("quesitem["+i+"].quesitemcontent")[0].focus();
	         }
	         messDiv.innerHTML=messDiv.innerHTML+mess;
	      }
	      return ret;
	   }
	   
	   function delFile(Idprefix,type,obj){
	      document.getElementById(Idprefix+"0").disabled="";
	        document.getElementById(Idprefix+"0").checked="checked";
	      document.getElementById(Idprefix+"1").disabled="";
	        document.getElementById(Idprefix+"1").checked="";
	      if(type=="1"){
	         document.getElementById(Idprefix+"2").disabled="";
	         document.getElementById(Idprefix+"2").checked="";
	         document.getElementById("quesFileUploadTrId").style.display="none";
	         document.getElementById("quesFileId").value="";
	         document.getElementById("quesFileId").disabled="disabled";
	         document.getElementById("quesFileurlTrId").style.display="none";
	         document.getElementById("quesFileurlId").value="";
	      } 
	      obj.style.visibility="hidden";
	   }
	   
	   function submitQues(){
	       var checkRet = checkItems(); 
	       if(checkRet){
	    	   var ques_content = CKEDITOR.instances.editor1.getData();
	    	   document.getElementById("editor1").value = ques_content;
	    	   var editor2_content = CKEDITOR.instances.editor2.getData();
	    	   document.getElementById("editor2").value = editor2_content;
	           submitForm('editForm');
	       }
	   }
	   
	</script>
	
  </body>
</html:html>
