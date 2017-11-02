<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,commonWeb.base.BaseActionBase, platformWeb.base.WebConstant"%>

<%String loginname = BaseActionBase.getLoginInfo(true).getLoginname();
  if(loginname==null){ loginname = ""; } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

  <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
  <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/menu.js"></script>
	<script type="text/javascript">
	<!--
	
	function goTopUrl(url){
	   window.top.document.location=url;
	}

	function displaySubMenu(li) {
        var subMenu = li.getElementsByTagName("ul")[0];
        subMenu.style.display = "block";
    }

    function hideSubMenu(li) {
        var subMenu = li.getElementsByTagName("ul")[0];
        subMenu.style.display = "none";
    }
	
	function showsubmenu(id){
	   var ulArr = document.getElementById('subMenuDiv').getElementsByTagName("ul");
	   var flag = 0;
	   if(ulArr.length>0){
	      var id_ext = id+'_subMenu';
	      for(var i=0;i<ulArr.length;i++){
	          if(ulArr[i].id==id_ext){
	             ulArr[i].className = 'subMenuShow';
	             ulArr[i].style.position = "absolute";
	             ulArr[i].style.left = document.getElementById(id).getBoundingClientRect().left;
	             flag = 1;
	          }else{
	             ulArr[i].className = 'subMenuNone';
	          }
	      }
	   }
	   if(flag==1){
	      document.getElementById("subMenuDiv").style.height="26px";
	   }else{
	      document.getElementById("subMenuDiv").style.height="7px";
	   }
	   
	}

	-->
	</script>
	<style type="text/css">
	<!--
	#top{
		border: 1px solid #dddddd;
		margin-bottom: 2px;
	}
	
	    #navigation ul, #navigation ul li ul {
            list-style-type:none;
        }
        #navigation {
            margin:20px;
        }
        #navigation ul li {
            float:left;
            text-align:center;
            position:relative;
        }
        #navigation ul li a:link, #navigation li a:visited {
            display:block;
            text-decoration:none;
            color:#000;
            width:120px;
            height:40px;
            line-height:40px;
            border:1px solid #fff;
            border-width:1px 1px 0 0;
            background:#c5dbf2;
            padding-left:10px;
        }
        #navigation ul li a:hover {
            color:#fff;
            background:#2687eb;
        }
        #navigation ul li ul li a:hover {
            color:#fff;
            background:#6b839c;
        }
        #navigation ul li ul {
            display:none;
            position:absolute;
            top:40px;
            left:0;
            margin-top:1px;
            width:120px;
        }
        #navigation ul li ul li ul {
            display:none;
            position:absolute;
            top:0px;
            left:130px;
            margin-top:0;
            margin-left:1px;
            width:120px;
        }
	
	-->
	</style>
</head>

<body>
<div id="top">
<img name="" src="<%=WebConstant.WebContext %>/styles/imgs/logo_platform.jpg"  alt="公司logo" />
欢迎：<%=loginname %> | 
<a href="javascript:void(0)" onclick="goBelowBannerUrl('myPreference_main.jsp');return false;">个人设置</a> | 
<a href="#">退出登录</a>
</div>
<div id="navigation">
   <ul>
      <li id="id_indexPage"><a href="#" onclick="menuclick('id_indexPage','main_user.jsp','leftbar_user_platform.jsp','');return false;">我的首页</a></li>
      <li id="id_learn" onmouseover="displaySubMenu(this)" onmouseout="hideSubMenu(this)">
         <a href="/<%=CommonConstant.WebContext_Platform %>/userprodshop/myUserproduct.do">学习考试</a>
         <ul>
             <li><a href="/<%=CommonConstant.WebContext_Platform %>/userprodshop/myUserproduct.do">我的课程</a></li>
             <li><a href="/exam/listTestinfouser.do">我的考试</a></li>
             <li><a href="/exercise/listUserExer.do">我的练习</a></li>
             <li><a href="/learncont/selfLearncontent.do">在线学习</a></li>
         </ul>
      </li>
      <li><a id="id_selectCourse" href="#" onclick="menuclick('id_selectCourse','/shopping/shopping_discript.jsp');return false;">我要选课</a></li>
      <li id="id_order"><a href="#" onclick="menuclick('id_order','main_user.jsp','leftbar_user_platform.jsp','');return false;">课程订单</a></li>
      <li id="id_social"><a href="#" onclick="menuclick('id_social','main_user.jsp','leftbar_user_platform.jsp','');return false;">我的社交</a></li>
   </ul>
</div>
<div id="subMenuDiv" class="bannerInternal" >
    <ul id="" class="subMenuNone">
    </ul>
</div>
</body>
</html>
