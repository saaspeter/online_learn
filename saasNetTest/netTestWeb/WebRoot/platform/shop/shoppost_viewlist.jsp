<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="shoppostForm" property="jsSuffix" type="java.lang.String"/>
    <title>商店公告通知</title>
	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/list.css">
	<style type="text/css">
		#leftbar1{
		   float:left;
		}
		
		#maincontent1{
		    float:left;
		    width: 100%;
		}
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    
	   function divLinkUrl(url){
	    	goUrl(url);
	   }
	</script>
		
  </head>
  
  <body>
	 <div id="maincontent1">
	  <html:form styleId="list" action="/shop/shoppostviewlist.do" method="get">
	  <input type="hidden" id="pageIndexId" name="pageIndex"/>
	  <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
	  <div class="fieldsTitleDiv">商店通知</div>
	  <div style="text-align: left;padding-left:5px;font-size: larger;min-height: 300px;">
	      <ul class="articleList">
	      <logic:present name="pageList">
		  <logic:iterate id="vo" name="pageList" indexId="ind">
	          <li><a href="javascript:void(0)" onclick="divLinkUrl('/shop/viewShoppost.do?queryVO.id=<bean:write name="vo" property="id"/>');"><bean:write name="vo" property="caption"/></a>
	             &nbsp;&nbsp; <bean:write name="vo" property="createtime" format="yyyy-MM-dd"/>
	          </li>
	      </logic:iterate>
	      </logic:present>
	      </ul>
	  </div>
	  
	  <jsp:include flush="true" page="/pubs/footpage.jsp"></jsp:include>
	  
	  </html:form>
	 </div>
  </body>
</html:html>
