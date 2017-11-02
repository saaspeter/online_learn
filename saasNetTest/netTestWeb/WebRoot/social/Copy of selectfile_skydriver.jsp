<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>select file from onedrive</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <% Object oauthAppIDObj = request.getAttribute("oauthAppID"); 
       String oauthAppID = "";
       if(oauthAppIDObj!=null){ oauthAppID = oauthAppIDObj.toString(); }
       String authorizecode = request.getParameter("code");
       Object socialuseridObj = request.getAttribute("socialuserid"); 
       String socialuserid = (socialuseridObj==null)?"":socialuseridObj.toString();
    %>
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
    <script src="http://js.live.net/v5.0/wl.js" type="text/javascript"></script>
    <script type="text/JavaScript">
    <!--
       /////////////////////////////
       var userid = '<%=socialuserid %>';
       var displayname = null;
       
        WL.init({ client_id: '<%=oauthAppID %>', redirect_uri: location.href });
	
		function openFromSkyDrive() {
		    WL.fileDialog({
		        mode: 'open',
		        select: 'single'
		    }).then(
		        function(response) {
		            var files = response.data.files;
		            if(files!=null && files.length>0){
		               var fileObj = new Object();
		               fileObj.id = files[0].id;
         			   var temsource = files[0].source;
         			   fileObj.source = temsource;
			           fileObj.name = files[0].name;
			           fileObj.authorizecode = '<%=authorizecode %>';
			           fileObj.userid = userid;
			           fileObj.displayname = displayname;
			           callback(fileObj);
		            }
		        },
		        function(errorResponse) {
		            if(errorResponse.error.code='user_canceled'){
		                window.close();
		            }else {
		                alert("WL.fileDialog errorResponse = " + JSON.stringify(errorResponse));
		            }
		        }
		    );
		}
		
		function callback(fileObj){
		    window.opener.window.selectcallback(fileObj);
		}
		                    
		function log(message) {
		    var child = document.createTextNode(message);
		    var parent = document.getElementById('JsOutputDiv') || document.body;
		    parent.appendChild(child);
		    parent.appendChild(document.createElement("br"));
		}
		
		function loadUserData(fileObj) {
		    WL.api({ path: "/me", method: "GET" }).then(
		        function (response) {
		            userid = response.id;
		            displayname = response.name;
		            // ajax call to savetoken
		            var rtnObj = toAjaxUrl("joinopentest.do?vo.testid="+testidVar,false);
		            if(typeof(fileObj)!='undefined' && fileObj!=null){
		               fileObj.userid = userid;
			           fileObj.displayname = displayname;
			           // 
			           callback(fileObj);
		            }
		        },
		        function (response) {
		            log("API call failed: " + JSON.stringify(response.error).replace(/,/g, "\n"));
		        }
		    );
		}
		
		openFromSkyDrive();
        //loadUserData();
        
    //-->
    </script>

  </head>
  
  <body style="margin: 5px;">

  </body>
</html>
