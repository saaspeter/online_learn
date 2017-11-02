package netTest.exam.logic.impl;

import commonTool.util.StringUtil;

import netTest.common.logic.interceptor.MethodCacheAfterAdvice;

public class ExampaperCacheAfterAdvice extends MethodCacheAfterAdvice {
    
	/** 需要清理的缓存key中的类名称 **/
	private String cacheClassname;
	
	/** 需要清理的缓存key中的方法名称数组，每个数组元素是2个字符串的集合，其中以逗号(,)隔开，
	 *  逗号前的字符串是过滤的方法名，逗号后的是要清除的cache中key的方法名 **/
	private String[] cacheMethodnameArr;
	
	@Override
	/**
	 * 查询考试试卷的cache使用的key. 类名.方法名.参数的第一个值(即paperid)
	 * 使用本拦截器的条件：输入的第一个参数必须是paperid
	 */
	protected String getCacheKey(String targetName, String methodName, Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		String cacheMethodname = methodName;
		if(cacheClassname==null||cacheClassname.trim().length()<1)
			cacheClassname = targetName;
		if(cacheMethodnameArr!=null){
			String[] tempArr = null;
			for(int i=0;i<cacheMethodnameArr.length;i++){
				tempArr = StringUtil.splitString(cacheMethodnameArr[i], ",");
				// 如果配置的字符串中第一个串是正在执行的方法，则取配置字符串的第2个串作为要清理的cache中的方法名
				if(tempArr!=null&&tempArr.length==2&&methodName.equals(tempArr[0])){
					cacheMethodname = tempArr[1];
					break;
				}
			}
		}
	    sb.append(cacheClassname).append(".").append(cacheMethodname);
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

	public String[] getCacheMethodnameArr() {
		return cacheMethodnameArr;
	}

	public void setCacheMethodnameArr(String[] cacheMethodnameArr) {
		this.cacheMethodnameArr = cacheMethodnameArr;
	}
	
}
