<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platform.constant.SysfunctionitemConstant,commonTool.constant.CommonConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>系统功能</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../../styles/css/edit.css">
    <script language=JavaScript src="../../styles/script/pageAction.js"></script>

  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/sysadmin/sysfunctionitem.do?method=save" method="post">
    <input id="backUrl" type="hidden" name="backUrl" value="<bean:write name="sysfunctionitemForm" property="backUrl"/>">
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="sysfunctionitemForm" property="backUrlEncode"/>">	
    	 <input type="hidden" name="vo.functioncode" value="<bean:write name="sysfunctionitemForm" property="vo.functioncode"/>">
	  <div id="functionLineDiv">
		  <div id="functionBarTopDiv">
			  <ul>
				 <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="netTest.commonpage.save"/></button></li>
				 <li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
			  </ul>
		  </div>
	  </div>
	
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
	     <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>
              
		    <li>
			   <div class="fieldLabel">功能编码:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.functioncode"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">功能名称:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.functionname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">所属系统:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.systemname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">支付方式:</div>
			   <div class="field"> 
			      <html:select name="sysfunctionitemForm" property="vo.paytype" style="width:300px;">
					 <html:optionsSaas consCode="Common.PayTypeConstant.PayType"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			


		    <li>
			   <div class="fieldLabel">价格:</div>
			   <div class="field"> 
			     <input type="text" name="vo.cost" value="<bean:write name="sysfunctionitemForm" property="vo.cost"/>"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">功能类型:</div>
			   <div class="field"> 
			      <html:select name="sysfunctionitemForm" property="vo.ismust" style="width:300px;">
			         <html:option value="<%=String.valueOf(CommonConstant.yes)%>">必须功能</html:option>
			         <html:option value="<%=String.valueOf(CommonConstant.no)%>">可选功能</html:option>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">生效日期:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.startdate" format="yyyy-MM-dd"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

		    <li>
			   <div class="fieldLabel">有效性:</div>
			   <div class="field"> 
			   	 <html:select name="sysfunctionitemForm" property="vo.status" style="width:300px;">
	                <option <logic:present name="sysfunctionitemForm" property="queryVO.status"><logic:equal name="sysfunctionitemForm" property="queryVO.status" value="<%=String.valueOf(CommonConstant.Status_valide)%>">selected="selected"</logic:equal></logic:present> value="<%=CommonConstant.Status_valide%>">生效</option>
	                <option <logic:present name="sysfunctionitemForm" property="queryVO.status"><logic:equal name="sysfunctionitemForm" property="queryVO.status" value="<%=String.valueOf(CommonConstant.Status_invalide)%>">selected="selected"</logic:equal></logic:present>  value="<%=CommonConstant.Status_invalide%>">失效</option>
		         </html:select>
			     
			   </div>
			   <div class="fieldDesc"></div>
			</li>	

		    <li>
			   <div class="fieldLabel">描述:</div>
			   <div class="field"> 
			     <bean:write name="sysfunctionitemForm" property="vo.funcdesc"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		 </ul>
	  </div>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');return false;"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="getAndToUrl('backUrl');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
  </body>
</html:html>
