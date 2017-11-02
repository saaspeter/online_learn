<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.vo.Questionitem,netTest.wareques.constant.QuestionConstant,netTest.util.QueshowUtil,netTest.exam.constant.UseranswerConstant,netTest.exam.constant.TestuserConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="exerExamForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="quesVO" name="exerExamForm" property="quesVO" type="netTest.exercise.vo.Exerques"/>
<bean:define id="useranswerVO" name="exerExamForm" property="useranswerVO" type="netTest.exercise.vo.Userexeranswer"/>
<bean:define id="quesnum" name="exerExamForm" property="quesnum" type="java.lang.Integer"/>
<bean:define id="quesIndex" name="exerExamForm" property="quesIndex" type="java.lang.Integer"/>
<bean:define id="testway" name="exerExamForm" property="testway" type="java.lang.Integer"/>
<bean:define id="exeruserStatus" name="exerExamForm" property="exeruserStatus" type="java.lang.Short"/>
  <%
    int showMeidaType = 0;   
    int questypeUse = quesVO.getQuestype();
    netTest.exercise.vo.Userexeranswer subanswerVO = null;
    boolean isResult = false;
    String disableStr = "";
    if(UseranswerConstant.ExamineStatus_DidJudge.equals(useranswerVO.getExaminestatus())){
       isResult = true;
       disableStr = "disabled=\"true\"";
    }
    String quesshift_action = "examQuesShift.do";
    String checkques_action = "checkQues.do";
    if(testway==1){
       quesshift_action = "preExamQuesShift.do";
       checkques_action = "preCheckQues.do";
    }
   %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>正在测试</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/exam.js"></script>
    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script type="text/javascript">
    
       var closewinway = 1;
       var questypeuseVar = <%=questypeUse %>;
    
       <% String[] itemFlagArr = new String[]{"A","B","C","D","E","F","G","H","I","J"}; 
          java.util.Map itemFlagMap = new java.util.HashMap();
       %>
       
       function switchQues(direction){
          var oForm = document.getElementById("examFormId");
          var result = checkExam(oForm,questypeuseVar,-1);
          if(result){
             closewinway = 0;
             oForm.action = '<%=quesshift_action %>';
             document.getElementById("directionId").value = direction;
             oForm.submit();
          }
       }
       
       function submitAnswer(){      
          var oForm = document.getElementById("examFormId");
          oForm.action = '<%=checkques_action %>';
          oForm.submit();
       }      
       
       function submitExercise(){      
          var oForm = document.getElementById("examFormId");
          oForm.action = 'submitExercise.do';
          oForm.submit();
       } 
       
       window.onbeforeunload = function(e){ 
	                         if(closewinway==1){
        					    return '确定直接退出?';  
        					 }
						  };
       
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="examFormId" action='<%="/exercise/"+quesshift_action %>' method="post">
      <input type="hidden" name="exerid" value='<bean:write name="exerExamForm" property="exerid"/>'/>
      <input type="hidden" name="quesIndex" value='<%=quesIndex %>'/>
      <input type="hidden" name="quesnum" value='<%=quesnum %>'/>
	  <input type="hidden" id="directionId" name="direction" value='<bean:write name="exerExamForm" property="direction"/>'/>
	  <input type="hidden" name="exeruserStatus" value='<%=exeruserStatus %>'/>
	  
	  <center>
	  <div style="margin:5px;"></div>
	  <div style="width:90%;font-size:large;text-align: center;margin:5px"><bean:write name="exerExamForm" property="exername"/></div>
	  <div style="width:90%;font-size:medium;text-align: left;margin:10px;"> 
            
      <% int questionNO = 1;
       %>
	  
      <!-- 循环显示题型开始 -->          

         <div style="font-size: medium;font-weight: bold">
              <bean:writeSaas name="exerExamForm" property="typeUseVO.questype" consCode="Question.QuesType"/>
              &nbsp;&nbsp;&nbsp;<bean:write name="exerExamForm" property="typeUseVO.questypenote"/>
         </div>      
         <div>
          <table style="width:100%;padding:3px">
             <tr>
                <td style="padding:5px;" bgcolor="#ECF5FF">
                   <%if(QuestionConstant.QuesType_TianKong.equals(questypeUse)){ %>
                      <%if(isResult){ %>
                        <bean:writeTK name="quesVO" property="question" cutStr="false" questypeProp="questype" filter="false" transfer="true" showIndentWay="2" inputName="kongge"/>
                      <%}else{ %>
                        <bean:writeTK name="quesVO" property="question" inputName="useranswerVO.answerArr" filter="false" transfer="true" showIndentWay="3" questypeProp="questype" answerTK="<%=useranswerVO.getAnswer() %>"/>
                   <%}}else if(QuestionConstant.QuesType_WanXingTianKong.equals(questypeUse)){ %>
                   <bean:writeTK name="quesVO" property="question" filter="false" transfer="true" showIndentWay="2" questypeProp="questype"/>
                   <%}else{ %>
                   <bean:write name="quesVO" property="question" />
                   <%} %>
                   <input type="hidden" name='useranswerVO.quesid' value='<bean:write name="quesVO" property="quesid"/>'/>
                   
                </td>
             </tr>
            <!-- 显示题目图片 -->
            
            <%if(QuestionConstant.FileType_pict.equals(quesVO.getFiletype())){ %>
	            <tr>
	               <td align="left" style="padding-left: 50px;">
	                   <img src="<%=WebConstant.FileContext+quesVO.getFileurl() %>" height="100">
	               </td>
	            </tr>
	        <% showMeidaType = 0;
	         }else if(QuestionConstant.FileType_voice.equals(quesVO.getFiletype())){
	            showMeidaType = 20;
	         }
	         %>
            
            <% if(showMeidaType!=0){ %>
                <tr>
                   <td align="left" id="containerSWF" style="padding-left: 50px;">
                       <script type="text/javascript">
						 var s1 = new SWFObject("../styles/player.swf","containerSWF","328","<%=showMeidaType %>","9","#123456");
						 s1.addParam("allowfullscreen","false");
						 s1.addParam("allowscriptaccess","always");
						 s1.addParam('flashvars','file=<%=quesVO.getFileurl() %>');
						 s1.addVariable("backcolor","FF0000");
						 s1.write("containerSWF");
				       </script>
			       </td>
			    </tr>
            <% showMeidaType=0; } %>
          </table>
      </div>
          
      <% if(QuestionConstant.QuesType_DanXuan.equals(questypeUse)){ %>
      <div>
          <table style="width:100%;padding:3px">
          <logic:iterate id="quesItemVO" name="quesVO" property="itemList" indexId="j" type="netTest.wareques.vo.Questionitem">
             <tr>
                <td>
                   <div> 
                       <label for='<%="item"+j %>'>
                          <html:radio styleId='<%="item"+j %>' name="useranswerVO" property="answer" tagName="useranswerVO.answer" value="<%=String.valueOf(quesItemVO.getQuesitemid()) %>" disabled="<%=isResult %>" ></html:radio>
                          &nbsp;
                          <% itemFlagMap.put(quesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print(itemFlagArr[j]+"."+"&nbsp;&nbsp;"); %>&nbsp;
                          <bean:write name="quesItemVO" property="quesitemcontent"/>
                       </label>
                   </div>
                   <%if(QuestionConstant.FileType_pict.equals(quesItemVO.getFiletype())){ %>
		              <div style="background: #eeeeee;padding-left: 50px;"> 
		                  <img src="<%=WebConstant.FileContext+quesItemVO.getFileurl() %>" height="100">
		              </div>
		           <%} %>
                </td>
            </tr>
          </logic:iterate>
          <%if(isResult){ %>  
             <tr>
                 <td>
                     正确答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,quesVO.getAnswer()) %>&nbsp;&nbsp;&nbsp;
                     (
                     <logic:equal name="useranswerVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                    <img src="../styles/imgs/ico/right.png" border="0"/>
	                 </logic:equal>
	                 <logic:equal name="useranswerVO" property="isright" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
	                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
	                 </logic:equal>
	                 )
                 </td>
              </tr>
          <%} %>          
          </table>
       </div>
       <%} %>
          
       <% if(QuestionConstant.QuesType_DuoXuan.equals(questypeUse)){ %>
       <div>
          <table style="width:100%;padding:3px">
          <logic:iterate id="quesItemVO" name="quesVO" property="itemList" indexId="j" type="netTest.wareques.vo.Questionitem">
              <tr>
                 <td>
                   <div> 
                       <label for='<%="item"+j %>'>
                          <html:checkbox styleId='<%="item"+j %>' tagName="useranswerVO.answerArr" value="<%=String.valueOf(quesItemVO.getQuesitemid()) %>" checkStr="<%=useranswerVO.getAnswer() %>" disabled="<%=isResult %>" ></html:checkbox>
                          &nbsp;
                          <% itemFlagMap.put(quesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print(itemFlagArr[j]+"."+"&nbsp;&nbsp;"); %>&nbsp;
                          <bean:write name="quesItemVO" property="quesitemcontent"/>
                       </label>
                   </div>
                   <%if(QuestionConstant.FileType_pict.equals(quesItemVO.getFiletype())){ %>
		              <div style="background: #eeeeee;padding-left: 50px;"> 
		                  <img src="<%=WebConstant.FileContext+quesItemVO.getFileurl() %>" height="100">
		              </div>
		           <%} %>
                 </td>
             </tr>
          </logic:iterate>
          <%if(isResult){ %>  
             <tr>
                 <td>
                     正确答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,quesVO.getAnswer()) %>&nbsp;&nbsp;&nbsp;
                     (
                     <logic:equal name="useranswerVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                    <img src="../styles/imgs/ico/right.png" border="0"/>
	                 </logic:equal>
	                 <logic:equal name="useranswerVO" property="isright" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
	                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
	                 </logic:equal>
	                 )
                 </td>
             </tr>
          <%} %>
          </table>
       </div>
       <%} %>
          
       <% if(QuestionConstant.QuesType_PanDuan.equals(questypeUse)){ %>
       <div>
		  <table style="width:100%;padding:3px">
             <tr>
                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;
                <label for="quesItemRightId">
                <html:radio styleId="quesItemRightId" value="<%=QuestionConstant.IsRight_right.toString() %>" name="useranswerVO" tagName="useranswerVO.answer" property="answer" disabled="<%=isResult %>" >正确</html:radio>
                </label>
                <label for="quesItemWrongId">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <html:radio styleId="quesItemWrongId" value="<%=QuestionConstant.IsRight_wrong.toString() %>" name="useranswerVO" tagName="useranswerVO.answer" property="answer" disabled="<%=isResult %>" >错误</html:radio>
                </label>
                </td>
             </tr>
             
             <tr><td>
             <%if(isResult){ %>  
                         本题答案: <logic:equal name="quesVO" property="answersim" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                                    正确
		                     </logic:equal>
		                     <logic:equal name="quesVO" property="answersim" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
		                               错误
		                     </logic:equal>
		            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;判题结果:
		            (
                     <logic:equal name="useranswerVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                    <img src="../styles/imgs/ico/right.png" border="0"/>
	                 </logic:equal>
	                 <logic:equal name="useranswerVO" property="isright" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
	                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
	                 </logic:equal>
	                 )
		     <%} %>
             </td></tr>
             
	      </table>
		</div>
        <%} %>
        
        <% if(isResult && QuestionConstant.QuesType_TianKong.equals(questypeUse)){ %>
	         <div>
			  <table style="width:100%;padding:3px">
			     <tr>
			        <td>
			            考生答案:&nbsp;&nbsp;
			            <% char[] isrightArrTK = useranswerVO.getIsright().toCharArray();
			               String[] usransArrTK = QueshowUtil.getQuesAnswerTK(useranswerVO.getAnswer());
			               if(isrightArrTK!=null&&isrightArrTK.length>0){
			                  for(int k=0;k<isrightArrTK.length;k++){
			                      if(k<usransArrTK.length){
			                         if(usransArrTK[k]==null||usransArrTK[k].trim().length()<1){
			                            usransArrTK[k] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			                         }
			                         out.print("<u>&nbsp;"+usransArrTK[k]+"</u>&nbsp;&nbsp;");
			                         if(QuestionConstant.IsRight_right.toString().equals(String.valueOf(isrightArrTK[k]))){
			                            out.print("<img src='../styles/imgs/ico/right.png' border='0'/>");
			                         }else{
			                            out.print("<img src='../styles/imgs/ico/wrong.png' border='0'/>");
			                         }
			                         out.print("&nbsp;&nbsp;&nbsp;");
			                      }
			                  }
			               }
			             %>
			        </td>
			     </tr>
			  </table>
			  </div>
		  <%} %>
        
          <!-- 完形填空题 -->
          <% if(QuestionConstant.QuesType_WanXingTianKong.equals(questypeUse)){ %>
          <div style="height:500px;width:100%;overflow: auto">
          <logic:iterate id="subQuesVO" name="quesVO" property="subquesList" indexId="subind" type="netTest.wareques.vo.Question">
          <% subanswerVO = useranswerVO.getSubanswList().get(subind); 
             if(UseranswerConstant.ExamineStatus_DidJudge.equals(subanswerVO.getExaminestatus())){
                isResult = true;
             } %>
          <div>
          <table width="99%" border="1" cellpadding="1" cellspacing="0" style="margin:3px">
             <tr>
                <td style="padding:5px;" bgcolor="#ECF5FF">
                   <% out.print((subind+1)+".&nbsp;"); %>
                   <bean:write name="subQuesVO" property="question" />
                   <input type="hidden" name='useranswer[<%=subind %>].quesid' value='<bean:write name="subQuesVO" property="quesid"/>'/>
                </td>
             </tr>
          </table>
          </div>
          <!-- 完形填空的选项 -->
          <div>
             <table width="99%" border="1" cellpadding="1" cellspacing="0" style="margin:3px">
              <logic:iterate id="subquesItemVO" name="subQuesVO" property="itemList" indexId="j" type="netTest.wareques.vo.Questionitem">
              <tr>
                 <td>
                   <div> 
                       <label for='<%="item"+j+"subques"+subind+"Id" %>'>
                          <input type="radio" id='<%="item"+j+"subques"+subind+"Id" %>' name='<%="useranswer["+subind+"].answerArr" %>' value="<%=String.valueOf(subquesItemVO.getQuesitemid()) %>" <%if(subquesItemVO.getQuesitemid().toString().equals(subanswerVO.getAnswer())){ out.print("checked=\"checked\""); } %>  <%if(isResult) {out.print("disabled=\"disabled\"");} %> />
                          &nbsp;
                          <% itemFlagMap.put(subquesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print(itemFlagArr[j]+"."+"&nbsp;&nbsp;"); %>&nbsp;
                          <bean:write name="subquesItemVO" property="quesitemcontent"/>
                       </label>
                   </div>
                   <%if(QuestionConstant.FileType_pict.equals(subquesItemVO.getFiletype())){ %>
		              <div style="background: #eeeeee;padding-left: 50px;"> 
		                  <img src="<%=WebConstant.FileContext+subquesItemVO.getFileurl() %>" height="100">
		              </div>
		           <%} %>
                 </td>
              </tr>
              </logic:iterate>
              <%if(isResult){ %>
               <tr>
                  <td>
                       正确答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,subQuesVO.getAnswer()) %>&nbsp;&nbsp;&nbsp;
                     (
                     <%if(QuestionConstant.IsRight_right.toString().equals(subanswerVO.getIsright())){ %> 
	                    <img src="../styles/imgs/ico/right.png" border="0"/>
	                 <%}else { %>
	                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
	                 <%} %>
	                 )
                  </td>
               </tr>
               <%} %>
             </table>
             </div>
             </logic:iterate>
             </div>
          <%} %>
          
          <!-- 阅读理解题 -->
          <% if(QuestionConstant.QuesType_YueDuLiJie.equals(questypeUse)){ %>
          <div style="width:100%;padding:3px;text-align:left;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题&rArr;</font></div>
          <div style="height:500px;width:100%;overflow: auto">
          <logic:iterate id="subQuesVO" name="quesVO" property="subquesList" indexId="subind" type="netTest.wareques.vo.Question">
             <% subanswerVO = useranswerVO.getSubanswList().get(subind); 
             if(UseranswerConstant.ExamineStatus_DidJudge.equals(subanswerVO.getExaminestatus())){
                isResult = true;
             } %>
             <div>
                <table style="width:99%;padding:1px">
	               <tr>
	                  <td style="padding:5px;" bgcolor="#ECF5FF">
	                     <% out.print(questionNO+".&nbsp;"); %>
	                     <bean:write name="subQuesVO" property="question" />
	                     <input type="hidden" name='useranswer[<%=(questionNO-1) %>].quesid' value='<bean:write name="subQuesVO" property="quesid"/>'/>
	                  </td>
	               </tr>
	            </table>
	         </div>
          <!-- 阅读理解的答案选项 -->
          <div>
             <table style="width:99%;padding:1px">
               <logic:iterate id="subquesItemVO" name="subQuesVO" property="itemList" indexId="j" type="netTest.wareques.vo.Questionitem">
               <tr>
                 <td>
                   <div> 
                       <label for='<%="item"+j+"subques"+subind+"Id" %>' >
                          <html:checkbox styleId='<%="item"+j+"subques"+subind+"Id" %>' tagName='<%="useranswer["+subind+"].answerArr" %>' value="<%=String.valueOf(subquesItemVO.getQuesitemid()) %>" checkStr="<%=subanswerVO.getAnswer() %>" disabled="<%=isResult %>" ></html:checkbox>
                          &nbsp;
                          <% itemFlagMap.put(subquesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print(itemFlagArr[j]+"."+"&nbsp;&nbsp;"); %>&nbsp;
	                      <bean:write name="subquesItemVO" property="quesitemcontent"/>
                       </label>
                   </div>
                   <%if(QuestionConstant.FileType_pict.equals(subquesItemVO.getFiletype())){ %>
		              <div style="background: #eeeeee;padding-left: 50px;"> 
		                  <img src="<%=WebConstant.FileContext+subquesItemVO.getFileurl() %>" height="100">
		              </div>
		           <%} %>
                 </td>
               </tr>
               </logic:iterate>
               <%if(isResult){ %>
               <tr>
                 <td>
                     正确答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,subQuesVO.getAnswer()) %>&nbsp;&nbsp;&nbsp;
                     (
	                 <%if(QuestionConstant.IsRight_right.toString().equals(subanswerVO.getIsright())){ %>
	                    <img src="../styles/imgs/ico/right.png" border="0"/>
	                 <%}else { %>
	                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
	                 <%} %>
	                 )
                 </td>
               </tr>
              <%} %>
             </table>
          </div>
          <%questionNO++; %>
          </logic:iterate>
          </div>
          <%} %>
          
          <% if(QuestionConstant.QuesType_WenDa.equals(questypeUse)){ %>
          <div>
             <table style="width:100%;padding:3px">
                <tr>
                   <td>
                      <textarea name="useranswerVO.answer" style="width:100%" rows="4" <%if(isResult){out.print("disabled=\"disabled\"");} %> ><bean:write name="useranswerVO" property="answer"/></textarea>
                   </td>
                </tr>
              <%if(isResult){ %>
                <tr>
                   <td>参考答案: <br>
                      <%=quesVO.getAnswer() %>
                   </td>
                </tr>
              <%} %>
             </table>
          </div>
          <%} %>
       
       <% if(isResult&&quesVO.getAnswerVO()!=null&&quesVO.getAnswerVO().getSolvedesc()!=null){ %>   
       <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">答案说明</font></div>
	   <div>
	      <table class="formTable">
		     <tr>
		        <td align="left" >
	      			<%=quesVO.getAnswerVO().getSolvedesc() %>
	      		</td>
	      	 </tr>
	      </table>
	   </div>
       <%} %>
       <div id="displayMessDiv" >题目进度: <%=quesIndex+1 %>/<%=quesnum %> </div>
       <br>    

	   <!-- 循环显示题型结束 -->
	   <div id="functionBarButtomDiv">
	  	   <ul>
	  	      <%if(quesIndex>0){ %>
	  	      <li><button type="button" onclick="switchQues('-1');">上一题</button></li>&nbsp;&nbsp;&nbsp;&nbsp;
	  	      <%} %>
	  	      <%if(quesIndex<(quesnum-1)){ %>
		      <li><button type="button" onclick="switchQues('1');">下一题</button></li>&nbsp;&nbsp;&nbsp;&nbsp;
		      <%} %>
			  <li><button type="button" onclick="submitAnswer();" <%if(isResult){out.print("disabled=\"disabled\"");} %> >提交答案</button></li>&nbsp;&nbsp;&nbsp;&nbsp;
		      <%if(testway==2 && (quesIndex+1==quesnum) && !TestuserConstant.Status_endExam.equals(exeruserStatus)){ %>
		      <li><button type="button" onclick="submitExercise();">完成练习</button></li>
		      <%} %>
		   </ul>
	   </div>
	   </div>
	   </center>
	   
	   <div>&nbsp;</div>
	</html:form>
	</div>
  </body>
</html:html>
