<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.product.vo.Openactivity, netTest.product.constant.UserproductConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

<bean:define id="jsSuffix" name="openactivityForm" property="jsSuffix" type="java.lang.String"/>
<bean:define id="editType" name="openactivityForm" property="editType" type="java.lang.Integer"/>
<bean:define id="showtype" name="openactivityForm" property="showtype" type="java.lang.Integer"/>
<bean:define id="productid" name="openactivityForm" property="vo.productid" type="java.lang.Long"/>
<bean:define id="shopidVar" name="openactivityForm" property="vo.shopid" type="java.lang.Long"></bean:define>
<bean:define id="categoryNameVar" name="openactivityForm" property="categoryName" type="java.lang.String"></bean:define>
  <%String categoryDisableStr=""; 
    if(productid!=null){ categoryDisableStr="disabled=\"disabled\""; } 
    if(categoryNameVar==null) { categoryNameVar=""; }
    String productidVar = (productid==null)?"":productid.toString();
    String saveBackUrl = "%2fproduct%2flistOpenactivity.do%3fshowtype%3d"+showtype;
    if(showtype==3){
    	saveBackUrl += "%26queryVO.productid="+productidVar;
    }
  %>
      
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="<%=WebConstant.WebContext %>/styles/css/edit.css">
	<link rel="stylesheet" href="<%=WebConstant.WebContext %>/bower_components/bootstrap/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<%=WebConstant.WebContext %>/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" />
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/pageAction.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/commonlogic.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/utiltool.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/ckeditor/ckeditor.js"></script>
	<script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/engine.js'></script>
    <script type='text/javascript' src='<%=WebConstant.WebContext %>/dwr/interface/countryregion.js'></script>
	<script type='text/javascript' src="<%=WebConstant.WebContext %>/styles/script/region/region.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/bower_components/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/bower_components/moment/min/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=WebConstant.WebContext %>/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript">
       function selectCate(){
 	      var url = "#"+webContext+"/productcategory/shopprodcatetreepage.do?urltype=1&shopid=<%=shopidVar %>";
 	      newDiv(url,1,1,270,300);
 	   }
       
       function selectCate_CB(id,name,param){
    	   if(id==null){
    		   return;
    	   }
    	   clearDiv();
           if(id!=null && id!=''){
         	  document.getElementById("categoryNameId").value = name;
        	  document.getElementById("categoryidId").value = id;
        	  clearMessagePage();
           }
       }
       
	   function clearCategory(){
		   document.getElementById("categoryNameId").value = '';
     	   document.getElementById("categoryidId").value = '';
     	   document.getElementById("categoryNameId").disabled = 'disabled';
     	   document.getElementById("categoryidId").disabled = 'disabled';
       }
       
       function selProduct_CB(id,name){
   	   	  var old_prod = document.getElementById("productidId").value;
   	      if(id!=null && id!=""&& id!=old_prod){
   	         document.getElementById("productidId").value=id;
   	         document.getElementById("productnameId").value=name;
   	         clearCategory();
   	      }
   	      clearDiv("alertFram2");
   	   }
       
       function removeCourse(){
    	   document.getElementById("productidId").value='';
 	       document.getElementById("productnameId").value='';
 	       document.getElementById("categoryNameId").disabled = '';
    	   document.getElementById("categoryidId").disabled = '';
       }
    
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action="/product/saveOpenactivity.do" method="post">
     <input id="backUrlEncode" type="hidden" name="backUrlEncode" value="<%=saveBackUrl %>">
	 <input type="hidden" name="vo.activityid" value="<bean:write name="openactivityForm" property="vo.activityid"/>">
	 
	  <div id="fieldsTitleDiv"><bean:message key="netTest.commonpage.newRecord"/></div>
	  
	  <div id="displayMessDiv">
          <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>

      <div style="margin-top:25px;">
	      <table class="formTable2">
	          <tr>
		         <td align="right"><font color="red">*</font>活动名:</td>
		         <td colspan="3">
		             <input type="text" style="width: 95%;" name="vo.name" usage="notempty,max-length:60" fie="活动名" value="<bean:write name="openactivityForm" property="vo.name"/>" />
		         </td>
		      </tr>
		      <tr>
		         <td align="right"><font color="red">*</font>开始时间:</td>
		         <td style="width:40%;">
		             <div class='input-group date' id='datetimepicker1'>
	                    <input type='text' name="vo.starttime" value='' fie="开始时间" usage="notempty" class="form-control" />
	                    <span class="input-group-addon">
	                        <span class="glyphicon glyphicon-calendar"></span>
	                    </span>
	                 </div>
		         </td>
		         <td align="right"><font color="red">*</font>结束时间:</td>
		         <td style="width:30%;">
		             <div class='input-group date' id='datetimepicker2'>
	                    <input type='text' name="vo.endtime" value='' fie="结束时间" usage="notempty" class="form-control" />
	                    <span class="input-group-addon">
	                        <span class="glyphicon glyphicon-calendar"></span>
	                    </span>
	                 </div>
		         </td>
		      </tr>
		      <tr>
		         <td align="right"><font color="red">*</font>内容目录:</td>
		         <td>
		             <input id="categoryNameId" value="<%=categoryNameVar %>" readonly="readonly" usage="notempty" fie="课程目录" placeholder="点击选择课程目录" onclick="selectCate()" <%=categoryDisableStr %> />
			         <input id="categoryidId" type="hidden" name="vo.categoryid" usage="notempty" value='<bean:write name="openactivityForm" property="vo.categoryid"/>' <%=categoryDisableStr %> />
		             <img src="../styles/imgs/help.gif" title='活动内容分类，如果选择了所属课程，则课程内容目录自动填充'/>
		         </td>
		         <td align="right">所属课程:</td>
		         <td>
		             <nobr>
		             <%if(productid==null){ %>
		             <input id="productnameId" type="text" name="vo.productname" value='<bean:write name="openactivityForm" property="vo.productname"/>' readonly="readonly" onclick="selectProd(this,'<%=UserproductConstant.ProdUseType_operatorMag %>')" />
			         <%}else { %>
			         <bean:write name="openactivityForm" property="vo.productname"/>
			         <%} %>
			         <input id="productidId" type="hidden" name="vo.productid" value='<%=productidVar %>' />
		         	 <%if(productid==null){ %>
		         	 <img src="../styles/imgs/ico/reset.gif" alt="删除" style="cursor: pointer;width:25px;" onclick="removeCourse('productidId','productnameId');" title="删除课程">
		             <%} %>
		             </nobr>
		         </td>
		      </tr>
		      <tr>
		         <td align="right"><font color="red">*</font>参与方式:</td>
		         <td>
		             <html:select styleId="jointypeId" name="openactivityForm" property="vo.jointype" styleClass="select_second" onchange="loadJointype(null)">
					    <html:optionsSaas consCode="OpenActivity.JoinType"/>
					 </html:select>
		         </td>
		         <td align="right"><font color="red">*</font>活动状态:</td>
		         <td>
		             <html:select name="openactivityForm" property="vo.status" styleClass="select_second">
					    <html:optionsSaas consCode="OpenActivity.Status"/>
					 </html:select>
		         </td>
		      </tr>
		      <tr>
		         <td align="right"><font color="red">*</font>联系方式:</td>
		         <td colspan="3">
		             <input type="text" name="vo.contactinfo" style="width: 95%;" fie="联系方式" usage="notempty,max-length:200" value="<bean:write name="openactivityForm" property="vo.contactinfo"/>" />
		         </td>
		      </tr>
		      <tr id="onlineurlTrId">
		         <td align="right">在线加会方式:</td>
		         <td colspan="3">
		             <input type="text" id="onlineurlId" style="width: 95%;" name="vo.onlineurl" value='<bean:write name="openactivityForm" property="vo.onlineurl"/>' />
		         </td>
		      </tr>
		      <tr id="cityTrId">
		         <td align="right"><font color="red">*</font>所在地区:</td>
		         <td>
		            <table style="border:0px;">
				        <tr>
				           <td>
				              <span>
				                  <select id="provinceId" name="privCode" onchange="selectRegion()" class="font_input" fie="所在地区" usage="notempty"></select>(省)
	                          </span>
	                       </td>
	                       <td id="cityId_container_id" style="text-align: left;">
	                          <select id="cityId" name="cityCode" class="font_input"></select>
	                       </td>
				           <td id="provinceId_tip_td" class="login_fieldDesc"><i>&nbsp;</i><span id="provinceId_tip"></span></td>
				        </tr>
			        </table>
		         </td>
		         <td align="right"><font color="red">*</font>具体地址:</td>
		         <td>
		             <textarea rows="4" id="placeId" cols="40" fie="具体地址" name="vo.place" usage="notempty,max-length:200"><bean:write name="openactivityForm" property="vo.place"/></textarea>
		         </td>
		      </tr>
		      <tr>
		         <td align="right"><font color="red">*</font>活动介绍:</td>
		         <td colspan="3">
		             <textarea cols="110" id="editor1" name="vo.description" rows="6" fie="活动介绍"><bean:write name="openactivityForm" property="vo.description"/></textarea>
					   <script type="text/javascript">
					       var p_editor = CKEDITOR.replace( 'editor1',
					       {
					    		toolbar : 'MyStandard',
					      		height : '400px', 
					      		filebrowserImageUploadUrl : '<%=WebConstant.WebContext %>/ckeditor/uploader?Type=Image&rootobjecttype=shop&parentObjectType=<%=Openactivity.ObjectType %>&parentObjectId=<bean:write name="openactivityForm" property="vo.activityid"/>'
					       });
					   </script>
		         </td>
		      </tr>
	      </table>
	  </div>

	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" onclick="submitForm('editForm');"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" onclick="goUrl('/product/listOpenactivity.do?showtype=<%=showtype %>&queryVO.productid=<%=productidVar %>');return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	<script type="text/JavaScript">
    <!-- 
        window.onload=function(){
           var localeidVar = '<bean:write name="openactivityForm" property="vo.localeid"/>';
           initRegion(localeidVar, '<bean:write name="openactivityForm" property="privCode"/>','<bean:write name="openactivityForm" property="cityCode"/>');
           $(function () {
               $('#datetimepicker1').datetimepicker({
                   locale: 'zh-cn',
                   format: 'YYYY-MM-DD HH:mm',
                   defaultDate: '<bean:writeDate name="openactivityForm" property="vo.starttime" formatKey="Common.DateFormatType.DateTime"/>'
               });
               $('#datetimepicker2').datetimepicker({
                   locale: 'zh-cn',
                   format: 'YYYY-MM-DD HH:mm',
                   defaultDate: '<bean:writeDate name="openactivityForm" property="vo.endtime" formatKey="Common.DateFormatType.DateTime"/>'
               });
           });
           // 
           loadJointype('<bean:write name="openactivityForm" property="vo.jointype"/>');
        }
    
        function loadJointype(jointype_value){
           if(jointype_value==null || jointype_value==''){
        	   jointype_value = $('#jointypeId').val();
           }
           if(jointype_value=='<%=Openactivity.JoinType_Offline %>'){
        	   $('#onlineurlTrId').css('display','none');
        	   $('#onlineurlId').attr("disabled","disabled");
        	   $("#cityId").removeAttr("disabled");
        	   $("#provinceId").removeAttr("disabled");
        	   $("#placeId").removeAttr("disabled");
        	   $("#cityTrId").css('display','');
           }else {
        	   $('#onlineurlTrId').css('display','');
        	   $('#onlineurlId').removeAttr("disabled");
        	   $("#cityId").attr("disabled","disabled");
        	   $("#provinceId").attr("disabled","disabled");
        	   $("#placeId").attr("disabled","disabled");
        	   $("#cityTrId").css('display','none');
           }
        }
    //-->
    </script>
  </body>
</html:html>
