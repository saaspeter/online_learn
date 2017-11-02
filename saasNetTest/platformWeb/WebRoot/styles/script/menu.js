
var lastPressObj = null;


function GetMenuID()
{
   var MenuID="";
   var _paramStr = new String(window.location.href);
   var _sharpPos = _paramStr.indexOf("#");
   if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)
   {
      _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);
   }else{
     _paramStr = "";
   }
   if (_paramStr.length > 0)
   {
     var _paramArr = _paramStr.split("&");
     if (_paramArr.length>0)
     {
        var _paramKeyVal = _paramArr[0].split("=");
        if (_paramKeyVal.length>0)
        {
            MenuID = _paramKeyVal[1];
        }
     }
   }
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