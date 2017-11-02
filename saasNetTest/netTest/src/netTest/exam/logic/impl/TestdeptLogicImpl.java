package netTest.exam.logic.impl;

import java.util.ArrayList;
import java.util.List;

import netTest.bean.BeanFactory;
import netTest.exam.constant.TestdeptConstant;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.dao.TestdeptDao;
import netTest.exam.dao.TestinfoDao;
import netTest.exam.dao.TestuserDao;
import netTest.exam.logic.TestdeptLogic;
import netTest.exam.vo.Testdept;
import netTest.exam.vo.Testinfo;

import commonTool.exception.LogicException;
import commonTool.exception.NeedParamsException;
import commonTool.exception.NoSuchRecordException;
import commonTool.util.StringUtil;

/**
 * 处理考试相关信息
 * @author fan
 *
 */
public class TestdeptLogicImpl implements TestdeptLogic {

	private TestinfoDao testinfoDao;
	private TestuserDao testuserDao;
	private TestdeptDao testdeptDao;
	

	/**
	 * 批量增加考试单位，会过滤掉已经有的考试单位
	 * @param testid
	 * @param shopid
	 * @param createorgid 考试创建者单位
	 * @param deptIdStr
	 * @return
	 */
//	public int addTestdept(Long testid,Long shopid,Long createorgid,String deptIdStr){
//		if(testid==null||shopid==null||createorgid==null)
//			throw new NeedParamsException("-- need parameters in TestdeptLogicImpl.addTestdept");
//		int nums = 0;
//		if(deptIdStr==null||deptIdStr.trim().length()<0)
//			return nums;
//		String[] tempArr1 = StringUtil.splitString(deptIdStr, ",");
//		String existdeptStr = testdeptDao.qryJoinTestdepts(testid, deptIdStr, shopid);
//		
//		List list = new ArrayList();
//		Testdept vo = null;
//		for(int i=0;i<tempArr1.length;i++){ 
//			if(!StringUtil.includeStr(existdeptStr, tempArr1[i].trim(), null)){
//				vo = new Testdept();
//				vo.setTestid(testid);
//				vo.setShopid(shopid);
//				vo.setSelfendexamnum(0);
//				vo.setSelfexamernum(0);
//				vo.setSelfexamingnum(0);
//				vo.setOrgbaseid(new Long(tempArr1[i].trim()));
//				vo.setStatus(TestdeptConstant.Status_normal);
//	            list.add(vo);
//			}
//		}
//		// 如果新增的考试单位中包括了考试组织的单位，并且查询出来的已经加入考试的单位中有考试组织单位，
//		// 则在此需要更新考试组织单位的状态为可见正常。
//		if(StringUtil.includeStr(deptIdStr, String.valueOf(createorgid), null)
//			&&StringUtil.includeStr(existdeptStr, String.valueOf(createorgid), null)){
//			Testdept votemp = new Testdept();
//			votemp.setShopid(shopid);
//			votemp.setTestid(testid);
//			votemp.setOrgbaseid(createorgid);
//			votemp.setStatus(TestdeptConstant.Status_normal);
//			testdeptDao.updateByLogicPK(votemp);
//		}
//		nums = testdeptDao.insertBatch(list);
//		return nums;
//	}
	
	/**
	 * 删除一个考试单位，同时删除该单位下的考试人员
	 * @param testid
	 * @param shopid
	 * @param testdeptid
	 * @param orgbaseid
	 * @return
	 */
//	public int removeTestdept(Long testid,Long shopid,Long testdeptid,Long orgbaseid){
//		if(testid==null||shopid==null||testdeptid==null||orgbaseid==null)
//			return 0;
//		Testinfo testinfoVO = testinfoDao.selectByPK(testid);
//		if(testinfoVO==null)
//			throw new NoSuchRecordException("-- test info doesn't exists");
//		if(!TestinfoConstant.Teststatus_unStart.equals(testinfoVO.getTeststatus()))
//			throw new LogicException("-- cannot remove the testdept when test started");
//		testuserDao.deleteByOrg(shopid, testid, orgbaseid);
//		int num = testdeptDao.deleteByPK(testdeptid);
//		return num;
//	}
	
	/**
	 * 返回单位的人员考试人数数据。Selfexamernum，Selfendexamnum，Selfexamingnum，Selfnotexamnum
	 * @param testdeptidStr
	 * @return
	 */
	public String[] qrytestusernum(String testdeptidStr){
		if(testdeptidStr!=null&&testdeptidStr.trim().length()>0){
		    String[] rtnArr = null;
			Testdept vo = testdeptDao.selectByPK(new Long(testdeptidStr.trim()));
			if(vo!=null){
				rtnArr = new String[4];
				rtnArr[0] = vo.getSelfexamernum().toString();
				rtnArr[1] = vo.getSelfendexamnum().toString();
				rtnArr[2] = vo.getSelfexamingnum().toString();
				rtnArr[3] = vo.getSelfnotexamnum().toString();
			}
			return rtnArr;
		}else{
			return null;
		}
	}
	
	
	/**
	 * static spring getMethod
	 */
	 public static TestdeptLogic getInstance(){
		 TestdeptLogic logic = (TestdeptLogic)BeanFactory.getBeanFactory().getBean("testdeptLogic");
	     return logic;
	 }

	public TestdeptDao getTestdeptDao() {
		return testdeptDao;
	}


	public void setTestdeptDao(TestdeptDao testdeptDao) {
		this.testdeptDao = testdeptDao;
	}

	public TestuserDao getTestuserDao() {
		return testuserDao;
	}


	public void setTestuserDao(TestuserDao testuserDao) {
		this.testuserDao = testuserDao;
	}

	public TestinfoDao getTestinfoDao() {
		return testinfoDao;
	}

	public void setTestinfoDao(TestinfoDao testinfoDao) {
		this.testinfoDao = testinfoDao;
	}

}
