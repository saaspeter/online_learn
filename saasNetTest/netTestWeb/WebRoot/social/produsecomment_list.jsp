<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant,netTest.product.vo.Productbase"%>
<%@ include file="/pubs/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="objectidVar" name="usecommentForm" property="vo.objectid"></bean:define>
    <bean:define id="commentavggrade" name="usecommentForm" property="commentavggrade"></bean:define>
    <bean:define id="commentusernumber" name="usecommentForm" property="commentusernumber"></bean:define>
    <title>course comment list</title>
    <link rel="stylesheet" type="text/css" media="screen" href="<%=WebConstant.WebContext %>/styles/css/list.css" />
    <style type="text/css">
        .{
		    margin:0px;
		}
		
		#menu5{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		.parentCommentTdStyle{
		   border-bottom:solid 3px #EEEEEE;
		}
		
		.subCommentTdStyle{
		   background: #FEEFEF;
		   padding: 3px;
		}
		
		.subCommentFunDivStyle{
		   text-align: right;
		   clear: both;
		   width: 100%;
		}
		
		/** five start **/
		.star_bg {
		    width: 120px; height: 20px;
		    background: url(../styles/imgs/star.png) repeat-x;
		    position: relative;
		    overflow: hidden;
		    margin-left: 10px;
		}
		.star {
		    height: 100%; 
		    line-height: 6em;
		    position: absolute;
		    z-index: 3;
		}
			
		.star_checked1 {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px; width: 24px;
		    left: 0; z-index: 1;
		}
		.star_checked2 {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px; width: 48px;
		    left: 0; z-index: 1;
		}
		.star_checked3 {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px; width: 72px; 
		    left: 0; z-index: 1;
		}
		.star_checked4 {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px; width: 96px; 
		    left: 0; z-index: 1;
		}
		.star_checked5 {    
		    background: url(../styles/imgs/star.png) repeat-x 0 -20px; width: 120px; 
		    left: 0; z-index: 1;
		}
    </style>
	<script type="text/javascript" src="../styles/script/pageAction.js"></script>
  </head>
  
  <body>
  <html:form styleId="list" action="/social/listusecomment.do">
  <input type="hidden" name="vo.objecttype" value="<%=Productbase.ObjectType %>"/>
  <input type="hidden" name="vo.objectid" value="<%=objectidVar %>"/>
  <input type="hidden" id="pageIndexId" name="pageIndex"/>
  <input type="hidden" id="totalDataNumberId" name="totalDataNumber"/>
  <div>
      
	  <div id="DataTableDiv" style="margin: 7px; width: 99%;">
	       <table id="comtableId" class="listDataTable">
	          <thead>
	             <tr class="listDataTableHead">
	                 <td>
	                                                         平均得分: <bean:write name="usecommentForm" property="commentavggrade"/> &nbsp; , <img src="../styles/imgs/person.gif" height="17px"> 共<%=commentusernumber %>人评价
	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     <button type="button" onclick="newDiv('/social/editusecomment.do?vo.objecttype=<%=Productbase.ObjectType %>&vo.objectid=<%=objectidVar %>',1,1,400,300,null,100);">
	                     <logic:present name="usecommentForm" property="vo.commentid">
	                         	我的评价
	                     </logic:present>
	                     <logic:notPresent name="usecommentForm" property="vo.commentid">
	                     		新增评价
	                     </logic:notPresent>
	                     </button>
	                 </td>
	             </tr>
	          </thead>
	          <tbody>
	             <logic:present name="pageList">
	             <logic:iterate id="vo" name="pageList" indexId="ind" >
	             <tr id="TrId<bean:write name="vo" property="commentid"/>">
	                 <td style="text-align: left;">
	                    <div style="height: 25px;">
                            <div class="star_bg" style="float: left;"> 
		  				       <span class='star star_checked<bean:write name="vo" property="grade"/>'></span>
	  					    </div>
	  					    &nbsp;<bean:write name="vo" property="creatordisplayname"/>
	  					    &nbsp;<bean:writeDate name="vo" property="updatedate" formatKey="Common.DateFormatType.Date"/>
						</div>
						<div style="text-align: left; margin-left: 10px;">
	                        <bean:write name="vo" property="content"/>
						</div>
	                 </td>
	             </tr>
	             </logic:iterate>
	             </logic:present>
	          </tbody>
	       </table>
	  </div>
      
      <jsp:include flush="true" page="/pubs/footpage.jsp"></jsp:include>
    
  </div>
  </html:form>
  <script type="text/javascript">

	 function shownewcomment(result, paramobj){
		 submitForm("list");
	 }
	 
	 window.onload = function(){
 		 if(typeof(parent.adjustHeightInBuy)!='undefined'){
 			 parent.adjustHeightInBuy(document.body.scrollHeight);
 	     }
 	  };
	 
  </script>
  </body>
</html:html>
