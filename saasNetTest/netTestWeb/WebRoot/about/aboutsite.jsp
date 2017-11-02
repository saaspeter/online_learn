<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pubs/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <title><bean:message key="netTest.page.common.title"/>--<bean:message bundle="staticDocKey" key="Page.aboutsite.title"/></title>
   <link rel="stylesheet" type="text/css" href="../styles/css/leftMenu.css"/>
   <link rel="stylesheet" type="text/css" href="../styles/css/edit.css"/>
   <link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
   <style type="text/css">
      #menu7{
		  display: block;
		  color: #667;
		  background-color: #ec8;
	  }
	  
	  .userinput_div{
	      font-size: larger;
	      line-height:1.5;
	      margin-bottom: 50px;
	  }
   </style>
</head>
  
  <body>
	<div class="col-xs-12 col-md-9 center-block">
	<jsp:include flush="true" page="../index/banner.jsp"></jsp:include>
	<div style="float:left; margin-top: 20px;" class="col-md-2 hidden-xs">
       <ul id="nav">
          <li class="menu"><a href="#introduceSite"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.introduceSite"/></a></li>
          <li class="menu"><a href="#functions"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.functions"/></a></li>
          <li class="menu"><a href="#contactInfo"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.contactInfo"/></a></li>
          <li class="menu"><a href="../social/listleavemess.do?queryVO.objecttype=system"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.systemAdvice"/></a></li>
       </ul>
    </div>
	
	<div style="float: left; margin-top: 20px; " class="col-xs-12 col-md-10">
	   <div class="fieldsTitleDiv">
	        <a name="introduceSite"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.introduceSite"/></a>
	   </div>
	   
	   <div class="userinput_div">	  
            <bean:message bundle="staticDocKey" key="Page.aboutsite.introduceSite"/>
       </div>
       
       <div class="fieldsTitleDiv">
	        <a name="functions"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.functions"/></a>
	   </div>
	   
	   <div class="userinput_div">	  
            <bean:message bundle="staticDocKey" key="Page.aboutsite.functions"/>
            <a href="#"><bean:message bundle="staticDocKey" key="Page.aboutsite.helpCenter"/></a>
       </div>
       
       <div class="fieldsTitleDiv">
	       <a name="contactInfo"><bean:message bundle="staticDocKey" key="Page.aboutsite.menu.contactInfo"/></a>
	   </div>
	   
	   <div class="userinput_div">	  
           <bean:message bundle="staticDocKey" key="Page.aboutsite.contactInfo"/>
       </div>
	</div>
	
    <jsp:include flush="true" page="../foot.jsp"></jsp:include>
    </div>
  </body>
</html>
