
   // 检查用户的答案是否符合要求。主要是对填空题和问答题。主要检查长度和是否有不能用的特殊字符
   function checkExam(oForm,questype,size){
      var els = null;
      var limitLeng = -1; // no any limit
      if(size!=null&&size!=''){
         limitLeng = size;
      }
      if(questype!=null){
      	 if(questype==4){  // tiankong
      	 	els = document.getElementsByTagName("input");
      	 	limitLeng = 50;
      	 }else if(questype==8){  // wenda
      	 	els = document.getElementsByTagName("textarea");
      	 }
      }
      if(els!=null&&els.length>0){
	      for(var i=0;i<els.length;i++){
			  if(questype==4){
			  	 if(els[i].type!='hidden'&&els[i].name.indexOf("useranswer")==0){
			  	 	 // check the special character
				  	 if(!filterSpecChar_TK(els[i].value)){
				  	 	setFocus(els[i]);
				  	 	return false;
				  	 }
				  	 if(els[i].value.length>limitLeng){
				  	 	showMessagePage('填空答案不能超过'+limitLeng+'个字');
				  	 	setFocus(els[i]);
				  	 	return false;
				  	 }
			  	 }
			  }else if(questype==8){
			  	 var rtnArr = checkElement(els[i], 0);
			  	 if(!rtnArr[1]){
			  	 	 setFocus(els[i]);
			  	 	 return false;
			  	 }
			  }
	      }
      }
      return true;
   }
   
   function filterSpecChar_TK(value){
   	  var reg = new RegExp(".*[〖|〗].*","i");
      var result = reg.test(value);
      if(result){
      	 showMessagePage('答案中不能有字符〖或〗');
      	 return false;
      }else{
      	 return true;
      }
   }
   
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
   
