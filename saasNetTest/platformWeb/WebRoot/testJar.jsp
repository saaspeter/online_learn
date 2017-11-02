<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.security.CodeSource,java.security.ProtectionDomain"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
the page is a test page::<br>
<%
String classLocation = "not find class!peter";
String error = "";
String className = "org.apache.commons.lang.builder.ToStringBuilder";
if(request.getParameter("classname")!=null&&(request.getParameter("classname")).trim().length()>0)
	className = request.getParameter("classname");
if ((className != null)
	&& ((className = className.trim()).length() != 0)) {
	// Attempt to load class and get its location.
	
		ProtectionDomain pd =
			Class.forName(className).getProtectionDomain();
		if (pd != null) {
			CodeSource cs = pd.getCodeSource();
			if (cs != null) {
				classLocation = cs.toString();
			} else {
				error = "No CodeSource found!";
			}
		} else {
			error = "No ProtectionDomain found!";
		}
	out.println("className:"+className+" location::::::"+classLocation);
	out.println("error info is::::::"+error);
}

%>
</body>
</html>