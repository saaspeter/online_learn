<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.paper.vo.Paperquestype,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="exerciseForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="questypeUse" name="exerciseForm" property="questypeUse" type="java.lang.Integer"/>
<bean:define id="exerid" name="exerciseForm" property="vo.exerid" type="java.lang.Long"/>

  <%
    int showMeidaType = 0;
    String patternWarequesidStr = "";
    String removequestypeStr = "";
   %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>试卷预览</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script type="text/javascript">
       var itemFlagArr = new Array('A','B','C','D','E','F','G','H','I','J');
       var exerquesidVar = '';
       var questypeVar = '';
       var actionVar = 0;// 1为新增，2为更换题目
       
       function changeQues(exerquesid,questype){
          exerquesidVar = exerquesid;
          questypeVar = questype;
          actionVar = 2;
          var warequesIdStrVar = document.getElementById("warequesIdStrId"+questype).value; 
          
          var productbaseid = '<bean:write name="exerciseForm" property="vo.productbaseid"/>';
          var productname = '<bean:write name="exerciseForm" property="vo.productname"/>';
              productname = encodeURIComponent(productname);
          
	      newDiv("/wareques/selWareques.do?selectType=1&queryVO.productbaseid="+productbaseid
	             +"&queryVO.questype="+questype+"&selquesidStr="+warequesIdStrVar
	             +"&queryVO.productname="+productname,1,1,750,500);
	   }
	   
	   
	   function addQues(questype){
          questypeVar = questype;
          actionVar = 1;
          var warequesIdStrVar = document.getElementById("warequesIdStrId"+questype).value; 
          
          var productbaseid = '<bean:write name="exerciseForm" property="vo.productbaseid"/>';
          var productname = '<bean:write name="exerciseForm" property="vo.productname"/>';
              productname = encodeURIComponent(productname);
          var url = "/wareques/selWareques.do?selectType=2&queryVO.productbaseid="+productbaseid
	             +"&queryVO.questype="+questype+"&selquesidStr="+warequesIdStrVar
	             +"&queryVO.productname="+productname;

	      newDiv(url,1,1,750,450);
	   }
	   
       function selWarequesCB(ids){
          clearDiv();
                 
          if(ids!=null&&ids!=""){
             var url = '';
             var successinfo = '';
             if(actionVar==1){
                url = '/exercise/addExerQues.do?'
                      +'vo.exerid=<%=exerid %>'
                      +'&vo.questype='+questypeVar
                      +'&quesidStr='+ids;
                successinfo = "commonWeb.page.pubs.message.jsp.addMutiSuccess";
             }else if(actionVar==2){
                url = '/exercise/changeQues.do?vo.exerquesid='+exerquesidVar
                      +'&vo.exerid=<%=exerid %>'
                      +'&vo.questype='+questypeVar
                      +'&quesidStr='+ids;
                successinfo = "commonWeb.page.pubs.message.jsp.modifySuccess";
             }
             if(url!=''){
                doSingleRec("editForm",url,'-1',successinfo);
                // clear varibles 
                actionVar = 0;
                exerquesidVar = '';
                questypeVar = '';
             }
          }
       }
       
       function switchQuestype(questype){
           if(questype!=null&&questype!=''){
              var url = '/exercise/editExercise.do?'
                    +'vo.exerid=<%=exerid %>'
                    +'&questypeUse='+questype+'&backUrlEncode=';
              goUrl(url,'backUrlEncode');
           }
       }
       
       function delExerquestype(patternid){
           if(patternid!=null && patternid!=''){
              var rtnObj = toAjaxUrl("deleteExerquestype.do?vo.patternid="+patternid,false);
              if(rtnObj.result=="1"){ 
                 alert(getMessage("delete_success"));
                 var url = '/exercise/editExercise.do?vo.exerid=<%=exerid %>&backUrlEncode=';
                 goUrl(url,'backUrlEncode');
              }else if(rtnObj.result=="2"){
                 alert(rtnObj.info);
              }else{
                 alert(getMessage("systemError"));
              }
           }
       }
       
       function showSolve(id){
          var solvTr = document.getElementById(id);
          if(solvTr.style.display=='none'){
             solvTr.style.display='';
          }else {
             solvTr.style.display='none';
          }
       }
       
       // tipMess: confirm information, if it is -1, then don't have this message
	   function doSingleRec(formId,url,tipMess,successinfo,errinfo){
		   	var num = 0;
			var formObj = document.getElementById(formId);
			if(formObj==null||url==null||url.length==0){
			    return ;
			}
			if(typeof(errinfo)=="undefined"||errinfo==null||errinfo==""){
				errinfo = "deleteError_HasRef";
			}
			if(typeof(successinfo)=="undefined"||successinfo==null||successinfo==""){
				successinfo = "commonWeb.page.pubs.message.jsp.deleteSuccess";
			}
			
	        url = doContextUrl(url);
	        if(tipMess=='4'){
	        	tipMess = getMessage("confirmDeleteMsg");
	        }
	        if(tipMess=='-1' || confirm(tipMess)){
	        	var rtnObj = toAjaxUrl(url,false);
	            if(rtnObj.result=="1"){ // forward the success page,if success
	               formObj.action = formObj.action+"?bundle=BizKey&DisplayMessageKey="+successinfo+"&DisplayMessageArg0Key="
	                                +rtnObj.info;
	               formObj.submit();
	            }else if(rtnObj.info!=null&&rtnObj.info.length>0){
	               errinfo = rtnObj.info;
	               alert(getMessage("deleteError")+" -- "+getMessage(errinfo));
	            }else{
	               alert(getMessage("systemError"));
	            }
	        }
	        return;
		}
       
    </script>
  </head>
  
  <body>
	<div class="editPage">
	  <input type="hidden" id="editbackurlId" value='/exercise/editExercise.do?vo.exerid=<bean:write name="exerciseForm" property="vo.exerid"/>'/>
	  <center>
      <div style="width:90%;font-size:medium;text-align: left"> 
      <logic:notEmpty name="exerciseForm" property="vo.questypeList">
	       <logic:iterate id="quesTypeShowVO" name="exerciseForm" property="vo.questypeList" type="netTest.exercise.vo.Exerquestype"> 
	           <%if(quesTypeShowVO.getQuestype().equals(questypeUse)){ %>
	           <font style="color: red;">
	              <bean:writeSaas name="quesTypeShowVO" property="questype" consCode="Question.QuesType"/>   
	           </font> 
	           <img src="../styles/imgs/edit.png" title="修改题型" style="cursor:pointer;" onclick="newDiv('/exercise/editExerquestype.do?queryVO.patternid=<bean:write name="quesTypeShowVO" property="patternid"/>',1,1,600,300);return false;"/>&nbsp;
		       <img src="../styles/imgs/delete.png" title="删除题型" style="cursor:pointer;" onclick="delSingleRecAjax('/exercise/deleteExerquestype.do?vo.patternid=<bean:write name="quesTypeShowVO" property="patternid"/>&backUrl=','editbackurlId');return false;"/>             
	           <%}else{ %>
	           <a href="javascript:void(0)" onclick="switchQuestype('<%=quesTypeShowVO.getQuestype() %>');return false;">
	              <bean:writeSaas name="quesTypeShowVO" property="questype" consCode="Question.QuesType"/> 
	           </a>
	           <%}
	             removequestypeStr += quesTypeShowVO.getQuestype()+",";
	            %>
	           &nbsp;|&nbsp;
	       </logic:iterate>
	  </logic:notEmpty>
      </div>
      <div style="width:95%;font-size:medium;text-align: right;padding-right: 20px;margin-top: 10px;"> 
          <button type="button" class="button green fontBold" onclick="newDiv('/exercise/addExerquestype.do?vo.exerid=<%=exerid %>&removequestypeStr=<%=removequestypeStr %>',1,1,600,300);">新增题型</button>
      </div>
      <br>
      <% int questionNO = 1; %>
	  
      <!-- 循环显示题型开始 -->          
	  <logic:notEmpty name="exerciseForm" property="vo.questypeList">
	  <logic:iterate id="exerquesTypeVO" name="exerciseForm" property="vo.questypeList" type="netTest.exercise.vo.Exerquestype"> 
          <% 
             Integer questypeTemp = exerquesTypeVO.getQuestype();
             if(questypeTemp.equals(questypeUse)){ %>
	       <div>
	          <table width="90%">
	             <tr style="font-size: medium;font-weight: bold">     
	                <td>
	              	   <bean:writeSaas name="exerquesTypeVO" property="questype"  consCode="Question.QuesType"/>
	              	   &nbsp;&nbsp;
	                   &nbsp;&nbsp;
	                   <a href="javascript:void(0)" onclick="addQues('<%=exerquesTypeVO.getQuestype() %>');return false;">
	                                                     新增试题</a>
	                   <p>
	                   <bean:write name="exerquesTypeVO" property="questypenote"/>
	                </td>        
	             </tr>
	          </table>       
	          <logic:notEmpty name="exerquesTypeVO" property="quesList">
	          <logic:iterate id="exerquesVO" name="exerquesTypeVO" property="quesList" indexId="ind" type="netTest.exercise.vo.Exerques">
	          <%  
	              patternWarequesidStr = patternWarequesidStr+exerquesVO.getQuesid().toString()+",";
	           %>
	          <table style="width:95%;padding:3px;border:2px solid #eeeeee;">
	             <tr>
	                <td style="padding:5px;" bgcolor="#ECF5FF"><% out.print(questionNO+".&nbsp;");questionNO++; %>
	                    <bean:writeTK name="exerquesVO" property="question" cutStr="false" questypeProp="questype" filter="false" transfer="true" showIndentWay="2" inputName="kongge"/>
	                   (<a href="#" onclick="changeQues('<bean:write name="exerquesVO" property="exerquesid"/>','<bean:write name="exerquesVO" property="questype"/>');return false;">
	                      更换</a>&nbsp;|&nbsp;
	                    <a href="#" onclick="doSingleRec('editForm','/exercise/deleteExerques.do?vo.exerid=<%=exerid %>&vo.exerquesid=<bean:write name="exerquesVO" property="exerquesid"/>&vo.quesid=<bean:write name="exerquesVO" property="quesid"/>&vo.questype=<bean:write name="exerquesVO" property="questype"/>','4');return false;">
	                      删除</a>&nbsp;)
	                </td>
	            </tr>
	            <!-- 显示题目图片 -->
	            
	            <logic:equal name="exerquesVO" property="filetype" value="<%=QuestionConstant.FileType_pict.toString() %>">
		            <tr>
		               <td align="left" style="padding-left: 50px;">
		                   <img src="<%=WebConstant.FileContext %><bean:write name="exerquesVO" property="fileurl"/>" height="100">
		               </td>
		            </tr>
		            <% showMeidaType = 0; %>
		        </logic:equal>
	            <logic:equal name="exerquesVO" property="filetype" value="<%=QuestionConstant.FileType_voice.toString() %>">
	                <% showMeidaType = 20; %>
	            </logic:equal>
	            
	            <% if(showMeidaType!=0){ %>
	                <tr>
	                   <td align="left" id="container<%=ind %>" style="padding-left: 50px;">hid<script type="text/javascript">
							 var s1 = new SWFObject("../styles/player.swf","container<%=ind %>","328","<%=showMeidaType %>","9","#123456");
							 s1.addParam("allowfullscreen","false");
							 s1.addParam("allowscriptaccess","always");
							 s1.addParam('flashvars','file=<bean:write name="exerquesVO" property="fileurl"/>');
							 s1.addVariable("backcolor","FF0000");
							 s1.write("container<%=ind %>");
					      </script>
				       </td>
				    </tr>
	            <% showMeidaType=0; } %>
	            
	            <% if(QuestionConstant.QuesType_DanXuan.equals(exerquesTypeVO.getQuestype())||
	                  QuestionConstant.QuesType_DuoXuan.equals(exerquesTypeVO.getQuestype())){ %>
	                 <logic:iterate id="quesItemVO" name="exerquesVO" property="itemList" indexId="itemInd">
		             <tr>
		                <td>
		                   <div> 
		                       <span style="width:16px;">
		                       <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
		                          <img src="../styles/imgs/ico/check.gif" border="0"/>
		                       </logic:equal>
		                       </span>
		                       &nbsp;<script>document.write(itemFlagArr[<%=itemInd %>]+'.'+'&nbsp;&nbsp;')</script><bean:write name="quesItemVO" property="quesitemcontent"/>
		                       
		                   </div>
		                   <logic:equal name="quesItemVO" property="filetype" value="<%=QuestionConstant.FileType_pict.toString() %>">
				              <div style="background: #eeeeee;padding-left: 50px;"> 
				                  <img src="<%=WebConstant.FileContext %><bean:write name="quesItemVO" property="fileurl"/>" height="100">
				              </div>
				           </logic:equal>
		                </td>
		             </tr>
		             </logic:iterate>
	            <% } %>
	            
	            <logic:equal name="exerquesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_PanDuan.toString() %>" >
		             <tr>
	                    <td align="left">&nbsp;&nbsp;答案:&nbsp;&nbsp;
	                       <logic:equal name="exerquesVO" property="answersim" value="<%=QuestionConstant.IsRight_right.toString() %>">正确</logic:equal>
	                       <logic:equal name="exerquesVO" property="answersim" value="<%=QuestionConstant.IsRight_wrong.toString() %>">错误</logic:equal>
	                    </td>
	             	 </tr>
             	</logic:equal>
             	
             	<logic:equal name="exerquesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_TianKong.toString() %>" >
             	
             	</logic:equal>
                
                <logic:equal name="exerquesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_WenDa.toString() %>" >
             	     <tr>
		                <td>参考答案：
		                   <bean:write name="exerquesVO" property="answerVO.answertext"/>
		                </td>
		             </tr>
             	</logic:equal>
                        
			    <logic:equal name="exerquesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_WanXingTianKong.toString() %>" >
			        <tr>
			           <td>
			              <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题</font></div>
			              <div style="height:500px;overflow: auto">
			              <logic:present name="exerquesVO" property="subquesList">
			              <logic:iterate id="subquesVO" name="exerquesVO" property="subquesList" indexId="indques">
                              <div>
						      <table width="99%" border="1" cellpadding="1" cellspacing="0" style="margin:2px">
						         <tr style="background-color:#DCEAFC;" align="center">
						             <td width="16"><%=indques+1 %>.&nbsp;</td>
						             <td>
						                <bean:write name="subquesVO" property="question"/>&nbsp;&nbsp;
						             </td>
						         </tr>
					             <logic:iterate id="quesItemVO1" name="subquesVO" property="itemList" indexId="inditem">
					             <tr>
					                <td align="center" width="16"> 
					                   <logic:equal name="quesItemVO1" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
					                      <img src="../styles/imgs/ico/check.gif" border="0"/>
					                   </logic:equal>
					                </td>
					                <td>
					                   &nbsp;<bean:write name="quesItemVO1" property="quesitemcontent"/>
					                </td>
					             </tr>
					             </logic:iterate>
					          </table>
						      </div>
			              </logic:iterate>
			              </logic:present>
			              </div>
			            </td>
			        </tr>
			    </logic:equal>
			    
			    <logic:equal name="exerquesTypeVO" property="questype" value="<%=QuestionConstant.QuesType_YueDuLiJie.toString() %>" >
			        <tr>
			          <td>
			              <logic:iterate id="subquesVO1" name="exerquesVO" property="subquesList" indexId="indques1" >
					      <div>
						      <table width="99%" border="1" cellpadding="1" cellspacing="0" style="margin:2px">
						         <tr style="background-color:#DCEAFC">
						             <td width="16"><%=indques1+1 %>.&nbsp;</td>
						             <td>
						                 <bean:write name="subquesVO1" property="question"/>&nbsp;&nbsp;
						             </td>
						         </tr>
					             <logic:iterate id="quesItemVO2" name="subquesVO1" property="itemList" >
					             <tr>
					                <td align="center" width="16"> 
					                   <logic:equal name="quesItemVO2" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>">
					                      <img src="../styles/imgs/ico/check.gif" border="0"/>
					                   </logic:equal>
					                </td>
					                <td>
					                   &nbsp;<bean:write name="quesItemVO2" property="quesitemcontent"/>
					                </td>
					             </tr>
					             </logic:iterate>
					          </table>
						  </div>
						  </logic:iterate>
			          </td>
			       </tr>
			    </logic:equal>
			    
			       <%if(exerquesVO.getAnswerVO()!=null&&exerquesVO.getAnswerVO().getSolvedesc()!=null){ %>
			       <tr>
			          <td style="padding-left:20px;margin-top:10px;background-color:#eeeeee;text-align: left;font-weight: bold">
			              <a href="javascript:showSolve('solveTr<%=exerquesVO.getQuesid() %>');">查看解题思路&gt;&gt;&gt;&gt;</a></td>
			       </tr>
			       <tr id="solveTr<%=exerquesVO.getQuesid() %>" style="display:none;">
			          <td style=""><%=exerquesVO.getAnswerVO().getSolvedesc() %></td>
			       </tr>
			       <%} %>
			    
		      </table>
		      <br>
              
	          </logic:iterate>
	          </logic:notEmpty>
	          <br>    
	          <input type='hidden' id='warequesIdStrId<%=questypeTemp.toString() %>' value='<%=patternWarequesidStr %>'/>
	       </div>
	       <%} %>
	     </logic:iterate>  
	  </logic:notEmpty>          
						
	<!-- 循环显示题型结束 -->
	</center>

	</div>
  </body>
</html:html>
