<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="warequesForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="warequesForm" property="editType" type="java.lang.Integer"/>
<bean:define id="quesType" name="warequesForm" property="vo.questype" type="java.lang.Integer"/>
<bean:define id="editquesid" name="warequesForm" property="editquesid" type="java.lang.String"/>
<bean:define id="quesidLong" name="warequesForm" property="vo.quesid" type="java.lang.Long"/>
<bean:define id="filetype" name="warequesForm" property="vo.filetype" type="java.lang.Short"/>
<bean:define id="productbaseidVar" name="warequesForm" property="vo.productbaseid" type="java.lang.Long"/>
<bean:define id="ableuploadlocal" name="warequesForm" property="ableuploadlocal" type="java.lang.Boolean"/>

 <% boolean isDisable=false;
    String quesid = null;
    if(quesidLong!=null)
       quesid = String.valueOf(quesidLong);
    if(editType!=null&&editType.intValue()!=WebConstant.editType_add){
      isDisable = true;
    } 
 %>
      
 <% int itemNoVar = 0; %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>阅读理解</title>

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
	   
	   function changeCheck(id){
          if(id==null||id==''){ return; }
          if(document.getElementById(id).checked){
             document.getElementById(id+"Right").value="<%=QuestionConstant.IsRight_right.toString() %>";            
          }else{
             document.getElementById(id+"Right").value="<%=QuestionConstant.IsRight_wrong.toString() %>";
          }
       }
	   
	   g_check.alert=true;
	          	   
	//-->
	</script>
  </head>
  
  <body> 
	<div class="editPage">
	<html:form styleId="editForm" action='<%="/wareques/editSubques.do?recurltype=2&vo.quesid="+(quesid==null?"":quesid)+"&vo.productbaseid="+productbaseidVar %>' method="post" enctype="multipart/form-data">
     <input id="backUrl" type="hidden" name="backUrl" value='<bean:write name="warequesForm" property="backUrl"/>'>
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value='<bean:write name="warequesForm" property="backUrlEncode"/>'>
     <input id="delItemStrId" type="hidden" name="delItemStr" value=""/>
     <input id="subquesOptId" type="hidden" name="subquesOpt" value='<bean:write name="warequesForm" property="subquesOpt"/>'>
	 <input id="prodidId" type="hidden" value="<%=productbaseidVar %>">
	 <input id="editquesidId" type="hidden" name="editquesid" value="<%=editquesid %>" />
	 <input id="delquesidId" type="hidden" name="delquesid" value="">
	 <input id="delquestypeId" type="hidden" name="delquestype" value="">
	 <input type="hidden" name="vo.wareid" value='<bean:write name="warequesForm" property="vo.wareid"/>'>
	 <input name="paperid" type="hidden" value='<bean:write name="warequesForm" property="paperid"/>'>

	 <input id="keysId" type="hidden" name="keys" value="">
	 
     <input type="hidden" name="vo.questype" value="<bean:write name="warequesForm" property="vo.questype"/>"/>
	
	  <div id="fieldsTitleDiv"><%if(!isDisable){ %>新增<%}else{ %>编辑<%} %>题目</div>
          
      <div style="width:98%;float: left;padding-left: 7px;">
	  <div style="padding:7px;margin-top:20px;background-color:#DCEAFC"><font style="font-weight: bold;">题目属性</font></div>
	  <%if(quesid!=null&&!quesid.equals(editquesid)){ %>
	      <!-- 只读题目 -->
	      <div>
		      <jsp:include flush="true" page="ques_include.jsp"></jsp:include>
		  </div>
		   <div style="width:99%;padding:7px;margin-top:10px;background-color:#DCEAFC"><font color="red">*</font><font style="font-weight: bold;">阅读材料</font></div>
		   <div>
		      <table class="formTable">
			     <tr>
			        <td align="left" >
		                <bean:write name="warequesForm" property="vo.question" filter="false"/>
		            </td>
		            <td align="left" width="80px" style="background-color:#DCEAFC">
		                <a href="#" onclick="saveSubques();return false;">编辑题干</a>
		            </td>
		         </tr>
		         <logic:equal name="warequesForm" property="vo.filetype" value="<%=QuestionConstant.FileType_pict.toString() %>">
		         <tr style="background: #DCEAFC">
			        <td align="left" id="container" colspan="2"> 
		               <img src="<%=WebConstant.FileContext %><bean:write name="warequesForm" property="vo.fileurl"/>" height="100">
			        </td>
		         </tr>
		         </logic:equal>
		      </table>
		  </div>
	  <%} %>
	  <%if(quesid==null||quesid.equals(editquesid)){ %>
	      <!-- 可以编辑题目 -->
	   	   <div>
               <jsp:include flush="true" page="ques_include.jsp"></jsp:include>
		   </div>
		   <div style="padding:7px;margin-top:10px;background-color:#DCEAFC"><font color="red">*</font><font style="font-weight: bold;">阅读材料</font></div>
		   <div>
		      <table class="formTable">
			     <tr>
			        <td colspan="2">
		                <textarea id="questionId" name="vo.question" style="width:100%" rows="9" usage="notempty,max-length:8000" fie="阅读材料"><bean:write name="warequesForm" property="vo.question"/></textarea>
		                <script type="text/javascript">
					         var p_editor = CKEDITOR.replace( 'questionId',
					         {
					      		toolbar : 'question',
					      		height : '250px'
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
			                      
			                      <html:radio styleId="quesFileRadioId2" name="warequesForm" property="vo.filetype" value="<%=QuestionConstant.FileType_voice.toString() %>" onclick='<%="changeQuesRadio(\'"+QuestionConstant.FileType_voice.toString()+"\')" %>' disabled="<%=isDisable%>"></html:radio>声音
			                      
			                      <%if(isDisable){%>&nbsp;<a href="javascript:void(0)" onclick="delFile('quesFileRadioId','1',this);return false;" >删除附件</a><%} %>
			                  </td>
			               </tr>
			               <tr id="quesFileUploadTrId" style="<%if(!QuestionConstant.FileType_pict.equals(filetype)&&!QuestionConstant.FileType_voice.equals(filetype)){out.print("display:none");}%>">
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
	  <%} %>
	  
	  <div style="padding:7px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题</font></div>
	  <div style="padding:7px;margin-top:10px;text-align: left;">
	      <font style="font-weight: bold;">题目选项排列方式:</font> 
	      <%if(quesid!=null&&!quesid.equals(editquesid)){ %>
	          <logic:equal value="<%=QuestionConstant.Rowitems_One.toString() %>" name="warequesForm" property="vo.rowitems">
	          	每个题目选项各占一行
	          </logic:equal>
	          <logic:equal value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="warequesForm" property="vo.rowitems">
	          	每个题目所有选项在一行显示
	          </logic:equal>
	      <%}else { %>
		      <label for="rowitemId1">
		      <html:radio styleId="rowitemId1" value="<%=QuestionConstant.Rowitems_One.toString() %>" name="warequesForm" property="vo.rowitems">每个题目选项各占一行</html:radio>
		      </label>
		      &nbsp;&nbsp;
		      <label for="rowitemId2">
		      <html:radio styleId="rowitemId2" value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="warequesForm" property="vo.rowitems">每个题目所有选项在一行显示</html:radio>
		      </label>
	      <%} %>
	  </div>
	  <logic:present name="warequesForm" property="vo.subquesList">
	  <logic:iterate id="subquesVO" name="warequesForm" property="vo.subquesList" indexId="indques" type="netTest.wareques.vo.Question">
	      <%if(subquesVO.getQuesid()!=null&&!String.valueOf(subquesVO.getQuesid()).equals(editquesid)){ %>
	      <!-- 只读状态 -->
          <div>
		      <table class="formTable2">
		         <tr style="background-color:#DCEAFC">
		             <td width="16"><%=indques+1 %>.&nbsp;</td>
		             <td><bean:write name="subquesVO" property="question"/>&nbsp;&nbsp;
		             <a href="javascript:void(0)" onclick="editSubques('<bean:write name="subquesVO" property="quesid"/>');return false;">编辑</a>&nbsp;&nbsp;
		             <a href="javascript:void(0)" onclick="delSubques('<bean:write name="subquesVO" property="quesid"/>','<bean:write name="subquesVO" property="questype"/>');return false;">删除该题</a>
		             </td>
		         </tr>
	             <logic:iterate id="quesItemVO" name="subquesVO" property="itemList" indexId="inditem">
	             <tr>
	                <td align="center" width="16"> 
	                   <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
	                      <img src="../styles/imgs/ico/check.gif" border="0"/>
	                   </logic:equal>
	                </td>
	                <td>
	                   &nbsp;<bean:write name="quesItemVO" property="quesitemcontent"/>
	                </td>
	             </tr>
	             </logic:iterate>
	          </table>
		  </div>
		  <%} %>
		  <%if((subquesVO.getQuesid()==null&&subquesVO.getPid()!=null)
		      ||(subquesVO.getQuesid()!=null&&String.valueOf(subquesVO.getQuesid()).equals(editquesid))){ 
		         if(subquesVO.getSubquesList()!=null) {itemNoVar=subquesVO.getItemList().size();} %>
		  <!-- 编辑状态 -->
		  <div>
              <table id="itemTableId" class="formTable2">
                 <tbody>
                 <tr style="background-color:#DCEAFC">
		             <td width="43"><%=indques+1 %>.&nbsp;</td>
		             <td>
		                 <textarea id="vo2_question_id" name="vo2.question" style="width:85%" rows="2" usage="notempty,max-length:1500" fie="题目题干"><bean:write name="subquesVO" property="question"/></textarea>
		                 <input type="hidden" name="vo2.quesid" value="<bean:write name="subquesVO" property="quesid"/>"/>
		                 <input type="hidden" name="vo2.questype" value="<bean:write name="subquesVO" property="questype"/>"/>
		                 <input type="hidden" name="vo2.wareid" value="<bean:write name="warequesForm" property="vo.wareid"/>" />
		                 <input type="hidden" name="vo2.productbaseid" value="<bean:write name="warequesForm" property="vo.productbaseid"/>"/>
		                 <input type="hidden" name="vo2.pid" value="<bean:write name="warequesForm" property="vo.quesid"/>"/>
		                 <input type="hidden" name="vo2.filetype" value="<%=QuestionConstant.FileType_None.toString() %>"/>
		                 &nbsp;<a href="#" onclick="delSubques('<bean:write name="subquesVO" property="quesid"/>','<bean:write name="subquesVO" property="questype"/>');return false;">删除该题</a>
		             </td>
		         </tr>
		         <tr>
                     <td width="43"><font color="red">*</font>答案</td>
                     <td><font color="red">*</font>答题选项</td>
                 </tr>
	             <logic:iterate id="quesItemVO" name="subquesVO" property="itemList" indexId="inditem">
	             <tr id="itemTableTrId<%=inditem%>">
	                <td align="center">
	                   <html:checkbox styleId='<%="itemCheckId"+inditem %>' name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" onclick='<%="changeCheck(\'itemCheckId"+inditem+"\')" %>'></html:checkbox>
	                   <input type="hidden" id='<%="itemCheckId"+inditem+"Right" %>' name='<%="quesitem["+inditem+"].isright" %>' value='<bean:write name="quesItemVO" property="isright"/>'>
	                </td>
	                <td>
	                   <div>
	                   <input type="hidden" name="quesitem[<%=inditem%>].quesid" value="<bean:write name="quesItemVO" property="quesid"/>">
	                   <input id="quesitemId<%=inditem%>" type="hidden" name="quesitem[<%=inditem%>].quesitemid" value="<bean:write name="quesItemVO" property="quesitemid"/>">
	                   &nbsp;<input type="text" name="quesitem[<%=inditem%>].quesitemcontent" value="<bean:write name="quesItemVO" property="quesitemcontent"/>" usage="notempty,max-length:300" fie="答题选项" style="width:87%">
	                     &nbsp;<a href="#" onclick="delNewRecDiv('<%=inditem%>');return false;">删除</a>
	                   </div>
	                </td>
	             </tr>
	             </logic:iterate>
	             </tbody>
	             <tr><td>&nbsp;</td><td align="center"><a href="#" onclick="addItem();return false;">新增题目选项</a>&nbsp;&nbsp;&nbsp;&nbsp;
	                 <a href="#" onclick="saveSubques();return false;">保存该问题</a>
	                 </td>
	             </tr>
	          </table>
		  </div>
		  <%} %>
	  </logic:iterate>
	  </logic:present>
	  <div style="text-align:center;margin:15px"><a href="javascript:void(0)" class="button green medium fontBold" onclick="addSubques();return false;">新增子问题</a></div>
	  
	  <div style="padding:7px;margin-top:20px;background-color:#DCEAFC"><font style="font-weight: bold;">解题思路</font></div>
	  <%if(quesid==null||quesid.equals(editquesid)){ %>
	  <div>
	      <table class="formTable">
	         <tbody>
             <tr>
                <td>
                    <textarea name="answerVO.solvedesc" id="editor2" style="width:100%" rows="4" usage="max-length:1500" fie="解题思路"><bean:write name="warequesForm" property="answerVO.solvedesc"/></textarea>
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
	  <%} %>
	  <%if(quesid!=null&&!quesid.equals(editquesid)){ %>
	  <div>
	      <table class="formTable">
		     <tr>
		        <td align="left" >
	      			<bean:write name="warequesForm" property="answerVO.solvedesc"/>
	      		</td>
	      	 </tr>
	      </table>
	  </div>
	  <%} %>
	  
	  </div>
	  <div style="width:20px;float:right">&nbsp;</div>
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
	  	    <li><button type="button" onclick="submitQues();">保存该题并返回</button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;">取消返回</button></li>
		 </ul>
	  </div>
	  <br>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	
	<script type="text/javascript">

	   var itemNo = <%=itemNoVar %>;
	   var itemNum = <%=itemNoVar %>;
	   var optObjval = document.getElementById("subquesOptId").value;
	   if(typeof(optObjval)!='undefined'&&optObjval!=null&&optObjval=='1'){
		   window.scrollTo(0,document.body.scrollHeight); // scroll to bottom, if add new subques
	   }

	   function addItem(){
	   	   var dTable = document.getElementById('itemTableId');
	       var tbody = dTable.tBodies[0]; 
           if(itemNum+1>15){
              alert('选择题最多有15个选项!');
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
                   "&nbsp;<input type='text' name='quesitem["+itemNo+"].quesitemcontent' value='' usage='notempty,max-length:300' fie='答题选项' style='width:87%'>"+
                       "&nbsp;&nbsp;<a href='#' onclick=\"delNewRecDiv('"+itemNo+"');return false;\">删除</a>"+
                   "</div>";
  	         itemNo = itemNo+1;
	         itemNum = itemNum+1;
	   }
	   
	   function delNewRecDiv(ind){
	      if(itemNum<3){
	         alert('题目选项不能少于2个!');
	         return;
	      }
	      var dTable = document.getElementById('itemTableId');
	      itemNum = itemNum-1;
	      var idVar = "quesitemId"+ind;
          document.getElementById("delItemStrId").value = document.getElementById("delItemStrId").value+document.getElementById(idVar).value+",";
	      var rowNo = document.getElementById("itemTableTrId"+ind).sectionRowIndex;
	      dTable.deleteRow(rowNo);
	   }
	   
	   function checkItems(){
	      var ret = true;
	      var i=0;
	      //var mess1 = "<li>请填写答案选项!</li>";
	      //var mess3 = "<li>请选择正确的答案选项!</li>";
	      var mess1 = "请填写答案选项!";
	      var mess3 = "请选择正确的答案选项!";
	      var mess = "";
	      for(;i<itemNo;i++){
	          if(document.getElementsByName("quesitem["+i+"].quesitemcontent")!=null&&document.getElementsByName("quesitem["+i+"].quesitemcontent").length>0){
	             if(document.getElementsByName("quesitem["+i+"].quesitemcontent")[0].value==''){
	                mess += mess1;
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
	         //var messDiv = document.getElementById("displayMessDiv");
	         //messDiv.innerHTML = "";
	         if(i<itemNo){
	            document.getElementsByName("quesitem["+i+"].quesitemcontent")[0].focus();
	         }
	         //messDiv.innerHTML=messDiv.innerHTML+mess;
	         alert(mess);
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
	      } 
	      obj.style.visibility="hidden";
	   }
	   
	   function addSubques(){
	      var optObj = document.getElementById("subquesOptId");
	      <%if(quesid==null||quesid.equals(editquesid)){ %>
	          var ques_content = CKEDITOR.instances.questionId.getData();
	    	  document.getElementById("questionId").value = ques_content;
	      <%} %>
	      if(optObj.value=='1'||optObj.value=='2'||optObj.value=='3'){
	         if(!checkItems()){ return false; }
	      }
	      document.getElementById("editquesidId").value='';
	      optObj.value="1";
	      submitForm('editForm');
	   }
	   
	   function editSubques(subquesid){
	      var optObj = document.getElementById("subquesOptId");
	      if(optObj.value=='1'||optObj.value=='2'||optObj.value=='3'){
	         if(!checkItems()){ return false; }
	      }
	      document.getElementById("editquesidId").value=subquesid;
	      optObj.value="3";
	      submitForm('editForm');
	   }
	   
	   function delSubques(subquesid,questype){
	      if(!confirm('确定要删除该子题目吗？')){ return; }
	      document.getElementById("editquesidId").value=document.getElementById("quesidId").value;
	      var optObj = document.getElementById("subquesOptId");
	      if(optObj.value=='1'||optObj.value=='2'||optObj.value=='3'){
	         if(subquesid!=null&&subquesid!=''){
	            if(!checkItems()){ return false; }
	         }
	      }      
	      optObj.value="4";
	      if(subquesid!=null&&subquesid!=''){
	         document.getElementById("delquesidId").value=subquesid;
	         document.getElementById("delquestypeId").value=questype;
	      }else{
	         document.getElementById("vo2_question_id").value='';// 删除的是新增的子题目，还没有保存，因此不需要真正删除
	      }
	      if(subquesid==null||subquesid==''){
	          submitForm('editForm', false);
	      }else{
	          submitForm('editForm');
	      }
	   }
	   
	   function saveSubques(){
	      document.getElementById("editquesidId").value='<%=quesid %>';
	      var optObj = document.getElementById("subquesOptId");
	      if(optObj.value=='1'||optObj.value=='2'||optObj.value=='3'){
	          if(!checkItems()){ return false; }
	      }      
	      optObj.value="2";
	      submitForm('editForm');
	   }
	   
	   function submitQues(){
	      var checkRet = checkItems(); 
	      var optObj = document.getElementById("subquesOptId");
	      if(checkRet){
	    	 if(optObj.value=='0'){
	    	    var ques_content = CKEDITOR.instances.questionId.getData();
		    	document.getElementById("questionId").value = ques_content;
		    	var editor2_content = CKEDITOR.instances.editor2.getData();
		    	document.getElementById("editor2").value = editor2_content;
	    	 }
	    	 optObj.value="0";
	         submitForm('editForm');
	      }
	   }
	   
	</script>
	
  </body>
</html:html>
