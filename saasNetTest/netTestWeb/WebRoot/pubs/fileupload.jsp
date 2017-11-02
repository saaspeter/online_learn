<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.learncont.vo.Learncontent, netTest.util.QiniuFileUtil"%>

<%
	String rootobjecttype = request.getParameter("rootobjecttype");
	String parentObjectType = request.getParameter("parentObjectType");
	String parentObjectId = request.getParameter("parentObjectId");
	String fileType = request.getParameter("fileType");
	if(fileType==null||"".equals(fileType)){
		fileType = "Image";
	}
	String uploadurl = request.getContextPath()+"/logoimage/uploader?"
			           +"Type="+fileType+"&rootobjecttype="+rootobjecttype+
			           "&parentObjectType="+parentObjectType+"&parentObjectId="+parentObjectId;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
<title>星云学习--上传文件</title>
<link rel="stylesheet" type="text/css"
	href="http://apps.bdimg.com/libs/jqueryui/1.9.2/themes/vader/jquery-ui.css">
<style type="text/css">
	.ipt {
		width: 300px;
	}
	
	label {
		width: 130px;
		display: block;
	}
	
	ul li {
		list-style: none;
		margin: 20px;
	}
	
	.ui-progressbar {
		position: relative;
	}
	
	.progress-label {
		position: absolute;
		left: 50%;
		top: 4px;
		font-weight: bold;
		text-shadow: 1px 1px 0 #fff;
	}
	
	#progressbar {
		height: 30px;
		display: none;
	}
	
	#dialog {
		display: none;
	}
</style>

<script type="text/javascript" src="<%=request.getContextPath() %>/styles/script/pageAction.js"></script>
<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="http://apps.bdimg.com/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript">
    // see also:  http://developer.qiniu.com/docs/v6/api/overview/up/form-upload.html
	
	var filename;
    var originalname;
	var filesize;
	$(document).ready(function() {
	    var progressbar = $("#progressbar"),
	        progressLabel = $(".progress-label");
	    progressbar.progressbar({
	        value: false,
	        change: function() {
	            progressLabel.text(progressbar.progressbar("value") + "%");
	        },
	        complete: function() {
	            progressLabel.text("Waiting processing!");
	        }
	    });
	    $("#file").change(function() {
	        //普通上传
	        var ajax_upload = function(f) {
	        	try{
	            var xhr = new XMLHttpRequest();
	            xhr.open('POST', "<%=uploadurl %>", true);
	            var formData, startDate;
	            formData = new FormData();
	            //formData.append('token', token);
	            formData.append('upload', f);
	            var taking;
	            xhr.upload.addEventListener("progress", function(evt) {
	                if (evt.lengthComputable) {
	                    var nowDate = new Date().getTime();
	                    taking = nowDate - startDate;
	                    var x = (evt.loaded) / 1024;
	                    var y = taking / 1000;
	                    var uploadSpeed = (x / y);
	                    var formatSpeed;
	                    if (uploadSpeed > 1024) {
	                        formatSpeed = (uploadSpeed / 1024).toFixed(2) + "Mb\/s";
	                    } else {
	                        formatSpeed = uploadSpeed.toFixed(2) + "Kb\/s";
	                    }
	                    var percentComplete = Math.round(evt.loaded * 100 / evt.total);
	                    progressbar.progressbar("value", percentComplete);
	                    console && console.log(percentComplete, ",", formatSpeed);
	                }
	            }, false);
	
	            xhr.onreadystatechange = function(response) {
	            	try{
	                if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
	                	var blkRet = JSON.parse(xhr.responseText);
	                    console && console.log(blkRet);
	                    if(blkRet.status==1){
	                    	$(".progress-label").text("Complete!");
		                    alert('上传成功!');
		                    // call parent callback
		               	    window.opener.callbackuploadfile(blkRet.uploadfilename, blkRet.fileurl,
		               	    								 blkRet.filesize);
		                    window.close();
	                    } else {
	                       showMessagePage(blkRet.message);
	                    }
	                } else if (xhr.status != 200 && xhr.responseText) {
	                	if(xhr.responseText!=null&&xhr.responseText.indexOf('error')!=-1){
	                		alert(xhr.responseText);
	                	}
	                	console.log(xhr.responseText);
	                }
	            	}catch(e){
	            		console.log(e);
	            		alert('error internal');
	            	}
	            };
	            startDate = new Date().getTime();
	            $("#progressbar").show();
	            xhr.send(formData);
	        	} catch(e){
	        		console.log(e);
	        		alert(e);
	        	}
	        };
	        if ($("#file")[0].files.length > 0) {
	        	filesize = GetFileSize('file');
	        	ajax_upload($("#file")[0].files[0]);
	        } else {
	            console && console.log("form input error");
	        }
	    });
	});
	
	function GetFileSize(fileid) {
		try {
		    var fileSize = 0;
		    //for IE
		    if (/msie/.test(navigator.userAgent.toLowerCase())) {
		 		//before making an object of ActiveXObject, 
		 		//please make sure ActiveX is enabled in your IE browser
		 		var objFSO = new ActiveXObject("Scripting.FileSystemObject"); var filePath = $("#" + fileid)[0].value;
		 		var objFile = objFSO.getFile(filePath);
		 		fileSize = objFile.size; //size in kb
		 	}
		 	//for FF, Safari, Opeara and Others
		 	else {
		 		fileSize = $("#" + fileid)[0].files[0].size; //size in kb
		 	}
		 	return fileSize;
		 } catch (e) {
			console.log("Error is :" + e);
			return "";
		 }
	}
</script>

</head>
<body>

	<div>
		<ul>
			<li>
			    
			</li>
			<li><label for="bucket">请选择文件:</label> 
			    <input id="file" name="file" class="ipt" type="file" /></li>
			<li style="text-align: right;">
			    &nbsp;&nbsp;
			    <button type="button" onclick="window.close();">关闭窗口</button>
			</li>
			<div id="progressbar">
				<div class="progress-label"></div>
			</div>
		</ul>
	</div>

	<div id="dialog" title="上传成功"></div>
</body>
</html>