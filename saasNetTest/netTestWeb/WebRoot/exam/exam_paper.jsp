<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.vo.Questionitem,netTest.paper.vo.Paperques,netTest.wareques.constant.QuestionConstant,java.util.List,commonTool.util.StringUtil,netTest.util.QueshowUtil" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="selfExamForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="selfExamForm" property="editType" type="java.lang.Integer"/>
<bean:define id="questypeUse" name="selfExamForm" property="questypeUse" type="java.lang.Integer"/>
<bean:define id="questypeidUse" name="selfExamForm" property="questypeidUse" type="java.lang.Long"/>
<bean:define id="quesList" name="selfExamForm" property="typeUseVO.paperquesList" type="java.util.List"/>
<bean:define id="totalpage" name="selfExamForm" property="answertypeVO.totalpage" type="java.lang.Integer"/>
<bean:define id="currentpage" name="selfExamForm" property="answertypeVO.currentpage" type="java.lang.Integer"/>

  <%
    int showMeidaType = 0;   
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
    
    //---------------------------------------------禁止页面刷新----------------------------------------------------
	  //屏蔽鼠标右键
	  document.oncontextmenu = function(){
	     event.returnValue=false;
	  };  
	    
	  document.onkeydown = function()   
	  {
	      if((window.event.altKey)&&   
	          ((window.event.keyCode==37)||       //屏蔽   Alt+   方向键   ←   
	           (window.event.keyCode==39)))       //屏蔽   Alt+   方向键   →   
	      {   
	          event.returnValue=false;   
	      }   
	            
	      if(  //(event.keyCode==8)  ||                                   //屏蔽退格删除键   
	           (event.keyCode==116)||                                   //屏蔽   F5   刷新键   
	           (event.ctrlKey && event.keyCode==82))
	      {   //Ctrl   +   R   
	          event.keyCode=0;   
	          event.returnValue=false;   
	      }   
	  };
//-----------------------------------------------禁止页面刷新--------------------------------------------------
       var closewinway = 1;
       var questypeuseVar = <%=questypeUse %>;
    
       var itemFlagArr = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

       function switchQuestype(questypeid){
          var oForm = document.getElementById("examFormId");
          var result = checkExam(oForm,questypeuseVar,-1);
          if(result){
             closewinway = 0;
             oForm.action = 'selfExamShift.do';
             document.getElementById("questypeidShwId").value = questypeid;
             oForm.submit();
          }
       }
       
       function switchPage(pagenumShw){
          var oForm = document.getElementById("examFormId");
          var result = checkExam(oForm,questypeuseVar,-1);
          if(result){
             closewinway = 0;
             oForm.action = 'selfExamShift.do';
             document.getElementById("pagenumShwId").value = pagenumShw;
             oForm.submit();
          }
       }
       
       function submitPaper(){
          if(confirm('确定要提交试卷吗?')){
             var oForm = document.getElementById("examFormId");
             var result = checkExam(oForm,questypeuseVar,-1);
             if(result){
                closewinway = 0;
                oForm.action = 'selfExamSubmit.do';
                submitForm("examFormId");
             }
          }
       }    
       
       var num = <bean:write name="selfExamForm" property="remainTime"/>;
       function t(){
		  var mm = Math.floor(num/60);
		  var ss = num % 60;
		  if(ss < 10) 
		  {  ss = "0"+ss; }
		  document.getElementById("remainTimeId").value = num;
		  document.getElementById("remainTimeViewId").value = mm+"分"+ss+"秒";
		  if(num == 60){
		     alert("考试还有1分钟,请保存后交卷");
		  }
		  if(num <= 0){
		  //结束时的操作
		     alert("考试时间已到!");
		     closewinway = 0;
		     var oForm = document.getElementById("examFormId");
		     oForm.action = 'selfExamSubmit.do';
             oForm.submit();
		     return;
		  }else{
		     num--;
		  }
		  if(num>0){
		     setTimeout("t()", 1000);
		  }
	   }
	
	    window.onload = function initFun(){  
		   window.moveTo(0,-30);  
		   window.resizeTo(screen.width,screen.height);  
		   t();
	    }; 
	    
	    window.onbeforeunload = function(e){ 
	                         if(closewinway==1){
        					    return '是否直接退出考试而不保存答卷内容?';  
        					 }
						  };
	     
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="examFormId" action="/exam/selfExamShift.do" method="post">
      <input type="hidden" id="paperidId" name="paperVO.paperid" value='<bean:write name="selfExamForm" property="paperVO.paperid"/>'/>
      <input type="hidden" id="questypeUseId" name="questypeUse" value='<%=questypeUse.intValue() %>'/>
      <input type="hidden" id="questypeidUseId" name="questypeidUse" value='<%=questypeidUse.intValue() %>'/>
      <input type="hidden" id="questypeidShwId" name="questypeidShw" value=''/>
      <input type="hidden" id="pagenumShwId" name="pagenumShw" value=''/>
	  <input type="hidden" id="remainTimeId" name="remainTime" value='<bean:write name="selfExamForm" property="remainTime"/>'/>
	  <input type="hidden" name="paperversion" value='<bean:write name="selfExamForm" property="paperversion"/>'/>
	  <input type="hidden" name="testid" value='<bean:write name="selfExamForm" property="testid"/>'/>
	  <input type="hidden" name="testuserid" value='<bean:write name="selfExamForm" property="testuserid"/>'/>
	  <input type="hidden" name="examnameshow" value='<bean:write name="selfExamForm" property="examnameshow"/>'/>
	  
	  <center>
	  <div style="margin:5px;"></div>
	  <div style="width:90%;font-size:large;text-align: center;margin:5px">
	      <h2><bean:write name="selfExamForm" property="examnameshow"/></h2>
	  </div>
	  <div style="width:90%;font-size:large;text-align: center;margin:5px">
	       考试时间还剩：<input id="remainTimeViewId" type="text" size="10" readonly border="0" style="height:25;font-size:20;color: red;" />
      </div>
      <div style="width:90%;font-size:medium;text-align: left;margin:10px;"> 
      <logic:notEmpty name="selfExamForm" property="paperVO.questypeList">
	       <logic:iterate id="paperQuesTypeShowVO" name="selfExamForm" property="paperVO.questypeList" type="netTest.paper.vo.Paperquestype"> 
	           <%if(paperQuesTypeShowVO.getQuestypeid().equals(questypeidUse)){ %>
	           <font style="color: red;">
	                <bean:write name="paperQuesTypeShowVO" property="questypename"/>   
	           </font>            
	           <%}else{ %>
	           <a href="#" onclick="switchQuestype('<%=paperQuesTypeShowVO.getQuestypeid() %>');return false;">
	              <bean:write name="paperQuesTypeShowVO" property="questypename"/>
	           </a>
	           <%} %>
	           &nbsp;|&nbsp;
	       </logic:iterate>
	  </logic:notEmpty>
      </div>
      
      <% int questionNO = 1;
       %>
	  
      <!-- 循环显示题型开始 -->          

          <div>
          <table width="90%">
             <tr style="font-size: medium;font-weight: bold">            
                <td>
              	   <bean:write name="selfExamForm" property="typeUseVO.questypename"/>(总分:
                   <bean:write name="selfExamForm" property="typeUseVO.patternscore"/>，每题
                   <bean:write name="selfExamForm" property="typeUseVO.quesscore"/>分)
                   <p>
                   <bean:write name="selfExamForm" property="typeUseVO.questypenote"/>
                </td>        
             </tr>
             <tr><td>
                  <%
                    if(totalpage>1){
	                    for(int pageind=1;pageind<totalpage+1;pageind++){
	                        if(currentpage!=pageind){
	                           out.print("[<a href='#' onclick='switchPage("+(pageind)+");return false;'>&nbsp;"+
	                                     (pageind)
	                                     +"&nbsp;</a>]");
	                        }else{
	                           out.print("[&nbsp;<font style='color: red;font-weight: bold'>"+pageind+"</font>&nbsp;]");
	                        }
	                        out.print("&nbsp;&nbsp;");
	                    }
                    }
                   %>
             </td></tr>
          </table> 
          </div>      
          <logic:notEmpty name="selfExamForm" property="typeUseVO.paperquesList">
          <logic:iterateQues quesvoid="paperQuesVO" usrvoid="useranswerVO" quesordId="quesorder" quesname="selfExamForm" quesprop="typeUseVO.paperquesList" usrname="selfExamForm" usrprop="answertypeVO.answerpageList" indexId="ind">
              <div>
	          <table style="width:90%;padding:3px">
	             <tr>
	                <td style="padding:10px 5px;" bgcolor="#ECF5FF">
	                   <% if(!QuestionConstant.Comptype_compWhole.equals(paperQuesVO.getComptype())){
	                         out.print(questionNO+".&nbsp;");questionNO++; 
	                      }else { out.print("&nabla;"); } %>
	                   <%if(QuestionConstant.QuesType_TianKong.equals(questypeUse)){ %>
	                   <bean:writeTK name="paperQuesVO" property="question" inputName='<%="useranswer["+quesorder+"].answerArr" %>' filter="false" transfer="true" showIndentWay="3" questypeProp="questype" answerTK="<%=useranswerVO.getAnswer() %>"/>
	                   <%}else if(QuestionConstant.QuesType_WanXingTianKong.equals(questypeUse)){ %>
	                   <bean:writeTK name="paperQuesVO" property="question" filter="false" transfer="true" showIndentWay="2" questypeProp="questype"/>
	                   <%}else{ %>
	                   <bean:write name="paperQuesVO" property="question" filter="false"/>
	                   <%} %>
	                   <logic:notEqual name="paperQuesVO" property="comptype" value="<%=QuestionConstant.Comptype_compWhole.toString() %>" >
	                      <input type="hidden" name='useranswer[<%=quesorder %>].quesid' value='<bean:write name="paperQuesVO" property="quesid"/>'/>
	                   </logic:notEqual>
	                </td>
	             </tr>
	            <!-- 显示题目图片 -->
	            
	            <%if(QuestionConstant.FileType_pict.equals(paperQuesVO.getFiletype())){ %>
		            <tr>
		               <td align="left" style="padding-left: 50px;">
		                   <img src="<%=WebConstant.FileContext+paperQuesVO.getFileurl() %>" height="100">
		               </td>
		            </tr>
		        <% showMeidaType = 0;
		         }else if(QuestionConstant.FileType_voice.equals(paperQuesVO.getFiletype())){
		            showMeidaType = 20;
		         }
		         %>
	            
	            <% if(showMeidaType!=0){ %>
	                <tr>
	                   <td align="left" id="container<%=ind %>" style="padding-left: 50px;">
	                       <script type="text/javascript">
							 var s1 = new SWFObject("../styles/player.swf","container<%=ind %>","328","<%=showMeidaType %>","9","#123456");
							 s1.addParam("allowfullscreen","false");
							 s1.addParam("allowscriptaccess","always");
							 s1.addParam('flashvars','file=<%=paperQuesVO.getFileurl() %>');
							 s1.addVariable("backcolor","FF0000");
							 s1.write("container<%=ind %>");
					       </script>
				       </td>
				    </tr>
	            <% showMeidaType=0; } %>
	          </table>
	          </div>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_DanXuan.toString() %>" >
	          <div>
              <table style="width:90%;padding:3px">
                  <logic:iteQuesitem itemvoid="quesItemVO" ordname="useranswerVO" ordprop="itemorderstr" listname="paperQuesVO" listprop="itemList" indexId="j">
                  <tr>
	                 <td>
	                   <div> 
	                       <label for='<%="item"+j+"ques"+ind+"Id" %>'>
	                          <html:radio styleId='<%="item"+j+"ques"+ind+"Id" %>' name="useranswerVO" property="answer" tagName='<%="useranswer["+quesorder+"].answer" %>' value='<%=String.valueOf(quesItemVO.getQuesitemid()) %>' ></html:radio>
	                          <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=j %>]+'.)');</script></font>&nbsp;
	                          <bean:write name="quesItemVO" property="quesitemcontent" filter="false"/>
	                       </label>
	                   </div>
	                   <%if(QuestionConstant.FileType_pict.equals(quesItemVO.getFiletype())){ %>
			              <div style="background: #eeeeee;padding-left: 50px;"> 
			                  <img src="<%=WebConstant.FileContext+quesItemVO.getFileurl() %>" height="100">
			              </div>
			           <%} %>
	                 </td>
	              </tr>
	              </logic:iteQuesitem>
              </table>
              </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_DuoXuan.toString() %>" >
	          <div>
              <table style="width:90%;padding:3px">
                  <logic:iteQuesitem itemvoid="quesItemVO" ordname="useranswerVO" ordprop="itemorderstr" listname="paperQuesVO" listprop="itemList" indexId="j">
                  <tr>
	                 <td>
	                   <div> 
	                       <label for='<%="item"+j+"ques"+ind+"Id" %>'>
	                          <html:checkbox styleId='<%="item"+j+"ques"+ind+"Id" %>' tagName='<%="useranswer["+quesorder+"].answerArr" %>' value="<%=String.valueOf(quesItemVO.getQuesitemid()) %>" checkStr="<%=useranswerVO.getAnswer() %>"></html:checkbox>
	                          <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=j %>]+'.)');</script></font>&nbsp;
	                          <bean:write name="quesItemVO" property="quesitemcontent" filter="false"/>
	                       </label>
	                   </div>
	                   <%if(QuestionConstant.FileType_pict.equals(quesItemVO.getFiletype())){ %>
			              <div style="background: #eeeeee;padding-left: 50px;"> 
			                  <img src="<%=WebConstant.FileContext+quesItemVO.getFileurl() %>" height="100">
			              </div>
			           <%} %>
	                 </td>
	              </tr>
	              </logic:iteQuesitem>
              </table>
              </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_PanDuan.toString() %>" >
	          <div>
			  <table style="width:90%;padding:3px">
	             <tr>
	                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;
	                <label for='<%="quesItemRightId"+ind %>'>
	                <html:radio styleId='<%="quesItemRightId"+ind %>' value="<%=QuestionConstant.IsRight_right.toString() %>" name="useranswerVO" tagName='<%="useranswer["+quesorder+"].answer" %>' property="answer">正确</html:radio>
	                </label>
	                <label for='<%="quesItemWrongId"+ind %>'>
	                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                <html:radio styleId='<%="quesItemWrongId"+ind %>' value="<%=QuestionConstant.IsRight_wrong.toString() %>" name="useranswerVO" tagName='<%="useranswer["+quesorder+"].answer" %>' property="answer">错误</html:radio>
	                </label>
	                </td>
	             </tr>
		      </table>
			  </div>
	          </logic:equal>
	          
	          <!-- 完形填空题 -->
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_WanXingTianKong.toString() %>" >
	          <div style="width:88%;padding:5px;margin-left:15px;text-align:left;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题&rArr;</font></div>
              <div style="height:500px;width:90%;overflow: auto;margin-left:15px;">
	          <logic:iterateQues quesvoid="subQuesVO" quesordId="subquesorder" usrvoid="subanswerVO" quesname="paperQuesVO" quesprop="subquesList" usrname="useranswerVO" usrprop="subanswList" indexId="subind">
              <div>
	          <table style="width:99%;padding:1px">
	             <tr>
	                <td style="padding:5px;" bgcolor="#ECF5FF">
	                   <% out.print(questionNO+".&nbsp;");questionNO++; %>
	                   <bean:write name="subQuesVO" property="question" filter="false"/>
                       <input type="hidden" name='useranswer[<%=(questionNO-1) %>].quesid' value='<bean:write name="subQuesVO" property="quesid"/>'/>
	                </td>
	             </tr>
	          </table>
	          </div>
	          <!-- 完形填空的选项 -->
	          <div>
              <table style="width:99%;padding:1px">
                  <logic:equal value="<%=QuestionConstant.Rowitems_One.toString() %>" name="subQuesVO" property="rowitems">
                  <logic:iteQuesitem itemvoid="subquesItemVO" ordname="subanswerVO" ordprop="itemorderstr" listname="subQuesVO" listprop="itemList" indexId="j">
                  <tr>
	                 <td>
	                    <div> 
	                       <label for='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>'>
	                          <html:radio styleId='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>' name="subanswerVO" property="answer" tagName='<%="useranswer["+(questionNO-1)+"].answerArr" %>' value="<%=String.valueOf(subquesItemVO.getQuesitemid()) %>" ></html:radio>
	                          <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=j %>]+'.)');</script></font>
	                          &nbsp;<bean:write name="subquesItemVO" property="quesitemcontent" filter="false"/>
	                       </label>
	                    </div>
	                    <%if(QuestionConstant.FileType_pict.equals(subquesItemVO.getFiletype())){ %>
			               <div style="background: #eeeeee;padding-left: 50px;"> 
			                  <img src="<%=WebConstant.FileContext+subquesItemVO.getFileurl() %>" height="100">
			               </div>
			            <%} %>
	                 </td>
	              </tr>
	              </logic:iteQuesitem>
	              </logic:equal>
	              <logic:equal value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="subQuesVO" property="rowitems">
	              <tr>
	                 <td>
	                    <logic:iteQuesitem itemvoid="subquesItemVO" ordname="subanswerVO" ordprop="itemorderstr" listname="subQuesVO" listprop="itemList" indexId="j">
	                    <span style="margin-right:10px; padding:2px; border: 1px solid #cccccc;"> 
	                       <label for='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>'>
	                          <html:radio styleId='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>' name="subanswerVO" property="answer" tagName='<%="useranswer["+(questionNO-1)+"].answerArr" %>' value="<%=String.valueOf(subquesItemVO.getQuesitemid()) %>" ></html:radio>
	                          <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=j %>]+'.)');</script></font>
	                          &nbsp;<bean:write name="subquesItemVO" property="quesitemcontent" filter="false"/>
	                       </label>
	                    </span>&nbsp;&nbsp;
			            </logic:iteQuesitem>
	              	 </td>
	              </tr>
	              </logic:equal>
              </table>
              </div>
              </logic:iterateQues>
              </div>
	          </logic:equal>
	          
	          <!-- 阅读理解题 -->
              <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_YueDuLiJie.toString() %>" >
              <div style="width:88%;margin-left:15px;padding:5px;text-align:left;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题&rArr;</font></div>
	          <div style="width:90%;overflow: auto;margin-left:15px;">
	          <logic:iterateQues quesvoid="subQuesVO" quesordId="subquesorder" usrvoid="subanswerVO" quesname="paperQuesVO" quesprop="subquesList" usrname="useranswerVO" usrprop="subanswList" indexId="subind">
              <div>
	          <table style="width:99%;padding:1px">
	             <tr>
	                <td style="padding:5px;" bgcolor="#ECF5FF">
	                   <% out.print(questionNO+".&nbsp;"); %>
	                   <bean:write name="subQuesVO" property="question" filter="false"/>
	                   <input type="hidden" name='useranswer[<%=(questionNO-1) %>].quesid' value='<bean:write name="subQuesVO" property="quesid"/>'/>
	                </td>
	             </tr>
	          </table>
	          </div>
	          <!-- 阅读理解的答案选项 -->
	          <div>
              <table style="width:99%;padding:1px">
                  <logic:equal value="<%=QuestionConstant.Rowitems_One.toString() %>" name="paperQuesVO" property="rowitems">
                  <logic:iteQuesitem itemvoid="subquesItemVO" ordname="subanswerVO" ordprop="itemorderstr" listname="subQuesVO" listprop="itemList" indexId="j">
                  <tr>
	                 <td>
	                   <div> 
	                       <label for='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>' >
	                          <html:checkbox styleId='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>' tagName='<%="useranswer["+(questionNO-1)+"].answerArr" %>' value="<%=String.valueOf(subquesItemVO.getQuesitemid()) %>" checkStr="<%=subanswerVO.getAnswer() %>"></html:checkbox>
	                          <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=j %>]+'.)');</script></font>&nbsp;
	                          <bean:write name="subquesItemVO" property="quesitemcontent" filter="false"/>
	                       </label>
	                   </div>
	                   <%if(QuestionConstant.FileType_pict.equals(subquesItemVO.getFiletype())){ %>
			              <div style="background: #eeeeee;padding-left: 50px;"> 
			                  <img src="<%=WebConstant.FileContext+subquesItemVO.getFileurl() %>" height="100">
			              </div>
			           <%} %>
	                 </td>
	              </tr>
	              </logic:iteQuesitem>
	              </logic:equal>
	              <logic:equal value="<%=QuestionConstant.Rowitems_AllInOneRow.toString() %>" name="paperQuesVO" property="rowitems">
	              <tr>
	                 <td style="padding-bottom:20px;">
	                    <logic:iteQuesitem itemvoid="subquesItemVO" ordname="subanswerVO" ordprop="itemorderstr" listname="subQuesVO" listprop="itemList" indexId="j">
	                    <span style="margin-right:10px; padding:2px; border: 1px solid #cccccc;"> 
	                       <label for='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>'>
	                          <html:checkbox styleId='<%="item"+j+"subques"+subind+"ques"+ind+"Id" %>' tagName='<%="useranswer["+(questionNO-1)+"].answerArr" %>' value="<%=String.valueOf(subquesItemVO.getQuesitemid()) %>" checkStr="<%=subanswerVO.getAnswer() %>"></html:checkbox>
	                          <font style="font-weight: bold;"><script>document.write(itemFlagArr[<%=j %>]+'.)');</script></font>
	                          &nbsp;<bean:write name="subquesItemVO" property="quesitemcontent" filter="false"/>
	                       </label>
	                    </span>&nbsp;&nbsp;
			            </logic:iteQuesitem>
	              	 </td>
	              </tr>
	              </logic:equal>
              </table>
              </div>
              <%questionNO++; %>
              </logic:iterateQues>
              </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value='<%=QuestionConstant.QuesType_WenDa.toString() %>' >
	          <div>
              <table style="width:90%;padding:3px">
                  <tr>
	                 <td>
                         <textarea name='<%="useranswer["+quesorder+"].answer" %>' style="width:100%" rows="4" usage="max-length:1000" fie="答案"><bean:write name="useranswerVO" property="answer" filter="false"/></textarea>
	                 </td>
	              </tr>
	          </table>
              </div>
	          </logic:equal>
	          
          </logic:iterateQues>
      </logic:notEmpty>
       
       <div id="displayMessDiv" ></div>
       <br>    

	<!-- 循环显示题型结束 -->
	   <div id="functionBarButtomDiv">
	  	   <ul>
	  	      <logic:notEqual name="selfExamForm" property="pagenumPrv" value="-3">
		          <li><button type="button" onclick="switchPage('<bean:write name="selfExamForm" property="pagenumPrv"/>');">上一页</button></li>&nbsp;&nbsp;&nbsp;&nbsp;
		      </logic:notEqual>
		      <logic:notEqual name="selfExamForm" property="pagenumNxt" value="-3">
			  	  <li><button type="button" onclick="switchPage('<bean:write name="selfExamForm" property="pagenumNxt"/>');">下一页</button></li>&nbsp;&nbsp;&nbsp;&nbsp;
			  </logic:notEqual>
			  <logic:equal name="selfExamForm" property="pagenumNxt" value="-3">
			  	  <li><button type="button" onclick="submitPaper();">提交试卷</button></li>
			  </logic:equal>
		   </ul>
	   </div>
	   
	   </center>
	   
	   <div>&nbsp;</div>
	</html:form>
	</div>
  </body>
</html:html>
