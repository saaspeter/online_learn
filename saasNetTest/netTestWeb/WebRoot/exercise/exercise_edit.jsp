<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="exerciseForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="exerciseForm" property="editType" type="java.lang.Integer"/>

  <% boolean isDisable=false;
    if(editType!=null&&editType.intValue()!=WebConstant.editType_edit){
      isDisable = true;
    } 
   %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>练习预览</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/javascript">
              
    </script>
  </head>
  
  <body>
    
	<div class="editPage">
	<html:form styleId="editForm" action="/exercise/editExercise.do" method="post">
	  <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="exerciseForm" property="backUrl"/>">
      <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="exerciseForm" property="backUrlEncode"/>">
	  <input type="hidden" name="vo.exerid" value="<bean:write name="exerciseForm" property="vo.exerid"/>" />
	  <input type="hidden" name="questypeUse" value="<bean:write name="exerciseForm" property="questypeUse"/>" />
	  
	  <center>
	  <%if(!isDisable){ %>
	  <div id="fieldsTitleDiv">编辑练习</div>
	  <%} %>
	              
      <div style="width:95%;padding-top:2px;margin-top:20px;background-color:#DCEAFC;text-align:center">
          <font style="font-weight: bold;">练习基本属性</font>
      </div> 
      
      <div style="width:95%;">
           <table class="formTable" >
               <tr>
                   <td colspan="4" align="center">&nbsp;
                      <bean:write name="exerciseForm" property="vo.exername"/>
                      <font style="font-size: smaller;">(更新时间：<bean:write name="exerciseForm" property="vo.moditime" format="yyyy-MM-dd"/>)</font>
                   </td>
               </tr>
               <tr>
                   <td style="text-align: right;">创建人：&nbsp;</td>
	               <td style="text-align: left;"><bean:write name="exerciseForm" property="vo.creatorname"/>&nbsp;</td>
	               <td style="text-align: right;">课程章节：&nbsp;</td>
	               <td style="text-align: left;">
	                   <bean:write name="exerciseForm" property="vo.contentcatename"/>
	                   <!-- 
	                   <input type="hidden" id="contentcateidId" name="vo.contentcateid" value="<bean:write name="exerciseForm" property="vo.contentcateid"/>" >
             		   <input type="text" id="contentcatenameId" name="vo.contentcatename" class="readonly" value='<bean:write name="exerciseForm" property="vo.contentcatename"/>' readonly="readonly" style="width:150px"/>
       			 	   <img src="../styles/imgs/ico/search.gif" alt="选择课程章节" onclick="selectContcate('<bean:write name="exerciseForm" property="vo.productbaseid"/>')">
       			 	   <img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate()">
	                    -->
	               </td>
               </tr>
               
            </table>
      </div>
      <br>
      <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
      <div>&nbsp;</div>
  
      <!-- 循环显示题型开始 -->          
	      
	      <jsp:include flush="true" page="/exercise/exercise_content.jsp"></jsp:include>    
						
	  <!-- 循环显示题型结束 -->
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="goUrl('/learncont/listLearncontent.do?queryVO.productbaseid=<bean:write name="exerciseForm" property="vo.productbaseid"/>&queryVO.contentcateid=<bean:write name="exerciseForm" property="vo.contentcateid"/>');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  </center>
	  </html:form>
	  <div id="buttomDiv">&nbsp;</div>
      
	</div>
  </body>
</html:html>
