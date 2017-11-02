package platform.social.logic;

import commonTool.base.Page;

public interface CommentsLogic {

	/**
	 * 查询comments分页,page中包括顶级comments, 同时包括下级子comments
	 * @param queryVO
	 * @return
	 */
	public Page qryComments(String objectid, String objecttype, String subclassid, Long creatorid, int pageIndex, int pageSize,Integer total);
	
	/**
	 * delete comments, and update its parent's sub comment number
	 * @param pk
	 */
	public abstract void delCommentsByPK(Long pk);

}