<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="platformWeb.base.WebConstant" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
  <link rel="stylesheet" type="text/css" media="screen" href="../styles/css/banner.css" />
  <script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript">
	<!--
	
	function goTopUrl(url){
	   window.top.document.location=url;
	}

	function menuclick(id,url1,url2,url3){
	   if(id!=null&&id!=''){
	      pressMenu(id);
	      showsubmenu(id);
	   }
	   if(typeof(url1)!="undefined"&&url1!=null&&url1!=''){
	      if(typeof(url2)=="undefined"){
	         url2 = '';
	      }
	      if(typeof(url3)=="undefined"){
	         url3 = '';
	      }
	      goBelowBannerUrl(url1, url2, url3);
	   }
	   return false;
	}
	
	-->
	</script>
	<style type="text/css">
	<!--
	#top{
		border: 1px solid #dddddd;
		margin-bottom: 2px;
	}
	
	-->
	</style>
</head>

<body>
   
   <div style="text-align: center;margin: 20px;">
       <button style="width:15%;height: 40px;font-size: large;margin: 15px;" onclick="goUrl('/customers/editUser.do');">个人基本资料</button>
       <button style="width:15%;height: 40px;font-size: large;margin: 15px;">修改密码</button>
       <button style="width:15%;height: 40px;font-size: large;margin: 15px;">设定地区语言</button>
   </div>
</body>

</html>
