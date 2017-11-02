<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
  //String url = (request.getAttribute("url")==null)?null:((String)request.getAttribute("url"));
  String url = "https://localhost:8443/saasCasServer-3.1.1/login?service=https%3A%2F%2Flocalhost%3A8443%2Fplatform%2Fj_acegi_cas_security_check";
  if(url==null)
     out.print("error:no url to go!");
  else
     response.sendRedirect(url);
%>