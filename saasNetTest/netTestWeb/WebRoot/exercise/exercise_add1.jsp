<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<bean:define id="jsSuffix" name="exerciseForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="sessionProductname" name="exerciseForm" property="sessionProductname" type="java.lang.String"/>
<bean:define id="sessionProductid" name="exerciseForm" property="sessionProductid" type="java.lang.Long"/>
<bean:define id="quetyporiList" name="exerciseForm" property="quetyporiList" type="java.util.List"/>
<% if(sessionProductid==null||sessionProductid.longValue()==-1l){sessionProductname="";} %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>生成练习</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/javascript" src="../styles/script/movediv.js"></script>
    <script type="text/javascript" src="../styles/script/utiltool.js"></script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/exercise/addExercise.do" method="post">
     <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="exerciseForm" property="backUrl"/>">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="exerciseForm" property="backUrlEncode"/>">
	 
	  <div id="fieldsTitleDiv">
	      新增练习:设置基本属性
	  </div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div style="width:95%;float:left;margin-left:20px;"> 
      
	      <div>
	           <table align="center" cellpadding="0" cellspacing="0" class="formTable2" width="90%">
	               <tr>
	                    <td align="right" ><font color="red">*</font>名称:</td>
	                    <td colspan="3"><input id="exernameId" type="text" name="vo.exername" value="<bean:write name="exerciseForm" property="vo.exername"/>" usage="notempty,max-length:90" fie="练习名称" style="width:80%"/></td>
	                </tr>
	                
	                <tr>
	                    <td align="right"><font color="red">*</font>所属产品:</td>
	                    <td>
	                        <input type="text" id="productnameId" name="vo.productname" value="<%=sessionProductname %>" readonly="readonly" onclick="selectProd(this)" usage="notempty" fie="所属产品" style="width:250px"/> 
	  			            <input type="hidden" id="productidId" name="vo.productbaseid" value="<%=(sessionProductid==null)?"":sessionProductid.toString() %>"/>
				        </td>
				        <td align="right">所属章节:</td>
	                    <td>
	                        <input type="hidden" id="contentcateidId" name="vo.contentcateid" value="<bean:write name="exerciseForm" property="vo.contentcateid"/>" >
             				<input type="text" id="contentcatenameId" name="vo.contentcatename" class="readonly" value="<bean:write name="exerciseForm" property="vo.contentcatename"/>" readonly="readonly" />
       			 			<img src="../styles/imgs/ico/search.gif" alt="选择章节" onclick="selectContcate('<bean:write name="exerciseForm" property="sessionProductid"/>')">
       			 			<img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate()">
	                    </td>
	                </tr>
	                     	                
	                <tr>
	                  <td align="right" valign="top"><font color="red">*</font>选择题型:</td>
	                  <td colspan="3">
	                      <logic:notEmpty name="exerciseForm" property="quetyporiList">
	                      <table>
				          <logic:iterate id="labelQuestypeVO" name="exerciseForm" property="quetyporiList" indexId="ind">
				          <% if(((ind+1)%4)==1){ %>
				          <tr>
				          <% } %>
				              <td>
				                 <html:checkbox tagName="keys" styleId='<%="questypeId"+ind %>' nameVal="labelQuestypeVO" propertyVal="value" >
				                    <label for='<%="questypeId"+ind %>'>
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
	                    <td>备注说明:</td>
	                    <td colspan="3">
	                        <textarea id="exerdescId" name="vo.exerdesc" rows="" cols="60%" usage="max-length:250" fie="备注说明"><bean:write name="exerciseForm" property="vo.exerdesc"/></textarea>
	                    </td>
	                </tr>
	           </table>
	      </div>
	  </div>    
      <div style="width:30px;float:right">&nbsp;</div>
	  			
	  <div id="functionBarButtomDiv">
	  	 <ul id="bottomDiv1">
		    <li><button type="button" onclick="checkandsubmit();">下一步: 选择题目</button></li>
		    <li>&nbsp;&nbsp;&nbsp;</li>
			<li><button type="button" onclick="goUrl('/learncont/listLearncontent.do?queryVO.productbaseid=<bean:write name="exerciseForm" property="vo.productbaseid"/>&queryVO.contentcateid=<bean:write name="exerciseForm" property="vo.contentcateid"/>');return false;">返回列表</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv">&nbsp;</div>
	</html:form>
	</div>
	<script type="text/javascript">
	 <!--      
	   function checkandsubmit(){
	       clearMessagePage();
	       var errNum = 0;
	       var errmsg = "";
	       var exernameObj = document.getElementById("exernameId");
	       var productnameObj = document.getElementById("productnameId");
	       var exerdescObj = document.getElementById("exerdescId");
	       var rtnArr1 = checkElement(exernameObj, 0);
	       if(!rtnArr1[1]){
			  errNum++;
			  errmsg = rtnArr1[2] + "<br>";
		   }
	       var rtnArr3 = checkElement(productnameObj, 0);
	       if(!rtnArr3[1]){
			  errNum++;
			  errmsg = errmsg + rtnArr3[2] + "<br>";
		   }
	       var rtnArr4 = checkElement(exerdescObj, 0);
	       if(!rtnArr4[1]){
			  errNum++;
			  errmsg = errmsg + rtnArr4[2] + "<br>";
		   }
	       
	       var questypeVal = getCheckedValue("keys");
	       if(questypeVal==null||questypeVal==''){
	          errmsg = errmsg + "请选择要包含的题型"+"<br>";
	          errNum++;
	       }
	       if(errNum>0){
	          showMessagePage(errmsg);
	          return;
	       }
	       submitForm('editForm');
	   }
	   
     //-->
  </script>
  </body>
</html:html>
