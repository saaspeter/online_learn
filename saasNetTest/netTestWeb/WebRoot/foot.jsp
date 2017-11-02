<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="commonTool.constant.CommonConstant,commonWeb.base.KeyInMemConstBase"%>
<%@ include file="/pubs/taglibs.jsp"%>
<% Long uselocaleid = (Long)session.getAttribute(KeyInMemConstBase.SessionKey_LocaleidUserSelect);
   java.util.Locale locale = (java.util.Locale)session.getAttribute(KeyInMemConstBase.SessionKey_LocaleUserSelect);
   String localestr = (locale==null)?"":locale.toString();
   String uselocaleidStr = "";
   if(uselocaleid!=null){
	   uselocaleidStr = uselocaleid.toString();
   }
%>

<style type="text/css">
<!--
#footer{
   width:100%; position:relative; border-top: 1px solid #cccccc; 
   clear:both;
   margin-top: 30px;
   text-align: center;
   padding-top: 10px;
   font-size: smaller;
}
-->
</style>

   <script type="text/JavaScript">     
      function tosite(selObj){
          if(selObj==null){
              return;
          }
          var old_localeid = '';
          var itemValue = selObj.options[selObj.selectedIndex].value;
          if(itemValue==old_localeid){ return; }
          document.location.href = "/<%=CommonConstant.WebContext_Education %>/tohome.do?localeid="+itemValue;
      }
   </script>

<div id="footer">
    <a href="/<%=CommonConstant.WebContext_Education %>/about/aboutsite.jsp#introduceSite">关于我们</a>&nbsp;&nbsp;&nbsp; 广告服务 &nbsp;&nbsp;&nbsp; <a href="/<%=CommonConstant.WebContext_Education %>/about/aboutsite.jsp#functions">帮助中心 </a>&nbsp;&nbsp;&nbsp; to site:
               <html:select styleId="selectlocaleid" value="<%=uselocaleidStr %>" onchange="tosite(this)" style="font-size:small;border: 1px #CCCCCC solid;">
                   <html:optionsSaas localeListSet="true" localetype="selectlocale"/>
               </html:select> 
    &nbsp;&nbsp;&nbsp; <a href="/<%=CommonConstant.WebContext_Education %>/about/aboutsite.jsp#contactInfo">联系我们</a>
</div>

<div style="text-align: center;font-size: smaller;margin-top: 10px;">
    Copyright © 2015 tomylearn.com All Rights Reserved 
    <%if(localestr!=null && localestr.indexOf("zh_CN")!=-1){ %>
    | <a href="http://www.miitbeian.gov.cn/" target="_beianquery">皖ICP备15004039号</a>
    <%} %>
</div>

<div style="height: 30px;"></div>
<!-- google analysis -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-56619447-1', 'auto');
  ga('send', 'pageview');
</script>
