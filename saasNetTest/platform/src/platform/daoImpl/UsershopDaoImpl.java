package platform.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.dao.UsershopDao;
import platform.dto.UsershopQuery;
import platform.vo.Usershop;
import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.util.StringUtil;

public class UsershopDaoImpl extends BaseDao implements UsershopDao {

	static Logger log = Logger.getLogger(UsershopDaoImpl.class.getName());
	
	public final static String Nickname_update_Usershop = "nickname_update_usershop";
	
	public final static String User_update_Usershop = "user_update_usershop";
	
    /**
     *
     */
    public UsershopDaoImpl() {
        super();
    }
    
    /**
     * select some record by PK
     */
    @Cacheable(value = "platform.shopCache", key = "'UsershopDao.selectByPK~'+#pk", unless = "#result==null")
	public Usershop selectByPK(Long pk) {
    	if(pk==null)
    		return null;
    	Usershop record = (Usershop) this.queryForObject("Usershop.selectByPK", pk);
    	// add cache key into key associate map
		if (record != null) {
			CacheSynchronizer.getInstance().buildAssoc("platform.shopCache",
					"UsershopDao.selectByPK~" + pk,
					new String[] { Usershop.ObjectType + ":" + pk });
		}
		return record;
    }
    
    private String getLogicCacheKey(Long shopid, Long userid){
    	return "UsershopDao.selectByLogicPK~"+shopid+"~"+userid;
    }
    
    private void buildAssocForLogicKey(Long usershopid, Long shopid, Long userid){
    	CacheSynchronizer.getInstance().buildAssoc("platform.shopCache",
				getLogicCacheKey(shopid, userid),
				new String[] { Usershop.ObjectType + ":" + usershopid });
    }
    
    /**
     * 根据shopid和userid查询出对应的用户商店对象
     * @param shopid
     * @param userid
     * @return
     * @throws Exception
     */
    @Cacheable(value = "platform.shopCache", key = "'UsershopDao.selectByLogicPK~'+#shopid+'~'+#userid", unless = "#result==null")
	public Usershop selectByLogicPK(Long shopid, Long userid) {
    	if(shopid==null||userid==null)
    		return null;
    	UsershopQuery queryVO = new UsershopQuery();
    	queryVO.setShopid(shopid);
    	queryVO.setUserid(userid);
		Usershop retVO = (Usershop)this.queryForObject("Usershop.selectByLogicPK", queryVO);
		// add cache key into key associate map
		if (retVO != null) {
			buildAssocForLogicKey(retVO.getUsershopid(), shopid, userid);
		}
		return retVO;
    }
    
    /**
     * 检查商店中是否存在该nickname
     * @param nickname
     * @param shopid
     * @return true: 存在， false: 不存在
     */
    @Cacheable(value = "platform.shopCache", key = "'UsershopDao.existsNicknameInShop~'+#nickname+'~'+#shopid+'~'+#syscode")
	public boolean existsNicknameInShop(String nickname, Long shopid, String syscode){
    	if(nickname==null||nickname.trim().length()<1)
    		return false;
    	boolean ret = false;
    	UsershopQuery queryVO = new UsershopQuery();
    	queryVO.setShopid(shopid);
    	queryVO.setNickname(nickname);
//    	queryVO.setSyscode(syscode);
    	Integer num = (Integer)this.queryForObject("Usershop.existNickname", queryVO);
    	if(num!=null && num>0)
    		ret = true;
    	else
    		ret = false;
    	// add cache key into key associate map
		CacheSynchronizer.getInstance().buildAssoc("platform.shopCache",
					"UsershopDao.existsNicknameInShop~"+nickname+"~"+shopid+"~"+syscode,
					new String[] { Nickname_update_Usershop + ":" + nickname });
		
    	return ret;
    }
        
    /**
     * select records by queryVO
     */
    public List selectByVO(UsershopQuery queryVO) {
		if(queryVO==null)
			return new ArrayList();
		List list = this.queryForList("Usershop.selectByVO", queryVO);
		return list;
    }
    
    /**
     * 查询用户加入的商店的信息
     */
    @Cacheable(value = "platform.shopCache", key = "'UsershopDao.qryUserShop~'+#userid+'~'+#usershopstatus")
	public List<Usershop> qryUserShop(Long userid, Short usershopstatus) {
		if(userid==null)
			return new ArrayList<Usershop>();
		UsershopQuery queryVO = new UsershopQuery();
		queryVO.setUserid(userid);
//		queryVO.setSyscode(syscode);
		queryVO.setUsershopstatus(usershopstatus);
		List<Usershop> list = (List<Usershop>)this.queryForList("Usershop.selectByVO", queryVO);
		// add cache key into key associate map
		CacheSynchronizer.getInstance().buildAssoc("platform.shopCache",
					"UsershopDao.qryUserShop~"+userid+"~"+usershopstatus,
					new String[] { User_update_Usershop + ":" + userid });
		return list;
    }
    
    /**
     *  根据用户的id查询用户
     */
    public List<Usershop> selectByUserId(Long shopid, List<Long> userList) {
		if(shopid==null||userList==null||userList.size()==0)
			return new ArrayList<Usershop>();
		List<Usershop> retList = new ArrayList<Usershop>();
		StringBuffer buffer = new StringBuffer();
		CacheSynchronizer cacheInstance = CacheSynchronizer.getInstance();
		Object usershopObj = null;
		// 先看userList中的user如果已经在cache中，则直接从cache中获得，对于不在其中的，用一次批量查询得到
    	for(Long userId : userList){
    		usershopObj = cacheInstance.getObject("platform.shopCache", getLogicCacheKey(shopid, userId));
    		if(usershopObj!=null){
    			retList.add((Usershop)usershopObj);
    		}else {
    			buffer.append(userId).append(",");
    		}
    	}
    	String useridStr = buffer.toString();
    	useridStr = StringUtil.trimComma(useridStr);
    	if(!"".equals(useridStr)){
    		UsershopQuery queryVO = new UsershopQuery();
    		queryVO.setShopid(shopid);
    		queryVO.setUseridStr(useridStr);
    		List<Usershop> searchlist = (List<Usershop>)this.queryForList("Usershop.selectByVO", queryVO);
    		if(searchlist!=null && searchlist.size()>0){
    			for(Usershop temvo : searchlist){
    				// put cache
    				cacheInstance.putObject("platform.shopCache", 
    						getLogicCacheKey(shopid, temvo.getUserid()), temvo);
    				// build assoic
    				buildAssocForLogicKey(temvo.getUsershopid(), shopid, temvo.getUserid());
    			}
    			retList.addAll(searchlist);
    		}
    	}
    	
		return retList;
    }
    
    /**
     * select page by queryVO 
     * @param queryVO:the query vo,if queryVO is null,then search all
     * @param pageIndex:the current page num,start from 1;
     * @param pageSize:the page size,if less equal than 0,the default PlatfromConstant.PAGESIZE will be used;
     * @return Page
     * @throws Exception
     */
    public Page selectByVOPage(UsershopQuery queryVO,int pageIndex,int pageSize,Integer total) {
    	if(queryVO==null)
    		return Page.EMPTY_PAGE;
        if(pageIndex<=0)
        	pageIndex = 1;
        if(pageSize<=0)
        	pageSize = CommonConstant.PAGESIZE;
        String sqlStr = "Usershop.selectByVO";
        return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
    }
           
    /**
     * insert a record
     * @return Object with the PK(generated by DB)
     */
    public Long insert(Usershop record) {
    	if(record==null)
    		return null;
		Long pk = (Long)super.insert("Usershop.insert", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				Nickname_update_Usershop, record.getNickname());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				User_update_Usershop, record.getUserid().toString());
		return pk;
    }

    /**
     * update a record By PK
     */
    public int updateByPK(Usershop record) {
    	if(record==null||record.getUsershopid()==null)
    		return 0;
		int rows = super.update("Usershop.updateByPK", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				Usershop.ObjectType, record.getUsershopid().toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				Nickname_update_Usershop, record.getNickname());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				User_update_Usershop, record.getUserid().toString());
		return rows;
    }
    
    /**
     * 更新用户在所有shop中的loginame，当用户的loginname更改后要更新所有的usershop
     */
    public int updateUserLoginame(Long userid, String newloginame) {
    	if(userid==null||newloginame==null||newloginame.trim().length()<1)
    		return 0;
    	int rows = 0;
    	List<Usershop> list = getInstance().qryUserShop(userid, null);
    	if(list!=null && list.size()>0){
        	Usershop vo = new Usershop();
        	vo.setUserid(userid);
        	vo.setLoginname(newloginame);
    		rows = super.update("Usershop.updateUserLoginame", vo);
    		// flush cache
    		for(Usershop votemp : list){
    			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
        				Usershop.ObjectType, votemp.getUsershopid().toString());
    		}
    		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
					    User_update_Usershop, userid.toString());
    	}
		
		return rows;
    }
    
    /**
     * update a record By Logic PK
     */
    public int updateByLogicPK(Usershop record) {
    	if(record==null||record.getUserid()==null||record.getShopid()==null)
    		return 0;
		int rows = super.update("Usershop.updateByLogicPK", record);
		// flush cache
		record = selectByLogicPK(record.getShopid(), record.getUserid());
		if(record!=null){
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
					Usershop.ObjectType, record.getUsershopid().toString());
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
					Nickname_update_Usershop, record.getNickname());
			CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
					User_update_Usershop, record.getUserid().toString());
		}
		return rows;
    }

    /**
     * delete a record by PK
     */
    public int deleteByPK(Long pk) {
    	if(pk==null)
    		return 0;
    	Usershop vo = getInstance().selectByPK(pk);
    	if(vo==null){
    		return 0;
    	}
		int rows = super.delete("Usershop.deleteByPK", pk);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				Usershop.ObjectType, pk.toString());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				Nickname_update_Usershop, vo.getNickname());
		CacheSynchronizer.getInstance().pubFlush("platform.shopCache", 
				User_update_Usershop, vo.getUserid().toString());
		return rows;
    }
    
    /**
     * delete records by userid, shopid, usershoptype
     * usershoptype: 见UsershopConstant
     */
    public int deleteUserInShop(Long userid, Long shopid, Short usershoptype) {
    	if(userid==null||shopid==null)
    		return 0;
    	UsershopQuery queryVO = new UsershopQuery();
    	queryVO.setUserid(userid);
    	queryVO.setShopid(shopid);
    	queryVO.setUsershoptype(usershoptype);
    	int rows = 0;
    	Usershop vo = selectByLogicPK(shopid, userid);
    	if(vo!=null && 
    		((usershoptype==null)||(usershoptype.equals(vo.getUsershoptype()))) )
    	{
    		rows = deleteByPK(vo.getUsershopid());
    	}

		return rows;
    }
    
    /**
     * 根据shopID删除商店人员记录
     * @param shopid
     * @return
     * @throws Exception
     */
    // 暂时不刷新usershop cache
    public int deleteByShop(Long shopid){
    	if(shopid==null)
    		return 0;
		int rows = super.delete("Usershop.deleteByShop", shopid);
		return rows;
    }
    
	/**
     * insertBatch records of List
     */
//    public int insertBatch(List list) {
//    	if(list==null||list.size()<=0)
//    		return 0;
//    	int rows = 0;
//    	rows = super.insertBatch("Usershop.insert", list);
//    	return rows;
//    }
    
    /**
     * deleteBatch records by the Long Array of PK
     */
//    public int deleteBatchByPK(Long[] pkArray){
//    	if(pkArray==null||pkArray.length<=0)
//    		return 0;
//    	int rows = 0;
//    	rows = super.deleteBatch("Usershop.deleteByPK", pkArray);
//    	return rows;
//    }
//    
//    /**
//     * deleteBatch records by the String Array of PK
//     */
//    public int deleteBatchByPK(String[] pkArray){
//    	if(pkArray==null||pkArray.length<=0)
//    		return 0;
//    	int rows = 0;
//    	Long[] arrs = new Long[pkArray.length];
//		for(int i=0;i<pkArray.length;i++){
//			if(pkArray[i]!=null)
//				arrs[i] = new Long(Long.parseLong(pkArray[i]));
//		}
//		rows = this.deleteBatchByPK(arrs);
//		return rows;	
//    }
    
    /**
     * static spring getMethod
     */
     public static UsershopDao getInstance() {
         UsershopDao dao = (UsershopDao)BeanFactory.getBeanFactory().getBean("usershopDaoProxy");
         return dao;
     }
    
}
