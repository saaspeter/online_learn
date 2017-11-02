/**
 * 
 */
// hope this format will load js asyn, not block page
var script = document.createElement("script"); 
script.type = "text/javascript"; 
script.src = "https://plus.google.com/js/client:plusone.js"; 
document.getElementsByTagName("head")[0].appendChild(script);

//document.write("<script type='text/javascript' src='https://plus.google.com/js/client:plusone.js'></script>");

var webContext = "/netTest";
var ServiceType_Google = '6';
var ServiceType_Facebook = '8';
var ServiceType_QQ = '9';
var testflag = 0;

function getLocaleStrFromPage(){
	if(typeof(getLocaleStr)!='undefined'){
		return getLocaleStr();
	}
	return "";
}

function getTimezonePage(){
	if(typeof(getTimezoneStr)!='undefined'){
		return getTimezoneStr();
	}
	return "";
}

function onSuccessCallback(authResult){
	var request = gapi.client.plus.people.get( {'userId' : 'me'} );
    request.execute( function(profile) {
       if (profile.error) {
           alert(profile.error);
           return;
       }
       var accountemail = '';
       if (profile.emails!=null && profile.emails.length>0) {
           for(var i=0;i<profile.emails.length;i++){
              if(profile.emails[i].type=='account'){
                 accountemail = profile.emails[i].value;
              }
           }
       }
       var displayname = profile.displayName;
       var socialuserid = profile.id;
       var gender = profile.gender;
       var localeStr = getLocaleStrFromPage();
       var timezoneStr = getTimezonePage();
       // call action save
       var link = webContext+'/social/saveloginwith.do?accesstoken='+authResult['access_token']
             +'&accountemail='+accountemail+'&servicetype='+ServiceType_Google
             +'&displayname='+displayname+'&socialuserid='+socialuserid+'&gender='+gender
             +'&locale='+localeStr+'&timezone='+timezoneStr;
       window.location.assign(link);
    });
}

function onGoogleSignInCallback(authResult){
	gapi.client.load('plus','v1', function(){
        if (authResult['access_token']) {
        	document.getElementById("loadingId").style.display='block';
        	setTimeout(function(){onSuccessCallback(authResult);}, 500);
        } else if (authResult['error']) {
          // There was an error, which means the user is not signed in.
        	if(authResult['error']!='immediate_failed'){
        		alert(authResult['error']);
        	}
        }
     });
}


