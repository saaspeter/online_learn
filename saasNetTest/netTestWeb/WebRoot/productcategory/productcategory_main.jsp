<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>

<html>
<head>
	<title>产品目录</title>
	<script type="text/javascript">
	   function onclickTree(id,name){     
	      if(id==null||id==""){
	         return;
	      }
          var url = '<%=WebConstant.WebContext %>/productcategory/listproductcategory.do?queryVO.pid='+id; 
          document.getElementById("content").src = url;
	   }
	</script>
</head>
<FRAMESET border=1 id="main" name="main" frameSpacing=1 frameBorder=1 cols=250,* bordercolor="#EEEEEE">
  <FRAME id="left" name="left" marginWidth=0 marginHeight=0 src="toprodcategorytreepage.do?syscode=<%=WebConstant.SysCode_Platform %>&editType=<%=WebConstant.editType_view %>" scrolling="auto">
  <FRAME id="content" name="content" src="<%=WebConstant.WebContext %>/productcategory/listproductcategory.do" scrolling="auto">
</FRAMESET>
<noframes>Need Frameset support</noframes>
<body>

</body>
</html>