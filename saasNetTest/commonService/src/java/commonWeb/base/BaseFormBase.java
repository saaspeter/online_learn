package commonWeb.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import commonTool.util.StringUtil;


/** 
 * MyEclipse Struts
 * Creation date: 08-08-2007
 */
public class BaseFormBase extends ActionForm {
	
	static Logger log = Logger.getLogger(BaseFormBase.class.getName());
	
	/** 用户要查看的产品或多种语言显示的物品所需用的localeid **/
	protected Long localeid;
	// 要返回页面的链接，没有经过encoder，在页面上不传递该参数
    protected String backUrl;
    // 经过encoder的要返回页面的链接，在页面上传递该参数
    protected String backUrlEncode;
    // 判断提交的变量中是否有backUrlEncode，用以在action处理完毕后页面转向时使用，
    // 如果不设置该标识，直接在保存或删除后转向该backUrl链接，则可能造成死循环??
    // 0代表没有提交backUrlEncode，1代表提交了backUrlEncode
    protected int hasUrlSubmit = 0;
    /** 上层框架的url, 用于iframe页面，当session过期用户重新登录后，转到parentUrl **/
//    protected String parentUrl;

	/** 用户需要用到的js文件后缀名，多国语言显示 **/
    protected String jsSuffix ;
    /** 语言名 **/
    protected String language;
    
    /** 需不需要记录用户过来的url地址，默认强制记录。1代表需要强制记录url（无论backUrlEncode是否已经有值）,
     * 2代表不记录url。3代表记录url，但是如果backUrlEncode已经有值就不再记录了 **/
    protected String recurltype = "3";
    // 是否已经调用过函数setBackUrlEncode，如果已经调用过，就不再调用了
    private boolean setUrlFlag = false;
    
    /** 记录选中记录的值的数组 **/
    protected String[] keys;
    /** 1代表新增记录，2代表修改记录，3代表查看记录 **/
    protected int editType;
	/** 搜索类型 1为只搜索本目录，2为包括下级数据的搜索 3为包含所有级别的数据 **/
    protected String complexSearchDivStatus;

	/** 要删除的记录的数组，其中第一个放的是主键，第2个放的是该记录的类型，
	 * 因为大量采用了表之间的继承关系，因此删除时需要知道记录类型，以便删除子记录 **/
	private String[][] delKeys;
	
	protected static Validator validator;
	
    
	public String getComplexSearchDivStatus() {
		return complexSearchDivStatus;
	}

	public void setComplexSearchDivStatus(String complexSearchDivStatus) {
		this.complexSearchDivStatus = complexSearchDivStatus;
	}

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	   // 清理数据
		backUrl = null;
	    backUrlEncode = null;
	    hasUrlSubmit = 0;
	    keys = null;
	    delKeys = null;
	    editType = 0;
	    complexSearchDivStatus = null;
	    localeid = null;
	    jsSuffix = null;
	    if(request.getParameter("recurltype")!=null&&request.getParameter("recurltype").trim().length()>0){
	    	recurltype = request.getParameter("recurltype").trim();
	    }
	    if("1".equals(recurltype)||
	    		("3".equals(recurltype)&&
	    			(request.getParameter("backUrlEncode")==null
	   			    ||((String)request.getParameter("backUrlEncode")).trim().length()<1)))
	    {
	       	  hasUrlSubmit = 0;
	   		  String params = "";
	   		  Enumeration enumeration = request.getParameterNames();
	   		  while (enumeration.hasMoreElements()) {
	   			 String parameter = (String)(enumeration.nextElement());
	   			 if(parameter!=null&&!"backUrlEncode".equals(parameter)&&!"backUrl".equals(parameter)
	   					 &&!"org.apache.struts.taglib.html.TOKEN".equals(parameter)
	   					 &&!KeyInMemConstBase.DisMessKey.equals(parameter) 
	   					 &&!KeyInMemConstBase.DisMessArg0Key.equals(parameter))
	   		        params += parameter+"="+(String)request.getParameter(parameter)+"&";
	   		  }
	   		  if(params.length()>0)
	   		     params = params.substring(0, params.length()-1);
	   		  String url = request.getRequestURI();
	   		  if(params!=null&&params.length()>0)
	   		     url += "?"+params;
	   		  this.setBackUrlEncode(url);
	    }else
	    	hasUrlSubmit = 1;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		
		return null;
	}
	
	/**
	 * user hibernate validate to do data check
	 * @param obj
	 * @return
	 */
	protected String validateData(Object obj) {
        StringBuffer buffer = new StringBuffer();//用于存储验证后的错误信息
        if(validator==null){
        	synchronized(this){
        		if(validator==null){
        		   ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        		   validator = factory.getValidator();
        		}
        	}
        }
        // 验证某个对象,，其实也可以只验证其中的某一个属性的
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);  
        Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();  
        while (iter.hasNext()) {  
            String message = iter.next().getMessage();  
            buffer.append(message);  
        }  
        return buffer.toString();  
    } 

	public String getBackUrl() {
		return backUrl;
	}

	/**
	 * @deprecated
	 * @param backUrl
	 */
	public void setBackUrl(String backUrl) {
		// 由backUrlEncode设置
		return;
		//this.backUrl = backUrl;
	}
	
	/**
     * 从java类中强行设置backUrlEncode
	 * @param backUrl
	 */
	public void classSetBackUrlEncode(String backUrlEncode) {
		setUrlFlag = false;
		hasUrlSubmit = 1;
		setBackUrlEncode(backUrlEncode);
	}

	public String getBackUrlEncode() {
		return backUrlEncode;
	}

	public void setBackUrlEncode(String backUrlEncode) {
		if(backUrlEncode==null||backUrlEncode.trim().length()<1||setUrlFlag)
			return;
		try {
			this.backUrlEncode = backUrlEncode;
			// 先对地址进行解code付给backUrl，在deocde付给backUrlEncode
			backUrlEncode = URLDecoder.decode(backUrlEncode, "utf-8");
			this.backUrl = backUrlEncode;
			this.backUrlEncode = URLEncoder.encode(backUrlEncode,"utf-8");
			setUrlFlag = true;
		} catch (UnsupportedEncodingException e) {
			log.error("oops, got an exception: ", e);
			this.backUrlEncode = "";
		}
	}
	
//    public String getParentUrl() {
//		return parentUrl;
//	}
//
//	public void setParentUrl(String parentUrl) {
//		if(parentUrl!=null){
//			try {
//				this.parentUrl = URLEncoder.encode(parentUrl,"utf-8");
//			} catch (UnsupportedEncodingException e) {
//				log.error("oops, got an exception: ", e);
//			}
//		}
//	}

	public int getHasUrlSubmit() {
		return hasUrlSubmit;
	}

	public void setHasUrlSubmit(int hasUrlSubmit) {
		this.hasUrlSubmit = hasUrlSubmit;
	}

	public String getJsSuffix() {
		if(jsSuffix==null||jsSuffix.trim().length()<1){
			jsSuffix = BaseActionBase.getLoginInfo(true).getJsSuffix();
		}
		return jsSuffix;
	}

	public void setJsSuffix(String jsSuffix) {
		this.jsSuffix = jsSuffix;
	}

	public String getLanguage() {
		if(language==null||language.trim().length()<1){
			language = BaseActionBase.getLoginInfo(true).getLocale().getLanguage();
		}
		return language;
	}

	public Long getLocaleid() {
		return localeid;
	}

	public void setLocaleid(Long localeid) {
		this.localeid = localeid;
	}

	public String[][] getDelKeys() {
		int len = 0;
		if(this.getKeys()!=null){
		   len = this.getKeys().length;
		   delKeys = new String[len][2];
		   String[] arrTemp = null;
		   for(int i=0;i<len;i++){
			  arrTemp = StringUtil.splitString(keys[i], "$");
			  if(arrTemp!=null&&arrTemp.length>0){
				 delKeys[i][0] = arrTemp[0];
				 if(arrTemp.length>1){
					delKeys[i][1] = arrTemp[1];
				 }
			  }
		   }
		}
		return delKeys;
	}

	public void setDelKeys(String[][] delKeys) {
		this.delKeys = delKeys;
	}

	public String getRecurltype() {
		return recurltype;
	}

	public void setRecurltype(String recurltype) {
		this.recurltype = recurltype;
	}
	
	/**
	 * 检查用户输入的验证码是否正确
	 * @param request
	 * @return
	 */
	protected boolean iscaptchaOK(HttpServletRequest request){
		String inputCode = request.getParameter("captcha_response");
		HttpSession session = request.getSession(true);
		String sessCode = (String)session.getAttribute("CaptchaCode");
		if(inputCode!=null && inputCode.equals(sessCode)){
			return true;
		}else {
			return false;
		}
	}

}
