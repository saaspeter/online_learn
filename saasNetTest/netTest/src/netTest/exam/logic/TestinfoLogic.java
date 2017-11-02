package netTest.exam.logic;

import java.util.Date;

import org.springframework.security.GrantedAuthority;

import netTest.exam.dto.TestinfoQuery;
import netTest.exam.vo.Testinfo;

import commonTool.base.Page;

public interface TestinfoLogic {

	/**
	 * 查询要列出来的考试信息，本函数为操作员查看的。学员能看到的考试信息不在此方法
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	// sql的逻辑：当magtesttype=1时，查询操作者单位创建的考试和参与的考试，因为所有创建考试的单位在testDept表中都存在
	// 当magtesttype=2时，查询操作者单位的下级单位创建的考试和参与的考试
	public Page listTestForOper(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total);

	/**
	 * 查询在主页上要显示的开放考试
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	//public Page listOpenTest(TestinfoQuery queryVO,int pageIndex,int pageSize,Integer total);
	
    /**
     * 保存考试信息
     * @param record
     * @return
     */
    public Testinfo save(Testinfo record);
    
    /**
     * 删除考试信息
     * @param testid
     * @param vo
     * @param shopid
     * @param userorgid
     * @return
     */
    public int deleteTest(Long testid, Testinfo vo, Long shopid,Long userorgid);
    
    /**
     * 根据productid删除考试信息, 一般要用异步线程的job来处理
     * @param productid
     * @param shopid
     * @return
     */
    public void deleteTestByProd(Long productid, Long shopid);
    
	/**
	 * 新增考试时判断用户输入的考试时间是否和本单位已有的考试有时间冲突
	 * @param shopidStr
	 * @param orgbaseidStr
	 * @param teststartdateStr
	 * @param testenddateStr
	 * @return
	 */
	public String qryTestCollideAddTest(String shopidStr,String orgbaseidStr,String teststartdateStr,String testenddateStr,String testidStr);
	
//    /**
//     * 在考试信息列表页面显示某个单位是否有时间冲突的考试
//     * @param shopidStr
//     * @param orgbaseidStr
//     * @return
//     */
//    public String qryTestCollideForDept(String shopidStr,String orgbaseidStr);
    
    /**
     * 查找单场考试信息
     * @param testid
     * @return
     */
    public Testinfo qryTestInfo(Long testid);
    
    /**
     * 改变考试的状态
     * @param testid
     * @param userorgid
     * @return
     */
    public Short changeStatus(Long testid,Long userorgid,Long shopid);
    
    /**
     * 更改考试状态:开始考试。
     * @param testid
     * @param paperid
     * @return
     */
    public Short statusStartTest(Long testid,Long paperid);
    
    /**
     * 更改考试状态:结束考试
     * @param testid
     * @return
     */
    public Short statusEndTest(Long testid, Date curDate);
    
    /**
     * 查询考试信息，该方法的结果将被缓存
     * @param testid
     * @return
     */
    public Testinfo getTestinfo(Long testid);
    
    /**
     * 根据产品id查询考试数量
     * @param productid
     * @param shopid
     * @param teststatus
     * @return
     */
    public int countTestinfoNumber(Long productid, Long shopid, Short teststatus);
    
    /**
     * 加载用户在test中的权限，并且加载test所属的product中的权限
     */
    public GrantedAuthority[] loadContainerAuthority(Long userid, Long testid, Long userShopid);
	
}