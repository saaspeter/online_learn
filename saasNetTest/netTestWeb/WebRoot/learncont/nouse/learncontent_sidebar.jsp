<!-- 带有学习目录折叠效果的 -->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, commonTool.constant.CommonConstant,netTest.learncont.vo.Learncontent,netTest.exercise.vo.Exercise" %>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
    <bean:define id="productidVar" name="learncontentForm" property="productbaseid" type="java.lang.Long"/>
    <bean:define id="contentcateidVar" name="learncontentForm" property="queryVO.contentcateid" type="java.lang.Long"/>
    <bean:define id="selectCatevoVar" name="learncontentForm" property="usecatevo" type="netTest.prodcont.vo.Contentcate"/>
    <bean:define id="topcatevoVar" name="learncontentForm" property="topcatevo" type="netTest.prodcont.vo.Contentcate"/>
    <% Long pidVar = null;
       if(selectCatevoVar!=null){
          pidVar = selectCatevoVar.getPid();
       }
       boolean hasmorelearn = false;
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
	<link rel="stylesheet" type="text/css" href="../styles/css/list.css">
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
	<script type="text/javascript">
	    function gochapter(productid, contentcateid){
	    	parent.gochapter(productid, contentcateid);
	    }
	    
	    function toggleFolder(obj, order){
	    	var ulobj = document.getElementById("cate_ul_"+order);
	    	var tableobj = document.getElementById("cateLearnTableId_"+order);
	    	var uldisplay = ulobj.style.display;
	    	// if obj class is:expendCate, means: it is expand flag, want to expand
	    	if(obj.className=="expendCate"){
	    		ulobj.style.display=(uldisplay == 'block')?'none' : 'block';
	    		if(typeof(tableobj)!="undefined" && tableobj!=null){
	    		   tableobj.style.display='block';
	    		}
	    		obj.className="collapseCate";
	    	}else if(obj.className=="collapseCate"){
	    		ulobj.style.display=(uldisplay == 'block')?'none' : 'block';
	    		if(typeof(tableobj)!="undefined" && tableobj!=null){
	    		   tableobj.style.display='none';
	    		}
	    		obj.className="expendCate";
	    	}
	    }
	    
	    function parentGo(url){
	    	parent.goUrl(url);
	    }
	</script>
  </head>

  <body>

	  <div style="width:100%;">
	     <ul id="navcate">
	       <%if(topcatevoVar!=null&&((topcatevoVar.getOnlinelearnlist()!=null&&topcatevoVar.getOnlinelearnlist().size()>0))
		                                   ||(topcatevoVar.getExerlist()!=null&&topcatevoVar.getExerlist().size()>0)) { %>
		    <li style="background-color:#DCEAFC;">
		        <a id="overviewId" href="javascript:void(0);" >课程学习总结:<%=productidVar %></a>
		        <table style="border: 0px;margin-left:30px;width: 97%">
                    <tr>
                       <td style="text-align: left;">
				          <logic:iterate id="learnvo" name="learncontentForm" property="topcatevo.onlinelearnlist" indexId="ind">
						      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
						           title='<bean:write name="learnvo" property="caption"/>'
						           onclick="parentGo('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productidVar %>&contentcateid=-1&objecttype=<%=Learncontent.ObjectType %>&objectid=<bean:write name="learnvo" property="contentid"/>');"
						           />
						      <% hasmorelearn=true; %>
					      </logic:iterate> &nbsp;&nbsp;
					      <logic:iterate id="exervo" name="learncontentForm" property="topcatevo.exerlist" indexId="ind_exer">
						      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/exam.png" 
							       title="<bean:write name="exervo" property="exername"/>"
							       onclick="openWin('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productidVar %>&contentcateid=-1&objecttype=<%=Exercise.ObjectType %>&objectid=<bean:write name="exervo" property="exerid"/>');"
							       />
							  <% hasmorelearn=true; %>
						  </logic:iterate>
                       </td>
                       <%if(hasmorelearn){ %>
                       <td style="text-align: right;padding-right: 25px;">
                          <button type="button" class="button green medium fontBold" onclick="parentGo('<%=WebConstant.WebContext %>/learncont/autolearn.do?queryVO.productbaseid=<%=productidVar %>&queryVO.contentcateid=-1');">开始学习</button>
                       </td>
                       <%} %>
                    </tr>
                </table>
		   </li>
		   <%} %>
		   <li style="background: #ffffff;text-align: center;"><span style="text-align: center;font-weight: bolder;font-size:larger;">&dArr;</span></li>
		   <logic:present name="learncontentForm" property="catelist">
		   <logic:iterate id="datavo" name="learncontentForm" property="catelist" indexId="i" type="netTest.prodcont.vo.Contentcate">
	       <li>
	           <div style="border-bottom: 2px #cccccc solid;">
	           <a href="javascript:void(0);" 
	              class="<%if(datavo.getSublist()!=null&&datavo.getSublist().size()>0){ if(datavo.getPk().equals(pidVar)){out.print("collapseCate");}else{out.print("expendCate");} } %>" 
	              style="background-color:#DCEAFC;" 
	              onclick="toggleFolder(this, '<%=i %>');return false;">
	              <bean:write name="datavo" property="contentcatename"/>
	           </a>
                <%if((datavo.getOnlinelearnlist()!=null&&datavo.getOnlinelearnlist().size()>0)
                        || (datavo.getExerlist()!=null&&datavo.getExerlist().size()>0)) { %>
		        <table id="cateLearnTableId_<%=i %>" class="cateLearnCont" style="display: <%if(datavo.getPk().equals(pidVar)||datavo.getPk().equals(contentcateidVar)){out.print("block");}else{out.print("none");} %>">
                   <tr>
                      <td style="text-align: left;">
                          <logic:present name="datavo" property="onlinelearnlist">
						  <logic:iterate id="learnvo" name="datavo" property="onlinelearnlist" indexId="ind">
					      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
					           title='<bean:write name="learnvo" property="caption"/>'
					           onclick="parentGo('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productidVar %>&objecttype=<%=Learncontent.ObjectType %>&objectid=<bean:write name="learnvo" property="contentid"/>&contentcateid=<bean:write name="learnvo" property="contentcateid"/>');"
					           />
					      </logic:iterate>
					      </logic:present>
					      &nbsp;&nbsp;
					      <logic:iterate id="exervo" name="datavo" property="exerlist" indexId="ind_exer">
						      <img style="width: 23px;cursor: pointer;" src="../styles/imgs/exam.png" 
							       title="<bean:write name="exervo" property="exername"/>"
							       onclick="parentGo('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productidVar %>&objecttype=<%=Exercise.ObjectType %>&objectid=<bean:write name="exervo" property="exerid"/>&contentcateid=<bean:write name="exervo" property="contentcateid"/>');"
							       />
						  </logic:iterate>
                      </td>
                      <td style="text-align: right;padding-right: 16px;">
                         <%if(datavo.getPk().equals(contentcateidVar)){%>
                         <img title="上次学习点" style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
                         <%} %>
                         <button type="button" class="button green medium fontBold" onclick="parentGo('<%=WebConstant.WebContext %>/learncont/autolearn.do?queryVO.productbaseid=<%=productidVar %>&queryVO.contentcateid=<%=datavo.getPk() %>');">开始学习</button>
                      </td>
                   </tr>
                </table>
                <%} %>
		        </div>
	            <ul id="cate_ul_<%=i %>" style="display: <% if(datavo.getPk().equals(contentcateidVar)||datavo.getPk().equals(pidVar)){out.print("block");}else{out.print("none");} %>">
	                <logic:present name="datavo" property="sublist">
	                <logic:iterate id="subdatavo" name="datavo" property="sublist" indexId="j" type="netTest.prodcont.vo.Contentcate">
		            <li id="cate<%=i %>sub<%=j %>_li" style="background-image:url('../styles/imgs/ico/greendot.png');background-position:6 10px;background-repeat:no-repeat;border-bottom: 2px #cccccc solid;" <%if(subdatavo.getPk().equals(contentcateidVar)){out.print("class='press'"); } %>>
		                <div style="margin-left:17px;border-left: 1px solid #cccccc;padding-left:5px;">
		                <a href="javascript:void(0);"><bean:write name="subdatavo" property="contentcatename"/></a>
	                    <%if((subdatavo.getOnlinelearnlist()!=null&&subdatavo.getOnlinelearnlist().size()>0)
                             || (subdatavo.getExerlist()!=null&&subdatavo.getExerlist().size()>0)) { %>
	                    <table style="border:0px; margin-left:30px; width: 97%">
	                       <tr>
	                          <td style="text-align: left;">
								 <logic:iterate id="learnvo" name="subdatavo" property="onlinelearnlist" indexId="ind">
							        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/filetype/<bean:write name="learnvo" property="fileicon"/>" 
							             title='<bean:write name="learnvo" property="caption"/>'
							             onclick="parentGo('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productidVar %>&objecttype=<%=Learncontent.ObjectType %>&objectid=<bean:write name="learnvo" property="contentid"/>&contentcateid=<bean:write name="learnvo" property="contentcateid"/>');"
							             />
							     </logic:iterate>
							     <logic:iterate id="exervo" name="subdatavo" property="exerlist" indexId="ind_exer">
							        <img style="width: 23px;cursor: pointer;" src="../styles/imgs/exam.png" 
							             title="<bean:write name="exervo" property="exername"/>"
							             onclick="parentGo('<%=WebConstant.WebContext %>/learncont/learn_main.jsp?productid=<%=productidVar %>&objecttype=<%=Exercise.ObjectType %>&objectid=<bean:write name="exervo" property="exerid"/>&contentcateid=<bean:write name="exervo" property="contentcateid"/>');"
							             />
							     </logic:iterate>
	                          </td>
	                          <td style="text-align: right;padding-right: 25px;">
	                             <%if(subdatavo.getPk().equals(contentcateidVar)){%>
	                             <img title="上次学习点" style="width: 20px;border: 0px;" src="../styles/imgs/enter.png">
	                             <%} %>
	                             <button type="button" class="button green medium fontBold" onclick="parentGo('<%=WebConstant.WebContext %>/learncont/autolearn.do?queryVO.productbaseid=<%=productidVar %>&queryVO.contentcateid=<%=subdatavo.getPk() %>');">开始学习</button>
	                          </td>
	                       </tr>
	                    </table>
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

  </body>
</html:html>
