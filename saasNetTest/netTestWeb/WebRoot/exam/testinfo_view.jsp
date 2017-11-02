<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="testinfoForm" property="jsSuffix" type="java.lang.String"/>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>考试信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	
  </head>
  
  <body>
	<div class="editPage">
	
	  <div id="fieldsTitleDiv"> 
	      <bean:write name="testinfoForm" property="vo.testname"/>
	  </div>

	  <div id="fieldDisDiv">
	     <ul>		
			<li>
			   <div class="fieldLabel">考试科目:</div>
			   <div class="field"> 
			       <bean:write name="testinfoForm" property="vo.productname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">考试开放类型:</div>
			   <div class="field"> 
			       <bean:writeSaas name="testinfoForm" property="vo.opentype" consCode="netTest.TestinfoConstant.OpenType"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">考试开始时间:</div>
			   <div class="field"> 
			     <bean:write name="testinfoForm" property="vo.teststartdate" format="yyyy-MM-dd HH:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		    <li>
			   <div class="fieldLabel">考试结束时间:</div>
			   <div class="field"> 
			     <bean:write name="testinfoForm" property="vo.testenddate" format="yyyy-MM-dd HH:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">考卷名称:</div>
			   <div class="field"> 
			       <bean:writeSaas name="testinfoForm" property="vo.papername"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">考卷类型:</div>
			   <div class="field"> 
			       <bean:writeSaas name="testinfoForm" property="vo.isdynamicpaper" consCode="netTest.PaperConstant.geneType"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

			<li>
			   <div class="fieldLabel">答卷时间(分钟):</div>
			   <div class="field"> 
			       <bean:write name="testinfoForm" property="vo.papertime"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<!-- 
			<li>
			   <div class="fieldLabel">*考生在考试中可否暂停:</div>
			   <div class="field"> 
			      <html:select name="testinfoForm" property="vo.testcanstop" style="width:250px">
			         <html:optionsSaas consCode="netTest.TestinfoConstant.testcanstop"/>
			      </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			 
			<li>
			   <div class="fieldLabel">可以中断的时间(分钟):</div>
			   <div class="field"> 
			     <input type="text" name="vo.canstoptime" value="<bean:write name="testinfoForm" property="vo.canstoptime"/>" style="width:250px"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			-->

		    <li>
			   <div class="fieldLabel">考试状态:</div>
			   <div class="field"> 
			       <bean:writeSaas name="testinfoForm" property="vo.teststatus" consCode="netTest.TestinfoConstant.Teststatus"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		    <li>
			   <div class="fieldLabel">创建单位:</div>
			   <div class="field"> 
			      <bean:write name="testinfoForm" property="vo.createorgname"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">创建人:</div>
			   <div class="field"> 
			      <bean:write name="testinfoForm" property="vo.createusername"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">创建时间:</div>
			   <div class="field"> 
			       <bean:write name="testinfoForm" property="vo.createtime" format="yyyy-MM-dd HH:mm:ss"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
             
            <li>
			   <div class="fieldLabel">考试备注:</div>
			   <div class="field"> 
			       <bean:write name="testinfoForm" property="vo.testnotes"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
            
		 </ul>
	  </div>
	  <br>
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="parent.clearDiv();">关闭</button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	
	</div>
  </body>
</html:html>
