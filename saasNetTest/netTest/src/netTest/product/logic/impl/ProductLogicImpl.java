package netTest.product.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netTest.bean.BOFactory;
import netTest.bean.BeanFactory;
import netTest.bean.SysparamConstantNettest;
import netTest.common.constant.RolesConstant;
import netTest.common.logic.impl.UserLoginSessionLogicImpl;
import netTest.event.EventHandlerNetTest;
import netTest.exam.constant.TestinfoConstant;
import netTest.exam.logic.TestinfoLogic;
import netTest.exception.ExceptionConstant;
import netTest.exercise.logic.ExerciseLogic;
import netTest.learncont.constant.LearncontentConstant;
import netTest.learncont.logic.LearncontentLogic;
import netTest.paper.logic.PaperLogic;
import netTest.prodcont.dao.ContentcateDao;
import netTest.prodcont.vo.Contentcate;
import netTest.product.constant.ProductConstant;
import netTest.product.constant.UserproductConstant;
import netTest.product.dao.GoodproductDao;
import netTest.product.dao.ProductbaseDao;
import netTest.product.dao.ProductlogDao;
import netTest.product.dao.UserproductDao;
import netTest.product.dto.ProductbaseQuery;
import netTest.product.logic.ProductLogic;
import netTest.product.logic.UserproductLogic;
import netTest.product.vo.Goodproduct;
import netTest.product.vo.Productbase;
import netTest.product.vo.Productlog;
import netTest.product.vo.Userproduct;
import netTest.util.UploadFileUtilNettest;
import netTest.wareques.logic.WareLogic;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;

import platform.constant.ShopConstant;
import platform.dao.ProductcategoryDao;
import platform.dao.ShopDao;
import platform.daoImpl.ProductcategoryDaoImpl;
import platform.daoImpl.ShopDaoImpl;
import platform.logicImpl.BOFactory_Platform;
import platform.vo.Productcategory;
import platform.vo.Shop;
import commonTool.base.Page;
import commonTool.concurrent.AbstractParallelExecutor;
import commonTool.concurrent.AbstractTaskRunnable;
import commonTool.constant.CommonConstant;
import commonTool.constant.PayTypeConstant;
import commonTool.event.Event;
import commonTool.event.EventHandle;
import commonTool.exception.LogicException;
import commonTool.util.DateUtil;
import commonTool.util.UploadFileUtil;

public class ProductLogicImpl implements ProductLogic, EventHandle {
	static Logger log = Logger.getLogger(ProductLogicImpl.class.getName());
	private ProductbaseDao dao;
	private UserproductDao userproductDao;
	private UserproductLogic userproductLogic;
	private TestinfoLogic testinfoLogic;
	private ExerciseLogic exerciseLogic;
	private PaperLogic paperLogic;
	private WareLogic wareLogic;
	private LearncontentLogic learncontentLogic;
	private ProductlogDao productlogDao;
	private ContentcateDao contentcateDao;

	// 不在spring配置文件中配置
	private ProductcategoryDao cateDao;
	
	private GoodproductDao goodproductDao;
	
	
	/* 
	 * 根据主键查询产品数据
	 */
	public Productbase selectVO(Long id, Long localeid) {
    	if(id==null)
    		return null;
    	Productbase vo = dao.selectByPK(id);
    	Productbase satisvo = dao.selectProdSatis(id);
    	if(satisvo!=null){
    		vo.setAlllearnedusernum(satisvo.getAlllearnedusernum());
    		vo.setCurrentlearnusernum(satisvo.getCurrentlearnusernum());
    		vo.setLearncontdocnum(satisvo.getLearncontdocnum());
    		vo.setLearncontmedianum(satisvo.getLearncontmedianum());
    		vo.setExercisenum(satisvo.getExercisenum());
    		vo.setTestinfonum(satisvo.getTestinfonum());
    	}
    	//
    	if(localeid!=null && vo!=null){
    		Productcategory cateVO = getCateDao().selectByLogicPK(vo.getCategoryid(), localeid);
    		if(cateVO!=null){
    		   vo.setCategoryname(cateVO.getCategoryname());
    		}
    	}
    	return vo;
	}
	
	/**
	 * 查询目前学习人数最多的课程, cache不会自动更新，时间到期后自动失去cache
	 * @param categoryid
	 * @return
	 */
	public List<Productbase> selectMostLearned(Long categoryid){
		List<Productbase> productlist = null;
		List<Productbase> mostlearnedList = dao.selectMostLearned(categoryid);
		if(mostlearnedList!=null && mostlearnedList.size()>0){
			productlist = new ArrayList<Productbase>();
			Productbase prodVO = null;
			for(Productbase mostLearnVO : mostlearnedList){
				prodVO = dao.selectByPK(mostLearnVO.getProductbaseid());
				if(prodVO!=null && ProductConstant.Status_valid.equals(prodVO.getStatus())){
				   productlist.add(prodVO);
			    }
			}
		}
		return productlist;
	}
	
	/**
	 * 查询系统推荐的产品
	 * @param cateid
	 * @param localeid
	 * @return
	 */
	public List<Productbase> selectRecommendProd(Long cateid, Long localeid){
		List<Productbase> productlist = null;
		Map<Long, Goodproduct> goodprodMap = BOFactory.getGoodproductDao().selGoodproduct(cateid, localeid);
	    if(goodprodMap!=null && !goodprodMap.isEmpty()){
		   productlist = new ArrayList<Productbase>();
		   Productbase prodVO = null;
		   for(Map.Entry<Long, Goodproduct> entry : goodprodMap.entrySet()){
			   prodVO = dao.selectByPK(entry.getKey());
			   if(prodVO!=null && ProductConstant.Status_valid.equals(prodVO.getStatus())){
				   productlist.add(prodVO);
			   }
		   }
	    }
	    return productlist;
	}
	
	/**
	 * 查询目录下包含了哪些产品及所属商店，为了管理产品使用
	 * @param vo
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryCateProdForMag(ProductbaseQuery queryVO, boolean loadextdata, int pageIndex,int pageSize,Integer total) {
		if(queryVO==null)
			return Page.EMPTY_PAGE;
		if(ProductConstant.Issysgoodproduct_yes.equals(queryVO.getIssysgoodproduct())
				&&queryVO.getCategoryid()==null){
			return Page.EMPTY_PAGE;
		}
		Page page = dao.selectByVOPage(queryVO.getCategoryid(), queryVO.getProductname(), queryVO.getShopid(), 
				   queryVO.getIsIncludeChild(), queryVO.getIssysgoodproduct(), queryVO.getPaytype(),
				   queryVO.getIsopen(), queryVO.getStatus(), queryVO.getLocaleid(), queryVO.getRegioncode(),
				   pageIndex, pageSize, total);
		if(loadextdata && page!=null&&page.getList()!=null&&page.getList().size()>0){
			Productbase tempVO = null;
			Shop shopVO = null;
			StringBuffer buffer = new StringBuffer();
			for(int i=0;i<page.getList().size();i++){
				tempVO = (Productbase)page.getList().get(i);
			    shopVO = getShopdao().selectByPK(tempVO.getShopid(), queryVO.getLocaleid());
			    tempVO.setShopname(shopVO.getShopname());
			    tempVO.setShopcode(shopVO.getShopcode());
				buffer.append(tempVO.getProductbaseid()).append(",");
			}
			//设置是否是系统推荐产品
			Map<Long, Goodproduct> goodprodMap = goodproductDao.selectByPkStr(buffer.toString());
			if(goodprodMap==null){
				goodprodMap = new HashMap<Long, Goodproduct>();
			}
			if(goodprodMap!=null){
				for(int i=0;i<page.getList().size();i++){
					tempVO = (Productbase)page.getList().get(i);
					if(goodprodMap.get(tempVO.getProductbaseid())!=null){
						tempVO.setIssysgoodproduct(ProductConstant.Issysgoodproduct_yes);
						tempVO.setGoodproductscope(goodprodMap.get(tempVO.getProductbaseid()).getScope());
					}else {
						tempVO.setIssysgoodproduct(ProductConstant.Issysgoodproduct_no);
						tempVO.setGoodproductscope(null);
					}
				}
			}
		}
		return page;
	}
	
	/**
	 * 查询目录下包含了哪些产品及所属商店. 互联网用户用此方法来搜索产品，因此必须是开放的有效的产品(由调用者set)
	 * @param vo
	 * @param needCommendProduct  是否需要加入推荐产品，如果需要则会在搜索结果的第一页加入推荐产品
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Page qryCateProductsPage(ProductbaseQuery queryVO, boolean needCommendProduct, int pageIndex, int pageSize,Integer total){
		if(queryVO==null||queryVO.getLocaleid()==null||(queryVO.getCategoryid()==null&&
				((queryVO.getProductname()==null)||queryVO.getProductname().trim().length()<1)))
			return Page.EMPTY_PAGE;
		//queryVO.setIsopen(ProductConstant.Isopen_yes);
		Page page = dao.selectByVOPage(queryVO.getCategoryid(), queryVO.getProductname(), queryVO.getShopid(), 
				   queryVO.getIsIncludeChild(), queryVO.getIssysgoodproduct(), queryVO.getPaytype(),
				   queryVO.getIsopen(), queryVO.getStatus(), queryVO.getLocaleid(), queryVO.getRegioncode(),
				   pageIndex, pageSize, total);
		List list1 = page.getList();
		if(page!=null && list1!=null && list1.size()>0){
			Productbase tempVO = null;
			Productbase tempVO2 = null;
			Shop shopVO = null;
			if(pageIndex<4 && needCommendProduct){
			   // 如果是前4页，需要把系统自动推荐的产品加入其中，并且添加在最前面
			   List list2 = null;
			   Map<Long, Goodproduct> goodprodMap = goodproductDao.selGoodproduct(queryVO.getCategoryid(), queryVO.getLocaleid());
			   if(goodprodMap!=null && !goodprodMap.isEmpty()){
				   list2 = new ArrayList();
				   for(Map.Entry<Long, Goodproduct> entry : goodprodMap.entrySet()){
					   tempVO2 = dao.selectByPK(entry.getKey());
					   if(ProductConstant.Status_valid.equals(tempVO2.getStatus())){
						   list2.add(tempVO2);
					   }
				   }
				   // 循环list1,去除已经存在推荐产品中的产品
				   for(int j=list1.size()-1; j>-1; j--){
						tempVO = (Productbase)list1.get(j);
						if(goodprodMap.get(tempVO.getProductbaseid())!=null){
						   list1.remove(j);
						}
				   }
				   // 这样保证 systemgoodproduct 可以被放置到最前面
				   list2.addAll(list1);
				   page.setList(list2);
			   }
			}
			
			// 设置shopname
			list1 = page.getList();
			for(int j=0; j<list1.size(); j++){
				tempVO = (Productbase)list1.get(j);
 			    shopVO = getShopdao().selectByPK(tempVO.getShopid(), queryVO.getLocaleid());
				tempVO.setShopname(shopVO.getShopname());
			}
			page.setList(list1);
		}
		return page;
	}
	
	/**
	 * 检查shop还可以创建的产品数目
	 * @param shopid
	 * @return
	 */
	public int checkShopProductLimit(Long shopid){
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		int maxproductnum = 0;
		if(ShopConstant.ChargeType_paid.equals(shopvo.getChargetype())){
			maxproductnum = SysparamConstantNettest.maxProduct_paidShop_num;
		}else {
			maxproductnum = SysparamConstantNettest.maxProduct_freeShop_num;
		}
		int currentnum = dao.selShopProdCount(shopid);
		return maxproductnum-currentnum;
	}
	
    /**
     * 根据产品主键字符串查询产品
     * @param pkStr
     * @param localeid 用来查询productcategory的目录名
     * @return
     */
	public List<Productbase> selectByPkStr(String pkStr, Long localeid){
		if(pkStr==null||pkStr.trim().length()<1){
			return new ArrayList<Productbase>();
		}
		List<Productbase> list = dao.selectByPkStr(pkStr);
		Productcategory cateVO = null;
		if(localeid!=null){
			Map<Long, String> cateMap = new HashMap<Long, String>();
			for(Productbase prodVO : list){
				if(cateMap.get(prodVO.getCategoryid())!=null){
					prodVO.setCategoryname(cateMap.get(prodVO.getCategoryid()));
				}else {
					cateVO = getCateDao().selectByLogicPK(prodVO.getCategoryid(), localeid);
					cateMap.put(prodVO.getCategoryid(), cateVO.getCategoryname());
					prodVO.setCategoryname(cateVO.getCategoryname());
				}
			}
		}		
		return list;
	}
	
	public Productbase save(Productbase vo, Long userid){
		if(vo==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		// 判断用户设置的合法性
		Long shopid = vo.getShopid();
		Productbase prodvoInDB = null;
		if(vo.getProductbaseid()!=null){
			prodvoInDB = dao.selectByPK(vo.getProductbaseid());
			shopid = prodvoInDB.getShopid();
		}
		Shop shopvo = BOFactory_Platform.getShopDao().selectByPK(shopid, null);
		// 如果商店是free shop，则创建的product也必须是free的，否则会报错
		if(vo.getPaytype()!=null && ShopConstant.ChargeType_free.equals(shopvo.getChargetype())
				&& !PayTypeConstant.PayType_free.equals(vo.getPaytype())){
			throw new LogicException(ExceptionConstant.Error_Invalid_Visit);
		}
		// new product
		if(vo.getProductbaseid()==null){
			// check product num limit
			int avaiablenum = checkShopProductLimit(vo.getShopid());
			if(avaiablenum<1){
				throw new LogicException(ExceptionConstant.Error_ResourceLimit_MaxShopProduct);
			}
			// insert
			Long pk = dao.insert(vo);
			vo.setProductbaseid(pk);
			// make creator as product admin
			UserproductLogicImpl.getInstance().addUserprodFromEdu_single(pk, userid, vo.getShopid(), UserproductConstant.ProdUseType_operatorMag, userid);
			
			// 记录日志
			Productlog logvo = new Productlog();
	    	logvo.setCreatorid(userid);
	    	logvo.setShopid(vo.getShopid());
	    	logvo.setProductid(pk);
	    	logvo.setLogtype(Productlog.LogType_New);
	    	logvo.setProductname(vo.getProductname());
	    	Date logdate = DateUtil.getInstance().getNowtime_GLNZ();
	    	logvo.setLogdate(logdate);
	    	productlogDao.insert(logvo);
		}else {
			// if status is changed from 'preparing' to 'valid', need check the learncontent of this course
			if(ProductConstant.Status_valid.equals(vo.getStatus())&&
				!ProductConstant.Status_valid.equals(prodvoInDB.getStatus())){
				boolean canpublish = checkCourseForPublish(vo.getProductbaseid());
				if(!canpublish){
					// throw exception
					throw new LogicException(ExceptionConstant.Error_Course_NoEnoughLearnContent);
				}
			}
			dao.updateByPK(vo);
		}
		return vo;
	}
	
	/**
	 * 检查课程内容是否满足发布要求
	 * 课程的任一课程章节必须要有学习资料，如果课程没有章节目录，则必须要有学习资料
	 * @param productid
	 * @return
	 */
	public boolean checkCourseForPublish(Long productid){
		Productbase prodsatisvo = dao.selectProdSatis(productid);
		if(prodsatisvo==null || 
				 ((prodsatisvo.getWarequesnum()==null||prodsatisvo.getWarequesnum()<1)
				  &&(prodsatisvo.getLearncontdocnum()==null||prodsatisvo.getLearncontdocnum()<1)
				  &&(prodsatisvo.getLearncontmedianum()==null||prodsatisvo.getLearncontmedianum()<1))
		   ){
			return false;
		}
		// 检查是否填写课程简介及长度是否大于100
		Productbase descvo = dao.selectProdDesc(productid);
		if(descvo==null || descvo.getProductdesc().length()<100){
			return false;
		}
		// 检查课程中是否有不包含任何学习资料的课程章节
		List<Contentcate> cateList_withoutLearn = contentcateDao.getCateNotExistInLearncontent(productid);
		List<Contentcate> FirstLevelCateList = contentcateDao.getAllInOne(productid);
		Map<Long, Contentcate> allElemMap = new HashMap<Long, Contentcate>();
		for(Contentcate catevo : FirstLevelCateList){
			allElemMap.put(catevo.getContentcateid(), catevo);
		}
		boolean rtn = true;
		if(cateList_withoutLearn!=null && cateList_withoutLearn.size()>0){
			for(Contentcate catevoTemp : cateList_withoutLearn){
				// 如果contentcate不是顶级节点，或者"是顶级结点并且没有子节点"，这样的情况都需要有学习资料，因此不允许发布
				if(!CommonConstant.TreeTopnodePid.equals(catevoTemp.getPid())
					|| (CommonConstant.TreeTopnodePid.equals(catevoTemp.getPid()) 
							&& !allElemMap.get(catevoTemp.getContentcateid()).hasSubList())
				  ){
					rtn = false;
					break;
				}
			}
		}
		return rtn;
	}
	
	public void onEvent(Event event) {
		Map paraMap = event.getParaMap();
		Long productid = new Long(paraMap.get("productid").toString());
		Integer number = new Integer(paraMap.get("number").toString());
		String eventType = event.getEventType();
		if(EventHandlerNetTest.EventType_LearnContent_AddDelete.equals(eventType)){
			Integer contenttype = new Integer(paraMap.get("contenttype").toString());
			updateLearnContentNumber(productid, contenttype, number);
		}else if(EventHandlerNetTest.EventType_TestInfo_Finished.equals(eventType)){
			updateTestExerciseNumber(productid, 1, number);
		}else if(EventHandlerNetTest.EventType_Exercise_AddDelete.equals(eventType)){
			updateTestExerciseNumber(productid, 2, number);
		}
		
	}
	
	private void updateLearnContentNumber(Long productid, Integer contenttype, Integer number){
		Productbase vo = new Productbase();
		vo.setProductbaseid(productid);
		if(LearncontentConstant.ContentType_HTML.equals(contenttype)||
				LearncontentConstant.ContentType_PDF.equals(contenttype)||
				LearncontentConstant.ContentType_WORD.equals(contenttype)||
				LearncontentConstant.ContentType_PPT.equals(contenttype)||
				LearncontentConstant.ContentType_EXCEL.equals(contenttype)){
			vo.setLearncontdocnum(number);
		}else if(LearncontentConstant.ContentType_VIDEO.equals(contenttype)||
				LearncontentConstant.ContentType_AUDIO.equals(contenttype)||
				LearncontentConstant.ContentType_FLASH.equals(contenttype)){
			vo.setLearncontmedianum(number);
		}
		if(vo.getLearncontdocnum()!=null || vo.getLearncontmedianum()!=null){
			dao.saveProdSatis(vo, 1);
		}
	}
	
	private void updateTestExerciseNumber(Long productid, int type, Integer number){
		Productbase vo = new Productbase();
		vo.setProductbaseid(productid);
		if(type==1){
			vo.setTestinfonum(number);
		}else if(type==2){
			vo.setExercisenum(number);
		}
		dao.saveProdSatis(vo, 1);
	}
	
	/**
	 * 删除产品, 必须先手动删除该产品下的userproduct
	 * @param productid
	 * @param sessUserid
	 * @param sessOrgid
	 */
	public void deleteProduct(Long productid, Long sessUserid, Long sessOrgid) throws LogicException{
		Productbase vo = dao.selectByPK(productid);
		Long shopid = vo.getShopid();
		List<Long> prodidList = userproductDao.selectAllProdUserId(productid, shopid, null);
		if(prodidList!=null && prodidList.size()>0){
			// 仅仅有当前操作的管理员，因此删除该user
			if(prodidList.size()==1 && prodidList.get(0).equals(sessUserid)){
				Userproduct userprodvo = userproductDao.selectByLogicPk(prodidList.get(0), productid);
				userproductLogic.checkAndDelUserproduct(userprodvo.getUserproductid(), sessUserid);
			}else {
			    throw new LogicException(ExceptionConstant.Error_Product_Delete_HasUserProduct);
			}
		}
		int testcount = testinfoLogic.countTestinfoNumber(productid, shopid, TestinfoConstant.Teststatus_started);
		if(testcount>0){
			throw new LogicException(ExceptionConstant.Error_Product_Delete_HasActiveTestinfo);
		}
		// 更新product的状态为删除中
		vo.setStatus(ProductConstant.Status_deleting);
		dao.updateByPK(vo);
		// 以下的删除采用异步过程来处理
		Map paraMap = new HashMap();
		paraMap.put("productid", productid);
		paraMap.put("sessUserid", sessUserid);
		paraMap.put("sessOrgid", sessOrgid);
		paraMap.put("shopid", shopid);
		paraMap.put("productname", vo.getProductname());
		EventHandlerNetTest.getInstance().getThreadExecutor().executeTask(
				AbstractParallelExecutor.Module_Event, 
                new AbstractTaskRunnable(paraMap){
                    public void run(){
                    	Map paraMap2 = (Map)this.getParamObject();
                    	Long productid = (Long)paraMap2.get("productid");
                    	Long sessUserid = (Long)paraMap2.get("sessUserid");
                    	Long sessOrgid = (Long)paraMap2.get("sessOrgid");
                    	Long shopid = (Long)paraMap2.get("shopid");
                    	String productname = (String)paraMap2.get("productname");
                    	// delete content comments
                    	BOFactory_Platform.getCommentsDao().deleteByObject(productid.toString(), Productbase.ObjectType);
                    	// delete course use comments
                    	BOFactory_Platform.getUsecommentDao().deleteByObject(productid, Productbase.ObjectType);
                    	// 删除考试信息
                		testinfoLogic.deleteTestByProd(productid, shopid);
                		// 删除练习信息 UserExerAnswer and ExerUser
                		exerciseLogic.deleteExerByProd(productid, shopid);
                		// 删除paper及paper question(先删除test,再删除paper)
                		paperLogic.deletePaperByProd(productid, shopid);
                		// 删除learncontent学习内容
                		learncontentLogic.deleteByProd(productid, shopid);
                		// 删除题库, 题库题目等
                		wareLogic.delWareByProd(productid, shopid);
                		// 删除产品学习章节目录
                		contentcateDao.deleteByProd(productid);
                		// 删除产品
                		dao.deleteByPK(productid);
                    	// 删除该产品的所有本地存储文件
                		String quesDir = UploadFileUtil.getUploadFileDir(null, Shop.ObjectType, shopid,  
                							Productbase.ObjectType, productid, null, null);
                    	UploadFileUtilNettest.delFile(null, quesDir, null, null, null);
                    	// 记录谁删除的产品log信息
                    	Productlog logvo = new Productlog();
                    	logvo.setCreatorid(sessUserid);
                    	logvo.setShopid(shopid);
                    	logvo.setProductid(productid);
                    	logvo.setLogtype(Productlog.LogType_Delete);
                    	logvo.setProductname(productname);
                    	Date logdate = DateUtil.getInstance().getNowtime_GLNZ();
                    	logvo.setLogdate(logdate);
                    	productlogDao.insert(logvo);
                    }
				});
	}
	
	
	public GrantedAuthority[] loadContainerAuthority(Long userid, Long productid, Long sessShopid, 
													 GrantedAuthority[] sessionAuthArr){
		Productbase vo = dao.selectByPK(productid);
		if(vo==null){
			return null;
		}
		GrantedAuthority[] rtnArr = null;
		boolean loadshop = true;
		
		Userproduct userprod = userproductDao.selectByLogicPk(userid, productid);
		if(userprod!=null && UserproductConstant.Status_active.equals(userprod.getStatus())) {
			if(UserproductConstant.ProdUseType_operatorMag.equals(userprod.getProdusetype())){
				rtnArr = RolesConstant.GrantedAuthority_ProductAdmin;
				loadshop = false;
			}else {
				rtnArr = RolesConstant.GrantedAuthority_ProductAccessor;
			}
		}
		
		if(loadshop){
			// 如果用户是商店管理员，则给与管理该产品的权限
			GrantedAuthority[] usershopArr = null;
			if(vo.getShopid().equals(sessShopid)){
				usershopArr = sessionAuthArr;
			}else {
				usershopArr = UserLoginSessionLogicImpl.getInstance().loadContainerAuthority(userid, vo.getShopid(), sessShopid, null);
			}
		    if(usershopArr!=null){
		    	for(GrantedAuthority authTemp : usershopArr){
		    		if(commonWeb.security.constant.RolesConstant.ROLE_ShopAdmin.equals(authTemp.getAuthority())
		    			||commonWeb.security.constant.RolesConstant.ROLE_ShopCreator.equals(authTemp.getAuthority()))
		    		{
		    			rtnArr = RolesConstant.GrantedAuthority_ProductAdmin;
		    		}
		    	}
		    }
		}
    	return rtnArr;
	}
	
	
    /**
     * static spring getMethod
     */
     public static ProductLogic getInstance() {
        ProductLogic logic = (ProductLogic)BeanFactory.getBeanFactory().getBean("productLogic");
        return logic;
     }

	public ProductbaseDao getDao() {
		return dao;
	}

	public void setDao(ProductbaseDao dao) {
		this.dao = dao;
	}

	public ProductcategoryDao getCateDao() {
		if(cateDao==null){
			cateDao = ProductcategoryDaoImpl.getInstance();
		}
		return cateDao;
	}

	public void setCateDao(ProductcategoryDao cateDao) {
		this.cateDao = cateDao;
	}

	public UserproductDao getUserproductDao() {
		return userproductDao;
	}

	public void setUserproductDao(UserproductDao userproductDao) {
		this.userproductDao = userproductDao;
	}

	public TestinfoLogic getTestinfoLogic() {
		return testinfoLogic;
	}


	public void setTestinfoLogic(TestinfoLogic testinfoLogic) {
		this.testinfoLogic = testinfoLogic;
	}


	public WareLogic getWareLogic() {
		return wareLogic;
	}


	public void setWareLogic(WareLogic wareLogic) {
		this.wareLogic = wareLogic;
	}


	public LearncontentLogic getLearncontentLogic() {
		return learncontentLogic;
	}


	public void setLearncontentLogic(LearncontentLogic learncontentLogic) {
		this.learncontentLogic = learncontentLogic;
	}


	public ExerciseLogic getExerciseLogic() {
		return exerciseLogic;
	}


	public void setExerciseLogic(ExerciseLogic exerciseLogic) {
		this.exerciseLogic = exerciseLogic;
	}


	public PaperLogic getPaperLogic() {
		return paperLogic;
	}


	public void setPaperLogic(PaperLogic paperLogic) {
		this.paperLogic = paperLogic;
	}


	public ProductlogDao getProductlogDao() {
		return productlogDao;
	}


	public void setProductlogDao(ProductlogDao productlogDao) {
		this.productlogDao = productlogDao;
	}
	
	public ShopDao getShopdao() {
		return ShopDaoImpl.getInstance();
	}

	public GoodproductDao getGoodproductDao() {
		return goodproductDao;
	}

	public void setGoodproductDao(GoodproductDao goodproductDao) {
		this.goodproductDao = goodproductDao;
	}
	
	public ContentcateDao getContentcateDao() {
		return contentcateDao;
	}

	public void setContentcateDao(ContentcateDao contentcateDao) {
		this.contentcateDao = contentcateDao;
	}

	public UserproductLogic getUserproductLogic() {
		return userproductLogic;
	}

	public void setUserproductLogic(UserproductLogic userproductLogic) {
		this.userproductLogic = userproductLogic;
	}
    
}
