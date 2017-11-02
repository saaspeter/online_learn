<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="commonWeb.base.BaseActionBase, commonTool.constant.CommonConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%String loginname = BaseActionBase.getLoginInfo(true).getLoginname();
  if(loginname==null){ loginname = ""; } %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商店的标识</title>
  <link rel="stylesheet" type="text/css" media="screen" href="../../styles/css/banner.css" />
  <script type="text/javascript" src="../../styles/script/pageAction.js"></script>
  <script type="text/javascript" src="../../styles/script/menu.js"></script>
	<script type="text/javascript">
	<!--
	
	function goTopUrl(url){
	   window.top.document.location=url;
	}
	
	function menuclick(id,url1,url2,url3){
	   if(id!=null&&id!=''){
	      pressMenu(id);
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
	
	function goBelowBannerUrl(mainPage,leftUrl,rightUrl){
       if(mainPage!=''){
          mainPage = doContextUrl(mainPage);
          if(leftUrl!=''||rightUrl!=''){
             leftUrl = doContextUrl(leftUrl);
             rightUrl = doContextUrl(rightUrl);
             var linkmark = "&";
	         if(mainPage.indexOf("?")==-1){
	           linkmark = "?";
	         }
             mainPage = mainPage+linkmark+"leftUrl="+leftUrl+"&rightUrl="+rightUrl;          	
          }
          parent.frames[1].document.location.href=mainPage;
       }
    }

	-->
	</script>
	<style type="text/css">
	<!--
	#top{
		border: 1px solid #dddddd;
	}
	
	-->
	</style>
</head>

<body>
<div id="top">
<table style="border: 0px; width: 100%;">
    <tr>
        <td><img name="" style="cursor: pointer;" onclick="goTopUrl('/<%=CommonConstant.WebContext_Education %>');" src="/<%=CommonConstant.WebContext_Education %>/styles/imgs/logo_platform.jpg" alt="公司logo"/></td>
        <td>你好，<%=loginname %>，<a href="javascript:void(0)" onclick="goTopUrl('/<%=CommonConstant.WebContext_Education %>/j_spring_security_logout');">退出系统</a></td>
    </tr>
</table>

</div>
<div id="navigation">
    <ul>
      <li id="menu1"><a id="id_banner_cusotmer" href="javascript:void(0);" onclick="menuclick('id_banner_cusotmer','../../pubs/main.jsp','/platform/sysAdmin/customerAdmin/leftbar_customerAdmin.jsp','/shop/listshop.do');return false;">客户管理</a></li>
	  <li id="menu2"><a id="id_banner_content" href="javascript:void(0);" onclick="menuclick('id_banner_content','../../pubs/main.jsp','/platform/sysAdmin/leftbar_contentAdmin.jsp','/index/listInfonewsMag.do');return false;">网站内容管理</a></li>
      <li id="menu3"><a id="id_banner_security" href="javascript:void(0);" onclick="menuclick('id_banner_security','../../pubs/main.jsp','/securityManage/leftbar_security.jsp','/securityManage/resources_main.jsp');return false;">权限管理</a></li>
      <li id="menu4"><a id="id_banner_basicset" href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../../pubs/main.jsp','/platform/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">平台基础设置</a></li>
      <li id="menu4"><a id="id_banner_basicset" href="javascript:void(0);" onclick="menuclick('id_banner_basicset','../../pubs/main.jsp','/platform/sysAdmin/basicSet/leftbar_basicSet.jsp','/i18n/i18n.do?method=list');return false;">我的消息</a></li>
    </ul>
</div>
</body>
</html>
