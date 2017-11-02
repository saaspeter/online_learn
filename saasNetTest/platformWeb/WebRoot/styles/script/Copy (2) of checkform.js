////////////////////////////////////////////////////////////////////////////////
/*
 *--------------- 客户端表单通用验证checkForm(oForm) -----------------
 *	本程序最初是由wanghr100(灰豆宝宝.net)的checkForm基础上进行修改的，增加了很多功能,如下：
 *	1.对非ie的支持
 *	2.增加了内置表达式和内置提示
 *  3.增加了显示方式（弹出式和页面显示式）
 *	4.增加了显示一条和显示全部
 *	5.进行了封装（CLASS_CHECK）
 *	6.支持外接函数或表达式（应用在密码一致）
 *	7.简化了调用方式，所有操作只需要<script language='javascript' src='checkform.js'>,
	  然后在HTML里定义各标签验证格式
 *	8.对IE增加了对键盘输入的限制（如：定义usage='int'时，输入框只能输入数字（非IE无效）
 *	9.增加了对disabled的不验证
 * 10.自定义报警方式（重写showMessageEx方法）
 * 11.能对多FORM验证,还有针对普通按钮(非submit)下的验证
 * 12.增加对同一个字段进行多种验证功能。

 *-----------------------------申明信息-----------------------------
 *	版本：	0.13
 *	作者：	ttyp
 *	邮箱：	ttyp@21cn.com
 *	博客：	http://www.cnblogs.com/ttyp/
 *  声明：	对本程序可以任意复制传播，但请保留这些声明，对于内置的表达式有些没有做到很严格，如果你
			有好的建议和意见，欢迎邮件和我联系或者上我的博客留言
 *	日期：	2005-9-20
 *	简介：
			本程序只需要对需要验证的标签设置五个属性：usage,exp,fct,fie,tip,fctMsg(可选几个自由组合)

			usage	:	内置格式,指在js文件中定义好的验证规则
			exp     :   用户自定义的正则表达式格式，出错时的消息来源于tip属性，记住一定要以^开头，以$结束
			fct		:   函数验证，内在验证满足不了时，自己写一个验证函数对该字段验证，该函数返回
			            true或false,当返回false时，系统自动把fctMsg的内容加入错误提示中。
			fie     :   出错字段名
			tip		:	出错字段的提示信息，内置验证出错提示，如果该字段为空，则用js默认的中文提示
			fctMsg  :   自己写的fct函数验证失败时要显示的信息

特征：
	１．验证是既验证usage中的内置验证规则，也会验证用户自定义的fct函数，如果不希望验证，则不配置该
	属性即可。消息规则是：fie+tip+fctMsg，如果有多条验证规则，fie仍只出现一次

	２．usage属性可以支持配置多个验证条目，执行多个验证的功能，例如usage="int,noempty"，会执行两
个验证。
    3. tip属性是用户自定义的正则表达式，其错误消息来源于tip属性
　　 4.提示语言的属性有：fie和tip,其中fie代表字段信息，具体就是指那个字段如：姓名，tip代表提示的
信息内容，例如：不能为空。加起来就是：姓名不能为空。

　　 5．增加fctMsg字段，用于当fct属性函数验证失败时，表示要显示的错误信息。

	6．调用时只需要引用<script language='javascript' src='checkform.js'>，然后为每个标记增加以上几个属性（不一定需要全部）

 *	原作者:	wanghr100(灰豆宝宝.net)
    modify: peter(2007.8.7)
 *	email:	wanghr100@126.com
 *
 *--------------- 客户端表单通用验证checkForm(oForm) -----------------
 */
////////////////////////////////////////////////////////////////////////////////

		var errStyle = "login_fieldDesc_wrong";
		var successStyle = "login_fieldDesc_right";
		var msgTextSuffix = "_tip";
		var msgStyleSuffix = "_tip_td";

function CLASS_CHECK(){
	this.pass		= true;		//是否通过验证
	this.showAll	= true;		//是否显示所有的验证错误
	this.alert		= false;	//报警方式（默认alert报警）
	this.errmsgArr  = new Array();  // 3维数组，分别为: 元素id, 验证是否通过, 报错信息
	this.showErrType = 2;       // 显示错误方式，1: 单个元素的后面显示错误信息  2: 所有的错误信息放在一起显示
	this.first		= null;		//在显示全部验证错误时的第一个错误控件（用于回到焦点）
	this.cancel		= false;	

	//定义内置格式
	var aUsage = {
		"int":"^([+-]?)\\d*$",											//整数
		"int+":"^([+]?)\\d*$",											//正整数
		"int-":"^-\\d*$",												//负整数
		"num":"^([+-]?)\\d*\\.?\\d*$",									//数字
		"num+":"^([+]?)\\d*\\.?\\d*$",									//正数
		"num-":"^-\\d*\\.?\\d*$",										//负数
		"float":"^([+-]?)\\d*\\.\\d*$",									//浮点数
		"float+":"^([+]?)\\d*\\.\\d*$",									//正浮点数
		"float-":"^-\\d*\\.\\d*$",										//负浮点数				
		"email":"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.(com|com\.cn|net|cn|net\\.cn|org|biz|info|gov|gov\\.cn|edu|edu\\.cn)$|^\\S{0}$",	//邮件
		"color":"^#[a-fA-F0-9]{6}$|^\\S{0}$",							//颜色
		"url":"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$|^\\S{0}$",	//联接
		"chinese":"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]*$",				//仅中文
		"ascii":"^[\\x00-\\xFF]*$",										//仅ACSII字符
		"zipcode":"^\\d{6}$|^\\s{0}$",									//邮编
		"mobile":"^0{0,1}13[0-9]{9}$|^\\S{0}$",							//手机
		"ip4":"^\(([0-1]?\\d{0,2})|(2[0-5]{0,2}))\\.(([0-1]?\\d{0,2})|(2[0-5]{0,2}))\\.(([0-1]?\\d{0,2})|(2[0-5]{0,2}))\\.(([0-1]?\\d{0,2})|(2[0-5]{0,2}))$|^\\S{0}$",				//ip地址
		"notempty":"[^ ]+$",											//非空
		"picture":"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$|^\\S{0}$",	//图片
		"txt":"(.*)\\.txt$|^\\S{0}$",					                //文本文件
		"excel":"(.*)\\.(xls|csv)$|^\\S{0}$",				        	//Excel,CSV文件
		"rar":"(.*)\\.(rar|zip|7zip|tgz)$|^\\S{0}$",					//压缩文件
		"date":"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$|^\\s{0}$|^\\S{0}$",				//日期
        "time":"^[0-2]?\\d:[0-5]?\\d$|^\\S{0}$",                                //时间
		"word_num":"^\\w*$"                                              //只能由字母,数字和下划线组成
	};

	//缺省消息
	var aMessage =	{
		"int"	:"请输入整数",											//整数
		"int+"	:"请输入正整数",											//正整数
		"int-"	:"请输入负整数",											//负整数
		"num"	:"请输入数字",											//数字
		"num+"	:"请输入正数",											//正数
		"num-"	:"请输入负整数",											//负数
		"float"	:"请输入浮点数",											//浮点数
		"float+":"请输入正浮点数",										//正浮点数
		"float-":"请输入负浮点数",										//负浮点数
		"email"	:"请输入正确的邮箱地址",									//邮件
		"color"	:"请输入正确的颜色",										//颜色
		"url"	:"请输入正确的连接地址",									//联接
		"chinese":"请输入中文",											//中文
		"ascii"	:"请输入ascii字符",										//仅ACSII字符
		"zipcode":"请输入正确的邮政编码",									//邮编
		"mobile":"请输入正确的手机号码",									//手机
		"ip4"	:"请输入正确的IP地址",										//ip地址
		"notempty":"不能为空",											//非空
		"picture":"请选择图片",											//图片
		"txt":"请选择文本文件(txt)",	    				                //文本文件
		"excel":"请选择文本文件(xls,csv)",    				        	//Excel,CSV文件
		"rar"	:"请输入压缩文件",										//压缩文件
		"date"	:"请输入正确的日期",										//日期
		"time"  :"请输入正确的时间",                                        //时间
		"word_num":"只能由字母,数字和下划线组成"
	};

	var me = this;

	me.checkForm = function(oForm){
		me.pass		= true;
		me.first	= null;
		
		if(me.cancel==true){
			return true;
		}
		
		var els = oForm.elements;
		var errNum = -1; // for the use of errmsgArr
		//遍历所有表元素
		for(var i=0;i<els.length;i++){
			var eleId = els[i].getAttribute("id");
			//取得格式
			var sUsage	= els[i].getAttribute("usage");	
			var fct = els[i].getAttribute("fct");
            // 取得字段的名字
            var fie = els[i].getAttribute("fie");
			if(fie==null||fie.length==0)
				fie = "";
			// 取得字段的值
			var sVal = getValue(els[i]);
			// 取得用户自定的正则表达式Exp
			var exp = els[i].getAttribute("exp");
            // 所有消息系统的变量
            var allMsg = "";
			var sTip = getTip(els[i]);
            // 记录fct执行后的消息的，如果fct执行成功，则此消息为空，否则为fctMsg
			var fctMsg = "";

            // 记录该字段是否通过的标志
			var fieldPass = true;

			//对于失效状态不验证
			if(((sUsage==null||sUsage=='')&&(exp==null||exp=='')&&(fct==null||fct==''))
			   ||isDisabled(els[i])==true){
				continue;
			}
			
			// 是否已经设置了消息的标志
			var setFlag = false;
			
			//如果设置usage，则使用内置正则表达式
			if(typeof(sUsage)!="undefined"&&sUsage!=null){
				//如果Usage在表达式里找到，则使用内置表达式，无则认为是表达式；表达式可以是函数；
                var arr = sUsage.split(",");
                if(typeof(arr)!="undefined"&&arr!=null){
                   for(j=0;j<arr.length;j++){
            		  if(aUsage[arr[j]]!=null){			
				// 执行验证reg
                         if(!valideExp(aUsage[arr[j]],sVal)){
						    fieldPass = false;
						 // 处理出错消息
                            if(sTip!=null&&sTip.length>0&&!setFlag){
							// 如果用户设置了tip，则显示用户的tip，并且只显示一次
                                allMsg = fie+sTip;
								setFlag = true;
							}else if((sTip==null||sTip.length==0)&&(aMessage[arr[j]]!=null)){
							// 如果用户没有设置tip，则需要使用系统自带的验证消息
                          		allMsg = allMsg+fie+aMessage[arr[j]]+";";
							}
						 }
					  }
				   }
				}
			}
		    // 处理Exp的验证
			if(typeof(exp)!="undefined"&&exp!=null&&exp.length>0){
			    if(!valideExp(exp,sVal)){
				    fieldPass = false;
				    // 处理出错消息
                    if(sTip!=null&&sTip.length>0&&!setFlag){
				    // 如果用户设置了tip，则显示用户的tip，并且只显示一次
                       allMsg = fie+sTip;
					}
				}
			}

			if(typeof(fct)!="undefined"&&fct!=null) {
			   try {
				 if(eval(fct)==false){	
					fieldPass = false;
				 // 处理出错消息
					fctMsg = getFctMsg(els[i]);
                    allMsg = allMsg+fctMsg+";";
				 }
			   } catch(e){ 
					alert("表达式[" + fct +"]错误:" + e.description)
					return false;
			   }
			} 
			
			// 将验证结果加入数组
			errNum++;
			addMessage(errNum, eleId, fieldPass, allMsg);
            // 如果不通过则处理要显示的消息系统
			if(!fieldPass){
				me.pass	= false;
				if(me.first==null){
					me.first = els[i];
				}
				if(me.showAll==false){
					//该表单元素取得焦点,用通用返回函数
					setFocus(els[i]);
					break;
				}
			}
		}
        if(errNum>=0){
        	showMessage();
        }
		if(me.pass==false){
			if(me.first!=null&&me.showAll==true){
				setFocus(me.first);
			}
		}

		return me.pass;
	}


    /**
	 * 验证正择表达式，返回true或false
	 */
    function valideExp(strReg,strValue){
// 执行验证reg
//字符串->正则表达式,不区分大小写
      var reg = new RegExp(strReg,"i");
	  if(!reg.test(strValue))
		return false;
	  else
		return true;
	}

	/*
	 *	添加错误信息
	 */
	function addMessage(errNum, eleId, fieldPass, msg){
		if((fieldPass)&&(typeof(eleId)=="undefined"||eleId==null||eleId=='')){
		   return;	
		}
		if(me.showErrType!=1){
			if(me.alert==true){
				msg = msg + "\n";
			} else {
				msg = "<li>" + msg + "</li>";
			}
		}
		me.errmsgArr[errNum] = new Array(eleId, fieldPass, msg);
	}

	/*
	 *	得到Tip错误信息
	 */
	function getTip(els){
		var sTip = els.getAttribute("tip");
		if(typeof(sTip)!="undefined"&&sTip!=null){
			return sTip;
		} else {
			return "";
		}
	}

	/*
	 *	得到fctMsg错误信息
	 */
	function getFctMsg(els){
		var fctMsg = els.getAttribute("fctMsg");
		if(typeof(fctMsg)!="undefined"&&fctMsg!=null){
			return fctMsg;
		} else {
			return "";
		}
	}

	/*
	 *	显示错误
	 */
	function showMessage(){
		//外接显示错误函数
		if(typeof(me.showMessageEx)=="function"){
			return me.showMessageEx(me.errmsgArr);
		}
        if(me.showErrType==1){
            showErrorOnField(me.errmsgArr);	
        }else {
			var errMsg = "";
			for(var k=0;k<me.errmsgArr.length;k++){
				if(me.errmsgArr[k]==null||me.errmsgArr[k]==''||me.errmsgArr[k].length!=3){
				   continue;	
				}
				if(!me.errmsgArr[k][1]){
				   errMsg = errMsg+me.errmsgArr[k][2];
				}
			}
			if(me.alert==true){
				alert(errMsg);
			} else {
				var divTip;
					divTip = document.getElementById("displayMessDiv");
				try	{
					if(typeof(divTip)=="undefined"||divTip==null){
							divTip = document.createElement("div");
							divTip.id			= "divErrorMessage";
							divTip.name			= "divErrorMessage";
							divTip.style.color	= "red";
							document.body.appendChild(divTip);
					}
	
					divTip.innerHTML = errMsg;
				}catch(e){}
			}
        }
	}

	/*
	 *	获得元素是否失效（失效的元素不做判断）
	 */
	function isDisabled(el){
		//对于radio,checkbox元素，只要其中有一个非失效元素就验证
		if(el.type=="radio"||el.type=="checkbox"){
			//取得第一个元素的name,搜索这个元素组
			var tmpels = document.getElementsByName(el.name);
			for(var i=0;i<tmpels.length;i++){
				if(tmpels[i].disabled==false){
					return false;
				}
			}
			return true;
		}else{
			return el.disabled;
		}
	}


	/*
	 *	对没有通过验证的元素设置焦点
	 */
	function setFocus(el){
		//取得表单元素的类型
		var sType = el.type;
		switch(sType){
			//文本输入框,光标定位在文本输入框的末尾
			case "text":
			case "hidden":
			case "password":
			case "file":
			case "textarea": 
				try{el.focus();var rng = el.createTextRange(); rng.collapse(false); rng.select();}catch(e){};
				break;
			
			//单多选,第一选项非失效控件取得焦点
			case "checkbox":
			case "radio": 
				var els = document.getElementsByName(el.name);
				for(var i=0;i<els.length;i++){
					if(els[i].disabled == false){
						els[i].focus();
						break;
					}
				}
				break;
			case "select-one":
			case "select-multiple":
				el.focus();
				break;
		}
	}



/**	
	//自动绑定到所有form的onsubmit事件
	if(window.attachEvent){
		window.attachEvent("onload",function()
		{
			for(var i=0;i<document.forms.length;i++){
				var theFrom = document.forms[i]; 
					function mapping(f){ 
						f.attachEvent("onsubmit",function(){return me.checkForm(f);}); 
					} 

					if(theFrom){
						mapping(theFrom); 
						theFrom.attachEvent("onclick",function(){
																var o = event.srcElement;
																if(typeof(o.type)!="undefined"){
																   var check = o.getAttribute("check");
																   if(typeof(check)!="undefined"&&check!=null&&check.toLowerCase()=="false"){
																	  me.cancel = true;
																   }																									
																}
													  }
											); 
					}
			}
		}
        );	
	}
	else
	{
		window.onsubmit = function(e){var theFrom = e.target;if(theFrom){return me.checkForm(theFrom);}}
		window.addEventListener("click",function(e){
											var o = e.target;
											if(typeof(o.type)!="undefined"){
												var check = o.getAttribute("check");
												if(typeof(check)!="undefined"&&check!=null&&check.toLowerCase()=="false"){													
													me.cancel = true;
												}																									
											}
								},false);
		
	}
**/

/**
	this.keyCheck = function(){
		if(window.attachEvent){
			window.attachEvent("onload",function(){for(var i=0;i<document.forms.length;i++){var theFrom = document.forms[i]; if(theFrom){myKeyCheck(theFrom);}}});
		}else{
			//TOOD
		}

		function myKeyCheck(oForm){
			var els = oForm.elements;
			//遍历所有表元素
			for(var i=0;i<els.length;i++){
				//取得格式
				var sUsage	= els[i].getAttribute("usage");	
				
				//如果设置Usage，则使用内置正则表达式，忽略Exp
				if(typeof(sUsage)!="undefined"&&sUsage!=null){
					switch(sUsage.toLowerCase ()){
						case "zipcode":
						case "int":
							els[i].onkeypress	= function(){return /\d/.test(String.fromCharCode(event.keyCode))||(this.value.indexOf('+')<0?String.fromCharCode(event.keyCode)=="+":false)||(this.value.indexOf('-')<0?String.fromCharCode(event.keyCode)=="-":false);}
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;
						case "mobile":
						case "int+":
							els[i].onkeypress	= function(){return /\d/.test(String.fromCharCode(event.keyCode))||(this.value.indexOf('+')<0?String.fromCharCode(event.keyCode)=="+":false);}
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;
						case "int-":
							els[i].onkeypress	= function(){return /\d/.test(String.fromCharCode(event.keyCode))||(this.value.indexOf('-')<0?String.fromCharCode(event.keyCode)=="-":false);}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;
						case "float":
						case "num":
							els[i].onkeypress	= function(){return /[\+\-\.]|\d/.test(String.fromCharCode(event.keyCode));}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;
						case "float+":
						case "num+":
							els[i].onkeypress	= function(){return /[\+\.]|\d/.test(String.fromCharCode(event.keyCode));}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;
						case "float-":
						case "num-":
							els[i].onkeypress	= function(){return /[\-\.]|\d/.test(String.fromCharCode(event.keyCode));}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;
						case "ascii":
							els[i].style.imeMode= "disabled";
							break;
						case "ip4":
							els[i].onkeypress	= function(){return /[\.]|\d/.test(String.fromCharCode(event.keyCode));}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							els[i].maxLength	= 15;
							break;
						case "color":
							els[i].onkeypress	= function(){return /[a-fA-Z]|\d/.test(String.fromCharCode(event.keyCode))||(this.value.indexOf('#')<0?String.fromCharCode(event.keyCode)=="#":false);}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].maxLength	= 7;
							els[i].style.imeMode= "disabled";
							break;
						case "date":
							els[i].onkeypress	= function(){return /[\/\-\.]|\d/.test(String.fromCharCode(event.keyCode));}					
							els[i].onpaste		= function(){return !clipboardData.getData('text').match(/\D/);}
							els[i].ondragenter	= function(){return false;}
							els[i].style.imeMode= "disabled";
							break;							
					}
				}
			}
		}
	}
**/

	function showErrorOnField(errmsgArr){
		if(errmsgArr==null||errmsgArr.length==0){
		   return;	
		}
		var errStyle = "login_fieldDesc_wrong";
		var successStyle = "login_fieldDesc_right";
		var msgTextSuffix = "_tip";
		var msgStyleSuffix = "_tip_td";
		var msgTextObj;
		var msgStyleObj;
		for(var p=0;p<errmsgArr.length;p++){
			if(errmsgArr[p]==null||errmsgArr[p]==''||errmsgArr[p].length!=3){
			   continue;	
			}
			msgTextObj = document.getElementById(errmsgArr[p][0]+msgTextSuffix);
			msgStyleObj = document.getElementById(errmsgArr[p][0]+msgStyleSuffix);
			if(msgTextObj!=null&&typeof(msgTextObj)!="undefined"){
				if(errmsgArr[p][1]){
					msgTextObj.innerHTML="";
				}else {
					msgTextObj.innerHTML=errmsgArr[p][2];
				}
			}
			if(msgStyleObj!=null&&typeof(msgStyleObj)!="undefined"){
				if(errmsgArr[p][1]){
					msgStyleObj.className = successStyle;
				}else {
					msgStyleObj.className = errStyle;
				}
			}
		}
	}
	
}

	/*
	 *	取得对象的值（对于单选多选框把其选择的个数作为需要验证的值）
	 */
	function getValue(el){
		//取得表单元素的类型
		var sType = el.type;
		switch(sType){
			//文本输入框,直接取值el.value
			case "text":
			case "hidden":
			case "password":
			case "file":
			case "textarea": return el.value;
			//单多下拉菜单,遍历所有选项取得被选中的个数返回结果"0"表示选中一个，"00"表示选中两个
			case "checkbox":
			case "radio": return getRadioValue(el);
			case "select-one":
			case "select-multiple": return getSelectValue(el);
		}
		//取得radio,checkbox的选中数,用"0"来表示选中的个数,我们写正则的时候就可以通过0{1,}来表示选中个数
		function getRadioValue(el){
			var sValue = "";
			//取得第一个元素的name,搜索这个元素组
			if(el.name!=null&&el.name!=''){
			   var tmpels = document.getElementsByName(el.name);
			   for(var i=0;i<tmpels.length;i++){
				  if(tmpels[i].checked){
					 sValue += "0";
				  }
			   }
			}
			return sValue;
		}
		//取得select的选中数,用"0"来表示选中的个数,我们写正则的时候就可以通过0{1,}来表示选中个数
		function getSelectValue(el){
			var sValue = "";
			for(var i=0;i<el.options.length;i++){
				//单选下拉框提示选项设置为value=""
				if(el.options[i].selected && el.options[i].value!=""){
					sValue += "0";
				}
			}
			return sValue;
		}
	}

    // 取得radio选中记录的值，如果radio是disabled的，则返回null
	function getCheckedValue(name){
		var sValue = "";
		if(name==null||name==''){
			return sValue;
		}
		//取得第一个元素的name,搜索这个元素组
		var tmpels = document.getElementsByName(name);
		if(tmpels!=null&&tmpels.length>0){
			for(var i=0;i<tmpels.length;i++){
				if(tmpels[i].disabled==false&&tmpels[i].checked){
					sValue += tmpels[i].value+",";
				}
			}
			if(sValue!=null&&sValue.length>0){
				sValue = sValue.substring(0,sValue.length-1);
			}else if(sValue==""){
				sValue = null;
			}
		}else{ // 没有这样的radio或checkbox
			sValue = "undefined";
		}
		return sValue;
	}

   //初始化
   var g_check = new CLASS_CHECK;
	//g_check.keyCheck();
	
   // 验证并提交表单
   function submitForm(formId){
       var formObj;
       if(formId!=null&&formId.length>0){
          formObj = document.getElementById(formId);
       }
       else{ 
          formObj = document.forms[0];
       }
       if(formObj!=null&&g_check.checkForm(formObj)){
          //formObj.submit();
          //newDiv('/pubs/saveDiv.jsp',1,0);
          return true;
       }else{
          return false;
       }
   }

