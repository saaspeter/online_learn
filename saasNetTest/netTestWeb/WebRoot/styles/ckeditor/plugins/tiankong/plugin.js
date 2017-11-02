/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

/**
 * @file Print Plugin
 */

CKEDITOR.plugins.add( 'tiankong',
{
  //requires : [ 'widget', 'button' ],
  icons: 'tiankong',
  init : function( editor )
  {
    // This "addButtonCommand" function isn't needed, but
    // would be useful if you want to add multiple buttons
    var addButtonCommand = function( buttonName, buttonLabel, commandName, styleDefiniton )
    {
      var style = new CKEDITOR.style( styleDefiniton );
      editor.attachStyleStateChange( style, function( state )
        {
          !editor.readOnly && editor.getCommand( commandName ).setState( state );
        });

      editor.addCommand( commandName, new CKEDITOR.styleCommand( style ) );
      editor.ui.addButton( buttonName,
        {
          label : buttonLabel,
          command : commandName,
          icon: CKEDITOR.plugins.getPath('tiankong') + "images/tiankong.png"
        });
    };

    var config = editor.config;
    //lang = editor.lang;
    var langcode = editor.langCode;
    langcode = getLangCode(langcode);
    var labelname = labelArr[langcode];
    
    // This version uses the language functionality, as used in "basicstyles"
    // you'll need to add the label to each language definition file
    addButtonCommand( 'Tiankong', labelname, 'tiankong', config.coreStyles_tiankong );

    // This version hard codes the label for the button by replacing `lang.red` with 'Red'
    //addButtonCommand( 'Red'   , 'Red'   , 'red'   , config.coreStyles_red );
  }
});

// CKEDITOR.config.coreStyles_red = { element : 'span', attributes : { 'class': 'tiankong', 'style' : 'background-color: yellow;', 'title' : 'Custom Format Entry' } };
// You can assign multiple attributes too
CKEDITOR.config.coreStyles_tiankong = { element : 'span', attributes : { 'name': 'spantiankong', 'style': 'border-bottom-color:blue; border-bottom-style:solid; border-bottom-width:2px'} };


function getLangCode(langcode){
	if(langcode.indexOf("en-")==0){
    	langcode = "en";
    }
	return langcode;
}

var labelArr = new Array();
labelArr['en']="mark FillBlank";
labelArr['zh']="設置填空題";
labelArr['zh-cn']="设置填空题";
