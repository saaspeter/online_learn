<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.vo.Questionitem,netTest.paper.vo.Paperques,netTest.wareques.constant.QuestionConstant,java.util.Map,java.util.HashMap,netTest.util.QueshowUtil" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="selfExamForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="selfExamForm" property="editType" type="java.lang.Integer"/>
<bean:define id="questypeUse" name="selfExamForm" property="questypeUse" type="java.lang.Integer" />
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
    <title>查看答卷</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/exam.js"></script>
    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script type="text/javascript">
    
       var questypeuseVar = <%=questypeUse %>;
       var closewinway = 1;
    
       <% String[] itemFlagArr = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"}; 
          Map itemFlagMap = new HashMap();
       %>

       function switchQuestype(questypeid){
          closewinway = 0;
          var oForm = document.getElementById("examFormId");
          document.getElementById("questypeidShwId").value = questypeid;
          document.getElementById("examFormId").submit();
       }
       
       function switchPage(pagenumShw){
          var oForm = document.getElementById("examFormId");
          var result = checkExam(oForm,questypeuseVar,-1);
          if(result){
             closewinway = 0;
             oForm.action = 'examResultShift.do';
             document.getElementById("pagenumShwId").value = pagenumShw;
             document.getElementById("examFormId").submit();
          }
       }
                    	          
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="examFormId" action="/exam/examResultShift.do" method="post">
      <input type="hidden" id="paperidId" name="paperVO.paperid" value='<bean:write name="selfExamForm" property="paperVO.paperid"/>'/>    
      <input type="hidden" id="questypeidUseId" name="questypeidUse" value='<%=questypeidUse.intValue() %>'/>
      <input type="hidden" id="questypeUseId" name="questypeUse" value='<%=questypeUse.intValue() %>'/>
      <input type="hidden" id="questypeidShwId" name="questypeidShw" value=''/>
      <input type="hidden" id="pagenumShwId" name="pagenumShw" value=''/>
      <input type="hidden" id="remainTimeId" name="remainTime" value='<bean:write name="selfExamForm" property="remainTime"/>'/>
	  <input type="hidden" name="totalscore" value='<bean:write name="selfExamForm" property="totalscore"/>'/>
	  <input type="hidden" name="paperversion" value='<bean:write name="selfExamForm" property="paperversion"/>'/>
	  <input type="hidden" name="testid" value='<bean:write name="selfExamForm" property="testid"/>'/>
	  <input type="hidden" name="userid" value='<bean:write name="selfExamForm" property="userid"/>'/>
	  <input type="hidden" name="testuserVO.displayname" value='<bean:write name="selfExamForm" property="testuserVO.displayname"/>'/>
	  <input type="hidden" name="examnameshow" value='<bean:write name="selfExamForm" property="examnameshow"/>'/>
	  <input type="hidden" name="testuserVO.displayname" value='<bean:write name="selfExamForm" property="testuserVO.displayname"/>'/>
	  
	  <center>
	  <div style="margin:5px;"></div>
	  <div style="width:90%;text-align: center;margin:5px">
	      <h3><bean:write name="selfExamForm" property="examnameshow"/></h3>
	   </div>
	  
	  <div>
           <table class="formTable_nowidth" style="width:70%;">
               <tr>
                   <logic:notEmpty name="selfExamForm" property="testuserVO.displayname">
                   <td style="width:100px;text-align:right">&nbsp;考生:</td>
                       <td style="align:left">
                           <bean:write name="selfExamForm" property="testuserVO.displayname"/>&nbsp;
                       </td>
                       </logic:notEmpty>
                       <td style="width:110px;text-align:right">&nbsp;试卷总分:</td>
                       <td style="align:left">
                           <bean:write name="selfExamForm" property="paperVO.papertotalscore"/>&nbsp;
                       </td>
                       <td style="width:100px;text-align:right">&nbsp;及格分:</td>
                       <td style="align:left">
                           <bean:write name="selfExamForm" property="paperVO.paperquascore"/>&nbsp;
                       </td>
                       <td style="width:110px;text-align:right">&nbsp;考生分数:</td>
                       <td style="align:left">
                           <bean:write name="selfExamForm" property="totalscore"/>&nbsp;
                       </td>
               </tr>
            </table>
      </div>
	  
      <div style="width:90%;font-size:medium;text-align: left;margin:10px;"> 
         <logic:notEmpty name="selfExamForm" property="paperVO.questypeList">
	        <logic:iterate id="paperQuesTypeShowVO" name="selfExamForm" property="paperVO.questypeList" type="netTest.paper.vo.Paperquestype"> 
	           <%if(paperQuesTypeShowVO.getQuestypeid().equals(questypeidUse)){ %>
	           <font style="color: red;">
	               <bean:write name="paperQuesTypeShowVO" property="questypename"/>   
	           </font>            
	           <%}else{ %>
	           <a href="javascript:void(0)" onclick="switchQuestype('<%=paperQuesTypeShowVO.getQuestypeid() %>');return false;">
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
                   <bean:write name="selfExamForm" property="typeUseVO.quesscore"/>分)(考生得分:<bean:write name="selfExamForm" property="answertypeVO.answerscore"/>)
                   &nbsp;&nbsp;&nbsp;<bean:write name="selfExamForm" property="typeUseVO.questypenote"/>
                </td>        
             </tr>
             <tr><td>
                  <%
                    if(totalpage>1){
	                    for(int pageind=1;pageind<totalpage+1;pageind++){
	                        if(currentpage!=pageind){
	                           out.print("[<a href='javascript:switchPage("+(pageind)+");' >&nbsp;"+
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
	                <td style="padding:5px;" bgcolor="#ECF5FF">
	                   <% if(!QuestionConstant.Comptype_compWhole.equals(paperQuesVO.getComptype())){
	                         out.print(questionNO+".&nbsp;");questionNO++; 
	                      } else { out.print("&nabla;"); } %>
	                   <%if(QuestionConstant.QuesType_TianKong.equals(questypeUse)){ %>
	                   <bean:writeTK name="paperQuesVO" property="question" inputName='<%="useranswer["+quesorder+"].answerArr" %>' filter="false" transfer="true" showIndentWay="2" questypeProp="questype" answerTK="<%=useranswerVO.getAnswer() %>"/>
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
	                 <td style="width:16px;">
	                     <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                    <img src="../styles/imgs/ico/check.gif" border="0"/>
		                 </logic:equal>	                   
	                 </td>
	                 <td style="float:left;">
	                 	 <% itemFlagMap.put(quesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print("<font style='font-weight: bold;'>"+itemFlagArr[j]+".)</font>"+"&nbsp;"); %>
	                     <bean:write name="quesItemVO" property="quesitemcontent"/>
	                     <%if(QuestionConstant.FileType_pict.equals(quesItemVO.getFiletype())){ %>
			              <div style="background: #eeeeee;padding-left: 50px;"> 
			                  <img src="<%=WebConstant.FileContext+quesItemVO.getFileurl() %>" height="100">
			              </div>
			             <%} %>
	                 </td>
	              </tr>
	              </logic:iteQuesitem>
	              <tr>
	                 <td style="width:16px;"></td>
	                 <td>
	                     考生答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,useranswerVO.getAnswer()) %>&nbsp;&nbsp;right or wrong:<bean:write name="useranswerVO" property="isright"/>
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
              </table>
              </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_DuoXuan.toString() %>" >
	          <div>
              <table style="width:90%;padding:3px">
                  <logic:iteQuesitem itemvoid="quesItemVO" ordname="useranswerVO" ordprop="itemorderstr" listname="paperQuesVO" listprop="itemList" indexId="j">
                  <tr>
	                 <td style="width:16px;">
	                    <logic:equal name="quesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                       <img src="../styles/imgs/ico/check.gif" border="0"/>
	                    </logic:equal>
	                 </td>
	                 <td style="float:left">
	                 	 <% itemFlagMap.put(quesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print("<font style='font-weight: bold;'>"+itemFlagArr[j]+".)</font>"); %>&nbsp;
	                          <bean:write name="quesItemVO" property="quesitemcontent"/>

	                     <%if(QuestionConstant.FileType_pict.equals(quesItemVO.getFiletype())){ %>
			              <div style="background: #eeeeee;padding-left: 50px;"> 
			                  <img src="<%=WebConstant.FileContext+quesItemVO.getFileurl() %>" height="100">
			              </div>
			             <%} %>
	                 </td>
	              </tr>
	              </logic:iteQuesitem>
	              <tr>
	                 <td style="width:16px;"></td>
	                 <td>
	                                                          考生答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,useranswerVO.getAnswer()) %>&nbsp;&nbsp;
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
              </table>
              </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_PanDuan.toString() %>" >
	          <div>
			  <table style="width:90%;padding:3px">
	             <tr>
	                <td align="left">&nbsp;&nbsp;&nbsp;&nbsp;
                          本题答案: <logic:equal name="paperQuesVO" property="answersim" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                                    正确
	             </logic:equal>
		         <logic:equal name="paperQuesVO" property="answersim" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
		                         错误
		         </logic:equal>
	             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     考生答案: <logic:equal name="useranswerVO" property="answer" value="<%=QuestionConstant.IsRight_right.toString() %>" >
	                                    正确
		         </logic:equal>
		         <logic:equal name="useranswerVO" property="answer" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
		                         错误
		         </logic:equal>
		         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
		      </table>
			  </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_TianKong.toString() %>" >
	          <div>
			  <table style="width:90%;padding:3px">
			     <tr>
			        <td>
			            考生答案:&nbsp;&nbsp;
			            <% char[] isrightArrTK = useranswerVO.getIsright().toCharArray();
			               String[] usransArrTK = QueshowUtil.getQuesAnswerTK(useranswerVO.getAnswer());
			               if(isrightArrTK!=null&&isrightArrTK.length>0){
			                  if(usransArrTK==null){
				                  usransArrTK = new String[isrightArrTK.length];
				              }
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
			  </logic:equal>
	          
	          <!-- 完形填空题 -->
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_WanXingTianKong.toString() %>" >
	          <div style="width:88%;margin-left:15px;padding:5px;text-align:left;background-color:#DCEAFC"><font style="font-weight: bold;">回答以下问题&rArr;</font></div>
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
	                       <span style="width:16px;">
		                      <logic:equal name="subquesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                         <img src="../styles/imgs/ico/check.gif" border="0"/>
		                      </logic:equal>
		                   </span>
	                          &nbsp;
	                          <% itemFlagMap.put(subquesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print("<font style='font-weight: bold;'>"+itemFlagArr[j]+".)</font>"); %>&nbsp;
	                          <bean:write name="subquesItemVO" property="quesitemcontent"/>
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
		                      <logic:equal name="subquesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                         <img src="../styles/imgs/ico/check.gif" border="0"/>
		                      </logic:equal>
	                          <% itemFlagMap.put(subquesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print("<font style='font-weight: bold;'>"+itemFlagArr[j]+".)</font>"); %>
	                          &nbsp;<bean:write name="subquesItemVO" property="quesitemcontent"/>
		                   </span>
			            </logic:iteQuesitem>
	              	 </td>
	              </tr>
	              </logic:equal>
	              <tr>
	                 <td>
	                     考生答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,subanswerVO.getAnswer()) %>&nbsp;&nbsp;
	                     (
	                     <logic:equal name="subanswerVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                    <img src="../styles/imgs/ico/right.png" border="0"/>
		                 </logic:equal>
		                 <logic:equal name="subanswerVO" property="isright" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
		                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
		                 </logic:equal>
		                 )
	                 </td>
	              </tr>
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
	                       <span style="width:16px;">
		                      <logic:equal name="subquesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                         <img src="../styles/imgs/ico/check.gif" border="0"/>
		                      </logic:equal>
		                   </span>
	                          <% itemFlagMap.put(subquesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print("<font style='font-weight: bold;'>"+itemFlagArr[j]+".)</font>"); %>
	                          &nbsp;<bean:write name="subquesItemVO" property="quesitemcontent"/>
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
	                 <td>
	                    <logic:iteQuesitem itemvoid="subquesItemVO" ordname="subanswerVO" ordprop="itemorderstr" listname="subQuesVO" listprop="itemList" indexId="j">
	                      <span style="margin-right:10px; padding:2px; border: 1px solid #cccccc;">
		                      <logic:equal name="subquesItemVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                         <img src="../styles/imgs/ico/check.gif" border="0"/>
		                      </logic:equal>
	                          <% itemFlagMap.put(subquesItemVO.getQuesitemid().toString(),itemFlagArr[j]); out.print("<font style='font-weight: bold;'>"+itemFlagArr[j]+".)</font>"); %>
	                          &nbsp;<bean:write name="subquesItemVO" property="quesitemcontent"/>
		                   </span>
			            </logic:iteQuesitem>
	              	 </td>
	              </tr>
	              </logic:equal>
	              <tr>
	                 <td>
	                     考生答案:&nbsp;&nbsp;<%=QueshowUtil.getUseranswerFlag(itemFlagMap,subanswerVO.getAnswer()) %>&nbsp;
	                     (
	                     <logic:equal name="subanswerVO" property="isright" value="<%=QuestionConstant.IsRight_right.toString() %>" >
		                    <img src="../styles/imgs/ico/right.png" border="0"/>
		                 </logic:equal>
		                 <logic:equal name="subanswerVO" property="isright" value="<%=QuestionConstant.IsRight_wrong.toString() %>" >
		                    <img src="../styles/imgs/ico/wrong.png" border="0"/>
		                 </logic:equal>
		                 )
	                 </td>
	              </tr>
              </table>
              </div>
              <%questionNO++; %>
              </logic:iterateQues>
              </div>
	          </logic:equal>
	          
	          <logic:equal name="questypeUse" value="<%=QuestionConstant.QuesType_WenDa.toString() %>" >
	          <div>
              <table style="width:90%;padding:3px;text-align: left">
                  <tr>
	                 <td style="align:left;font-weight:bold" colspan="2">参考答案:</td>
	              </tr>
	              <tr>
	                 <td>&nbsp;</td>
	                 <td style="align:left;">
	                     <bean:write name="paperQuesVO" property="answerVO.answertext" filter="false"/>
	                 </td>
	              </tr>
                  <tr>
	                 <td colspan="2" style="align:left;font-weight:bold">考生答案:(得分:<bean:write name="useranswerVO" property="answerscore"/>)</td>
	              </tr>
	              <tr>
	                 <td>&nbsp;</td>
	                 <td style="align:left;">
	                     <bean:write name="useranswerVO" property="answer"/>
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
			  <li><button type="button" onclick="window.close();">关闭窗口</button></li>
		   </ul>
	   </div>
	   
	   </center>
	   
	   <div>&nbsp;</div>
	</html:form>
	</div>
  </body>
</html:html>
