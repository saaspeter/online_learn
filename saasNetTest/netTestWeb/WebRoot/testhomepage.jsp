<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>MyHtml.html</title>
	
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
    <script src="http://js.live.net/v5.0/wl.js" type="text/javascript"></script>
    <script language=JavaScript src="styles/script/pageAction.js"></script>
    <script type="text/javascript" src="styles/script/swfobject.js"></script>
    
    <script type="text/JavaScript">
    <!--
       function popupselect(){
          window.open("https://login.live.com/oauth20_authorize.srf?client_id=000000004C0E29BD&scope=wl.skydrive,wl.offline_access&response_type=code&redirect_uri=http://www.mylearn.com/netTest/oath/goselectfile.do",
                  '',"width=600,height=500,top=80,left=200");
          // the directory uri is a servlet which will: get authentication code, then make rest call to 
          // https://login.live.com/oauth20_token.srf?client_id=000000004C0E29BD&redirect_uri=http://www.mylearn.com/netTest/MyHtml.html&client_secret=ry8OnbTQrnN6RDq6QYrtW4rGURSWeWW8&code=c3bd8b49-8c70-6760-20e2-cbf746b7e6bc&grant_type=authorization_code
          // then servlet get the access_token and refeash token and store them in DB
          // return to a callback page, which just call parent js callback
       }
       
       function selectcallback(file){
          if(file!=null){
             var filesource = file.source;
             var pos = file.source.indexOf("?download&psid=1");
             if(pos!=-1){
                filesource = file.source.substring(0,pos);
             }
             
             if(file.name.indexOf(".jpg")!=-1||file.name.indexOf(".JPG")!=-1||file.name.indexOf(".gif")!=-1){
                document.getElementById("imgId").src=filesource;
                log("file.source:"+filesource);
             }else if(file.name.indexOf(".flv")!=-1||file.name.indexOf(".wmv")!=-1
                 ||file.name.indexOf(".mp4")!=-1||file.name.indexOf(".MP4")!=-1){
                
                log("file.source:"+filesource);
                var s1 = new SWFObject("styles/player.swf","container","95%","600","9","#123456");
			    s1.addParam("allowfullscreen","true");
			    s1.addParam("allowscriptaccess","always");
			    s1.addParam('flashvars','file='+filesource);
			    s1.addVariable("backcolor","FF0000");
			    s1.write("container");
             }
          }
       }
       

		
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
      <div id="downloadFile_div">
          <button type="button" onclick="popupselect();">open from skydrive</button>
      </div>
      <div id="showImgDivId">
          <img id="imgId" src="">
      </div>
      <div id="container" style="width:70%;margin:10px;text-align:center;">

      </div>
      
  </body>
</html>
