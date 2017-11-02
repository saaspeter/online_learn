<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.vo.Productbase, commonTool.constant.PayTypeConstant, netTest.product.constant.ProductConstant" %>
<%@ include file="/pubs/taglibs.jsp" %>
<bean:define id="logoimageVar" name="productForm" property="vo.logoimage" type="java.lang.String"></bean:define>
<bean:define id="currentlearnusernum" name="productForm" property="vo.currentlearnusernum" type="java.lang.Integer"></bean:define>
<bean:define id="prodstatus" name="productForm" property="vo.status" type="java.lang.Short"></bean:define>
<%
    String shopidVar = request.getParameter("shopid");
    String productlogo = "";
    if(logoimageVar!=null){
    	productlogo = WebConstant.FileContext + logoimageVar;
    }
    if(currentlearnusernum==null){
    	currentlearnusernum = 0;
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <bean:define id="productbaseidPage" name="productForm" property="vo.productbaseid" type="java.lang.Long"/>
    <bean:define id="paytypeVar" name="productForm" property="vo.paytype" type="java.lang.Short"/>
    <title>课程详细信息</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

    <link href="../styles/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet"/>
	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<link rel="stylesheet" type="text/css" href="../styles/css/tab/simpleTab2.css" />
	<link rel="stylesheet" type="text/css" href="../styles/css/progress_trackers.css">
	<style type="text/css">
		<!--
		
		*{
		   margin: 0px;
		}
		
		#menu5{
		  display: block;
		  color: #667;
		  background-color: #ec8;
		}
		
		#leftbar1{
		   float:left;
		   width: 13%;
		}
		
		#maincontent1{
		    float:left;
		    width: 85%;
		    border-left: 1px #cccccc solid;
		    margin-left: 6px;
		}
		
		.productCover{
		    width: 200px; height:100px;cursor: pointer;
		}
		
		-->
	</style>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript">
        // control flag in leftbar jsp
        var iframego = 1;
    </script>

  </head>
  
 <body>
    <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<bean:write name="productForm" property="backUrlEncode"/>">	
	<div class="col-xs-12 col-md-9 center-block">
	
	 <jsp:include flush="true" page='<%="../shop/banner_shop.jsp?shopid="+shopidVar %>'></jsp:include>

	 <div style="height:15px; clear:both;"></div>
	 
	 <div>
	 
		<div id="leftbar1">
		    <jsp:include page="../leftbar_netTest.jsp"></jsp:include>
		</div>
	    
	    <div id="maincontent1">
		<div id="fieldsTitleDiv">
		    <table style="border: 0px; width: 100%;">
		        <tr>
		            <td style="margin: 5px;width: 240px;">
		                <img id="logoimgId" src="<%=productlogo %>" alt="上传课程封面图片" title="点击更换课程图片" 
		                     class='productCover <%if(productlogo==null||"".equals(productlogo)){out.print("alertBorder");} %>'
		                     onclick="openWin('/pubs/fileupload.jsp?rootobjecttype=shop&parentObjectType=<%=Productbase.ObjectType %>&parentObjectId=<%=productbaseidPage %>',400,200,'no','no');">
		            </td>
		            <td style="text-align: center;font-size: x-large;"><bean:write name="productForm" property="vo.productname"/></td>
		        </tr>
		    </table>
		</div>
		
		<div id="errorDisplayDiv"></div>
		
		<div id="fieldDisDiv">
		   <table class="formTable">
		       <tr>
		          <td style="text-align: right;">产品目录:</td>
		          <td><bean:write name="productForm" property="vo.categoryname"/></td>
		          <td style="text-align: right;">课程开放性:</td>
		          <td><bean:writeSaas name="productForm" property="vo.isopen" consCode="netTest.ProductConstant.Isopen"/></td>
		       </tr>
		       <tr>
		          <td style="text-align: right;">付款方式:</td>
		          <td><bean:writeSaas name="productForm" property="vo.paytype" consCode="Common.PayTypeConstant.PayType"/>
		              <%if(PayTypeConstant.PayType_useday_somedays.equals(paytypeVar)){ %>
		                  (学习天数:<bean:write name="productForm" property="vo.uselimitnum"/>)
		              <%}else if(PayTypeConstant.PayType_usetimes_sometimes.equals(paytypeVar)){ %>
		                  (学习次数:<bean:write name="productForm" property="vo.uselimitnum"/>)
		              <%} %>
		          </td>
		          <td style="text-align: right;">产品价格:</td>
		          <td><bean:write name="productForm" property="vo.productprice"/></td>
		       </tr>
		       <tr>
		          <td style="text-align: right;">课程状态:</td>
		          <td <%if(ProductConstant.Status_preparing.equals(prodstatus)) out.print("class='alertFont'"); %>><bean:writeSaas name="productForm" property="vo.status" consCode="netTest.ProductConstant.status"/>
		              <%if(ProductConstant.Status_preparing.equals(prodstatus)) { %>
		                   <img src="../styles/imgs/help.gif" title="学员暂时不能学习本课程,请完善学习内容后,点击 发布课程 "/>
		              <% } %>
		          </td>
		          <td colspan="2">本课程当前有  <%=currentlearnusernum %> 人</td>
		       </tr>
		   </table>
		</div>
		
		<div id="functionBarButtomDiv">
		   <ul>
			  <li><button type="button" class="btn btn-success" onclick="goUrl('/product/editproduct.do?productbaseid=<%=productbaseidPage %>&backUrlEncode=');">
			                  编辑基本信息</button>
			  </li>
			  <%if(ProductConstant.Status_preparing.equals(prodstatus)) { %>
			  <li><button type="button" class="btn btn-success" onclick="goUrl('/product/saveproduct.do?vo.productbaseid=<%=productbaseidPage %>&vo.status=<%=ProductConstant.Status_valid %>&backUrlEncode=','backUrlEncode');">
			                  发布课程</button><img src="../styles/imgs/help.gif" style="width:35px;" title="发布课程后用户才能查看并学习该课程。点击查看发布课程流程"
			                  onclick="document.getElementById('publishflowId').style.display='block'"/>
			  </li>
			  <% } %>
		   </ul>
		</div>
		
		<div id="publishflowId" style="display: none;">
          <ol class="steps" style="margin-bottom: 5px;">
	        <li class="step1"><span><a href="javascript:void(0)" title="可以在'课程人员管理'菜单中指定课程管理员，由其继续完成课程发布和管理">设置课程管理员( 可选? )</a></span></li>
	        <li class="step2"><span>填写课程介绍</span></li>
	        <li class="step3"><span>设置课程目录</span></li>
	        <li class="step4"><span>添程视频/文档/练习</span></li>
	        <li class="step5"><span>发布课程</span></li>
	      </ol>
        </div>
		
		<div id="displayMessDiv">
		      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
		</div>
		
		<div style="height:auto; width:98%; clear:both;margin: 7px;">
		   <ul class="tabnav">
		      <li id="tab1" class='selectTab'><a href="javascript:void(0)" onclick="showTab(1,3)">课程介绍</a></li>
			  <li id="tab2"><a href="javascript:void(0)" onclick="showTab(2,3)">课程目录</a></li>
		   </ul>
		</div>
		
		<div id="content1" style="width:98%;margin: 7px;">
		   <iframe id="iframecontent1" name="iframecontent1" width="100%" height="100%" src="<%=WebConstant.WebContext %>/product/viewproddescedit.do?vo.productbaseid=<%=productbaseidPage %>" scrolling="auto" frameborder="0"></iframe>
		</div>
		
		<div id="content2" style="width:98%;margin: 7px;display:none">
		   <iframe id="iframecontent2" name="iframecontent2" width="100%" height="100%" src="<%=WebConstant.WebContext %>/prodcont/onlyviewprodcate.do?productbaseid=<%=productbaseidPage %>&canedit=1" scrolling="auto" frameborder="0"></iframe>
		</div>
		
		</div>
		<jsp:include flush="true" page="../foot.jsp"></jsp:include>
	 </div>
	 
	 </div>

  </body>
  
   <script type="text/javascript" language="javascript">  
	  function iFrameHeight(iframeid, height) {   
		 var ifm= document.getElementById(iframeid); 
		 var contentid = iframeid.substring(6);
		 if(height!=null&&height!=''){
		    document.getElementById(contentid).style.height = height;
		 }else {
		    var subWeb = ifm.contentDocument;  
		    if(typeof(subWeb)=='undefined'){
		       subWeb = ifm.document;
		    }  
			if(ifm != null && subWeb != null) {
			   document.getElementById(contentid).style.height = subWeb.body.scrollHeight;
			} 
		 }
		   
	  }
	  
	  function editContCate(id, name){
         var url = "#<%=WebConstant.WebContext %>/prodcont/contentcate_main.jsp?productbaseid="+id;
         newDiv(url,1,1,600,400);
	  }
	  
	  
	  function callbackuploadfile(uploadfilename, fileurl, filesize){
		  if(fileurl!=null && fileurl!=''){
			  document.getElementById("logoimgId").src = fileurl+"?"+Math.random();
		  }
	  }
   </script>
  
</html:html>
