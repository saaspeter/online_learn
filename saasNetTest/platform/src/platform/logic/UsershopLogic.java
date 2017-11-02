package platform.logic;

import java.util.List;
import java.util.Map;

import platform.vo.Usershop;

public interface UsershopLogic {

    /**
     * 根据用户id查询用户在商店中的名称，如果不在商店中就返回用户的displayname
     * TODO 此函数以后要改造并且要缓存其中的结果
     * @param shopid
     * @param userid
     * @return String  用户的displayname(loginname)
     */
    public String qryUsernameById(Long shopid, Long userid);
	
    /**
     * 根据用户id查询用户在商店中的名称，如果不在商店中就返回用户的displayname
     * @param shopid
     * @param userList
     * @return
     */
    public Map<Long,String> qryUsernameByIdList(Long shopid, List<Long> userList);
	
	/**
	 * 更新商店中的用户信息，暂时只更新基本信息，以后可能更新用户产品信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Usershop update(Usershop vo);
	
	/**
	 * 更新user在shop中的nickname
	 * @param shopid
	 * @param userid
	 * @param nickname
	 * @return 1: 更新成功 -2: nickname已经存在，更新失败
	 */
	public int updateNickname(Long shopid, Long userid, String nickname, String syscode);
	
	/**
	 * 把订单条目中的购买人加入商店，如果商店中已经存在该学员则只用更新该学员的产品数，
	 * 否则直接插入一条记录
	 * @param itemVO
	 * @return
	 */
	public int putUserIntoShop(Long shopid, Long userid, String nickname, Short usershoptype);
	
	/**
	 * 用于商店管理员往商店里添加人员，此时要新增一个人员信息，还要增加人员加入商店的信息
	 * @param usershop
	 * @return
	 * @throws Exception
	 */
	public Long addUserAndIntoShop(Usershop usershop);
	
	/**
	 * 从shopid商店中删除userID用户。这个方法原则上是提供给各个子系统调用，商店本身并不提供删除用户的操作。
	 * 如果用户的“阶段状态”是未设置全局帐号，则在此可以删除用户的基本信息，否则不能删除
	 * 至于各个子系统中的业务数据信息，由各个子系统自己删除。
	 * @param userid
	 * @param shopid
	 * @param syscode
	 * @return
	 */
	public boolean delUserInShop(Long userid,Long shopid);
	
	/**
	 * 从shopid商店中删除多个用户
	 * @param userArr
	 * @param shopid
	 * @param syscode
	 * @return map：result：0失败，1成功；info:成功时返回成功删除的记录数，失败时返回错误编码
	 * @throws Exception
	 */
	public Map delBatchUserInShop(Long[] userArr,Long shopid);
	
	/**
	 * 根据shopid删除用户商店中的数据
	 * @param shopid
	 * @return
	 * @throws Exception
	 */
	public int delUserByShop(Long shopid) ;
	
	/**
	 * 检查用户是否存在,包括用户在某一系统商店中是否存在和用户的全局帐号是否存在
	 * @param loginnamedept
	 * @param shopcode
	 * @param syscode
	 * @return
	 * @throws Exception
	 */
	public Map isExists(String loginnamedept,String shopcode,String syscode) ;
	
	/**
	 * 查询用户加入的所有有效商店记录
	 * @param userid
	 * @param localeid
	 * @return
	 */
	public List<Usershop> qryUserShop(Long userid, Short usershopstatus, Long localeid);
	
	/**
     * 检查用户是否可以访问某个商店，对于可以匿名访问的资源不要调用该方法
     */
    public Usershop checkAccessShop(Long userid, Long shopid, String syscode, Long localeid);
    
    /**
     * 根据shopid和userid查询出对应的用户商店对象，同时包括用户的基本user信息
     * @param shopid
     * @param userid
     * @return
     * @throws Exception
     */
    public Usershop selectByLogicPK(Long shopid,Long userid);
    
}