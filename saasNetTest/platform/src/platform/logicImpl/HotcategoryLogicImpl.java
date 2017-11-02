package platform.logicImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import platform.bean.BeanFactory;
import platform.dao.HotcategoryDao;
import platform.exception.ExceptionConstant;
import platform.logic.HotcategoryLogic;
import platform.logic.ProductcategoryLogic;
import platform.vo.Hotcategory;
import platform.vo.Productcategory;
import commonTool.cache.CacheSynchronizer;
import commonTool.constant.CommonConstant;
import commonTool.exception.LogicException;

public class HotcategoryLogicImpl implements HotcategoryLogic {
	
	static Logger log = Logger.getLogger(HotcategoryLogicImpl.class.getName());
	private HotcategoryDao dao;
	
	public static final String CacheObjectType_Hotcategory_Localeid = "hotcategory_localeid";
	
	private ProductcategoryLogic productcategoryLogic;

	public HotcategoryDao getDao() {
		return dao;
	}

	public void setDao(HotcategoryDao dao) {
		this.dao = dao;
	}

    /**
     * 查询某个国家的热门目录，返回热门目录列表，每个热门目录之中包含其子热门目录集合
     * @param localeid
     * @return
     */
	@Cacheable(value="platform.categoryCache", key="'Hotcategory.qryHotcategory~'+#localeid+'~'+#syscode+'~'+#isforadmin")
	public List<Hotcategory> qryHotcategory(Long localeid, String syscode, boolean isforadmin){
		if(syscode==null||localeid==null){
			return null;
		}
		Short categorystatus = CommonConstant.Status_valide;
		if(isforadmin){
			categorystatus = null;
		}
		List<Hotcategory> list = dao.selectBySyscodeLocale(localeid, syscode, categorystatus);
		Productcategory cateVO = productcategoryLogic.getSystemTopCategory(syscode, localeid);
		List<Hotcategory> rtnList = null;
		if(cateVO!=null && list!=null && list.size()>0){
			Iterator<Hotcategory> ite = list.iterator();
			rtnList = new ArrayList<Hotcategory>();
			Hotcategory tempVO = null;
			while(ite.hasNext()){
				tempVO = ite.next();
				if(cateVO.getCategoryid().equals(tempVO.getPid())){
					rtnList.add(tempVO);
					ite.remove();
				}
			}
//			for(int i=list.size()-1;-1<i;i--){
//				tempVO = list.get(i);
//				if(cateVO.getCategoryid().equals(tempVO.getPid())){
//					rtnList.add(tempVO);
//					list.remove(i);
//				}
//			}
			for(Hotcategory tempVO2 : rtnList){
				for(int j=0;j<list.size();j++){
					tempVO = list.get(j);
					if(tempVO2.getCategoryid().equals(tempVO.getPid())){
						tempVO2.addSubVO(tempVO);
					}
				}
			}
		}
		if(rtnList!=null){
			CacheSynchronizer.getInstance().buildAssoc("platform.categoryCache", 
					"Hotcategory.qryHotcategory~"+localeid+"~"+syscode+"~"+isforadmin,
					new String[]{CacheObjectType_Hotcategory_Localeid+":all"});
		}
		return rtnList;
	}
	
	/**
	 * 新增热门目录，如果已经存在的热门目录，则抛出异常。如果localeid为空则新增该热门目录的所有国家语言设置
	 * @param categoryid
	 * @param pid
	 * @param localeid
	 * @return
	 * @throws LogicException
	 */
	//@Caching(evict={@CacheEvict(value="platform.categoryCache", key="'Hotcategory.qryHotcategory~'+#localeid+'~'+#syscode")})
	public int addHotCategory(Hotcategory vo) throws LogicException{
		if(vo==null||vo.getCategoryid()==null||vo.getPid()==null)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		int rtn = 1;
		if(vo.getLocaleid()==null){
			// 先删除该热门目录的全部国家语言设置
			dao.delCategoryAllLocale(vo.getCategoryid(), vo.getSyscode());
			// 新增该热门目录的全部国家语言设置
			dao.insertAllLocale(vo);
		}else {
			int exist = dao.exists(vo.getCategoryid(), vo.getLocaleid(), vo.getSyscode());
			if(exist==0){
				dao.insert(vo);
			}else{
				throw new LogicException(ExceptionConstant.Error_Record_Exists);
			}
		}
		CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", CacheObjectType_Hotcategory_Localeid, "all");
		return rtn;
	}
	
	/**
	 * 删除热门目录设置，如果localeid为空，则说明要删除该热门目录的全部国家语言设置
	 * @param categoryid
	 * @param pid
	 * @param localeid
	 * @return
	 * @throws LogicException
	 */
	//@Caching(evict={@CacheEvict(value="platform.categoryCache", key="'Hotcategory.qryHotcategory~'+#localeid+'~'+#syscode")})
	public int delHotCategory(Long categoryid, Long localeid, String syscode) throws LogicException{
		if(categoryid==null||syscode==null||syscode.trim().length()<1)
			throw new LogicException(ExceptionConstant.Error_Need_Paramter);
		int rtn = 1;
		// check whether hot category has child category
		int exist = dao.existsChild(categoryid, syscode);
		if(exist==0){
			if(localeid!=null){
				dao.deleteByPK(categoryid, localeid, syscode);
			}else {
	            dao.delCategoryAllLocale(categoryid, syscode);
			}
		}else {
			throw new LogicException(ExceptionConstant.Error_Record_BeenReference);
		}
		CacheSynchronizer.getInstance().pubFlush("platform.categoryCache", CacheObjectType_Hotcategory_Localeid, "all");
		return rtn;
	}
    
    /**
     * static spring getMethod
     */
     public static HotcategoryLogic getInstance(){
       	 HotcategoryLogic logic = (HotcategoryLogic)BeanFactory.getBeanFactory().getBean("hotcategoryLogic");
         return logic;
     }

	public ProductcategoryLogic getProductcategoryLogic() {
		return productcategoryLogic;
	}

	public void setProductcategoryLogic(ProductcategoryLogic productcategoryLogic) {
		this.productcategoryLogic = productcategoryLogic;
	}
    

}
