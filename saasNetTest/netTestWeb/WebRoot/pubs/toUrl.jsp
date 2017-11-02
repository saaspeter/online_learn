<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonWeb.base.KeyInMemConstBase,netTestWeb.base.WebConstant" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
  String url = (request.getAttribute("url")==null)?null:((String)request.getAttribute("url"));
  url = WebConstant.doContextUrl(url);
  String pageAction = (request.getAttribute("pageAction")==null)?null:((String)request.getAttribute("pageAction"));
  String DisMessKey = (request.getAttribute(KeyInMemConstBase.DisMessKey)==null)?null:((String)request.getAttribute(KeyInMemConstBase.DisMessKey));
  String DisMessArg0Key = (request.getAttribute(KeyInMemConstBase.DisMessArg0Key)==null)?null:((String)request.getAttribute(KeyInMemConstBase.DisMessArg0Key));
  String bundle = (request.getAttribute("bundle")==null)?null:((String)request.getAttribute("bundle"));
  if(pageAction!=null){
     if("closeWindow".equals(pageAction)){
%>
  <script type="text/javascript">
      window.close();
  </script>
<%
     }else if("closeDiv".equals(pageAction)){
%>
  <script type="text/javascript">
      //parent.clearDiv();
  <%
      if(url!=null&&url.trim().length()>0){
  %>
         parent.location.href = '<%=url %>' ;
  <%
      }else{
  %>     
         var backurlobj = parent.document.getElementById("backUrl");
         if(backurlobj!=null&&typeof(backurlobj)!="undefined"){
            parent.location.href = backurlobj.value;
         }else if(parent.document.forms[0]!=null){
            var formurl = parent.document.forms[0].action;
            <% if(DisMessKey!=null&&DisMessKey.trim().length()>0){ %>
                  if(formurl.indexOf("?")!=-1){
                     formurl = formurl + "&<%=KeyInMemConstBase.DisMessKey+"="+DisMessKey %>"
                               + "&<%=KeyInMemConstBase.DisMessArg0Key+"="+DisMessArg0Key %>&bundle=<%=bundle %>";
                  }else {
                     formurl = formurl + "?<%=KeyInMemConstBase.DisMessKey+"="+DisMessKey %>"
                               + "&<%=KeyInMemConstBase.DisMessArg0Key+"="+DisMessArg0Key %>&bundle=<%=bundle %>";
                  }
            <% } %>
            parent.document.forms[0].action = formurl;
            parent.document.forms[0].submit();
         }
  <%  } %>  
      
  </script>
<%     
     }
  }else if(url==null){
     out.print("error:no url to go!");
  }else{
     response.sendRedirect(url);
  }
%>