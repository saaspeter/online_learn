<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/pubs/taglibs.jsp"%>
<html>
<head>
	<title>课程内容目录</title>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
</head>

  <%
    String productbaseidStr = "";
    String listType = "";
    String selectType = "";
    String url = "listContentcatemag.do";
    if(request.getParameter("productbaseid")!=null&&request.getParameter("productbaseid").trim().length()>0)
       productbaseidStr = request.getParameter("productbaseid").trim();
    if(request.getParameter("listType")!=null&&request.getParameter("listType").trim().length()>0)
       listType = request.getParameter("listType").trim();
    if(request.getParameter("selectType")!=null&&request.getParameter("selectType").trim().length()>0)
       selectType = request.getParameter("selectType").trim();
    if(listType!=null&&"2".equals(listType)){
        url = "listContentcatesel.do";
    }
   %>  
<FRAMESET border=1 frameSpacing=1 frameBorder="yes" cols=250,* bordercolor="#EEEEEE">
  <FRAME id="left" name="left" marginWidth=0 marginHeight=0 src="toContcateTree.do?queryVO.productbaseid=<%=productbaseidStr %>&selectType=<%=selectType %>" scrolling="auto">
  <FRAME id="content" name="content" src="<%=url %>?queryVO.productbaseid=<%=productbaseidStr %>&selectType=<%=selectType %>" scrolling="auto">
</FRAMESET>
<noframes>需要框架显示</noframes>
<body>

</body>
</html>