// this js is for single input text region selection


function showArea(){
	$("#addr").remove();
	var iptName=$(this).attr("id");
	var iptOffSet=$(this).offset();
	var iptLeft=iptOffSet.left-160;
	var iptTop=iptOffSet.top+20;
	var str="<div id='addr'><span>请选择地点<a id='fh'>返回省份列表</a><a id='gb'>[&nbsp;X&nbsp;]</a></span><ul></ul><div style='clear:both;'></div></div>";
	areasLen=provinceArr.length;
	var str2="";
	for(var i=0;i<areasLen;i++){
		str2=str2+"<li id='p"+provinceArr[i][0]+"'>"+provinceArr[i][1]+"</li>";
	}
	$("body").append(str);
	$("#addr ul").append(str2);
	$("#addr").css({left:iptLeft+"px",top:iptTop+"px"});
	$("#addr span a#fh").bind("click",function(){
		$("#addr ul").empty();
		$("#addr ul").append(str2);
		$("#addr ul li").bind("click",{iptn:iptName},liBind);
	});
	$("#addr span a#gb").bind("click",function(){
		$("#addr").remove();
	});
	$("#addr ul li").bind("click",{iptn:iptName},liBind);
}
function setVal(event){
	var setipt2=event.data.ipt2;
	var iptText=$(this).text();
	var iptVal=$(this).attr("id");
	var code = iptVal.substring(1,5);
	$("#"+setipt2+"Val").val(code);
	$("#"+setipt2).val(iptText);
	$("#addr").css("display","none");
	if(typeof(regionCallback)!="undefined"){
	   regionCallback(code, iptText);
	}
}
function liBind(event){
	var setipt=event.data.iptn;
	var liId=$(this).attr("id");
	var liText=$(this).text();
	var pArr=eval(liId);
	pLen=pArr.length;
	if(pLen==0){
	    var code = liId.substring(1,5);
		$("#"+setipt+"Val").val(code);
		$("#"+setipt).val(liText);
		$("#addr").css("display","none");
		if(typeof(regionCallback)!="undefined"){
		   regionCallback(code, liText);
		}
	}
	else{
		listr="";
		for(j=0;j<pLen;j++){
			listr=listr+"<li id='c"+pArr[j][0]+"'>"+pArr[j][1]+"</li>";
		}
		$("#addr ul").empty();
		$("#addr ul").append(listr);
		$("#addr ul li").bind("click",{ipt2:setipt},setVal);
	}	
}
