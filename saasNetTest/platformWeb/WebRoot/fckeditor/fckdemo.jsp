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
            var oFCKeditor = new FCKeditor('content');//�������Ϊ��Ԫ�أ���FCKeditor���ɵ�input��textarea����name
            oFCKeditor.BasePath='/netTest/fckeditor/';//ָ��FCKeditor��·����Ҳ����fckeditor.js���ڵ�·��
            oFCKeditor.Height='100%';
            oFCKeditor.ToolbarSet='Default';//ָ��������
            oFCKeditor.Value="<%=content%>";//Ĭ��ֵ
            oFCKeditor.Create();
        </script>
    </span>
    </td>
</tr>
<tr><td align=center><input type="submit" value="�ύ"><button type="button" onclick="testUnderline();return false;">test</button></td></tr>
<tr><td>&nbsp;</td></tr>
<tr><td>ȡֵ����ֱ�ӱ��������ݿ⣩��</td></tr>
<tr><td style="padding:10px;"><%=content%></td></tr>
</table>

</form>
</body>
</html>