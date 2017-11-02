
var qqappid = '101136588';
// should enhance, check current domain is: www.tomylearn.com or tomylearn.com, different domain will block the login
var qqredirecturi = 'http://www.tomylearn.com/netTest/pubs/qc_callback.html';
document.write("<script type='text/javascript' src='http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js' charset='utf-8' data-appid='"
		       +qqappid+"' data-redirecturi='"+qqredirecturi+"'></script>");

var webContext = "/netTest";
var ServiceType_Google = '6';
var ServiceType_Facebook = '8';
var ServiceType_QQ = '9';

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

function onQQSignInCallback(opts){
	var localeStr = getLocaleStrFromPage();
    var timezoneStr = getTimezonePage();
    var displayname = opts.nickname;
    var gender = opts.gender;
    if(QC.Login.check()){//如果已登录
    	QC.Login.getMe(function(openId, accessToken){
    		// call action save
            var link = webContext+'/social/saveloginwith.do?accesstoken='+accessToken
                      +'&servicetype='+ServiceType_QQ
                      +'&displayname='+displayname+'&socialuserid='+openId+'&gender='+gender
                      +'&locale='+localeStr+'&timezone='+timezoneStr;
            window.location.assign(link);
        });
    }
    
}


