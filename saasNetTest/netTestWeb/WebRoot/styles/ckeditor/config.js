/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	// need add this, or when edit or save, will insert extra <p>, see http://stackoverflow.com/questions/6969081/why-does-ckeditor-add-line-breaks-and-paragraph-before-initial-text
	CKEDITOR.on('instanceReady', function (ev) {
        ev.editor.dataProcessor.writer.setRules('p',
         {
             indent: false,
             breakBeforeOpen: false,
             breakAfterOpen: false,
             breakBeforeClose: false,
             breakAfterClose: false
         });
    });
	//config.enterMode = CKEDITOR.ENTER_BR;
    //config.shiftEnterMode = CKEDITOR.ENTER_BR;  
	
	config.toolbar_MyFull =
	   [
		  { name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Save', 'Preview', '-', 'Templates' ] },
		  { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
		  { name: 'editing', groups: [ 'find', 'selection' ], items: [ 'Find', 'Replace', '-', 'SelectAll'] },
		  { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
		  '/',
		  { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
		  { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
		  { name: 'insert', items: [ 'Image', 'Flash', 'oembed', 'Table', 'HorizontalRule', 'Smiley' ] },
		  { name: 'specialstring', items: [ 'SpecialChar', 'EqnEditor', 'Syntaxhighlight' ]},
		  '/',
		  { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
		  { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		  { name: 'tools', items: [ 'Maximize' ] },
		  { name: 'others', items: [ '-' ] }
       ];
	
	config.toolbar_MyStandard =
	   [
		  { name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Save', 'Preview', '-', 'Templates' ] },
		  { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
		  { name: 'editing', groups: [ 'find', 'selection' ], items: [ 'Find', 'Replace', '-', 'SelectAll' ] },
		  { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
		  '/',
		  { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
		  { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
		  { name: 'insert', items: [ 'Flash', 'oembed', 'Table', 'HorizontalRule', 'Smiley' ] },
		  { name: 'specialstring', items: [ 'SpecialChar', 'EqnEditor', 'Syntaxhighlight' ]},
		  '/',
		  { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
		  { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		  { name: 'tools', items: [ 'Maximize' ] },
		  { name: 'others', items: [ '-' ] }
       ];
	
	config.toolbar_MySimplest =
	   [	  
	      { name: 'clipboard', items: [ 'Cut' , 'Copy' , 'Paste'] },
	      { name: 'insert', items: [ 'Table' , 'SpecialChar' ] },
	      { name: 'basicstyles', items:[ 'Bold' , 'Italic' , 'Underline' , 'Strike', 'RemoveFormat' ] },
	      { name: 'colors', items: [ 'TextColor','BGColor' ] },
	      { name: 'paragraph', items:[ 'BulletedList' ] },
	      { name: 'links', items: [ 'Link' , 'Unlink' ] }
       ];

	config.toolbar_question =
	   [
	      { name: 'question', items: [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','SpecialChar','EqnEditor','Syntaxhighlight' ] }
       ];
	
    config.toolbar_tiankong =
	   [
	      { name: 'tiankong', items: [ 'Tiankong','-','Bold','Italic','Strike','Subscript','Superscript','-','SpecialChar','EqnEditor','Syntaxhighlight' ] }
       ];

    //config.extraPlugins = 'youtube,tiankong,syntaxhighlight';
    config.extraPlugins = 'image2,tiankong';
    // remove the image plug-in preview text
    config.image_previewText = ' ';
    config.oembed_WrapperClass = 'embededContent';
    
    // change default font size
    config.fontSize_defaultLabel = '14px';
	
};
