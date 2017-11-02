<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<%
    String urltypeStr = "urltype=login";
    if(request.getParameter("urltype")!=null){
    	urltypeStr = "urltype="+request.getParameter("urltype");
    }
    String messcodeStr = "&messcode=-1";
    if(request.getParameter("messcode")!=null){
    	messcodeStr = "&messcode="+request.getParameter("messcode");
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- here all need login page need go to main login page, login_simple.jsp no use currently -->
<script type="text/javascript">
<!--
    if(typeof(window.parent)!="undefined" && window.parent!=null){
    	if(typeof(window.parent.parent)!="undefined" && window.parent.parent!=null){
            window.parent.parent.location.href="<%=request.getContextPath()+"/tologin.do?"+urltypeStr+messcodeStr %>";
    	}else {
    		window.parent.location.href="<%=request.getContextPath()+"/tologin.do?"+urltypeStr+messcodeStr %>";
    	}
    }else {
        window.location.href="<%=request.getContextPath()+"/tologin.do?"+urltypeStr+messcodeStr %>";
    }
//-->
</script>
