<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant"%>

<html>
<head>
	<title>产品目录</title>
	<script type="text/javascript">
	   function onclickTree(id,name){     
	      if(id==null||id==""){
	         return;
	      }
          var url = '<%=WebConstant.WebContext %>/productcategory/productcategory.do?method=list&queryVO.pid='+id; 
          document.getElementById("content").src = url;
	  }
	</script>
</head>
<FRAMESET border=1 id="main" name="main" frameSpacing=1 frameBorder=1 cols=150,* bordercolor="#EEEEEE">
  <FRAME id="left" name="left" marginWidth=0 marginHeight=0 src="productcategory_tree.jsp" scrolling="auto">
  <FRAME id="content" name="content" src="<%=WebConstant.WebContext %>/productcategory/productcategory.do?method=list" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>