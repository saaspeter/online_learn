<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.product.vo.Productbase"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="objectidVar" name="usecommentForm" property="vo.objectid"></bean:define>
    <bean:define id="objecttypeVar" name="usecommentForm" property="vo.objecttype"></bean:define>
    <bean:define id="commentidVar" name="usecommentForm" property="vo.commentid"></bean:define>
    <bean:define id="gradeVar" name="usecommentForm" property="vo.grade" type="java.lang.Short"></bean:define>
    <%String gradeStrVar = (gradeVar==null)?null:gradeVar.toString(); 
      Object CheckResultObj = request.getAttribute("CheckResult");
      String CheckResult = (CheckResultObj==null)?"":CheckResultObj.toString();
      String ErrorMessage = "";
      if("0".equals(CheckResult)){
    	  ErrorMessage = (String)request.getAttribute("ErrorMessage");
      }
    %>
    <title>edit comment</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <style type="text/css">
        /** thanks to: zhangxinxu@zhangxinxu.com **/
	    .star_bg {
		    width: 120px; height: 20px;
		    background: url(../styles/imgs/star.png) repeat-x;
		    position: relative;
		    overflow: hidden;
		    margin-left: 20px;
		}
		.star {
		    height: 100%; width: 24px;
		    line-height: 6em;
		    position: absolute;
		    z-index: 3;
		}
		.star:hover {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px!important;
		    left: 0; z-index: 2;
		}
		.star_1 { left: 0; }
		.star_2 { left: 24px; }
		.star_3 { left: 48px; }
		.star_4 { left: 72px; }
		.star_5 { left: 96px; }
		.star_1:hover { width: 24px; }
		.star_2:hover { width: 48px; }
		.star_3:hover { width: 72px; }
		.star_4:hover { width: 96px; }
		.star_5:hover { width: 120px; }
		
		label { 
		    display: block; _display:inline;
		    height: 100%; width: 100%;
		    cursor: pointer;
		}
		
		/* 幕后的英雄，单选按钮 */
		.score { position: absolute; clip: rect(0 0 0 0); }
		.score:checked + .star {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px;
		    left: 0; z-index: 1;
		}
		.score_1:checked ~ .star_1 { width: 24px; }
		.score_2:checked ~ .star_2 { width: 48px; }
		.score_3:checked ~ .star_3 { width: 72px; }
		.score_4:checked ~ .star_4 { width: 96px; }
		.score_5:checked ~ .star_5 { width: 120px; }
		
		.star_bg:hover .star {  background-image: none; }
		
		/* for IE6-IE8 JS 交互 */
		.star_checked {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px;
		    left: 0; z-index: 1;
		}
    </style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">  
        window.onload=function(){
        	var checkresult = '<%=CheckResult %>';
    	    if('0'==checkresult){
    	    	alert('<%=ErrorMessage %>');
    	    	window.parent.clearDiv();
    	    }
        }
    
        function saveComments(){
        	var grade = getCheckedValue("score");
        	if(grade==null){
        		alert('请选择得分等级!');
        		return;
        	}
        	var content = document.getElementById("editor1").value;
   		    var url = "saveprodusecomment.do";
   		    var param = "vo.objecttype=<%=objecttypeVar %>&vo.objectid=<%=objectidVar %>&vo.grade="+grade
   		                +"&vo.commentid=<%=commentidVar %>&vo.content="+content;
   		    
            var rtnObj = toAjaxUrl(url,false,param);
            if(rtnObj.result=="1"){ // forward the success page,if success
            	var paramobj = new Object();
            	paramobj.commentid = '<%=commentidVar %>';
            	paramobj.grade = grade;
            	paramobj.content = content;
           	    window.parent.shownewcomment(rtnObj.info, paramobj);
            }else if(rtnObj.result=="2"){
           	   alert(rtnObj.info);
            }else{
               alert("systemError");
            }
        }
        
        function delComment(){
   		    var url = "deleteusecomment.do";
   		    var param = "vo.commentid=<%=commentidVar %>";
            var rtnObj = toAjaxUrl(url,false,param);
            if(rtnObj.result=="1"){ // forward the success page,if success
           	    window.parent.shownewcomment(rtnObj.info, '<%=commentidVar %>');
            }else if(rtnObj.result=="2"){
           	    alert(rtnObj.info);
            }else{
                alert("systemError");
            }
        }
    
    </script>
  </head>
  
  <body>
	<div style="text-align: center;width: 100%;">
	
      <div style="width: 99%;text-align: center;">
      <%if(commentidVar!=null){ %>
	           我的评价
	  <%} else{%>
	  	新增评价
	  <%} %>
	  </div>
	  <p/>
	  
	  <div style="width: 99%;text-align: center;">
	      <textarea cols="40" style="border:1px solid #888888" id="editor1" name="vo.content" rows="2"><bean:write name="usecommentForm" property="vo.content"/></textarea>
	  </div>
	  <p>
	  <div id="starBg" class="star_bg">                    	
		  <input type="radio" id="starScore1" class="score score_1" value="1" name="score" <% if("1".equals(gradeStrVar)){out.print("checked='checked'");} %>>
		  <a href="#starScore1" class="star star_1" title="差"><label for="starScore1">差</label></a>
		  <input type="radio" id="starScore2" class="score score_2" value="2" name="score" <% if("2".equals(gradeStrVar)){out.print("checked='checked'");} %>>
		  <a href="#starScore2" class="star star_2" title="较差"><label for="starScore2">较差</label></a>
		  <input type="radio" id="starScore3" class="score score_3" value="3" name="score" <% if("3".equals(gradeStrVar)){out.print("checked='checked'");} %>>
		  <a href="#starScore3" class="star star_3" title="普通"><label for="starScore3">普通</label></a>
		  <input type="radio" id="starScore4" class="score score_4" value="4" name="score" <% if("4".equals(gradeStrVar)){out.print("checked='checked'");} %>>
		  <a href="#starScore4" class="star star_4" title="较好"><label for="starScore4">较好</label></a>
		  <input type="radio" id="starScore5" class="score score_5" value="5" name="score" <% if("5".equals(gradeStrVar)){out.print("checked='checked'");} %>>
		  <a href="#5" class="star star_5" title="很好"><label for="starScore5">很好</label></a>
	  </div>
	  <p/>
	  <div style="width: 99%;text-align: center;">
	  	 <input type="button" value="提交" onclick="saveComments()"/>
	  	 <%if(commentidVar!=null){ %>
	  	      <button type="button" onclick="delComment()">删除我的评价</button>
	  	 <%} %>
	  	 <button type="button" onclick="window.parent.clearDiv();">取消</button>
	  </div>
	
	</div>
  </body>
</html:html>
