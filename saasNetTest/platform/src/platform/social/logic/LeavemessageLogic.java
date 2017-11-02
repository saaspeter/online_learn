package platform.social.logic;

import commonTool.base.Page;


public interface LeavemessageLogic {

	/**
	 * 查询comments, 同时包括下级子comments
	 * @param queryVO
	 * @return
	 */
	public Page qryMessagePage(String objectid, String objecttype, int pageIndex, int pageSize,Integer total);
	
	/**
	 * delete comments, and update its parent's sub comment number
	 * @param pk
	 */
	public abstract void delMessageByPK(Long pk);

}