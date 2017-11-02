/**
 * modified by badqiu (badqiu@gmail.com)
 */

/*
 * Really easy field validation with Prototype
 * http://tetlaw.id.au/view/blog/really-easy-field-validation-with-prototype
 * Andrew Tetlaw
 * Version 1.5.3 (2006-07-15)
 * 
 * Copyright (c) 2006 Andrew Tetlaw
 * http://www.opensource.org/licenses/mit-license.php
 */
Validator = Class.create();

Validator.messagesSourceEn = [
	['validation-failed' , 'Validation failed.'],
	['required' , 'This is a required field.'],
	['validate-number' , 'Please enter a valid number in this field.'],
	['validate-digits' , 'Please use numbers only in this field. please avoid spaces or other characters such as dots or commas.'],
	['validate-alpha' , 'Please use letters only (a-z) in this field.'],
	['validate-alphanum' , 'Please use only letters (a-z) or numbers (0-9) only in this field. No spaces or other characters are allowed.'],
	['validate-date' , 'Please enter a valid date.'],
	['validate-email' , 'Please enter a valid email address. For example fred@domain.com .'],
	['validate-url' , 'Please enter a valid URL.'],
	['validate-date-au' , 'Please use this date format: dd/mm/yyyy. For example 17/03/2006 for the 17th of March, 2006.'],
	['validate-currency-dollar' , 'Please enter a valid $ amount. For example $100.00 .'],
	['validate-one-required' , 'Please select one of the above options.'],
	['validate-date-cn' , 'Please use this date format: yyyy-mm-dd. For example 2006-03-16.'],
	['validate-integer' , 'Please enter a valid integer in this field'],
	['min-value' , 'min value is %s.'],
	['max-value' , 'max value is %s.'],
	['min-length' , 'min length is %s,current length is %s.'],
	['max-length' , 'max length is %s,current length is %s.'],
	['validate-int-range' , 'Please enter integer value between %s and %s'],
	['validate-float-range' , 'Please enter number between %s and %s'],
	['validate-length-range' , 'Please enter value length between %s and %s,current length is %s'],
	['validate-file' , 'Please enter file type in [%s]'],
	['validate-pattern' , 'Validation failed.'],
	['validate-chinese','Please enter chinese'],
	['validate-ip','Please enter a valid IP address'],
	['validate-phone','Please enter a valid phone number,current length is %s.'],
	['validate-mobile-phone','Please enter a valid mobile phone,For example 13910001000.current length is %s.'],
	['validate-equals','Conflicting with above value.']
	['less-than','Input value must be less than above value.'],
	['great-than','Input value must be great than above value.']
]

Validator.messagesSourceCn = [
	['validation-failed' , '验证失败.'],
	['required' , '请输入值.'],
	['validate-number' , '请输入有效的数字.'],
	['validate-digits' , '请输入一个数字. 避免输入空格],逗号,分号等字符'],
	['validate-alpha' , '请输入英文字母.'],
	['validate-alphanum' , '请输入英文字母或是数字,其它字符是不允许的.'],
	['validate-date' , '请输入有效的日期.'],
	['validate-email' , '请输入有效的邮件地址,如 username@example.com.'],
	['validate-url' , '请输入有效的URL地址.'],
	['validate-date-au' , 'Please use this date format: dd/mm/yyyy. For example 17/03/2006 for the 17th of March, 2006.'],
	['validate-currency-dollar' , 'Please enter a valid $ amount. For example $100.00 .'],
	['validate-one-required' , '在上面选项至少选择一个.'],
	['validate-date-cn' , '请使用这样的日期格式: yyyy-mm-dd. 例如:2006-03-17.'],
	['validate-integer' , '请输入正确的整数'],
	['min-value' , '最小值为%s'],
	['max-value' , '最大值为%s'],
	['min-length' , '最小长度为%s,当前长度为%s.'],
	['max-length', '最大长度为%s,当前长度为%s.'],
	['validate-int-range' , '输入值应该为 %s 至 %s 的整数'],
	['validate-float-range' , '输入值应该为 %s 至 %s 的数字'],
	['validate-length-range' , '输入值的长度应该在 %s 至 %s 之间,当前长度为%s'],
	['validate-file' , '文件类型应该为[%s]其中之一'],
	['validate-pattern' , '输入的值不匹配'],
	['validate-chinese','请输入中文'],
	['validate-ip','请输入正确的IP地址'],
	['validate-phone','请输入正确的电话号码,如:0920-29392929,当前长度为%s.'],
	['validate-mobile-phone','请输入正确的手机号码,当前长度为%s.'],
	['validate-equals','与上面不一至,请重新输入'],
	['less-than','应该小于前面的值'],
	['great-than','应该大于前面的值']
]

Validator.messagesSource = Validator.messagesSourceCn;
Validator.messages = {};
//init Validator.messages
Validator.messagesSource.each(function(ms){
	Validator.messages[ms[0]] = ms[1];
});

Validator.format = function(str,args) {
	args = args || [];
	Validation.assert(args.constructor == Array,"Validator.format() arguement 'args' must is Array");
	var result = str
	for (var i = 0; i < args.length; i++){
		result = result.replace(/%s/, args[i]);	
	}
	return result;
}

Validator.prototype = {
	initialize : function(className, error, test, options) {
		this.options = Object.extend({}, options || {});
		this._test = test ? test : function(v,elm){ return true };
		this._error = error ? error : Validator.messages['validation-failed'];
		this.className = className;
	},
	test : function(v, elm) {
		if(this.options.depends && this.options.depends.length > 0) {
			var dependsResult = $A(this.options.depends).all(function(depend){
				return Validation.get(depend).test(v,elm);
			});
			if(!dependsResult) return dependsResult;
		}
		if(!elm) elm = {}
		return this._test(v,elm,Validation.getArgumentsByClassName(this.className,elm.className),this);
	},
	error : function(v,elm,useTitle) {
		var dependError = null;
		$A(this.options.depends).any(function(depend){
			var validation = Validation.get(depend);
			if(!validation.test(v,elm))  {
				dependError = validation.error(v,elm,useTitle)
				return true;
			}
			return false;
		});
		if(dependError != null) return dependError;

		var args  = Validation.getArgumentsByClassName(this.className,elm.className);
		var error = this._error;
		if(typeof error == 'string') {
			if(v) args.push(v.length);
			error = Validator.format(this._error,args);
		}else if(typeof error == 'function') {
			error = error(v,elm,args,this);
		}else {
			alert('error must type of string or function');
		}
		if(!useTitle) useTitle = elm.className.indexOf('useTitle') >= 0;
		return useTitle ? ((elm && elm.title) ? elm.title : error) : error;
	}
}

var Validation = Class.create();

Validation.prototype = {
	initialize : function(form, options){
		this.options = Object.extend({
			onSubmit : true,
			stopOnFirst : false,
			immediate : false,
			focusOnError : true,
			useTitles : false,
			onFormValidate : function(result, form) {},
			onElementValidate : function(result, elm) {}
		}, options || {});
		this.form = $(form);
		var id =  Validation.getElmID(this.form);
		Validation.validations[id] = this;
		if(this.options.onSubmit) Event.observe(this.form,'submit',this.onSubmit.bind(this),false);
		if(this.options.immediate) {
			var useTitles = this.options.useTitles;
			var callback = this.options.onElementValidate;
			Form.getElements(this.form).each(function(input) { // Thanks Mike!
				Event.observe(input, 'blur', function(ev) { Validation.validate(Event.element(ev),{useTitle : useTitles, onElementValidate : callback}); });
			});
		}
	},
	onSubmit :  function(ev){
		if(!this.validate()) Event.stop(ev);
	},
	validate : function() {
		var result = false;
		var useTitles = this.options.useTitles;
		var callback = this.options.onElementValidate;
		if(this.options.stopOnFirst) {
			result = Form.getElements(this.form).all(function(elm) { return Validation.validate(elm,{useTitle : useTitles, onElementValidate : callback}); });
		} else {
			result = Form.getElements(this.form).collect(function(elm) { return Validation.validate(elm,{useTitle : useTitles, onElementValidate : callback}); }).all();
		}
		if(!result && this.options.focusOnError) {
			var first = Form.getElements(this.form).findAll(function(elm){return $(elm).hasClassName('validation-failed')}).first();
			if(first.select) first.select();
			first.focus();
		}
		this.options.onFormValidate(result, this.form);
		return result;
	},
	reset : function() {
		Form.getElements(this.form).each(Validation.reset);
	}
}

Object.extend(Validation, {
	validate : function(elm, options){
		options = Object.extend({
			useTitle : false,
			onElementValidate : function(result, elm) {}
		}, options || {});
		elm = $(elm);
		var cn = elm.classNames();
		return result = cn.all(function(value) {
			var test = Validation.test(value,elm,options.useTitle);
			options.onElementValidate(test, elm);
			return test;
		});
	},
	_getInputValue : function(elm) {
		var elm = $(elm);
		if(elm.type.toLowerCase() == 'file') {
			return elm.value;
		}else {
			return $F(elm);
		}
	},
	_getErrorMsg : function(useTitle,elm,validation) {
		return validation.error(Validation._getInputValue(elm),elm,useTitle);
	},
	test : function(name, elm, useTitle) {
		var v = Validation.get(name);
		var prop = '__advice'+name.camelize();
		if(Validation.isVisible(elm) && !v.test(Validation._getInputValue(elm),elm)) {
			if(!elm[prop]) {
				var advice = Validation.getAdvice(name, elm);
				if(typeof advice == 'undefined') {
					var errorMsg = Validation._getErrorMsg(useTitle,elm,v);
					advice = '<div class="validation-advice" id="advice-' + name + '-' + Validation.getElmID(elm) +'" style="display:none">' + errorMsg + '</div>'
					switch (elm.type.toLowerCase()) {
						case 'checkbox':
						case 'radio':
							var p = elm.parentNode;
							if(p) {
								new Insertion.Bottom(p, advice);
							} else {
								new Insertion.After(elm, advice);
							}
							break;
						default:
							new Insertion.After(elm, advice);
				    }
					advice = $('advice-' + name + '-' + Validation.getElmID(elm));
				}
				if(typeof Effect == 'undefined') {
					advice.style.display = 'block';
				} else {
					new Effect.Appear(advice, {duration : 1 });
				}
			}
			var advice = Validation.getAdvice(name, elm);
			advice.innerHTML = Validation._getErrorMsg(useTitle,elm,v);
			elm[prop] = true;
			elm.removeClassName('validation-passed');
			elm.addClassName('validation-failed');
			return false;
		} else {
			var advice = Validation.getAdvice(name, elm);
			if(typeof advice != 'undefined') {
				if(typeof Effect == 'undefined')
					advice.hide()
				else 
					new Effect.Fade(advice, {duration : 1 });
			}
			
			elm[prop] = '';
			elm.removeClassName('validation-failed');
			elm.addClassName('validation-passed');
			return true;
		}
	},
	isVisible : function(elm) {
		while(elm && elm.tagName != 'BODY') {
			if(!$(elm).visible()) return false;
			elm = elm.parentNode;
		}
		return true;
	},
	getAdvice : function(name, elm) {
		return Try.these(
			function(){ return $('advice-' + name + '-' + Validation.getElmID(elm)) },
			function(){ return $('advice-' + Validation.getElmID(elm)) }
		);
	},
	getElmID : function(elm) {
		return elm.id ? elm.id : elm.name;
	},
	reset : function(elm) {
		elm = $(elm);
		var cn = elm.classNames();
		cn.each(function(value) {
			var prop = '__advice'+value.camelize();
			if(elm[prop]) {
				var advice = Validation.getAdvice(value, elm);
				advice.hide();
				elm[prop] = '';
			}
			elm.removeClassName('validation-failed');
			elm.removeClassName('validation-passed');
		});
	},
	add : function(className, error, test, options) {
		var nv = {};
		nv[className] = new Validator(className, error, test, options);
		Object.extend(Validation.methods, nv);
	},
	addAllThese : function(validators) {
		var nv = {};
		$A(validators).each(function(value) {
				nv[value[0]] = new Validator(value[0], value[1], value[2], (value.length > 3 ? value[3] : {}));
			});
		Object.extend(Validation.methods, nv);
	},
	get : function(name) {
		var resultMethodName;
		for(var methodName in Validation.methods) {
			if(name == methodName) {
				resultMethodName = methodName;
				break;
			}
			if(name.indexOf(methodName) >= 0) {
				resultMethodName = methodName;
			}
		}
		return Validation.methods[resultMethodName] ? Validation.methods[resultMethodName] : new Validator();
		//return  Validation.methods[name] ? Validation.methods[name] : new Validator();
	},
	// ͨ��classname���ݵĲ������ͨ��'-'�ָ������
	getArgumentsByClassName : function(prefix,className) {
		if(!className || !prefix)
			return [];
		var pattern = new RegExp(prefix+'-(\\S+)');
		var matchs = className.match(pattern);
		if(!matchs)
			return [];
		var results = [];
		var args =  matchs[1].split('-');
		for(var i = 0; i < args.length; i++) {
			if(args[i] == '') {
				if(i+1 < args.length) args[i+1] = '-'+args[i+1];
			}else{
				results.push(args[i]);
			}
		}
		return results;
	},
	assert : function(condition,message) {
		var errorMessage = message || ("assert failed error,condition="+condition);
		if (!condition) {
			alert(errorMessage);
			throw new Error(errorMessage);
		}else {
			return condition;
		}
	},
	methods : {}
});

Validation.add('IsEmpty', '', function(v) {
				return  ((v == null) || (v.length == 0)); // || /^\s+$/.test(v));
			});

Validation.addAllThese([
	['required', Validator.messages['required'], function(v) {
				return !(Validation.get('IsEmpty').test(v) || /^\s+$/.test(v));
			}],
	['validate-number', Validator.messages['validate-number'], function(v) {
				return Validation.get('IsEmpty').test(v) || (!isNaN(v) && !/^\s+$/.test(v));
			}],
	['validate-digits', Validator.messages['validate-digits'], function(v) {
				return Validation.get('IsEmpty').test(v) ||  !/[^\d]/.test(v);
			}],
	['validate-alpha', Validator.messages['validate-alpha'], function (v) {
				return Validation.get('IsEmpty').test(v) ||  /^[a-zA-Z]+$/.test(v)
			}],
	['validate-alphanum', Validator.messages['validate-alphanum'], function(v) {
				return Validation.get('IsEmpty').test(v) ||  !/\W/.test(v)
			}],
	['validate-date', Validator.messages['validate-date'], function(v) {
				var test = new Date(v);
				return Validation.get('IsEmpty').test(v) || !isNaN(test);
			}],
	['validate-email', Validator.messages['validate-email'], function (v) {
				return Validation.get('IsEmpty').test(v) || /\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/.test(v)
			}],
	['validate-url', Validator.messages['validate-url'], function (v) {
				return Validation.get('IsEmpty').test(v) || /^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i.test(v)
			}],
	['validate-date-au', Validator.messages['validate-date-au'], function(v) {
				if(Validation.get('IsEmpty').test(v)) return true;
				var regex = /^(\d{2})\/(\d{2})\/(\d{4})$/;
				if(!regex.test(v)) return false;
				var d = new Date(v.replace(regex, '$2/$1/$3'));
				return ( parseInt(RegExp.$2, 10) == (1+d.getMonth()) ) && 
							(parseInt(RegExp.$1, 10) == d.getDate()) && 
							(parseInt(RegExp.$3, 10) == d.getFullYear() );
			}],
	['validate-currency-dollar', Validator.messages['validate-currency-dollar'], function(v) {
				// [$]1[##][,###]+[.##]
				// [$]1###+[.##]
				// [$]0.##
				// [$].##
				return Validation.get('IsEmpty').test(v) ||  /^\$?\-?([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/.test(v)
			}],
	['validate-one-required', Validator.messages['validate-one-required'], function (v,elm) {
				var p = elm.parentNode;
				var options = p.getElementsByTagName('INPUT');
				return $A(options).any(function(elm) {
					return $F(elm);
				});
			}]
]);


//custom validate start
Validation.addAllThese([
	['validate-date-cn', Validator.messages['validate-date-cn'], function(v) {
				if(Validation.get('IsEmpty').test(v)) return true;
				var regex = /^(\d{4})-(\d{2})-(\d{2})$/;
				if(!regex.test(v)) return false;
				var d = new Date(v.replace(regex, '$1/$2/$3'));
				return ( parseInt(RegExp.$2, 10) == (1+d.getMonth()) ) && 
							(parseInt(RegExp.$3, 10) == d.getDate()) && 
							(parseInt(RegExp.$1, 10) == d.getFullYear() );
			}],

	['validate-integer', Validator.messages['validate-integer'], function(v) {
				return Validation.get('IsEmpty').test(v) || (/^[-+]?[\d]+$/.test(v));
			}],

	['validate-chinese', Validator.messages['validate-chinese'], function(v) {
				return Validation.get('IsEmpty').test(v) || (/^[\u4e00-\u9fa5]+$/.test(v));
			}],

	['validate-ip', Validator.messages['validate-ip'], function(v) {
				return Validation.get('IsEmpty').test(v) || (/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(v));
			}],

	['validate-phone', Validator.messages['validate-phone'], function(v) {
				return Validation.get('IsEmpty').test(v) || /^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/.test(v);
			}],

	['validate-mobile-phone', Validator.messages['validate-mobile-phone'], function(v) {
				return Validation.get('IsEmpty').test(v) || (/(^0?[1][35][0-9]{9}$)/.test(v));
			}],
	/**
	 * Usage : validate-equals-otherInputId
	 * Example : validate-equals-username or validate-equals-email etc..
	 */
	['validate-equals',Validator.messages['validate-equals'], function(v,elm,args,metadata) {
				return Validation.get('IsEmpty').test(v) || $F(args[0]) == v;
			}],
	/**
	 * Usage : less-than-otherInputId
	 */
	['less-than',Validator.messages['less-than'], function(v,elm,args,metadata) {
				if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($F(args[0])))
					return Validation.get('IsEmpty').test(v) || parseFloat(v) < parseFloat($F(args[0]));
				return Validation.get('IsEmpty').test(v) || v < $F(args[0]);
			}],
	/**
	 * Usage : great-than-otherInputId
	 */
	['great-than',Validator.messages['great-than'], function(v,elm,args,metadata) {
				if(Validation.get('validate-number').test(v) && Validation.get('validate-number').test($F(args[0])))
					return Validation.get('IsEmpty').test(v) || parseFloat(v) > parseFloat($F(args[0]));
				return Validation.get('IsEmpty').test(v) || v > $F(args[0]);
			}],
	/*
	 * Usage: min-length-number
	 * Example: min-length-10
	 */
	['min-length',Validator.messages['min-length'],function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || v.length >= parseInt(args[0]);
	}],
	/*
	 * Usage: max-length-number
	 * Example: max-length-10
	 */
	['max-length',Validator.messages['max-length'],function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || v.length <= parseInt(args[0]);
	}],
	/*
	 * Usage: validate-file-type1-type2-typeX
	 * Example: validate-file-png-jpg-jpeg
	 */
	['validate-file', function(v,elm,args,metadata) {
		return Validator.format(Validator.messages['validate-file'],[args.join(',')]);
	},function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || $A(args).any(function(extentionName) {
			return new RegExp('\\.'+extentionName+'$','i').test(v);
		});
	}],
	/*
	 * Usage: validate-float-range-minValue-maxValue
	 * Example: -2.1 to 3 = validate-float-range--2.1-3
	 */
	['validate-float-range', Validator.messages['validate-float-range'],function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || (parseFloat(v) >= parseFloat(args[0]) && parseFloat(v) <= parseFloat(args[1]))
	},{depends : ['validate-number']}],
	/*
	 * Usage: validate-int-range-minValue-maxValue
	 * Example: -10 to 20 = validate-int-range--10-20
	 */
	['validate-int-range',Validator.messages['validate-int-range'],function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || (parseInt(v) >= parseInt(args[0]) && parseInt(v) <= parseInt(args[1]))
	},{depends : ['validate-integer']}],
	/*
	 * Usage: validate-length-range-minLength-maxLength
	 * Example: 10 to 20 = validate-length-range-10-20
	 */
	['validate-length-range', Validator.messages['validate-length-range'],function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || (v.length >= parseInt(args[0]) && v.length <= parseInt(args[1]))
	}],
	/*
	 * Usage: max-value-number
	 * Example: max-value-10
	 */
	['max-value',Validator.messages['max-value'] ,function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || parseFloat(v) <= parseFloat(args[0]);
	},{depends : ['validate-number']}],
	/*
	 * Usage: min-value-number
	 * Example: min-value-10
	 */
	['min-value',Validator.messages['min-value'],function(v,elm,args,metadata) {
		return Validation.get('IsEmpty').test(v) || parseFloat(v) >= parseFloat(args[0]);
	},{depends : ['validate-number']}],
	/*
	 * Usage: validate-pattern-RegExp
	 * Example: <input id='sex' class='validate-pattern-/^[fm]$/i'>
	 */
	['validate-pattern',Validator.messages['validate-pattern'],function(v,elm,args,metadata) {
		var extractPattern = /validate-pattern-\/(\S*)\/(\S*)?/;
		Validation.assert(extractPattern.test(elm.className),"invalid validate-pattern expression,example: validate-pattern-/a/i");
		elm.className.match(extractPattern);
		return Validation.get('IsEmpty').test(v) || new RegExp(RegExp.$1,RegExp.$2).test(v);
	}],
	/*
	 * Example: <input id='email' class='validate-ajax' validateUrl='http://localhost:8080/validate-email.jsp' validateFailedMessage='email already exists'>
	 */
	['validate-ajax',function(v,elm,args,metadata) {
		return elm.getAttribute('validateFailedMessage') || Validator.messages['validation-failed'];
	},function(v,elm,args,metadata) {
		Validation.assert(elm.getAttribute('validateUrl'),'element validate by ajax must has "validateUrl" attribute');
		//Validation.assert(elm.getAttribute('validateFailedMessage'),'element validate by ajax must has "validateFailedMessage" attribute');
		
		if(elm._ajaxValidating && elm._hasAjaxValidateResult) {
			elm._ajaxValidating = false;
			elm._hasAjaxValidateResult = false;
			return elm._ajaxValidateResult;
		}

		var sendRequest = function() {
			new Ajax.Request(elm.getAttribute('validateUrl'),{
				parameters : Form.Element.serialize(elm),
				onSuccess : function(response) {
					if('true' != response.responseText.strip()  && 'false' != response.responseText.strip())
						Validation.assert(false,'validate by ajax,response.responseText must equals "true" or "false",actual='+response.responseText);
					elm._ajaxValidateResult = eval(response.responseText);
					elm._hasAjaxValidateResult = true;
					Validation.test('validate-ajax',elm);
				}
			});
			elm._ajaxValidating = true;
			return true;
		}

		return elm._ajaxValidating || Validation.get('IsEmpty').test(v) || sendRequest();
	}]
]);

Validation.validations = {};
Validation.autoBind = function() {
	 var forms = document.getElementsByClassName('required-validate');
	 $A(forms).each(function(form){
		var validation = new Validation(form,{immediate:true});
		Event.observe(form,'reset',function() {validation.reset();},false);
	 });
};

Validation.$ = function(id) {
	return Validation.validations[id];
}

Event.observe(window,'load',Validation.autoBind,false);