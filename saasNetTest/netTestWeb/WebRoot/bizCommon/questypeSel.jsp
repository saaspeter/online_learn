<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,java.util.List,commonWeb.security.authentication.UserinfoSession,commonTool.biz.logic.ConstantInf,commonTool.biz.logicImpl.ConstantLogicImpl,netTest.wareques.constant.QuestionConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>
<%
   UserinfoSession loginInfo = null;
   if(session.getAttribute("loginInfo")==null)
      response.sendRedirect("");
   else
      loginInfo = (UserinfoSession)session.getAttribute("loginInfo");
   String jsSuffix = loginInfo.getJsSuffix();
   ConstantInf inf = ConstantLogicImpl.getInstance();
   List questypeList = inf.getLabelList(QuestionConstant.QuesType_ConsCode, loginInfo.getUseLocaleid(request));
   request.setAttribute("questypeList",questypeList);
   String questypeStr = "";
   if(request.getParameter("questypeStr")!=null)
      questypeStr = request.getParameter("questypeStr");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>选择题目类型</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/movediv.js"></script>
	<script type="text/javascript">
        function doSelect(){
          var checkArr = document.getElementsByName("keys");
          var num = 0;
          var id = "";
          var name = "";
          var ids = "";
          var names = "";
          for (var i=0;i<checkArr.length;i++){
			 if (checkArr[i].checked == true){
				id = checkArr[i].value;
				name = document.getElementById(checkArr[i].id+"Name").value;
				num++;
				ids = ids+id+",";
				names = names+name+";";
			 }
		  }
		  if(num==0){
		     showMessagePage(getMessage("selectOneMsg"));
		     return;
		  }else{
		     ids = ids.substring(0,ids.length-1);
		     names = names.substring(0,names.length-1);
		     if(typeof(parent.selQuestypeCB)!="undefined"){
		        parent.selQuestypeCB(ids,names);
		     }
		  }
        }
       
	</script>
  </head>
  
  <body>
  <div class="listPage">

  <div id="displayMessDiv">
       <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
  </div>

  <div id="fieldsTitleDiv">请选择题目类型</div>

  <div id="DataTableDiv">
      <table>
          <logic:notEmpty name='<%="questypeList" %>' >
          <logic:iterate id="labelQuestypeVO" name='<%="questypeList" %>' indexId="ind">
          <% if(((ind+1)%3)==1){ %>
          <tr>
          <% } %>
              <td>
                 <html:checkbox tagName="keys" styleId='<%="questypeId"+ind %>' nameVal="labelQuestypeVO" propertyVal="value" checkStr="<%=questypeStr %>">
                    <label for="questypeId<%=ind %>">
                       <bean:write name="labelQuestypeVO" property="label"/>
                       <input type="hidden" id='<%="questypeId"+ind+"Name" %>' value='<bean:write name="labelQuestypeVO" property="label"/>'/>
                    </label>
                 </html:checkbox>&nbsp;&nbsp;&nbsp;
              </td>
          <% if(((ind+1)%3)==0||((ind+1)==questypeList.size())){ %>
          </tr>
          <%} %>
          </logic:iterate>
          </logic:notEmpty>
      </table>

  </div>
  <div id="functionBarButtomDiv">
		  <ul>
			 <li><button type="button" onclick="doSelect();return false;">确定</button></li>
			 <li><button type="button" onclick="parent.clearDiv();return false;">取消</button></li>
		  </ul>
  </div>

  </div>
   
  </body>
</html:html>
