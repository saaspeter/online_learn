<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
  String shopid = (request.getAttribute("shopid")==null)?null:(String)request.getAttribute("shopid");
  if(shopid==null){
     shopid = request.getParameter("shopid");
  }
  String url = "https://localhost:8443/saasCasServer-3.1.1/login?service=http%3A%2F%2Flocalhost%3A8080%2FnetTest%2Fj_acegi_cas_security_check";
  System.out.println("<----->:"+url);
  if(url==null)
     out.print("error:no url to go!");
  else
     response.sendRedirect(url);
%>