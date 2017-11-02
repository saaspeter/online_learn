<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant"%>

<html>
<head>
	<title>学习内容</title>
	<script type="text/javascript">
	      window.moveTo(0,0);  
		  window.resizeTo(screen.width,screen.height);  
	</script>
</head>

<FRAMESET id="main" name="main" cols="*,65" framespacing="3" border="6" frameborder="1" scrolling="no">
  <FRAME id="left" name="content" src="<%=WebConstant.WebContext %>/learncont/viewLearncontent.do?vo.contentid=<%=request.getParameter("learncontentid") %>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="1" resize=yes>
  <FRAME id="content" name="comment" src="<%=WebConstant.WebContext %>/social/listComments.do?queryVO.objecttype=learncontent&queryVO.objectid=<%=request.getParameter("learncontentid") %>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="1" resize=yes>
</FRAMESET>
<noframes>need frame support</noframes>
<body>

</body>
</html>