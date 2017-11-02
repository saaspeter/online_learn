<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="hotcategoryForm" property="jsSuffix" type="java.lang.String"/>
    <style type="text/css">
        a:link {
		    text-decoration: none;
		}
		
		body {
		   margin:0px;
		   overflow-x:auto;overflow-y:auto;
		}
		
    </style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript">
	    
	</script>
  </head>
  
  <body>
      <div style="padding-left:6px;">
      <logic:present name="hotcategoryForm" property="hotcateList">
      <logic:iterate id="vo" name="hotcategoryForm" property="hotcateList" type="platform.vo.Hotcategory">
          <div style="font-weight: bold; margin-top: 10px;margin-bottom: 5px;"> &bull;
             <a href="javascript:void(0);" onclick="parent.switchCategory_CB('<bean:write name="vo" property="categoryid"/>','<bean:write name="vo" property="categoryname"/>');">
                <bean:write name="vo" property="categoryname"/>
             </a>
          </div>
          <logic:present name="vo" property="subcatelist">
          <div style="font-size:smaller;">
          <logic:iterate id="subvo" indexId="i" name="vo" property="subcatelist" length="subsize" >
             <a href="javascript:void(0);" style="white-space: nowrap;" onclick="parent.switchCategory_CB('<bean:write name="subvo" property="categoryid"/>','<bean:write name="subvo" property="categoryname"/>');">
                <font style="line-height:150%;"><bean:write name="subvo" property="categoryname"/></font>
             </a> <%if(i<vo.getSubcatelist().size()-1){ %> &nbsp;|&nbsp;<%} %>
          </logic:iterate>
          </div>
          </logic:present>
      </logic:iterate>
      </logic:present>
   	  </div>
  </body>
</html:html>
