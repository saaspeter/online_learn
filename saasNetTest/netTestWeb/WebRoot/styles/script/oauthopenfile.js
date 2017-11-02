
    WL.init({ client_id: '000000004C0E29BD', redirect_uri: 'http://www.mylearn.com/netTest/oath/goselectfile.do?servicetype=7' });
	
	function openFromSkyDrive() {
	    WL.fileDialog({
	        mode: 'open',
	        select: 'single'
	    }).then(
	        function(response) {
	            var files = response.data.files;
	            var file = null;
	            for (var i = 0; i < files.length; i++) {
	                file = files[i];
	            }
	            selectcallback(file);
	        },
	        function(errorResponse) {
	            log("WL.fileDialog errorResponse = " + JSON.stringify(errorResponse));
	        }
	    );
	}
	                    
	function log(message) {
	    var child = document.createTextNode(message);
	    var parent = document.getElementById('JsOutputDiv') || document.body;
	    parent.appendChild(child);
	    parent.appendChild(document.createElement("br"));
	}
	
	function onDownloadFileError(responseFailed) {
	    log(responseFailed.error.message);
	}
