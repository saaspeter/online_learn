	
	// web的上下文应用名称
	var webContext = "/netTest";
	var styleContext = webContext+"/styles/";
	document.write("<script language='JavaScript' src='"+styleContext+"script/resource/mess_default.js'></script>");
	
	function getMessage(messName){
		if(messName==null||messName==""){
			return "";
		}
        eval("var temp = typeof("+messName+");");
        var messRtn = "";
        if(temp!="undefined"){
        	eval("messRtn="+messName+";");
        }else{
        	eval("messRtn="+messName+"_default"+";");
        }
		return messRtn;
	}
	
	// 在此加入checkform.js，jsp中不需要再次引用检验js
	document.write("<script language='JavaScript' src='"+styleContext+"script/checkform.js'></script>");
	
	
	// 定义显示方式是否用alert的方式，默认不是
	var showMessagePage_alert = false;
	
	// 代表默认隐藏复杂搜索查询条件。1为打开，2为隐蔽高级搜索
	var hidComplexSearch = "2";
	// 代表是否是选中所有checkbox,还是取消所有的选中记录。1为选中所有，2为取消全部
	var selectAll = "1";
		
   // 左侧功能栏点击链接，在右侧内容面板上显示链接内容，
   // 同时如果传入对象，则设置该对象的class为按下样式
   function goRightUrl(mainUrl){
      mainUrl = doContextUrl(mainUrl);
      parent.document.getElementById("mainFrame").src=mainUrl;

   }
   
    // 本页面转向url，id1为附加参数
	function goUrl(url,id1){
	    var link;
	    if(url!=null&&url.length>0)
	    {     
	   	    url = doContextUrl(url);
            link = url;
		    if((typeof(id1)!="undefined")&&(id1!=null)&&id1.length>0)
		    {     
		       var param1;
		       if(typeof(document.getElementById(id1))!="undefined"&&(document.getElementById(id1).value.length>0))
	           {
	              param1 = document.getElementById(id1).value;
	           }
	           if(param1!=null&&param1.length>0)
	           {
	              link = link + param1;
	           }
		    }
		    newDiv('/pubs/saveDiv.html',1,0,120,120);
		    window.location.assign(link);
	    }
	}
	
	// 从页面获取id为key的链接，然后将页面跳转到这个链接
	function getAndToUrl(key, backupUrl){
	   if(key!=null&&key.length>0){
	      var url = document.getElementById(key);
	      if((typeof(url)!="undefined")&&url!=null&&url.value.length>0){
	          goUrl(url.value);
	      }else if((typeof(backupUrl)!="undefined")&&backupUrl!=null&&backupUrl!=''){
	      	  goUrl(backupUrl);
	      }
	   }else if((typeof(backupUrl)!="undefined")&&backupUrl!=null&&backupUrl!=''){
      	  goUrl(backupUrl);
       }
	}
    
    // 本页面转向url，id1为附加参数
	function addRecord(url, id1){
	    var link;
	    if(url!=null&&url.length>0)
	    {    
            url = doContextUrl(url);
	        link = url; 
	        var linkmark = "&";
	        if(link.indexOf("?")==-1){
	           linkmark = "?";
	        }
	        if((typeof(id1)!="undefined")&&(id1!=null)&&id1.length>0)
		    {     
		       var param1;
		       if(typeof(document.getElementById(id1))!="undefined"&&(document.getElementById(id1).value.length>0))
	           {
	              param1 = document.getElementById(id1).value;
	           }
	           if(param1!=null&&param1.length>0)
	           {
	              link = link + param1;
	           }
		    }
	        var backUrlEncode = document.getElementById("backUrlEncode");
            // 添加列表页面链接
            if((typeof(backUrlEncode)!="undefined")&&(backUrlEncode!=null)&&backUrlEncode.value.length>0){
               link = link+linkmark+"backUrlEncode="+backUrlEncode.value;
            }
		    document.location.href = link;
	    }
	}
	
	function modifyRecord(fromId,checkboxName,url){
	   	var num = 0;
	    var id = "";
		var formObj = document.getElementById(fromId);
		
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
			
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(getMessage("selectOneMsg"));
			return false;
		}
		if (num > 1) {
			showMessagePage(getMessage("oneOnlyMsg"));
			return false;
		}
        url = doContextUrl(url);
        url = url+id;
        var backUrlEncode = document.getElementById("backUrlEncode");
        // 添加列表页面链接
        if((typeof(backUrlEncode)!="undefined")&&(backUrlEncode!=null)&&backUrlEncode.value.length>0){
            url = url+"&backUrlEncode="+backUrlEncode.value;
        }
		document.location.href = url;
	}
	
	function deleteRecord(formId,checkboxName,url){
	   	var num = 0;
		var formObj = document.getElementById(formId);
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
        url = doContextUrl(url);
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(getMessage("selectOneMsg"));
			return false;
		}
		var backUrlEncode = document.getElementById("backUrlEncode");
        // 添加列表页面链接
        if((typeof(backUrlEncode)!="undefined")&&(backUrlEncode!=null)&&backUrlEncode.value.length>0){
        	if(url.indexOf("?")!=-1){
               url = url+"&backUrlEncode="+backUrlEncode.value;
        	}else{
        	   url = url+"?backUrlEncode="+backUrlEncode.value;
        	}
        }
        if(confirm(getMessage("confirmDeleteMsg"))){
            formObj.action = url;
            formObj.submit();
        }
        return;
	}
	
	function deleteSingleRec(url){
		if(confirm(getMessage("confirmDeleteMsg"))){
			var backUrlEncode = document.getElementById("backUrlEncode");
	        // 添加列表页面链接
	        if(url.indexOf('backUrlEncode')==-1&&(typeof(backUrlEncode)!="undefined")
	           &&(backUrlEncode!=null)&&backUrlEncode.value.length>0)
	        {
	        	if(url.indexOf("?")!=-1){
	               url = url+"&backUrlEncode="+backUrlEncode.value;
	        	}else{
	        	   url = url+"?backUrlEncode="+backUrlEncode.value;
	        	}
	        }
            goUrl(url);
        }
	}
	
	function delRec(formId,checkboxName,url,errHasRef){
	   	var num = 0;
		var formObj = document.getElementById(formId);
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
		if(typeof(errHasRef)=="undefined"||errHasRef==null||errHasRef==""){
			errHasRef = "deleteError_HasRef";
		}
        url = doContextUrl(url);
        var pks = "";
        var recType = null;  // 由于大量采用表继承的做法，有时删除记录时需要提供该记录的类型，以便删除相关连的子记录
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				pks = pks +"keys="+ formObj.elements[i].value;
				if(typeof(document.getElementById(formObj.elements[i].id+"Type"))!="undefined"
				   &&(document.getElementById(formObj.elements[i].id+"Type")!=null)){
					recType = "$"+document.getElementById(formObj.elements[i].id+"Type").value;
				}else{
					recType = "";
				}
				pks = pks + recType + "&";
				num++;
				recType = ""; // set blank
			}
		}

		if (num == 0) {
			showMessagePage(getMessage("selectOneMsg"));
			return false;
		}
        if(confirm(getMessage("confirmDeleteMsg"))){
        	var delimer = "?";
        	if(url.indexOf("?")!=-1){
               delimer = "&";
        	}
        	url = url+delimer+pks;
            if(url.lastIndexOf("&")==url.length-1){
            	url = url.substring(0,url.length-1);
            }
        	var rtnObj = toAjaxUrl(url,false);
            if(rtnObj.result=="1"){ // forward the success page,if success
               var forwardUrl = formObj.action;
               if(forwardUrl.indexOf("?")!=-1){
	               delimer = "&";
        	   }else {
        	   	   delimer = "?";
        	   }
               formObj.action = forwardUrl+delimer+"bundle=BizKey&DisplayMessageKey=commonWeb.page.pubs.message.jsp.deleteSuccess&DisplayMessageArg0Key="
                                +rtnObj.info;
               formObj.submit();
            }else if(rtnObj.info!=null&&rtnObj.info.length>0){
               if((rtnObj.info).charAt(rtnObj.info.length-1)==","){
               	   rtnObj.info = rtnObj.info.substring(0,rtnObj.info.length-1);
               }
               var pkArr = rtnObj.info.split(",");
               var names = "";
               if(pkArr!=null&&pkArr.length>0){
                  for(var i=0;i<pkArr.length;i++){
                  	  if(typeof(document.getElementById("pkId["+pkArr[i]+"]Name"))!="undefined"
                  		  && document.getElementById("pkId["+pkArr[i]+"]Name")!=null){
               	         names = names+document.getElementById("pkId["+pkArr[i]+"]Name").value+";";
                  	  }
                  }
               }
               if(names==''){names = rtnObj.info;}
               alert(getMessage("deleteError")+":: "+names+" -- "+getMessage(errHasRef));
            }else if(rtnObj.tip!=null&&rtnObj.tip.length>0){
               alert(rtnObj.tip);
            }else{
               alert(getMessage("systemError"));
            }
        }
        return;
	}
	
	function delSingleRecAjax(url, gobackurlId, alertmess){
		if(url==null||url.length==0){
		    return ;
		}
		url = doContextUrl(url);
		var alertmessshow = getMessage("confirmDeleteMsg");
		if(typeof(alertmess)!='undefined'&&alertmess!=null&&alertmess!=''){
			alertmessshow = alertmess;
		}
		if(confirm(alertmessshow)){
			var rtnObj = toAjaxUrl(url,false);
            if(rtnObj.result=="1"){ // forward the success page,if success
               var backUrlObj = document.getElementById("backUrl");
               if(typeof(gobackurlId)!='undefined'&&gobackurlId!=null){
               	   backUrlObj = document.getElementById(gobackurlId);
               }
               if((typeof(backUrlObj)!="undefined") 
                  && (backUrlObj!=null) && (backUrlObj.value!='')){
                  var delimer = "&";
                  if(backUrlObj.value.indexOf("?")!=-1){
	               	 delimer = "&";
        	   	  }else {
        	   	     delimer = "?";
        	      }
                  var backUrl = backUrlObj.value+delimer+"bundle=BizKey&DisplayMessageKey=commonWeb.page.pubs.message.jsp.deleteSuccessCommon";
	              goUrl(backUrl);
               }else if(document.forms[0]){
            	   document.forms[0].submit();
               }
            }else {
               alertDiv(rtnObj.info);
            }
        }
	}
		
	// 在指定地方显示错误信息
	function showMessagePage(message, showtype){
		if(showMessagePage_alert==true){
			alert(message);
		} else {
			var divTip;
				divTip = document.getElementById("displayMessDiv");
			try	{
				if(typeof(divTip)=="undefined"||divTip==null){
						divTip = document.createElement("div");
						divTip.id			= "divShowMessage";
						divTip.name			= "divShowMessage";
						divTip.style.color	= "red";
						document.body.appendChild(divTip);
				}
				if(typeof(showtype)!="undefined"&&showtype=='1'){
					divTip.innerHTML = divTip.innerHTML+"<li>"+message+"</li>";
				}else {
				    divTip.innerHTML = "<li>"+message+"</li>";
				}
			}catch(e){
				console.log(e);
			}
		}
	}
	
	// 清楚显示的消息
	function clearMessagePage(){
		var divTip = document.getElementById("displayMessDiv");
		if(typeof(divTip)!="undefined"&&divTip!=null){
			divTip.innerHTML = "";
		}
	}
  
	  /** 选择所有的checkbox框 **/
	  function  selectAllCheckbox(formId, parentCheckbox, keys)
	  {
		var formObj = document.getElementById(formId);
		var keyObjList = document.getElementsByName(keys);
		for (var i = 0; i < keyObjList.length; i++)
		{
			if (keyObjList[i].disabled == false)
			{
				if(selectAll=="1"){
				   keyObjList[i].checked = true;
			    }else if(selectAll=="2"){
			       keyObjList[i].checked = false;
			    }
		    }	
	    }
	    if(selectAll=="1"){
	       selectAll="2";
	    }else if(selectAll=="2"){
	       selectAll="1";
	    }
	    return;
	  }
  
	  // 当点击左侧树条目时，调用此函数刷新右侧页面
	  function showRightFromTree(id,name){ 
	      if(id==null||id==""){
	         return;
	      }
	      // 设置树所在的页面的点中的节点的值
	      var selectNode = document.getElementById("selectNode");
	      if((typeof(selectNode)!="undefined")&&(selectNode!=null)){
	         selectNode.value=id;
	      }
	      //adjustIframeHeight(parent.frames[1].frameElement);
	      // 如果右侧页面没有实现函数showRight，则采用默认url
	      if(typeof(parent.frames[1].showRight)=="undefined")
	      {
	         var url = document.getElementById("rightUrl").value+id; 
	         url = doContextUrl(url);
	         parent.frames[1].location.href = url;
	      }else{
	         parent.frames[1].showRight(id);
	      }
	  }
  
	  // 跳转页面的函数
	  function jumpMenu(targ,selObj,restore,link){ //v3.0
	     itemValue = selObj.options[selObj.selectedIndex].value;
	     if(typeof(link)=="undefined"||link==null){
	        link = "";
	     }
		 link = doContextUrl(link);
	     link = link+itemValue;
	     var backUrlEncode = document.getElementById("backUrlEncode");
	        // 添加列表页面链接
	     if((typeof(backUrlEncode)!="undefined")&&(backUrlEncode!=null)&&backUrlEncode.value.length>0){
	            link = link+"&backUrlEncode="+backUrlEncode.value;
	     }
	     eval(targ+".location='"+link+"'");
	     if (restore) selObj.selectedIndex=0;
	  }
	
	  // 显示或隐藏高级搜索项
	  function changeComplexSearch(divId,divStatus){
	     var reverseStatus = document.getElementById("complexSearchDivStatus").value;
	     if(typeof(divStatus)!="undefined"&&divStatus!=null&&divStatus.length>0){
	        hidComplexSearch = divStatus;
	     }
	     document.getElementById("complexSearchDivStatus").value = hidComplexSearch;
	     if(hidComplexSearch!=null&&hidComplexSearch=="1"){
	        document.getElementById(divId).style.display="block";
	        hidComplexSearch="2";
	     }else{
	        document.getElementById(divId).style.display="none";     
	        hidComplexSearch="1";
	     }
	  }
  	
	  function newDiv2(url,lock,hastop,width,height,posLeft,posTop,divId){
		  return newDiv_base(1,null,1,url,lock,hastop,width,height,posLeft,posTop,divId);
	  }
	  
	  function newDiv(url,lock,hastop,width,height,posLeft,posTop,divId){ 
		  return newDiv_base(1,null,0,url,lock,hastop,width,height,posLeft,posTop,divId);
	  }
	  
	  function alertDiv(message){ 
		  if(message==null){
			  message = '';
		  }
		  message = "<div style='color:red;border:0px;width:96%;padding:10px;text-align:left;padding-bottom:30px;'>"+message+"</div>"
		            +"<div style='width:100%;border:0px;text-align:center;'>"
		            +"<button type='button' style='padding:10px 20px;height:30px;' onclick='clearDiv();'>"
		            +getMessage('Text_OkButton')+"</button></div>";
		  newDiv_base(0,message,0,null,1,1,350,150);
	  }
	  
	  function confirmDiv(message){ 
		  if(message==null){
			  message = '';
		  }
		  message = "<div style='border:0px;width:96%;padding:10px;text-align:left;padding-bottom:30px;'>"+message+"</div>"
		            +"<div style='width:100%;border:0px;text-align:center;'>"
		            +"<button type='button' style='padding:10px 20px;' onclick='return true;'>"+getMessage('Text_OkButton')+"</button>"
		            +"&nbsp;&nbsp;"
		            +"<button type='button' style='padding:10px 20px;' onclick='return false;clearDiv();'>"+getMessage('Text_CancelButton')+"</button>"
		            +"</div>";
		  newDiv_base(0,message,0,null,1,1,350,150);
	  }
	  
	  // 屏蔽母窗口和解锁母窗口的函数, showmestype: 0 plaintext, 1 iframe url
	  function newDiv_base(showmestype,message,ajaxload,url,lock,hastop,
			               width,height,posLeft,posTop,divId){ 
		  //var eSrc=(document.all)?window.event.srcElement:arguments[1];
	  	  if(lock==null){
	  	  	 lock = "";
	  	  }
	  	  lock = lock+"";
	  	  if(hastop==null){
	  	  	 hastop = "";
	  	  }
	  	  hastop = hastop+"";
		  var shield   =   null;
		  if((typeof(width)=="undefined")||width==null||width.length==0||width=="0"){
		     width = 450;
		  }
		  if((typeof(height)=="undefined")||height==null||height.length==0||height=="0"){
		     height = 200;
		  }
		  if(lock!=null&&lock=="1"){
			  shield = document.createElement("DIV");  
			  shield.id   =   "DIV_shield";  
			  shield.style.position   =   "absolute";  
			  shield.style.left   =   "0px";  
			  shield.style.top   =   "0px";  
			  shield.style.width   =   "100%";  
			  shield.style.height   =   document.body.scrollHeight+"px";  
			  shield.style.background   =   "#333";  
			  shield.style.textAlign   =   "center";  
			  //shield.style.zIndex   =   "100";  
			  shield.style.filter   =   "alpha(opacity=0)";  
			  shield.style.opacity   =   0.3;  
	      }
	      if((typeof(divId)=="undefined")||divId==null){
	      	 divId = "alertFram";
	      }
	      
		  var   alertFram   =   null;
		  var divObj = document.getElementById(divId);
		  if(typeof(divObj)!="undefined"&&divObj!=null){
		     alertFram = divObj;
		     alertFram.tabindex = "0";
		     alertFram.style.zIndex = 1;
		     alertFram.focus();
		  }else{
		     alertFram = document.createElement("DIV");  
		     alertFram.id = divId;
		     alertFram.tabindex = "0";
		     alertFram.className = "newDivclass";
		     alertFram.style.position   =   "absolute";  
		     if((typeof(posLeft)=="undefined")||(posLeft==null)||(posLeft=='')){
		        alertFram.style.left = Math.abs(Math.round((window.document.body.offsetWidth-width)/2))+"px";  
		     }else{
		     	alertFram.style.left = posLeft+"px";
		     }
		     
		     if((typeof(posTop)=="undefined")||(posTop==null)||(posTop=='')){
                var topscoll = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop; 
		        var posTopTemp = Math.abs(Math.round((window.document.body.clientHeight-height)/2+topscoll));  
                if(posTopTemp<20){
                	posTopTemp = "20";
                }
                alertFram.style.top = posTopTemp+"px";	     
		     }else{
		     	alertFram.style.top = posTop+"px";
		     }
		     //alertFram.style.marginLeft   =   "-225px"   ;  
		     //alertFram.style.marginTop   =   -75+document.documentElement.scrollTop+"px";  
		     alertFram.style.width   =   width+"px";  
		     if(hastop!=null&&hastop=="1"){
		    	 alertFram.style.height   =   height+25+"px"; 
		     }else {
		    	 alertFram.style.height   =   height+"px"; 
		     }
		     alertFram.style.background   =   "#ffffff";  
		     alertFram.style.textAlign   =   "center";  
		     alertFram.style.border = "1px #cccccc solid";
		     //alertFram.style.lineHeight   =   "150px";  
		     alertFram.style.zIndex   =  "10001";
		  }
		  // 
		  document.body.appendChild(alertFram); 
		  
		  var topcode = "";
		  if(hastop!=null&&hastop=="1"){
		     topcode = "<div  style=\"width:100%;clear:both;height:25px;background:#0099ff;text-align:right;cursor:move;\">"+
		           "<a href=\"javascript:void(0)\" style=\"height:100%;background:#ff0000;text-decoration:none;margin:0px;font-size:23px;font-weight:bold;color:#ffff;\" onclick=\"clearDivInter();return false;\">&nbsp;&times&nbsp;</a>"+"</div>";
		  }
		  
		  if(showmestype==1){
			  if(url==null){
		  	  	 url = "";
		  	  }
			  url = doContextUrl(url);
			  var newdivId = "newDivContentDiv";
			  var strHtml = "";  
			  if(ajaxload==0){
				  strHtml = topcode
				            +"<div id=\""+newdivId+"\" style=\"margin:0;height:"+height+";margin:0px;overflow:auto;\">"
					        +"<iframe id=\"contentDivFrame\" style=\"margin:0;\" width=\"100%\" height=\""+height+"\" font-size:15px;\" frameborder=0 src=\""+url+"\"></iframe>";
				            +"</div>";
				  alertFram.innerHTML   =   strHtml;
			  }else if(ajaxload==1){
				  getByAjax(url,false,null,function(data){
					  strHtml = topcode+data;
					  alertFram.innerHTML   =   strHtml;
				  });
				  
			  }
		  }else if(showmestype==0){
			  alertFram.innerHTML = topcode+message;
		  }
		  
		  if(lock!=null&&lock==1&&shield!=null){ 
			  document.body.appendChild(shield);  
			  shield.style.filter="alpha(opacity=\"20\")";  		  
		  }
		  
		  this.clearDivInter = function(){  
		     document.body.removeChild(alertFram);  
		     if(lock!=null&&lock=="1"&&shield!=null){
		       document.body.removeChild(shield);  
		     }
		     document.body.onselectstart = function(){return true;};  
		     document.body.oncontextmenu = function(){return true;};  
		  };
		  document.body.onselectstart = function(){return false;};  
		  document.body.oncontextmenu = function(){return false;};  
		  
		  return alertFram;
	  }
	  
	  function clearDiv(divId){
	     if((typeof(divId)=="undefined")||divId==null){
	      	 divId = "alertFram";
	     }
	     var alertFram = document.getElementById(divId);
	     if(typeof(alertFram)=='undefined'||alertFram==null){
	    	 return;
	     }
	     document.body.removeChild(alertFram);
	     var shield = document.getElementById("DIV_shield");
	     if(typeof(shield)!='undefined' && shield!=null){
	        document.body.removeChild(shield);  
	     }
	     document.body.onselectstart = function(){return true;};  
	     document.body.oncontextmenu = function(){return true;}; 
	  }
		
	  // 显示tab页的函数
	  function showTab(no, totalNum, url, param){
		  if(no==null||no==""){
		     no = 1;
		  }
		  if(totalNum==null||totalNum==""){
		     totalNum = 10;
		  }
		  for(var i=1;i<=totalNum;i++){
		     if(i==no){
		        document.getElementById("content"+i).style.display="block";
		        document.getElementById("tab"+i).className='selectTab';
		        if(typeof(url)!='undefined' && url!=null && url!=''){
		        	loadDivByAjax("content"+i, url, param, true);
		        }
		     }else{
		        document.getElementById("content"+i).style.display="none";
		        document.getElementById("tab"+i).className='';
		     }
		  }
	  }
  
    // 设置数据表格的间隔着色
	function setListTableStyle()
	{
		var oTable;
		var oTR;
		for (var i = 0; i < document.getElementsByTagName("table").length; i++)
		{
			oTable = document.getElementsByTagName("table")[i];
			if (oTable.className == "listDataTable")
			{			
				oTable.rows[0].className = "listDataTableHead";
				for (var j = 1; j < oTable.rows.length; j++)
				{
					oTR = oTable.rows[j];
					if (j % 2 == 1)
					{
						oTR.className = "oddRow";
					}
					else
					{
						oTR.className = "evenRow";
					}
				}
			}
		}
	}
	
    // 组装用户勾选的记录，通常包含ids和names。返回的数组是ids和names组成的。1代表只能选择一个，2代表可以选择多个
    function selectCheck(formId,checkName,selType,suffixName,selectOneMsgVar,oneOnlyMsgVar){
       if(typeof(selType)=="undefined"||selType==null||selType==''){
       	  selType = '1';
       }
       var formObj = document.getElementById(formId);
       var num = 0;
       var id = "";
       var name = "";
       var ids = "";
       var names = "";
       var backObj = {};
       for (var i=0;i<formObj.elements.length;i++){
	      if (formObj.elements[i].name == checkName && formObj.elements[i].checked == true){
	         id = formObj.elements[i].value;
	         num++;
	         ids = ids+id+",";
	         if(suffixName!=null&&suffixName.length>0){
	            name = document.getElementById((formObj.elements[i].id+suffixName)).value;
	            names = names+name+";";
	         }
          }
       }
 	   if(num==0){
 	   	  if(selectOneMsgVar==null||selectOneMsgVar==""){
 	   	  	 selectOneMsgVar = "selectOneMsg";
 	   	  }
          showMessagePage(getMessage(selectOneMsgVar));
          return false;
       }
       if ((selType==1||selType=="1")&&(num>1)) {
       	  if(oneOnlyMsgVar==null||oneOnlyMsgVar==""){
 	   	  	 oneOnlyMsgVar = "oneOnlyMsg";
 	   	  }
		  showMessagePage(getMessage(oneOnlyMsgVar));
		  return false;
	   }
       if(ids!=""){
		  ids = ids.substring(0,ids.length-1);
		  if(names!=null&&names.length>1){
		     names = names.substring(0,names.length-1);
		  }
	   }
       backObj['ids'] = ids;
       backObj['names'] = names;
       return backObj;
    }

    // 处理跳转的url，如果以"/"开头，并且不以"/webContext"开头就添加webContext
    // 如果以#开头则只取#后的字符串作为url
    function doContextUrl(url){
    	if(url!=null&&url.length>0){
	    	if(url.indexOf("#")==0&&url.length>1){
	    	   url = url.substring(1,url.length);
	    	}else if(url.indexOf("/")==0&&url.indexOf(webContext)!=0){
			   url = webContext+url;
	        }
    	}
        return url;
    }
	
	// isAsync: true(异步) false(同步)
	function getByAjax(url,isAsync,param,callback){
	    var http=null;
	    if(window.XMLHttpRequest){
	       http = new XMLHttpRequest();
	    }else if(window.ActiveXObject){
	       try{
	          http = new ActiveXObject("Msxml2.XMLHTTP");
	       }catch(e){
	          try{
	             http = new ActiveXObject("Microsoft.XMLHTTP");
	          }catch(e){
	             http=false;
	          }
	       }
	    }
	    // Synchronous XMLHttpRequest on the main thread is deprecated, but here need still use it
	    if(typeof(isAsync)=="undefined"||isAsync==null){
	    	isAsync = false;
	    }
	    
	    url = doContextUrl(url);
	    try{
	    	http.onreadystatechange=function() 
	        {
	            if (http.readyState==4 && http.status==200){
	            	callback(http.responseText);
	            }
	            
	        };

		    http.open("POST",url,isAsync); // sync
		    http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		    if(typeof(param)=="undefined"||param==null){
		       http.send();
		    }else {
		       http.send(param);
		    }
		    //return http.responseText;
	    }catch(e){ alert(getMessage("systemError")+', '+e); }
	}
	
	function loadDivByAjax(divId, url, param, isAsyn){
		if(divId==null || divId=='' || url==null || url==''){
			return;
		}
		if(typeof(isAsyn)=='undefined'){
			isAsyn = true;
		}
		getByAjax(url,isAsyn,param,function(data){
			document.getElementById(divId).innerHTML = data;
		});
	}
		
	// isAsync: true(异步) false(同步), in fact, here isAsync is no use
	function toAjaxUrl(url,isAsync,param,callback){
	    try{
	    	var retObj = null;
		    getByAjax(url,isAsync,param, function(data){
		    	if(data!=null && data!=''){
		    		retObj = ParseMeasureXMLData(data);
		    	}
		    	if(typeof(callback)!='undefined' && callback!=null){
		    		callback(retObj);
		    	}
		    });
		    return retObj;
	    }catch(e){ alert(getMessage("systemError")+', '+e); }
	}
	
	//解析数据
	function ParseMeasureXMLData(responseXML){
	    var responseDoc = null;
		if (window.DOMParser && navigator.userAgent.indexOf("Trident")==-1)
		{ // support DOMParser and browser is not IE
			var parser=new DOMParser();
			responseDoc=parser.parseFromString(responseXML,"text/xml");
		}else {// Internet Explorer
			responseDoc=new ActiveXObject("Microsoft.XMLDOM");
			responseDoc.async="false";
			responseDoc.loadXML(responseXML); 
		}
	    var retObj = new Object();
	    var resultType = getXMLNodeText(responseDoc,"/response/resultType");
	    if(resultType=='1'){
	    	retObj = new Object();
	    	retObj.result = getXMLNodeText(responseDoc,"/response/result");
	        retObj.info = getXMLNodeText(responseDoc,"/response/info");
	        retObj.tip = getXMLNodeText(responseDoc,"/response/tip");
	    }else if(resultType=='2'){
	    	retObj = new Array();
	    	var resultArray = responseDoc.selectSingleNode("/response/resultArray");
	    	if(resultArray){
	    	   var resultChildObj = null;
			   for(var i=0;i<resultArray.childNodes.length;i++){
			       //var resultNode = resultArray.childNodes[i];
			       resultChildObj = new Object();
			       resultChildObj.result = getXMLNodeText(responseDoc,"/resultNode/result");
			       resultChildObj.info = getXMLNodeText(responseDoc,"/resultNode/info");
			       resultChildObj.tip = getXMLNodeText(responseDoc,"/resultNode/tip");
			       retObj[i] = resultChildObj;
			   }
			}
	    }else {
	    	retObj.result = '-1';
	    	retObj.info = responseXML;
	    }
	    
	    return retObj;
	}
	
	function getXMLNodeText(doc,xpath){
		var retval = "";
        if(typeof XPathEvaluator != "undefined"){
           var xpe = new XPathEvaluator();
           var nsResolver = xpe.createNSResolver( doc.ownerDocument == null ? doc.documentElement : doc.ownerDocument.documentElement);
           var results = xpe.evaluate(xpath,doc,nsResolver,XPathResult.FIRST_ORDERED_NODE_TYPE, null);
           if(results!=null&&results.singleNodeValue!=null){
              retval = results.singleNodeValue.textContent; 
           }
        }else {
           var nod = doc.selectSingleNode(xpath);
		   if(nod!=null){
		      retval = nod.text;
		   }
        }
        return retval;
	}
	
	/* Open New Window */
	function openWin(url,w,h,Brresize,Brscroll)
	{
		url = doContextUrl(url);
		if((typeof(w)=="undefined")&&(typeof(h)=="undefined")
		    &&(typeof(Brresize)=="undefined")&&(typeof(Brscroll)=="undefined")){
			window.open(url);
		}else {
			var width = w;
	        var height = h;
		    if(width==0)
		       width=screen.availWidth;
		    if(height==0)
		       height=screen.availHeight;

	        var toolbar = 'no';
	        var location = 'no';
	        var status = 'no';       
	        var menubar = 'no';
	        var scrollbars = Brscroll;
	        var resizable = Brresize;
	        var left = parseInt((screen.availWidth/2) - (width/2));
	        var top = parseInt((screen.availHeight/2) - (height/2));
	        var windowFeatures = "width=" + width + ",height=" + height + ",left="+
	         				left + ",top=" + top + ",screenX=" + left + ",screenY=" + 
	               			top + ",toolbar=" + toolbar + ",location=" + location + ",status=" + 
	               			status + ",menubar=" + menubar + ",scrollbars=" + scrollbars + ",resizable=" + resizable;
	
	      	window.open(url,'',windowFeatures);  
		}
        
	}

	function endsWith(str, suffix) {
	    return str.indexOf(suffix, str.length - suffix.length) !== -1;
	}
	
	// auto adjust frame height
	function adjustIframeHeight(iframeid, contentdivId)
    {
		var ifm = document.getElementById(iframeid);
		alert(ifm);
		if(typeof(ifm)!='undefined'&&ifm!=null){
			var subWeb = ifm.contentDocument;  
		    if(typeof(subWeb)=='undefined'){
		       subWeb = ifm.document;
		    } 
			if(ifm != null && subWeb != null) {
			   var finalHeight = subWeb.body.scrollHeight+30+'px';
			   document.getElementById(contentdivId).style.height = finalHeight;
			} 
		}
    }
	