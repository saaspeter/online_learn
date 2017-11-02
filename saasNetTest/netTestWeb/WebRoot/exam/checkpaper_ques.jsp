<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.wareques.constant.QuestionConstant,netTest.paper.vo.Paperques,netTest.util.QueshowUtil" %>
<jsp:directive.page import="netTest.exam.constant.UseranswerConstant"/>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="checkPaperForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="checkPaperForm" property="editType" type="java.lang.Integer"/>
<bean:define id="quesVO" name="checkPaperForm" property="quesVO" type="netTest.paper.vo.Paperques"/>
<bean:define id="quesid" name="checkPaperForm" property="quesid" type="java.lang.Long"/>
<bean:size id="answerSize" name="checkPaperForm" property="useranswerList"/> 
      
  <% int showMeidaType = 0; 
     String[] ansArr = null;
     char[] isrightArrTK = null;
     int kongsize = 0;
     float score = quesVO.getPaperquesscore();
  %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>评阅试题</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript" src="../styles/script/swfobject.js"></script>
    <script language="javascript">
	<!--
        function smtCheckanswer(questype,kongsize,actiontype){
           if(confirm('确定提交?')){
               var answersize = <%=answerSize %>;
	           var quesscore = parseFloat('<%=score %>');
	           if(questype=='<%=QuestionConstant.QuesType_TianKong %>'){
	              var checkvar = '';
	              var checkkong = '';
	              for(var i=0;i<answersize;i++){
	                  for(var j=0;j<kongsize;j++){
	                      checkkong = getCheckedValue('ans['+i+']kong'+j);
	                      if(checkkong!='<%=QuestionConstant.IsRight_right %>'
	                         &&checkkong!='<%=QuestionConstant.IsRight_wrong %>')
	                      { 
	                         checkkong = '<%=QuestionConstant.IsRight_wrong %>';
	                      }
	                      checkvar = checkvar+checkkong;
	                  }
	                  document.getElementById("userscore["+i+"]Id").value=checkvar;
	              }
	           }
	           document.getElementById("tourltypeID").value = actiontype;
	           submitForm("editForm");
           }
        }
        
        function backTo(){
           if(confirm('是否直接返回而不保存评阅分数?')){
               document.getElementById("editForm").action = "manaualCheckPaper.do";
               document.getElementById("editForm").submit();
           }
        }
       	
	//-->
	</script>
  </head>
  
  <body> 
	<div class="editPage">
	  <html:form styleId="editForm" action="/exam/saveCheckResult.do" method="post">
	  <input type="hidden" name="testVO.testid" value="<bean:write name="checkPaperForm" property="testVO.testid"/>"/>
	  <input type="hidden" name="quesVO.questype" value="<%=quesVO.getQuestype() %>"/>
	  <input type="hidden" name="quesscore" value="<%=quesVO.getPaperquesscore() %>"/>
	  <input type="hidden" id="tourltypeID" name="tourltype" value="<bean:write name="checkPaperForm" property="tourltype"/>"/>
	  <input type="hidden" name="quesid" value="<bean:write name="checkPaperForm" property="quesid"/>"/>
	  <input type="hidden" name="pid" value="<bean:write name="checkPaperForm" property="pid"/>"/>
	  <input type="hidden" name="paperVO.paperid" value="<bean:write name="checkPaperForm" property="paperVO.paperid"/>"/>
	 
	  <center style="width:99%">  
	  <div class="titleBar">
          <bean:write name="checkPaperForm" property="testVO.testname"/>
      </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div style="text-align:left; medium;font-weight: bold;padding:5px;">
	      <bean:write name="checkPaperForm" property="questypename"/>(每题<bean:write name="quesVO" property="paperquesscore"/>分)
	  </div>
	  
	  <div>
          <table style="padding:3px; width:100%;">
             <tr>
                <td style="padding:5px;" bgcolor="#ECF5FF">
                   <%if(QuestionConstant.QuesType_TianKong.equals(quesVO.getQuestype())){ %>
                   <bean:writeTK name="quesVO" property="question" filter="false" transfer="true" showIndentWay="2"/>
                   <%}else{ %>
                   <bean:write name="quesVO" property="question" />
                   <%} %>
                </td>
             </tr>
            <!-- 显示题目图片 -->
            <%if(QuestionConstant.FileType_pict.equals(quesVO.getFiletype())){ %>
	            <tr>
	               <td align="left" style="padding-left: 50px;">
	                   <img src="<%=WebConstant.FileContext+quesVO.getFileurl() %>" height="100">
	               </td>
	            </tr>
	        <% }else if(QuestionConstant.FileType_voice.equals(quesVO.getFiletype())){
	             showMeidaType = 20;
	           }
	         %>
            
            <% if(showMeidaType!=0){ %>
                <tr>
                   <td align="left" id="container0" style="padding-left: 50px;">
                      <script type="text/javascript">
						 var s1 = new SWFObject("../styles/player.swf","container0","328","<%=showMeidaType %>","9","#123456");
						 s1.addParam("allowfullscreen","false");
						 s1.addParam("allowscriptaccess","always");
						 s1.addParam('flashvars','file=<%=WebConstant.FileContext+quesVO.getFileurl() %>');
						 s1.addVariable("backcolor","FF0000");
						 s1.write("container0");
				      </script>
			       </td>
			    </tr>
            <% showMeidaType=0; } %>
          </table>
      </div>
	  
	  <%if(quesVO.getSubquesList()!=null&&quesVO.getSubquesList().size()>0){ 
	       Paperques subvo = null;
	       for(int i=0;i<quesVO.getSubquesList().size();i++){
	           subvo = (Paperques)quesVO.getSubquesList().get(i);
	           if(subvo.getQuesid().equals(quesid)){
	  %>
	        <div>
                <bean:write name="subvo" property="question" />
            </div>
            <div style="padding-top:2px;margin-top:10px;background-color:#DCEAFC"><font style="font-weight: bold;">参考答案</font></div>
			<div style="margin:2px">
			    <bean:writeSaas name="checkPaperForm" property="subvo.answer"/>
		    </div>
      <%}}}%>
      
      <%if(!QuestionConstant.QuesType_TianKong.equals(quesVO.getQuestype())){ %>
      <div style="padding-top:2px;margin-top:20px;background-color:#DCEAFC;font-size:medium;text-align:left;font: bold; ">参考答案</div>
	  <div style="padding-top:2px;margin-top:20px;text-align: left">
	      <bean:write name="checkPaperForm" property="quesVO.answer"/>
	  </div>
	  <%} %>
	  
	  <div style="padding-top:2px;margin-top:20px;background-color:#DCEAFC;font-size:medium;text-align:left;font: bold; ">
            考生答案
      </div>
      
      <logic:present name="checkPaperForm" property="useranswerList">
          <%if(QuestionConstant.QuesType_TianKong.equals(quesVO.getQuestype())){ %>
          <div style="text-align: left">
              <logic:iterate id="useransVO" name="checkPaperForm" property="useranswerList" indexId="ind" type="netTest.exam.vo.Useranswer">
              <% kongsize = QueshowUtil.getQuesAnswerCountTK(quesVO.getQuestion());
                 ansArr = QueshowUtil.getQuesAnswerTK(useransVO.getAnswer());
                 if(useransVO.getIsright()!=null&&useransVO.getIsright().trim().length()>0){
                	 isrightArrTK = useransVO.getIsright().toCharArray();
                 }else {
                	 isrightArrTK = new char[kongsize];
                 }
                 
                 if(kongsize>0){ 
                	if(ansArr==null){
                		ansArr = new String[kongsize];
                	}
               %>
              <div style="margin-top: 30px;background-color: #eeeeee">
                                      考生<%=ind+1 %>答案：<%if(UseranswerConstant.ExamineStatus_Judging.equals(useransVO.getExaminestatus())){ %>
              <font style="color: red;">[该考生的答案正被其他阅卷人员评阅]</font> <%} %>
              </div>
              <div>
              <table cellspacing="5px">
              <tr>
                 <%for(int i=0;i<kongsize;i++){ 
                      if(ansArr[i]==null||ansArr[i].trim().length()==0){
                         ansArr[i] = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";  
                      }
                 %>
                   <td bgcolor="#ECF5FF"><span>
                       <%="<u>"+ansArr[i]+"</u>" %>&nbsp;&nbsp;
                       (<input type="radio" name="ans[<%=ind %>]kong<%=i %>" value="<%=QuestionConstant.IsRight_right.toString() %>"  
                         <% if(QuestionConstant.IsRight_right.toString().equals(String.valueOf(isrightArrTK[i]))){out.print("checked='checked'");} %>
                       />正确
                       <input type="radio" name="ans[<%=ind %>]kong<%=i %>" value="<%=QuestionConstant.IsRight_wrong.toString() %>" 
                         <% if(QuestionConstant.IsRight_wrong.toString().equals(String.valueOf(isrightArrTK[i]))){out.print("checked='checked'");} %>
                       />错误)
                       <input type="hidden" id="userscore[<%=ind %>]Id" name="userscore[<%=ind %>].isright" value="<bean:write name="useransVO" property="isright"/>" />
                       <input type="hidden" name="userscore[<%=ind %>].useranswerid" value="<bean:write name="useransVO" property="useranswerid"/>"/>
                       <input type="hidden" name="userscore[<%=ind %>].examinestatus" value="<bean:write name="useransVO" property="examinestatus"/>"/>
                   </span></td>
                   <td>&nbsp;&nbsp;</td>
                   <%} %>
              </tr>
              </table>
              </div>
              <%} %>
              </logic:iterate>
          </div>
          <%}else{ %>
          <logic:iterate id="useransVO" name="checkPaperForm" property="useranswerList" indexId="ind2" type="netTest.exam.vo.Useranswer">
              <div style="margin-top: 30px;text-align: left;background-color: #eeeeee">
                       考生<%=ind2+1 %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;得分：
                   <input id="userscore[<%=ind2 %>]Id" name="userscore[<%=ind2 %>].answerscore" value="<bean:write name="useransVO" property="answerscore"/>" usage="notempty,num-range:0:<%=score %>" fie="考生分数"/>
                   <input type="hidden" name="userscore[<%=ind2 %>].useranswerid" value="<bean:write name="useransVO" property="useranswerid"/>"/>
                   <input type="hidden" name="userscore[<%=ind2 %>].examinestatus" value="<bean:write name="useransVO" property="examinestatus"/>"/>
                   <%if(UseranswerConstant.ExamineStatus_Judging.equals(useransVO.getExaminestatus())){ %>
              <font style="color: red;">[该考生的答案正被其他阅卷人员评阅]</font>    <%} %>
              </div>
              <div style="background-color: #ECF5FF;margin: 3px;text-align: left">
                  答案：<bean:write name="useransVO" property="answer"/>&nbsp;&nbsp;&nbsp;
              </div>
          </logic:iterate>
          <%} %>
      </logic:present>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
	  	    <li><button type="button" onclick="smtCheckanswer('<%=quesVO.getQuestype() %>','<%=kongsize %>','1');return false;">提交并继续评阅</button></li>
	  	    <li><button type="button" onclick="smtCheckanswer('<%=quesVO.getQuestype() %>','<%=kongsize %>','0');return false;">提交，返回</button></li>
		    <li><button type="button" onclick="backTo();return false;">撤销返回</button></li>
		 </ul>
	  </div>
	  
	  <center>
	  
	  <br>
	  <div id="buttomDiv"></div>
	  </center>
	  </html:form>
	</div>
	
  </body>
</html:html>
