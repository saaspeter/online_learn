<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant,netTest.learncont.vo.Learncontent,netTest.exercise.vo.Exercise,netTest.learncont.constant.LearncontentConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="contentcateForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productidVar" name="contentcateForm" property="productbaseid" type="java.lang.Long"/>
    <bean:define id="topcatevoVar" name="contentcateForm" property="topcatevo" type="netTest.prodcont.vo.Contentcate"/>
    <bean:define id="isadmin" name="contentcateForm" property="isadmin" type="java.lang.Boolean"/>
    <% Long pidVar = null;
       boolean emptyCategory = true;
       String canedit = request.getParameter("canedit");
       boolean caneditBoolean = false;
       if(canedit!=null && "1".equals(canedit)){
    	   caneditBoolean = true;
       }
    %>
    <title></title>
	<style type="text/css">
		* {
		   padding: 0;
		   margin: 0;
		}
		#navcate {
		   width:100%;
		   line-height: 27px; 
		   list-style: none;
		   text-align:left;
		   /*定义整个ul菜单的行高和背景色*/
		}
		/*==================一级目录===================*/
		#navcate a {
		   width: 100%; 
		   display: block;
		   padding-top: 3px;
		   padding-bottom: 3px;
		   padding-left:10px;
		   /*Width(一定要)，否则下面的Li会变形*/
		}
		#navcate li {
		   width:100%;
		   border-bottom:#FFF 1px solid; /*下面的一条白边*/
		   float:left;
		   overflow: hidden;
		   list-style:none;
		   background-color: #ffffff;
		   /*float：left,本不应该设置，但由于在Firefox不能正常显示
		   继承Nav的width,限制宽度，li自动向下延伸*/
		}
		
		#navcate a:link  {
		   color:#000; text-decoration:none;
		}
		#navcate a:visited  {
		   color:#666;text-decoration:none;
		}
		
		/*==================二级目录===================*/
		#navcate li ul {
		   list-style:none;
		   text-align:left;
		}
		
		#navcate li ul li:hover{
		   background:#eeeeee; /*一级目录onMouseOver显示的背景色*/
		   font-weight:bold;
		}

	</style>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
	<script type="text/javascript">
	    
	</script>
  </head>

  <body>

	  <div style="width:98%;margin-left:10px;">
	     <%if(isadmin && caneditBoolean){ %>
	     <div style="text-align:right;margin-right:50px;">
	          <button class="btn btn-success" onclick="window.location.assign('<%=request.getContextPath() %>/prodcont/contentcate_main.jsp?productbaseid=<%=productidVar %>');">编辑课程目录</button>
	     </div>
	     <%} %>
	     <div>
	     <ul id="navcate">
	       <%if(topcatevoVar!=null&&((topcatevoVar.getOnlinelearnlist()!=null&&topcatevoVar.getOnlinelearnlist().size()>0))
		                                   ||(topcatevoVar.getExerlist()!=null&&topcatevoVar.getExerlist().size()>0)) { 
	    	   boolean hasTryDoc = false; Long tryContentid = null;  
	    	   emptyCategory = false;
		    %>
		    <li style="background-color:#DCEAFC;">
		        <a id="overviewId" href="javascript:void(0);" >课程学习总结:<%=productidVar %></a>
		        <table style="border: 0px;margin-left:30px;width: 97%">
                    <tr>
                       <td style="text-align: left;">
				          <logic:iterate id="learnvo" name="contentcateForm" property="topcatevo.onlinelearnlist" indexId="ind" type="netTest.learncont.vo.Learncontent">
						        <%if(LearncontentConstant.IsTry_Try.equals(learnvo.getIstry())){ 
							    	 hasTryDoc= true; tryContentid = learnvo.getContentid();
							     %>
						        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
						             title='<bean:write name="learnvo" property="caption"/>' 
						             onclick="openWin('/learncont/viewLearncontent.do?preview=1&vo.contentid=<%=tryContentid %>',800,650,'yes','yes');return false;"/>
						        <%}else { %>
						        <img class="disableImage" style="width: 23px;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
						             title='<bean:write name="learnvo" property="caption"/>' />
						        <%} %>
					      </logic:iterate> &nbsp;&nbsp;
					      <logic:iterate id="exervo" name="contentcateForm" property="topcatevo.exerlist" indexId="ind_exer">
						      <img class="disableImage" style="width: 23px;" src="../styles/imgs/exam.png" 
							       title="<bean:write name="exervo" property="exername"/>"/>
						  </logic:iterate>
						  <%if(hasTryDoc){ %>
						     <button type="button" class="btn btn-success btn-xs" style="margin-left:2em;" 
						             onclick="openWin('/learncont/viewLearncontent.do?preview=1&vo.contentid=<%=tryContentid %>',800,650,'yes','yes');return false;">预览</button>
						  <%} %>
                       </td>
                    </tr>
                </table>
		   </li>
		   
		   <li style="background: #ffffff;text-align: center;"><span style="text-align: center;font-weight: bolder;font-size:larger;">&dArr;</span></li>
		   <%} %>
		   
		   <logic:present name="contentcateForm" property="datalist">
		   <% emptyCategory = false; %>
		   <logic:iterate id="datavo" name="contentcateForm" property="datalist" indexId="i" type="netTest.prodcont.vo.Contentcate">
	       <li>
	           <div style="border-bottom: 2px #cccccc solid;background-color:#DCEAFC;padding:8px;">
	           <span>
	              <bean:write name="datavo" property="contentcatename"/>
	           </span>
                <%if((datavo.getOnlinelearnlist()!=null&&datavo.getOnlinelearnlist().size()>0)
                        || (datavo.getExerlist()!=null&&datavo.getExerlist().size()>0)) { 
                	     boolean hasTryDoc = false; Long tryContentid = null;
                %>
                <span style="height:100%;vertical-align: bottom;">
                    <logic:present name="datavo" property="onlinelearnlist">
				    <logic:iterate id="learnvo" name="datavo" property="onlinelearnlist" indexId="ind" type="netTest.learncont.vo.Learncontent">
				    <%if(LearncontentConstant.IsTry_Try.equals(learnvo.getIstry())){ 
				    	 hasTryDoc= true; tryContentid = learnvo.getContentid();
				     %>
			        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
			             title='<bean:write name="learnvo" property="caption"/>' 
			             onclick="openWin('/learncont/viewLearncontent.do?preview=1&vo.contentid=<%=tryContentid %>',800,650,'yes','yes');return false;"/>
			        <%}else { %>
			        <img class="disableImage" style="width: 23px;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
			             title='<bean:write name="learnvo" property="caption"/>' />
			        <%} %>
			        </logic:iterate>
			        </logic:present>
			        &nbsp;&nbsp;
			        <logic:iterate id="exervo" name="datavo" property="exerlist" indexId="ind_exer">
				    <img class="disableImage" style="width: 23px;" src="../styles/imgs/exam.png" 
					           title="<bean:write name="exervo" property="exername"/>" />
				    </logic:iterate>
				    <%if(hasTryDoc){ %>
				     <button type="button" class="btn btn-success btn-xs" style="margin-left:2em;" 
				             onclick="openWin('/learncont/viewLearncontent.do?preview=1&vo.contentid=<%=tryContentid %>',800,650,'yes','yes');return false;">预览</button>
				     <%} %>
                </span>
                <%} %>
		        </div>
	            <ul id="cate_ul_<%=i %>">
	                <logic:present name="datavo" property="sublist">
	                <logic:iterate id="subdatavo" name="datavo" property="sublist" indexId="j" type="netTest.prodcont.vo.Contentcate">
		            <li id="cate<%=i %>sub<%=j %>_li" style="background-image:url('../styles/imgs/ico/greendot.png');background-position:6 10px;background-repeat:no-repeat;border-bottom: 2px #cccccc solid;">
		                <div style="margin-left:17px;border-left: 1px solid #cccccc;padding-left:5px;padding-top:3px;padding-bottom:8px;">
		                <span style="margin-left: 12px; margin-right:35px;"><bean:write name="subdatavo" property="contentcatename"/></span>
	                    <%if((subdatavo.getOnlinelearnlist()!=null&&subdatavo.getOnlinelearnlist().size()>0)
                             || (subdatavo.getExerlist()!=null&&subdatavo.getExerlist().size()>0)) { 
	                    	    boolean hasTryDoc = false; Long tryContentid = null;
	                     %>
	                          <span style="height:100%;vertical-align: bottom;">
								 <logic:iterate id="learnvo" name="subdatavo" property="onlinelearnlist" indexId="ind" type="netTest.learncont.vo.Learncontent">
								    <%if(LearncontentConstant.IsTry_Try.equals(learnvo.getIstry())){ 
								    	 hasTryDoc= true; tryContentid = learnvo.getContentid();
								     %>
							        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
							             title='<bean:write name="learnvo" property="caption"/>' 
							             onclick="openWin('/learncont/viewLearncontent.do?preview=1&vo.contentid=<%=tryContentid %>',800,650,'yes','yes');return false;"/>
							        <%}else { %>
							        <img class="disableImage" style="width: 23px;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
							             title='<bean:write name="learnvo" property="caption"/>' />
							        <%} %>
							     </logic:iterate>
							     <logic:iterate id="exervo" name="subdatavo" property="exerlist" indexId="ind_exer">
							        <img class="disableImage" style="width: 23px;bottom:2px;" src="../styles/imgs/exam.png" 
							             title="<bean:write name="exervo" property="exername"/>" />
							     </logic:iterate>
							     <%if(hasTryDoc){ %>
							     <button type="button" class="btn btn-success btn-xs" style="margin-left:2em;" 
							             onclick="openWin('/learncont/viewLearncontent.do?preview=1&vo.contentid=<%=tryContentid %>',800,650,'yes','yes');return false;">预览</button>
							     <%} %>
	                          </span>
	                    <%} %>
	                    </div>
		            </li>
		            </logic:iterate>
		   	        </logic:present>
		   	   </ul>
	       </li>
		   </logic:iterate>
		   </logic:present>
		</ul>
	    </div>
	    
	    <%if(emptyCategory){ %>
	    <div class="alertFont" style="text-align:center;">还未设置课程目录，请设编辑程目录</div>
	    <%} %>
	    
	  </div>

  </body>
  
  <script type="text/javascript" language="javascript">
      window.onload = function(){
		 if(typeof(parent.adjustHeightInBuy)!='undefined'){
			 parent.adjustHeightInBuy(document.body.scrollHeight);
	     }
	  };
   </script>
  
</html:html>
