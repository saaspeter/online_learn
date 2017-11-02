<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="paperForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="paperForm" property="editType" type="java.lang.Integer"/>
<bean:define id="questypeidUse" name="paperForm" property="questypeidUse" type="java.lang.Long"/>
<bean:define id="paperid_var" name="paperForm" property="vo.paperid" type="java.lang.Long"/>

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
    
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/paper/savePaper.do" method="post">
      <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="%2FnetTest%2Fpaper%2FeditPaper.do%3FqueryVO.paperid%3D<%=paperid_var %>%26questypeidUse%3D<%=questypeidUse %>">
	  <input type="hidden" name="vo.paperid" value='<%=paperid_var %>' />
	  <center>

	  <div id="fieldsTitleDiv">编辑试卷</div>
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
      
      <div>
           <table class="formTable2">
               <tr>
                   <td colspan="6" align="center">&nbsp;
                      <bean:write name="paperForm" property="vo.papername"/>
                   </td>
               </tr>
               <tr>
	               <td>&nbsp;试卷总分：</td>
	               <td>
	                   <bean:write name="paperForm" property="vo.papertotalscore"/>&nbsp;
	               </td>
	               <td>&nbsp;<font color="red">*</font>及格分：</td>
	               <td>
	                   <input type="text" name="vo.paperquascore" value='<bean:write name="paperForm" property="vo.paperquascore"/>' usage="notempty,num-range:1:600" fie="及格分" style="width:50"/>
	                   &nbsp;
	               </td>
	               <td><font color="red">*</font>答题时间：&nbsp;</td>
	               <td>
	                   <input type="text" name="vo.papertime" value='<bean:write name="paperForm" property="vo.papertime"/>' usage="notempty,int-range:1:300" fie="答题时间" style="width:50"/>分
	                   &nbsp;
	               </td>
               </tr>
               <tr>
                   <td>&nbsp;出卷时间：</td>
                   <td><bean:write name="paperForm" property="vo.createtime" format="yyyy-MM-dd"/>&nbsp;</td>
                   <td>出卷单位：&nbsp;</td>
	               <td><bean:write name="paperForm" property="vo.orgname"/>&nbsp;</td>
	               <td>出卷方式：&nbsp;</td>
	               <td><bean:writeSaas name="paperForm" property="vo.genetype" consCode="netTest.PaperConstant.geneType"/>&nbsp;</td>
               </tr>
               <tr>
                   <td>出卷题库：&nbsp;</td>
	               <td colspan="5"><bean:write name="paperForm" property="vo.warenamestr"/>&nbsp;</td>
               </tr>
            </table>
      </div>

      <div>&nbsp;</div>
  
      <!-- 循环显示题型开始 -->          
	  <jsp:include flush="true" page="/paper/paper_content.jsp"></jsp:include>    				
	  <!-- 循环显示题型结束 -->
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');" class="bigButton"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="goUrl('/paper/listPaper.do');return false;" class="bigButton"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  
	  </center>
	  
	  <div id="buttomDiv">&nbsp;</div>
	</html:form>
	</div>
  </body>
</html:html>
