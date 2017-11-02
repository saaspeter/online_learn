<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <% Object oauthAppIDObj = request.getAttribute("oauthAppID"); 
       String oauthAppID = "";
       if(oauthAppIDObj!=null){ oauthAppID = oauthAppIDObj.toString(); }
    %>
    <script src="https://www.dropbox.com/static/api/1/dropbox-datastores-0.1.0-b3.js" type="text/javascript"></script>
    <script type="text/JavaScript">
    <!-- 
       /////////////////////////////
       var userid = null;
       var displayname = null;
       var client = new Dropbox.Client({key: '<%=oauthAppID %>'});
       
       function loadUserData() {
			// Try to finish OAuth authorization.
			client.authenticate({interactive: true}, function (error) {
			    if (error) {
			        alert('Authentication error: ' + error);
			    }
			});
			
			if (client.isAuthenticated()) {
			    //userid = client.dropboxUid();
			    client.getAccountInfo(function (err, data) {
				   if (err) {
				      alert('Error: ' + err);
				   }else {
				      parent.callsetuser(data);
				   }
				});
			}else {
			    alert('not authen');
			}
		}
       
        loadUserData();
        
		function log(message) {
		    var child = document.createTextNode(message);
		    var parent = document.getElementById('JsOutputDiv') || document.body;
		    parent.appendChild(child);
		    parent.appendChild(document.createElement("br"));
		}
       
    //-->
    </script>

  </head>
  
  <body>
      
  </body>
</html>
