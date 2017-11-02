  
   var SetNullChar = '-999';
  
   function doSelect(){
      var formObj = document.getElementById("list");
      var num = 0;
      var id = "";
      var name = "";
      var ids = "";
      var names = "";
      for (var i=0;i<formObj.elements.length;i++){
		if (formObj.elements[i].name == "keys" && formObj.elements[i].checked == true){
			id = formObj.elements[i].value;
			name = document.getElementById((formObj.elements[i].id+"name")).value;
			num++;
			ids = ids+id+",";
			names = names+name+";";
		}
	  }
	  if(num==0){
	     showMessagePage(getMessage("mulSelUsr_list_selOneUser"));
	     return;
	  }else{
	     ids = ids.substring(0,ids.length-1);
	     if(typeof(parent.selUserProdCB)!="undefined"){
	        parent.selUserProdCB(ids,names);
	     }
	  }
   }
   
   // select product, same with switchProduct()
   function selectProd(objElement, usetype){
	  var divObj = document.getElementById("alertFram2");
      if(typeof(divObj)!="undefined"&&divObj!=null){
         divObj.style.display = "";
      }else{
         var offsetObj = getElementOffset(objElement);
         var posLeft = offsetObj.x;
         var posTop = offsetObj.y+offsetObj.h;
         if(typeof(usetype)=="undefined"||usetype==null){
       	    usetype = '';
         }
         var newdiv = newDiv('/usercontext/listCanuseprod.do?actionType=2&usetype='+usetype,0,0,260,160,posLeft,posTop,'alertFram2');
         //newdiv.onmouseout = function(){ newdiv.style.display = "none"; objElement.focus(); }
         document.getElementById("contentDivFrame").onmouseout = function(){ clearDiv("alertFram2"); objElement.focus(); };
      }
   }
   
   function selProduct_CB(id,name){
   	  var old_prod = document.getElementById("productidId").value;
      if(id!=null && id!=""&& id!=old_prod){
         document.getElementById("productidId").value=id;
         document.getElementById("productnameId").value=name;
      }
      clearDiv("alertFram2");
      var inputArr = document.getElementsByTagName("INPUT");
      if(typeof(inputArr)!=null && inputArr!=null){
    	  for(var i=0; i<inputArr.length; i++){
    		  if(inputArr[i].type=='text'){
    			  inputArr[i].focus();
    		  }
    	  }
      }
   }
   
   // selection multiply operation --- begin 
   // 如果有idHidden则同时还要设置隐藏域的内容
   function addMultiSelOpt(ids,names,selectId,idHidden,nameHidden){   
      if(ids==null||ids==''||names==null||names==''||selectId==null||selectId==''){
         return;
      }
      var idArr = ids.split(",");
      var nameArr = names.split(";");
      var sel = document.getElementById(selectId);   
      var canadd = 1;
      for(var j=0;j<idArr.length;j++){
      	  if(idArr[j]==null||idArr[j]==''){
      	  	 continue;
      	  }
          canadd = 1;
          for(var i=0;i<sel.length;i++){   
              if(sel.options[i].value == idArr[j]){   
                 canadd = 0;
                 break; 
              }   
          }
          if(canadd==1){
             var op = document.createElement("option");
             sel.options.add(op);   
             op.value = idArr[j];    
             op.textContent = nameArr[j];
          }
      }   
      if(idHidden!=null&&typeof(idHidden)!="undefined"&&idHidden!=''){
      	  var back = getSelectedOptions(selectId);
      	  if(back!=null){
      	  	 document.getElementById(idHidden).value = back['ids'];
      	  	 if(nameHidden!=null&&typeof(nameHidden)!="undefined"&&nameHidden!=''){
      	  	 	document.getElementById(nameHidden).value = back['names'];
      	  	 }
      	  }
      }
   }
   
   function rmMultiSelOpt(selectId,idHidden,nameHidden){
      var sel = document.getElementById(selectId);
      for(var i=sel.length-1;i>-1;i--){   
          if(sel.options[i].selected == true){   
             sel.removeChild(sel.options[i]);   
          }   
      }
      if(idHidden!=null&&typeof(idHidden)!="undefined"&&idHidden!=''){
      	  var back = getSelectedOptions(selectId);
      	  if(back!=null){
      	  	 document.getElementById(idHidden).value = back['ids'];
      	  	 if(nameHidden!=null&&typeof(nameHidden)!="undefined"&&nameHidden!=''){
      	  	 	document.getElementById(nameHidden).value = back['names'];
      	  	 }
      	  }
      	  if(sel.length==0){
      	     document.getElementById(idHidden).value = SetNullChar;
          }
      }
      
   }  
   
   function rmAllMultiOpt(selectId,idHidden,nameHidden){
   	  var sel = document.getElementById(selectId);
      for(var i=sel.length-1;i>-1;i--){    
          sel.removeChild(sel.options[i]);   
      }
      if(idHidden!=null&&typeof(idHidden)!="undefined"&&idHidden!=''){
   	     document.getElementById(idHidden).value = '';
      }
      if(nameHidden!=null&&typeof(nameHidden)!="undefined"&&nameHidden!=''){
      	 document.getElementById(nameHidden).value = '';
      }
   }
   
   function getSelectedOptions(selectId){
   	  var back = new Object();
   	  var ids = '';
   	  var names = '';
   	  var sel = document.getElementById(selectId);
   	  for(var i=0;i<sel.length;i++){   
          if(sel.options[i].value!=''){   
             ids = ids+sel.options[i].value+",";
             names = names+sel.options[i].textContent+";";
          }   
      }
      back['ids'] = ids;
      back['names'] = names;
      return back;
   }
   
   // selection multiply operation --- end 
   
   // switch shop
   function switchShop(objElement){
        var divObj = document.getElementById("alertFram_shop");
        if(typeof(divObj)!="undefined"&&divObj!=null){
           divObj.style.display = "";
        }else{
           var offsetObj = getElementOffset(objElement);
           var posLeft = offsetObj.x;
           var posTop = offsetObj.y+offsetObj.h;
           var newdiv = newDiv('/usercontext/listshopforuser.do',0,0,250,200,posLeft,posTop,'alertFram_shop');
           //newdiv.onmouseout = function(){ newdiv.style.display = "none"; }
           document.getElementById("contentDivFrame").onmouseout = function(){ newdiv.style.display = "none"; objElement.focus(); }
        }
    }
    
    // callback of switchShop
    function selShop_CB(id, name){
        var sessionshopid = document.getElementById("shopidId").value;
        if(id==null||id==sessionshopid){
           clearDiv();
           return;
        }
        document.getElementById("shopidId").value = id;
        //document.getElementById("shopnameId").value = name;
        document.getElementById("list").submit();
    }
    
    // switch product. usetype: how to use product, see UserproductConstant.produsetype
    function switchProduct(objElement, usetype, showAllProd){
        var divObj = document.getElementById("alertFram2");
        if(typeof(divObj)!="undefined"&&divObj!=null){
           divObj.style.display = "";
        }else{
           var offsetObj = getElementOffset(objElement);
           var posLeft = offsetObj.x;
           var posTop = offsetObj.y+offsetObj.h;
           if(typeof(usetype)=="undefined"||usetype==null){
           	  usetype = '';
           }
           if(typeof(showAllProd)=="undefined"||showAllProd==null){
        	  showAllProd = '0'; // don't show 'all product' in the open page
           }
           var newdiv = newDiv('/usercontext/listCanuseprod.do?usetype='+usetype+'&showAllProd='+showAllProd,0,0,200,160,posLeft,posTop,'alertFram2');
           //newdiv.onmouseout = function(){ newdiv.style.display = "none"; objElement.focus(); }
           document.getElementById("contentDivFrame").onmouseout = function(){ newdiv.style.display = "none"; objElement.focus(); }
        }
    }
    
    function switchProcut_CB(id, name){
        var sessProductid = document.getElementById("prodidId").value;
        if(id==null||id==sessProductid){
           clearDiv();
           return;
        }
        document.getElementById("prodidId").value = id;
        //document.getElementById("prodnameId").value = name;
        // callback function
        if(typeof(prod_CB_Hook)!="undefined"&&prod_CB_Hook!=null){
        	prod_CB_Hook(id, name);
        }else {
        	document.getElementById("list").submit();
        }
    }
   
    // switch category
    function switchCategory(objElement){
        var divObj = document.getElementById("alertFram2");
        if(typeof(divObj)!="undefined"&&divObj!=null){
           divObj.style.display = "";
        }else{
           var posLeft = getElementOffset(objElement).x+2;
           var posTop = getElementOffset(objElement).y+2;
           var newdiv = newDiv('/productcategory/prodcate_select_all.jsp',0,0,320,500,posLeft,posTop,'alertFram2');
           
           document.onclick = function(e){ 
        	                       if (window.event && !e) {
        	                    	   e=window.event;
        	                       }
        	                       var evt=e.target||e.srcElement;
        	                       if(evt!=objElement && evt.id!='leftbar' && evt.id != 'alertFram2'){
	                                  newdiv.style.display = "none"; 
	                               }
                               };
        }
    }
    
    function switchCategory_CB(id, name){
        var categoryid = document.getElementById("Id_categoryid").value;
        if(id==null||id==categoryid){
           clearDiv();
           return;
        }
        document.getElementById("Id_categoryid").value = id;

        
        document.forms[0].submit();
    }
    
    // select product chapter information
    function selectContcate(productid){
      if(productid==null||productid==""){
         alert('请先选择课程!');
         return;
      }
      var url = "#"+webContext+"/prodcont/selectContcateTree.do?queryVO.productbaseid="+productid+"&selectType=1";
      newDiv(url,1,1,270,300);
   }
   
   function selContcateCB(id,name,params){
       document.getElementById("contentcateidId").value = id;
       document.getElementById("contentcatenameId").value = name;
       if(typeof(contcate_CB_Hook)!="undefined"&&contcate_CB_Hook!=null){
    	  contcate_CB_Hook(id, name);
       }
       clearDiv();
   }
   
   function removeContcate(){
      document.getElementById("contentcateidId").value = '';
      document.getElementById("contentcatenameId").value = '';
      if(typeof(removeContcate_CB_Hook)!="undefined"&&removeContcate_CB_Hook!=null){
    	 removeContcate_CB_Hook();
      }
   }
   
   // 过滤一些特殊符号，以后可能还会增多
function transInputVal(val){
   if(val!=null&&val!=''){
	  var reg0 = new RegExp("〖","g"); 
          val = val.replace(reg0, "/〖");
	  var reg1 = new RegExp("〗","g"); 
          val = val.replace(reg1, "/〗");
	  //var reg2 = new RegExp("<[U|u]>","g"); 
      var reg2 = new RegExp("<span(\\s.*?\\s?)name=\"spantiankong\"(.*?)>(.*?)</span>","g"); 
          val = val.replace(reg2, "〖$3〗");
      //var reg3 = new RegExp("<span name=\"spantiankong\" style=\"border-bottom:2px solid blue;\">(.*?)</span>","g"); 
      //    val = val.replace(reg3, "〖$1〗");
	  //var reg3 = new RegExp("</[U|u]>","g"); 
      //    val = val.replace(reg3, "〗");
//	  var reg4 = new RegExp("<[P|p]>","g"); 
//      val = val.replace(reg4, "");
//	  var reg5 = new RegExp("</[P|p]>","g"); 
//    val = val.replace(reg5, "\n");
	  var reg6 = new RegExp("<[B|b][R|r](/)?>","g"); 
          val = val.replace(reg6, "\n");

//	  var reg14 = new RegExp("&amp;","g"); 
//	      val = val.replace(reg14, "&");
//	  var reg15 = new RegExp("&lt;","g"); 
//	      val = val.replace(reg15, "<");
//	  var reg16 = new RegExp("&gt;","g"); 
//	      val = val.replace(reg16, ">");
//	  var reg17 = new RegExp("&quot;","g");
//	      val = val.replace(reg17,"\"");   
//	  var reg18 = new RegExp("&ldquo;","g");
//	      val = val.replace(reg18,"“");
//	  var reg19 = new RegExp("&rdquo;","g");
//	      val = val.replace(reg19,"”");
//	  var reg20 = new RegExp("&nbsp;","g");
//	      val = val.replace(reg20," ");
   }
   return val;
}
