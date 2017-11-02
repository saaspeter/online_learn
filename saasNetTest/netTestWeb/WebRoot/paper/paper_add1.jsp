<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.paper.vo.Paperpatternratio,commonTool.constant.CommonConstant,netTest.paper.constant.PaperConstant,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="paperForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="paperForm" property="editType" type="java.lang.Integer"/>
<bean:define id="sessionProductname" name="paperForm" property="sessionProductname" type="java.lang.String"/>
<bean:define id="sessionProductid" name="paperForm" property="sessionProductid" type="java.lang.Long"/>
<bean:define id="quetyporiList" name="paperForm" property="quetyporiList" type="java.util.List"/>
<bean:size id="wareNumInt" name="paperForm" property="wareList"/>
<bean:size id="contcateNumInt" name="paperForm" property="contcateList"/>

<% if(sessionProductid==null||sessionProductid.longValue()==-1l){sessionProductname="";} %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>生成试卷练习</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script language=JavaScript src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript" src="../styles/script/utiltool.js"></script>
    <script type="text/javascript">
    
       var questypeNum = 0;
	   var contcateNum = <%=contcateNumInt %>;
       var concatTabVar = '';
       var questypeVar = '';
    
       function selectWare(){
          var prodidStr = document.getElementById("productidId").value;
          var prodname = document.getElementById("productnameId").value;
          if(prodidStr==null||prodidStr==""){
             alert('请选择课程!');
             return;
          }
	      newDiv("/wareques/listWareSelect.do?selectType=2&productbaseid="+prodidStr+"&queryVO.productname="+prodname,1,1,670,300);
	   }
	   
       function selWareCB(ids,names,productbaseid){
          if(ids!=null&&ids!=""){
             addMultiSelOpt(ids,names,"selectWareId","wareIdStrId","warenameStrId");
          }
          clearDiv();
       }
       
       function selContcate(tableId,questypeVal){
          var prodidStr = document.getElementById("productidId").value;
          if(prodidStr==null||prodidStr==""){
             alert('请选择课程!');
             return;
          }
          concatTabVar = tableId;
          questypeVar = questypeVal;
          var url = "#/<%=CommonConstant.WebContext_Education %>/prodcont/contentcate_main.jsp?productbaseid="+prodidStr+"&listType=2";
	      newDiv(url,1,1,670,300);
	   }
	   
       function selContcateCB(ids,names){
          if(ids!=null&&ids!=""){
             var idArr = ids.split(",");
             var nameArr = names.split(";");
             var selectedIds = ','+getSelectedIds('3',concatTabVar)+',';
             for(var i=0;i<idArr.length;i++){
                if(idArr[i]!=''&&selectedIds.indexOf(','+idArr[i]+",")==-1){
                   addContcateTr(nameArr[i],idArr[i],concatTabVar,questypeVar);
                }
             }
          }
          clearDiv();
       }
       
       function getSelectedIds(type,prefix0){
          var num = null;
          var prefix1 = null;
          var ids = '';
          if(type=='3'){
             num = contcateNum;
             prefix1 = 'concatEleId';
             if(typeof(prefix0)!="undefined"&&prefix0!=null){
                prefix1 = prefix1+prefix0;
             }
          }
          if(num!=null){
             for(var i=0;i<num+1;i++){
                if(typeof(document.getElementById(prefix1+i))!="undefined"&&document.getElementById(prefix1+i)!=null){
                   ids = ids+document.getElementById(prefix1+i).value+",";
                }
             }
          }
          return ids;
       }
       
       function changeDiffRadio(type){
          if(type=='1'){
             document.getElementById("diffDivId").style.display="";
          }else if(type=='2'){
             document.getElementById("diffDivId").style.display="none";
          }
       }
       
       function checkQuestype(obj){
          if(obj!=null){
             if(obj.checked==true){
                var label = document.getElementById(obj.id+"Name").value;
                addQuestype(label,obj.value);
             }else{
                removeRow('quetypTabId','quetypTabIdTrId'+obj.value);
             }
          }
       }
       
       function selUserProdCB(ids,names){
          var prodidStr_old = document.getElementById("productidId").value;
          selUserProdCB_base(ids,names);
          var prodidStr_new = document.getElementById("productidId").value;
          if(prodidStr_new!=prodidStr_old){
	          // delete all ware selections
              rmAllMultiOpt('selectWareId','wareIdStrId','warenameStrId');
	          // delete all contcate selections
	          for(var k=0;k<contcateNum;k++){
	              removeRow('3',k);
	          }
          }
       }
       
       function selectContcate(){
          var prodidId = document.getElementById("productidId").value;
          if(prodidId==null||prodidId==""){
             alert('请先选择课程!');
             return;
          }
          var url = "#/<%=CommonConstant.WebContext_Education %>/prodcont/contentcate_main.jsp?productbaseid="+prodidId+"&listType=2&selectType=1";
	      newDiv(url,1,1,670,300);
       }
       
       function removeContcate(){
          document.getElementById("contentcateidId").value = '';
          document.getElementById("contentcatenameId").value = '';
       }
       
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/paper/genePaper.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="paperForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="paperForm" property="backUrlEncode"/>">
	 <input type="hidden" name="vo.paperid" value="<bean:write name="paperForm" property="vo.paperid"/>" />
	 
	  <div id="fieldsTitleDiv">
	      <span id="titleSpan1">第一步:设置基本属性</span>
	      <span id="titleSpan2">第二步:设置题型参数</span>
	  </div>
	  
	  <div id="step1Div">
     
	      <div>
	           <table class="formTable">
	               <tr>
	                    <td align="right"><font color="red">*</font>名称:</td>
	                    <td colspan="3" style="text-align: left;">
	                        <input type="text" id="papernameId" name="vo.papername" value="<bean:write name="paperForm" property="vo.papername"/>" usage="notempty,max-length:90" fie="试卷名称" style="width:90%"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td align="right"><font color="red">*</font>答题时间:</td>
	                    <td style="text-align: left;">
	                        <input type="text" id="papertimeId" name="vo.papertime" value="<bean:write name="paperForm" property="vo.papertime"/>" usage="notempty,int-range:1:300" fie="答题时间" style="width:200px"/>分钟
	                    </td>
	                    <td align="right"><font color="red">*</font>所属产品:</td>
	                    <td style="text-align: left;">
	                        <input type="text" id="productnameId" name="vo.productname" value="<%=sessionProductname %>" readonly="readonly" onclick="selectProd(this)" usage="notempty" fie="所属产品" style="width:250px"/> 
	  			            <input type="hidden" id="productidId" name="vo.productbaseid" value="<%=(sessionProductid==null)?"":sessionProductid.toString() %>"/>
				        </td>
	                </tr>
	                         	                
	                <tr>
	                  <td align="right" valign="top"><font color="red">*</font>选择题型:</td>
	                  <td colspan="3">
	                      <logic:notEmpty name="quetyporiList" >
	                      <table>
				          <logic:iterate id="labelQuestypeVO" name="quetyporiList" indexId="ind">
				          <% if(((ind+1)%4)==1){ %>
				          <tr>
				          <% } %>
				              <td>
				                 <html:checkbox tagName="keys" styleId='<%="questypeId"+ind %>' nameVal="labelQuestypeVO" propertyVal="value" onclick="checkQuestype(this)">
				                    <label for="questypeId<%=ind %>">
				                       <bean:write name="labelQuestypeVO" property="label"/>
				                       <input type="hidden" id='<%="questypeId"+ind+"Name" %>' value="<bean:write name="labelQuestypeVO" property="label"/>"/>
				                    </label>
				                 </html:checkbox>&nbsp;&nbsp;&nbsp;
				              </td>
				          <% if(((ind+1)%4)==0||((ind+1)==quetyporiList.size())){ %>
				          </tr>
				          <%} %>
				          </logic:iterate>
				          </table>
				          </logic:notEmpty>
	                  </td>
	                </tr>
	                
	                <tr>
	                    <td align="right"><font color="red">*</font>来源题库:</td>
	                    <td colspan="3" align="left">
	                        <input type="hidden" id="wareIdStrId" name="vo.wareidstr" value="<bean:write name="paperForm" property="vo.wareidstr"/>" >
	                        <input type="hidden" id="warenameStrId" name="vo.warenamestr" value="<bean:write name="paperForm" property="vo.warenamestr"/>" >
	                        <select id="selectWareId" name="" multiple="multiple" size="3" style="width:67%;">
					            <logic:present name="paperForm" property="wareList">
					                <logic:iterate id="wareVO" name="paperForm" property="wareList">
					                     <option value="<bean:write name="wareVO" property="elementid"/>"><bean:write name="wareVO" property="elementname"/></option>
					                </logic:iterate>
					            </logic:present> 
					        </select>
				       		<img src="../styles/imgs/ico/search.gif" alt="选择题库" style="cursor: pointer;width:25px;" onclick="selectWare()">
				       		<img src="../styles/imgs/ico/reset.gif" alt="删除题库" style="cursor: pointer;width:25px;" onclick="rmMultiSelOpt('selectWareId','wareIdStrId','warenameStrId');" title="选中题库，点击这里可以移除题库">
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td>试卷说明:</td>
	                    <td colspan="3" align="left">
	                        <textarea id="paperdescId" name="vo.paperdesc" rows="" cols="80%" usage="max-length:250" fie="试卷说明"><bean:write name="paperForm" property="vo.paperdesc"/></textarea>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <td>生成方式:</td>
	                    <td colspan="3" align="left">
	                        <label for="createTypeId">
	                            <input type="checkbox" id="createTypeId" name="createtype" value="1" onchange="autoCreateCheck()">系统根据题型参数, 自动选题生成试卷
	                        </label>
	                    </td>
	                </tr>
	           </table>
	      </div>
	     
          <div style="width:30px;float:right">&nbsp;</div>
      
      </div>

      <div id="step2Div" style="width:98%;text-align: center;">
      
          <div style="padding-top:2px;margin-top:20px;background-color:#DCEAFC;text-align:center">
              <font style="font-weight: bold;">题型设置</font>
          </div>
          <div>
          	  <table id="quetypTabId" style="width:100%">
	          <tbody>
                 <logic:iterate id="questypeVO" name="paperForm" property="questypeList" type="netTest.paper.vo.Paperquestype" indexId="ind">
                 <tr id="quetypTabIdTrId<%=questypeVO.getQuestype() %>">
                    <td>
                        <div>
                            <table align="center" cellpadding="0" cellspacing="0" class="formTable">
                                <tr>
				                    <td align="center">
				                        <input name="questype[<%=ind%>].patternid" type="hidden" value="<%=questypeVO.getPatternid() %>">
				                        <input name="questype[<%=ind%>].paperid" type="hidden" value="<%=questypeVO.getPaperid() %>">
				                        <input name="questype[<%=ind%>].shopid" type="hidden" value="<%=questypeVO.getShopid() %>">
				                        <input name="questype[<%=ind%>].questype" type="hidden" value="<%=questypeVO.getQuestype() %>">
				                        <input name="questype[<%=ind%>].questypename" type="hidden" value="<%=questypeVO.getQuestypename() %>">
				                        <font style="font-weight: bold;"><%=questypeVO.getQuestypename() %></font>：
				                    </td>
									<td align="right"><font color="red">*</font>
									    <%if(QuestionConstant.QuesType_TianKong.equals(questypeVO.getQuestype())){ %>
									    <bean:message key="page.paperadd.tip.tiankong.score"/>
									    <%}else { %>
									    <bean:message key="page.paperadd.tip.ques.score"/> 
									    <%} %>
									    <%if(QuestionConstant.QuesType_WanXingTianKong.equals(questypeVO.getQuestype()) ||
									    	 QuestionConstant.QuesType_YueDuLiJie.equals(questypeVO.getQuestype()) ){ %>
									    <img src="../styles/imgs/help.gif" title='<bean:message key="page.paperadd.tip.complexques.score"/>'/>
									    <%}else if(QuestionConstant.QuesType_TianKong.equals(questypeVO.getQuestype())){ %>
									    <img src="../styles/imgs/help.gif" title='<bean:message key="page.paperadd.tip.tiankong.score"/>'/>
									    <%} %>
									</td>
			                    	<td align="left"><input name="questype[<%=ind%>].patternscore" type="text" value="<bean:write name="questypeVO" property="patternscore"/>" size="4" usage="notempty,num+" fie="每题分数">分</td>
			                    	<td align="right"><font color="red">*</font><bean:message key="page.paperadd.tip.ques.number"/>:
			                    	    <%if(QuestionConstant.QuesType_WanXingTianKong.equals(questypeVO.getQuestype()) ||
									    	 QuestionConstant.QuesType_YueDuLiJie.equals(questypeVO.getQuestype()) ){ %>
									    <img src="../styles/imgs/help.gif" title='<bean:message key="page.paperadd.tip.complexques.number"/>'/> 
									    <%}else if(QuestionConstant.QuesType_TianKong.equals(questypeVO.getQuestype())){  %>
									    <img src="../styles/imgs/help.gif" title='<bean:message key="page.paperadd.tip.tiankong.number"/>'/>
									    <%} %>
			                    	</td>
			                    	<td align="left"><input name="questype[<%=ind%>].patternquesnum" type="text" value="" size="4" usage="notempty,int-range:1:300" fie="题目数量">个&nbsp;</td>
									<td align="right"><bean:message key="page.paperadd.tip.ques.order"/>:</td>
			                    	<td align="left"><input name="questype[<%=ind%>].disorder" type="text" value="<bean:write name="questypeVO" property="disorder"/>" size="3" usage="int+" fie="顺序"></td>
		                        </tr>
                            </table>
                        </div>
                        <div>                                    
                            <table id="concatTabId<bean:write name="questypeVO" property="questype"/>" align="center" cellpadding="0" cellspacing="0" class="formTable">
					           <tbody>
				                  <logic:iterate id="contcateVO" name="paperForm" property="contcateList" indexId="indx">
				                  <tr id="concatTabId<bean:write name="questypeVO" property="questype"/>TrId<%=indx%>">
				                     <td align="right" width="25%">
				                         <input type="hidden" id="contCateElemId<%=indx%>" name="contcate[<%=indx%>].elementid" value="<bean:write name="contcateVO" property="elementid"/>" >
				                         <input type="hidden" name="contcate[<%=indx%>].elementtype" value="<%=Paperpatternratio.Elementtype_Cont %>" >
				                         <input type='hidden' name='contcate["+contcateNum+"].questype' value='<bean:write name="questypeVO" property="questype"/>' >
				                     </td>
				                     <td colspan="3">
				                         <bean:write name="contcateVO" property="elementname"/>:
				                         <input type="hidden" name="contcate[<%=indx%>].elementname" value="<bean:write name="contcateVO" property="elementname"/>">
				                     </td>
				                     <td>
				                         <input type="text" name="contcate[<%=indx%>].elementnum" value="<bean:write name="contcateVO" property="elementratio"/>" size="4">个
				                     </td>
				                     <td>
				                         <a href="#" onclick="removeRow('3','<%=indx%>');return false;">删除</a>
				                     </td>
				                  </tr>
				                  </logic:iterate>
				               </tbody>
				            </table>
                        </div>
                        <div style="text-align:center;margin:10px"><a href="javascript:void(0)" onclick="selectContcate();return false;">选择每章节所需题目数量</a></div>
                     </td>
                 </tr>
                 </logic:iterate>
              </tbody>
              </table>
          </div>
                
          <div id="diffDivId">
          <div style="padding-top:2px;margin-top:20px;background-color:#DCEAFC;text-align:center">
              <font style="font-weight: bold;">难度比例设置</font>
          </div>
          <div>
          	  <table align="center" cellpadding="0" cellspacing="0" class="formTable">
	          <tbody>
                 <logic:iterate id="diffVO" name="paperForm" property="diffList" indexId="ind">
                 <tr id="diffTableTrId<%=ind%>">
                     <td align="right" width="40%">
                         <bean:write name="diffVO" property="elementname"/>:
                         <input type="hidden" name="diff[<%=ind%>].elementname" value="<bean:write name="diffVO" property="elementname"/>">
                         <input type="hidden" name="diff[<%=ind%>].elementid" value="<bean:write name="diffVO" property="elementid"/>" >
                         <input type="hidden" name="diff[<%=ind%>].elementtype" value="<%=Paperpatternratio.Elementtype_Diff %>" >
                     </td>
                     <td>
                         <input type="text" name="diff[<%=ind%>].elementratio" value="<bean:write name="diffVO" property="elementratio"/>" >%
                     </td>
                 </tr>
                 </logic:iterate>
              </tbody>
              </table>
          </div>
          </div>
      </div>
      
      <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
          	  			
	  <div id="functionBarButtomDiv">
		 <ul id="bottomDiv1" style="display:none">
		    <li><button type="button" onclick="goUrl('/paper/listPaper.do?productbaseid=<%=sessionProductid %>');return false;">取消返回</button></li>
		    <li>&nbsp;&nbsp;</li>
		    <li>
		        <button type="button" onclick="showSteps(2);return false;">下一步:设置题型参数</button>
		    </li>
		 </ul>
		 <ul id="bottomDiv2">
		    <li><button type="button" onclick="showSteps(1);return false;">返回上一步</button></li>
		    <li>&nbsp;&nbsp;</li>
		    <li><button type="button" onclick="submitForm('editForm');return false;">确定生成</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv">&nbsp;</div>
	</html:form>
	</div>
	<script type="text/javascript">
	 <!--
	   var dQuestypeTable = document.getElementById('quetypTabId'); // 问题类型的table
	   var tQuestypebody = dQuestypeTable.tBodies[0]; 
	   
	   function addQuestype(label,value){
  	         var outnewTr = tQuestypebody.insertRow(-1);
  	         outnewTr.id="quetypTabIdTrId"+value;
             var outnewTd1 = outnewTr.insertCell(0); 
             outnewTd1.innerHTML = "<div>"+
                                "<table id='quetypTabIdTrId"+value+"TabId' align='center' cellpadding='0' cellspacing='0' class='formTable'>"+
                                    "<tbody></tbody>"+
                                "</table>"+
                                "</div>"+
                                "<div>"+
                                "<div style='text-align:center;margin:10px'><a href='#' onclick=\"selContcate("+"'quetypTabIdTrId"+value+"TabId','"+value+"');return false;\">选择每章节所需题目数量</a></div>"
                                ;
       
             var newTr = document.getElementById("quetypTabIdTrId"+value+"TabId").tBodies[0].insertRow(-1);  
             var newTd1 = newTr.insertCell(0); 
             newTd1.align = "center";
             //newTd1.width = "25%";
             newTd1.innerHTML = "<input name='questype["+questypeNum+"].questype' type='hidden' value='"+value+"'> "+
                                "<input name='questype["+questypeNum+"].questypename' type='hidden' value='"+label+"'> "+
                                "<font style='font-weight: bold;'>"+label+"</font>:";
             
             var tip_ques_score = '<bean:message key="page.paperadd.tip.ques.score"/>';
             if(value=='<%=QuestionConstant.QuesType_TianKong.toString() %>'){
            	 tip_ques_score = '<bean:message key="page.paperadd.tip.tiankong.score"/>';
             }
             
             var helpimg_score = "";
             if(value=='<%=QuestionConstant.QuesType_WanXingTianKong.toString() %>'
                || value=='<%=QuestionConstant.QuesType_YueDuLiJie.toString() %>'){
            	 helpimg_score = '<img src="../styles/imgs/help.gif" title="'+'<bean:message key="page.paperadd.tip.complexques.score"/>'+'"/>';
             }else if(value=='<%=QuestionConstant.QuesType_TianKong.toString() %>'){
            	 helpimg_score = '<img src="../styles/imgs/help.gif" title="'+'<bean:message key="page.paperadd.tip.tiankong.score"/>'+'"/>';
             }
                                
             var newTd2 = newTr.insertCell(1);
             newTd2.align = "right";
             newTd2.innerHTML = "<font color='red'>*</font>"+tip_ques_score+":"+helpimg_score;
             
             var newTd3 = newTr.insertCell(2);
             newTd3.align = "left";
             newTd3.innerHTML = "<input name='questype["+questypeNum+"].quesscore' type='text' value='' size='4' usage='notempty,num+' fie='"+tip_ques_score+"'>分";
             
             var tip_ques_number = '<bean:message key="page.paperadd.tip.ques.number"/>';
             var helpimg_number = "";
             if(value=='<%=QuestionConstant.QuesType_WanXingTianKong.toString() %>'
                 || value=='<%=QuestionConstant.QuesType_YueDuLiJie.toString() %>'){
            	 helpimg_number = '<img src="../styles/imgs/help.gif" title="'+'<bean:message key="page.paperadd.tip.complexques.number"/>'+'"/>';
             }else if(value=='<%=QuestionConstant.QuesType_TianKong.toString() %>'){
            	 helpimg_number = '<img src="../styles/imgs/help.gif" title="'+'<bean:message key="page.paperadd.tip.tiankong.number"/>'+'"/>';
             }
             
             var newTd4 = newTr.insertCell(3);
             newTd4.align = "right";
             newTd4.innerHTML = "<font color='red'>*</font>"+tip_ques_number+":"+helpimg_number;
             
             var newTd5 = newTr.insertCell(4);
             newTd5.align = "left";
             newTd5.innerHTML = "<input name='questype["+questypeNum+"].patternquesnum' type='text' value='' size='4' usage='notempty,int-range:1:300' fie='"+tip_ques_number+"'>个&nbsp;";
             
             var tip_ques_order = '<bean:message key="page.paperadd.tip.ques.order"/>'; 
             
             var newTd6 = newTr.insertCell(5);
             newTd6.align = "right";
             newTd6.innerHTML = tip_ques_order+":";
             
             var newTd7 = newTr.insertCell(6);
             newTd7.align = "left";
             newTd7.innerHTML = "<input name='questype["+questypeNum+"].disorder' type='text' value='' size='3' usage='int+' fie='"+tip_ques_order+"'>";

  	         questypeNum = questypeNum+1;  	         
	   }  	     
	   	   
	   function removeRow(tableId,trId){
	       var ddTable = document.getElementById(tableId).tBodies[0];
	       var rowNo = document.getElementById(trId).sectionRowIndex;
	       ddTable.deleteRow(rowNo);
	   }
	   
	   function addContcateTr(label,value,tableId,questypeVal){
             var newTr = document.getElementById(tableId).tBodies[0].insertRow(-1);
             newTr.id = "contCateTableTrId"+contcateNum;  
             var newTd1 = newTr.insertCell(0); 
             newTd1.align = "right";
             //newTd1.width = "25%";
             newTd1.innerHTML = "<input type='hidden' id='concatEleId"+tableId+contcateNum+"' name='contcate["+contcateNum+"].elementid' value='"+value+"' >"+
                                "<input type='hidden' name='contcate["+contcateNum+"].elementtype' value='<%=Paperpatternratio.Elementtype_Cont %>' >"+
                                "<input type='hidden' name='contcate["+contcateNum+"].questype' value='"+questypeVal+"' >";
  
             var newTd2 = newTr.insertCell(1);
             newTd2.colSpan="3";
             newTd2.align = "right";
             newTd2.innerHTML = label+":"+
                                "<input type='hidden' name='contcate["+contcateNum+"].elementname' value='"+label+"'>";
             
             var newTd3 = newTr.insertCell(2);
             newTd3.innerHTML = "<input type='text' name='contcate["+contcateNum+"].elementnum' value='' size='4' usage='int+' fie='章节题目'>个";
             
             var newTd4 = newTr.insertCell(3);
             newTd4.colSpan="2";
             newTd4.align = "center";
             var trIdVar = 'contCateTableTrId'+contcateNum;
             newTd4.innerHTML = "<a href='#' onclick='removeRow(\""+tableId+"\",\""+trIdVar+"\");return false;'>删除</a>";

  	         contcateNum = contcateNum+1;
	   }
	 
       window.onload=function(){
           showSteps(1);
		   changeDiffRadio('<bean:write name="paperForm" property="diffControl"/>');      
	   }
	   
	   function showSteps(step){
	       if(step==1){
	          clearMessagePage();
	          document.getElementById("step1Div").style.display="block";
	          document.getElementById("step2Div").style.display="none";
	          document.getElementById("titleSpan1").style.display="block";
	          document.getElementById("titleSpan2").style.display="none";
	          document.getElementById("bottomDiv1").style.display="block";
	          document.getElementById("bottomDiv2").style.display="none";
	          autoCreateCheck();
	       }else if(step==2){
	          if(checkStep1()){
	             clearMessagePage();
	             document.getElementById("step1Div").style.display="none";
	             document.getElementById("step2Div").style.display="block";
	             document.getElementById("titleSpan1").style.display="none";
	             document.getElementById("titleSpan2").style.display="block";
	             document.getElementById("bottomDiv1").style.display="none";
	             document.getElementById("bottomDiv2").style.display="block";
	          }
	       }
	   }
	   
	   function checkStep1(){
	       clearMessagePage();
	       var result = true;
	       var errNum = 0;
	       var errmsg = "";
	       var papernameObj = document.getElementById("papernameId");
	       var papertimeObj = document.getElementById("papertimeId");
	       var productnameObj = document.getElementById("productnameId");
	       var paperdescObj = document.getElementById("paperdescId");
	       var rtnArr1 = checkElement(papernameObj, 0);
	       if(!rtnArr1[1]){
			  errNum++;
			  errmsg = rtnArr1[2] + "<br>";
		   }
	       var rtnArr2 = checkElement(papertimeObj, 0);
	       if(!rtnArr2[1]){
			  errNum++;
			  errmsg = errmsg + rtnArr2[2] + "<br>";
		   }
	       var rtnArr3 = checkElement(productnameObj, 0);
	       if(!rtnArr3[1]){
			  errNum++;
			  errmsg = errmsg + rtnArr3[2] + "<br>";
		   }
	       var rtnArr4 = checkElement(paperdescObj, 0);
	       if(!rtnArr4[1]){
			  errNum++;
			  errmsg = errmsg + rtnArr4[2] + "<br>";
		   }
	       
	       var questypeVal = getCheckedValue("keys");
	       if(questypeVal==null||questypeVal==''){
	          errmsg = errmsg + "请选择要包含的题型"+"<br>";
	          errNum++;
	       }
	       var wareObj = getSelectedOptions("selectWareId");
	       if(wareObj==null||wareObj.ids==null||wareObj.ids==''){
	          errmsg = errmsg + "请选择生成来源题库"+"<br>";
	          errNum++;
	       }
	       if(errNum>0){
	          result = false;
	          showMessagePage(errmsg);
	       }
	       return result;
	   }
	   
	   function autoCreateCheck(){
		   
	   }
	   
	   
     //-->
  </script>
  </body>
</html:html>
