package netTest.product.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import netTest.bean.BeanFactory;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.UserproductDao;
import netTest.product.dto.UserproductQuery;
import netTest.product.vo.Userproduct;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import commonTool.base.BaseDao;
import commonTool.base.Page;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.util.DateUtil;

public class UserproductDaoImpl extends BaseDao implements UserproductDao {

	static Logger log = Logger.getLogger(UserproductDaoImpl.class.getName());

	public final static String Product_addDel_Userproduct = "product_addDel_userproduct";
	public final static String Product_update_Userproduct = "product_update_userproduct";

	public final static String User_addDel_Userproduct = "user_addDel_userproduct";
	public final static String User_update_Userproduct = "user_update_userproduct";

	/**
     *
     */
	public UserproductDaoImpl() {
		super();
	}

	/**
	 * select some record by PK
	 */
	@Cacheable(value = "netTest.productCache", key = "'UserproductDao.selectByPK~'+#pk", unless = "#result==null")
	public Userproduct selectByPK(Long pk) {
		if (pk == null)
			return null;
		Userproduct record = (Userproduct) this.queryForObject(
				"Userproduct.selectByPK", pk);
		// add cache key into key associate map
		if (record != null) {
			CacheSynchronizer.getInstance().buildAssoc("netTest.productCache",
					"UserproductDao.selectByPK~" + pk,
					new String[] { Userproduct.ObjectType + ":" + pk });
		}

		return record;
	}

	/**
	 * select some record by PK logic
	 */
	@Cacheable(value = "netTest.productCache", key = "'UserproductDao.selectByLogicPk~'+#userid+'~'+#productbaseid", unless = "#result==null")
	public Userproduct selectByLogicPk(Long userid, Long productbaseid) {
		if (userid == null || productbaseid == null)
			return null;
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setUserid(userid);
		queryVO.setProductbaseid(productbaseid);
		Userproduct record = (Userproduct) this.queryForObject(
				"Userproduct.selectByLogicPk", queryVO);
		// add cache key into key associate map
		if (record != null) {
			CacheSynchronizer.getInstance().buildAssoc(
					"netTest.productCache",
					"UserproductDao.selectByLogicPk~" + userid + "~" + productbaseid,
					new String[] { Userproduct.ObjectType + ":"
							+ record.getUserproductid() });
		}
		return record;
	}

	/**
	 * 查询一个产品的学习用户数目
	 */
	public int selectUserCountOfProduct(Long productid) {
		if (productid == null)
			return 0;
		Integer count = (Integer) this.queryForObject(
				"Userproduct.selectUserCountOfProduct", productid);
		if (count == null)
			count = 0;
		return count;
	}

	/**
	 * select records by queryVO
	 */
	public List<Userproduct> selectByVO(UserproductQuery queryVO) {
		if (queryVO == null)
			return new ArrayList();
		List list = this.queryForList("Userproduct.selectByVO", queryVO);
		return list;
	}

	/**
	 * 查询学习某一课程的所有用户的id
	 * 
	 * @param productid
	 * @return
	 */
	public List<Long> selectAllProdUserId(Long productid, Long shopid, Short prodUsetype) {
		if (productid == null)
			return new ArrayList<Long>();
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setProductbaseid(productid);
		queryVO.setShopid(shopid);
		queryVO.setProdusetype(prodUsetype);
		List<Long> list = (List<Long>) this.queryForList(
				"Userproduct.selectAllProdUserId", queryVO);

		return list;
	}

	/**
	 * 查询学习某一课程的admin
	 * 
	 * @param productid
	 * @return
	 */
	@Cacheable(value = "netTest.productCache", key = "'UserproductDao.selectProductAdminUserId~'+#productid", unless = "#result==null")
	public List<Long> selectProductAdminUserId(Long productid) {
		if (productid == null)
			return new ArrayList<Long>();
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setProductbaseid(productid);
		queryVO.setProdusetype(UserproductConstant.ProdUseType_operatorMag);
		List<Long> list = (List<Long>) this.queryForList(
				"Userproduct.selectAllProdUserId", queryVO);
		// add cache key into key associate map
		if (list != null) {
			CacheSynchronizer.getInstance()
					.buildAssoc(
							"netTest.productCache",
							"UserproductDao.selectAllProdUserId~" + productid,
							new String[] { Product_addDel_Userproduct + ":"
									+ productid });
		}
		return list;
	}

	/**
	 * 查询用户学习的产品所在的shopid set
	 * 
	 * @param userid
	 * @return
	 */
	// public Set<Long> selectUserShopidSet(Long userid){
	// List<Long> list =
	// (List<Long>)this.queryForList("Userproduct.selectUserShopidList",
	// userid);
	// Set<Long> set = new HashSet<Long>();
	// if(list!=null&&list.size()>0){
	// for(Long shopid : list){
	// set.add(shopid);
	// }
	// }
	// return set;
	// }

	/**
	 * 把userproduct中使用产品快过期的记录生成usernotification 此函数只处理按日期计算的产品
	 * 
	 * @param currentDate
	 * @param days
	 *            多少天内到期
	 */
	public void notifyDueDateProduct(Date currentDate, int days,
			Short notifytype, String messcode, String content, Long creatorid,
			Short isread, String linkurl, Short openlinktype) {
		if (currentDate == null || days < 0 || notifytype == null
				|| messcode == null)
			return;
		Date enddate = DateUtil.dateAddDays(currentDate, days);
		Map map = new HashMap();
		map.put("notifytype", notifytype);
		map.put("messcode", messcode);
		map.put("content", content);
		map.put("createtime", currentDate);
		map.put("creatorid", creatorid);
		map.put("isread", isread);
		map.put("linkurl", linkurl);
		map.put("enddate", enddate);
		map.put("openlinktype", openlinktype);
		super.insert("Userproduct.notifyDueDateProduct", map);
	}

	/**
	 * 把userproduct中使用产品快过期的记录生成usernotification 此函数只处理按次数计算的产品 只取前500条，为性能考虑
	 * 
	 * @param currentDate
	 * @param times
	 *            还有多少次到期
	 */
	public void notifyDueNumsProduct(Date currentDate, Integer times,
			Short notifytype, String messcode, String content, Long creatorid,
			Short isread, String linkurl, Short openlinktype) {
		if (times < 0 || notifytype == null || messcode == null)
			return;
		Map map = new HashMap();
		map.put("notifytype", notifytype);
		map.put("messcode", messcode);
		map.put("content", content);
		map.put("createtime", currentDate);
		map.put("creatorid", creatorid);
		map.put("isread", isread);
		map.put("linkurl", linkurl);
		map.put("times", times);
		map.put("openlinktype", openlinktype);
		super.insert("Userproduct.notifyDueNumsProduct", map);
	}

	/**
	 * 查询出已经到期的用户产品，最多返回500条 返回的userproduct中包含shopid
	 */
	public List<Userproduct> selectNeedRemoveProd(Date currentDate) {
		if (currentDate == null)
			return new ArrayList<Userproduct>();
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setEnddate(currentDate);
		List<Userproduct> list = this.queryForList(
				"Userproduct.selectNeedRemoveProd", queryVO);
		return list;
	}

	/**
	 * 查询用户目前在使用的产品服务列表。
	 * 
	 * @param userid
	 * @param shopid
	 * @param status
	 *            :有效或失效的产品服务
	 * @return
	 */
	@Cacheable(value = "netTest.productCache", key = "'UserproductDao.selUserProds~'+#userid+'~'+#shopid+'~'+#status", unless = "#result==null")
	public List<Userproduct> selUserProds(Long userid, Long shopid, Short status) {
		if (userid == null)
			return new ArrayList<Userproduct>();
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setUserid(userid);
		if (shopid != null) {
			queryVO.setShopid(shopid);
		}
		if (status != null) {
			queryVO.setStatus(status);
		}
		List<Userproduct> list = (List<Userproduct>) this.queryForList(
				"Userproduct.selUserProds", queryVO);
		// add cache key into key associate map
		if (list != null) {
			CacheSynchronizer.getInstance().buildAssoc(
					"netTest.productCache",
					"UserproductDao.selUserProds~" + userid + "~" + shopid
							+ "~" + status,
					new String[] { User_addDel_Userproduct + ":" + userid,
							User_update_Userproduct + ":" + userid });
		}
		return list;
	}

	/**
	 * 查询用户最近学习的6个产品
	 * 
	 * @return
	 */
	public List<Userproduct> selMostAccessProds(Long userid) {
		if (userid == null)
			return new ArrayList<Userproduct>();
		List<Userproduct> list = (List<Userproduct>) this.queryForList(
				"Userproduct.selMostAccessProds", userid);
		return list;
	}

	/**
	 * cache 见 UserproductLogicImpl.listUserProduct()
	 */
	public Page selectByVOPage(UserproductQuery queryVO, int pageIndex,
			int pageSize, Integer total) {
		if (pageIndex <= 0)
			pageIndex = 1;
		if (pageSize <= 0)
			pageSize = CommonConstant.PAGESIZE;
		String sqlStr = "Userproduct.selectByVO";
		return queryForPage(sqlStr, queryVO, pageIndex, pageSize, total);
	}

	/**
	 * insert a record
	 * 
	 * @return Object with the PK(generated by DB)
	 */
	
	public Long insert(Userproduct record) {
		if (record == null)
			return null;
		Long pk = (Long) super.insert("Userproduct.insert", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Product_addDel_Userproduct, record.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				User_addDel_Userproduct, record.getUserid().toString());
		
		return pk;
	}

	/**
	 * update a record By PK
	 */
	public int updateByPK(Userproduct record) {
		if (record == null || record.getUserproductid() == null)
			return 0;
		int rows = super.update("Userproduct.updateByPK", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Userproduct.ObjectType, record.getUserproductid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Product_update_Userproduct, record.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				User_update_Userproduct, record.getUserid().toString());
				
		return rows;
	}

	/**
	 * 更新产品使用方式和状态
	 * 
	 * @param pk
	 * @param produsetype
	 */
	public int updUsetypeStatusByPK(Long pk, Short produsetype, Short status) {
		if(pk==null||(produsetype==null&&status==null)){
			return 0;
		}
		Userproduct vo = getInstance().selectByPK(pk);
		Userproduct updatevo = new Userproduct();
		updatevo.setUserproductid(pk);
		updatevo.setProdusetype(produsetype);
		updatevo.setStatus(status);
		int rows = super.update("Userproduct.updateByPK", updatevo);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Userproduct.ObjectType, pk.toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Product_update_Userproduct, vo.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				User_update_Userproduct, vo.getUserid().toString());
		return rows;
	}

	/**
	 * 更新用户使用产品的最后日期
	 * 
	 * @param pk
	 * @param produsetype
	 */
	public void updateAccessTime(Long userid, Long productid, Date accessTime) {
		if (userid == null || productid == null)
			return;
		if (accessTime == null) {
			accessTime = DateUtil.getInstance().getNowtime_GLNZ();
		}
		Userproduct record = selectByLogicPk(userid, productid);
		if(record==null){
			return;
		}
		record.setLastaccesstime(accessTime);
		super.update("Userproduct.updateAccessTime", record);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Userproduct.ObjectType, record.getUserproductid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Product_update_Userproduct, record.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				User_update_Userproduct, record.getUserid().toString());
	}

	/**
	 * update the record if exists pk,else insert the record
	 * 
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public Userproduct save(Userproduct record) {
		if (record == null)
			return null;
		if (record.getUserproductid() == null
				|| record.getUserproductid().intValue() == 0) {
			Long pkValue = this.insert(record);
			record.setUserproductid(pkValue);
			return record;
		} else {
			this.updateByPK(record);
			return record;
		}
	}

	/**
	 * delete a record by PK
	 */
	public int deleteByPK(Long pk) {
		if (pk == null)
			return 0;
		Userproduct vo = selectByPK(pk);
		if(vo==null){
			return 0;
		}
		int rows = super.delete("Userproduct.deleteByPK", pk);
		// flush cache
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Userproduct.ObjectType, pk.toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				Product_addDel_Userproduct, vo.getProductbaseid().toString());
		CacheSynchronizer.getInstance().pubFlush("netTest.productCache", 
				User_addDel_Userproduct, vo.getUserid().toString());
		return rows;
	}

	public int deleteByPKArray(List<Userproduct> usrprdList) {
		if (usrprdList == null || usrprdList.size() < 1)
			return 0;
		Long[] pkArr = new Long[usrprdList.size()];
		Userproduct voTemp = null;
		String[] pkStrArr = new String[usrprdList.size()];
		Set<String> prodSet = new HashSet<String>();
		Set<String> userSet = new HashSet<String>();
		for(int i=0; i<usrprdList.size(); i++){
			voTemp = usrprdList.get(i);
			pkArr[i] = voTemp.getUserproductid();
			pkStrArr[i]= voTemp.getUserproductid().toString();
			prodSet.add(voTemp.getProductbaseid().toString());
			userSet.add(voTemp.getUserid().toString());
		}
		int rows = super.deleteBatch("Userproduct.deleteByPK", pkArr);
		
		// flush cache
		CacheSynchronizer.getInstance().pubFlushBatch("netTest.productCache", 
											Userproduct.ObjectType, pkStrArr);
		String[] prodidArr = new String[prodSet.size()];
		prodSet.toArray(prodidArr);
		CacheSynchronizer.getInstance().pubFlushBatch("netTest.productCache", 
										Product_addDel_Userproduct, prodidArr);
		String[] useridArr = new String[userSet.size()];
		userSet.toArray(useridArr);
		CacheSynchronizer.getInstance().pubFlushBatch("netTest.productCache", 
										User_addDel_Userproduct, useridArr);
		
		return rows;
	}

	/**
	 * 查询用户是否已经有了该产品
	 * 
	 * @param productbaseid
	 * @param shopid
	 * @param userid
	 * @return
	 */
	public boolean isExist(Long productbaseid, Long userid) {
		boolean result = false;
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setProductbaseid(productbaseid);
		queryVO.setUserid(userid);
		Object obj = this.getSqlMapClientTemplate().queryForObject(
				"Userproduct.isExist1", queryVO);
		if (obj != null && ((Integer) obj).intValue() == 1) {
			result = true;
		}
		return result;
	}

	/**
	 * static spring getMethod
	 */
	public static UserproductDao getInstance() {
		UserproductDao dao = (UserproductDao) BeanFactory.getBeanFactory()
				.getBean("userproductDaoProxy");
		return dao;
	}

}
