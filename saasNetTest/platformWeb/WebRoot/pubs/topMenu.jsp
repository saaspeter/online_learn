<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.sf.navigator.menu.MenuRepository,net.sf.navigator.menu.MenuComponent,java.util.List,java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" href="../styles/css/topMenu.css">
  <script type="text/javascript" src="../styles/script/menu.js"></script>
  <script type="text/javascript">
     function displaySubMenu(li) {
         var subMenu = li.getElementsByTagName("ul")[0];
         subMenu.style.display = "block";
     }

     function hideSubMenu(li) {
         var subMenu = li.getElementsByTagName("ul")[0];
         subMenu.style.display = "none";
     }
  </script>
  
</head>
<%
   // 此菜单只能显示2级子菜单
   List topList = new ArrayList();
   MenuRepository repository = null;
   if(request.getAttribute("repository")!=null)
	 repository = (MenuRepository)request.getAttribute("repository");  
   if(repository!=null)
	  topList = repository.getTopMenus();
%>
<body>
<div id="PARENT">
<ul id="navigation">
   <%
     MenuComponent menu = null;
     MenuComponent subMenu = null;
     MenuComponent[] subMenus = null; // 子菜单集合
     String onclickStr = "";
     for(int i=0;i<topList.size();i++){
    	 menu = (MenuComponent)topList.get(i);
    	 if(menu!=null){
    		if(menu.getOnclick()!=null&&menu.getOnclick().trim().length()>0)
    		   onclickStr = "onclick=\""+menu.getOnclick()+"\"";
    		else
    		   onclickStr = "";
    		subMenus = menu.getMenuComponents();
    		// 输出菜单，类似：<li><a href="#" onclick=";">&gt;语言设置</a></li>
    	    out.println("<li onmouseover=\"displaySubMenu(this)\" onmouseout=\"hideSubMenu(this)\"><a href=\"#\" "+onclickStr+">"+menu.getTitle()+"</a></li>");
    	    if(subMenus!=null&&subMenus.length>0){
    	    	out.println("<ul>");
    	    	// 显示2级子菜单
    	    	for(int j=0;j<subMenus.length;j++){
    	    		subMenu = (MenuComponent)subMenus[j];
    	    		if(subMenu!=null){
    	    			out.println("<li><a href=\"#\" onclick=\""+subMenu.getOnclick()+"\">"+subMenu.getTitle()+"</a></li>");
    	    		}
    	    	}
    	    	out.println("</ul></li>");
    	    }
    	 }
     }
   %>
</ul>   
</div>

</body>
</html>