package netTest.exam.logic;

import java.util.Date;

import netTest.exam.vo.Testinfo;
import netTest.exam.vo.Testuser;


public interface TestuserLogic {

	/**
	 * 批量增加考试人员，会过滤掉已经有的考试人员
	 * @param shopid
	 * @param testid
	 * @param orgbaseid
	 * @param userIdstr
	 * @return
	 */
	public int addTestuser(Long shopid,Long testid,String userIdstr);
	
	/**
	 * 添加学习考试课程的所有学员到本次考试中
	 * @param shopid
	 * @param testid
	 * @param productid
	 * @return
	 */
	public int addAllProductUserIntoTest(Long shopid,Long testid);
	
	/**
	 * 删除一个考试单位，同时删除该单位下的考试人员
	 * @param testid
	 * @param shopid
	 * @param testdeptid
	 * @param orgbaseid
	 * @return
	 */
	public int removeTestuser(Long testid,Long shopid,String[] keys,Long testdeptid);
	
	/**
	 * 新增考试时判断用户输入的考试时间是否和本单位已有的考试有时间冲突
	 * @param shopidStr
	 * @param useridStr
	 * @return
	 */
    public String qryTestCollideUser(String shopidStr,String useridStr);
    
    
    /**
     * 用户加入open Test
     * @param usranwMap
     * @return
     */
    public Testuser autoJoinTest(Testinfo testVO,Long userid);
    
    /**
     * 初始化用户的答案，为用户生成试题，插入用户答案表
     * @param usranwMap
     * @return
     */
    public boolean initUseranswer(Long testid,Long paperid,Long userid,Long shopid,int needRandom);
    
    /**
     * 得到考生所剩答卷时间,单位是秒
     * @param papertime
     * @param testenddate
     * @return
     */
    public long getTestLefttime(long lefttime,Date testenddate);
    
    /**
     * 更新一场考试的考生得分和是否及格。这是在评阅完考生的答案后执行的。
     * @param testid
     * @param paperid
     * @param queschecktype
     * @param paperquascore: 试卷及格分，如果不为空则检查并更新考生是否及格
     * @return
     */
    public int sumTestuserScore(Long testid,Long paperid,Short queschecktype,Float paperquascore);
    
    /**
     * 供job使用，删除无用的useranswer数据
     */
    public void cleanUseranswerJob();
    
    /**
     * 为新加入课程的用户增加正在进行的考试
     * @param productid
     * @param userid
     * @return
     */
    public int autoJoinProductUpcomingTest(Long productid, Long userid);

}