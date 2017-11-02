<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTestWeb.base.WebConstant, netTest.learncont.constant.LearncontentConstant, platform.social.constant.SocialoathtokenConstant" %>
<%@ include file="/pubs/taglibs.jsp"%>

  <bean:define id="jsSuffix" name="learncontentForm" property="jsSuffix" type="java.lang.String"/>
  <bean:define id="contentidVar" name="learncontentForm" property="vo.contentid" type="java.lang.Long"></bean:define>
  <bean:define id="productbaseidVar" name="learncontentForm" property="vo.productbaseid" type="java.lang.Long"></bean:define>
  <bean:define id="productnameVar" name="learncontentForm" property="vo.productname" type="java.lang.String"></bean:define>
  <bean:define id="linktypeVar" name="learncontentForm" property="vo.linktype" type="java.lang.Short"/>
  <bean:define id="editTypeVar" name="learncontentForm" property="editType" type="java.lang.Integer"/>
  <bean:define id="urlVar" name="learncontentForm" property="vo.linkfilesource" type="java.lang.String"/>
  <bean:define id="contenttypeVar" name="learncontentForm" property="vo.contenttype" type="java.lang.Integer"/>
  <bean:define id="ableuploadlocal" name="learncontentForm" property="ableuploadlocal" type="java.lang.Boolean"/>
  <bean:define id="ableuploadfileserver" name="learncontentForm" property="ableuploadfileserver" type="java.lang.Boolean"/>
  
  <%  
      String styleStr1="";
      String styleStr2=""; 
      if(urlVar==null){
         urlVar = "";
      }
      String urlshowVar = "";
      if(LearncontentConstant.Linktype_DirectLINK.equals(linktypeVar)
    	  || LearncontentConstant.Linktype_OnlineLINK.equals(linktypeVar)){
    	  urlshowVar = urlVar;
      }
      String contentidStrVar = (contentidVar==null)?"":contentidVar.toString();
      boolean showUpload = true;
  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true">
  <head>
    <html:base />
    <title>新增学习内容</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" type="text/css" href="../styles/css/edit.css">
	<script type="text/javascript" src="../styles/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="<%=WebConstant.WebContext %>/styles/script/resource/mess<%=jsSuffix %>.js"></script>
    <script type="text/javascript" src="../styles/script/pageAction.js"></script>
    <script type="text/javascript" src="../styles/script/commonlogic.js"></script>
    <script type="text/JavaScript">
    <!--
       var contentidVarJs = '<%=contentidStrVar %>';
       function deleteFile(){
    	  var url = doContextUrl('/deleteexistfile.do?vo.contentid='+contentidVarJs);
   		  if(confirm(getMessage("confirmDeleteMsg"))){
   			   var rtnObj = toAjaxUrl(url,false);
               if(rtnObj.result=="1"){ // 
            	   document.getElementById("linktypeID").disabled = "";
            	   var linktype = document.getElementById("linktypeID").value;
          		   deternStyle(linktype);
          		   document.getElementById("ID_linkfilenameCont").style.display="none";
               }else {
            	   alert(rtnObj.info);
               }
   		  }
       }
       
       function changeUploadWay(){
          var linktype = document.getElementById("linktypeID").value;
          deternStyle(linktype);
       }
       
       function deternStyle(uploadfileway){
    	  if(uploadfileway==null){
    		  uploadfileway = document.getElementById("linktypeID").value;
    	  }
          if(uploadfileway==<%=LearncontentConstant.Linktype_Local %>){
             document.getElementById("ID_uploadFile").style.display="block";
             document.getElementById("ID_uploadFile").disabled="";
             document.getElementById("ID_input_content").style.display="none";
             document.getElementById("ID_selectfromapp_table").style.display="none";
          }else if(uploadfileway==<%=LearncontentConstant.Linktype_DirectLINK %>||
        		   uploadfileway==<%=LearncontentConstant.Linktype_OnlineLINK %>)
          {
             document.getElementById("ID_uploadFile").style.display="none";
             document.getElementById("ID_uploadFile").disabled="disabled";
             document.getElementById("ID_input_content").disabled="";
             document.getElementById("ID_input_content").style.display="block";
             document.getElementById("ID_selectfromapp_table").style.display="none";
          }else{
             document.getElementById("ID_uploadFile").style.display="none";
             document.getElementById("ID_uploadFile").disabled="disabled";
             document.getElementById("ID_input_content").style.display="none";
             document.getElementById("ID_selectfromapp_table").style.display="block";
             document.getElementById("ID_selectfromapp_btn").disabled="";
          }
       }
       
       var subwindow = null;
       function selectfromapp(){
          var linktype = document.getElementById("linktypeID").value;
          var url = '';
          if(linktype=='<%=SocialoathtokenConstant.ServiceType_MSN.toString() %>'){
             url = "<%=SocialoathtokenConstant.getAuthenUrl(SocialoathtokenConstant.ServiceType_MSN) %>"+"&redirect_uri=<%=SocialoathtokenConstant.getFileRedirectUrl(SocialoathtokenConstant.ServiceType_MSN, true) %>";
          }else if(linktype=='<%=SocialoathtokenConstant.ServiceType_Dropbox.toString() %>'){
             url = "<%=SocialoathtokenConstant.getAuthenUrl(SocialoathtokenConstant.ServiceType_Dropbox) %>"+"&redirect_uri=<%=SocialoathtokenConstant.getFileRedirectUrl(SocialoathtokenConstant.ServiceType_Dropbox, true) %>";
          }else if(linktype=='<%=SocialoathtokenConstant.ServiceType_Google.toString() %>'){
             url = "<%=SocialoathtokenConstant.getAuthenUrl(SocialoathtokenConstant.ServiceType_Google) %>"+"&redirect_uri=<%=SocialoathtokenConstant.getFileRedirectUrl(SocialoathtokenConstant.ServiceType_Google, true) %>";
          }
          if(url!=''){
             subwindow = window.open(url,'pickupfile',"width=750,height=580,top=80,left=200,resizable=yes,scrollbars=yes");
          }
          if(linktype=='<%=LearncontentConstant.linktype_QiniuLink %>'){
        	  openuploadpage();
          }
       }
       
       function selectcallback(file){
          if(file!=null){
             document.getElementById("ID_input_content").disabled="";
             if(typeof(file.source)!='undefined' && file.source!=null){
                document.getElementById("ID_input_content").value=file.source;
             }else if(file.id!='undefined' && file.id!=null){
            	 document.getElementById("ID_input_content").value='';
             }
             document.getElementById("ID_linkfileId").value=file.id;
             document.getElementById("ID_linkfilenameCont").innerHTML = file.name;
             document.getElementById("ID_linkfilenameCont").style.display="block";
             document.getElementById("ID_linkfilename").value = file.name;
             document.getElementById("linkuserid_Id").value=file.userid;
             if(subwindow!=null){
                subwindow.close();
             }
             //clearDiv();
          }
       }
       
       function openuploadpage(){
    	   var caption = document.getElementById("captionID").value;
    	   var productid = document.getElementById("prodidId").value;
    	   var contentcateid = document.getElementById("contentcateidId").vaule;
    	   var contenttype = document.getElementById("contenttypeID").value;
    	   var istry = document.getElementById("istryID").value;
    	   var linktype = document.getElementById("linktypeID").value;
    	   var filepath = document.getElementById("ID_uploadFile").value;
    	   
    	   var url = "/openuploadpage.do?vo.contentid="+contentidVarJs+"&vo.caption="+caption+"&vo.productbaseid="+productid
    			   +"&vo.contenttype="+contenttype+"&vo.contentcateid="+contentcateid+"&vo.istry="+istry
    			   +"&vo.linktype="+linktype+"&selectfilepath="+filepath;
    	   openWin(url,500,200,'no','no');
       }
       
       function callbackopenuploadfile(contentid){
    	   if(contentid!=null && contentid!=''){
    		   contentidVarJs = contentid;
    		   var action = document.getElementById("editForm").action;
    		   if(endsWith(action, "&vo.contentid=")){
    			   document.getElementById("editForm").action = action + contentidVarJs;
    		   }
    	   }
       }
       
       function callbackuploadfile(fileId, filename){
    	   if(filename!=null && filename!=''){
    		   document.getElementById("uploadfilenameId").innerHTML = filename;
    		   document.getElementById("ID_linkfilenameCont").style.display="block";
    		   document.getElementById("ID_selectfromapp_btn").disabled="disabled";
    		   document.getElementById("linktypeID").disabled = "disabled";
    	   }
       }
       
    //-->
    </script>
  </head>
  
  <body>
	<div class="editPage">
	<html:form styleId="editForm" action='<%="/learncont/saveLearncontent.do?vo.productbaseid="+productbaseidVar+"&vo.contentid="+contentidStrVar %>' method="post" enctype="multipart/form-data">
      <input id="linkuserid_Id" type="hidden" name="vo.linkuserid"/>
      <div id="fieldsTitleDiv">学习资料
           <img src="../styles/imgs/ico/link_edit.png" style="width: 20px;height: 20px;" onclick="document.getElementById('pagelinkId').style.display='block';" title="显示本页站内链接地址，可在别的页面中引用">
      </div>
      <div id="pagelinkId" style="text-align: center;display: none;">/learncont/viewLearncontent.do?vo.contentid=<%=contentidStrVar %></div>
	  
	  <div id="displayMessDiv"></div>

	  <div id="fieldDisDiv">
	     <ul>
	     
	     	<li>
			   <div class="fieldLabel">学习内容名称:</div>
			   <div class="field"> 
			      <input type="text" id="captionID" name="vo.caption" value="<bean:write name="learncontentForm" property="vo.caption"/>" usage="notempty,max-length:200" fie="学习内容名称"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	     
	        <li>
			   <div class="fieldLabel">课程:</div>
			   <div class="field"> 
			      <input id="prodidId" name="vo.productbaseid" type="hidden" value="<%=productbaseidVar %>" >
			      <%=productnameVar %>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">学习内容类型:</div>
			   <div class="field"> 
			   	   <bean:writeSaas name="learncontentForm" property="vo.contenttype" consCode="netTest.Learncontent.contenttype"/>
		           <input id="contenttypeID" type="hidden" name="vo.contenttype" value="<bean:write name="learncontentForm" property="vo.contenttype"/>" > 
			   </div>
			   <div class="fieldDesc"></div>
			</li>
	        
	        <li>
			   <div class="fieldLabel">课程章节:</div>
			   <div class="field"> 
			       <input type="hidden" id="contentcateidId" name="vo.contentcateid" value="<bean:write name="learncontentForm" property="vo.contentcateid"/>" >
             	   <input type="text" id="contentcatenameId" name="vo.contentcatename" class="readonly" value="<bean:write name="learncontentForm" property="vo.contentcatename"/>" readonly="readonly"/>
       			   <img src="../styles/imgs/ico/search.gif" alt="选择章节" onclick="selectContcate('<%=productbaseidVar %>')">
       			   <img src="../styles/imgs/ico/reset.gif" alt="删除章节" onclick="removeContcate()">
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">资料使用类型:</div>
			   <div class="field"> 
			       <html:select styleId="istryID" name="learncontentForm" property="vo.istry">
					  <html:optionsSaas consCode="netTest.LearncontentConstant.IsTry"/>
			       </html:select>
			       <img src="../styles/imgs/help.png" style="cursor: pointer;" title="如果选择'免费试用学习资料'，即为预览学习资料，任何人都可预览">
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">上传文件方式:</div>
			   <div class="field"> 
			       <html:select styleId="linktypeID" name="learncontentForm" property="vo.linktype" onchange="changeUploadWay()">
					  <%if(LearncontentConstant.ContentType_AUDIO.equals(contenttypeVar)
						   || LearncontentConstant.ContentType_VIDEO.equals(contenttypeVar)){ %>
					  <html:optionsSaas consCode="common.uploadFile.way" removeStr="1,6"/>   
					  <%}else { %>
					  <html:optionsSaas consCode="common.uploadFile.way" removeStr="1,3,6"/>
					  <%} %>
					  <%if(ableuploadfileserver){ %>
					  <html:option value="4">上传到文件服务器</html:option>
					  <%} %>
					  <%if(ableuploadlocal){ %>
					  <html:option value="1">上传到本地服务器</html:option>
					  <%} %>
			       </html:select>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
		    <li>
			   <div class="fieldLabel">链接文件:</div>
			   <div class="field"> 
			        <input id="ID_uploadFile" type="file" name="uploadFile" style="width:350px;<%=styleStr1 %>" >
			        <input id="ID_linkfileId" type="hidden" name="vo.linkfileid">
			        <input id="ID_input_content" type="text" name="vo.linkfilesource" placeholder="在线视频页面顶部浏览器网址" style="width:350px;<%=styleStr2 %>" value="<%=urlshowVar %>">
                    <table id="ID_selectfromapp_table" style="border: 0px;">
                        <tr><td>
			            <button id="ID_selectfromapp_btn" type="button" onclick="selectfromapp();return false;" class="ModerateButton">选择文件</button>
			            </td><td>
			            <font style="font-size: smaller;">(如果浏览器提示是否允许弹出窗口，请选择允许弹出)</font>
			            </td></tr>
			        </table>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">文件信息:</div>
			   <div class="field"> 
			      <table style="border: 0px;width: 500px;">
			         <tr>
			            <td id="ID_linkfilenameCont" style="display: none">
			                                           上传文件名:&nbsp;
			                <span id="uploadfilenameId">
		                    	<bean:write name="learncontentForm" property="vo.linkfilename"/>&nbsp;&nbsp;
			               	</span>
			               	<logic:notEmpty name="learncontentForm" property="vo.linkusername">
			               	上传帐号:&nbsp;<bean:write name="learncontentForm" property="vo.linkusername"/>
			               	</logic:notEmpty>
			               	<a href="javascript:void(0)" onclick="deleteFile();">删除</a>
			            </td>
			         </tr>
			      </table>
			      
			   </div>
			   <div class="fieldDesc"><input id="ID_linkfilename" name="vo.linkfilename" type="hidden"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">学习顺序:</div>
			   <div class="field"> 
			      <input name="vo.orderno" type="text" style="width: 5em;" value='<bean:write name="learncontentForm" property="vo.orderno"/>' >
			      <img src="../styles/imgs/help.png" style="cursor: pointer;" title="在相同章节中学习的顺序，数值小则先学习">
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<li>
			   <div class="fieldLabel">说明:</div>
			   <div class="field"> 
			      <textarea id="editor1" name="vo.content" rows="8" cols="70"><bean:write name="learncontentForm" property="vo.content"/></textarea>
			      <script type="text/javascript">
			         var p_editor = CKEDITOR.replace( 'editor1',
			         {
			      		toolbar : 'MySimplest', 
			      		height : '100px'
			    	 });
			       </script>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			
			<%if(editTypeVar.intValue()==WebConstant.editType_edit) { %>
			<li>
			   <div class="fieldLabel">更新日期:</div>
			   <div class="field"> 
			      <bean:write name="learncontentForm" property="vo.lastupdatetime" format="yyyy-MM-dd"/>
			   </div>
			   <div class="fieldDesc"></div>
			</li>
			<%} %>
		 </ul>
	  </div>
	  
	  <div id="displayMessDiv">
	      <tiles:insert page="/pubs/messDiv.jsp"></tiles:insert>
	  </div>
	  
	  <div id="functionBarButtomDiv">
	  	 <ul>
		    <li><button type="button" class="bigButton" onclick="submitForm('editForm');" title="after saved, please preview it!"><bean:message key="netTest.commonpage.save"/></button></li>
			<li><button type="button" class="bigButton" onclick="backto();return false;"><bean:message key="netTest.commonpage.back"/></button></li>
		 </ul>
	  </div>
	  <div id="buttomDiv"></div>
	</html:form>
	</div>
	<script type="text/javascript">
	 <!--
       window.onload=function(){
		   deternStyle(<%=linktypeVar %>);
		   // init first element view
		   <%if(editTypeVar.intValue()==WebConstant.editType_edit){ %>
                <%if(urlVar!=null && !"".equals(urlVar)){ %>
       	  			document.getElementById("linktypeID").disabled="disabled";
       	  			document.getElementById("ID_selectfromapp_table").style.display="none";
       	  			document.getElementById("ID_linkfilenameCont").style.display="block";
       	  		<%} else{%>
       	  			document.getElementById("ID_linkfilenameCont").style.display="none";
       	 		<%} %>
           <%}else { %>
           		document.getElementById("ID_linkfilenameCont").style.display="none";
           <%} %>
	   }
	 
	   function backto(){
		   var caption = document.getElementById("captionID").value;
		   <%if(editTypeVar.intValue()==WebConstant.editType_edit){ %>
		        goUrl('/learncont/listLearncontent.do?productbaseid=<%=productbaseidVar %>&queryVO.contentcateid='+document.getElementById('contentcateidId').value);
		   <%}else { %>
		   if(caption==null||confirm('确定不保存本页内容?')){
			    goUrl('/learncont/listLearncontent.do?productbaseid=<%=productbaseidVar %>&queryVO.contentcateid='+document.getElementById('contentcateidId').value);
		   }
		   <%} %>
	   }
     //-->
    </script>
  </body>
</html:html>
