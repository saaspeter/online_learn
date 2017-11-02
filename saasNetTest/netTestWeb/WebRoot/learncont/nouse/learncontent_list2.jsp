<!-- 带折叠效果的学习目录 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,commonTool.constant.CommonConstant,netTest.learncont.vo.Learncontent,netTest.exercise.vo.Exercise" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productidVar" name="learncontentForm" property="sessionProductid" type="java.lang.Long"/>
    <bean:define id="useCatevoVar" name="learncontentForm" property="usecatevo" type="netTest.prodcont.vo.Contentcate"/>
    <bean:define id="topcatevoVar" name="learncontentForm" property="topcatevo" type="netTest.prodcont.vo.Contentcate"/>
    <% Long pidVar = null;
       Long contentcateidVar = null;
       if(useCatevoVar!=null){
          pidVar = useCatevoVar.getPid();
          contentcateidVar = useCatevoVar.getContentcateid();
       }
       String productIdStr = (productidVar==null)?"":productidVar.toString();
       String contentcateidStr = (contentcateidVar==null)?"":contentcateidVar.toString();
       boolean hasmorelearn = false;
       %>
    <title>学习内容</title>
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	
	<style type="text/css">
		#bannermenu2{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
				
		#navcate {
		   width:100%;
		   line-height: 30px; 
		   list-style: none;
		   text-align:left;
		   margin: 0px;
		   padding: 0px;
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
		
		#navcate li a {
		   
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
		
		.cateLearnCont{
		    border: 0px; margin-left:17px; 
		    padding-left:13px; width: 97%;
		    border-left: 1px solid #cccccc;
		}
		
		.expendCate{
		    background-image: url('../styles/imgs/ico/expand_vertical.bmp');
		    background-position:90%;background-repeat:no-repeat;
		    background-attachment:scoll;
		}
		
		.collapseCate{
		    background-image: url('../styles/imgs/ico/collapse_vertical.bmp');
		    background-position:90%;background-repeat:no-repeat;
		    background-attachment:scoll;
		}
		
		.display_block{
		    diaplay: block;
		}
		
		.display_none{
		    diaplay: none;
		}
	</style>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
	<script type="text/javascript" src="../styles/script/utiltool.js"></script>
	<script type="text/javascript" src="../styles/script/commonlogic.js"></script>
	<script type="text/javascript">
	    function contcate_CB_Hook(id, name){
	       document.getElementById("list").submit();
	    }
	    
	    function setquerydoclevel(){
           var checkobj = document.getElementById("doclevelcheckId");
           if(checkobj.checked==true){
               document.getElementById("doclevelId").value="1";
           }else {
               document.getElementById("doclevelId").value="0";
           }
        }
	    
	    function showcontent(contentcateid, linktype, obj, order){
	    	showdiscuss(contentcateid);
	    	if(linktype==1){
	    		toggleFolder(obj, order);
	    	}
	    }
	    
	    function showdiscuss(contentcateid){
	    	if(contentcateid==null||contentcateid=='null'){
	    		contentcateid = '';
	    	}
	    	var paramstr = 'productid=<%=productIdStr %>&contentcateid='+contentcateid+'&contentcatename=';
	    	loadDivByAjax("discussDiv", '<%=WebConstant.WebContext %>/social/listprodcommentsuser.do',paramstr,false);
	    }
	    
	    function toggleFolder(obj, order){
	    	var ulobj = document.getElementById("cate_ul_"+order);
	    	var tableobj = document.getElementById("cateLearnTableId_"+order);
	    
	    	// if obj class is:expendCate, means: it is expand flag, want to expand
	    	if(obj.className=="expendCate"){
	    		if(ulobj!=null){
	    			ulobj.style.display=(ulobj.style.display == 'block')?'none' : 'block';
	    		}
	    		if(typeof(tableobj)!="undefined" && tableobj!=null){
	    		   tableobj.style.display='block';
	    		}
	    		obj.className="collapseCate";
	    	}else if(obj.className=="collapseCate"){
	    		if(ulobj!=null){
	    			ulobj.style.display=(ulobj.style.display == 'block')?'none' : 'block';
	    		}
	    		if(typeof(tableobj)!="undefined" && tableobj!=null){
	    		   tableobj.style.display='none';
	    		}
	    		obj.className="expendCate";
	    	}
	    }
	</script>
	
  </head>
  
  <body>
  <div class="col-xs-12 col-md-9 center-block">
  <jsp:include flush="true" page="/userAdmin/banner_user.jsp"></jsp:include>
  
  <div class="listPage">
  <html:form styleId="list" action="/learncont/selfLearncontent.do" method="post">
  <input id="shopidId" type="hidden" name="shopid" value="<bean:write name="learncontentForm" property="sessionShopid"/>" >
  <input id="prodidId" type="hidden" name="productbaseid" value="<%=productIdStr %>" >
  <input id="contentcateidId" type="hidden" name="queryVO.contentcateid" value="<%=contentcateidStr %>">
  
  <div class="navlistBar">
        学习考试&nbsp;&gt;&gt;&nbsp;在线学习
        (&nbsp;课程:<a href="javascript:void(0)" onclick="switchProduct(this);" title="点击切换课程"><bean:write name="learncontentForm" property="sessionProductname"/></a>&nbsp;)
  </div>
  
  <div style="border: 0;margin: 2px;overflow: auto;">
	  <div style="height:auto;">
		  <ul class="tabnav">
		    <li><a href="../product/listcoursepost.do?vo.productbaseid=<%=productIdStr %>">信息台</a></li>
		    <li class='selectTab'><a href="javascript:void(0)">课程资料</a></li>
			<li><a href="../exam/listTestinfouser.do?productbaseid=<%=productIdStr %>&contentcateid=<bean:write name="learncontentForm" property="queryVO.contentcateid"/>">考试信息</a></li>
		  </ul>
	  </div>
	  
	  <div class="dashLine"></div>
	  
	  <div id="displayMessDiv">
	      <%if(productidVar==null){ %>请点击左上角选择课程<%} %>
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <%if(productidVar!=null){ %>
	  <div style="background-color:#9ff;padding:2px;margin: 5 0 5 0px;">
	      上次学习时间:<bean:writeDate name="learncontentForm" property="lastlearndate" formatKey="Common.DateFormatType.DateTime"/>, 学习章节: <bean:write name="learncontentForm" property="lastcontentcatename" />, 
        <a class="button green medium fontBold" href="autolearn.do?queryVO.productbaseid=<%=productIdStr %>">继续学习</a>
	  </div>
	  <%} %>
	  
	  <%if(productidVar!=null){ %>
	  <div id="DataTableDiv" style="width:45%;float:left;border-right:1px #cccccc solid;padding-right:7px;">
	     <ul id="navcate">
	        <%if(topcatevoVar!=null&&(topcatevoVar.hasLearncontent())) { %>
		    <li style="border-bottom: 2px #cccccc solid;">
		        <a id="overviewId" href="javascript:void(0);" style="background-color:#DCEAFC;"
		           onclick="showcontent('', 0);">课程综述:</a>
		        <table style="border: 0px;margin-left:30px;width: 97%">
                    <tr>
                       <td style="text-align: left;">
                          <logic:present name="learncontentForm" property="topcatevo.onlinelearnlist">
				          <logic:iterate id="learnvo" name="learncontentForm" property="topcatevo.onlinelearnlist" indexId="ind">
						      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
						           title='<bean:write name="learnvo" property="caption"/>'
						           onclick="window.open('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productIdStr %>&contentcateid=-1&objecttype=<%=Learncontent.ObjectType %>&objectid=<bean:write name="learnvo" property="contentid"/>','learnwindow');"
						           />
						      <% hasmorelearn=true; %>
					      </logic:iterate>
					      </logic:present> &nbsp;
					      <logic:present name="learncontentForm" property="topcatevo.exerlist">
					      <logic:iterate id="exervo" name="learncontentForm" property="topcatevo.exerlist" indexId="ind_exer">
						      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/exam.png" 
							       title="<bean:write name="exervo" property="exername"/>"
							       onclick="window.open('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productIdStr %>&contentcateid=-1&objecttype=<%=Exercise.ObjectType %>&objectid=<bean:write name="exervo" property="exerid"/>','learnwindow');"
							       />
							   <% hasmorelearn=true; %>
						  </logic:iterate>
						  </logic:present>&nbsp;
						  <%if(topcatevoVar.getDownloadlearnlist()!=null && topcatevoVar.getDownloadlearnlist().size()>0){ %>
						      <img style="width: 23px;" src="../styles/imgs/filetype/filetype_download.png" 
							       title="has attachment to download"/>
							   <% hasmorelearn=true; %>
						  <%} %>
                       </td>
                       <%if(hasmorelearn){ %>
                       <td style="text-align: right;padding-right: 25px;">
                          <button type="button" class="button green medium fontBold" onclick="window.open('<%=WebConstant.WebContext %>/learncont/autolearn.do?queryVO.productbaseid=<%=productIdStr %>&queryVO.contentcateid=-1','learnwindow');">开始学习</button>
                       </td>
                       <%} %>
                    </tr>
                </table>
		   </li>
		   
		   <li style="background: #ffffff;text-align: center;">
		      <span style="text-align: center;font-weight: bolder;font-size:larger;">&dArr;</span>
		   </li>
		   <%} %>
		   <logic:present name="learncontentForm" property="catelist">
		   <logic:iterate id="datavo" name="learncontentForm" property="catelist" indexId="i" type="netTest.prodcont.vo.Contentcate">
	       <li>
	           <div style="border-bottom: 2px #cccccc solid;">
	           <a href="javascript:void(0);" 
	              class="<%if(datavo.hasSubList()||datavo.hasLearncontent()){ if(datavo.getPk().equals(pidVar)){out.print("collapseCate");}else{out.print("expendCate");} } %>" 
	              style="background-color:#DCEAFC;" 
	              onclick="showcontent('<%=datavo.getContentcateid() %>', 1, this, '<%=i %>');return false;">
	              <bean:write name="datavo" property="contentcatename"/>
	              &nbsp;&nbsp;
	               <%if(datavo.getPk().equals(contentcateidVar)){%>
	                    <img style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
	               <%} %>
	           </a>
                <%if(datavo.hasLearncontent()) { %>
		        <table id="cateLearnTableId_<%=i %>" class="cateLearnCont" style="display: <%if(datavo.getPk().equals(pidVar)||datavo.getPk().equals(contentcateidVar)){out.print("block");}else{out.print("none");} %>">
                   <tr>
                      <td style="text-align: left;">
                          <logic:present name="datavo" property="onlinelearnlist">
						  <logic:iterate id="learnvo" name="datavo" property="onlinelearnlist" indexId="ind">
					      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
					           title='<bean:write name="learnvo" property="caption"/>'
					           onclick="window.open('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productIdStr %>&objecttype=<%=Learncontent.ObjectType %>&objectid=<bean:write name="learnvo" property="contentid"/>&contentcateid=<bean:write name="learnvo" property="contentcateid"/>','learnwindow');"
					           />
					      </logic:iterate> 
					      </logic:present>&nbsp;
					      <logic:iterate id="exervo" name="datavo" property="exerlist" indexId="ind_exer">
						      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/exam.png" 
							       title="<bean:write name="exervo" property="exername"/>"
							       onclick="window.open('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productIdStr %>&objecttype=<%=Exercise.ObjectType %>&objectid=<bean:write name="exervo" property="exerid"/>&contentcateid=<bean:write name="exervo" property="contentcateid"/>','learnwindow');" />
						  </logic:iterate> &nbsp;
						  <%if(datavo.getDownloadlearnlist()!=null && datavo.getDownloadlearnlist().size()>0){ %>
						      <img style="width: 23px;" src="../styles/imgs/filetype/filetype_download.png" 
							       title="has attachment to download"/>
						  <%} %>
                      </td>
                      <td style="text-align: right;padding-right: 16px;">
                         <%if(datavo.getPk().equals(contentcateidVar)){%>
                         <img title="上次学习点" style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
                         <%} %>
                         <button type="button" class="button green medium fontBold" onclick="window.open('<%=WebConstant.WebContext %>/learncont/autolearn.do?queryVO.productbaseid=<%=productIdStr %>&queryVO.contentcateid=<%=datavo.getPk() %>','learnwindow');">开始学习</button>
                      </td>
                   </tr>
                </table>
                <%} %>
		        </div>
		        <logic:present name="datavo" property="sublist">
	            <ul id="cate_ul_<%=i %>" style="display: <% if(datavo.getPk().equals(contentcateidVar)||datavo.getPk().equals(pidVar)){out.print("block");}else{out.print("none");} %>">
	                <logic:iterate id="subdatavo" name="datavo" property="sublist" indexId="j" type="netTest.prodcont.vo.Contentcate">
		            <li id="cate<%=i %>sub<%=j %>_li" style="background-image:url('../styles/imgs/ico/greendot.png');background-position:6 10px;background-repeat:no-repeat;border-bottom: 2px #cccccc solid;" <% if(subdatavo.getPk().equals(contentcateidVar)){out.print("class='press'"); } %>>
		                <div style="margin-left:17px;border-left: 1px solid #cccccc;padding-left:5px;">
		                <a href="javascript:void(0);" onclick="showcontent('<%=subdatavo.getContentcateid() %>', 0);return false;"><bean:write name="subdatavo" property="contentcatename"/></a>
	                    <%if(subdatavo.hasLearncontent()) { %>
	                    <table style="border:0px; margin-left:30px; width: 97%">
	                       <tr>
	                          <td style="text-align: left;">
								 <logic:iterate id="learnvo" name="subdatavo" property="onlinelearnlist" indexId="ind">
							        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
							             title='<bean:write name="learnvo" property="caption"/>'
							             onclick="window.open('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productIdStr %>&objecttype=<%=Learncontent.ObjectType %>&objectid=<bean:write name="learnvo" property="contentid"/>&contentcateid=<bean:write name="learnvo" property="contentcateid"/>','learnwindow');"
							             />
							     </logic:iterate>&nbsp;
							     <logic:iterate id="exervo" name="subdatavo" property="exerlist" indexId="ind_exer">
							        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/exam.png" 
							             title="<bean:write name="exervo" property="exername"/>"
							             onclick="window.open('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productIdStr %>&objecttype=<%=Exercise.ObjectType %>&objectid=<bean:write name="exervo" property="exerid"/>&contentcateid=<bean:write name="exervo" property="contentcateid"/>','learnwindow');"
							             />
							     </logic:iterate>&nbsp;
							     <%if(subdatavo.getDownloadlearnlist()!=null && subdatavo.getDownloadlearnlist().size()>0){ %>
								      <img style="width: 23px;" src="../styles/imgs/filetype/filetype_download.png" 
									       title="has attachment to download"/>
								 <%} %>
	                          </td>
	                          <td style="text-align: right;padding-right: 25px;">
	                             <%if(subdatavo.getPk().equals(contentcateidVar)){%>
	                             <img title="上次学习点" style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
	                             <%} %>
	                             <button type="button" class="button green medium fontBold" onclick="window.open('<%=WebConstant.WebContext %>/learncont/autolearn.do?queryVO.productbaseid=<%=productIdStr %>&queryVO.contentcateid=<%=subdatavo.getPk() %>','learnwindow');">开始学习</button>
	                          </td>
	                       </tr>
	                    </table>
	                    <%} %>
	                    </div>
		            </li>
		            </logic:iterate>
		   	   </ul>
		   	   </logic:present>
	       </li>
		   </logic:iterate>
		   </logic:present>
		</ul>
	  </div>
	  
	  <div id="sideCommentDivTop" class="sideTopSubItem" style="width:53%;float:right;background-color: #DCEAFC" onclick="showtab('sideCommentDiv');return false;">&nbsp;&nbsp;<img alt="讨论区" src="../styles/imgs/discussion.png">&nbsp;&nbsp;<font style="font-size:x-large;;font-weight: bold;">讨论区</font></div>
	  <div id="discussDiv" style="width: 53%;float: right;padding-top: 10px;">
	      
	  </div>
	  <%} %>
	  
  </div>
	  
  <div id="buttomDiv"></div>
  </html:form>
  </div>
  
  <jsp:include flush="true" page="../../foot.jsp"></jsp:include>
  </div>
  
  </body>
  
  <script type="text/javascript" language="javascript">
	  
	  window.onload = function(){
		 showdiscuss('<%=contentcateidVar %>');
	  };
   </script>
</html:html>
