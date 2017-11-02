
   // get object position: retObj.x  retObj.y
   function getElementOffset(elem){ 
       var offsetTotalX = elem.offsetLeft; 
       var offsetTotalY = elem.offsetTop; 
       var offsetTotalH = elem.offsetHeight;
       var offsetTotalW = elem.offsetWidth;
       var pOffsetElem = elem.offsetParent; 
       while( pOffsetElem ){ 
          offsetTotalX += pOffsetElem.offsetLeft; 
          offsetTotalY += pOffsetElem.offsetTop; 
          pOffsetElem = pOffsetElem.offsetParent; 
       } 
       return { 
            'x' : offsetTotalX, 
            'y' : offsetTotalY,
            'h' : offsetTotalH,
            'w' : offsetTotalW
       }; 
   } 
   
   
   function getMousePos(e) { 
       e = e || event; 
       var posx = 0; 
       var posy = 0; 
       if ( e.pageX && e.pageY ) { 
           posx = e.pageX; 
           posy = e.pageY; 
       }else if (e.clientX && e.clientY ) { 
           posx = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft; 
           posy = e.clientY + document.body.scrollTop + document.documentElement.scrollTop; 
       } 
       return { 
           'x' : posx, 
           'y' : posy 
       }; 
   } 

   // input tip dropList
   var TipInputList = {};
   
   TipInputList.initDropList = function(inputID, divID){
   	  var obj = document.getElementById(inputID);
	  if(obj.offsetParent){
	     x = obj.offsetLeft;
	     y = obj.offsetTop;
	     h = obj.offsetHeight;
	     w = obj.width;
	     while(obj = obj.offsetParent){
	       x += obj.offsetLeft;
	       y += obj.offsetTop;
	     }
	  }
	  document.getElementById(divID).style.left = x;
	  document.getElementById(divID).style.top = y + h;
	  document.getElementById(divID).style.width = w;
   };
   
   TipInputList.filltxt = function (inputID, divID, txt){
      document.getElementById(inputID).value = txt;
      document.getElementById(divID).innerHTML = "";
   };
	   
   TipInputList.hide = function (divID){
      setTimeout(function(){document.getElementById(divID).innerHTML = "";},1000);
   };
	
   TipInputList.changeselect = function (inputID, divID, listArr, isInit, callMethod){
      var txt = document.getElementById(inputID).value;
      txt = txt.toLowerCase();
	  var newList = "";
	
      var results = 0;
      if(txt!="" || (isInit==1&&txt=='')){
	     newList = "<table id='listTableID' cellpadding=1 cellspacing=1 width='100%' style='border:0 px;' bgcolor='#ffffff'>";
         var rowstyle = '';
         for(var i=0;i<listArr.length;i++){
	        var strpart = listArr[i][1];
            strpart = strpart.toLowerCase();
            if(strpart.indexOf(txt)!=-1 || (isInit==1&&txt=='')){
               results = results + 1;
			   var filltxtFun = "TipInputList.filltxt('"+inputID+"','"+divID+"','"+listArr[i][1]+"');";
			   var callMethodStr = callMethod;
			   if(callMethodStr!=null&&callMethodStr!=''){
                  callMethodStr = callMethodStr+"('"+listArr[i][0]+"','"+listArr[i][1]+"');";
			   }else {
			      callMethodStr = "";
			   }
			   if(results%2==1){
			  	  rowstyle = '#ECF5FF';
			   }else {
			  	  rowstyle = '#ffffff';
			   }
			   var listStr = " id=\"listInputTDID"+i+"\" onmouseover=\"this.style.background='#eeeeee'\" onmouseout=\"this.style.background='"+rowstyle+"'\""
                            + " onclick=\""+filltxtFun+callMethodStr+"\"";
               newList += "<tr style='background-color:"+rowstyle+";'><td" + listStr + " style='cursor:pointer;padding-top:2px;white-space:nowrap'>&nbsp;" + listArr[i][1] + "</td></tr>";
            }
	     }
	     newList += "</table>";
	  }else{
	     newList = "";
	  }
	  document.getElementById(divID).innerHTML = newList;
	  //parent.document.getElementById("alertFram2").style.width = document.getElementById("listTableID").offsetWidth+18+"px";
   };
  