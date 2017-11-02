
   // get object position: retObj.x  retObj.y
   function getElementOffset(elem){ 
       var offsetTotalX = elem.offsetLeft; 
       var offsetTotalY = elem.offsetTop; 
       var pOffsetElem = elem.offsetParent; 
       while( pOffsetElem ){ 
          offsetTotalX += pOffsetElem.offsetLeft; 
          offsetTotalY += pOffsetElem.offsetTop; 
          pOffsetElem = pOffsetElem.offsetParent; 
       } 
       return { 
            'x' : offsetTotalX, 
            'y' : offsetTotalY 
       } 
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
       } 
   } 

   

