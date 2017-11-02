package netTest.product.logic;

import netTest.product.vo.Openactivitymember;
import commonTool.base.Page;

public interface OpenactivityLogic {
	
	/**
	 * 查询主界面上可以预定的公开活动，状态是未结束的
	 * @param categoryid
	 * @param name
	 * @param localeid
	 * @param regioncode
	 * @param productid
	 * @param starttimerange: 多少天内可以预定
	 * @param pageIndex
	 * @param pageSize
	 * @param total
	 * @return
	 */
	public Page query(Long categoryid, String name, Long localeid, 
			   String regioncode, Short status, Long shopid,
			   Long productid, Integer starttimerange, boolean checkstarttime,
			   Integer pageIndex, Integer pageSize,Integer total);

	/**
	 * 查询一个用户参加了哪些activity
	 * @param userid
	 * @param pageIndex
	 * @param pageSize
	 * @param total
	 * @return
	 */
	public abstract Page queryActivityByUser(Long userid, int pageIndex,
			int pageSize, Integer total);
	
	/**
	 * join the activity
	 * @param vo
	 */
	public void saveMember(Openactivitymember vo);
	
	/**
	 * join the activity
	 * @param vo
	 */
	public void leaveActivity(Long activityid, Long[] userIdArr, boolean sendNotify);

}