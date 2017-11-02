<%@ page import="org.springframework.security.ui.AccessDeniedHandlerImpl" %>

<h1>Sorry, access is denied</h1>


<p>
<%= request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY)%>

<p>
