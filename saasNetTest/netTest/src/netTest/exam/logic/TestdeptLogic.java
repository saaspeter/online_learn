package netTest.exam.logic;


public interface TestdeptLogic {

	/**
	 * 批量增加考试单位，会过滤掉已经有的考试单位
	 * @param testid
	 * @param shopid
	 * @param deptIdStr
	 * @param deptNameStr
	 * @return
	 */
//	public int addTestdept(Long testid,Long shopid,Long createorgid,String deptIdStr);
	
	/**
	 * 删除一个考试单位，同时删除该单位下的考试人员
	 * @param testid
	 * @param shopid
	 * @param testdeptid
	 * @param orgbaseid
	 * @return
	 */
//	public int removeTestdept(Long testid,Long shopid,Long testdeptid,Long orgbaseid);
	
	/**
	 * 返回单位的人员考试人数数据。Selfexamernum，Selfendexamnum，Selfexamingnum，Selfnotexamnum
	 * @param testdeptidStr
	 * @return
	 */
	public String[] qrytestusernum(String testdeptidStr);

}