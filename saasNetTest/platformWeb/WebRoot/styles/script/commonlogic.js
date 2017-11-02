  
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
   
   // select product
   function selectProd(){
      var url = "/usercontext/listCanuseprod.do?actionType=2";
      newDiv(url,1,1,500,300);
   }
   
   function selProduct_CB(id,name){
      if(id!=null&&id!=""){
         document.getElementById("productidId").value=id;
         document.getElementById("productnameId").value=name;
      }
      clearDiv();
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
             op.innerText = nameArr[j];
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
             names = names+sel.options[i].innerText+";";
          }   
      }
      back['ids'] = ids;
      back['names'] = names;
      return back;
   }
   
   // selection multiply operation --- end 
   
   // switch shop
   function switchShop(objElement){
        var divObj = document.getElementById("alertFram1");
        if(typeof(divObj)!="undefined"&&divObj!=null){
           divObj.style.display = "";
        }else{
           var posLeft = getElementOffset(objElement).x+2;
           var posTop = getElementOffset(objElement).y+2;
           var newdiv = newDiv('/usercontext/listshopforuser.do',0,0,250,250,posLeft,posTop,'alertFram1');
           newdiv.onmouseout = function(){ newdiv.style.display = "none"; }
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
        document.getElementById("shopnameId").value = name;
        document.getElementById("list").submit();
    }
    
    // switch product
    function switchProduct(objElement){
        var divObj = document.getElementById("alertFram2");
        if(typeof(divObj)!="undefined"&&divObj!=null){
           divObj.style.display = "";
        }else{
           var posLeft = getElementOffset(objElement).x+2;
           var posTop = getElementOffset(objElement).y+2;
           var newdiv = newDiv('/usercontext/listCanuseprod.do',0,0,250,350,posLeft,posTop,'alertFram2');
           newdiv.onmouseout = function(){ newdiv.style.display = "none"; }
        }
    }
    
    function switchProcut_CB(id, name){
        var sessProductid = document.getElementById("prodidId").value;
        if(id==null||id==sessProductid){
           clearDiv();
           return;
        }
        document.getElementById("prodidId").value = id;
        document.getElementById("prodnameId").value = name;
        document.getElementById("list").submit();
    }
   
    // switch category
    function switchCategory(objElement){
        var divObj = document.getElementById("alertFram2");
        if(typeof(divObj)!="undefined"&&divObj!=null){
           divObj.style.display = "";
        }else{
           var posLeft = getElementOffset(objElement).x+2;
           var posTop = getElementOffset(objElement).y+2;
           var newdiv = newDiv('/productcategory/prodcate_select_all.jsp',0,0,331,495,posLeft,posTop,'alertFram2');
           newdiv.onmouseout = function(){ newdiv.style.display = "none"; }
        }
    }
    
    function switchCategory_CB(id, name){
        var categoryid = document.getElementById("Id_categoryid").value;
        if(id==null||id==categoryid){
           clearDiv();
           return;
        }
        document.getElementById("Id_categoryid").value = id;
        document.getElementById("Id_categoryname").value = name;
        var newscategoryid_original = document.getElementById("Id_newscategoryid_original");
        var newscategoryid = document.getElementById("Id_newscategoryid");
        if(newscategoryid_original!=null&&typeof(newscategoryid_original)!="undefined"
           &&newscategoryid!=null&&typeof(newscategoryid)!="undefined"){
           	newscategoryid.value = newscategoryid_original.value;
        }
        document.forms[0].submit();
    }
   
