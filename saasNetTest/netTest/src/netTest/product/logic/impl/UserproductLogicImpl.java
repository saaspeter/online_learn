package netTest.product.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.event.EventHandlerNetTest;
import netTest.exception.ExceptionConstant;
import netTest.order.vo.Orderproduct;
import netTest.product.constant.UserProdBuyLogConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.ProductbaseDao;
import netTest.product.dao.UserprodbuylogDao;
import netTest.product.dao.UserprodstatuslogDao;
import netTest.product.dao.UserproductDao;
import netTest.product.dao.impl.ProductbaseDaoImpl;
import netTest.product.dao.impl.UserproductDaoImpl;
import netTest.product.dto.UserproductQuery;
import netTest.product.logic.ProductLogic;
import netTest.product.logic.UserproductLogic;
import netTest.product.vo.Learnactivity;
import netTest.product.vo.Productbase;
import netTest.product.vo.Userprodstatuslog;
import netTest.product.vo.Userproduct;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.concurrent.ParallelExecutor;
import platform.constant.UsershopConstant;
import platform.dao.UserDao;
import platform.dao.UsershopDao;
import platform.daoImpl.UserDaoImpl;
import platform.logic.UsershopLogic;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.User;

import commonTool.base.Page;
import commonTool.biz.dao.UsernotificationDao;
import commonTool.biz.vo.Usernotification;
import commonTool.cache.CacheSynchronizer;
import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.concurrent.AbstractTaskRunnable;
import commonTool.constant.CommonConstant;
import commonTool.constant.PayTypeConstant;
import commonTool.constant.UsernotificationConstant;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.exception.LogicException;
import commonTool.util.AssertUtil;
import commonTool.util.DateUtil;
import commonWeb.security.constant.RolesConstant;
import commonWeb.security.constant.UserRolesConstant;
import commonWeb.security.dao.RolesDao;
import commonWeb.security.dao.UserRoleDao;
import commonWeb.security.dao.impl.RolesDaoImpl;
import commonWeb.security.dao.impl.UserRoleDaoImpl;
import commonWeb.security.vo.UserRole;


public class UserproductLogicImpl implements UserproductLogic,EventHandle{

	static Logger log = Logger.getLogger(UserproductLogicImpl.class.getName());
	
	private UserproductDao usrproDao;
	private UserprodbuylogDao buylogDao;
	private UserprodstatuslogDao stalogDao;
	private UsernotificationDao usernotificationDao;
	private ProductbaseDao productdao;
	
	/**
	 * 列出userproduct记录，包括user的信息
	 * @param queryVO
	 * @param pageIndex
	 * @param pageSize: 如果为-1表示只查询list而不返回page
	 * @return
	 */
	@Cacheable(value = "netTest.productCache", key = "'UserproductLogic.listUserProduct~'+#productid+'~'+#userid+'~'+#produsetype+'~'+#shopid+'~'+#status+'~'+#pageIndex+'~'+#pageSize",
				unless = "#result==null")
	public Page listUserProduct(Long productid, Long userid, Short produsetype, 
			                    Long shopid, Short status, String orderByStr,
								int pageIndex,int pageSize,Integer total){
		if(productid==null && userid==null){
			return Page.EMPTY_PAGE;
		}
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setProductbaseid(productid);
		queryVO.setUserid(userid);
		queryVO.setProdusetype(produsetype);
		queryVO.setShopid(shopid);
		queryVO.setStatus(status);
		
		// 当不指定user查询时，需要按照user排序
		User searchuserVO = null;
		if(queryVO.getUserid()==null){
			queryVO.setOrder_By_Clause("userID");
		}else {
			searchuserVO = getUserDao().selectByPK(userid);
		}
		if(orderByStr!=null&&orderByStr.trim().length()>0){
			queryVO.setOrder_By_Clause(orderByStr.trim());
		}
		
		Page page = null;
		if(pageSize==-1){
			List retList = usrproDao.selectByVO(queryVO);
			page = Page.EMPTY_PAGE;
			page.setList(retList);
		}else {
			page = usrproDao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		}
		
		if(page!=null && page.getList()!=null){
			Userproduct vo = null;
			Productbase prodVO = null;
			List<Long> useridList = new ArrayList<Long>();
			ProductLogic prodLogic = BOFactory.getProductLogic();
			for(int i=0;i<page.getList().size();i++){
				vo = (Userproduct)page.getList().get(i);
				// 如果userid不为空说明是查询某个用户的
				if(queryVO.getUserid()!=null){
					vo.setDisplayname(searchuserVO.showDisplayLoginame());
				}else if(!useridList.contains(vo.getUserid())){
					useridList.add(vo.getUserid());
				}
				if(queryVO.getProductname()==null || queryVO.getProductname().trim().length()<1){
				   // 设置产品名称,需要充分利用product的cache
				   prodVO = prodLogic.selectVO(vo.getProductbaseid(), null);
				   vo.setProductname(prodVO.getProductname());
				}
			}
			if(useridList.size()>0){
				UsershopLogic usershoplogic = BOFactory_Platform.getUsershopLogic();
				Map<Long,String> nameMap = usershoplogic.qryUsernameByIdList(queryVO.getShopid(), useridList);
				for(int i=0;i<page.getList().size();i++){
					vo = (Userproduct)page.getList().get(i);
					vo.setDisplayname(nameMap.get(vo.getUserid()));
				}
			}
			
			// add cache key into key assoc map
			CacheSynchronizer.getInstance().buildAssoc("netTest.productCache", 
			    				"UserproductLogic.listUserProduct~"+productid+'~'+userid+'~'
			    				+produsetype+'~'+shopid+'~'+status+'~'+pageIndex+'~'+pageSize, 
			    				new String[]{UserproductDaoImpl.Product_addDel_Userproduct+":"+productid, 
											 UserproductDaoImpl.Product_update_Userproduct+":"+productid,
											 UserproductDaoImpl.User_addDel_Userproduct+":"+userid,
											 UserproductDaoImpl.User_update_Userproduct+":"+userid,
			    							 });
			
		}
		return page;
	}
	
	/**
	 * list user's current used product
	 * @param userid
	 * @param shopid
	 * @param status
	 * @return
	 */
	public Page listMyProduct(Long userid, Long shopid, Short status,
							  int pageIndex,int pageSize,Integer total)
	{
		if(userid==null){
			return null;
		}
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setUserid(userid);
		queryVO.setProdusetype(UserproductConstant.ProdUseType_userNormal);
		queryVO.setShopid(shopid);
		queryVO.setStatus(status);
		queryVO.setOrder_By_Clause("lastAccessTime desc");
		
		// 当不指定user查询时，需要按照user排序
		User searchuserVO = getUserDao().selectByPK(userid);
		Page page = usrproDao.selectByVOPage(queryVO, pageIndex, pageSize, total);
		List<Userproduct> retList = null;
		if(page==null){
			page = Page.EMPTY_PAGE;
		}else {
			retList = (List<Userproduct>)page.getList();
		}
		
		if(retList!=null && retList.size()>0){
			Productbase prodVO = null;
			ProductLogic prodLogic = BOFactory.getProductLogic();
			for(Userproduct vo : retList){
				// set user displayname
				vo.setDisplayname(searchuserVO.showDisplayLoginame());
				
				if(queryVO.getProductname()==null || queryVO.getProductname().trim().length()<1){
				   // 设置产品名称,需要充分利用product的cache
				   prodVO = prodLogic.selectVO(vo.getProductbaseid(), null);
				   vo.setProductname(prodVO.getProductname());
				}
			}			
		}
		return page;
	}
		
	/**
	 * 根据订单条目处理用户购买培训类产品的信息, 为用户建立用户产品记录并且记录相关日志
     * 把用户加入商店；把用户加入该商店的单位。
     * 当用户支付订单条目中不需要审批的培训类产品时调用该方法；当商店管理员审批通过用户购买培训类产品订单条目时调用该方法；
	 * @param itemVO
	 * @return 新生成的用户产品记录，如果为null，说明新增失败
	 */
	public Userproduct deliverProdToUsr(Orderproduct orderprodVO, Long opertorid)throws Exception{
		if(orderprodVO==null||orderprodVO.getProductbaseid()==null
				||orderprodVO.getShopid()==null
				||orderprodVO.getUserid()==null)
			return null;
		Userproduct vo = new Userproduct();
		// 插入用户产品信息
		vo.setIspay(UserproductConstant.IsPay_yes);
		vo.setCost(orderprodVO.getSpendcost());
		vo.setProductbaseid(orderprodVO.getProductbaseid());
		vo.setProductname(orderprodVO.getProductname());
		vo.setShopid(orderprodVO.getShopid());
		vo.setGenetype(UserproductConstant.GeneType_fromOrder);
		vo.setProdusetype(UserproductConstant.ProdUseType_userNormal);
		// 处理不同支付方式的使用日期和次数限制
		this.setUseTimeNum(vo,orderprodVO.getPaytype(), orderprodVO.getUselimitnum());
		vo.setStatus(UserproductConstant.Status_active);
        // 根据订单条目中的购买产品类型设置使用状态级别
		if(PayTypeConstant.PayType_free.equals(orderprodVO.getPaytype())){
		   vo.setStatusrank(UserproductConstant.StatusRank_Use_free);
		} else{
		   vo.setStatusrank(UserproductConstant.StatusRank_Use_normal);
		}
		vo.setUserid(orderprodVO.getUserid());
		
		vo.setStartdate(DateUtil.getInstance().getNowtime_GLNZ());
		
		Long pk = usrproDao.insert(vo); // 插入用户产品表
		vo.setUserproductid(pk);
		// 以后这一部分要用一个异步线程来做
		if(pk!=null){
		   List<Userproduct> usrprdList = new ArrayList<Userproduct>();
		   usrprdList.add(vo);
		   // 记录用户产品记录
		   buylogDao.logUserProduct(usrprdList, orderprodVO.getOrderid(), vo.getStartdate(), UserProdBuyLogConstant.ActionType_Added);
		   // 记录产品状态日志表(UserProdStatusLog),另写一个方法
		   Short afstatus = vo.getStatus();
		   Short afstatusrank = vo.getStatusrank();
		   vo.setStatus(UserproductConstant.Status_NotExistStatus);
		   vo.setStatusrank(UserproductConstant.Status_NotExistStatus);
		   this.insertProdStatusLog(usrprdList, afstatus, afstatusrank, opertorid);
		   // 检查是否有正在进行的考试，如果有这把用户加入考试
		   BOFactory.getTestuserLogic().autoJoinProductUpcomingTest(orderprodVO.getProductbaseid(), orderprodVO.getUserid());
		}

		return vo;
	}

			
	/**
	 * 记录下用户的状态改变的日志, 同时累计加减产品学习人数
	 * @param vo1：改变前的状态信息，只需设置status和statusrank
	 * @param afstatus: 改变后的状态
	 * * @param afstatus: 改变后的子原因
	 * @return
	 * @throws Exception
	 */
	private boolean insertProdStatusLog(List<Userproduct> list, Short afstatus, Short afstatusrank, Long opertorid){
		if(list==null || list.size()<1)
			return false;
		Map paraMap = new HashMap();
		paraMap.put("userprodList", list);
		paraMap.put("afstatus", afstatus);
		paraMap.put("afstatusrank", afstatusrank);
		paraMap.put("opertorid", opertorid);
		ParallelExecutor.getInstance().executeTask(AbstractParallelExecutor.Module_Default, 
            new AbstractTaskRunnable(paraMap){
                public void run(){
                	Map map = (Map)this.getParamObject();
                	List<Userproduct> list_in = (List<Userproduct>)map.get("userprodList");
                	Short afstatus_in = (Short)map.get("afstatus");
                	Short afstatusrank_in = (Short)map.get("afstatusrank");
                	Long opertorid_in = (Long)map.get("opertorid");
                	
                	// 插入状态改变日志
            		List<Userprodstatuslog> insertList = new ArrayList<Userprodstatuslog>();
            		for(Userproduct vo : list_in){
            			Userprodstatuslog logVO = new Userprodstatuslog();
            			logVO.setBfstatus(vo.getStatus());
            			logVO.setBfstatusrank(vo.getStatusrank());
            			logVO.setAfstatus(afstatus_in);
            			logVO.setAfstatusrank( afstatusrank_in);
            			logVO.setHappendate(DateUtil.getInstance().getNowtime_GLNZ());
            			logVO.setProductbaseid(vo.getProductbaseid());
            			logVO.setProductname(vo.getProductname());
            			logVO.setShopid(vo.getShopid());
            			logVO.setUserid(vo.getUserid());
            			logVO.setUserproductid(vo.getUserproductid());
            			logVO.setOpertorid(opertorid_in);
            			insertList.add(logVO);
            			
            			// 处理product的学习人员数目更新
            			Productbase prodsatidvo = new Productbase();
            			prodsatidvo.setProductbaseid(vo.getProductbaseid());
            			if(UserproductConstant.Status_active.equals(afstatus_in)&&
            					UserproductConstant.Status_NotExistStatus.equals(vo.getStatus())){
            				prodsatidvo.setAlllearnedusernum(1);
            				prodsatidvo.setCurrentlearnusernum(1);
            			}else if(UserproductConstant.Status_inactive.equals(afstatus_in)&&
            					UserproductConstant.StatusRank_Suspend_serviceEnd.equals(afstatusrank_in)){
            				prodsatidvo.setCurrentlearnusernum(-1);
            			}
            			productdao.saveProdSatis(prodsatidvo, 1);
            		}
            		
            		stalogDao.insertBatch(insertList);
                }
			});
		
		
		return true;
	}
				
	/**
	 * 根据产品的支付使用方式不同，
	 * 根据订单条目信息把用户产品表中的使用时间、使用次数、产品使用类型设置好
	 * @param userproduct
	 * @param orderitem
	 * @return
	 */
	private Userproduct setUseTimeNum(Userproduct userprod, Short paytype, Integer uselimitnum){
		if(paytype==null)
			return userprod;
		userprod.setPaytype(paytype);
		Date nowDate = DateUtil.getInstance().getNowtime_GLNZ();
		if(PayTypeConstant.PayType_useday_month.equals(paytype)){
			userprod.setStartdate(nowDate);
			// 设置下一个月相对应的日期，间差为1个月
			userprod.setEnddate(DateUtil.getDayOfNextMonth(nowDate));
		}else if(PayTypeConstant.PayType_useday_somedays.equals(paytype)){
			userprod.setStartdate(DateUtil.getInstance().getNowtime_GLNZ());
			Date temp1 = DateUtil.dateAddDays(nowDate, uselimitnum);
			userprod.setEnddate(temp1);
		}else if(PayTypeConstant.PayType_usetimes_sometimes.equals(paytype)){
			userprod.setUselimitnum(uselimitnum);
			userprod.setUsenum(0);
		}
		return userprod;
	}

	/**
	 * 检查userproduct的状态，如果可以删除，则删除userproduct, 否则抛出错误异常
	 * @param userproductid
	 * @param operatorid
	 */
	public void checkAndDelUserproduct(Long userproductid, Long operatorid){
		Userproduct vo = usrproDao.selectByPK(userproductid);
		AssertUtil.assertNotNull(vo, null);
		// 检查userproduct是否已过期, 如果过期，则可以直接删除
		boolean isexpire = isUserproductExpire(vo);
		boolean candelete = false;
		if(isexpire){		
			candelete = true;
		}else {
			// 如果userproduct还是active状态，并且不是删除自己，则不允许删除
			if(UserproductConstant.Status_active.equals(vo.getStatus())
				&& !vo.getUserid().equals(operatorid)) {
				throw new LogicException(ExceptionConstant.Error_UserProduct_Delete_ActiveError);
			}else {
				candelete = true;
			}
		}
		if(candelete){
			List<Userproduct> usrprdList = new ArrayList<Userproduct>();
			usrprdList.add(vo);
			// delete user product
			delUserprod(usrprdList, operatorid);
			// 检查用户是否在商店中还有可以管理的product，如果没有了，则删除用户的产品管理员角色
			delUserProductMagRole(vo.getUserid(), vo.getShopid());
		}
	}
	
	/**
	 * 用户产品是否过期,true为过期，false为未过期
	 * @param vo
	 * @return
	 */
	public boolean isUserproductExpire(Userproduct vo){
		AssertUtil.assertNotNull(vo, null);
		boolean rtn = false;
		if(PayTypeConstant.PayType_free.equals(vo.getPaytype())){
			rtn = false;
		}else if(PayTypeConstant.PayType_once_nolimit.equals(vo.getPaytype())){
			if(UserproductConstant.IsPay_no.equals(vo.getIspay())){
				rtn = true;
			}else {
				rtn = false;
			}
		}else if(PayTypeConstant.PayType_useday_month.equals(vo.getPaytype())){
			//TODO
		}else if(PayTypeConstant.PayType_useday_somedays.equals(vo.getPaytype())){
			Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
			if(currentDate.compareTo(vo.getEnddate())>0){
				// 当前日期比结束日期晚，则userproduct依然有效
				rtn = false;
			}else {
				rtn = true;
			}
		}else if(PayTypeConstant.PayType_usetimes_sometimes.equals(vo.getPaytype())){
			if(vo.getUselimitnum()>vo.getUsenum()){
				rtn = false;
			}else {
				rtn = true;
			}
		}
		return rtn;
	}
	
	/**
	 * 根据用户和商店，删除用户在该商店的所有使用产品信息。
	 * 一般不会调用该函数的，因为这些信息很重要，是计费等的信息，其中的关系错综复杂
	 * 并且会生成用户notification
	 * @param userid
	 * @param shopid
	 * @return
	 */
	private int delUserprod(List<Userproduct> usrprdList, Long operatorid){
        if(usrprdList==null || usrprdList.size()<1)
        	return 0;
        // 预处理数据
		Userproduct voTemp = null;
		Productbase prodvo = null;
		for(int i=0; i<usrprdList.size(); i++){
			voTemp = usrprdList.get(i);
			if(voTemp.getProductname()==null || "".equals(voTemp.getProductname())){
				prodvo = productdao.selectByPK(voTemp.getProductbaseid());
				voTemp.setProductname(prodvo.getProductname());
			}
		}
        // 记录用户产品日志
		buylogDao.logUserProduct(usrprdList, null, DateUtil.getInstance().getNowtime_GLNZ(), UserProdBuyLogConstant.ActionType_Deleted);
		// 记录状态日志
		this.insertProdStatusLog(usrprdList, UserproductConstant.Status_inactive, UserproductConstant.StatusRank_Suspend_serviceEnd, operatorid);
		
		// 批量删除user product
		int rows = usrproDao.deleteByPKArray(usrprdList);
		// 循环userproduct，检查是否需要删除user在shop中的记录
		Set<String> usershopset = new HashSet<String>();
		String compStrTemp = null;
		UsershopDao usershopdao = BOFactory_Platform.getUsershopDao();
		List<Userproduct> userproductListTemp;
		Date curdate = DateUtil.getInstance().getNowtime_GLNZ();
		List<Usernotification> notifyList = new ArrayList<Usernotification>();
		for(int j=0; j<usrprdList.size(); j++){
			voTemp = usrprdList.get(j);
			compStrTemp = voTemp.getUserid().toString()+"||"+voTemp.getShopid().toString();
			if(!usershopset.contains(compStrTemp)){
				usershopset.add(compStrTemp);
				// 如果user在shop中没有产品了，如果用户不在商店中的org单位中，则删除该用户在usershop表中的记录
				userproductListTemp = usrproDao.selUserProds(voTemp.getUserid(), voTemp.getShopid(), null);
				if(userproductListTemp==null||userproductListTemp.size()<1){
					usershopdao.deleteUserInShop(voTemp.getUserid(), voTemp.getShopid(), UsershopConstant.Usershoptype_Productuser);
				}
			}
			// 为用户生成notification通知
			Usernotification notifyvo = new Usernotification();
			notifyvo.setContent(null);
			notifyvo.setCreatetime(curdate);
			notifyvo.setIsread(UsernotificationConstant.IsRead_NotRead);
			notifyvo.setLinkurl(UsernotificationConstant.getNotifyUrl(UsernotificationConstant.Messcode_Userproduct_Deleted));
			notifyvo.setMesscode(UsernotificationConstant.Messcode_Userproduct_Deleted);
			notifyvo.setNotifytype(UsernotificationConstant.NotifyType_UserProduct);
			notifyvo.setObjectname(voTemp.getProductname());
			notifyvo.setOpenlinktype(UsernotificationConstant.OpenlinkType_DirectGOTO);
			notifyvo.setTouserid(voTemp.getUserid());
			notifyList.add(notifyvo);
		}
		// 插入用户通知
		usernotificationDao.insertBatch(notifyList);
		return rows;
	}
	
	/**
	 * 在教育系统中为用户添加课程。
	 * 如果该产品已经存在则不会添加该产品，不报错
	 * 该方法可以给别的logic方法使用，不要在Action中调用，不会产生发送notification
	 * @param productbaseid
	 * @param userid
	 * @param shopid
	 * @param produsetype: 如何使用该产品,参见UserproductConstant.ProdUseType定义
	 * @return 如果vo不为空则说明新增成功，否则没有新增
	 * @throws Exception
	 */
	public Userproduct addUserprodFromEdu_single(Long productbaseid,Long userid,Long shopid,Short produsetype, Long opertorid) {
		if(productbaseid==null||userid==null||shopid==null)
			return null;
		Userproduct vo = null;
		// 检查用户是否已经存在该产品
		boolean exist = usrproDao.isExist(productbaseid, userid);
		if(!exist){
			Productbase prodVO = getProdDao().selectByPK(productbaseid);
			if(prodVO!=null){
				vo = new Userproduct();
				// 插入用户产品信息
				vo.setIspay(UserproductConstant.IsPay_yes);
				vo.setCost(0d);
				vo.setProductbaseid(productbaseid);
				vo.setProductname(prodVO.getProductname());
				vo.setUserid(userid);
				vo.setShopid(shopid);
				
				this.setUseTimeNum(vo,prodVO.getPaytype(), prodVO.getUselimitnum());
				vo.setGenetype(UserproductConstant.GeneType_fromSubSystem);
				if(produsetype==null){
					produsetype = UserproductConstant.ProdUseType_userNormal;
				}
				vo.setProdusetype(produsetype);
				vo.setCategoryid(prodVO.getCategoryid());
				vo.setStartdate(DateUtil.getInstance().getNowtime_GLNZ());
				vo.setStatus(UserproductConstant.Status_active); // 暂时该字段不启用，正常使用
				// 根据订单条目中的购买产品类型设置使用状态级别
				if(PayTypeConstant.PayType_free.equals(prodVO.getPaytype())){
				   vo.setStatusrank(UserproductConstant.StatusRank_Use_free);
				} else{
				   vo.setStatusrank(UserproductConstant.StatusRank_Use_normal);
				}

				Long id = usrproDao.insert(vo); // 插入用户产品表
				vo.setUserproductid(id);
				
				// 如果是管理产品，则将用户角色变为产品管理员
				if(UserproductConstant.ProdUseType_operatorMag.equals(produsetype)){
					saveUserProductMagRole(userid, shopid);
				}
				
				// 记录产品状态日志表(UserProdStatusLog),另写一个方法
				List<Userproduct> usrprdList = new ArrayList<Userproduct>();
				usrprdList.add(vo);
				buylogDao.logUserProduct(usrprdList, null, vo.getStartdate(), UserProdBuyLogConstant.ActionType_Added);
				Short afstatus = vo.getStatus();
				Short afstatusRank = vo.getStatusrank();
				vo.setStatus(new Short("-1"));
				vo.setStatusrank(new Short("-1"));

			    this.insertProdStatusLog(usrprdList, afstatus, afstatusRank, opertorid);
			}
		}
		return vo;
	}
	
	/**
	 * 在教育系统中为用户添加课程。生成类型是“从子系统”，使用类型是“免费使用”
	 * 如果该产品已经存在则不会添加该产品，不报错
	 * 如果达到资源限制，则抛出异常
	 * @param productidStr
	 * @param useridArr
	 * @param shopid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean addUserprodFromEdu(Long productid,String[] useridArr,Long shopid,Short status, Long opertorid){
		if(productid==null||useridArr==null
				||useridArr.length<1||shopid==null||status==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);

		Productbase prodvo = productdao.selectByPK(productid);
		int userlimit = SysparamConstantNettest.maxProductUser_freeProduct_num;
		if(!PayTypeConstant.PayType_free.equals(prodvo.getPaytype())){
			userlimit = SysparamConstantNettest.maxProductUser_paidProduct_num;
		}
		int usernum = usrproDao.selectUserCountOfProduct(productid);
		if(usernum+useridArr.length>userlimit){
			throw new LogicException(ExceptionConstant.Error_ResourceLimit_MaxProductUser);
		}
		Userproduct votemp = null;
		Long useridLong = null;
		List<Map> eventList = new ArrayList<Map>();
		for(int j=0;j<useridArr.length;j++){
			useridLong = new Long(useridArr[j]);
			votemp = this.addUserprodFromEdu_single(productid,useridLong,shopid,status,opertorid);
		    // 如果votemp不为空说明新增成功，则需要发送消息给用户
			if(votemp!=null){
				// 给用户发送 新增用户产品的notification
	    		Map<String, String> subeventMap = new HashMap<String, String>();
	    		subeventMap.put("touserid", useridLong.toString());
	    		subeventMap.put("notifytype", UsernotificationConstant.NotifyType_UserProduct.toString());
	    		subeventMap.put("creatorid", opertorid.toString());
	    		//paraMap.put("content", null);
	    		subeventMap.put("messcode", UsernotificationConstant.Messcode_Userproduct_BeenAdded);
	    		subeventMap.put("objectname", votemp.getProductname());
	    		subeventMap.put("linkurl", UsernotificationConstant.getNotifyUrl(UsernotificationConstant.Messcode_Userproduct_BeenAdded)+votemp.getUserproductid().toString());
	    		subeventMap.put("openlinktype", UsernotificationConstant.OpenlinkType_NewDiv.toString());
	    		eventList.add(subeventMap);
		    }
		}
		
		Map paraMap = new HashMap();
		paraMap.put(EventHandlerNetTest.Parameter_BatchList, eventList);
		Event event = new Event(EventHandlerNetTest.EventType_Usernotification_UserMessage, paraMap);
		EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);

		return true;
	}
	
    
    /**
     * 检查用户是否有被分配的产品关联，主要用于删除用户时的检查
     * @param userids
     * @param shopid
     * @return -1缺少参数 0没有引用 1有产品引用
     */
	public String checkUserProdLink(Long userid, Long shopid){
	    if(userid==null||shopid==null)
	    	return "-1";
	    UserproductQuery queryVO = new UserproductQuery();
	    List list = null;
    	queryVO.setUserid(userid);
    	queryVO.setShopid(shopid);
    	//queryVO.setStatus(UserproductConstant.Status_use); // 需要子查询有效的吗?
    	list = usrproDao.selectByVO(queryVO);
    	if(list!=null&&list.size()>0)
    		return "1";
    	else
    		return "0";
	}
	
	/**
	 * 查询用户还没有购买的产品的具体信息，如果用户有了这个产品则抛出异常。用于给用户添加产品时dwr调用
	 * @param userid
	 * @param productid
	 * @return
	 */
	public Productbase getNonUserProduct(Long userid, Long productid, Long localeid){
		boolean exist = usrproDao.isExist(productid, userid);
		Productbase vo = null;
		if(exist){
			throw new LogicException(ExceptionConstant.Error_User_HasProduct);
		}else {
			vo = ProductLogicImpl.getInstance().selectVO(productid, localeid);
		}
		return vo;
	}
	
	/**
     * 更新产品使用方式和状态
     * @param pk
     * @param produsetype
     */
    public int updUsetypeStatusByPK(Long userproductid, Short produsetype,
    		      Short status, Long opertorid, String statusdesc)
    {	
    	if(status==null){
    		return 0;
    	}
    	Userproduct usrprovo = usrproDao.selectByPK(userproductid);
    	// 如果没有任何改变，直接返回
    	if(usrprovo.getStatus().equals(status) && usrprovo.getProdusetype().equals(produsetype)){
    		return 0;
    	}
    	// 修改状态
    	int rows = usrproDao.updUsetypeStatusByPK(userproductid, produsetype, status);
    	// 如果用户产品改变为有效并且是管理产品，则给用户添加product manager role
    	if(UserproductConstant.Status_active.equals(status)
    			&& UserproductConstant.ProdUseType_operatorMag.equals(produsetype)){
    		saveUserProductMagRole(usrprovo.getUserid(), usrprovo.getShopid());
    	}
    	// 如果用户产品改变为失效或者是普通使用产品，则给用户删除product manager role
    	if(UserproductConstant.Status_inactive.equals(status)
    			|| UserproductConstant.ProdUseType_userNormal.equals(produsetype)){
    		delUserProductMagRole(usrprovo.getUserid(), usrprovo.getShopid());
    	}
    	
    	// 记录用户产品状态改变日志(只有状态改变时才记录log)
    	if(!usrprovo.getStatus().equals(status)){
    		Userprodstatuslog logVO = new Userprodstatuslog();
    		logVO.setBfstatus(usrprovo.getStatus());
    		logVO.setBfstatusrank(usrprovo.getStatusrank());
    		logVO.setAfstatus(status);
    		logVO.setAfstatusrank(null);
    		logVO.setHappendate(DateUtil.getInstance().getNowtime_GLNZ());
    		logVO.setProductbaseid(usrprovo.getProductbaseid());
    		logVO.setShopid(usrprovo.getShopid());
    		logVO.setUserid(usrprovo.getUserid());
    		logVO.setUserproductid(userproductid);
    		logVO.setOpertorid(opertorid);
    		logVO.setStatusdisc(statusdesc);
    		stalogDao.insert(logVO);
        	// 发送用户产品状态变更的 user notification
    		Map<String, String> paraMap = new HashMap<String, String>();
    		paraMap.put("touserid", usrprovo.getUserid().toString());
    		paraMap.put("notifytype", UsernotificationConstant.NotifyType_UserProduct.toString());
    		paraMap.put("creatorid", opertorid.toString());
    		String messKey = UsernotificationConstant.Messcode_Userproduct_StatusChanged_Inactive;
    		if(UserproductConstant.Status_active.equals(status)){
    			messKey = UsernotificationConstant.Messcode_Userproduct_StatusChanged_Active;
    		}
    		//paraMap.put("content", contentKey);
    		paraMap.put("messcode", messKey);
    		paraMap.put("objectname", usrprovo.getProductname());
    		paraMap.put("linkurl", UsernotificationConstant.getNotifyUrl(messKey)+userproductid);
    		paraMap.put("openlinktype", UsernotificationConstant.OpenlinkType_NewDiv.toString());
    		Event event = new Event(EventHandlerNetTest.EventType_Usernotification_UserMessage, paraMap);
    		EventHandlerNetTest.getInstance().publishEvent(event, EventHandlerNetTest.HandleType_Asyschronized_Thread);
        }
		return rows;
    }
    
	/**
	 * 根据用户信息返回用户可以使用的产品字符串数组
	 * 用于向用户sessionInfo里写可用的产品字符串
	 * @param shopid: 如果shopid为空，则查询用户所有的产品
	 * @return prodIsStrArr: 产品id字符串数组，第1个元素是:学习使用的prodIdStr，
	 * 第2个元素是可以管理的prodIdStr，第3个元素是:如果用户只有一个产品，则返回该产品的productname
	 */
    @Cacheable(value = "netTest.productCache", key = "'UserproductLogic.getUserprodStr~'+#userid+'~'+#shopid", unless = "#result==null")
	public String[] getUserprodStr(Long userid, Long shopid){
		if(userid==null){
			return new String[]{"","",""};
		}
		StringBuffer buffer_use = new StringBuffer();
		StringBuffer buffer_mag = new StringBuffer();
		String prodname = null;
		// 查询用户的产品列表
		List<Userproduct> userproductList = usrproDao.selUserProds(userid, shopid, UserproductConstant.Status_active);
		String[] rtnArr = null;
		if(userproductList!=null&&userproductList.size()>0){
			Userproduct vo = null;
			for(int i=0;i<userproductList.size();i++){
				vo = userproductList.get(i);
				if(vo!=null){
					buffer_use.append(String.valueOf(vo.getProductbaseid())).append(",");
					if(UserproductConstant.ProdUseType_operatorMag.equals(vo.getProdusetype())){
						buffer_mag.append(String.valueOf(vo.getProductbaseid())).append(",");
					}
				}
			}
			// 如果只有一个产品，则设置产品名称
			if(userproductList.size()==1 && vo!=null){
				prodname = vo.getProductname();
			}
			
			String useprodStr = buffer_use.toString();
			if(useprodStr.endsWith(",")){
				useprodStr = useprodStr.substring(0, useprodStr.length()-1);
			}
			String magprodStr = buffer_mag.toString();
			if(magprodStr.endsWith(",")){
				magprodStr = magprodStr.substring(0, magprodStr.length()-1);
			}
			rtnArr = new String[]{useprodStr, magprodStr, prodname};
			// flush cache
			CacheSynchronizer.getInstance().buildAssoc(
					"netTest.productCache",
					"UserproductLogic.getUserprodStr~" + userid + "~" + shopid,
					new String[] { UserproductDaoImpl.User_addDel_Userproduct + ":" + userid,
							UserproductDaoImpl.User_update_Userproduct + ":" + userid });
						
		}
		return rtnArr;
	}
    
    /**
     * get user learning and manage product number
     */
    public int[] getUserprodNumber(Long userid, Long shopid){
		if(userid==null){
			return new int[]{0,0};
		}
		int learnNum = 0;
		int manageNum = 0;
		String prodname = null;
		// 查询用户的产品列表
		List<Userproduct> userproductList = usrproDao.selUserProds(userid, shopid, UserproductConstant.Status_active);
		int[] rtnArr = null;
		if(userproductList!=null&&userproductList.size()>0){
			Userproduct vo = null;
			for(int i=0;i<userproductList.size();i++){
				vo = userproductList.get(i);
				if(vo!=null){
					if(UserproductConstant.ProdUseType_userNormal.equals(vo.getProdusetype())){
						learnNum++;
					}else if(UserproductConstant.ProdUseType_operatorMag.equals(vo.getProdusetype())){
						manageNum++;
					}
				}
			}
			rtnArr = new int[]{learnNum, manageNum};		
		}else {
			rtnArr = new int[]{0,0};
		}
		
		return rtnArr;
	}
	
	/**
	 * 查找快到期的用户产品记录，并生成userNotification
	 * 查找已经过期的用户产品记录，并删除userproduct，并且记录日志
	 * TODO 提前多少天或多少次提醒用户的值应该由系统参数中定义(sysparam)，而不应该写死
	 */
	public void notifyDueUserProduct(){
		Date currentDate = DateUtil.getInstance().getNowtime_GLNZ();
		int defaultNotiday = UsernotificationConstant.Notification_ProductDay;
		int lefttimes = UsernotificationConstant.Notification_ProductTimes;
		Long creatorid = UsernotificationConstant.CreatorID_System;
		// 查询要到期的用户产品信息，并生成notifications
		usrproDao.notifyDueDateProduct(currentDate, defaultNotiday, 
										UsernotificationConstant.NotifyType_UserProduct, 
										UsernotificationConstant.Messcode_UserProduct_NearDueProduct_Date, 
										null, creatorid, UsernotificationConstant.IsRead_NotRead, 
										UsernotificationConstant.getNotifyUrl(UsernotificationConstant.Messcode_UserProduct_NearDueProduct_Date), 
										UsernotificationConstant.OpenlinkType_NewDiv);
		// 查询要到使用次数的用户产品信息，生成notifications
		usrproDao.notifyDueNumsProduct(currentDate, lefttimes, 
				                       UsernotificationConstant.NotifyType_UserProduct, 
				                       UsernotificationConstant.Messcode_UserProduct_NearDueProduct_Times, 
				                       null, creatorid, UsernotificationConstant.IsRead_NotRead, 
				                       UsernotificationConstant.getNotifyUrl(UsernotificationConstant.Messcode_UserProduct_NearDueProduct_Times), 
				                       UsernotificationConstant.OpenlinkType_NewDiv);
	
	    // 处理用户已经到期的用户产品，删除userproduct，并写日志
		// 分批处理，每次500条记录
		boolean hasmore = true;
		List<Userproduct> needRemoveList = null;
		while(hasmore){
			needRemoveList = usrproDao.selectNeedRemoveProd(currentDate);
			if(needRemoveList!=null && needRemoveList.size()>0){
				this.delUserprod(needRemoveList, creatorid);
			}else {
				hasmore = false;
			}
		}
		
	}
	
	private void saveUserProductMagRole(Long userid, Long shopid){
		UserRoleDao userroleDao = UserRoleDaoImpl.getInstance();
		RolesDao roleDao = RolesDaoImpl.getInstance();
		String roleidStr = roleDao.selRoleidStrByCode(new String[]{RolesConstant.ROLE_ShopProductAdmin}, CommonConstant.SysCode_Education);
		Long roleid = new Long(roleidStr);
		List<Long> roleidList = userroleDao.selectUserRole(userid, shopid, CommonConstant.SysCode_Education);
		if(roleidList==null || !roleidList.contains(roleid)){
	    	UserRole userroleVO = new UserRole();
	    	userroleVO.setRoleid(roleid);
	    	userroleVO.setShopid(shopid);
	    	userroleVO.setUserid(userid);
	    	userroleVO.setUsetype(UserRolesConstant.UseType_use);
	    	userroleVO.setSyscode(CommonConstant.SysCode_Education);
	    	userroleDao.insert(userroleVO);
	    }
	}
	
	/**
	 * 如果用户没有可以管理的产品了，就把用户在该商店中的产品管理角色删除
	 * @param userid
	 * @param shopid
	 */
	private void delUserProductMagRole(Long userid, Long shopid){
		UserproductQuery queryVO = new UserproductQuery();
		queryVO.setUserid(userid);
		queryVO.setShopid(shopid);
		queryVO.setStatus(UserproductConstant.Status_active);
		queryVO.setProdusetype(UserproductConstant.ProdUseType_operatorMag);
		List list = usrproDao.selectByVO(queryVO);
		if(list==null || list.size()<1){
			UserRoleDao userroleDao = UserRoleDaoImpl.getInstance();
			RolesDao roleDao = RolesDaoImpl.getInstance();
			String roleidStr = roleDao.selRoleidStrByCode(new String[]{RolesConstant.ROLE_ShopProductAdmin}, CommonConstant.SysCode_Education);
			Long roleid = new Long(roleidStr);
			userroleDao.deleteByPK(userid, roleid, shopid, CommonConstant.SysCode_Education);
		}
	}
	
    /**
     * static spring getMethod
     */
    public static UserproductLogic getInstance() {
       	 UserproductLogic logic = (UserproductLogic)BeanFactory.getBeanFactory().getBean("userproductLogic");
         return logic;
     }


	public UserprodbuylogDao getBuylogDao() {
		return buylogDao;
	}


	public void setBuylogDao(UserprodbuylogDao buylogDao) {
		this.buylogDao = buylogDao;
	}

	
	public UserproductDao getUsrproDao() {
		return usrproDao;
	}


	public void setUsrproDao(UserproductDao usrproDao) {
		this.usrproDao = usrproDao;
	}

	public UserprodstatuslogDao getStalogDao() {
		return stalogDao;
	}

	public void setStalogDao(UserprodstatuslogDao stalogDao) {
		this.stalogDao = stalogDao;
	}

	public ProductbaseDao getProdDao() {
		return ProductbaseDaoImpl.getInstance();
	}


	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		String eventType = event.getEventType();
		if(EventHandlerNetTest.EventType_Product_LearnActivity.equals(eventType)){
			Learnactivity vo = (Learnactivity)paraMap.get("vo");
			// update user's lastest access time on the product
			if(vo!=null){
			   usrproDao.updateAccessTime(vo.getUserid(), vo.getProductid(), vo.getStarttime());
			}
		}
		
	}

	public UserDao getUserDao() {
		return UserDaoImpl.getInstance();
	}

	public UsernotificationDao getUsernotificationDao() {
		return usernotificationDao;
	}

	public void setUsernotificationDao(UsernotificationDao usernotificationDao) {
		this.usernotificationDao = usernotificationDao;
	}

	public ProductbaseDao getProductdao() {
		return productdao;
	}

	public void setProductdao(ProductbaseDao productdao) {
		this.productdao = productdao;
	}
	
}
