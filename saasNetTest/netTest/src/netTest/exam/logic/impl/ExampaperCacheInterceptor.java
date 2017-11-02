package netTest.exam.logic.impl;

import netTest.common.logic.interceptor.MethodCacheInterceptor;

public class ExampaperCacheInterceptor extends MethodCacheInterceptor {

	/** 缓存key中的类名称 **/
	private String cacheClassname;
	
	@Override
	/**
	 * 查询考试试卷的cache使用的key. 类名.方法名.参数的第一个值(即paperid)
	 * 使用本拦截器的条件：输入的第一个参数必须是paperid
	 */
	protected String getCacheKey(String targetName, String methodName, Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		if(cacheClassname==null||cacheClassname.trim().length()<1)
			cacheClassname = targetName;
	    sb.append(cacheClassname).append(".").append(methodName);
	    if ((arguments != null) && (arguments.length != 0)) {
	        sb.append(".").append(arguments[0]);
	    }
	    return sb.toString();
	}

	public String getCacheClassname() {
		return cacheClassname;
	}

	public void setCacheClassname(String cacheClassname) {
		this.cacheClassname = cacheClassname;
	}
	
	
}
