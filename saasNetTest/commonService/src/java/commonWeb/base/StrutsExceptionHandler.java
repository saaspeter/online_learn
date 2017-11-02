package commonWeb.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class StrutsExceptionHandler extends ExceptionHandler {

	private static final Log log = LogFactory.getLog(ExceptionHandler.class);
	
	/**
	 * 继承原有的struts处理异常函数，加入记录日志功能
	 */
	public ActionForward execute(Exception ex, ExceptionConfig ae,
	        ActionMapping mapping, ActionForm formInstance,
	        HttpServletRequest request, HttpServletResponse response)
	        throws ServletException {
		// 如果是逻辑异常，则取异常中包含的key的信息
		if(ex instanceof commonTool.exception.LogicException){
			request.setAttribute("errorCode", ex.getMessage());
			String arg0 = ((commonTool.exception.LogicException)ex).
					      getExtraInfoByKey(commonTool.exception.LogicException.Key_arg0);
			if(arg0!=null && !"".equals(arg0)){
				request.setAttribute(commonTool.exception.LogicException.Key_arg0, arg0);
			}
			String message_extra = ((commonTool.exception.LogicException)ex).
				      	  getExtraInfoByKey(commonTool.exception.LogicException.Key_extra_display);
			if(message_extra!=null && !"".equals(message_extra)){
				request.setAttribute(commonTool.exception.LogicException.Key_extra_display, message_extra);
			}
		}else if(ex instanceof commonTool.exception.NoLoginException){
			request.setAttribute("shopid", ex.getMessage());
		}
		log.error("--struts throws exception::"+ex.getMessage(), ex);
		return super.execute(ex, ae, mapping, formInstance, request, response);
	}
	
}
