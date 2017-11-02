<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="hotcategoryForm" property="jsSuffix" type="java.lang.String"/>
	<script language=JavaScript src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript">
	    
	</script>
  </head>
  
  <body>
      <logic:present name="hotcategoryForm" property="hotcateList">
      <logic:iterate id="vo" name="hotcategoryForm" property="hotcateList">
          <div style="font-weight: bold; margin: 5px;"> &bull;
             <a href="javascript:void(0);" onclick="parent.switchCategory_CB('<bean:write name="vo" property="categoryid"/>','<bean:write name="vo" property="categoryname"/>');">
                <bean:write name="vo" property="categoryname"/>
             </a>
          </div>
          <logic:present name="vo" property="subcatelist">
          <div style="font-size: smaller;">
          <logic:iterate id="subvo" name="vo" property="subcatelist">
             <a href="javascript:void(0);" style="white-space: nowrap;" onclick="parent.switchCategory_CB('<bean:write name="subvo" property="categoryid"/>','<bean:write name="subvo" property="categoryname"/>');">
                <bean:write name="subvo" property="categoryname"/>
             </a>&nbsp;|&nbsp;
          </logic:iterate>
          </div>
          </logic:present>
      </logic:iterate>
      </logic:present>
   
  </body>
</html:html>
