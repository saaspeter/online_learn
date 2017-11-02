
//<script type="text/javascript">
//<![CDATA[
var s = "I'd like u,〖124/〗5，/〖iii,ooo,/〗ppp,99〗,but u don`t like me at all,〖ppp〗llll";
var re = /(([^\/])\u3016)|(([^\/])\u3017)/g;

alert(s.replace(re,function(s){
RegExp.lastIndex = 0;
var re = /\u3016|(([^\/])\u3017)/g;
re.test(s);
if(RegExp.$1 != ""){
return RegExp.$2 + "</u>";
}else{
return "<u>";
}
}));
//]]>
//</script> 