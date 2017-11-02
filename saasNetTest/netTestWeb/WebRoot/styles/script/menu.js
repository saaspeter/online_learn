
var lastPressObj = null;

function displaySubMenu(li) {
    var subMenu = li.getElementsByTagName("ul")[0];
    subMenu.style.display = "block";
}

function hideSubMenu(li) {
    var subMenu = li.getElementsByTagName("ul")[0];
    subMenu.style.display = "none";
}

function DoMenu(emid)
{
   var obj = document.getElementById(emid); 
   obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
}

/**
 * 把传进来的对象(<a>)的class设置为按下，把上一个菜单还原
 */
function pressMenu(id){
   if(id==null||id==""){
     return;
   }
   var obj = document.getElementById(id);
   var classname = "press";
   if(lastPressObj!=null){
      lastPressObj.className = "";
   }
   obj.className = classname;
   lastPressObj = obj;
}

function switchTag(tag,content)
{
	for(i=1; i <6; i++)
	{
		if ("tag"+i==tag)
		{
			document.getElementById(tag).getElementsByTagName("a")[0].className="selectli"+i;
			document.getElementById(tag).getElementsByTagName("a")[0].getElementsByTagName("span")[0].className="selectspan"+i;
		}else{
			document.getElementById("tag"+i).getElementsByTagName("a")[0].className="";
			document.getElementById("tag"+i).getElementsByTagName("a")[0].getElementsByTagName("span")[0].className="";
		}
		if ("content"+i==content)
		{
			document.getElementById(content).className="";
		}else{
			document.getElementById("content"+i).className="hidecontent";
		}
		document.getElementById("content").className=content;
	}
}