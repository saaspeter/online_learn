	
	// web的上下文应用名称
	var webContext = "/platform"
	
	// 定义显示方式是否用alert的方式，默认不是
	var showMessagePage_alert = false;
	
	var selectOneMsg_str = "请选择一条记录!";
	var confirmDeleteMsg_str = "确定要删除这些记录吗?";
	var oneOnlyMsg_str = "只能选择一条记录修改!";
	
	// 代表默认隐藏复杂搜索查询条件。1为打开，2为隐蔽高级搜索
	var hidComplexSearch = "2";
	// 代表是否是选中所有checkbox,还是取消所有的选中记录。1为选中所有，2为取消全部
	var selectAll = "1";
		
   // 左侧功能栏点击链接，在右侧内容面板上显示链接内容，
   // 同时如果传入对象，则设置该对象的class为按下样式
   function goRightUrl(mainUrl,obj){
      mainUrl = doContextUrl(mainUrl);
      parent.document.getElementById("mainFrame").src=mainUrl;
      if(typeof(obj)!="undefined"&&obj!=null&&obj!=""&&typeof(pressMenu)!="undefined"&&pressMenu!=null){
      	 pressMenu(obj);
      }
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
	              link = link +"&"+id1+"="+ param1;
	           }
		    }
		    document.location.href = link;
	    }
	}
	
	// 从页面获取id为key的链接，然后将页面跳转到这个链接
	function getAndToUrl(key){
	   if(key!=null&&key.length>0){
	      var url = document.getElementById(key);
	      if((typeof(url)!="undefined")&&url!=null&&url.value.length>0){
	          goUrl(url.value);
	      }
	   }
	}
	// 当点击banner上的条目时，banner以下应显示的内容
	function goBelowBannerUrl(leftUrl,mainUrl){
        leftUrl = doContextUrl(leftUrl);
        mainUrl = doContextUrl(mainUrl);
        parent.frames[1].document.getElementById("leftFrame").src=leftUrl;
        parent.frames[1].document.getElementById("mainFrame").src=mainUrl;
    }
    
    // 当点击banner上的条目时，banner以下应显示的内容
	function goBelowUrl(url){
        mainUrl = doContextUrl(mainUrl);
        parent.frames[1].document.location.href=url;
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
	
	function modifyRecord(fromId,checkboxName,url,selectOneMsg,oneOnlyMsg){
	   	var num = 0;
	    var id = "";
		var formObj = document.getElementById(fromId);
		
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
		if(selectOneMsg==null||selectOneMsg.length==0){
		    selectOneMsg = selectOneMsg_str;
		}
		
		if(oneOnlyMsg==null||oneOnlyMsg.length==0){
		    oneOnlyMsg = oneOnlyMsg_str;
		}
				
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(selectOneMsg);
			return false;
		}
		if (num > 1) {
			showMessagePage(oneOnlyMsg);
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
	
	// 处理一条checkbox记录
    function doSingleRecord(fromId,checkboxName,url,selectOneMsg,confirmMsg){
	   	var num = 0;
	    var id = "";
		var formObj = document.getElementById(fromId);
		
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
		if(selectOneMsg==null||selectOneMsg.length==0){
		    selectOneMsg = selectOneMsg_str;
		}
		for(var i=0;i<formObj.elements.length;i++){
			if(formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if(num==0||num>1) {
			showMessagePage(selectOneMsg);
			return false;
		}
        url = doContextUrl(url);
        // 组装url链接
		url = url+id;
        var backUrlEncode = document.getElementById("backUrlEncode");
        if((typeof(backUrlEncode)!="undefined")&&(backUrlEncode!=null)&&backUrlEncode.value.length>0){
            url = url+"&backUrlEncode="+backUrlEncode.value;
        }
        // 根据情况提交
		if(confirmMsg!=null&&confirmMsg!=""){
           if(confirm(confirmMsg)){
              document.location.href = url;
           }
        }else{
        	  document.location.href = url;
        }
	}
	
	function editInPop(fromId,checkboxName,url,width,height){
	   	var num = 0;
	    var id = "";
		var formObj = document.getElementById(fromId);
		
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
		if((typeof(width)=="undefined")||width==null||width.length==0||width=="0"){
	       width = 600;
	    }
	    if((typeof(height)=="undefined")||height==null||height.length==0||height=="0"){
	       height = 300;
	    }
				
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(selectOneMsg_str);
			return false;
		}
		if (num > 1) {
			showMessagePage(oneOnlyMsg_str);
			return false;
		}
        url = doContextUrl(url);
        url = url+id;
        newDiv(url,0,1,width,height);
	}
	
	function deleteRecord(formId,checkboxName,url,selectOneMsg,confirmDeleteMsg){
	   	var num = 0;
		var formObj = document.getElementById(formId);
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
        url = doContextUrl(url);
		if(selectOneMsg==null||selectOneMsg.length==0){
		    selectOneMsg = selectOneMsg_str;
		}
		if(confirmDeleteMsg==null||confirmDeleteMsg.length==0){
		    confirmDeleteMsg = confirmDeleteMsg_str;
		}		
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(selectOneMsg);
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
        if(confirm(confirmDeleteMsg)){
            formObj.action = url;
            formObj.submit();
        }
        return;
	}
	
	// 批量处理记录,在弹出的层中显示批量处理需要显示的url
	function batchProcessDiv(formId,checkboxName,url,width,height,selectOneMsg){
		if((typeof(width)=="undefined")||width==null||width.length==0||width=="0"){
	       width = 600;
	    }
	    if((typeof(height)=="undefined")||height==null||height.length==0||height=="0"){
	       height = 300;
	    }
		if(selectOneMsg==null||selectOneMsg.length==0){
		    selectOneMsg = selectOneMsg_str;
		}
		var ids = getSelectedKeys(formId,checkboxName,2,selectOneMsg);
		if(!ids){
			return false;
		}
        url = doContextUrl(url);
		url = url + ids;
		newDiv(url,1,0,width,height);
	}
	
	// 批量处理记录,在本页面提交批量处理
	function batchProcess(formId,checkboxName,url,selectOneMsg,confirmMsg){
	   	var num = 0;
		var formObj = document.getElementById(formId);
		if(formObj==null||url==null||url.length==0){
		    return ;
		}
		if(selectOneMsg==null||selectOneMsg.length==0){
		    selectOneMsg = selectOneMsg_str;
		}
		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				id = formObj.elements[i].value;
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(selectOneMsg);
			return false;
		}
		var backUrlEncode = document.getElementById("backUrlEncode");
        // 添加列表页面链接
        if((typeof(backUrlEncode)!="undefined")&&(backUrlEncode!=null)&&backUrlEncode.value.length>0){
            url = url+"&backUrlEncode="+backUrlEncode.value;
        }
        if(confirmMsg!=null&&confirmMsg!=""){
           if(confirm(confirmMsg)){
              formObj.action = url;
              formObj.submit();
           }
        }else{
        	  formObj.action = url;
              formObj.submit();
        }
        return;
	}
	
	// 在指定地方显示错误信息
	function showMessagePage(message){
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
				divTip.innerHTML = "<li>"+message+"</li>";
			}catch(e){}
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
//alert("complexSearchDivStatus:"+document.getElementById("complexSearchDivStatus").value);
  }
  
  // 注册时检查用户是否存在
  function checkUser(id,existsMess,notExistsMess,exeFunc){
     var accountObj = document.getElementById(id);
     if(existsMess==null||existsMess.length==0){
        existsMess = "该帐号已经被注册，请重新选择！";
     }
     if(notExistsMess==null||notExistsMess.length==0){
        notExistsMess = "该帐号还未使用，您可以继续！";
     }
     
     if(accountObj!=null){
        customer.isExists(accountObj.value,function callback(result){
           if(typeof(exeFunc)!="undefined"&&(exeFunc!=null)){
           	  eval(exeFunc(rtn));           	
           }
           if(result!=null&&result==true){
		      showMessagePage(existsMess);
		      return false;
		   }else{
		      showMessagePage(notExistsMess);
		      return true;
           }
        }
        );
     }
  }
  
  function callback(result,mess){
     if(result!=null&&result==true){
        showMessagePage(mess);
     }else{
        showMessagePage("帐号未被注册，可以继续使用；");
     }
  }
  	
  // 屏蔽母窗口和解锁母窗口的函数
  function newDiv(url,lock,hastop,width,height){ 
	  //var eSrc=(document.all)?window.event.srcElement:arguments[1];  
  	  if(url==null){
  	  	 url = "";
  	  }
	  url = doContextUrl(url);
	  var shield   =   null;
	  
	  if((typeof(width)=="undefined")||width==null||width.length==0||width=="0"){
	     width = 450;
	  }
	  if((typeof(height)=="undefined")||height==null||height.length==0||height=="0"){
	     height = 150;
	  }
	  
	  if(lock!=null&&lock=="1"){
		  shield = document.createElement("DIV");  
		  shield.id   =   "shield";  
		  shield.style.position   =   "absolute";  
		  shield.style.left   =   "0px";  
		  shield.style.top   =   "0px";  
		  shield.style.width   =   "100%";  
		  shield.style.height   =   document.body.scrollHeight+"px";  
		  shield.style.background   =   "#333";  
		  shield.style.textAlign   =   "center";  
		  shield.style.zIndex   =   "100";  
		  shield.style.filter   =   "alpha(opacity=0)";  
		  shield.style.opacity   =   0;  
		  
      }
	
	  var   alertFram   =   null;
	  var divObj = document.getElementById("alertFram");
	  if(typeof(divObj)!="undefined"&&divObj!=null){
	     alertFram = divObj;
	  }else{
	     alertFram = document.createElement("DIV");  
	     alertFram.id="alertFram";  
	     alertFram.className = "dragclass";
	     alertFram.style.position   =   "absolute";  
	     //alertFram.style.left   =   Math.abs(Math.round((window.document.body.clientWidth  -  width)/2));  
	     alertFram.style.left   = "300";
	     //alertFram.style.top   =   Math.abs(Math.round((window.document.body.clientHeight  -  height)/2));  
	     alertFram.style.top   = "150";
	     alertFram.style.marginLeft   =   "-225px"   ;  
	     alertFram.style.marginTop   =   -75+document.documentElement.scrollTop+"px";  
	     alertFram.style.width   =   width;  
	     alertFram.style.height   =   height; 
	     alertFram.style.background   =   "#ffffff";  
	     alertFram.style.textAlign   =   "center";  
	     alertFram.style.border = "1px #cccccc solid";

	     //alertFram.style.lineHeight   =   "150px";  
	     alertFram.style.zIndex   =   "10001";  
	  }

	  var topcode = "";
	  if(hastop!=null&&hastop=="1"){
	     topcode = "<div  style=\"width:100%;height:20px;background:#0099ff;text-align:right;cursor:move;\">"+
	           "<a href=\"\" style=\"text-decoration:none;padding:3px;font-size:15px;\" onclick=\"divHidden();return false;\">-</a>"+
               "<a href=\"\" style=\"text-decoration:none;padding:3px;font-size:15px;\" onclick=\"divShow();return false;\">+</a>"+
               "<a href=\"\" style=\"text-decoration:none;padding:3px;font-size:15px;\" onclick=\"clearDiv();return false;\">x</a>"+"</div>";
	  }
	   
	  strHtml = topcode +
	      "<div id=\"contentDiv\" style=\"height="+height+"\"><iframe width=\"100%\" height=\"100%font-size:15px;\" frameborder=0 src=\""+url+"\"></iframe></div>";  
	  alertFram.innerHTML   =   strHtml;  
	  document.body.appendChild(alertFram); 
	  
	  if(lock!=null&&lock==1&&shield!=null){ 
		  document.body.appendChild(shield);  
		  shield.style.filter="alpha(opacity=\"20\")";  
		  // 屏蔽渐渐变灰的效果
//		  this.setOpacity   =   function(obj,opacity){  
//			  if(opacity>=1)opacity=opacity/100;  
//			  try{   obj.style.opacity=opacity;   }catch(e){}  
//			  try{    
//				  if(obj.filters.length>0&&obj.filters("alpha")){  
//				    obj.filters("alpha").opacity=opacity*100;  
//				  }else{  
//				    obj.style.filter="alpha(opacity=\""+(opacity*100)+"\")";  
//				  }  
//			  }catch(e){}  
//		  }  
//		  var   c   =   0;  
//		  this.doAlpha   =   function(){  
//		      if   (++c   >   20){clearInterval(ad);return   0;}  
//		      setOpacity(shield,c);  
//		  }  
//		  var   ad   =   setInterval("doAlpha()",1);  
		  
	  }
	  
	  this.clearDiv   =   function(){  
	  //alertFram.style.display   =   "none";  
	  //shield.style.display   =   "none";  
	     document.body.removeChild(alertFram);  
	     if(lock!=null&&lock=="1"&&shield!=null){
	       document.body.removeChild(shield);  
	     }
	     //eSrc.focus();  
	     document.body.onselectstart   =   function(){return   true;}  
	     document.body.oncontextmenu   =   function(){return   true;}  
	  }  
	  //document.getElementById("do_OK").focus();  
	  //eSrc.blur();  
	  document.body.onselectstart   =   function(){return   false;}  
	  document.body.oncontextmenu   =   function(){return   false;}  
  }
  
  
  
  // 得到checkbox列表中被选中的记录的值,其中selType表示是否可以选择多个。1代表只能选择一个，2代表可以选择多个
  // rtnType代表返回类型，1或者不指定代表返回字符串(','隔开)，2代表返回数组
  function getSelectedKeys(fromId,checkboxName,selType,rtnType){
	   	var num = 0;
	    var ids = "";
	    var rtnArr = new Array();
		var formObj = document.getElementById(fromId);

		for (var i=0;i<formObj.elements.length;i++){
			if (formObj.elements[i].name == checkboxName && formObj.elements[i].checked == true){
				if(rtnType!=null&&(rtnType=="2"||rtnType==2)){
				   rtnArr[num] = formObj.elements[i].value;	
				}else{
				   ids = ids+formObj.elements[i].value+",";
				}
				num++;
			}
		}
		if (num == 0) {
			showMessagePage(getMessage("selectOneMsg"));
			return false;
		}
		if(ids!=""){
		   ids = ids.substring(0,ids.length-1);
		}
		if ((selType==1||selType=="1")&&(num>1)) {
			showMessagePage(getMessage("oneOnlyMsg"));
			return false;
		}
		if(rtnType!=null&&(rtnType=="2"||rtnType==2)){
		   return rtnArr;	
		}else{
		   return ids;
		}
	}
	
	// 显示tab页的函数
	function tab(no,totalNum){
	   if(no==null||no==""){
	      no = 1;
	   }
	   if(totalNum==null||totalNum==""){
	      totalNum = 10;
	   }
	   for(var i=0;i<totalNum;i++){
	      if(i==no){
	        document.getElementById("content"+i).style.display="block";
	      }else{
	        if(typeof(document.getElementById("content"+i))!="undefined"&&(document.getElementById("content"+i)!=null)){
	           document.getElementById("content"+i).style.display="none";
	        }
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
 	   	  	 selectOneMsgVar = selectOneMsg_str;
 	   	  }
          showMessagePage(selectOneMsg_str);
          return false;
       }
       if ((selType==1||selType=="1")&&(num>1)) {
       	  if(oneOnlyMsgVar==null||oneOnlyMsgVar==""){
 	   	  	 oneOnlyMsgVar = oneOnlyMsg_str;
 	   	  }
		  showMessagePage(oneOnlyMsg_str);
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

	