package netTest.orguser.logic;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import netTest.orguser.dto.OrguserQuery;
import netTest.orguser.vo.Orguser;
import commonTool.base.Page;

public interface OrguserLogic {

	/**
	 * 根据userid和shopid得到orguser,如果user在单位中，则其中org信息不为空
	 * @param userid
	 * @param shopid
	 * @return
	 */
	public Orguser qryOrguserByUsershop(Long userid, Long shopid);
	
	/**
	 * 查询机构的人员列表分页信息，用于点击查询按钮时查询
	 * @param orgid：机构id
	 * @param includeChild:是否包含下级单位，1为不包括下级，2为包括下级
	 * @param order_By_Clause：排序规则，必须以order开头
	 * @param pageNO
	 * @param pageSize
	 * @return Viewpersonin的分页信息
	 * @throws Exception
	 */
	public Page qryOrguserPage(OrguserQuery queryVO,int pageNO,int pageSize,Integer total);
		
	/**
	 * 更新单位用户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Orguser updateUserDept(Orguser vo);
	
	/**
	 * 新增或更新单位用户信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Orguser saveUserDept(Orguser vo);
		
	/**
	 * 把用户加入到该商店的默认单位中，如果该商店没有设置默认商店则返回失败
	 * @param shopid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public boolean putUserIntoOrg(Long shopid,Long userid, Long orgid);
	
	/**
	 * 新增单位人员信息，
	 * 需要在平台系统中新增用户信息，在商店人员中添加该人员信息，如果有人员使用产品的信息，需要添加用户产品表
	 * 把人员插入到orgbaseid单位中
	 * @param orgbaseid
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Orguser addOrguserDept(Orguser orguser);
	
	/**
	 * 根据人员ID数组(userID)，删除所有人员信息和人员所在的单位信息，
	 * 同时人员所在的小组人员信息也被删除。
	 * 如果人员已经设置了全局帐号信息，则不能把该人的信息(在平台系统中)删除，
	 * 因此需要先检查人员是否已经设置了全局帐号信息.
	 * 如果符合删除条件则其过程应该是，先把用户从单位小组中去除，
	 * 然后把人员在用户产品表中的数据删除，然后删除用户商店表中的数据，然后从平台人员信息中删除
	 * @param keys: userID的数组
	 * @return
	 */
	public int delUserDept(Long userid, Long shopid);
	
	
	/**
	 * 人员从一个单位调到另一个单位,//TODO 检查人员是否有业务上的限制，例如人员正在考试，此时应该不允许转单位
	 * @param userid
	 * @param oldDeptid
	 * @param newDeptid
	 * @return
	 */
	public int transferDept(Long userid,Long oldDeptid,Long newDeptid);
	
    /**
     * 查询user对应的org信息，一个用户只能属于一个org
     */
    public Orguser selectOrgForUser(Long userid, Long shopid);
    
    public List<String> importOrguserFromExcel(InputStream input,Long shopid, 
			String shopcode, Orguser vo, Locale locale);
			
}