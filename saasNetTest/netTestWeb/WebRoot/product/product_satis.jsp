<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="productdesc" name="productForm" property="vo.productdesc" type="java.lang.Long"/>
    <title>课程详细信息</title>

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
    <script language=JavaScript src="../styles/script/pageAction.js"></script>

  </head>
  
 <body>
	<div class="fieldDisDiv" style="height: 100px;">
	   <ul>
		   <li>
			   <div class="fieldLabel">课程文档资源数量:</div>
			   <div class="field"> 
			      <bean:write name="productForm" property="vo.learncontdocnum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	  	    <li>
			   <div class="fieldLabel">课程多媒体资源数量:</div>
			   <div class="field"> 
			      <bean:write name="productForm" property="vo.learncontmedianum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">课程练习数量:</div>
			   <div class="field"> 
			      <bean:write name="productForm" property="vo.exercisenum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<li>
			   <div class="fieldLabel">完成考试数量:</div>
			   <div class="field"> 
			      <bean:write name="productForm" property="vo.testinfonum"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>

	   </ul>
	  
	</div>

  </body>
</html:html>
