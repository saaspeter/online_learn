<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <% String actionType = request.getParameter("actionType");
       if(actionType==null||actionType.trim().length()<1){
          actionType = "1"; // 1: flush parent page, 2: just select product
       }
     %>
    <title>课程列表</title>
    <style >
        *{margin:1;}
        .selectList {
		   line-height: 24px; 
		   list-style-type: none;
		   text-align:left;
		   margin: 0;
		}
        .selectList a {
		    display: block;
		    width: 100%;
		}
		.selectList li {
  		    background:#eeeeee; 
			border-bottom:#FFF 1px solid; 
			float:left;
			padding-left: 5px;
		}
		.selectList li a:hover{
		   background:#51A6FB; 
		}
		.selectList a:link  {
		   color:#000; text-decoration:none;
		}
		.selectList a:visited  {
		   color:#666;text-decoration:none;
		}
		.selectList a:hover  {
		   color:#FFF;text-decoration:none;font-weight:bold;
		}
    </style>
	
	<script type="text/javascript">
	    function backCall(id,name){
	       <%if("1".equals(actionType)){ %>
           parent.switchProcut_CB(id,name);
           <%}else { %>
           parent.selProduct_CB(id,name);
           <%} %>
        }
	       
	</script>
  </head>
  
  <body>
  <div><input id="indexInput" style="width: 250px;" value="" onchange=""></div>
  <div >
      <logic:present name="prodlist">
      <ul class="selectList">
         <%if("1".equals(actionType)){ %>
         <li><a href="javascript:void(0);" onclick="backCall('-1','');return false;">所有可用课程</a></li>
         <%} %>
         <logic:iterate id="vo" name="prodlist" indexId="ind">
           <li><a href="javascript:void(0);" onclick="backCall('<bean:write name="vo" property="productbaseid"/>','<bean:write name="vo" property="productname"/>');return false;"><bean:write name="vo" property="productname"/></a></li>
         </logic:iterate>
	  </ul>
	  </logic:present>
  </div>
  </body>
</html:html>
