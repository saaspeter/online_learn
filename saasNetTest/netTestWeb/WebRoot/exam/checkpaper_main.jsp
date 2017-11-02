<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.exam.dto.UseranswerQuery,netTest.wareques.constant.QuestionConstant,java.util.HashMap" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="checkPaperForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="quescheckMap" name="checkPaperForm" property="quescheckMap" type="java.util.Map"/>

  <% 
     boolean hasQuesTobeCheck = true;
     if(quescheckMap==null){
        quescheckMap = new HashMap();
     }
     if(quescheckMap.isEmpty()){
        hasQuesTobeCheck = false;
     }
     UseranswerQuery voTemp = null;
   %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>评阅试卷</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript">
         
         function checkQuesPage(quesid,pid,uncheckNum,questypename){
             var testidVar = '<bean:write name="checkPaperForm" property="testVO.testid"/>';
             var paperidVar = '<bean:write name="checkPaperForm" property="paperVO.paperid"/>';
             var testnameVar = '<bean:write name="checkPaperForm" property="testVO.testname"/>';
             goUrl('/exam/checkanswerPage.do?testVO.testid='+testidVar+
                   '&paperVO.paperid='+paperidVar+'&quesid='+quesid+'&pid='+pid+'&uncheckNum='+uncheckNum+
                   '&questypename='+questypename+'&testVO.testname='+testnameVar);
         }
       
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="checkFormId" action="/exam/manaualCheckPaper.do" method="post">
      <input type="hidden" name="paperVO.paperid" value='<bean:write name="checkPaperForm" property="paperVO.paperid"/>'/>
      <input type="hidden" name="testVO.testid" value='<bean:write name="checkPaperForm" property="testVO.testid"/>'/>
	  
	  <center>

	  <div class="titleBar">
          <bean:write name="checkPaperForm" property="testVO.testname"/><br/>
          <span style="font-size: smaller">
          (试卷：<bean:write name="checkPaperForm" property="paperVO.papername"/>)
          </span>
      </div> 
      
      <br>
      <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
      <br>
      <% int questionNO = 1; %>
	  
      <!-- 循环显示题型开始 -->          
	  <logic:notEmpty name="checkPaperForm" property="paperVO.questypeList">
	     <logic:iterate id="paperQuesTypeVO" name="checkPaperForm" property="paperVO.questypeList" type="netTest.paper.vo.Paperquestype"> 

	       <div>
	          <table width="100%">
	             <tr style="font-size: medium;font-weight: bold">            
	                <td>
	              	   <bean:write name="paperQuesTypeVO" property="questypename"/>(总分:
	                   <bean:write name="paperQuesTypeVO" property="patternscore"/>，每题
	                   <bean:write name="paperQuesTypeVO" property="quesscore"/>分)
	                   &nbsp;&nbsp;<bean:write name="paperQuesTypeVO" property="questypenote"/>
	                   &nbsp;&nbsp;
	                   <font style="color:blue;">
                       <%if(QuestionConstant.Queschecktype_auto.equals(QuestionConstant.getQueschecktype(paperQuesTypeVO.getQuestype()))){ %>
                         (题型所有题目自动评阅完毕)
                       <%}else if(QuestionConstant.Queschecktype_both.equals(QuestionConstant.getQueschecktype(paperQuesTypeVO.getQuestype()))){ %>
                         (题型所有题目自动评阅完毕，可以手动修改评阅结果)
                       <%} %>
                       </font>
	                </td>
	             </tr>
	          </table>     
	          <%if(!QuestionConstant.Queschecktype_auto.equals(QuestionConstant.getQueschecktype(paperQuesTypeVO.getQuestype()))){ %>  
	          <logic:notEmpty name="paperQuesTypeVO" property="paperquesList">
	          <logic:iterate id="paperQuesVO" name="paperQuesTypeVO" property="paperquesList" indexId="ind" type="netTest.paper.vo.Paperques">
	          <table style="width:99%;padding:3px">
	             <tr>
	                <td style="padding:5px;" bgcolor="#ECF5FF">
	                    <% out.print(questionNO+").&nbsp;");questionNO++; %><bean:writeTK name="paperQuesVO" property="question" cutStr="false" questypeProp="questype" filter="false" transfer="true" showIndentWay="2" inputName="kongge"/>
	                </td>
	            </tr>
	            <%if(quescheckMap.get(paperQuesVO.getQuesid())!=null
	                 &&QuestionConstant.Comptype_whole.equals(paperQuesVO.getComptype()))
	              { 
	                 voTemp = (UseranswerQuery)quescheckMap.get(paperQuesVO.getQuesid());
	                 if(QuestionConstant.QuesType_TianKong.equals(paperQuesVO.getQuestype())){
	                    voTemp.setQuantityUnCheck(voTemp.getQuantityPreCheck());
	                 }
	                 if(hasQuesTobeCheck&&voTemp.getQuantity()!=null&&voTemp.getQuantityChecked()!=null
	                    &&(voTemp.getQuantity()==voTemp.getQuantityChecked())){
	                    hasQuesTobeCheck = false;
	                 }
	            %>
	            <tr>
	               <td style="text-align: right">
	                   (共有<%=voTemp.getQuantity() %>题需评阅，已评阅<%=voTemp.getQuantityChecked() %>题，
	                       <font style="color:red;">正在评阅<%=voTemp.getQuantityChecking() %>题，待评阅<%=voTemp.getQuantityUnCheck() %>题</font>)
	                   &nbsp;&nbsp;&nbsp;
	                   <%if(voTemp.getQuantityChecking()>0||voTemp.getQuantityUnCheck()>0){ %>
	                   <button type="button" onclick="checkQuesPage('<%=paperQuesVO.getQuesid() %>','','<%=voTemp.getQuantityUnCheck() %>','<bean:write name="paperQuesTypeVO" property="questypename"/>');return false;">评阅该题</button>
	                   <%} %>
	               </td>
	            </tr>
	            <%}else { %>
	            <tr>
	               <td style="text-align: right; font-weight: bold; color:blue;">(没有需要评阅的用户答案)</td>
	            </tr>
	            <%} %>
	            <!-- 显示题目图片 -->
	            <logic:equal name="paperQuesVO" property="filetype" value="<%=QuestionConstant.FileType_pict.toString() %>">
		            <tr>
		               <td align="left" style="padding-left: 50px;">
		                   <img src='<%=WebConstant.FileContext %><bean:write name="paperQuesVO" property="fileurl"/>' height="100">
		               </td>
		            </tr>
		        </logic:equal>
	            		    
		      </table>
		      <br>

	          </logic:iterate>
	          </logic:notEmpty>
	          <%} %>
	          <br>    
	       </div>

	     </logic:iterate>  
	  </logic:notEmpty>          
			
	  <div id="functionBarButtomDiv">
	  	 <ul>
	  	    <%if(!hasQuesTobeCheck){ %>
	  	    <li><font style="color:blue;font-weight: bold;">所有试题评阅完毕,</font><button type="button" onclick="goUrl('/exam/finishcheckpaper.do?testVO.testid=<bean:write name="checkPaperForm" property="testVO.testid"/>');" class="bigButton">结束试卷评阅</button></li>
		    <%} %>
		    <li><button type="button" onclick="goUrl('/exam/listTodoTestinfo.do');" class="bigButton">返回</button></li>
		 </ul>
	  </div>
			
	<!-- 循环显示题型结束 -->
	</center>
	</html:form>
	</div>
  </body>
</html:html>
