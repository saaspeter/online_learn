// this regino js is for two level selection box to get region

var where = new Array(); 
//where[0]= new comefrom("$请选择省份","");
function comefrom(loca,locacity) { this.loca = loca; this.locacity = locacity; }

function initRegion(localeid, selectPrivCode, selectCityCode){
   countryregion.getDisplayArr(localeid,function CB_getRegionArr(rtnArr){
	   if(rtnArr!=null&&rtnArr.length>0){
	   	  where = new Array();
	   	  where[0]= new comefrom("$请选择省份","");
	   	  for(var i=0;i<rtnArr.length;i++){
	   	  	  where[i+1] = new comefrom(rtnArr[i][0], rtnArr[i][1]);
	   	  }
	   }
	   initHtmlOption(selectPrivCode, selectCityCode);
    });
}

function splitStr(str, delim){
   if(str==null){
      return str;
   }
   if(str==''||delim==null||delim==''||str.indexOf(delim)==-1){
      return new Array(str);
   }else {
      return str.split(delim);
   }
}

function selectRegion() { 
   var provinceObj = document.getElementById("provinceId");
   var cityObj = document.getElementById("cityId");
   var codenameArr;
   var cityStrArr;
   var varitem;
   var selectProvince = provinceObj.options[provinceObj.selectedIndex].value;
   for(i = 0;i < where.length;i ++) { 
      codenameArr = splitStr(where[i].loca, "$"); 	
      if (codenameArr[0] == selectProvince) { 
	     if(where[i].locacity!=null&&where[i].locacity!=''){
		    // remove all the city options
			cityObj.options.length=0;
			cityStrArr = splitStr(where[i].locacity, "|");
			if(cityStrArr!=null&&cityStrArr.length>0){
		       document.getElementById("cityId_container_id").style.display="block";
		       cityObj.value = selectProvince;
		       for(j=0;j<cityStrArr.length;j++) { 
		       	  if(cityStrArr[j]!=null&&cityStrArr[j]!=''){
			         codenameArr = splitStr(cityStrArr[j], "$"); 	
			         varitem = new Option(codenameArr[1], codenameArr[0]);  
			         cityObj.options.add(varitem); 
		       	  }
		       }
            }			
		 }else {
		 	document.getElementById("cityId_container_id").style.display="none";
		 	cityObj.options.length=0;
		 }
		 break;
      }
   } 
} 

function initHtmlOption(selectPrivCode, selectCityCode) { 
   var provinceObj = document.getElementById("provinceId");
   var cityObj = document.getElementById("cityId");
   provinceObj.options.length=0;
   cityObj.options.length=0;
   var length = where.length; 
   var codenameArr;
   var cityStrArr;
   var cityStr;
   for(k=0;k<where.length;k++) { 
	  codenameArr = splitStr(where[k].loca, "$"); 
	  var varitem = new Option(codenameArr[1], codenameArr[0]); 
	  if(codenameArr[0]==selectPrivCode){
		 varitem.selected = "selected";
		 if(where[k].locacity!=null&&where[k].locacity!=''){
		    cityStrArr = splitStr(where[k].locacity, "|");
		 }
	  }	  
	  provinceObj.options.add(varitem); 
   } 
   codenameArr = null;
   if(cityStrArr!=null&&cityStrArr.length>0){
   	  document.getElementById("cityId_container_id").style.display="block";
	  for(j=0;j<cityStrArr.length;j++) {
	  	 if(cityStrArr[j]!=null&&cityStrArr[j]!=''){
	  	 	codenameArr = splitStr(cityStrArr[j], "$"); 	
		    var varitem = new Option(codenameArr[1], codenameArr[0]); 
			if(codenameArr[0]==selectCityCode){
			   varitem.selected = "selected";
			}	  
			cityObj.options.add(varitem); 
	  	 }
	  } 
   }else {
   	  if(selectCityCode!=null) {
   	  	 cityObj.value = selectCityCode;
   	  }else if(selectPrivCode!=null){
   	  	 cityObj.value = selectPrivCode;
   	  }
   	  document.getElementById("cityId_container_id").style.display="none";
   }
} 
