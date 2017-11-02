//综合ajax的应用在tab中 通过tab的标签获得该标签下的要显示的内容
window.onload = function() {
	var tab = document.getElementById("first");
	var divs = tab.getElementsByTagName("div");
	var responseContent;
	for(var i = 0;i<divs.length;i = i+1) {
		divs[i].onclick = function() {
			//ajax的应用
//			var text = this.innerHTML;
//			var http = createXMLHttp();
//			var url = "";
			//true即为异步方式传输数据
//			http.open("get",url,true);
//			http.onreadystatechange = function() {
				//表示传输完成
//				if(http.readyState == 4) {
//					responseContent = http.responseText;
//				}
//			}

			if(this.className == "on") {
				return false;
			} else {
				//得到状态为on的tab
				var status = getStatus(divs);
				var id = this.id;
				this.className = "on";
				document.getElementById(id+"_cont").className = "show";
				document.getElementById(status).className = "hide";
				return true;
			}
		}
	}
}

//找到状态为on的那个tab 将状态设置为off
function getStatus(divs) {
	var tabId;
	for(var i=0;i<divs.length;i=i+1) {
		if(divs[i].className == 'on') {
			divs[i].className = "off";
			tabId = divs[i].id+"_cont";
			break;
		} else {
			continue;
		}
	}
	return tabId;
}

//创建xmlhttp
function createXMLHttp() {
	var request;
	var browser = navigator.appName;
	if(browser == "microsoft internet explorer") {
		var versions = ["Microsoft.XMLHttp","MSXML2.XMLHttp.4.0","MSXML2.XMLHttp.3.0","MSXML2.XMLHttp","MSXML2.XMLHttp.5.0"];
		for(var i=0;i<versions.length;i++) {
			try {
				request = new ActiveXObject(versions[i]);
				return request;
			} catch(e) {}
		}
	} else {
		request = new XMLHttpRequest();
		return request;
	}
}
