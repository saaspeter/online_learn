<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>select file from google drive</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <% Object oauthAppIDObj = request.getAttribute("oauthAppID"); 
       Object accesstokenObj = request.getAttribute("key_accesstoken"); 
       Object socialuseridObj = request.getAttribute("socialuserid"); 
       String oauthAppID = "";
       String accesstoken = "";
       String socialuserid = "";
       if(oauthAppIDObj!=null){oauthAppID = oauthAppIDObj.toString();}
       if(accesstokenObj!=null){accesstoken = accesstokenObj.toString();}
       if(socialuseridObj!=null){socialuserid = socialuseridObj.toString();}
    %>
    <script src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">

    // Use the Google Loader script to load the google.picker script.
    google.setOnLoadCallback(createPicker);
    google.load('picker', '1');

    // Create and render a Picker object for searching images.
    function createPicker() {
      var view = new google.picker.View(google.picker.ViewId.DOCS);
      var picker = new google.picker.PickerBuilder()
          .setAppId('<%=oauthAppID %>')
          .setOAuthToken('<%=accesstoken %>') 
          .addView(view)
          .setCallback(pickerCallback)
          .build();
       picker.setVisible(true);
    }

    // A simple callback implementation.
    function pickerCallback(data) {
      if (data.action == google.picker.Action.PICKED) {
         var fileObj = new Object();
         fileObj.id = data.docs[0].id;
         //alert(fileObj.id);
         fileObj.name = data.docs[0][google.picker.Document.NAME];
         fileObj.userid = '<%=socialuserid %>';
         window.opener.window.selectcallback(fileObj);
      }
    }
    </script>

  </head>
  
  <body>
      <div id="downloadFile_div">
          
      </div>
      <div>
          <img id="imgId" src="">
      </div>
      <div id="container" style="width:95%;margin:10px;text-align:center;">
      
      </div>
  </body>
</html>
