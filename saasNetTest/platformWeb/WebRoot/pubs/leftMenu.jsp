<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="net.sf.navigator.menu.MenuRepository,net.sf.navigator.menu.MenuComponent,java.util.List,java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" href="../styles/css/leftMenu.css">
  <script type="text/javascript" src="../styles/script/menu.js"></script>
  <script type="text/javascript">
	 function menuFix() {
	   var obj = document.getElementById("nav").getElementsByTagName("li");
	   for(var i=0; i<obj.length; i++) {
	      obj[i].onmouseover=function() {
	         this.className+=(this.className.length>0? " ": "") + "sfhover";
	      }
	      obj[i].onMouseDown=function() {
	         this.className+=(this.className.length>0? " ": "") + "sfhover";
	      }
	      obj[i].onMouseUp=function() {
	         this.className+=(this.className.length>0? " ": "") + "sfhover";
	      }
	      obj[i].onmouseout=function() {
	         this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
	      }
	   }
	}
	
	function DoMenu(emid)
	{
	   var obj = document.getElementById(emid); 
	   obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
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
<ul id="nav">
   <%
     String defaultOpenIcon = "&gt;"; // 默认菜单打开的标志
     String defaultCollapsedIcon = "&and;"; // 默认菜单闭合的标志
     String openIcon = "";
     String subMenuId = "subMenuId"; // 子菜单id前缀，用于控制菜单开关的
     String defaultSwitchFun = "DoMenu";
     String subMenuSwitchFun = ""; // 菜单开关的function
     String url = "#";
     MenuComponent menu = null;
     MenuComponent subMenu = null;
     MenuComponent[] subMenus = null; // 子菜单集合
     for(int i=0;i<topList.size();i++){
    	 menu = (MenuComponent)topList.get(i);
    	 if(menu!=null){
    	    // 如果onclick事件为空，并且url不为空，则显示url。优先处理onclick
    	    if(menu.getUrl()!=null&&menu.getUrl().trim().length()>0&&(menu.getOnclick()==null||menu.getOnclick().trim().length()<1))
    	       url = menu.getUrl();
    	    else
    	       url = "#";
    		subMenus = menu.getMenuComponents();
    		if(subMenus!=null&&subMenus.length>0){
    			// 如果有子菜单需要设置菜单开关标志和开关函数
    			openIcon = defaultCollapsedIcon;
    			subMenuSwitchFun = defaultSwitchFun+"('"+subMenuId+String.valueOf(i)+"');";
    		}else{
    			openIcon = defaultOpenIcon;
    			subMenuSwitchFun = "";
    		}
    		// 输出菜单，类似：<li><a href="#" onclick="goRightUrl('/i18n/i18n.do?method=list',this);">&gt;语言设置</a></li>
    	    out.println("<li><a href=\""+url+"\" onclick=\""+subMenuSwitchFun+menu.getOnclick()+"\">"+openIcon+menu.getTitle()+"</a></li>");
    	    if(subMenus!=null&&subMenus.length>0){
    	    	out.println("<ul id=\""+subMenuId+String.valueOf(i)+"\" class=\"collapsed\">");
    	    	// 显示2级子菜单
    	    	for(int j=0;j<subMenus.length;j++){
    	    		subMenu = (MenuComponent)subMenus[j];
    	    		if(subMenu!=null){
    	    			out.println("<li><a href=\"#\" onclick=\""+subMenu.getOnclick()+"\">"+defaultOpenIcon+subMenu.getTitle()+"</a></li>");
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