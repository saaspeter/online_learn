package platform.social.constant;


public class CommentsConstant {

	/** 1. 对象，例如对file或testinfo的comment, 此时objectType和objectID会被用到
        2. URL， 对一个链接地址的comment，加载该网页的时候就可以加载的comment，此时sourceUrl会被用到 **/
	public final static String SourceType_Object = "1";
	
	public final static String SourceType_URL = "URL";
	
	/** 放置comments的目录 **/
	public static final String CommentsFileDir = "commentsFile";
	
	/**
	 * 是否是支持的comments类型. 目前支持的类型有:
	 * product 目前仅支持product, 其他的暂不支持
	 * @param objectType
	 * @return
	 */
	public static boolean isSupportObjectType(String objectType){
		boolean support = false;
		if(objectType!=null){
			if("product".equals(objectType)){
				support = true;
			}else if("learncontent".equals(objectType)) {
				support = true;
			}
		}
		return support;
	}
	
}
