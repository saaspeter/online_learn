<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="netTest.learncont.vo.Learncontent, netTest.util.QiniuFileUtil"%>
<%@ include file="/pubs/taglibs.jsp"%>
<bean:define id="contentidVar" name="learncontentForm" property="vo.contentid" type="java.lang.Long"></bean:define>
<bean:define id="verifykeyVar" name="learncontentForm" property="verifykey" type="java.lang.String"></bean:define>
<%
	String contentidStrVar = (contentidVar == null) ? "" : contentidVar.toString();
	String fileprefix = QiniuFileUtil.constructUploadFilePrefixName(
			Learncontent.ObjectType, contentidVar);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
<title>tomylearn-upload file</title>
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="../styles/css/beautifulButton.css">
<style type="text/css">
	.ipt {
		width: 300px;
		font-size: larger;
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
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
<script type="text/javascript">
    // see also:  http://developer.qiniu.com/docs/v6/api/overview/up/form-upload.html
	
	var filename;
    var originalname;
	var filesize;
	$(document).ready(function() {
	    var Qiniu_UploadUrl = "http://up.qiniu.com";
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
	        var Qiniu_upload = function(f, token, key) {
	            var xhr = new XMLHttpRequest();
	            xhr.open('POST', Qiniu_UploadUrl, true);
	            var formData, startDate;
	            formData = new FormData();
	            if (key !== null && key !== undefined) formData.append('key', key);
	            formData.append('token', token);
	            formData.append('file', f);
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
	                if (xhr.readyState == 4 && xhr.status == 200 && xhr.responseText != "") {
	                    var blkRet = JSON.parse(xhr.responseText);
	                    console && console.log(blkRet);
	                    //$("#dialog").html(xhr.responseText).dialog();
	                    //alert(xhr.responseText+" | fileId:"+blkRet.key);
	                    // call update url
	                    var url = "/afteruploadfilecb.do";
	                    var param = "id=<%=contentidStrVar%>&name="+filename+"&size="+filesize+"&verifykey=<%=verifykeyVar %>";
	         	        var rtnObj = toAjaxUrl(url, false, param);
	                    if(rtnObj.result=="1"){ 
	                       $(".progress-label").text("Complete!");
	                       alert('上传成功!');
	                       // call parent callback
	               	       window.opener.callbackuploadfile(blkRet.key, originalname);
	                       window.close();
	                    }else {
	                       showMessagePage(rtnObj.info);
	                    }
	                } else if (xhr.status != 200 && xhr.responseText) {
	                	if(xhr.responseText!=null&&xhr.responseText.indexOf('error')!=-1){
	                		alert(xhr.responseText);
	                	}
	                	console.log(xhr.responseText);
	                }
	            };
	            startDate = new Date().getTime();
	            $("#progressbar").show();
	            xhr.send(formData);
	        };
	        var token = $("#token").val();
	        if ($("#file")[0].files.length > 0 && token != "") {
	        	filesize = GetFileSize('file');
	        	originalname = getNameFromPath(document.getElementById('file').value);
	        	filename = '<%=fileprefix%>'+originalname;
	        	// disable close button
	        	$('#closebtnId').attr('disabled','disabled');
	        	// upload
	        	Qiniu_upload($("#file")[0].files[0], token, filename);
	        } else {
	            console && console.log("form input error");
	        }
	    });
	    // call parent callback
	    window.opener.callbackopenuploadfile('<%=contentidStrVar%>');
	});

	function getNameFromPath(strFilepath) {
		var objRE = new RegExp(/([^\/\\]+)$/);
		var strName = objRE.exec(strFilepath);

		if (strName == null) {
			return null;
		} else {
			return strName[0];
		}
	}
	
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
			    <input id="key" type="hidden" name="key" class="ipt" value=""> 
				<input id="token" type="hidden" name="token" class="ipt" value="<bean:write name="learncontentForm" property="uploadtoken"/>">
			</li>
			<li><label for="bucket"><span style="font-size: larger;font-weight: bold;">请选择文件:</span></label></li>
			<li><input id="file" name="file" class="ipt" type="file" /></li>
			<li style="text-align: right;">
			    &nbsp;&nbsp;
			    <button id="closebtnId" type="button" class="button green medium" onclick="window.close();">关闭窗口</button>
			</li>
			<div id="progressbar">
				<div class="progress-label"></div>
			</div>
		</ul>
	</div>

	<div id="dialog" title="上传成功"></div>
</body>
</html>