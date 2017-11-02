<%@    page contentType="text/html;charset=GBK"%>
<html>
<head>
<title>FCKeditor Test</title>
<script type="text/javascript" src="fckeditor.js"></script>
<script type="text/javascript">
function testUnderline(){
   var val = document.getElementsByName("content")[0].value;
   alert(val);
}
</script>
</head>
<body>
<form action="fckdemo.jsp" method="post">

<% 
String content=request.getParameter("content");
if (content != null) {
  content = content.replaceAll("\r\n", "");
  content = content.replaceAll("\r", "");
  content = content.replaceAll("\n", "");
  content = content.replaceAll("\"", "'");
}else{
  content = "";
}
%>

<table width=100%>
<tr>
    <td colspan=4 style='text-align:center' width=100% height=300px>
    <span>
        <script type="text/javascript">
            var oFCKeditor = new FCKeditor('content');//传入参数为表单元素（由FCKeditor生成的input或textarea）的name
            oFCKeditor.BasePath='/netTest/fckeditor/';//指定FCKeditor根路径，也就是fckeditor.js所在的路径
            oFCKeditor.Height='100%';
            oFCKeditor.ToolbarSet='Default';//指定工具栏
            oFCKeditor.Value="<%=content%>";//默认值
            oFCKeditor.Create();
        </script>
    </span>
    </td>
</tr>
<tr><td align=center><input type="submit" value="提交"><button type="button" onclick="testUnderline();return false;">test</button></td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td>取值（可直接保存至数据库）：</td></tr>
<tr><td style="padding:10px;"><%=content%></td></tr>
</table>

</form>
</body>
</html>