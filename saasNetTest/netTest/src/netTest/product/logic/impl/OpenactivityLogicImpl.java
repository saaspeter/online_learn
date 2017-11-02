package netTest.product.logic.impl;

import java.util.Date;

import commonTool.base.Page;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import netTest.bean.BeanFactory;
import netTest.product.dao.OpenactivityDao;
import netTest.product.dao.OpenactivitymemberDao;
import netTest.product.logic.OpenactivityLogic;
import netTest.product.vo.Openactivity;
import netTest.product.vo.Openactivitymember;

public class OpenactivityLogicImpl implements OpenactivityLogic {

	private OpenactivityDao openactivityDao;
	
	private OpenactivitymemberDao openactivitymemberDao;
	
	/**
	 * 查询主界面上可以预定的公开活动，状态是未结束的
	 * @param categoryid
	 * @param name
	 * @param localeid
	 * @param regioncode
	 * @param status
	 * @param shopid
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
			   Integer pageIndex, Integer pageSize,Integer total)
	{
		Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		Date starttime2 = null;
		Date endtime1 = null;
		if(checkstarttime && starttimerange!=null){
			// starttimerange=-1 表示查询所有开始时间的活动
			if(starttimerange!=-1 && starttimerange!=0){
			   starttime2 = DateUtil.toEndOfTheDay(DateUtil.dateAddDays(currentDate, starttimerange));
			}
			// only not all, set lease beginning day
			if(starttimerange!=-1){
			   endtime1 = DateUtil.toBeginningOfTheDay(currentDate);
			}
		}
		return openactivityDao.query(categoryid, name, localeid, regioncode, status, shopid, 
				productid, null, starttime2, endtime1, null, pageIndex, pageSize, total);
	}

	/* 得到用户参加的activity list
	 * @return page: Openactivitymember list page
	 */
	@Override
	public Page queryActivityByUser(Long userid, int pageIndex, int pageSize, Integer total){
		Page page = openactivitymemberDao.selectByVOPage(null, userid, null, pageIndex, pageSize, total);
	    if(page!=null && page.getList()!=null && page.getList().size()>0){
	    	Openactivitymember memberVO = null;
	    	Openactivity activityVO = null;
	    	for(int i=0;i<page.getList().size();i++){
	    		memberVO = (Openactivitymember)page.getList().get(i);
	    		activityVO = openactivityDao.selectByPKSimple(memberVO.getActivityid());
	    		memberVO.setActivityvo(activityVO);
	    	}
	    }
	    return page;
	}
	
	/**
	 * join the activity
	 * @param vo
	 */
	public void saveMember(Openactivitymember vo){
		AssertUtil.assertNotNull(vo, null);
		AssertUtil.assertNotNull(vo.getActivityid(), null);
		AssertUtil.assertNotNull(vo.getUserid(), null);
		Openactivitymember membervo = openactivitymemberDao.selectByPK(vo.getActivityid(), vo.getUserid());
		if(membervo==null){
			vo.setRegistertime(DateUtil.getInstance().getNowtime_GLNZ());
			vo.setJoinstatus(Openactivitymember.JoinStatus_Register);
		    openactivitymemberDao.insert(vo);
		    openactivityDao.updateUserNum(vo.getActivityid(), 1);
		}else {
			openactivitymemberDao.updateByPK(vo);
		}
	}
	
	/**
	 * join the activity
	 * @param vo
	 */
	public void leaveActivity(Long activityid, Long[] userIdArr, boolean sendNotify){
		if(activityid==null || userIdArr==null || userIdArr.length<1){
			return;
		}
		
		openactivitymemberDao.deleteBatch(activityid, userIdArr);
        if(sendNotify){
			for(Long userid : userIdArr){
	        	//TODO sent the notification to user
	        }
        }
		// update number
        openactivityDao.updateUserNum(activityid, -userIdArr.length);
	}
	
	/**
     * static spring getMethod
     */
     public static OpenactivityLogic getInstance(){
    	 OpenactivityLogic logic = (OpenactivityLogic)BeanFactory.getBeanFactory().getBean("openactivityLogic");
         return logic;
     }
	
	public OpenactivityDao getOpenactivityDao() {
		return openactivityDao;
	}

	public void setOpenactivityDao(OpenactivityDao openactivityDao) {
		this.openactivityDao = openactivityDao;
	}

	public OpenactivitymemberDao getOpenactivitymemberDao() {
		return openactivitymemberDao;
	}

	public void setOpenactivitymemberDao(OpenactivitymemberDao openactivitymemberDao) {
		this.openactivitymemberDao = openactivitymemberDao;
	}
	
}
