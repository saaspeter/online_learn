package commonWeb.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.ExceptionConstantBase;
import commonTool.exception.LogicException;


public class FlushCacheAction extends BaseAction{

	static Logger log = Logger.getLogger(FlushCacheAction.class.getName());
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	String cachename = request.getParameter("cachename");
    	String cachekey = request.getParameter("cachekey"); // if is *, then clean all
    	
    	String result = String.valueOf(CommonConstant.success);
		String info = "";
    	try {
    		CacheSynchronizer.getInstance().flushCache(cachename, cachekey);
    		info = KeyInMemoryConstant.deleteSuccessCommon;
		} catch (LogicException e) {
			log.error("error in FlushCacheAction1:", e);
			result = String.valueOf(CommonConstant.fail);
			info = e.getMessage();
		} catch (Exception e){
			log.error("error in FlushCacheAction2:", e);
			result = String.valueOf(CommonConstant.fail);
			info = ExceptionConstantBase.Error_System;
		}
		// 得到错误信息
		info = getResources(request, "BizKey").getMessage(info);
		this.writeAjaxRtn(result, null, info, response);
		return null;
    }

}
