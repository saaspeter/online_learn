<%@ page import="netTestWeb.base.WebConstant, platform.social.constant.SocialoathtokenConstant"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>select file from Dropbox</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <% Object oauthAppIDObj = request.getAttribute("oauthAppID"); 
       String oauthAppID = "";
       if(oauthAppIDObj!=null){ oauthAppID = oauthAppIDObj.toString(); }
       String authorizecode = request.getParameter("code");
       Object socialuseridObj = request.getAttribute("socialuserid"); 
       String socialuserid = (socialuseridObj==null)?"":socialuseridObj.toString();
    %>
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
    <script type="text/javascript" src="https://www.dropbox.com/static/api/1/dropins.js" id="dropboxjs" data-app-key="<%=oauthAppID %>"></script>
    <script src="https://www.dropbox.com/static/api/1/dropbox-datastores-0.1.0-b3.js" type="text/javascript"></script>
    <script type="text/JavaScript">
    <!-- 
       /////////////////////////////
       var userid = '<%=socialuserid %>';
       var displayname = null;
       
       function getFileid(sourceurl){
           if(sourceurl==null || sourceurl==''){
              return null;
           }
		   var strReg = "(https|http)://dl.dropboxusercontent.com/\\d+/\\w+/[^/]+/"; 
		   var reg = new RegExp(strReg,"i");
		   return sourceurl.replace(reg,"");
	   }
       
       var options = {
            // Required. Called when a user selects an item in the Chooser.
            success: function(files) {
                var fileObj = new Object();
		        fileObj.id = getFileid(files[0].link);
		        fileObj.name = files[0].name;
		        fileObj.source = files[0].link;
		        fileObj.authorizecode = '<%=authorizecode %>';
		        if(fileObj.id!=null && fileObj.id.length>400){
		           alert('file name is too long');
		           return;
		        }
		        fileObj.userid = userid;
			    fileObj.displayname = displayname;
			    callback(fileObj);
            },

            // Optional. Called when the user closes the dialog without selecting a file
            // and does not include any parameters.
            cancel: function() {
                window.close();
            },

            // Optional. "preview" (default) is a preview link to the document for sharing,
            // "direct" is an expiring link to download the contents of the file. For more
            // information about link types, see Link types below.
            linkType: "direct",

            // Optional. A value of false (default) limits selection to a single file, while
            // true enables multiple file selection.
            multiselect: false,
        
            //extensions: ['.pdf', '.doc', '.docx'],    
        };
               
        Dropbox.choose(options);
        
		function log(message) {
		    var child = document.createTextNode(message);
		    var parent = document.getElementById('JsOutputDiv') || document.body;
		    parent.appendChild(child);
		    parent.appendChild(document.createElement("br"));
		}
		
		function callsetuser(date){
		    userid = data.uid;
			displayname = data.name;
		}
		
		function callback(fileObj){
		    window.opener.window.selectcallback(fileObj);
		}
       
    //-->
    </script>

  </head>
  
  <body>
      <div>
          loading......
      </div>
      <div id="container" style="width:95%;margin:10px;text-align:center;">
      
      </div>
  </body>
</html>
